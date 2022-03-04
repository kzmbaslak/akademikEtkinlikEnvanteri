package desktopApp.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.BelgeService;
import akdmEtkinlikEnvanter.business.abstracts.BelgeTuruService;
import akdmEtkinlikEnvanter.business.abstracts.BirlikService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Belge;
import akdmEtkinlikEnvanter.entities.concretes.BelgeTuru;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import desktopApp.ui.pages.MessagePage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

@Component
public class BelgeTuruController {
	
	@Autowired
	private BelgeTuruService belgeTuruService;
	@Autowired
	private BelgeService belgeService;
	
	@FXML
	TextField txtFieldAd;

	@FXML
	Button buttonAdd, buttonUpdate, buttonDelete;
	
	private BelgeTuru belgeTuru;
	
	
	public void setBelgeTuru(BelgeTuru belgeTuru) {
		this.belgeTuru = belgeTuru;
		if(belgeTuru != null) {
			fillBelgeTuru();
		}
		
	}
	public void refrashPage() {
		txtFieldAd.clear();
		
		if(this.belgeTuru != null) {
			DataResult<BelgeTuru> result = belgeTuruService.getById(this.belgeTuru.getId());
			if(result.isSuccess()) {
				belgeTuru = result.getData();
				if(belgeTuru != null) {
					fillBelgeTuru();
				}
			}
		}
	}

	private void fillBelgeTuru() {
		txtFieldAd.setText(belgeTuru.getAd());
		
	}
	
	public void addBelgeTuru() {
		DataResult<BelgeTuru> newBelgeTuru = belgeTuruService.add(new BelgeTuru(0,txtFieldAd.getText(),null));
		if(newBelgeTuru.isSuccess()) {
			this.belgeTuru = newBelgeTuru.getData();
		}
		new MessagePage().show(newBelgeTuru);
	}
	
	public void updateBelgeTuru() {
		if (this.belgeTuru == null){
			new MessagePage().show(new ErrorResult("Belge Türü Mevcut Değildir."));
			return;
		}
		this.belgeTuru.setAd(txtFieldAd.getText());
		DataResult<BelgeTuru> result = belgeTuruService.save(this.belgeTuru);
		new MessagePage().show(result);
	}
	
	public void deleteBelgeTuru() {
		
		if(this.belgeTuru == null) {
			new MessagePage().show(new Result(false, "Belge Türü Bulunamadı."));
			return;
		}
		Result result = belgeTuruService.getById(this.belgeTuru.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Belge Türü Bulunamadı. Sayfayı Yenileyiniz."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Belge Türü Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			Alert alert1 = new Alert(AlertType.CONFIRMATION);
			alert1.setTitle("Belge Türü Sil");

			alert1.setContentText(belgeService.getByBelgeTuru(this.belgeTuru).getData().toString());
			alert1.setHeaderText("Bu Belge Türü Silinirse Aşağıdaki Belgelerin Tümü de Silinecektir.!!!!!!!!!");
			if(alert1.showAndWait().get() == ButtonType.OK) {
				result = belgeTuruService.delete(this.belgeTuru);
				
				if(result.isSuccess()) {
					this.belgeTuru = null;
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
