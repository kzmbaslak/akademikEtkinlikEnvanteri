package desktopApp.ui.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.TezService;
import akdmEtkinlikEnvanter.business.abstracts.UniversiteEnstituService;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.UniversiteEnstitu;
import desktopApp.ui.pages.MessagePage;
import desktopApp.ui.pages.UniversiteEnstituPage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

@Component
public class UniversiteEnstituListController {
	
	@Autowired
	private UniversiteEnstituService universiteEnstituService;
	@Autowired
	private TezService tezService;
	@FXML
	private TableView<UniversiteEnstitu> tblUniversiteEnstitu;
	
	@FXML
	private TableColumn<UniversiteEnstitu, String> tblColumnAd;

	@FXML
	TextField txtFieldSearchUniEnstAd;
	
	@FXML
	Button buttonAdd, buttonUpdate, buttonDelete;
	
	private List<UniversiteEnstitu> universiteEnstituler;
	
	@FXML
	private void initialize() {
		setColumnInitialSettings();
	}
	
	public void refrashPage() {
		txtFieldSearchUniEnstAd.clear();
		fillTblUniversiteEnstitu();
		
		
	}
	
	public void setUniversiteEnstituler(List<UniversiteEnstitu> universiteEnstituler) {
		this.universiteEnstituler = universiteEnstituler;
		if(universiteEnstituler != null)
			tblUniversiteEnstitu.getItems().addAll(universiteEnstituler);
	}
	
	public void fillTblUniversiteEnstitu() {
		List<UniversiteEnstitu> universiteler = universiteEnstituService.findByAdContainingIgnoreCase(txtFieldSearchUniEnstAd.getText()).getData();
		
		ObservableList<UniversiteEnstitu> universiteObservableList = FXCollections.observableArrayList(
				universiteler
				);
		
		tblUniversiteEnstitu.setItems(universiteObservableList);
	}
	
	public void addUniversiteEnstitu(ActionEvent e) {
		new UniversiteEnstituPage().show(null);
	}
	

	public void updateUniversiteEnstitu(ActionEvent e) {
		UniversiteEnstitu universiteEnstitu = tblUniversiteEnstitu.getSelectionModel().getSelectedItem();
		
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
	
	public void deleteUniversiteEnstitu() {
		UniversiteEnstitu universiteEnstitu = tblUniversiteEnstitu.getSelectionModel().getSelectedItem();
		if(universiteEnstitu == null) {
			new MessagePage().show(new Result(false, "Üniversite Enstitü Bulunamadı."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Üniversite/Enstitü Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			Alert alert1 = new Alert(AlertType.CONFIRMATION);
			alert1.setTitle("Üniversite/Enstitü Sil");

			alert1.setContentText(tezService.findByUniversiteEnstitu(universiteEnstitu).getData().toString());
			alert1.setHeaderText("Bu Yüksek Öğrenim Silinirse Aşağıdaki Tezlerin Tümü de Silinecektir.!!!!!!!!!");
			if(alert1.showAndWait().get() == ButtonType.OK) {
				Result result = universiteEnstituService.delete(universiteEnstitu);
				
				if(result.isSuccess()) {
					tblUniversiteEnstitu.getItems().remove(universiteEnstitu);
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
		tblColumnAd.setCellValueFactory(new PropertyValueFactory<UniversiteEnstitu,String>("ad"));
				
	}
}
