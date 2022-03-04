package desktopApp.ui.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.SinavService;
import akdmEtkinlikEnvanter.business.abstracts.SinavSonucService;
import akdmEtkinlikEnvanter.business.abstracts.SinavTuruService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Rutbe;
import akdmEtkinlikEnvanter.entities.concretes.Sinav;
import akdmEtkinlikEnvanter.entities.concretes.SinavTuru;
import desktopApp.ui.pages.MessagePage;
import desktopApp.ui.pages.SinavPage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

@Component
public class SinavListController {

	@Autowired
	private SinavService sinavService;
	@Autowired
	private SinavSonucService sinavSonucService;
	@Autowired
	private SinavTuruService sinavTuruService;
	
	@FXML
	private TableView<Sinav> tblSinav;
	
	@FXML
	private TableColumn<Sinav, SinavTuru> tblColumnTuru;
	
	@FXML
	private TableColumn<Sinav, LocalDate> tblColumnTarihi;

	@FXML
	private ComboBox<SinavTuru> cmBoxSearchSinavTuru;
	
	@FXML
	private DatePicker datePickerSearchFrom, datePickerSearchTo;
	
	@FXML
	Button buttonAdd, buttonUpdate, buttonDelete;
	
	private List<Sinav> sinavlar;
	
	@FXML
	private void initialize() {
		setColumnInitialSettings();
		fillSearchField();
	}
	
	public void refrashPage() {
		datePickerSearchFrom.setValue(null);
		datePickerSearchTo.setValue(null);
		
		cmBoxSearchSinavTuru.getItems().clear();
		fillSearchField();
		fillTblSinav();
	}
	
	public void setSinavlar(List<Sinav> sinavlar) {
		this.sinavlar = sinavlar;
		if(sinavlar != null)
			tblSinav.getItems().addAll(sinavlar);
	}
	
	public void fillSearchField() {
		cmBoxSearchSinavTuru.getItems().addAll(
					sinavTuruService.getAll().getData()
				);
	}
	
	public void fillTblSinav() {
		String sinavTuruAd = "";
		if(cmBoxSearchSinavTuru.getValue() != null)
			sinavTuruAd = cmBoxSearchSinavTuru.getValue().getAd();
		List<Sinav> sinavlar = sinavService.findByTarihBetweenAndSinavTuru_AdContainingIgnoreCase(
				datePickerSearchFrom.getValue(), datePickerSearchTo.getValue(),
				sinavTuruAd).getData();
		
		ObservableList<Sinav> sinavObservableList = FXCollections.observableArrayList(
				sinavlar
				);
		
		tblSinav.setItems(sinavObservableList);
	}
	
	public void addSinav() {
		new SinavPage().show(null);
	}
	
	public void updateSinav() {
		Sinav sinav = tblSinav.getSelectionModel().getSelectedItem();
		if(sinav == null) {
			new MessagePage().show(new ErrorResult("Bir Sınav Seçiniz!!!"));
			return;
		}
		Result result = sinavService.getById(sinav.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Sınav  Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		
		
		new SinavPage().show(sinav);
	}
	
	public void deleteSinav() {
		Sinav sinav = tblSinav.getSelectionModel().getSelectedItem();
		if(sinav == null) {
			new MessagePage().show(new Result(false, "Sınav Bulunamadı."));
			return;
		}
		Result result = sinavService.getById(sinav.getId());
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

			alert1.setContentText(sinavSonucService.getBySinav(sinav).getData().toString());
			alert1.setHeaderText("Bu Sınav Silinirse Aşağıdaki Sınav Sonuçlarının Tümü de Silinecektir.!!!!!!!!!");
			if(alert1.showAndWait().get() == ButtonType.OK) {
				result = sinavService.delete(sinav);
				
				if(result.isSuccess()) {
					tblSinav.getItems().remove(sinav);
//					buttonAdd.setDisable(true);
//					buttonUpdate.setDisable(true);
//					buttonDelete.setDisable(true);
//					((Stage) buttonDelete.getScene().getWindow()).close();
				}
				new MessagePage().show(result);
			}	
		}
	}
	
	private void setColumnInitialSettings() {
		//tblColumnTc.setCellValueFactory(cellData -> cellData.getValue().getTcNo()) ;
		tblColumnTuru.setCellValueFactory(new PropertyValueFactory<Sinav,SinavTuru>("sinavTuru"));
		tblColumnTarihi.setCellValueFactory(new PropertyValueFactory<Sinav,LocalDate>("tarih"));
				
	}
}
