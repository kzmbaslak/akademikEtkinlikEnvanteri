package desktopApp.utilities.excel.bussiness.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.KursService;
import akdmEtkinlikEnvanter.business.abstracts.YayinService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.entities.concretes.Kitap;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Yayin;
import desktopApp.utilities.excel.bussiness.abstracts.KursExService;
import desktopApp.utilities.excel.bussiness.abstracts.YayinExService;
import desktopApp.utilities.textOperations.StringSpliter;

@Service
public class YayinParseManager implements YayinExService {

	@Autowired
	YayinService yayinService;

	@Override
	public Yayin add(Yayin yayin) {
		if(yayin == null)
			return null;
		yayin.setAd(yayin.getAd().replace("\n", " "));
		DataResult<Yayin> result = yayinService.add(yayin);
		return result.getData();
	}

	@Override
	public List<Yayin> add(String yayinAdi,Personel personel) {
		if(yayinAdi == null)
			return null;
		StringSpliter stringSpliter = new StringSpliter();
		DataResult<List<String>> kursAdlari = stringSpliter.stringSplitForNumber(yayinAdi);
		List<Yayin> result = new ArrayList<Yayin>();
		
		if(kursAdlari.isSuccess()) {
			for (String ad: kursAdlari.getData()) {
				ad = ad.replace("\n", " ");
				Yayin yayin = new Yayin(ad);
				if(personel != null) {
					yayin.getYazarlar().add(personel);
					personel.getYayinlar().add(yayin);
					yayin = yayinService.add(yayin,personel).getData();
				}
				else
					yayin = yayinService.add(yayin).getData();
				result.add(yayin);
				
			}
			
			return result;
		}
		return null;
	}

	@Override
	public String getByPersonel(Personel personel) {
		List<Yayin> yayinlar = yayinService.findByYazarlar_Id(personel.getId()).getData();
		String resultYayinlar = "";
		int counter = 0;
		for (Yayin yayin: yayinlar) {
			resultYayinlar += ++counter+"."+yayin.getAd()+"\n";
			
		}
		return resultYayinlar;
	}

	
	
	
}
