package desktopApp.ui.controllers;



import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.springframework.web.method.annotation.ErrorsMethodArgumentResolver;
//
//import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import akdmEtkinlikEnvanter.business.abstracts.PersonelService;
import akdmEtkinlikEnvanter.business.abstracts.TezService;
import akdmEtkinlikEnvanter.business.abstracts.TezTuruService;
import akdmEtkinlikEnvanter.business.abstracts.UniversiteEnstituService;
import akdmEtkinlikEnvanter.business.abstracts.YuksekOgrenimService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Tez;
import akdmEtkinlikEnvanter.entities.concretes.TezTuru;
import akdmEtkinlikEnvanter.entities.concretes.UniversiteEnstitu;
import akdmEtkinlikEnvanter.entities.concretes.YuksekOgrenim;
import desktopApp.ui.DesktopApplication;
import desktopApp.ui.pages.MessagePage;
import desktopApp.ui.pages.PersonelPage;
import desktopApp.ui.pages.UniversiteEnstituPage;
import desktopApp.ui.pages.YuksekOgrenimPage;
import desktopApp.utilities.javaFxOperations.FileOperations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import lombok.Data;

@Component
@Data
public class TezController {
	
	@Autowired
	private TezService tezService;
	@Autowired
	private UniversiteEnstituService universiteEnstituService;
	@Autowired
	private TezTuruService tezTuruService;
	@Autowired
	private YuksekOgrenimService yuksekOgrenimService;
	@Autowired
	private PersonelService personelService;
	
	@Autowired 
	FileOperations fileOperations;
	
	private Tez tez;
	
	@FXML
	private TextField txtFieldTezKonusu;
	@FXML
	Text txtFileName;
	@FXML
	private ComboBox<TezTuru> cmBoxTezTuru;
	@FXML
	private ComboBox<UniversiteEnstitu> cmBoxUniversiteEnstitu;
	@FXML
	private ComboBox<YuksekOgrenim> cmBoxYuksekOgrenim;
	@FXML
	private ComboBox<Personel> cmBoxPersonel;
	
	@FXML
	private DatePicker datePickerBitirdigiTarih;

	@FXML
	private Button buttonAdd, buttonUpdate, buttonDelete, buttonSelectFile;
	
	File tezFile;
	
	@FXML
	private void initialize() {
		tezFile = null;
		
	}
	
	public void refrashPage() {
		txtFieldTezKonusu.clear();
		datePickerBitirdigiTarih.setValue(null);
		cmBoxTezTuru.getItems().clear();
		cmBoxPersonel.getItems().clear();
		cmBoxUniversiteEnstitu.getItems().clear();
		cmBoxYuksekOgrenim.getItems().clear();
		this.tezFile = null;
		
		fillTez();
		fillTezTuru();
		fillUniversiteEnstitu();
		fillYuksekOgrenim();
		fillPersonel();
		
	}
	
	public void setTez(Tez tez) {
		this.tez = tez;
		fillTez();
		fillTezTuru();
		fillUniversiteEnstitu();
		fillYuksekOgrenim();
		fillPersonel();
		
	}
	
	private void fillTez() {
		if(tez != null) {
			txtFieldTezKonusu.setText(this.tez.getTezKonusu());
			datePickerBitirdigiTarih.setValue(tez.getBitirdigiTarih());
			txtFileName.setText(tez.getFile()+"");
			this.tezFile = tez.getFile();
		}
		
	}

	private void fillTezTuru() {
		ObservableList<TezTuru> tezTurleri = FXCollections.observableArrayList();
		for (TezTuru tezTuru : tezTuruService.getAll().getData()) {
			tezTurleri.add(tezTuru);
		}
		if(cmBoxTezTuru.getSelectionModel().getSelectedItem() == null && tez != null)
			cmBoxTezTuru.getSelectionModel().select(tez.getTezTuru());
		cmBoxTezTuru.setItems(tezTurleri);
		
		
	}
	
	public void fillUniversiteEnstitu() {
		ObservableList<UniversiteEnstitu> universiteEnstihtuler =  FXCollections.observableArrayList();
		for (UniversiteEnstitu universiteEnstitu : universiteEnstituService.getAll().getData()) {
			universiteEnstihtuler.add(universiteEnstitu);
		}
		if(cmBoxUniversiteEnstitu.getSelectionModel().getSelectedItem() == null && tez != null)
			cmBoxUniversiteEnstitu.getSelectionModel().select(tez.getUniversiteEnstitu());
		cmBoxUniversiteEnstitu.setItems(universiteEnstihtuler);
		
	}
	
	public void fillYuksekOgrenim() {
		ObservableList<YuksekOgrenim> yuksekOgrenimler = FXCollections.observableArrayList();
		for (YuksekOgrenim yuksekOgrenim : yuksekOgrenimService.getAll().getData()) {
			yuksekOgrenimler.add(yuksekOgrenim);
		}
		if(cmBoxYuksekOgrenim.getSelectionModel().getSelectedItem() == null && tez != null)
			cmBoxYuksekOgrenim.getSelectionModel().select(tez.getYuksekOgrenim());
		cmBoxYuksekOgrenim.setItems(yuksekOgrenimler);
	}
	
	public void fillPersonel() {
		ObservableList<Personel> personeller = FXCollections.observableArrayList();
		for (Personel personel : personelService.findAllByOrderByAdAsc().getData()) {
			personeller.add(personel);
		}
		if(cmBoxPersonel.getSelectionModel().getSelectedItem() == null && tez != null)
			cmBoxPersonel.getSelectionModel().select(tez.getPersonel());
		cmBoxPersonel.setItems(personeller);
	}
	
	public void addTez(ActionEvent e) {
		DataResult<Tez> newTez = tezService.add(new Tez(0, txtFieldTezKonusu.getText()
				, cmBoxTezTuru.getValue(), datePickerBitirdigiTarih.getValue(),
				cmBoxYuksekOgrenim.getValue(), cmBoxUniversiteEnstitu.getValue(),
				cmBoxPersonel.getValue(),tezFile));
		if(tezFile != null)
			this.tez.setFile(this.tezFile);
		if(newTez.isSuccess()) {
			this.tez = newTez.getData();
		}
		new MessagePage().show(newTez);
	}
	
	public void updateTez(ActionEvent e) {
		if (this.tez == null){
			new MessagePage().show(new ErrorResult("Tez Mevcut Değildir."));
			return;
		}
		this.tez.setTezKonusu(txtFieldTezKonusu.getText());
		this.tez.setTezTuru(cmBoxTezTuru.getValue());
		this.tez.setUniversiteEnstitu(cmBoxUniversiteEnstitu.getValue());
		this.tez.setYuksekOgrenim(cmBoxYuksekOgrenim.getValue());
		this.tez.setBitirdigiTarih(datePickerBitirdigiTarih.getValue());
		this.tez.setPersonel(cmBoxPersonel.getValue());
		this.tez.setFile(this.tezFile);
		Result result;
		try {
			result = tezService.save(tez);
		}
		catch(Exception ex) {
			new MessagePage().show(new ErrorResult("Seçmiş Olduğunuz Veriler Silinmiş Olabilir. Sayfayı Yenileyiniz."));
			ex.printStackTrace();
			return;
		}
		new MessagePage().show(result);
	}

	public void deleteTez(ActionEvent e) {
		if(this.tez == null) {
			new MessagePage().show(new Result(false, "Tez Bulunamadı."));
			return;
		}
		Result result = tezService.findById(this.tez.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Tez Bulunamadı. Sayfayı Yenileyiniz."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Tez Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			result = tezService.delete(this.tez);
			if(result.isSuccess()) {
				this.tez = null;
//				buttonAdd.setDisable(true);
//				buttonUpdate.setDisable(true);
//				buttonDelete.setDisable(true);
				
				((Stage) buttonDelete.getScene().getWindow()).close();
			}
			new MessagePage().show(result);
		}
	}
	
	public void addUniversiteEnstitu(ActionEvent e) {
		new UniversiteEnstituPage().show(null);
	}
	

	public void updateUniversiteEnstitu(ActionEvent e) {
		UniversiteEnstitu universiteEnstitu = cmBoxUniversiteEnstitu.getSelectionModel().getSelectedItem();
		
		if(universiteEnstitu == null) {
			new MessagePage().show(new ErrorResult("Bir Üniversite Enstitü Seçiniz!!!"));
			return;
		}
		Result result = universiteEnstituService.getById(universiteEnstitu.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Üniversite Enstitü  Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
			
		
		new UniversiteEnstituPage().show(universiteEnstitu);
	}
	
//	public void deleteUniversiteEnstitu() {
//		UniversiteEnstitu universiteEnstitu = cmBoxUniversiteEnstitu.getValue();
//		
//		Alert alert = new Alert(AlertType.CONFIRMATION);
//		alert.setTitle("Üniversite/Enstitü Sil");
//		alert.setContentText("Silmek istediğinizden emin misiniz?");
//		alert.setHeaderText("Sil");
//		
//		if(alert.showAndWait().get() == ButtonType.OK) {
//			Alert alert1 = new Alert(AlertType.CONFIRMATION);
//			alert1.setTitle("Üniversite/Enstitü Sil");
//
//			alert1.setContentText(tezService.findByUniversiteEnstitu(universiteEnstitu).getData().toString());
//			alert1.setHeaderText("Bu Yüksek Öğrenim Silinirse Aşağıdaki Tezlerin Tümü de Silinecektir.!!!!!!!!!");
//			if(alert1.showAndWait().get() == ButtonType.OK) {
//				Result result = universiteEnstituService.delete(universiteEnstitu);
//				buttonDeleteUniEnst.setVisible(false);
//				new MessagePage().show(result);
//			}
//		}
//	}
	
	public void addYuksekOgrenim(ActionEvent e) {
		new YuksekOgrenimPage().show(null);
	}
	
	public void updateYuksekOgrenim(ActionEvent e) {
		YuksekOgrenim yuksekOgrenim = cmBoxYuksekOgrenim.getSelectionModel().getSelectedItem();
		
		if(yuksekOgrenim == null) {
			new MessagePage().show(new ErrorResult("Bir Yüksek Öğrenim Enstitü Seçiniz!!!"));
			return;
		}
		Result result = yuksekOgrenimService.getById(yuksekOgrenim.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Yüksek Öğrenim  Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		
		new YuksekOgrenimPage().show(yuksekOgrenim);
	}
	
//	public void deleteYuksekOgrenim() {
//		YuksekOgrenim yuksekOgrenim = cmBoxYuksekOgrenim.getValue();
//		
//		Alert alert = new Alert(AlertType.CONFIRMATION);
//		alert.setTitle("Yüksek Öğrenim Sil");
//		alert.setContentText("Silmek istediğinizden emin misiniz?");
//		alert.setHeaderText("Sil");
//		
//		if(alert.showAndWait().get() == ButtonType.OK) {
//			Alert alert1 = new Alert(AlertType.CONFIRMATION);
//			alert1.setTitle("Yüksek Öğrenim Sil");
//
//			alert1.setContentText(tezService.findByYuksekOgrenim(yuksekOgrenim).getData().toString());
//			alert1.setHeaderText("Bu Yüksek Öğrenim Silinirse Aşağıdaki Tezlerin Tümü de Silinecektir.!!!!!!!!!");
//			if(alert1.showAndWait().get() == ButtonType.OK) {
//				Result result = yuksekOgrenimService.delete(yuksekOgrenim);
//				buttonDeleteYukOgr.setVisible(false);
//				new MessagePage().show(result);
//			}
//		}
//	}
	
	public void addPersonel(ActionEvent e) {
		new PersonelPage().show(null);
	}
	
	public void selectFile() {
		String path = fileOperations.getChooseFilePath("Dosyalar");
		
		try {
			if(path == null) {
				//new MessagePage().show(new ErrorResult("Veriler Aktarılamadı."));
				return;
			}
			tezFile = new File(path);
			txtFileName.setText(tezFile.getName());
		}
		catch(Exception ex) {
			new MessagePage().show(new ErrorResult(ex.getMessage()));
		}
	}
	
	public void openFile() {
		if(this.tez != null && this.tez.getFile() != null)
			DesktopApplication.hostServices.showDocument(this.tez.getFile().getPath());
		else
			new MessagePage().show(new ErrorResult("Teze Ait Dosya Bulunmamaktadır."));
	}
	
}
