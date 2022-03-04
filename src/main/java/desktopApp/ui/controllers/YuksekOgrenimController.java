package desktopApp.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.TezService;
import akdmEtkinlikEnvanter.business.abstracts.YuksekOgrenimService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.YuksekOgrenim;
import desktopApp.ui.pages.MessagePage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

@Component
public class YuksekOgrenimController {

	@Autowired
	private YuksekOgrenimService yuksekOgrenimService;
	@Autowired
	private TezService tezService;
	
	@FXML
	TextField txtFieldAlanAd;

	@FXML
	Button buttonAdd, buttonUpdate, buttonDelete;
	
	private YuksekOgrenim yuksekOgrenim;
	
	
	public void setYuksekOgrenim(YuksekOgrenim yuksekOgrenim) {
		this.yuksekOgrenim = yuksekOgrenim;
		if(yuksekOgrenim != null) {
			fillYuksekOgrenim();
		}
		
	}


	private void fillYuksekOgrenim() {
		txtFieldAlanAd.setText(yuksekOgrenim.getAlanAdi());
		
	}
	
	public void addYuksekOgrenim() {
		DataResult<YuksekOgrenim> newYuksekOgrenim = yuksekOgrenimService.add(new YuksekOgrenim(txtFieldAlanAd.getText()));
		if(newYuksekOgrenim.isSuccess()) {
			this.yuksekOgrenim = newYuksekOgrenim.getData();
		}
		new MessagePage().show(newYuksekOgrenim);
	}
	
	public void updateYuksekOgrenim() {
		if (this.yuksekOgrenim == null){
			new MessagePage().show(new ErrorResult("Yüksek Öğrenim Mevcut Değildir."));
			return;
		}
		this.yuksekOgrenim.setAlanAdi(txtFieldAlanAd.getText());
		DataResult<YuksekOgrenim> result = yuksekOgrenimService.save(this.yuksekOgrenim);
		new MessagePage().show(result);
	}
	
	public void deleteYuksekOgrenim() {
		if(this.yuksekOgrenim == null) {
			new MessagePage().show(new Result(false, "Yüksek Öğrenim Bulunamadı."));
			return;
		}
		Result result = yuksekOgrenimService.getById(this.yuksekOgrenim.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Yüksek Öğrenim Bulunamadı. Sayfayı Yenileyiniz."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Yüksek Öğrenim Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			Alert alert1 = new Alert(AlertType.CONFIRMATION);
			alert1.setTitle("Yüksek Öğrenim Sil");

			alert1.setContentText(tezService.findByYuksekOgrenim(this.yuksekOgrenim).getData().toString());
			alert1.setHeaderText("Bu Yüksek Öğrenim Silinirse Aşağıdaki Tezlerin Tümü de Silinecektir.!!!!!!!!!");
			if(alert1.showAndWait().get() == ButtonType.OK) {
				result = yuksekOgrenimService.delete(this.yuksekOgrenim);
				if(result.isSuccess()) {
					this.yuksekOgrenim = null;
//					buttonAdd.setDisable(true);
//					buttonUpdate.setDisable(true);
//					buttonDelete.setDisable(true);
					((Stage) buttonDelete.getScene().getWindow()).close();
				}
				new MessagePage().show(result);
			}
		}
	}
}
