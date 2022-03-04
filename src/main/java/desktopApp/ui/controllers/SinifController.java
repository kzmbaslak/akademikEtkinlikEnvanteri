package desktopApp.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.SinifService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Rutbe;
import akdmEtkinlikEnvanter.entities.concretes.Sinif;
import desktopApp.ui.pages.MessagePage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

@Component
public class SinifController {

	@Autowired
	private SinifService sinifService;
	
	private Sinif sinif;

	@FXML
	private TextField txtFieldAd;

	@FXML
	private Button buttonAdd, buttonUpdate, buttonDelete;
	
	public void setSinif(Sinif sinif) {
		this.sinif = sinif;
		if(sinif != null) {
			fillSinif();
		}
	}

	private void fillSinif() {
		txtFieldAd.setText(sinif.getAd());
		
	}
	
	public void addSinif() {
		DataResult<Sinif> newSinif = sinifService.add(new Sinif(txtFieldAd.getText()));
		if(newSinif.isSuccess()) {
			this.sinif = newSinif.getData();
		}
		new MessagePage().show(newSinif);
	}
	
	public void updateSinif() {
		if (this.sinif == null){
			new MessagePage().show(new ErrorResult("Sınıf Mevcut Değildir."));
			return;
		}
		sinif.setAd(txtFieldAd.getText());
		DataResult<Sinif> result = sinifService.save(sinif);
		new MessagePage().show(result);
	}
	
	public void deleteSinif() {
		if(this.sinif == null) {
			new MessagePage().show(new Result(false, "Sınıf Bulunamadı."));
			return;
		}
		Result result = sinifService.getById(this.sinif.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Sınıf Bulunamadı. Sayfayı Yenileyiniz."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Sınıf Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			result = sinifService.delete(this.sinif);
			if(result.isSuccess()) {
				this.sinif = null;
//				buttonAdd.setDisable(true);
//				buttonUpdate.setDisable(true);
//				buttonDelete.setDisable(true);
				((Stage) buttonDelete.getScene().getWindow()).close();
			}
			new MessagePage().show(result);
		}
		
	}
}
