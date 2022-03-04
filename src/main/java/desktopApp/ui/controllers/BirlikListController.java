package desktopApp.ui.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.BirlikService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Rutbe;
import akdmEtkinlikEnvanter.entities.concretes.Sinif;
import akdmEtkinlikEnvanter.entities.concretes.Tez;
import desktopApp.ui.pages.BirlikPage;
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
public class BirlikListController {
	
	@Autowired
	private BirlikService birlikService;
	
	@FXML
	private TableView<Birlik> tblBirlik;
	
	@FXML
	private TableColumn<Birlik, String> tblColumnAd;

	@FXML
	Button buttonAdd, buttonUpdate, buttonDelete;
	
	@FXML
	TextField txtFieldSearchBirlikAd;
	
	private List<Birlik> birlikler;
	
	@FXML
	private void initialize() {
		setColumnInitialSettings();
	}
	
	public void refrashPage() {
		txtFieldSearchBirlikAd.clear();
		fillTblBirlik();
	}
	
	public void setBirlikler(List<Birlik> birlikler) {
		this.birlikler = birlikler;
		if(birlikler != null)
			fillTblBirlik();
	}

	public void fillTblBirlik() {
		List<Birlik> birlikler = birlikService.findByAdContainingIgnoreCase(txtFieldSearchBirlikAd.getText()).getData();
		
		ObservableList<Birlik> birlikObservableList = FXCollections.observableArrayList(
				birlikler
				);
		
		tblBirlik.setItems(birlikObservableList);
	}
	
	public void addBirlik() {
		new BirlikPage().show(null);
	}
	
	public void updateBirlik() {
		Birlik birlik = tblBirlik.getSelectionModel().getSelectedItem();
		if(birlik == null) {
			new MessagePage().show(new Result(false, "Bir Birlik Seçiniz."));
			return;
		}
		Result result = birlikService.getById(birlik.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Birlik  Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		
		new BirlikPage().show(birlik);
	}
	
	public void deleteBirlik() {
		Birlik birlik = tblBirlik.getSelectionModel().getSelectedItem();
		if(birlik == null) {
			new MessagePage().show(new Result(false, "Birlik Bulunamadı."));
			return;
		}
		Result result = birlikService.getById(birlik.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Birlik Bulunamadı. Sayfayı Yenileyiniz."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Tez Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			result = birlikService.delete(birlik);
			if(result.isSuccess()) {
				tblBirlik.getItems().remove(birlik);
				birlik = null;
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
		tblColumnAd.setCellValueFactory(new PropertyValueFactory<Birlik,String>("ad"));
				
	}
}
