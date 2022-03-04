package desktopApp.ui.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.BirlikService;
import akdmEtkinlikEnvanter.business.abstracts.YayineviDergiService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Rutbe;
import akdmEtkinlikEnvanter.entities.concretes.Sinif;
import akdmEtkinlikEnvanter.entities.concretes.Tez;
import akdmEtkinlikEnvanter.entities.concretes.YayineviDergi;
import desktopApp.ui.pages.BirlikPage;
import desktopApp.ui.pages.MessagePage;
import desktopApp.ui.pages.YayineviDergiPage;
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
public class YayineviDergiListController {
	
	@Autowired
	private YayineviDergiService yayineviDergiService;
	
	@FXML
	private TableView<YayineviDergi> tblYayineviDergi;
	
	@FXML
	private TableColumn<YayineviDergi, String> tblColumnAd;

	@FXML
	Button buttonAdd, buttonUpdate, buttonDelete;
	
	@FXML
	TextField txtFieldSearchYayineviDergiAd;
	
	private List<YayineviDergi> yayineviDergiler;
	
	@FXML
	private void initialize() {
		setColumnInitialSettings();
	}
	
	public void refrashPage() {
		txtFieldSearchYayineviDergiAd.clear();
		fillTblYayineviDergi();
	}
	
	public void setYayineviDergiler(List<YayineviDergi> yayineviDergiler) {
		this.yayineviDergiler = yayineviDergiler;
		if(yayineviDergiler != null)
			fillTblYayineviDergi();
	}

	public void fillTblYayineviDergi() {
		List<YayineviDergi> yayineviDergiler = yayineviDergiService.getByAdContainingIgnoreCase(txtFieldSearchYayineviDergiAd.getText()).getData();
		
		ObservableList<YayineviDergi> yayineviDergiObservableList = FXCollections.observableArrayList(
				yayineviDergiler
				);
		
		tblYayineviDergi.setItems(yayineviDergiObservableList);
	}
	
	public void addYayineviDergi() {
		new YayineviDergiPage().show(null);
	}
	
	public void updateYayineviDergi() {
		YayineviDergi yayineviDergi = tblYayineviDergi.getSelectionModel().getSelectedItem();
		if(yayineviDergi == null) {
			new MessagePage().show(new Result(false, "Bir Yayınevi/Dergi Seçiniz."));
			return;
		}
		Result result = yayineviDergiService.getById(yayineviDergi.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Yayınevi/Dergi  Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		
		new YayineviDergiPage().show(yayineviDergi);
	}
	
	public void deleteYayineviDergi() {
		YayineviDergi yayineviDergi = tblYayineviDergi.getSelectionModel().getSelectedItem();
		if(yayineviDergi == null) {
			new MessagePage().show(new Result(false, "Yayınevi/Dergi Bulunamadı."));
			return;
		}
		Result result = yayineviDergiService.getById(yayineviDergi.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Yayınevi/Dergi Bulunamadı. Sayfayı Yenileyiniz."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Yayınevi/Dergi Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			result = yayineviDergiService.delete(yayineviDergi);
			if(result.isSuccess()) {
				tblYayineviDergi.getItems().remove(yayineviDergi);
				yayineviDergi = null;
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
		tblColumnAd.setCellValueFactory(new PropertyValueFactory<YayineviDergi,String>("ad"));
				
	}
}
