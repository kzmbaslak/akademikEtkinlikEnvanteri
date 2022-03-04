package desktopApp.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.BelgeService;
import akdmEtkinlikEnvanter.business.abstracts.BelgeTuruService;
import akdmEtkinlikEnvanter.business.abstracts.BirlikService;
import akdmEtkinlikEnvanter.business.abstracts.KitapService;
import akdmEtkinlikEnvanter.business.abstracts.YayinService;
import akdmEtkinlikEnvanter.business.abstracts.YayineviDergiService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.BelgeTuru;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Yayin;
import akdmEtkinlikEnvanter.entities.concretes.YayineviDergi;
import desktopApp.ui.pages.MessagePage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

@Component
public class YayineviDergiController {
	
	@Autowired
	private YayineviDergiService yayineviDergiService;
	@Autowired
	private YayinService yayinService;
	@Autowired
	private KitapService kitapService;
	
	@FXML
	TextField txtFieldAd;

	@FXML
	Button buttonAdd, buttonUpdate, buttonDelete;
	
	private YayineviDergi yayineviDergi;
	
	
	public void setYayineviDergi(YayineviDergi yayineviDergi) {
		this.yayineviDergi = yayineviDergi;
		if(yayineviDergi != null) {
			fillYayineviDergi();
		}
		
	}

	public void refrashPage() {
		txtFieldAd.clear();
		
		
		if(this.yayineviDergi != null) {
			DataResult<YayineviDergi> result = yayineviDergiService.getById(this.yayineviDergi.getId());
			if(result.isSuccess()) {
				yayineviDergi = result.getData();
				if(yayineviDergi != null) {
					fillYayineviDergi();
				}
				
			}
		}
	}
	
	private void fillYayineviDergi() {
		txtFieldAd.setText(yayineviDergi.getAd());
		
	}
	
	public void addYayineviDergi() {
		DataResult<YayineviDergi> newYayineviDergi = yayineviDergiService.add(new YayineviDergi(0,txtFieldAd.getText(),null,null));
		if(newYayineviDergi.isSuccess()) {
			this.yayineviDergi = newYayineviDergi.getData();
		}
		new MessagePage().show(newYayineviDergi);
	}
	
	public void updateYayineviDergi() {
		if (this.yayineviDergi == null){
			new MessagePage().show(new ErrorResult("Yayınevi Dergi Mevcut Değildir."));
			return;
		}
		this.yayineviDergi.setAd(txtFieldAd.getText());
		DataResult<YayineviDergi> result = yayineviDergiService.save(this.yayineviDergi);
		new MessagePage().show(result);
	}
	
	public void deleteYayineviDergi() {
		
		if(this.yayineviDergi == null) {
			new MessagePage().show(new Result(false, "Yayınevi Dergi Bulunamadı."));
			return;
		}
		Result result = yayineviDergiService.getById(this.yayineviDergi.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Yayınevi Dergi Bulunamadı. Sayfayı Yenileyiniz."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Yayınevi Dergi Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			Alert alert1 = new Alert(AlertType.CONFIRMATION);
			alert1.setTitle("Belge Türü Sil");

			alert1.setContentText(yayinService.getByYayineviDergi(this.yayineviDergi).getData().toString());
			alert1.setContentText(kitapService.getByYayineviDergi(this.yayineviDergi).getData().toString());
			alert1.setHeaderText("Bu Yayınevi Dergi Silinirse Aşağıdaki Belgeler ve Kitapların Tümü de Silinecektir.!!!!!!!!!");
			if(alert1.showAndWait().get() == ButtonType.OK) {
				result = yayineviDergiService.delete(this.yayineviDergi);
				
				if(result.isSuccess()) {
					this.yayineviDergi = null;
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
