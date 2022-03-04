package desktopApp.ui.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.KitapService;
import akdmEtkinlikEnvanter.business.abstracts.PersonelService;
import akdmEtkinlikEnvanter.business.abstracts.YayineviDergiService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Kitap;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Yayin;
import akdmEtkinlikEnvanter.entities.concretes.YayineviDergi;
import desktopApp.ui.DesktopApplication;
import desktopApp.ui.pages.MessagePage;
import desktopApp.ui.pages.PersonelPage;
import desktopApp.ui.pages.YayineviDergiPage;
import desktopApp.utilities.javaFxOperations.FileOperations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@Component
public class KitapController {

	@Autowired
	private KitapService kitapService;
	@Autowired
	private YayineviDergiService yayineviDergiService;
	@Autowired
	private PersonelService personelService;
	

	@Autowired 
	FileOperations fileOperations;
	
	@FXML
	TextField txtFieldAd;

	@FXML
	ComboBox<YayineviDergi> cmBoxYayineviDergi;
	
	@FXML
	ListView<Personel> listPersonel, listPersonelOther;
	
	@FXML
	Button buttonAdd, buttonUpdate, buttonDelete;
	
	@FXML
	DatePicker datePickerYayinTarih;
	
	@FXML
	Text txtFileName;

	File kitapFile;
	
	private Kitap kitap;
	
	@FXML
	private void initialize() {
		kitapFile = null;
		fillPersonel();
		fillYayineviDergi();
		
	}

	public void setKitap(Kitap kitap) {
		this.kitap = kitap;
		if(kitap != null) {
			fillKitap();
			fillPersonel();
		}
		
	}

	public void refrashPage() {
		txtFieldAd.clear();
		txtFileName.setText("");
		
		listPersonel.getItems().clear();
		listPersonelOther.getItems().clear();
		datePickerYayinTarih.setValue(null);
		kitapFile = null;
		
		if(this.kitap != null) {
			DataResult<Kitap> result = kitapService.getById(this.kitap.getId());
			if(result.isSuccess()) {
				kitap = result.getData();
				if(kitap != null) {
					fillKitap();
				}
			}
		}
		fillPersonel();
		fillYayineviDergi();
	}
	
	private void fillYayineviDergi() {
		ObservableList<YayineviDergi> yayineviDergiler = FXCollections.observableArrayList();
		for (YayineviDergi yayineviDergi : yayineviDergiService.getAll().getData()) {
			yayineviDergiler.add(yayineviDergi);
		}
		if(cmBoxYayineviDergi.getSelectionModel().getSelectedItem() == null && kitap != null)
			cmBoxYayineviDergi.getSelectionModel().select(kitap.getYayineviDergi());
		cmBoxYayineviDergi.setItems(yayineviDergiler);
	}


	private void fillKitap() {
		if(this.kitap != null) {
			txtFieldAd.setText(kitap.getAd());
			txtFileName.setText(kitap.getFile()+"");
			datePickerYayinTarih.setValue(kitap.getYayinTarihi());
			this.kitapFile = kitap.getFile();
		}
	}
	
	public void addKitap() {
		DataResult<Kitap> newKitap = kitapService.add(new Kitap(0,txtFieldAd.getText(),
				datePickerYayinTarih.getValue(),listPersonel.getItems(),cmBoxYayineviDergi.getValue(),this.kitapFile));
		if(newKitap.isSuccess()) {
			this.kitap = newKitap.getData();
		}
		new MessagePage().show(newKitap);
	}
	
	public void updateKitap() {
		if (this.kitap == null){
			new MessagePage().show(new ErrorResult("Kitap Mevcut Değildir."));
			return;
		}
		this.kitap.setAd(txtFieldAd.getText());
		this.kitap.setYayinTarihi(datePickerYayinTarih.getValue());
		this.kitap.setYayineviDergi(cmBoxYayineviDergi.getValue());
		this.kitap.setYazarlar(listPersonel.getItems());
		this.kitap.setFile(this.kitapFile);
		
		DataResult<Kitap> result = kitapService.save(this.kitap);
		new MessagePage().show(result);
	}
	
	public void deleteKitap() {
		
		if(this.kitap == null) {
			new MessagePage().show(new Result(false, "Kitap Bulunamadı."));
			return;
		}
//		Result result = belgeService.getById(this.belge.getId());
//		if(!result.isSuccess()) {
//			new MessagePage().show(new ErrorResult("Belge Bulunamadı. Sayfayı Yenileyiniz."));
//			return;
//		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Kitap Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			Result result = kitapService.delete(this.kitap);
			if(result.isSuccess()) {
				this.kitap = null;
//				buttonAdd.setDisable(true);
//				buttonUpdate.setDisable(true);
//				buttonDelete.setDisable(true);
				((Stage) buttonDelete.getScene().getWindow()).close();
			}
			new MessagePage().show(result);
		}
		
		
	}
	
	public void addYayineviDergi(ActionEvent e) {
//		Birlik birlik = cmBoxBirlik.getSelectionModel().getSelectedItem();
		new YayineviDergiPage().show(null);
	}

	public void updateYayineviDergi(ActionEvent e) {		
		YayineviDergi yayineviDergi = cmBoxYayineviDergi.getSelectionModel().getSelectedItem();
		if(yayineviDergi == null) {
			new MessagePage().show(new Result(false, "Bir Yayınevi Dergi Seçiniz."));
			return;
		}
		Result result = yayineviDergiService.getById(yayineviDergi.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Yayınevi Dergi Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		
		new YayineviDergiPage().show(yayineviDergi);
	}
	
	public void addPersonel(ActionEvent e) {
		new PersonelPage().show(null);
	}
	
	public void updatePersonel(ActionEvent e) {
		Personel personel = listPersonel.getSelectionModel().getSelectedItem();
		if(personel == null) {
			new MessagePage().show(new Result(false, "Bir Personel Seçiniz."));
			return;
		}
		Result result = personelService.getById(personel.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Personel  Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		
		new PersonelPage().show(personel);
	}
	
	private List<Integer> personellerId;
	public void fillPersonel() {
		personellerId = new ArrayList<>();
		if(kitap != null) {
			int selectedPersonel = listPersonel.getSelectionModel().getSelectedIndex();
			List<Personel> personeller = personelService.getByKitaplar_IdOrderByAdAsc(kitap.getId()).getData();
	//		List<Integer> kurslarId = new ArrayList<>();
			personellerId.add(0);
			
			if(listPersonel.getItems().size() == 0) {
				for (Personel personel : personeller) {
					listPersonel.getItems().add(personel);
					personellerId.add(personel.getId());
				}
			}
			listPersonel.getSelectionModel().select(selectedPersonel);
		}
		
		
		fillPersonelOther();

		
	}

	public void fillPersonelOther() {
		List<Personel> personellerOther;
		if(kitap != null) {
			personellerOther = personelService.findByIdNotInOrderByAdAsc(personellerId).getData();
		}
		else
			personellerOther = personelService.findAll().getData();
//		System.out.println("Girdi");
//		for (Kurs kurs : kurslarOther) {
//			System.out.println(kurs.getAd());
//		}
		
		listPersonelOther.getItems().clear();
		for (Personel personel : personellerOther) {
			listPersonelOther.getItems().add(personel);
			
		}
	
	}
	
	public void move2listPersonel() {
		Personel personel = listPersonelOther.getSelectionModel().getSelectedItem();
		if(personel != null)
			listPersonel.getItems().add(personel);
		
		listPersonelOther.getItems().remove(personel);
	}
	
	public void move2listPersonelOther() {
		Personel personel = listPersonel.getSelectionModel().getSelectedItem();		
		if(personel != null)
			listPersonelOther.getItems().add(personel);
		
		listPersonel.getItems().remove(personel);
	}
	
	public void selectFile() {
		String path = fileOperations.getChooseFilePath("Dosyalar");
		
		try {
			if(path == null) {
				//new MessagePage().show(new ErrorResult("Veriler Aktarılamadı."));
				return;
			}
			kitapFile = new File(path);
			txtFileName.setText(kitapFile.getName());
		}
		catch(Exception ex) {
			new MessagePage().show(new ErrorResult(ex.getMessage()));
		}
	}
	
	public void openFile() {
		if(this.kitap != null && this.kitap.getFile() != null)
			DesktopApplication.hostServices.showDocument(this.kitap.getFile().getPath());
		else
			new MessagePage().show(new ErrorResult("Kitaba Ait Dosya Bulunmamaktadır."));
	}
}
