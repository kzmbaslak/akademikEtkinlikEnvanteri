package desktopApp.utilities.excel.bussiness.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.KitapService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Kitap;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import desktopApp.utilities.excel.bussiness.abstracts.KitapExService;
import desktopApp.utilities.textOperations.StringSpliter;

@Service
public class KitapParseManager implements KitapExService {

	@Autowired
	KitapService kitapService;

	@Override
	public Kitap add(Kitap kitap) {
		if(kitap == null)
			return null;
		kitap.setAd(kitap.getAd().replace("\n", " "));
		DataResult<Kitap> result = kitapService.add(kitap);
		return result.getData();
	}

	@Override
	public List<Kitap> add(String kitapAdi,Personel personel) {
		if(kitapAdi == null)
			return null;
		StringSpliter stringSpliter = new StringSpliter();
		DataResult<List<String>> kursAdlari = stringSpliter.stringSplitForNumber(kitapAdi);
		List<Kitap> result = new ArrayList<Kitap>();
		
		if(kursAdlari.isSuccess()) {
			for (String ad: kursAdlari.getData()) {
				ad = ad.replace("\n", " ");
				Kitap kitap = new Kitap(ad);
				if(personel != null) {
					kitap.getYazarlar().add(personel);
					personel.getKitaplar().add(kitap);
					kitap = kitapService.add(kitap,personel).getData();
				}
				else
					kitap = kitapService.add(kitap).getData();
				result.add(kitap);
				
			}
			
			return result;
		}
		return null;
	}

	@Override
	public String getByPersonel(Personel personel) {
		List<Kitap> kitaplar = kitapService.findByYazarlar_Id(personel.getId()).getData();
		String resultKitaplar = "";
		int counter = 0;
		for (Kitap kitap : kitaplar) {
			resultKitaplar += ++counter+"."+kitap.getAd()+"\n";
			
		}
		return resultKitaplar;
	}

	
	
	
}
