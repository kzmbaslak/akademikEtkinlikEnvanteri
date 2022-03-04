package desktopApp.ui.controllers;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.BelgeService;
import akdmEtkinlikEnvanter.business.abstracts.BelgeTuruService;
import akdmEtkinlikEnvanter.business.abstracts.KitapService;
import akdmEtkinlikEnvanter.business.abstracts.PersonelService;
import akdmEtkinlikEnvanter.business.abstracts.TezService;
import akdmEtkinlikEnvanter.business.abstracts.TezTuruService;
import akdmEtkinlikEnvanter.business.abstracts.UniversiteEnstituService;
import akdmEtkinlikEnvanter.business.abstracts.YayinService;
import akdmEtkinlikEnvanter.business.abstracts.YayineviDergiService;
import akdmEtkinlikEnvanter.business.abstracts.YuksekOgrenimService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.dataAccess.abstracts.TezDao;
import akdmEtkinlikEnvanter.entities.concretes.Belge;
import akdmEtkinlikEnvanter.entities.concretes.BelgeTuru;
import akdmEtkinlikEnvanter.entities.concretes.Kitap;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Tez;
import akdmEtkinlikEnvanter.entities.concretes.TezTuru;
import akdmEtkinlikEnvanter.entities.concretes.UniversiteEnstitu;
import akdmEtkinlikEnvanter.entities.concretes.Yayin;
import akdmEtkinlikEnvanter.entities.concretes.YayineviDergi;
import akdmEtkinlikEnvanter.entities.concretes.YuksekOgrenim;
import desktopApp.ui.DesktopApplication;
import desktopApp.ui.pages.BelgePage;
import desktopApp.ui.pages.KitapPage;
import desktopApp.ui.pages.MessagePage;
import desktopApp.ui.pages.TezPage;
import desktopApp.ui.pages.YayinPage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

@Component
public class KitapListController {
	
	private KitapService kitapService;
	private PersonelService personelService;
	private YayineviDergiService yayineviDergiService;
	
	@FXML
	private TableView<Kitap> tblKitap;
	
	@FXML
	private TableColumn<Kitap, String> tblColumnAd;

	@FXML
	private TableColumn<Kitap, File> tblColumnFile;

	@FXML
	private TableColumn<Kitap, LocalDate> tblColumnYayinTarihi;	

	@FXML
	private TableColumn<Kitap, List<Personel>> tblColumnYazar;

	@FXML
	private TableColumn<Kitap, YayineviDergi> tblColumnYayineviDergi;
	
	@FXML
	private TextField txtFieldSearchAd;

	@FXML
	private ComboBox<Personel> cmBoxSearchYazar;

	@FXML
	private ComboBox<YayineviDergi> cmBoxSearchYayineviDergi;
	
	@FXML
	private DatePicker datePickerSearchFrom, datePickerSearchTo;

	@FXML
	private Button buttonAdd, buttonUpdate, buttonDelete;
	
	private List<Kitap> kitaplar;
	
	
	@FXML
	private void initialize() {
		setColumnInitialSettings();
		fillSearchField();
//		fillTezTbl();
	}
	
	public void refrashPage() {
		txtFieldSearchAd.clear();
		datePickerSearchFrom.setValue(null);
		datePickerSearchTo.setValue(null);
		cmBoxSearchYazar.getItems().clear();
		cmBoxSearchYayineviDergi.getItems().clear();
		fillSearchField();
		fillKitapTbl();
		
	}
	
	public void fillSearchField() {
		cmBoxSearchYazar.getItems().addAll(
					personelService.findAllByOrderByAdAsc().getData()
				);
		cmBoxSearchYayineviDergi.getItems().addAll(
					yayineviDergiService.findAllByOrderByAdAsc().getData()
				);
	}
	
	public void fillKitapTbl() {
//		tblTez.getItems().clear();
		
		String yayineviDergiAd = "";
		int personelId = 0;
		if(cmBoxSearchYazar.getValue() != null) {
			personelId = cmBoxSearchYazar.getValue().getId();
		}
		if(cmBoxSearchYayineviDergi.getValue() != null)
			yayineviDergiAd = cmBoxSearchYayineviDergi.getValue().getAd();
		List<Kitap> kitaplar = kitapService.findByYayinTarihiBetweenAndAdContainingIgnoreCaseAndYayineviDergi_adContaining
				(datePickerSearchFrom.getValue(), datePickerSearchTo.getValue(), txtFieldSearchAd.getText(),
						yayineviDergiAd, personelId).getData();
		
		ObservableList<Kitap> kitapObservableList = FXCollections.observableArrayList(
				kitaplar
				);
		
//		if(tezler != null)
//			tblTez.getItems().addAll(tezler);
		tblKitap.setItems(kitapObservableList);
	}
	
	

	public void setKitaplar(List<Kitap> kitaplar) {
		this.kitaplar = kitaplar;
		if(kitaplar != null)
			tblKitap.getItems().addAll(kitaplar);
	}
	
	public void addKitap(ActionEvent e) {
		new KitapPage().show(null);
	}
	
	public void updateKitap(ActionEvent e) {
		Kitap kitap = tblKitap.getSelectionModel().getSelectedItem();
		if(kitap == null) {
			new MessagePage().show(new Result(false, "Bir Kitap Seçiniz."));
			return;
		}
		Result result = kitapService.getById(kitap.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Kitap  Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		
		new KitapPage().show(kitap);
	}

	public void deleteKitap(ActionEvent e) {
		Kitap kitap = tblKitap.getSelectionModel().getSelectedItem();
		if(kitap == null) {
			new MessagePage().show(new Result(false, "Bir Kitap Seçin."));
			return;
		}
		Result result = kitapService.getById(kitap.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Kitap Bulunamadı. Sayfayı Yenileyiniz."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Kitap Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			result = kitapService.delete(kitap);
			if(result.isSuccess()) {
				tblKitap.getItems().remove(kitap);
//				buttonAdd.setDisable(true);
//				buttonUpdate.setDisable(true);
//				buttonDelete.setDisable(true);
				
//				((Stage) buttonDelete.getScene().getWindow()).close();
			}
			new MessagePage().show(result);
		}
	}
	
	public void openFileKitap() {
		Kitap kitap = tblKitap.getSelectionModel().getSelectedItem();
		if(kitap == null) {
			new MessagePage().show(new Result(false, "Bir Kitap Seçiniz."));
			return;
		}
		Result result = kitapService.getById(kitap.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Kitap  Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		
		if(kitap.getFile() != null && kitap.getFile().exists())
			DesktopApplication.hostServices.showDocument(kitap.getFile().getPath());
		else
			new MessagePage().show(new ErrorResult("Kitaba Ait Dosya Bulunmamaktadır."));
		
	}
	
	private void setColumnInitialSettings() {
		tblColumnAd.setCellValueFactory(new PropertyValueFactory<Kitap,String>("ad"));
		tblColumnYayineviDergi.setCellValueFactory(new PropertyValueFactory<Kitap,YayineviDergi>("yayineviDergi"));
		tblColumnYazar.setCellValueFactory(new PropertyValueFactory<Kitap,List<Personel>>("yazarlar"));
		tblColumnYayinTarihi.setCellValueFactory(new PropertyValueFactory<Kitap,LocalDate>("yayinTarihi"));
		tblColumnFile.setCellValueFactory(new PropertyValueFactory<Kitap,File>("file"));
	}
	
	@Autowired
	public KitapListController(KitapService kitapService, PersonelService personelService, BelgeTuruService belgeTuruService,
			YuksekOgrenimService yuksekOgrenimService, YayineviDergiService yayineviDergiService) {
		super();
		this.kitapService = kitapService;
		this.personelService = personelService;
		this.yayineviDergiService = yayineviDergiService;
	}
}
