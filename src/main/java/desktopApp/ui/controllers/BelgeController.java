package desktopApp.ui.controllers;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.BelgeService;
import akdmEtkinlikEnvanter.business.abstracts.BelgeTuruService;
import akdmEtkinlikEnvanter.business.abstracts.BirlikService;
import akdmEtkinlikEnvanter.business.abstracts.PersonelService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Belge;
import akdmEtkinlikEnvanter.entities.concretes.BelgeTuru;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.TezTuru;
import akdmEtkinlikEnvanter.entities.concretes.Yayin;
import desktopApp.ui.DesktopApplication;
import desktopApp.ui.pages.BelgeTuruPage;
import desktopApp.ui.pages.BirlikPage;
import desktopApp.ui.pages.MessagePage;
import desktopApp.ui.pages.PersonelPage;
import desktopApp.utilities.javaFxOperations.FileOperations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@Component
public class BelgeController {

	@Autowired
	private BelgeService belgeService;
	@Autowired
	private BelgeTuruService belgeTuruService;
	@Autowired
	private PersonelService personelService;
	

	@Autowired 
	FileOperations fileOperations;
	
	@FXML
	TextField txtFieldAd;

	@FXML
	ComboBox<BelgeTuru> cmBoxBelgeTuru;
	@FXML
	ComboBox<Personel> cmBoxPersonel;
	
	@FXML
	Button buttonAdd, buttonUpdate, buttonDelete;
	
	@FXML
	Text txtFileName;

	private File belgeFile;
	
	private Belge belge;
	
	@FXML
	private void initialize() {
		belgeFile = null;
		
	}
	
	public void setBelge(Belge belge) {
		this.belge = belge;
		if(belge != null) {
			fillBelge();
		}
		fillBelgeTuru();
		fillPersonel();
		
	}

	public void refrashPage() {
		txtFieldAd.clear();
		txtFileName.setText("");
		
		cmBoxPersonel.getItems().clear();
		cmBoxBelgeTuru.getItems().clear();
		belgeFile = null;
		
		if(this.belge != null) {
			DataResult<Belge> result = belgeService.getById(this.belge.getId());
			if(result.isSuccess()) {
				belge = result.getData();
				if(belge != null) {
					fillBelge();
				}
				
			}
		}
		fillBelgeTuru();
		fillPersonel();
	}

	private void fillPersonel() {
		ObservableList<Personel> personeller = FXCollections.observableArrayList();
		for (Personel personel : personelService.findAllByOrderByAdAsc().getData()) {
			personeller.add(personel);
		}
		if(cmBoxPersonel.getSelectionModel().getSelectedItem() == null && belge != null)
			cmBoxPersonel.getSelectionModel().select(belge.getPersonel());
		cmBoxPersonel.setItems(personeller);
	}


	private void fillBelgeTuru() {
		ObservableList<BelgeTuru> belgeTurleri = FXCollections.observableArrayList();
		for (BelgeTuru belgeTuru : belgeTuruService.getAll().getData()) {
			belgeTurleri.add(belgeTuru);
		}
		if(cmBoxBelgeTuru.getSelectionModel().getSelectedItem() == null && belge != null)
			cmBoxBelgeTuru.getSelectionModel().select(belge.getBelgeTuru());
		cmBoxBelgeTuru.setItems(belgeTurleri);
		
	}


	private void fillBelge() {
		if(this.belge != null) {
			txtFieldAd.setText(belge.getAd());
			txtFileName.setText(belge.getFile()+"");
		}
	}
	
	public void addBelge() {
		DataResult<Belge> newBelge = belgeService.add(new Belge(0,txtFieldAd.getText(),
				cmBoxBelgeTuru.getValue(),cmBoxPersonel.getValue(),this.belgeFile));
		if(newBelge.isSuccess()) {
			this.belge = newBelge.getData();
		}
		new MessagePage().show(newBelge);
	}
	
	public void updateBelge() {
		if (this.belge == null){
			new MessagePage().show(new ErrorResult("Belge Mevcut Değildir."));
			return;
		}
		this.belge.setAd(txtFieldAd.getText());
		this.belge.setBelgeTuru(cmBoxBelgeTuru.getValue());
		this.belge.setPersonel(cmBoxPersonel.getValue());
		this.belge.setFile(this.belgeFile);
		
		DataResult<Belge> result = belgeService.save(this.belge);
		new MessagePage().show(result);
	}
	
	public void deleteBelge() {
		
		if(this.belge == null) {
			new MessagePage().show(new Result(false, "Belge Bulunamadı."));
			return;
		}
//		Result result = belgeService.getById(this.belge.getId());
//		if(!result.isSuccess()) {
//			new MessagePage().show(new ErrorResult("Belge Bulunamadı. Sayfayı Yenileyiniz."));
//			return;
//		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Belge Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			Result result = belgeService.delete(this.belge);
			if(result.isSuccess()) {
				this.belge = null;
//				buttonAdd.setDisable(true);
//				buttonUpdate.setDisable(true);
//				buttonDelete.setDisable(true);
				((Stage) buttonDelete.getScene().getWindow()).close();
			}
			new MessagePage().show(result);
		}
		
		
	}
	
	public void addBelgeTuru(ActionEvent e) {
//		Birlik birlik = cmBoxBirlik.getSelectionModel().getSelectedItem();
		new BelgeTuruPage().show(null);
	}

	public void updateBelgeTuru(ActionEvent e) {		
		BelgeTuru belgeTuru = cmBoxBelgeTuru.getSelectionModel().getSelectedItem();
		if(belgeTuru == null) {
			new MessagePage().show(new Result(false, "Bir Belge Türü Seçiniz."));
			return;
		}
		Result result = belgeTuruService.getById(belgeTuru.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Belge Türü  Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		
		new BelgeTuruPage().show(belgeTuru);
	}
	
	public void addPersonel(ActionEvent e) {
//		Birlik birlik = cmBoxBirlik.getSelectionModel().getSelectedItem();
		new PersonelPage().show(null);
	}

	public void updatePersonel(ActionEvent e) {		
		Personel personel = cmBoxPersonel.getSelectionModel().getSelectedItem();
		if(personel == null) {
			new MessagePage().show(new Result(false, "Bir Personel Seçiniz."));
			return;
		}
		Result result = personelService.getById(personel.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Personel Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		
		new PersonelPage().show(personel);
	}
	
	public void selectFile() {
		String path = fileOperations.getChooseFilePath("Dosyalar");
		
		try {
			if(path == null) {
				//new MessagePage().show(new ErrorResult("Veriler Aktarılamadı."));
				return;
			}
			belgeFile = new File(path);
			txtFileName.setText(belgeFile.getName());
		}
		catch(Exception ex) {
			new MessagePage().show(new ErrorResult(ex.getMessage()));
		}
	}
	
	public void openFile() {
		if(this.belge != null && this.belge.getFile() != null)
			DesktopApplication.hostServices.showDocument(this.belge.getFile().getPath());
		else
			new MessagePage().show(new ErrorResult("Belgeye Ait Dosya Bulunmamaktadır."));
	}
}
