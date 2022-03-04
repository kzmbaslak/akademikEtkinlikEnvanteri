package desktopApp.ui.controllers;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.BelgeService;
import akdmEtkinlikEnvanter.business.abstracts.BirlikService;
import akdmEtkinlikEnvanter.business.abstracts.KitapService;
import akdmEtkinlikEnvanter.business.abstracts.KursService;
import akdmEtkinlikEnvanter.business.abstracts.PersonelService;
import akdmEtkinlikEnvanter.business.abstracts.RutbeService;
import akdmEtkinlikEnvanter.business.abstracts.SinavSonucService;
import akdmEtkinlikEnvanter.business.abstracts.SinifService;
import akdmEtkinlikEnvanter.business.abstracts.TezService;
import akdmEtkinlikEnvanter.business.abstracts.YayinService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Belge;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Kitap;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Rutbe;
import akdmEtkinlikEnvanter.entities.concretes.Sinav;
import akdmEtkinlikEnvanter.entities.concretes.SinavSonuc;
import akdmEtkinlikEnvanter.entities.concretes.SinavTuru;
import akdmEtkinlikEnvanter.entities.concretes.Sinif;
import akdmEtkinlikEnvanter.entities.concretes.Tez;
import akdmEtkinlikEnvanter.entities.concretes.Yayin;
import desktopApp.ui.pages.BelgePage;
import desktopApp.ui.pages.BirlikPage;
import desktopApp.ui.pages.KitapPage;
import desktopApp.ui.pages.KursPage;
import desktopApp.ui.pages.MessagePage;
import desktopApp.ui.pages.RutbePage;
import desktopApp.ui.pages.SinavSonucPage;
import desktopApp.ui.pages.SinifPage;
import desktopApp.ui.pages.TezPage;
import desktopApp.ui.pages.YayinPage;
import desktopApp.utilities.arrayOperations.ArrayCopy;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Data;

@Component
@Data
public class PersonelController {
	@Autowired
	private PersonelService personelService;
	@Autowired
	private BirlikService birlikService;
	@Autowired
	private SinifService sinifService;
	@Autowired
	private RutbeService rutbeService;
	@Autowired
	private TezService tezService;
	@Autowired
	private KursService kursService;
	@Autowired
	private SinavSonucService sinavSonucService;
	@Autowired
	private BelgeService belgeService;
	@Autowired
	private YayinService yayinService;
	@Autowired
	private KitapService kitapService;
	
	
	@FXML
	private ComboBox<Birlik> cmBoxBirlik;

	@FXML
	private ComboBox<Rutbe> cmBoxRutbe;
	
	@FXML
	private TextField txtFieldTcNo, txtFieldAd, txtFieldSoyad, txtFieldSicil, txtFieldTel, txtFieldAciklama,
	txtFieldSearchBirlik, txtFieldSearchRutbe, txtFieldsearchTez, txtFieldsearchKurs;
	
	@FXML
	private ListView<Tez> listTez, listTezOther;

	@FXML
	private ListView<Kurs> listKurs, listKursOther;
	
	@FXML
	private ListView<SinavSonuc> listAles, listYdsYokdilKpds;
	
	@FXML
	private ListView<Belge> listBelge;
	@FXML
	private ListView<Yayin> listYayin;
	@FXML
	private ListView<Kitap> listKitap;
	
	@FXML
	private Button buttonAdd, buttonUpdate, buttonDelete;
	
	private Personel personel;
	
	@FXML
	private void initialize() {
		fillTezOther();
		fillKursOther();
		fillBirlik();
	//	fillUnvan();
		fillRutbe();
		
	}
	
	
	public void setPersonel(Personel personel) {
		this.personel = personel;
		if(personel != null) {
			fillPersonel();
			fllSinavSonuc();
			fillTez();
			fillKurs();
			fillBelge();
			fillYayin();
			fillKitap();
		}
		else {
			fillTezOther();
			fillKursOther();
		}
		fillBirlik();
//		fillUnvan();
		fillRutbe();
	}

	public void refrashPage() {
		txtFieldTcNo.clear();
		txtFieldAd.clear();
		txtFieldSoyad.clear();
		txtFieldSicil.clear();
		txtFieldTel.clear();
		txtFieldAciklama.clear();
		txtFieldSearchBirlik.clear();
		txtFieldSearchRutbe.clear();
		txtFieldsearchTez.clear();
		txtFieldsearchKurs.clear();
		cmBoxBirlik.getItems().clear();
		cmBoxRutbe.getItems().clear();
		listTez.getItems().clear();
		listTezOther.getItems().clear();
		listKurs.getItems().clear();
		listKursOther.getItems().clear();
		listAles.getItems().clear();
		listYdsYokdilKpds.getItems().clear();
		listBelge.getItems().clear();
		listYayin.getItems().clear();
		listKitap.getItems().clear();
		
		if(this.personel != null) {
			DataResult<Personel> result = personelService.getById(this.personel.getId());
			if(result.isSuccess()) {
				personel = result.getData();
				if(personel != null) {
					fillPersonel();
					fillBirlik();
					fillRutbe();
					fillTez();
					fillKurs();
					fllSinavSonuc();
					fillBelge();
					fillYayin();
					fillKitap();
				}
				
			}
		}
	}
	
	private void fillPersonel() {
		txtFieldTcNo.setText(this.personel.getTcNo());
		txtFieldAd.setText(this.personel.getAd());
		txtFieldSoyad.setText(this.personel.getSoyad());
		txtFieldSicil.setText(this.personel.getSicil());
		txtFieldTel.setText(this.personel.getTel());
		txtFieldAciklama.setText(this.personel.getAciklama());
		
	}

	public void fillBirlik() {
		List<Birlik> birlikler = new ArrayList<Birlik>();
		for (Birlik birlik : birlikService.findByAdContainingIgnoreCaseOrderByAdAsc(txtFieldSearchBirlik.getText()).getData()) {
			birlikler.add(birlik);
		}
		//cmBoxBirlik.setPromptText(personel.getBirlik().getAd());
		if(cmBoxBirlik.getSelectionModel().getSelectedItem() == null && personel != null)
			cmBoxBirlik.getSelectionModel().select(personel.getBirlik());
		cmBoxBirlik.getItems().clear();
		cmBoxBirlik.getItems().addAll(birlikler);
		
	}
//	
//	public void fillUnvan() {
//		List<Sinif> siniflar = new ArrayList<Sinif>();
//		for (Sinif sinif : sinifService.getAll().getData()) {
//			siniflar.add(sinif);
//		}
//		//cmBoxUnvan.setPromptText(personel.getRutbe().getSinif().getAd());
//		if(cmBoxUnvan.getSelectionModel().getSelectedItem() == null)
//			cmBoxUnvan.getSelectionModel().select(personel.getRutbe().getSinif());
//		cmBoxUnvan.getItems().clear();
//		cmBoxUnvan.getItems().addAll(siniflar);
//		
//	}

	public void fillRutbe() {
		List<Rutbe> rutbeler = new ArrayList<Rutbe>();
		for (Rutbe rutbe : rutbeService.findByAdContainingIgnoreCase(txtFieldSearchRutbe.getText()).getData()) {
			rutbeler.add(rutbe);
		}
		//cmBoxRutbe.setPromptText(personel.getRutbe().getAd());
		if(cmBoxRutbe.getSelectionModel().getSelectedItem() == null && personel != null)
			cmBoxRutbe.getSelectionModel().select(personel.getRutbe());
		cmBoxRutbe.getItems().clear();
		cmBoxRutbe.getItems().addAll(rutbeler);
	}

	public void fillTez() {
		int selectedTez = listTez.getSelectionModel().getSelectedIndex();
		
		List<Tez> tezler = tezService.findByPersonel(personel).getData();
		if(listTez.getItems().size() == 0) {
			for (Tez tez : tezler) {
			listTez.getItems().add(tez);
			}
		}
		
		fillTezOther();
		

		listTez.getSelectionModel().select(selectedTez);
	}
	
	public void fillTezOther() {
		List<Tez> tezlerOther = tezService.findByPersonelNotAndTezKonusuContainingIgnoreCase(
				personel,txtFieldsearchTez.getText()).getData();
		
		listTezOther.getItems().clear();
//		List<Tez> tezlerOther = tezService.findByPersonelNot(personel).getData();
		for (Tez tez : tezlerOther) {
			listTezOther.getItems().add(tez);
		}
	}
	
	List<Integer> kurslarId;
	public void fillKurs() {
		kurslarId = new ArrayList<>();
		if(personel != null) {
			int selectedKurs = listKurs.getSelectionModel().getSelectedIndex();
			List<Kurs> kurslar = kursService.getByPersoneller_IdOrderByAdAsc(personel.getId()).getData();
	//		List<Integer> kurslarId = new ArrayList<>();
			kurslarId.add(0);
			
			if(listKurs.getItems().size() == 0) {
				for (Kurs kurs : kurslar) {
					listKurs.getItems().add(kurs);
					kurslarId.add(kurs.getId());
				}
			}
			listKurs.getSelectionModel().select(selectedKurs);
		}
		
		
		fillKursOther();

		
	}

	public void fillKursOther() {
		List<Kurs> kurslarOther;
		if(personel != null) {
			kurslarOther = kursService.findByIdNotInAndAdContainingIgnoreCaseOrderByAdAsc(
				kurslarId, txtFieldsearchKurs.getText()).getData();
		}
		else
			kurslarOther = kursService.findAll().getData();
//		System.out.println("Girdi");
//		for (Kurs kurs : kurslarOther) {
//			System.out.println(kurs.getAd());
//		}
		
		listKursOther.getItems().clear();
		for (Kurs kurs : kurslarOther) {
			listKursOther.getItems().add(kurs);
			
		}
	
	}
	
	public void fllSinavSonuc() {
		int selectedAles = listAles.getSelectionModel().getSelectedIndex();
		int selectedlistYdsYokdilKpds = listYdsYokdilKpds.getSelectionModel().getSelectedIndex();
		listAles.getItems().clear();
		listYdsYokdilKpds.getItems().clear();
		List<SinavSonuc> sinavSonuclari = sinavSonucService.getByPersonel(this.personel).getData();
		for (SinavSonuc sinavSonuc : sinavSonuclari) {
			if(sinavSonuc.getSinav().getSinavTuru().getId() == 1)
				listAles.getItems().add(sinavSonuc);
			else {
				listYdsYokdilKpds.getItems().add(sinavSonuc);
			}
		}
		listAles.getSelectionModel().select(selectedAles);
		listYdsYokdilKpds.getSelectionModel().select(selectedlistYdsYokdilKpds);
		
	}
	
	public void fillBelge() {
		int selectedBelge = listTez.getSelectionModel().getSelectedIndex();
		
		List<Belge> belgeler = belgeService.findByPersonel(personel).getData();
		if(listBelge.getItems().size() == 0) {
			for (Belge belge : belgeler) {
				listBelge.getItems().add(belge);
			}
		}
		
		listBelge.getSelectionModel().select(selectedBelge);
	}
	
	public void fillYayin() {
		int selectedYayin = listYayin.getSelectionModel().getSelectedIndex();
		
		List<Yayin> yayinlar = yayinService.findByYazarlar_Id(personel.getId()).getData();
		if(listYayin.getItems().size() == 0) {
			for (Yayin belge : yayinlar) {
				listYayin.getItems().add(belge);
			}
		}
		
		listYayin.getSelectionModel().select(selectedYayin);
	}
	
	public void fillKitap() {
		int selectedKitap = listKitap.getSelectionModel().getSelectedIndex();
		
		List<Kitap> kitaplar = kitapService.findByYazarlar_Id(personel.getId()).getData();
		if(listKitap.getItems().size() == 0) {
			for (Kitap kitap : kitaplar) {
				listKitap.getItems().add(kitap);
			}
		}
		
		listKitap.getSelectionModel().select(selectedKitap);
	}
	
	public void move2listTez() {
		Tez tez = listTezOther.getSelectionModel().getSelectedItem();
		if(tez != null)
			listTez.getItems().add(tez);
		
		listTezOther.getItems().remove(tez);
	}
	
	public void move2listTezOther() {
		Tez tez = listTez.getSelectionModel().getSelectedItem();		
		if(tez != null)
			listTezOther.getItems().add(tez);
		
		listTez.getItems().remove(tez);
	}
	
	public void move2listKurs() {
		Kurs kurs = listKursOther.getSelectionModel().getSelectedItem();
		if(kurs != null)
			listKurs.getItems().add(kurs);
		
		listKursOther.getItems().remove(kurs);
	}
	
	public void move2listKursOther() {
		Kurs kurs = listKurs.getSelectionModel().getSelectedItem();		
		if(kurs != null)
			listKursOther.getItems().add(kurs);
		
		listKurs.getItems().remove(kurs);
	}
	
	public void addPersonel(ActionEvent e) {
		Personel newPersonel = new Personel();
		setForm2Personel(newPersonel);
		
		DataResult<Personel> result = personelService.add(newPersonel);

		if(result.isSuccess()) {
			for (Tez tez : listTez.getItems()) {
				tez.setPersonel(newPersonel);
				tezService.save(tez);
			}
//			System.out.println(newPersonel.getId()+" -----");
			for (Kurs kurs : listKurs.getItems()) {
//				for (Personel personel : kurs.getPersoneller()) {
//					System.out.println(personel.getId());
//				}
//				System.out.println(newPersonel.getId());
				kurs.getPersoneller().add(newPersonel);
//				for (Personel personel : kurs.getPersoneller()) {
//					System.out.println(personel.getId());
//				}
				kursService.save(kurs);
			}

			List<SinavSonuc> sinavlar = listAles.getItems();
			sinavlar.addAll(listYdsYokdilKpds.getItems());
			for (SinavSonuc sinavSonuc : sinavlar) {
				sinavSonuc.setPersonel(newPersonel);
				sinavSonucService.save(sinavSonuc);
			}
			this.personel = newPersonel;
//			buttonDelete.setVisible(true);
//			buttonUpdate.setVisible(true);
		}
		new MessagePage().show(result);
	}
	
	public void deletePersonel(ActionEvent e) {
		if(this.personel == null) {
			new MessagePage().show(new Result(false, "Personel Bulunamadı."));
			return;
		}
		Result result = personelService.getById(this.personel.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Personel Bulunamadı. Sayfayı Yenileyiniz."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Tez Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			try {
				result = personelService.delete(personel);
			}
			catch(Exception ex) {
				
				new MessagePage().show(new ErrorResult("Veritabanı işleminde hata oldu. Sayfayı yenileyip tekrar deneyiniz."));
				return;
			}
//			Result result = personelService.delete(personel);
			if(result.isSuccess()) {
				this.personel = null;
//				buttonAdd.setDisable(true);
//				buttonDelete.setDisable(true);
//				buttonUpdate.setDisable(true);
				((Stage) buttonDelete.getScene().getWindow()).close();
			}
				
			new MessagePage().show(result);
		}
		
	}
	
	public void updatePersonel(ActionEvent e) {
		if (this.personel == null){
			new MessagePage().show(new ErrorResult("Personel Mevcut Değildir."));
			return;
		}
		setForm2Personel(this.personel);
		
		DataResult<Personel> result = personelService.save(this.personel);
		
		if(result.isSuccess()) {
			updatePersonelTez(this.personel);
			updatePersonelKurs(this.personel);
			
			
//
//			List<SinavSonuc> sinavlar = listAles.getItems();
//			sinavlar.addAll(listYdsYokdilKpds.getItems());
//			for (SinavSonuc sinavSonuc : sinavlar) {
//				sinavSonuc.setPersonel(this.personel);
//				sinavSonucService.save(sinavSonuc);
//			}
//			
		}
		new MessagePage().show(result);
	}

	private void updatePersonelKurs(Personel personel) {
		List<Kurs> kurslar = kursService.findByPersoneller_Id(personel.getId()).getData();
		
		List<Kurs> kurslarForm = new ArrayCopy().deepCopy(listKurs.getItems());
		boolean isFinded;
		for (Kurs kurs : kurslar) {
			isFinded = false;
			for (Kurs kursForm : listKurs.getItems()) {
				if(kursForm.getId() == kurs.getId()) {
					isFinded = true;
					kurslarForm.remove(kursForm);
					break;
				}
			}
			if(!isFinded) {
				kurs.getPersoneller().removeIf(p -> p.getId() == personel.getId());
				kursService.save(kurs);
			}
			
		}
		for (Kurs kurs : kurslarForm) {
			kurs.getPersoneller().add(personel);
			kursService.save(kurs);
		}
		
	}



	private void updatePersonelTez(Personel personel) {
		List<Integer> tezIds = new ArrayList<>();
		tezIds.add(Integer.MAX_VALUE);
		for (Tez tez : listTez.getItems())
			tezIds.add(tez.getId());
		tezService.deleteByPersonelAndIdNotIn(personel, tezIds);
		for (Tez tez : listTez.getItems()) {
			tez.setPersonel(personel);
			tezService.save(tez);
		}
		
	}



	private void setForm2Personel(Personel personel) {
		personel.setTcNo(txtFieldTcNo.getText());
		personel.setAd(txtFieldAd.getText());
		personel.setSoyad(txtFieldSoyad.getText());
		personel.setSicil(txtFieldSicil.getText());
		personel.setTel(txtFieldTel.getText());
		personel.setBirlik(cmBoxBirlik.getValue());
		personel.setRutbe(cmBoxRutbe.getValue());
		personel.setAciklama(txtFieldAciklama.getText());
		
		personel.setAciklama(txtFieldAciklama.getText());
		
	}



	public void addBirlik(ActionEvent e) {
//		Birlik birlik = cmBoxBirlik.getSelectionModel().getSelectedItem();
		new BirlikPage().show(null);
	}

	public void updateBirlik(ActionEvent e) {		
		Birlik birlik = cmBoxBirlik.getSelectionModel().getSelectedItem();
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
//	public void addSinif(ActionEvent e) {
//		Sinif sinif = cmBoxUnvan.getSelectionModel().getSelectedItem();
//		new SinifPage().show(sinif);
//	}

	public void addRutbe(ActionEvent e) {
//		Rutbe rutbe = cmBoxRutbe.getSelectionModel().getSelectedItem();
		new RutbePage().show(null);
	}

	public void updateRutbe(ActionEvent e) {
		Rutbe rutbe = cmBoxRutbe.getSelectionModel().getSelectedItem();
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
	
	public void addAles(ActionEvent e) {
		Sinav sinav = new Sinav();
		sinav.setSinavTuru(new SinavTuru(1, null, null));
		SinavSonuc sinavSonuc = new SinavSonuc();
		sinavSonuc.setSinav(sinav);
		sinavSonuc.setPersonel(this.personel);
		new SinavSonucPage().show(sinavSonuc);
	}

	public void updateAles(ActionEvent e) {		
		SinavSonuc sinavSonuc = listAles.getSelectionModel().getSelectedItem();
		if(sinavSonuc == null) {
			new MessagePage().show(new Result(false, "Bir Ales Sınav Sonucu Seçiniz."));
			return;
		}
		Result result = sinavSonucService.getById(sinavSonuc.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Ales Sınav Sonucu  Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		new SinavSonucPage().show(sinavSonuc);
	}
	
	public void deleteAles(ActionEvent e) {
		SinavSonuc sinavSonuc = listAles.getSelectionModel().getSelectedItem();
		
		if(sinavSonuc == null) {
			new MessagePage().show(new Result(false, "Bir Ales Sınav Sonucu Seçiniz."));
			return;
		}
		Result result = sinavSonucService.getById(sinavSonuc.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Ales Sınav Sonucu  Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Sınav Sonucu Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			result = sinavSonucService.delete(sinavSonuc);
			new MessagePage().show(result);
		}
	}
	
	public void addYdsYokdilKpds(ActionEvent e) {
		Sinav sinav = new Sinav();
		sinav.setSinavTuru(new SinavTuru(2, null, null));
		SinavSonuc sinavSonuc = new SinavSonuc();
		sinavSonuc.setSinav(sinav);
		sinavSonuc.setPersonel(this.personel);
		new SinavSonucPage().show(sinavSonuc);
	}

	public void updateYdsYokdilKpds(ActionEvent e) {
		SinavSonuc sinavSonuc = listYdsYokdilKpds.getSelectionModel().getSelectedItem();
		if(sinavSonuc == null) {
			new MessagePage().show(new Result(false, "Bir Yds Yokdil Kpds Sınav Sonucu Seçiniz."));
			return;
		}
		Result result = sinavSonucService.getById(sinavSonuc.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Yds Yokdil Kpds Sınav Sonucu  Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		new SinavSonucPage().show(sinavSonuc);
	}
	

	public void deleteYdsYokdilKpds(ActionEvent e) {
		SinavSonuc sinavSonuc = listYdsYokdilKpds.getSelectionModel().getSelectedItem();
		
		if(sinavSonuc == null) {
			new MessagePage().show(new Result(false, "Bir Yds Yokdil Kpds Sınav Sonucu Seçiniz."));
			return;
		}
		Result result = sinavSonucService.getById(sinavSonuc.getId());
		if(!result.isSuccess()) {
			new MessagePage().show(new ErrorResult("Bu Yds Yokdil Kpds Sınav Sonucu  Daha Önce Silinmiştir. Sayfayı Yenileyiniz."));
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Sınav Sonucu Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			result = sinavSonucService.delete(sinavSonuc);
			new MessagePage().show(result);
		}
	}
	
	public void addTez(ActionEvent e) {
		Tez tez = new Tez();
		tez.setPersonel(this.personel);
		new TezPage().show(tez);
	}
	
	public void updateTez(ActionEvent e) {
		Tez tez = listTez.getSelectionModel().getSelectedItem();
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
	
	public void updateKurs(ActionEvent e) {
		Kurs kurs = listKurs.getSelectionModel().getSelectedItem();
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
	
	public void addBelge(ActionEvent e) {
		Belge belge = new Belge();
		belge.setPersonel(this.personel);
		new BelgePage().show(belge);
	}
	
	public void updateBelge(ActionEvent e) {
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
		
		new BelgePage().show(belge);
	}
	
	public void deleteBelge(ActionEvent e) {
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
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Belge Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			result = belgeService.delete(belge);
			new MessagePage().show(result);
		}
	}
	
	public void addYayin(ActionEvent e) {
		Yayin yayin = new Yayin();
		yayin.getYazarlar().add(this.personel);
		new YayinPage().show(yayin);
	}
	
	public void updateYayin(ActionEvent e) {
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
		
		new YayinPage().show(yayin);
	}
	
	public void deleteYayin(ActionEvent e) {
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
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Yayın Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			result = yayinService.delete(yayin);
			new MessagePage().show(result);
		}
	}
	
	public void addKitap(ActionEvent e) {
		Kitap kitap = new Kitap();
		kitap.getYazarlar().add(this.personel);
		new KitapPage().show(kitap);
	}
	
	public void updateKitap(ActionEvent e) {
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
		
		new KitapPage().show(kitap);
	}
	
	public void deleteKitap(ActionEvent e) {
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
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Kitap Sil");
		alert.setContentText("Silmek istediğinizden emin misiniz?");
		alert.setHeaderText("Sil");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			result = kitapService.delete(kitap);
			new MessagePage().show(result);
		}
	}
}