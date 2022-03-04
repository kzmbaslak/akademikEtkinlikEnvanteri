package desktopApp.utilities.excel.bussiness.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.KursService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import desktopApp.utilities.excel.bussiness.abstracts.KursExService;
import desktopApp.utilities.textOperations.StringSpliter;

@Service
public class KursParseManager implements KursExService {

	@Autowired
	KursService kursService;

	@Override
	public Kurs add(Kurs kurs) {
		if(kurs == null)
			return null;
		kurs.setAd(kurs.getAd().replace("\n", " "));
		DataResult<Kurs> result = kursService.add(kurs);
		return result.getData();
	}

	@Override
	public List<Kurs> add(String kursAdi,Personel personel) {
		if(kursAdi == null)
			return null;
		StringSpliter stringSpliter = new StringSpliter();
		DataResult<List<String>> kursAdlari = stringSpliter.stringSplitForNumber(kursAdi);
		List<Kurs> result = new ArrayList<Kurs>();
		
		if(kursAdlari.isSuccess()) {
			for (String ad: kursAdlari.getData()) {
				ad = ad.replace("\n", " ");
				Kurs kurs = new Kurs(ad);
				if(personel != null) {
					kurs.getPersoneller().add(personel);
					personel.getKurslar().add(kurs);
					kurs = kursService.add(kurs,personel).getData();
				}
				else
					kurs = kursService.add(kurs).getData();
				result.add(kurs);
				
			}
			
			return result;
		}
		return null;
	}

	@Override
	public String getByPersonel(Personel personel) {
		List<Kurs> kurslar = kursService.findByPersoneller_Id(personel.getId()).getData();
		String resultKurslar = "";
		int counter = 0;
		for (Kurs kurs : kurslar) {
			resultKurslar += ++counter+"."+kurs.getAd()+"\n";
			
		}
		return resultKurslar;
	}
	
	
}
