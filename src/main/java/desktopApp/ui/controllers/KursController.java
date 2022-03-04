package desktopApp.ui.controllers;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.KursService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessResult;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import desktopApp.ui.pages.MessagePage;
import desktopApp.utilities.javaFxOperations.FileOperations;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

@Component
public class KursController {
	
	@Autowired
	private KursService kursService;
	@Autowired 
	private FileOperations fileOperations;
	
	@FXML
	TextField txtFieldAd;

	@FXML
	Text txtFileName;
	
	@FXML
	Button buttonAdd, buttonUpdate, buttonDelete;
	
	private Kurs kurs;
	
	public void setKurs(Kurs kurs) {
		this.kurs = kurs;
		if(kurs != null) {
			fillKurs();
		}
		
	}


	private void fillKurs() {
		txtFieldAd.setText(kurs.getAd());
		
	}
	

	public void addKurs() {
		DataResult<Kurs> newKurs = kursService.add(new Kurs(txtFieldAd.getText()));
		if(newKurs.isSuccess()) {
			this.kurs = newKurs.getData();
		}
		new MessagePage().show(newKurs);
	}
	
	public void updateKurs() {
		if (this.kurs == null){
			new MessagePage().show(new ErrorResult("Kurs Mevcut Değildir."));
			return;
		}
		this.kurs.setAd(txtFieldAd.getText());
		DataResult<Kurs> result = kursService.save(this.kurs);
		new MessagePage().show(result);
	}
	
	public void deleteKurs() {
		
		if(this.kurs == null) {
			new MessagePage().show(new Result(false, "Kurs Bulunamadı."));
			return;
		}
		Result result = kursService.findById(this.kurs.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Kurs Bulunamadı. Sayfayı Yenileyiniz."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Kurs Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			result = kursService.delete(this.kurs);
			if(result.isSuccess()) {
				this.kurs = null;
//				buttonAdd.setDisable(true);
//				buttonUpdate.setDisable(true);
//				buttonDelete.setDisable(true);
				((Stage) buttonDelete.getScene().getWindow()).close();
			}
			new MessagePage().show(result);
		}
		
		
	}
	
	
}
