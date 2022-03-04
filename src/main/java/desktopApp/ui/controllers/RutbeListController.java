package desktopApp.ui.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.RutbeService;
import akdmEtkinlikEnvanter.business.abstracts.SinifService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Rutbe;
import akdmEtkinlikEnvanter.entities.concretes.Sinif;
import desktopApp.ui.pages.MessagePage;
import desktopApp.ui.pages.RutbePage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

@Component
public class RutbeListController {
	@Autowired
	private RutbeService rutbeService;
	@Autowired
	private SinifService sinifService;
	
	@FXML
	private TableView<Rutbe> tblRutbe;
	
	@FXML
	private TableColumn<Rutbe, String> tblColumnAd, tblColumnSinif;

	@FXML
	private Button buttonAdd, buttonUpdate, buttonDelete;
	
	@FXML
	TextField txtFieldSearchRutbeAd;

	@FXML
	private ComboBox<Sinif> cmBoxSinif;
	
	private List<Rutbe> rutbeler;
	
	@FXML
	private void initialize() {
		setColumnInitialSettings();
		fillSinif();
	}
	
	public void refrashPage() {
		txtFieldSearchRutbeAd.clear();
		fillTblRutbe();
		fillSinif();
	}
	
	public void setRutbeler(List<Rutbe> rutbeler) {
		this.rutbeler = rutbeler;
		if(rutbeler != null)
			fillTblRutbe();
	}
	
	public void fillTblRutbe() {
		String rutbeSinifAd = "";
		if(cmBoxSinif.getValue() != null)
			rutbeSinifAd = cmBoxSinif.getValue().getAd();
		List<Rutbe> rutbeler = rutbeService.findByAdContainingAndSinif_ad(
				txtFieldSearchRutbeAd.getText(),rutbeSinifAd).getData();
		
		ObservableList<Rutbe> rutbeObservableList = FXCollections.observableArrayList(
				rutbeler
				);
		
		tblRutbe.setItems(rutbeObservableList);
	}
	
	public void fillSinif() {
		cmBoxSinif.getItems().clear();
		cmBoxSinif.getItems().addAll(sinifService.getAll().getData());
		
	}
	
	public void addRutbe() {
		new RutbePage().show(null);
	}
	
	public void updateRutbe() {
		Rutbe rutbe = tblRutbe.getSelectionModel().getSelectedItem();
		if(rutbe == null) {
			new MessagePage().show(new Result(false, "Bir Rütbe Seçiniz."));
			return;
		}
		Result result = rutbeService.getById(rutbe.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Rütbe  Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		
		new RutbePage().show(rutbe);
	}
	
	public void deleteRutbe() {
		Rutbe rutbe = tblRutbe.getSelectionModel().getSelectedItem();
		if(rutbe == null) {
			new MessagePage().show(new Result(false, "Rütbe Bulunamadı."));
			return;
		}
		Result result = rutbeService.getById(rutbe.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Rütbe Bulunamadı. Sayfayı Yenileyiniz."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Tez Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			result = rutbeService.delete(rutbe);
			if(result.isSuccess()) {
				tblRutbe.getItems().remove(rutbe);
//				buttonAdd.setDisable(true);
//				buttonUpdate.setDisable(true);
//				buttonDelete.setDisable(true);
			}
			new MessagePage().show(result);
		}
		
	}
	
	private void setColumnInitialSettings() {
		//tblColumnTc.setCellValueFactory(cellData -> cellData.getValue().getTcNo()) ;
		tblColumnAd.setCellValueFactory(new PropertyValueFactory<Rutbe,String>("ad"));
		tblColumnSinif.setCellValueFactory(new PropertyValueFactory<Rutbe,String>("sinif"));
				
	}
}
