package desktopApp.ui.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.BelgeService;
import akdmEtkinlikEnvanter.business.abstracts.BirlikService;
import akdmEtkinlikEnvanter.business.abstracts.KitapService;
import akdmEtkinlikEnvanter.business.abstracts.KursService;
import akdmEtkinlikEnvanter.business.abstracts.PersonelService;
import akdmEtkinlikEnvanter.business.abstracts.RutbeService;
import akdmEtkinlikEnvanter.business.abstracts.SinavService;
import akdmEtkinlikEnvanter.business.abstracts.SinavSonucService;
import akdmEtkinlikEnvanter.business.abstracts.TezService;
import akdmEtkinlikEnvanter.business.abstracts.UniversiteEnstituService;
import akdmEtkinlikEnvanter.business.abstracts.YayinService;
import akdmEtkinlikEnvanter.business.abstracts.YayineviDergiService;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessResult;
import akdmEtkinlikEnvanter.dataAccess.abstracts.BelgeDao;
import akdmEtkinlikEnvanter.entities.concretes.Belge;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Kitap;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Rutbe;
import akdmEtkinlikEnvanter.entities.concretes.SinavSonuc;
import akdmEtkinlikEnvanter.entities.concretes.Sinif;
import akdmEtkinlikEnvanter.entities.concretes.Tez;
import akdmEtkinlikEnvanter.entities.concretes.Yayin;
import desktopApp.ui.DesktopApplication;
import desktopApp.ui.pages.BelgeListPage;
import desktopApp.ui.pages.BirlikListPage;
import desktopApp.ui.pages.ExcelPage;
import desktopApp.ui.pages.KitapListPage;
import desktopApp.ui.pages.KursListPage;
import desktopApp.ui.pages.MessagePage;
import desktopApp.ui.pages.PersonelPage;
import desktopApp.ui.pages.RutbeListPage;
import desktopApp.ui.pages.SinavListPage;
import desktopApp.ui.pages.TezListPage;
import desktopApp.ui.pages.UniversiteEnstituListPage;
import desktopApp.ui.pages.YayinListPage;
import desktopApp.ui.pages.YayineviDergiListPage;
import desktopApp.utilities.excel.ExcelHandler;
import desktopApp.utilities.javaFxOperations.FileOperations;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.util.Callback;

@Component
public class DesktopApplicationController {
	

	private PersonelService personelService;
	private BirlikService birlikService;
	private KursService kursService;
	private TezService tezService;	
	private RutbeService rutbeService;	
	private SinavService sinavService;	
	private SinavSonucService sinavSonucService;	
	private UniversiteEnstituService universiteEnstituService;
	private YayinService yayinService;
	private KitapService kitapService;
	private BelgeService belgeService;
	private YayineviDergiService yayineviDergiService;
	private ExcelHandler excelHandler;
	
	@FXML
	private TableView<Personel> tblAkdmEnvtr;
	@FXML
	private TableView<Personel> tblPersonel;
	@FXML
	private TableView<Birlik> tblBirlik;
	@FXML
	private TableView<Rutbe> tblRutbe;
	@FXML
	private TableView<Sinif> tblSinif;
	
	
	@FXML
	private TableColumn<Personel, String> tblColumnNumber;
	
	@FXML
	private TableColumn<Personel, String> tblColumnTc, tblColumnAd, tblColumnSoyad, tblColumnSicil, 
		tblColumnTel, tblColumnAciklamalar, tblColumnSinif,
		tblColumnDoktoraTez, tblColumnYukLisTez,tblColumnAles, tblColumnYdsYokdilKpds;
	@FXML
	private TableColumn<Personel, Birlik> tblColumnBirlik;
	@FXML
	private TableColumn<Personel, Rutbe> tblColumnRutbe;
	@FXML
	private TableColumn<Personel, List<Kurs>> tblColumnKurs;
	@FXML
	private TableColumn<Personel, List<Yayin>> tblColumnYayin;
	@FXML
	private TableColumn<Personel, List<Kitap>> tblColumnKitap;
	@FXML
	private TableColumn<Personel, List<Belge>> tblColumnBelge;
	
	
	@FXML
	private TextField txtFieldTcNo, txtFieldBirlik, txtFieldUnvan, txtFieldAd, txtFieldSoyad, txtFieldRutbe, txtFieldSicil, txtFieldTel,
		txtFieldAciklama, txtFieldSearchAd, txtFieldSearchSoyad, txtFieldSearchTc, txtFieldSearchSicil, txtFieldSearchTel, txtFieldSearchAciklama;
	
	@FXML
	private ListView<String> listDoktoraAlan, listDoktoraUniEns, listDoktoraBitTarih, listYLAlan,
		 listYLUniEnst, listYLBitTarih, listKurs, listAles, listYdsYokdilKpds;
	@FXML
	private ListView<Tez> listDoktoraTezKonusu, listYLTezKonusu;
	
	@FXML
	private ListView<Belge> listBelge;
	@FXML
	private ListView<Yayin> listYayin;
	@FXML
	private ListView<Kitap> listKitap;
	
	
	@FXML
	private ComboBox<Rutbe> cmBoxSearchRutbe;
	
	@FXML
	private ComboBox<Birlik> cmBoxSearchBirlik;
	
	@FXML
	private TextArea txtAreaConsole;
	
	
	@Autowired
	BelgeDao belgeDao;
	@Autowired
	FileOperations fileOperations;
	
	@FXML
	private void initialize() {
		setColumnInitialSettings();
		
		
		
		tblAkdmEnvtr.getSelectionModel().selectedItemProperty().addListener(
					(observable, oldValue, newValue) -> fillProperties(newValue)
				);
		fillSearchField();
		fillAkdmEnvtrTbl();
//		tblPersonel.getSelectionModel().selectedItemProperty().addListener(
//					(observable, oldValue, newValue) -> fillForPersonelTable(newValue)
//				);
//		
//		tblRutbe.getSelectionModel().selectedItemProperty().addListener(
//					(observable, oldValue, newValue) -> fillForRutbeTable(newValue)
//				);
		
		//<CTRL + C işlevi>
		final KeyCodeCombination keyCodeCopy = new KeyCodeCombination(KeyCode.C,KeyCombination.CONTROL_DOWN);
		tblAkdmEnvtr.setOnKeyPressed(event -> {
		       if (keyCodeCopy.match(event)) {
		    	   copyPersonel2System();
		       }
		});
		
		//</CTRL + C işlevi> 
		 
		fillConsole();
		
	}
	

	public void refrashPage() {
		txtFieldTcNo.clear();
		txtFieldBirlik.clear();
		txtFieldUnvan.clear();
		txtFieldAd.clear();
		txtFieldSoyad.clear();
		txtFieldRutbe.clear();
		txtFieldSicil.clear();
		txtFieldTel.clear();
		txtFieldAciklama.clear();
		listDoktoraAlan.getItems().clear();
		listDoktoraTezKonusu.getItems().clear();
		listDoktoraUniEns.getItems().clear();
		listDoktoraBitTarih.getItems().clear();
		listYLAlan.getItems().clear();
		listYLTezKonusu.getItems().clear();
		listYLUniEnst.getItems().clear();
		listYLBitTarih.getItems().clear();
		listKurs.getItems().clear();
		listAles.getItems().clear();
		listYdsYokdilKpds.getItems().clear();
		listYayin.getItems().clear();
		listKitap.getItems().clear();
		listBelge.getItems().clear();
		
		txtFieldSearchAd.clear();
		txtFieldSearchSoyad.clear();
		txtFieldSearchTc.clear();
		txtFieldSearchSicil.clear();
		txtFieldSearchTel.clear();
		txtFieldSearchAciklama.clear();
		cmBoxSearchBirlik.getItems().clear();
		cmBoxSearchRutbe.getItems().clear();
		fillSearchField();
		fillAkdmEnvtrTbl();
	}
	
	public void fillSearchField() {
		cmBoxSearchBirlik.getItems().addAll(
					birlikService.findAllByOrderByAdAsc().getData()
				);
		cmBoxSearchRutbe.getItems().addAll(
				rutbeService.getAll().getData()
			);
	}
	
	private void copyPersonel2System() {
		System.out.println("ctrl + c");
        
   	 	final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        Personel personel = tblAkdmEnvtr.getSelectionModel().getSelectedItem();
        String text = "<thml><body><table><tr>";
        if(personel != null) {
        	text += "<td>"+personel.getTcNo()+"</td>"+
        		"<td>"+personel.getAd()+"</td>"+
        		"<td>"+personel.getSoyad()+"</td>"+
        		"<td>"+personel.getSicil()+"</td>"+
        		"<td>"+personel.getTel()+"</td>"+
        		"<td>"+personel.getAciklama()+"</td>"+
        		"<td>"+personel.getBirlik()+"</td>"+
        		"<td>"+personel.getBirlik()+"</td>"+
        		"<td>"+(personel.getRutbe()!=null?personel.getRutbe().getSinif():"")+"</td>"+
        		"<td>"+personel.getRutbe()+"</td>";
        }
        text += "</tr></table></body></html>";
        content.putString(text);
        clipboard.setContent(content);
		
	}
	
	public void goToBirlik() {
		new BirlikListPage().show(birlikService.getAll().getData());
	}
	
	public void goToKurs() {
		new KursListPage().show(kursService.findAll().getData());
	}

	public void goToRutbe() {
		new RutbeListPage().show(rutbeService.getAll().getData());
	}
	
	public void goToSinav() {
		new SinavListPage().show(sinavService.findAll().getData());
	}

	public void goToTez() {
		new TezListPage().show(tezService.findAll().getData());
	}
	
	public void goToUniversiteEnstitu() {
		new UniversiteEnstituListPage().show(universiteEnstituService.getAll().getData());
	}

	public void goToBelge() {
		new BelgeListPage().show(belgeService.getAll().getData());
	}

	public void goToYayin() {
		new YayinListPage().show(yayinService.getAll().getData());
	}

	public void goToKitap() {
		new KitapListPage().show(kitapService.getAll().getData());
	}

	public void goToYayineviDergi() {
		new YayineviDergiListPage().show(yayineviDergiService.getAll().getData());
	}
	
	public void addPersonel(ActionEvent e) {
		new PersonelPage().show(null);
	}
	
	public void editPersonel(ActionEvent e) {
	
		Personel personel;
		try {
			personel = tblAkdmEnvtr.getSelectionModel().getSelectedItem();
			if(personel == null) {
				new MessagePage().show(new ErrorResult("Bir Personel Seçiniz!!!"));
				return;
			}
			Result result = personelService.getById(personel.getId());
			if(!result.isSuccess()) {
				new MessagePage().show(new ErrorResult("Bu Personel  Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
				return;
			}
				
		}
		catch(Exception ex) {
			new MessagePage().show(new ErrorResult("Bir Personel Seçiniz!!!"));
			return;
		}
		
		new PersonelPage().show(personel);
		
	}
	
	public void openFileBelge() {
		Belge belge = listBelge.getSelectionModel().getSelectedItem();
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
	
	public void openFileYayin() {
		Yayin yayin = listYayin.getSelectionModel().getSelectedItem();
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
	
	public void openFileKitap() {
		Kitap kitap = listKitap.getSelectionModel().getSelectedItem();
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
	
	public void openFileDoktora() {
		Tez tez = listDoktoraTezKonusu.getSelectionModel().getSelectedItem();
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

	public void openFileYuksekLisans() {
		Tez tez = listYLTezKonusu.getSelectionModel().getSelectedItem();
		if(tez == null) {
			new MessagePage().show(new Result(false, "Bir Tez Seçiniz."));
			return;
		}
		Result result = tezService.findById(tez.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Tez Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		
		if(tez.getFile() != null && tez.getFile().exists())
			DesktopApplication.hostServices.showDocument(tez.getFile().getPath());
		else
			new MessagePage().show(new ErrorResult("Teze Ait Dosya Bulunmamaktadır."));
		
	}
	
	public void goToExcelImportExport() {
		System.out.println("goTo eXCEL");
		new ExcelPage().show();
	}
	
	public void importData(ActionEvent e) { 
		String path = fileOperations.getChooseFilePath("Excel File","*.xlsx");
		try {
			if(path == null) {
				//new MessagePage().show(new ErrorResult("Veriler Aktarılamadı."));
				return;
			}
			
			excelHandler.pullValueOfExcelToDatabase(path);
			new MessagePage().show(new SuccessResult("Veriler İçe Aktarıldı."));
			fillAkdmEnvtrTbl();
		}
		catch(Exception ex) {
			new MessagePage().show(new ErrorResult(ex.getMessage()));
		}
		
		
	}
	
	public void exportData(ActionEvent e) {
		String path = fileOperations.getChooseDirectoryPath();
		try {
			if(path == null)
				return;
			excelHandler.pullValueOfDatabaseToExcel(path);
			
			new MessagePage().show(new SuccessResult("Veriler Dışa Aktarıldı."));
		}
		catch(Exception ex) {
			ex.printStackTrace();
			new MessagePage().show(new ErrorResult("Veriler Aktarılamadı. "+ex.getMessage()));
		}
		
	}
	
	public void fillConsole() {
		txtAreaConsole.clear();
		txtAreaConsole.setText(new DesktopApplication().getConsole());
	}
	
	public void clearConsole() {
		new DesktopApplication().clearConsole();
		fillConsole();
	}
	
	public void fillAkdmEnvtrTbl(){
//		tblAkdmEnvtr.getItems().clear();
//		DataResult<List<Personel>> r = personelService.findByAdContainingIgnoreCaseAndSoyadContainingIgnoreCaseAndSicilContainingIgnoreCaseAndTcNoContainingIgnoreCaseAndTelContainingIgnoreCaseAndAciklamaContainingIgnoreCaseAndBirlikAndRutbe(txtFieldSearchAd.getText(), txtFieldSearchSoyad.getText(), txtFieldSearchSicil.getText(), txtFieldSearchTc.getText());
//		System.out.println(r.getMessage());
//		for (Personel p : r.getData()) {
//			System.out.println(p.getAd());
//		}
		
		String rutbeAd = "",birlikAd = "";
		if(cmBoxSearchBirlik.getValue() != null)
			birlikAd = cmBoxSearchBirlik.getValue().getAd();
		if(cmBoxSearchRutbe.getValue() != null)
			rutbeAd = cmBoxSearchRutbe.getValue().getAd();
		
		List<Personel> personeller = personelService.findByAdContainingIgnoreCaseAndSoyadContainingIgnoreCaseAndSicilContainingIgnoreCaseAndTcNoContainingIgnoreCaseAndTelContainingIgnoreCaseAndAciklamaContainingIgnoreCaseAndBirlik_adContainingIgnoreCaseAndRutbe_adContainingIgnoreCase(
				txtFieldSearchAd.getText(), txtFieldSearchSoyad.getText(), txtFieldSearchSicil.getText(),
				txtFieldSearchTc.getText(), txtFieldSearchTel.getText(), txtFieldSearchAciklama.getText(),
				birlikAd, rutbeAd).getData();
		
		ObservableList<Personel> personelObservableList = FXCollections.observableArrayList(
//				personelService.findAll().getData()
				personeller
				
				);
		
		tblAkdmEnvtr.setItems(personelObservableList);
		tblAkdmEnvtr.refresh();
	}
	
	private void fillProperties(Personel personel) {
		if(personel == null)
			return;
//		tblPersonel.setItems(
//				FXCollections.observableArrayList(
//						personel
//				)
//				);
//		fillForPersonelTable(personel);
		
		
		
		txtFieldTcNo.setText(personel.getTcNo());
		
		txtFieldBirlik.setText(personel.getBirlik()!= null?personel.getBirlik().getAd():"Yok");
		txtFieldUnvan.setText(personel.getRutbe() != null?personel.getRutbe().getSinif().getAd():"Yok");
		txtFieldAd.setText(personel.getAd());
		txtFieldSoyad.setText(personel.getSoyad());
		txtFieldRutbe.setText(personel.getRutbe() != null?personel.getRutbe().getAd():"Yok");
		txtFieldSicil.setText(personel.getSicil());
		txtFieldTel.setText(personel.getTel());
		
		
		listDoktoraAlan.getItems().clear();
		listDoktoraTezKonusu.getItems().clear();
		listDoktoraUniEns.getItems().clear();
		listDoktoraBitTarih.getItems().clear();
		listYLAlan.getItems().clear();
		listYLTezKonusu.getItems().clear();
		listYLUniEnst.getItems().clear();
		listYLBitTarih.getItems().clear();

		for (Tez tez : tezService.findByPersonel(personel).getData()){
			if(tez.getTezTuru().getId() == 1) {//Doktora
				listDoktoraAlan.getItems().add(tez.getYuksekOgrenim().getAlanAdi());
				listDoktoraTezKonusu.getItems().add(tez);
				listDoktoraUniEns.getItems().add(tez.getUniversiteEnstitu().getAd());
				listDoktoraBitTarih.getItems().add(tez.getBitirdigiTarih()+"");
			}
			else {
				listYLAlan.getItems().add(tez.getYuksekOgrenim().getAlanAdi());
				listYLTezKonusu.getItems().add(tez);
				listYLUniEnst.getItems().add(tez.getUniversiteEnstitu().getAd());
				listYLBitTarih.getItems().add(tez.getBitirdigiTarih()+"");
			}
		}
		
		listKurs.getItems().clear();
		for(Kurs kurs: kursService.getByPersoneller_IdOrderByAdAsc(personel.getId()).getData()) {
			listKurs.getItems().add(kurs.getAd());
		}
		
		listAles.getItems().clear();
		listYdsYokdilKpds.getItems().clear();
		List<SinavSonuc> sinavSonuclar = sinavSonucService.getByPersonel(personel).getData();
		for(SinavSonuc sinavSonuc: sinavSonuclar) {
			if(sinavSonuc.getSinav().getSinavTuru().getId() == 1) {//Ales
				listAles.getItems().add(sinavSonuc.getSinavNot()+"");
			}
			else {//Yökdil/Kpds
				listYdsYokdilKpds.getItems().add(sinavSonuc.getSinavNot()+"");
			}
		}
		
		listYayin.getItems().clear();
		for (Yayin yayin : yayinService.findByYazarlar_Id(personel.getId()).getData()) {
			listYayin.getItems().add(yayin);
		}
		
		listKitap.getItems().clear();
		for (Kitap kitap : kitapService.findByYazarlar_Id(personel.getId()).getData()) {
			listKitap.getItems().add(kitap);
		}

		listBelge.getItems().clear();
		for (Belge belge : belgeService.findByPersonel(personel).getData()) {
			listBelge.getItems().add(belge);
		}
		
		txtFieldAciklama.setText(personel.getAciklama());
	}
	
//	private void fillForPersonelTable(Personel personel) {//Ayrıntı Kısmı Tablo Biçimi İçindir.
//		tblBirlik.setItems(
//				FXCollections.observableArrayList(
//						personel.getBirlik()
//				)
//				);
//		tblRutbe.setItems(
//				FXCollections.observableArrayList(
//						personel.getRutbe()
//				)
//				);
//		fillForRutbeTable(personel.getRutbe());
//	}
//	
//	private void fillForRutbeTable(Rutbe rutbe) {
//		tblSinif.setItems(
//				FXCollections.observableArrayList(
//						rutbe.getSinif()
//				)
//				);
//	}
	
	int i = 0;
	private void setColumnInitialSettings() {
		//tblColumnTc.setCellValueFactory(cellData -> cellData.getValue().getTcNo()) ;
				tblColumnTc.setCellValueFactory(new PropertyValueFactory<Personel,String>("tcNo"));
				tblColumnNumber.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Personel,String>, ObservableValue<String>>() {
					
					@Override
					public ObservableValue<String> call(CellDataFeatures<Personel,String> param) {
						return new ReadOnlyObjectWrapper(tblAkdmEnvtr.getItems().indexOf(param.getValue())+1+"");
//						return new ReadOnlyObjectWrapper(++i+"");

					}
				});
//				tblColumnBirlik.setCellValueFactory(new PropertyValueFactory<PersonelWithAllObjects,String>("birlik"));
//				tblColumnUnvan.setCellValueFactory(new PropertyValueFactory<PersonelWithAllObjects,String>("sinif"));
				tblColumnAd.setCellValueFactory(new PropertyValueFactory<Personel,String>("ad"));
				tblColumnSoyad.setCellValueFactory(new PropertyValueFactory<Personel,String>("soyad"));
//				tblColumnRutbe.setCellValueFactory(new PropertyValueFactory<PersonelWithAllObjects,String>("rutbe"));
				tblColumnSicil.setCellValueFactory(new PropertyValueFactory<Personel,String>("sicil"));
				tblColumnTel.setCellValueFactory(new PropertyValueFactory<Personel,String>("tel"));
//				tblColumnDoktoraAlani.setCellValueFactory(new PropertyValueFactory<PersonelWithAllObjects,String>("doktoraAlani"));
//				tblColumnDoktoraTezKonusu.setCellValueFactory(new PropertyValueFactory<PersonelWithAllObjects,String>("doktoraTezKonusu"));
//				tblColumnDoktoraUniEns.setCellValueFactory(new PropertyValueFactory<PersonelWithAllObjects,String>("doktoraUniversiteEnstitu"));
//				tblColumnDoktoraBitTarih.setCellValueFactory(new PropertyValueFactory<PersonelWithAllObjects,String>("doktoraBitirdigiTarih"));
//				tblColumnYukLisAlani.setCellValueFactory(new PropertyValueFactory<PersonelWithAllObjects,String>("yuksekLisansAlani"));
//				tblColumnYukLisTezKonusu.setCellValueFactory(new PropertyValueFactory<PersonelWithAllObjects,String>("yuksekLisansTezKonusu"));
//				tblColumnYukLisUniEnst.setCellValueFactory(new PropertyValueFactory<PersonelWithAllObjects,String>("yuksekLisansUniversiteEnstitu"));
//				tblColumnYukLisBitTarih.setCellValueFactory(new PropertyValueFactory<PersonelWithAllObjects,String>("yuksekLisansBitirdigiTarih"));
//				tblColumnKurslar.setCellValueFactory(new PropertyValueFactory<PersonelWithAllObjects,String>("kurslar"));
//				tblColumnAles.setCellValueFactory(new PropertyValueFactory<PersonelWithAllObjects,String>("ales"));
//				tblColumnYdsYokdilKpds.setCellValueFactory(new PropertyValueFactory<PersonelWithAllObjects,String>("ydsYokdilKpds"));
//				tblColumnDoktoraAlani.setCellValueFactory(new PropertyValueFactory<PersonelWithAllObjects,String>("doktoraAlani"));
				tblColumnAciklamalar.setCellValueFactory(new PropertyValueFactory<Personel,String>("aciklama"));
				tblColumnBirlik.setCellValueFactory(new PropertyValueFactory<Personel,Birlik>("birlik"));
				tblColumnSinif.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Personel,String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Personel, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyObjectWrapper(param.getValue().getRutbe().getSinif());
					}
				});
				tblColumnRutbe.setCellValueFactory(new PropertyValueFactory<Personel,Rutbe>("rutbe"));
				tblColumnDoktoraTez.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Personel,String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Personel, String> param) {
						// TODO Auto-generated method stub
						
						return new ReadOnlyObjectWrapper(tezService.findByPersonelAndTezTuruId(param.getValue(), 1).getData());
					}
				});
				
				tblColumnYukLisTez.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Personel,String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Personel, String> param) {
						// TODO Auto-generated method stub
						
						return new ReadOnlyObjectWrapper(tezService.findByPersonelAndTezTuruId(param.getValue(), 2).getData());
					}
				});
				tblColumnKurs.setCellValueFactory(new PropertyValueFactory<Personel,List<Kurs>>("kurslar"));
				tblColumnAles.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Personel,String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Personel, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyObjectWrapper(sinavSonucService.getByPersonelAndSinav_SinavTuru_Id(param.getValue(), 1).getData());
					}
				});
				
				tblColumnYdsYokdilKpds.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Personel,String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Personel, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyObjectWrapper(sinavSonucService.getByPersonelAndSinav_SinavTuru_Id(param.getValue(), 2).getData());
					}
				});
				
				tblColumnYayin.setCellValueFactory(new PropertyValueFactory<Personel,List<Yayin>>("yayinlar"));
				
				tblColumnKitap.setCellValueFactory(new PropertyValueFactory<Personel,List<Kitap>>("kitaplar"));
				
				tblColumnBelge.setCellValueFactory(new PropertyValueFactory<Personel,List<Belge>>("belgeler"));
				//Personel Table
//				tblColPersonelTc.setCellValueFactory(new PropertyValueFactory<Personel,String>("tcNo"));
//				tblColPersonelAd.setCellValueFactory(new PropertyValueFactory<Personel,String>("ad"));
//				tblColPersonelSoyad.setCellValueFactory(new PropertyValueFactory<Personel,String>("soyad"));
//				tblColPersonelSicil.setCellValueFactory(new PropertyValueFactory<Personel,String>("sicil"));
//				tblColPersonelTel.setCellValueFactory(new PropertyValueFactory<Personel,String>("tel"));
//				tblColPersonelAciklama.setCellValueFactory(new PropertyValueFactory<Personel,String>("aciklama"));
//				
//				//Birlik
//				tblColBirlikAd.setCellValueFactory(new PropertyValueFactory<Birlik,String>("ad"));
//
//				//Rutbe
//				tblColRutbeAd.setCellValueFactory(new PropertyValueFactory<Rutbe,String>("ad"));
//				
//				//Sinif
//				tblColSinifAd.setCellValueFactory(new PropertyValueFactory<Sinif,String>("ad"));
				
	}
	
	@Autowired
	public DesktopApplicationController(PersonelService personelService, BirlikService birlikService,
			KursService kursService, TezService tezService, RutbeService rutbeService, SinavService sinavService,
			SinavSonucService sinavSonucService, UniversiteEnstituService universiteEnstituService,
			YayinService yayinService, KitapService kitapService, BelgeService belgeService, YayineviDergiService yayineviDergiService,
			ExcelHandler excelHandler) {
		super();
		this.personelService = personelService;
		this.birlikService = birlikService;
		this.kursService = kursService;
		this.tezService = tezService;
		this.rutbeService = rutbeService;
		this.sinavService = sinavService;
		this.sinavSonucService = sinavSonucService;
		this.universiteEnstituService = universiteEnstituService;
		this.excelHandler = excelHandler;
		this.yayinService = yayinService;
		this.kitapService = kitapService;
		this.belgeService = belgeService;
		this.yayineviDergiService = yayineviDergiService;
	}
	
}
