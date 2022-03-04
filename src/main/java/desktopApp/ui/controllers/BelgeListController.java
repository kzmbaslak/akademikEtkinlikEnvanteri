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
import akdmEtkinlikEnvanter.entities.concretes.YayineviDergi;
import akdmEtkinlikEnvanter.entities.concretes.YuksekOgrenim;
import desktopApp.ui.DesktopApplication;
import desktopApp.ui.pages.BelgePage;
import desktopApp.ui.pages.MessagePage;
import desktopApp.ui.pages.TezPage;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

@Component
public class BelgeListController {
	
	private BelgeService belgeService;
	private PersonelService personelService;
	private BelgeTuruService belgeTuruService;
	private YayineviDergiService yayineviDergiService;
	
	@FXML
	private TableView<Belge> tblBelge;
	
	@FXML
	private TableColumn<Belge, String> tblColumnAd;

	@FXML
	private TableColumn<Belge, BelgeTuru> tblColumnBelgeTuru;

	@FXML
	private TableColumn<Belge, LocalDate> tblColumnYayinTarihi;	

	@FXML
	private TableColumn<Belge, Personel> tblColumnPersonel;

	@FXML
	private TableColumn<Belge, File> tblColumnFile;
	
	@FXML
	private TextField txtFieldSearchAd;
	
	@FXML
	private ComboBox<BelgeTuru> cmBoxSearchBelgeTuru;

	@FXML
	private ComboBox<Personel> cmBoxSearchPersonel;

	@FXML
	private Button buttonAdd, buttonUpdate, buttonDelete;
	
	private List<Belge> belgeler;
	
	
	@FXML
	private void initialize() {
		setColumnInitialSettings();
		fillSearchField();
//		fillTezTbl();
	}
	
	public void refrashPage() {
		txtFieldSearchAd.clear();
		cmBoxSearchBelgeTuru.getItems().clear();
		cmBoxSearchPersonel.getItems().clear();
		
		fillSearchField();
		fillBelgeTbl();
		
	}
	
	public void fillSearchField() {
		cmBoxSearchPersonel.getItems().addAll(
					personelService.findAllByOrderByAdAsc().getData()
				);
		cmBoxSearchBelgeTuru.getItems().addAll(
				belgeTuruService.getAll().getData()
			);
	}
	
	public void fillBelgeTbl() {
//		tblTez.getItems().clear();
		
		String belgeTuruAd = "", personelSicil = "";
		if(cmBoxSearchBelgeTuru.getValue() != null)
			belgeTuruAd = cmBoxSearchBelgeTuru.getValue().getAd();
		if(cmBoxSearchPersonel.getValue() != null)
			personelSicil = cmBoxSearchPersonel.getValue().getSicil();
		
		List<Belge> belgeler = belgeService.findByAdContainingIgnoreCaseAndBelgeTuru_adContainingAndPersonel_sicilContaining
				(txtFieldSearchAd.getText(), belgeTuruAd, personelSicil).getData();
		
		ObservableList<Belge> belgeObservableList = FXCollections.observableArrayList(
				belgeler
				);
		
//		if(tezler != null)
//			tblTez.getItems().addAll(tezler);
		tblBelge.setItems(belgeObservableList);
	}
	
	

	public void setBelgeler(List<Belge> belgeler) {
		this.belgeler = belgeler;
		if(belgeler != null)
			tblBelge.getItems().addAll(belgeler);
	}
	
	public void addBelge(ActionEvent e) {
		new BelgePage().show(null);
	}
	
	public void updateBelge(ActionEvent e) {
		Belge belge = tblBelge.getSelectionModel().getSelectedItem();
		if(belge == null) {
			new MessagePage().show(new Result(false, "Bir Belge Seçiniz."));
			return;
		}
		Result result = belgeService.getById(belge.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Belge  Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		
		new BelgePage().show(belge);
	}

	public void deleteBelge(ActionEvent e) {
		Belge belge = tblBelge.getSelectionModel().getSelectedItem();
		if(belge == null) {
			new MessagePage().show(new Result(false, "Bir Belge Seçin."));
			return;
		}
		Result result = belgeService.getById(belge.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Belge Bulunamadı. Sayfayı Yenileyiniz."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Belge Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			result = belgeService.delete(belge);
			if(result.isSuccess()) {
				tblBelge.getItems().remove(belge);
//				buttonAdd.setDisable(true);
//				buttonUpdate.setDisable(true);
//				buttonDelete.setDisable(true);
				
//				((Stage) buttonDelete.getScene().getWindow()).close();
			}
			new MessagePage().show(result);
		}
	}

	public void openFileBelge() {
		Belge belge = tblBelge.getSelectionModel().getSelectedItem();
		if(belge == null) {
			new MessagePage().show(new Result(false, "Bir Belge Seçiniz."));
			return;
		}
		Result result = belgeService.getById(belge.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Belge  Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		
		if(belge.getFile() != null && belge.getFile().exists())
			DesktopApplication.hostServices.showDocument(belge.getFile().getPath());
		else
			new MessagePage().show(new ErrorResult("Belgeye Ait Dosya Bulunmamaktadır."));
		
	}
	
	private void setColumnInitialSettings() {
		//tblColumnTc.setCellValueFactory(cellData -> cellData.getValue().getTcNo()) ;
		tblColumnAd.setCellValueFactory(new PropertyValueFactory<Belge,String>("ad"));
		tblColumnBelgeTuru.setCellValueFactory(new PropertyValueFactory<Belge,BelgeTuru>("belgeTuru"));
		tblColumnPersonel.setCellValueFactory(new PropertyValueFactory<Belge,Personel>("personel"));
		tblColumnFile.setCellValueFactory(new PropertyValueFactory<Belge,File>("file"));
				
	}
	
	@Autowired
	public BelgeListController(BelgeService belgeService, PersonelService personelService, BelgeTuruService belgeTuruService,
			YuksekOgrenimService yuksekOgrenimService, YayineviDergiService yayineviDergiService) {
		super();
		this.belgeService = belgeService;
		this.personelService = personelService;
		this.belgeTuruService = belgeTuruService;
		this.yayineviDergiService = yayineviDergiService;
	}
}
