package desktopApp.ui.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.BirlikService;
import akdmEtkinlikEnvanter.business.abstracts.SinavService;
import akdmEtkinlikEnvanter.business.abstracts.SinavSonucService;
import akdmEtkinlikEnvanter.business.abstracts.SinavTuruService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Sinav;
import akdmEtkinlikEnvanter.entities.concretes.SinavTuru;
import akdmEtkinlikEnvanter.entities.concretes.TezTuru;
import desktopApp.ui.pages.MessagePage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

@Component
public class SinavController {
	@Autowired
	private SinavService sinavService;
	@Autowired
	private SinavTuruService sinavTuruService;
	@Autowired
	private SinavSonucService sinavSonucService;
	
	@FXML
	ComboBox<SinavTuru> cmBoxSinavTuru;
	
	@FXML
	DatePicker datePickerTarih;
	
	@FXML
	Button buttonAdd, buttonUpdate, buttonDelete;
	
	private Sinav sinav;
	
	@FXML
	private void initialize() {
		fillSinavTuru();
	}
	
	public void refrashPage() {
		cmBoxSinavTuru.getItems().clear();
		fillSinav();
		fillSinavTuru();
	}
	
	public void setSinav(Sinav sinav) {
		this.sinav = sinav;
		if(sinav != null) {
			fillSinav();
			fillSinavTuru();
		}
		
	}


	private void fillSinav() {
		if(this.sinav != null)
			datePickerTarih.setValue(this.sinav.getTarih());
		
	}
	
	public void addSinav() {
		DataResult<Sinav> newSinav = sinavService.add(new Sinav(datePickerTarih.getValue(), cmBoxSinavTuru.getValue()));
		if(newSinav.isSuccess()) {
			this.sinav = newSinav.getData();
		}
		new MessagePage().show(newSinav);
	}
	
	public void updateSinav() {
		if (this.sinav == null){
			new MessagePage().show(new ErrorResult("Sınav Mevcut Değildir."));
			return;
		}
		DataResult<Sinav> result = sinavService.getById(this.sinav.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Sınav Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		
		this.sinav.setSinavTuru(cmBoxSinavTuru.getValue());
		this.sinav.setTarih(datePickerTarih.getValue());
		result = sinavService.save(this.sinav);
		new MessagePage().show(result);
	}
	
	public void deleteSinav() {
		if(this.sinav == null) {
			new MessagePage().show(new Result(false, "Sınav Bulunamadı."));
			return;
		}
		Result result = sinavService.getById(this.sinav.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Sınav Bulunamadı. Sayfayı Yenileyiniz."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Sınav Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			Alert alert1 = new Alert(AlertType.CONFIRMATION);
			alert1.setTitle("Sınav Sil");

			alert1.setContentText(sinavSonucService.getBySinav(this.sinav).getData().toString());
			alert1.setHeaderText("Bu Sınav Silinirse Aşağıdaki Sınav Sonuçlarının Tümü de Silinecektir.!!!!!!!!!");
			if(alert1.showAndWait().get() == ButtonType.OK) {
				result = sinavService.delete(this.sinav);
				
				if(result.isSuccess()) {
					this.sinav = null;
//					buttonAdd.setDisable(true);
//					buttonUpdate.setDisable(true);
//					buttonDelete.setDisable(true);
					((Stage) buttonDelete.getScene().getWindow()).close();
				}
				new MessagePage().show(result);
			}	
		}
		
		
	}
	
	private void fillSinavTuru() {
		ObservableList<SinavTuru> sinavTurleri = FXCollections.observableArrayList();
		for (SinavTuru sinavTuru : sinavTuruService.getAll().getData()) {
			sinavTurleri.add(sinavTuru);
		}
		if(cmBoxSinavTuru.getSelectionModel().getSelectedItem() == null && sinav != null)
			cmBoxSinavTuru.getSelectionModel().select(sinav.getSinavTuru());
		
		cmBoxSinavTuru.setItems(sinavTurleri);
		
	}
}
