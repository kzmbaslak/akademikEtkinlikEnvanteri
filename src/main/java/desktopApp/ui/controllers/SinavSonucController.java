package desktopApp.ui.controllers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.PersonelService;
import akdmEtkinlikEnvanter.business.abstracts.SinavService;
import akdmEtkinlikEnvanter.business.abstracts.SinavSonucService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Rutbe;
import akdmEtkinlikEnvanter.entities.concretes.Sinav;
import akdmEtkinlikEnvanter.entities.concretes.SinavSonuc;
import desktopApp.ui.pages.MessagePage;
import desktopApp.ui.pages.PersonelPage;
import desktopApp.ui.pages.SinavPage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import lombok.Data;

@Component
@Data
public class SinavSonucController {

	@Autowired
	private SinavSonucService sinavSonucService;
	@Autowired
	private PersonelService personelService;
	@Autowired
	private SinavService sinavService;
	
	private SinavSonuc sinavSonuc;
	
	@FXML
	private TextField txtFieldNot, txtFieldSearch;
	
	@FXML
	private ComboBox<Personel> cmBoxPersonel;

	@FXML
	ComboBox<Sinav> cmBoxSinav;
	
	public void refrashPage() {
		txtFieldNot.clear();
		txtFieldSearch.clear();
		cmBoxSinav.getItems().clear();
		fillSinavSonuc();
		fillSinav();
		fillPersonel();
		
	}
	
	public void setSinavSonuc(SinavSonuc sinavSonuc) {
		this.sinavSonuc = sinavSonuc;
		
		fillSinavSonuc();
		fillSinav();
		fillPersonel();
	}

	private void fillSinavSonuc() {
		txtFieldNot.setText(sinavSonuc.getSinavNot()+"");
	}

	private void fillSinav() {
		if(sinavSonuc.getSinav().getId() != 0)
			cmBoxSinav.getSelectionModel().select(this.sinavSonuc.getSinav());
		cmBoxSinav.getItems().addAll(sinavService.findBySinavTuru(
				sinavSonuc.getSinav().getSinavTuru()).getData());
		
	}
	
	public void fillPersonel() {
		List<Personel> personeller = personelService.findByAdContainingIgnoreCase(txtFieldSearch.getText()).getData();
		if(cmBoxPersonel.getSelectionModel().getSelectedItem() == null && this.sinavSonuc != null)
			cmBoxPersonel.getSelectionModel().select(this.sinavSonuc.getPersonel());

		ObservableList<Personel> personelObservable = FXCollections.observableArrayList(
				personeller
				);
		cmBoxPersonel.setItems(personelObservable);
	}
	
	public void addSinavSonuc() {
		SinavSonuc newSinavSonuc = new SinavSonuc();
		try
		{
			Double not = Double.parseDouble(txtFieldNot.getText());
			newSinavSonuc.setSinavNot(not);
		}
		catch(Exception e) {
			new MessagePage().show(new ErrorResult("Not say??sal bir de??er olmak zorundad??r."));
			return;
		}
		
		newSinavSonuc.setPersonel(cmBoxPersonel.getValue());
		newSinavSonuc.setSinav(cmBoxSinav.getValue());
		DataResult<SinavSonuc> result = sinavSonucService.add(newSinavSonuc);
		if(result.isSuccess()) {
			this.sinavSonuc = result.getData();
		}
		new MessagePage().show(result);
	}
	
	public void updateSinavSonuc() {
		if (this.sinavSonuc == null || this.sinavSonuc.getId() == 0){
			new MessagePage().show(new ErrorResult("S??nav Sonucu Mevcut De??ildir."));
			return;
		}
		try
		{
			Double not = Double.parseDouble(txtFieldNot.getText());
			this.sinavSonuc.setSinavNot(not);
		}
		catch(Exception e) {
			new MessagePage().show(new ErrorResult("Not say??sal bir de??er olmak zorundad??r."));
			return;
		}
		this.sinavSonuc.setPersonel(cmBoxPersonel.getValue());
		this.sinavSonuc.setSinav(cmBoxSinav.getValue());
		DataResult<SinavSonuc> result = sinavSonucService.save(sinavSonuc);
		new MessagePage().show(result);
	}
	
	public void deleteSinavSonuc() {
		if(this.sinavSonuc == null) {
			new MessagePage().show(new Result(false, "S??nav Sonucu Bulunamad??."));
			return;
		}
		Result result = sinavSonucService.getById(this.sinavSonuc.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("S??nav Sonucu Bulunamad??. Sayfay?? Yenileyiniz."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("S??nav Sonucu Sil");
		alert.setContentText("Silmek istedi??inizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			result = sinavSonucService.delete(sinavSonuc);
			if(result.isSuccess()) {
				this.sinavSonuc = null;
//				buttonAdd.setDisable(true);
//				buttonUpdate.setDisable(true);
//				buttonDelete.setDisable(true);
				((Stage) txtFieldNot.getScene().getWindow()).close();
			}
			new MessagePage().show(result);
		}
	}
	
	
	public void editPersonel(ActionEvent e) {
		
		Personel personel = cmBoxPersonel.getValue();
		if(personel == null) {
			new MessagePage().show(new ErrorResult("Bir Personel Se??iniz!!!"));
			return;
		}
		Result result = personelService.getById(personel.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Personel  Daha ??nce Silinmi??tir. Sayfay?? Yenileyiniz."));
			return;
		}
		
		
		new PersonelPage().show(personel);
			
	}
	
	public void editSinav(ActionEvent e) {
		Sinav sinav = cmBoxSinav.getValue();
		if(sinav == null) {
			new MessagePage().show(new ErrorResult("Bir S??nav Se??iniz!!!"));
			return;
		}
		Result result = sinavService.getById(sinav.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu S??nav  Daha ??nce Silinmi??tir. Sayfay?? Yenileyiniz."));
			return;
		}
		
		
		new SinavPage().show(sinav);
	}
}
