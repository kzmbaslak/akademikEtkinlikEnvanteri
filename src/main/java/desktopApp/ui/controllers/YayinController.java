package desktopApp.ui.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.BelgeService;
import akdmEtkinlikEnvanter.business.abstracts.BirlikService;
import akdmEtkinlikEnvanter.business.abstracts.PersonelService;
import akdmEtkinlikEnvanter.business.abstracts.YayinService;
import akdmEtkinlikEnvanter.business.abstracts.YayineviDergiService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Belge;
import akdmEtkinlikEnvanter.entities.concretes.BelgeTuru;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Tez;
import akdmEtkinlikEnvanter.entities.concretes.Yayin;
import akdmEtkinlikEnvanter.entities.concretes.YayineviDergi;
import desktopApp.ui.DesktopApplication;
import desktopApp.ui.pages.MessagePage;
import desktopApp.ui.pages.PersonelPage;
import desktopApp.ui.pages.TezPage;
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
public class YayinController {

	@Autowired
	private YayinService yayinService;
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

	File yayinFile;
	
	private Yayin yayin;
	
	@FXML
	private void initialize() {
		yayinFile = null;
//		fillPersonelOther();
		fillYayineviDergi();
		
	}

	public void setYayin(Yayin yayin) {
		this.yayin = yayin;
		if(yayin != null) {
			fillYayin();
			
		}
		fillPersonel();
		
	}

	public void refrashPage() {
		txtFieldAd.clear();
		txtFileName.setText("");
		
		listPersonel.getItems().clear();
		listPersonelOther.getItems().clear();
		datePickerYayinTarih.setValue(null);
		yayinFile = null;
		
		if(this.yayin != null) {
			DataResult<Yayin> result = yayinService.getById(this.yayin.getId());
			if(result.isSuccess()) {
				yayin = result.getData();
				if(yayin != null) {
					fillYayin();
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
		if(cmBoxYayineviDergi.getSelectionModel().getSelectedItem() == null && yayin != null)
			cmBoxYayineviDergi.getSelectionModel().select(yayin.getYayineviDergi());
		cmBoxYayineviDergi.setItems(yayineviDergiler);
	}


	private void fillYayin() {
		if(this.yayin != null) {
			txtFieldAd.setText(yayin.getAd());
			txtFileName.setText(yayin.getFile()+"");
			datePickerYayinTarih.setValue(yayin.getYayinTarihi());
			this.yayinFile = yayin.getFile();
		}
	}
	
	public void addYayin() {
		DataResult<Yayin> newYayin = yayinService.add(new Yayin(0,txtFieldAd.getText(),
				datePickerYayinTarih.getValue(),listPersonel.getItems(),cmBoxYayineviDergi.getValue(),this.yayinFile));
		if(newYayin.isSuccess()) {
			this.yayin = newYayin.getData();
		}
		new MessagePage().show(newYayin);
	}
	
	public void updateYayin() {
		if (this.yayin == null){
			new MessagePage().show(new ErrorResult("Yayın Mevcut Değildir."));
			return;
		}
		this.yayin.setAd(txtFieldAd.getText());
		this.yayin.setYayinTarihi(datePickerYayinTarih.getValue());
		this.yayin.setYayineviDergi(cmBoxYayineviDergi.getValue());
		this.yayin.setYazarlar(listPersonel.getItems());
		this.yayin.setFile(this.yayinFile);
		
		DataResult<Yayin> result = yayinService.save(this.yayin);
		new MessagePage().show(result);
	}
	
	public void deleteYayin() {
		
		if(this.yayin == null) {
			new MessagePage().show(new Result(false, "Yayın Bulunamadı."));
			return;
		}
//		Result result = belgeService.getById(this.belge.getId());
//		if(!result.isSuccess()) {
//			new MessagePage().show(new ErrorResult("Belge Bulunamadı. Sayfayı Yenileyiniz."));
//			return;
//		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Yayın Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			Result result = yayinService.delete(this.yayin);
			if(result.isSuccess()) {
				this.yayin = null;
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
		if(yayin != null) {
			int selectedPersonel = listPersonel.getSelectionModel().getSelectedIndex();
			List<Personel> personeller = personelService.getByYayinlar_IdOrderByAdAsc(yayin.getId()).getData();
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
		if(yayin != null && personellerId.size() != 0) {
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
			yayinFile = new File(path);
			txtFileName.setText(yayinFile.getName());
		}
		catch(Exception ex) {
			new MessagePage().show(new ErrorResult(ex.getMessage()));
		}
	}
	
	public void openFile() {
		if(this.yayin != null && this.yayin.getFile() != null)
			DesktopApplication.hostServices.showDocument(this.yayin.getFile().getPath());
		else
			new MessagePage().show(new ErrorResult("Yayına Ait Dosya Bulunmamaktadır."));
	}
	
}
