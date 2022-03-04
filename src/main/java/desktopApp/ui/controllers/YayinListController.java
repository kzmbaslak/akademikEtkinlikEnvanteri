package desktopApp.ui.controllers;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.BelgeService;
import akdmEtkinlikEnvanter.business.abstracts.BelgeTuruService;
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
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Tez;
import akdmEtkinlikEnvanter.entities.concretes.TezTuru;
import akdmEtkinlikEnvanter.entities.concretes.UniversiteEnstitu;
import akdmEtkinlikEnvanter.entities.concretes.Yayin;
import akdmEtkinlikEnvanter.entities.concretes.YayineviDergi;
import akdmEtkinlikEnvanter.entities.concretes.YuksekOgrenim;
import desktopApp.ui.DesktopApplication;
import desktopApp.ui.pages.BelgePage;
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
public class YayinListController {
	
	private YayinService yayinService;
	private PersonelService personelService;
	private YayineviDergiService yayineviDergiService;
	
	@FXML
	private TableView<Yayin> tblYayin;
	
	@FXML
	private TableColumn<Yayin, String> tblColumnAd;

	@FXML
	private TableColumn<Yayin, File> tblColumnFile;

	@FXML
	private TableColumn<Yayin, LocalDate> tblColumnYayinTarihi;	

	@FXML
	private TableColumn<Yayin, List<Personel>> tblColumnYazar;

	@FXML
	private TableColumn<Yayin, YayineviDergi> tblColumnYayineviDergi;
	
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
	
	private List<Yayin> yayinlar;
	
	
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
		fillYayinTbl();
		
	}
	
	public void fillSearchField() {
		cmBoxSearchYazar.getItems().addAll(
					personelService.findAllByOrderByAdAsc().getData()
				);
		cmBoxSearchYayineviDergi.getItems().addAll(
					yayineviDergiService.findAllByOrderByAdAsc().getData()
				);
	}
	
	public void fillYayinTbl() {
//		tblTez.getItems().clear();
		
		String yayineviDergiAd = "";
		int personelId = 0;
		if(cmBoxSearchYazar.getValue() != null) {
			personelId = cmBoxSearchYazar.getValue().getId();
		}
		if(cmBoxSearchYayineviDergi.getValue() != null)
			yayineviDergiAd = cmBoxSearchYayineviDergi.getValue().getAd();
		List<Yayin> yayinlar = yayinService.findByYayinTarihiBetweenAndAdContainingIgnoreCaseAndYayineviDergi_adContaining
				(datePickerSearchFrom.getValue(), datePickerSearchTo.getValue(), txtFieldSearchAd.getText(),
						yayineviDergiAd, personelId).getData();
		ObservableList<Yayin> yayinObservableList = FXCollections.observableArrayList(
				yayinlar
				);
		
//		if(tezler != null)
//			tblTez.getItems().addAll(tezler);
		tblYayin.setItems(yayinObservableList);
	}
	
	

	public void setYayinlar(List<Yayin> yayinlar) {
		this.yayinlar = yayinlar;
		if(yayinlar != null)
			tblYayin.getItems().addAll(yayinlar);
	}
	
	public void addYayin(ActionEvent e) {
		new YayinPage().show(null);
	}
	
	public void updateYayin(ActionEvent e) {
		Yayin yayin = tblYayin.getSelectionModel().getSelectedItem();
		if(yayin == null) {
			new MessagePage().show(new Result(false, "Bir Yayın Seçiniz."));
			return;
		}
		Result result = yayinService.getById(yayin.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Yayın  Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		
		new YayinPage().show(yayin);
	}

	public void deleteYayin(ActionEvent e) {
		Yayin yayin = tblYayin.getSelectionModel().getSelectedItem();
		if(yayin == null) {
			new MessagePage().show(new Result(false, "Bir Yayın Seçin."));
			return;
		}
		Result result = yayinService.getById(yayin.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Yayın Bulunamadı. Sayfayı Yenileyiniz."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Yayın Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			result = yayinService.delete(yayin);
			if(result.isSuccess()) {
				tblYayin.getItems().remove(yayin);
//				buttonAdd.setDisable(true);
//				buttonUpdate.setDisable(true);
//				buttonDelete.setDisable(true);
				
//				((Stage) buttonDelete.getScene().getWindow()).close();
			}
			new MessagePage().show(result);
		}
	}

	public void openFileYayin() {
		Yayin yayin = tblYayin.getSelectionModel().getSelectedItem();
		if(yayin == null) {
			new MessagePage().show(new Result(false, "Bir Yayın Seçiniz."));
			return;
		}
		Result result = yayinService.getById(yayin.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Yayın  Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		
		if(yayin.getFile() != null && yayin.getFile().exists())
			DesktopApplication.hostServices.showDocument(yayin.getFile().getPath());
		else
			new MessagePage().show(new ErrorResult("Yayına Ait Dosya Bulunmamaktadır."));
		
	}
	
	
	private void setColumnInitialSettings() {
		tblColumnAd.setCellValueFactory(new PropertyValueFactory<Yayin,String>("ad"));
		tblColumnYayineviDergi.setCellValueFactory(new PropertyValueFactory<Yayin,YayineviDergi>("yayineviDergi"));
		tblColumnYazar.setCellValueFactory(new PropertyValueFactory<Yayin,List<Personel>>("yazarlar"));
		tblColumnYayinTarihi.setCellValueFactory(new PropertyValueFactory<Yayin,LocalDate>("yayinTarihi"));
		tblColumnFile.setCellValueFactory(new PropertyValueFactory<Yayin,File>("file"));
	}
	
	@Autowired
	public YayinListController(YayinService yayinService, PersonelService personelService, BelgeTuruService belgeTuruService,
			YuksekOgrenimService yuksekOgrenimService, YayineviDergiService yayineviDergiService) {
		super();
		this.yayinService = yayinService;
		this.personelService = personelService;
		this.yayineviDergiService = yayineviDergiService;
	}
}
