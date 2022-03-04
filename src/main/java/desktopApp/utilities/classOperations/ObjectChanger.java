package desktopApp.utilities.classOperations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.KursService;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import desktopApp.ui.entities.PersonelWithAllObjects;

@Component
public class ObjectChanger {
	
	KursService kursService;
	
	
	@Autowired
	public ObjectChanger(KursService kursService) {
		super();
		this.kursService = kursService;
	}



	public List<PersonelWithAllObjects> personelList2PersonelWithObjects(List<Personel> personeller){
		if(personeller == null)
			return null;
		List<PersonelWithAllObjects> result = new ArrayList<>();
		for (Personel p : personeller) {
			PersonelWithAllObjects newPers = new PersonelWithAllObjects(p.getId(),p.getTcNo(),p.getAd(),p.getSoyad(),
					p.getSicil(),p.getTel(),p.getBirlik().getAd(),p.getRutbe().getAd(),
					p.getRutbe().getSinif().getAd(),null);
			
			
		//	new PersonelWithAllObjects(doktoraAlani, doktoraTezKonusu, doktoraUniversiteEnstitu, doktoraBitirdigiTarih, yuksekLisansAlani, yuksekLisansTezKonusu, yuksekLisansUniversiteEnstitu, yuksekLisansBitirdigiTarih, kurslar, ales, ydsYokdilKpds, aciklamalar)
			
//			tezler;
//			doktoraAlani;
//			doktoraTezKonusu;
//			doktoraUniversiteEnstitu;
//			doktoraBitirdigiTarih;
//			yuksekLisansAlani;
//			yuksekLisansTezKonusu;
//			yuksekLisansUniversiteEnstitu;
//			yuksekLisansBitirdigiTarih;
//			kurslar;
//			ales;
//			ydsYokdilKpds;
//			aciklamalar;
			//System.out.println(p.getSinavlar().toString());
			List<Kurs> kurslar = kursService.findByPersoneller_Id(p.getId()).getData();
			
			//System.out.println(p.getSinavNames(1)+"----------------------"+p.getAd());
			
			
			
			
			//sınav sonuçları
			//tezler
			
			result.add(newPers);
		}
		return result;
	}
}
