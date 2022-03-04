package desktopApp.ui.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.PersonelService;
import akdmEtkinlikEnvanter.business.abstracts.TezService;
import akdmEtkinlikEnvanter.business.abstracts.TezTuruService;
import akdmEtkinlikEnvanter.business.abstracts.UniversiteEnstituService;
import akdmEtkinlikEnvanter.business.abstracts.YuksekOgrenimService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.dataAccess.abstracts.TezDao;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Tez;
import akdmEtkinlikEnvanter.entities.concretes.TezTuru;
import akdmEtkinlikEnvanter.entities.concretes.UniversiteEnstitu;
import akdmEtkinlikEnvanter.entities.concretes.YuksekOgrenim;
import desktopApp.ui.DesktopApplication;
import desktopApp.ui.pages.MessagePage;
import desktopApp.ui.pages.TezPage;
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
public class TezListController {
	
	private TezService tezService;
	private PersonelService personelService;
	private TezTuruService tezTuruService;
	private YuksekOgrenimService yuksekOgrenimService;
	private UniversiteEnstituService universiteEnstituService;
	
	@FXML
	private TableView<Tez> tblTez;
	
	@FXML
	private TableColumn<Tez, String> tblColumnTezKonusu;

	@FXML
	private TableColumn<Tez, TezTuru> tblColumnTezTuru;

	@FXML
	private TableColumn<Tez, LocalDate> tblColumnBitirdigiTarih;
	
	@FXML
	private TableColumn<Tez, YuksekOgrenim> tblColumnYuksekOgrenim;
	
	@FXML
	private TableColumn<Tez, UniversiteEnstitu> tblColumnUniversiteEnstitu;

	@FXML
	private TableColumn<Tez, Personel> tblColumnPersonel;
	
	@FXML
	private BarChart<String, Integer> barChartTez;
	
	@FXML
	private TextField txtFieldSearchTezKonusu;
	
	@FXML
	private ComboBox<TezTuru> cmBoxSearchTezTuru;
	
	@FXML
	private ComboBox<YuksekOgrenim> cmBoxSearchYuksekOgrenim;

	@FXML
	private ComboBox<UniversiteEnstitu> cmBoxSearchUniEnst;

	@FXML
	private ComboBox<Personel> cmBoxSearchPersonel;
	
	@FXML
	private DatePicker datePickerSearchFrom, datePickerSearchTo;

	@FXML
	private Button buttonAdd, buttonUpdate, buttonDelete;
	
	private List<Tez> tezler;
	
	
	@FXML
	private void initialize() {
		setColumnInitialSettings();
		fillTezGraph();
		fillSearchField();
//		fillTezTbl();
	}
	
	public void refrashPage() {
		txtFieldSearchTezKonusu.clear();
		datePickerSearchFrom.setValue(null);
		datePickerSearchTo.setValue(null);
		cmBoxSearchTezTuru.getItems().clear();
		cmBoxSearchPersonel.getItems().clear();
		cmBoxSearchUniEnst.getItems().clear();
		cmBoxSearchYuksekOgrenim.getItems().clear();
		
		fillSearchField();
		fillTezTbl();
		
	}
	
	public void fillSearchField() {
		cmBoxSearchPersonel.getItems().addAll(
					personelService.findAllByOrderByAdAsc().getData()
				);
		cmBoxSearchTezTuru.getItems().addAll(
				tezTuruService.getAll().getData()
			);
		cmBoxSearchUniEnst.getItems().addAll(
				universiteEnstituService.getAllByOrderByAdAsc().getData()
			);
		cmBoxSearchYuksekOgrenim.getItems().addAll(
				yuksekOgrenimService.getAllByOrderByAlanAdiAsc().getData()
			);
	}
	
	public void fillTezTbl() {
//		tblTez.getItems().clear();
		
		String tezTuruAd = "", yuksekOgrAlanAd = "", uniEnstAd = "", personelSicil = "";
		if(cmBoxSearchTezTuru.getValue() != null)
			tezTuruAd = cmBoxSearchTezTuru.getValue().getAd();
		if(cmBoxSearchYuksekOgrenim.getValue() != null)
			yuksekOgrAlanAd = cmBoxSearchYuksekOgrenim.getValue().getAlanAdi();
		if(cmBoxSearchUniEnst.getValue() != null)
			uniEnstAd = cmBoxSearchUniEnst.getValue().getAd();
		if(cmBoxSearchPersonel.getValue() != null)
			personelSicil = cmBoxSearchPersonel.getValue().getSicil();
		
		List<Tez> tezler = tezService.findByBitirdigiTarihBetweenAndTezKonusuContainingIgnoreCaseAndTezTuru_adContainingIgnoreCaseAndYuksekOgrenim_alanAdiContainingIgnoreCaseAndUniversiteEnstitu_adContainingIgnoreCaseAndPersonel_sicilContainingIgnoreCase
				(datePickerSearchFrom.getValue(), datePickerSearchTo.getValue(), txtFieldSearchTezKonusu.getText(),
						tezTuruAd, yuksekOgrAlanAd, uniEnstAd, personelSicil).getData();
		
		ObservableList<Tez> tezObservableList = FXCollections.observableArrayList(
				tezler
				);
		
//		if(tezler != null)
//			tblTez.getItems().addAll(tezler);
		tblTez.setItems(tezObservableList);
	}
	
	private void fillTezGraph() {
		XYChart.Series<String, Integer> dataSeries = new XYChart.Series();
		dataSeries.setName("Tez");
		List<Integer> dates = tezService.getBitirdigiTarihGroupBy().getData();
		for (Integer year : dates) {
			if(year != null) {
				int yearCount = tezService.countByBitirdigiTarih(year).getData();
				dataSeries.getData().add(new XYChart.Data(year+"", yearCount));
			}
		}
		
		barChartTez.getData().add(dataSeries);
	}

	public void setTezler(List<Tez> tezler) {
		this.tezler = tezler;
		if(tezler != null)
			tblTez.getItems().addAll(tezler);
	}
	
	public void addTez(ActionEvent e) {
		new TezPage().show(null);
	}
	
	public void updateTez(ActionEvent e) {
		Tez tez = tblTez.getSelectionModel().getSelectedItem();
		if(tez == null) {
			new MessagePage().show(new Result(false, "Bir Tez Seçiniz."));
			return;
		}
		Result result = tezService.findById(tez.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Tez  Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		
		new TezPage().show(tez);
	}

	public void deleteTez(ActionEvent e) {
		Tez tez = tblTez.getSelectionModel().getSelectedItem();
		if(tez == null) {
			new MessagePage().show(new Result(false, "Bir Tez Seçin."));
			return;
		}
		Result result = tezService.findById(tez.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Tez Bulunamadı. Sayfayı Yenileyiniz."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Tez Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			result = tezService.delete(tez);
			if(result.isSuccess()) {
				tblTez.getItems().remove(tez);
//				buttonAdd.setDisable(true);
//				buttonUpdate.setDisable(true);
//				buttonDelete.setDisable(true);
				
//				((Stage) buttonDelete.getScene().getWindow()).close();
			}
			new MessagePage().show(result);
		}
	}
	
	public void openFileTez() {
		Tez tez = tblTez.getSelectionModel().getSelectedItem();
		if(tez == null) {
			new MessagePage().show(new Result(false, "Bir Tez Seçiniz."));
			return;
		}
		Result result = tezService.findById(tez.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Tez  Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		
		if(tez.getFile() != null && tez.getFile().exists())
			DesktopApplication.hostServices.showDocument(tez.getFile().getPath());
		else
			new MessagePage().show(new ErrorResult("Teze Ait Dosya Bulunmamaktadır."));
		
	}
	
	private void setColumnInitialSettings() {
		//tblColumnTc.setCellValueFactory(cellData -> cellData.getValue().getTcNo()) ;
		tblColumnTezKonusu.setCellValueFactory(new PropertyValueFactory<Tez,String>("tezKonusu"));
		tblColumnTezTuru.setCellValueFactory(new PropertyValueFactory<Tez,TezTuru>("tezTuru"));
		tblColumnBitirdigiTarih.setCellValueFactory(new PropertyValueFactory<Tez,LocalDate>("bitirdigiTarih"));
		tblColumnYuksekOgrenim.setCellValueFactory(new PropertyValueFactory<Tez,YuksekOgrenim>("yuksekOgrenim"));
		tblColumnUniversiteEnstitu.setCellValueFactory(new PropertyValueFactory<Tez,UniversiteEnstitu>("universiteEnstitu"));
		tblColumnPersonel.setCellValueFactory(new PropertyValueFactory<Tez,Personel>("personel"));
				
	}
	
	@Autowired
	public TezListController(TezService tezService, PersonelService personelService, TezTuruService tezTuruService,
			YuksekOgrenimService yuksekOgrenimService, UniversiteEnstituService universiteEnstituService) {
		super();
		this.tezService = tezService;
		this.personelService = personelService;
		this.tezTuruService = tezTuruService;
		this.yuksekOgrenimService = yuksekOgrenimService;
		this.universiteEnstituService = universiteEnstituService;
	}
}
