package desktopApp.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.TezService;
import akdmEtkinlikEnvanter.business.abstracts.UniversiteEnstituService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.UniversiteEnstitu;
import desktopApp.ui.pages.MessagePage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

@Component
public class UniversiteEnstituController {
	@Autowired
	private UniversiteEnstituService universiteEnstituService;
	@Autowired
	private TezService tezService;
	@FXML
	TextField txtFieldAd;

	@FXML
	Button buttonAdd, buttonUpdate, buttonDelete;
	
	private UniversiteEnstitu universiteEnstitu;
	
	
	public void setUniversiteEnstitu(UniversiteEnstitu universiteEnstitu) {
		this.universiteEnstitu = universiteEnstitu;
		if(universiteEnstitu != null) {
			fillUniversiteEnstitu();
		}
		
	}


	private void fillUniversiteEnstitu() {
		txtFieldAd.setText(this.universiteEnstitu.getAd());
		
	}
	
	public void addUniversiteEnstitu() {
		DataResult<UniversiteEnstitu> newUniversiteEnstitu = universiteEnstituService.add(new UniversiteEnstitu(txtFieldAd.getText()));
		if(newUniversiteEnstitu.isSuccess()) {
			this.universiteEnstitu = newUniversiteEnstitu.getData();
		}
		new MessagePage().show(newUniversiteEnstitu);
	}
	
	public void updateUniversiteEnstitu() {
		if (this.universiteEnstitu == null){
			new MessagePage().show(new ErrorResult("Üniversite/Enstitü Mevcut Değildir."));
			return;
		}
		this.universiteEnstitu.setAd(txtFieldAd.getText());
		DataResult<UniversiteEnstitu> result = universiteEnstituService.save(this.universiteEnstitu);
		new MessagePage().show(result);
	}
	
	public void deleteUniversiteEnstitu() {
		if(this.universiteEnstitu == null) {
			new MessagePage().show(new Result(false, "Üniversite Enstitü Bulunamadı."));
			return;
		}
		Result result = universiteEnstituService.getById(this.universiteEnstitu.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Üniversite Enstitü Bulunamadı. Sayfayı Yenileyiniz."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Üniversite/Enstitü Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			Alert alert1 = new Alert(AlertType.CONFIRMATION);
			alert1.setTitle("Üniversite/Enstitü Sil");

			alert1.setContentText(tezService.findByUniversiteEnstitu(this.universiteEnstitu).getData().toString());
			alert1.setHeaderText("Bu Yüksek Öğrenim Silinirse Aşağıdaki Tezlerin Tümü de Silinecektir.!!!!!!!!!");
			if(alert1.showAndWait().get() == ButtonType.OK) {
				result = universiteEnstituService.delete(this.universiteEnstitu);
				
				if(result.isSuccess()) {
					this.universiteEnstitu = null;
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
