package desktopApp.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.BirlikService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
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
public class BirlikController {
	
	@Autowired
	private BirlikService birlikService;
	
	@FXML
	TextField txtFieldAd;

	@FXML
	Button buttonAdd, buttonUpdate, buttonDelete;
	
	private Birlik birlik;
	
	
	public void setBirlik(Birlik birlik) {
		this.birlik = birlik;
		if(birlik != null) {
			fillBirlik();
		}
		
	}


	private void fillBirlik() {
		txtFieldAd.setText(birlik.getAd());
		
	}
	
	public void addBirlik() {
		DataResult<Birlik> newBirlik = birlikService.add(new Birlik(txtFieldAd.getText()));
		if(newBirlik.isSuccess()) {
			this.birlik = newBirlik.getData();
		}
		new MessagePage().show(newBirlik);
	}
	
	public void updateBirlik() {
		if (this.birlik == null){
			new MessagePage().show(new ErrorResult("Birlik Mevcut Değildir."));
			return;
		}
		this.birlik.setAd(txtFieldAd.getText());
		DataResult<Birlik> result = birlikService.save(this.birlik);
		new MessagePage().show(result);
	}
	
	public void deleteBirlik() {
		
		if(this.birlik == null) {
			new MessagePage().show(new Result(false, "Birlik Bulunamadı."));
			return;
		}
		Result result = birlikService.getById(this.birlik.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Birlik Bulunamadı. Sayfayı Yenileyiniz."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Birlik Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			result = birlikService.delete(this.birlik);
			if(result.isSuccess()) {
				this.birlik = null;
//				buttonAdd.setDisable(true);
//				buttonUpdate.setDisable(true);
//				buttonDelete.setDisable(true);
				((Stage) buttonDelete.getScene().getWindow()).close();
			}
			new MessagePage().show(result);
		}
		
		
	}
}
