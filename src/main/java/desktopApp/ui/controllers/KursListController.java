package desktopApp.ui.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.KursService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import desktopApp.ui.pages.KursPage;
import desktopApp.ui.pages.MessagePage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

@Component
public class KursListController {
	
	@Autowired
	private KursService kursService;
	
	private List<Kurs> kurslar;

	@FXML
	private TableView<Kurs> tblKurs;
	
	@FXML
	private TableColumn<Kurs, String> tblColumnAd;

	@FXML
	Button buttonAdd, buttonUpdate, buttonDelete;
	
	@FXML
	TextField txtFieldSearchKursAd;
	
	@FXML
	private void initialize() {
		setColumnInitialSettings();
	}
	
	public void refrashPage() {
		txtFieldSearchKursAd.clear();
		fillTblKurs();
	}
	
	public void setKurslar(List<Kurs> kurslar) {
		this.kurslar = kurslar;
		if(kurslar != null)
			fillTblKurs();
	}
	
	public void fillTblKurs() {
		List<Kurs> kurslar = kursService.findByAdContainingIgnoreCase(txtFieldSearchKursAd.getText()).getData();
		
		ObservableList<Kurs> kursObservableList = FXCollections.observableArrayList(
				kurslar
				);
		
		tblKurs.setItems(kursObservableList);
	}
	
	public void addKurs() {
		new KursPage().show(null);
	}
	
	public void updateKurs() {
		Kurs kurs = tblKurs.getSelectionModel().getSelectedItem();
		if(kurs == null) {
			new MessagePage().show(new Result(false, "Bir Kurs Seçiniz."));
			return;
		}
		Result result = kursService.findById(kurs.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Kurs  Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		
		new KursPage().show(kurs);
	}
	
	public void deleteKurs() {
		Kurs kurs = tblKurs.getSelectionModel().getSelectedItem();
		if(kurs == null) {
			new MessagePage().show(new Result(false, "Kurs Bulunamadı."));
			return;
		}
		Result result = kursService.findById(kurs.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Kurs Bulunamadı. Sayfayı Yenileyiniz."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Kurs Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			result = kursService.delete(kurs);
			if(result.isSuccess()) {
				tblKurs.getItems().remove(kurs);
//				buttonAdd.setDisable(true);
//				buttonUpdate.setDisable(true);
//				buttonDelete.setDisable(true);
//				((Stage) buttonDelete.getScene().getWindow()).close();
			}
			new MessagePage().show(result);
		}
		
		
	}
	
	private void setColumnInitialSettings() {
		//tblColumnTc.setCellValueFactory(cellData -> cellData.getValue().getTcNo()) ;
		tblColumnAd.setCellValueFactory(new PropertyValueFactory<Kurs,String>("ad"));
				
	}
}
