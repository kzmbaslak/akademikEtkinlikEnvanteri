package desktopApp.ui.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.RutbeService;
import akdmEtkinlikEnvanter.business.abstracts.SinifService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Rutbe;
import akdmEtkinlikEnvanter.entities.concretes.Sinif;
import desktopApp.ui.pages.MessagePage;
import desktopApp.ui.pages.RutbePage;
import desktopApp.ui.pages.SinifPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

@Component
public class RutbeController {

	@Autowired
	private RutbeService rutbeService;
	@Autowired
	private SinifService sinifService;
	
	private Rutbe rutbe;
	
	@FXML
	private TextField txtFieldAd;

	@FXML
	private Button buttonAdd, buttonUpdate, buttonDelete;
	
	@FXML
	private ComboBox<Sinif> cmBoxSinif;
	
	@FXML
	private void initialize() {
		fillSinif();
	}
	
	public void setRutbe(Rutbe rutbe) {
		this.rutbe = rutbe;
		if(rutbe != null) {
			fillRutbe();
		}
		fillSinif();
	}

	private void fillRutbe() {
		txtFieldAd.setText(rutbe.getAd());
		
	}
	
	public void fillSinif() {
		List<Sinif> siniflar = new ArrayList<Sinif>();
		for (Sinif sinif : sinifService.getAll().getData()) {
			siniflar.add(sinif);
		}
		//cmBoxRutbe.setPromptText(personel.getRutbe().getAd());
		if(cmBoxSinif.getSelectionModel().getSelectedItem() == null && this.rutbe != null)
			cmBoxSinif.getSelectionModel().select(this.rutbe.getSinif());
		
		cmBoxSinif.getItems().clear();
		cmBoxSinif.getItems().addAll(siniflar);
		
	}
	
	public void addRutbe() {
		DataResult<Rutbe> newRutbe = rutbeService.add(new Rutbe(txtFieldAd.getText(),cmBoxSinif.getValue()));
		if(newRutbe.isSuccess()) {
			this.rutbe = newRutbe.getData();
		}
		new MessagePage().show(newRutbe);
	}
	
	public void updateRutbe() {
		if (this.rutbe == null){
			new MessagePage().show(new ErrorResult("R??tbe Mevcut De??ildir."));
			return;
		}
		rutbe.setAd(txtFieldAd.getText());
		rutbe.setSinif(cmBoxSinif.getValue());
		DataResult<Rutbe> result = rutbeService.save(rutbe);
		new MessagePage().show(result);
	}
	
	public void deleteRutbe() {
		if(this.rutbe == null) {
			new MessagePage().show(new Result(false, "R??tbe Bulunamad??."));
			return;
		}
		Result result = rutbeService.getById(this.rutbe.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("R??tbe Bulunamad??. Sayfay?? Yenileyiniz."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Tez Sil");
		alert.setContentText("Silmek istedi??inizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			result = rutbeService.delete(this.rutbe);
			if(result.isSuccess()) {
				this.rutbe = null;
//				buttonAdd.setDisable(true);
//				buttonUpdate.setDisable(true);
//				buttonDelete.setDisable(true);
				((Stage) buttonDelete.getScene().getWindow()).close();
			}
			new MessagePage().show(result);
		}
		
	}
	
	public void addSinif(ActionEvent e) {
		new SinifPage().show(null);
	}
	
	public void updateSinif(ActionEvent e) {
		Sinif sinif = cmBoxSinif.getSelectionModel().getSelectedItem();
		if(sinif == null) {
			new MessagePage().show(new Result(false, "Bir S??n??f Se??iniz."));
			return;
		}
		Result result = sinifService.getById(sinif.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu R??tbe  Daha ??nce Silinmi??tir. Sayfay?? Yenileyiniz."));
			return;
		}
		
		new SinifPage().show(sinif);
	}
}
