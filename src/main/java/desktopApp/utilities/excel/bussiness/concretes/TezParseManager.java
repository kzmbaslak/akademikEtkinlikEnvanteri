package desktopApp.utilities.excel.bussiness.concretes;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.TezService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Tez;
import akdmEtkinlikEnvanter.entities.concretes.TezTuru;
import desktopApp.utilities.dateOperations.TextToDateConverter;
import desktopApp.utilities.excel.bussiness.abstracts.TezExService;
import desktopApp.utilities.textOperations.StringSpliter;

@Service
public class TezParseManager implements TezExService{

	@Autowired
	TezService tezService;
	
	@Override
	public Tez add(Tez tez) {
		DataResult<Tez> result = tezService.add(tez);
		return result.getData();
	}

	@Override
	public List<Tez> add(List<Tez> tezler, String tezKonusu, String tezBitirmeTarihleri) {
		StringSpliter stringSpliter = new StringSpliter();
		DataResult<List<String>> konular = stringSpliter.stringSplitForNumber(tezKonusu);
		DataResult<List<String>> bitirmeTarihleri = stringSpliter.stringSplitForNumber(tezBitirmeTarihleri);
		
		//System.out.println(konular.getData());
		Iterator<String> k = null;
		Iterator<String> bT = null;
		
		if(konular.isSuccess())
			k = konular.getData().iterator();
		if(bitirmeTarihleri.isSuccess())
			bT = bitirmeTarihleri.getData().iterator();
		
		TextToDateConverter textToDateConverter = new TextToDateConverter();
//		if(konular.isSuccess()) {

			for (Tez tez : tezler) {
				
				if(k != null)
					tez.setTezKonusu((k.hasNext())?k.next():"!");
				else {
					tez.setTezKonusu("!");
				}
				if(bT != null)
					tez.setBitirdigiTarih(textToDateConverter.convertText2Date((bT.hasNext())?bT.next():null));
				
				tez = tezService.add(tez).getData();
			}
			return tezler;
			
//		}
//		else
//			return null;
	}

	@Override
	public String[] getByPersonelAndTezTuruId(Personel personel, int tezTuruId) {
		String[] resultTezler = new String[] {"","","",""};
		
		List<Tez> tezler = tezService.findByPersonelAndTezTuruId(personel, tezTuruId).getData();
//		List<Tez> tezler = tezService.findByPersonel(personel).getData();
		Iterator<Tez> tezlerItrtr = tezler.iterator();
		int counter = 0;
		while (tezlerItrtr.hasNext()) {
			Tez tez = tezlerItrtr.next();
			if(tez.getTezTuru().getId() == tezTuruId) {
				resultTezler[0] += ++counter+"."+tez.getYuksekOgrenim().getAlanAdi()+(tezlerItrtr.hasNext()?"\n":"");
				resultTezler[1] += counter+"."+tez.getTezKonusu()+(tezlerItrtr.hasNext()?"\n":"");
				System.out.println(tez.getUniversiteEnstitu()+"         "+tez.getTezKonusu());
				resultTezler[2] += counter+"."+tez.getUniversiteEnstitu().getAd()+(tezlerItrtr.hasNext()?"\n":"");
				if(tez.getBitirdigiTarih() != null)
					resultTezler[3] += counter+"."+tez.getBitirdigiTarih().getYear()+(tezlerItrtr.hasNext()?"\n":"");
				else
					resultTezler[3] += counter+".DEVAM";
			}
		}
		return resultTezler;
	}

}
