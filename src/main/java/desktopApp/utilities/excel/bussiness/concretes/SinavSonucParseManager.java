package desktopApp.utilities.excel.bussiness.concretes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.SinavService;
import akdmEtkinlikEnvanter.business.abstracts.SinavSonucService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Sinav;
import akdmEtkinlikEnvanter.entities.concretes.SinavSonuc;
import desktopApp.utilities.dateOperations.TextToDateConverter;
import desktopApp.utilities.excel.bussiness.abstracts.SinavSonucExService;
import desktopApp.utilities.textOperations.StringSpliter;

@Service
public class SinavSonucParseManager implements SinavSonucExService{

	@Autowired
	SinavSonucService sinavSonucService;
	
	@Autowired
	SinavService sinavService;
	
	@Override
	public SinavSonuc add(SinavSonuc sinavSonuc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SinavSonuc> add(String sinavVeTarih, Sinav sinav, Personel personel) {
		StringSpliter stringSpliter = new StringSpliter();
		DataResult<HashMap<String, String>> data = stringSpliter.stringSplitForExample(sinavVeTarih);
		TextToDateConverter textToDateConverter = new TextToDateConverter();
		
		List<SinavSonuc> result = new ArrayList<>();
		if(data.isSuccess()) {
			

			for (Entry<String, String> entry : data.getData().entrySet()) {
//				System.out.println(entry.getKey()+"    "+entry.getValue());
				LocalDate date = textToDateConverter.convertText2Date(entry.getKey());
				Double temp = null;
				if(entry.getKey().length() < 4) {
					Double biggest = 0.0;
					for (String year : data.getData().keySet()) {
						
						temp = Double.parseDouble(data.getData().get(year));
						if(temp > biggest) {
							biggest = temp;
							
						}
						if(year.length() == 4) {
							date = textToDateConverter.convertText2Date(year);
							
						}
					}
				}
				
				sinav.setTarih(date);
				sinav.setId(0);
				DataResult<Sinav> sinavTmp = sinavService.add(sinav);
				if(sinavTmp.getData() != null) {
					SinavSonuc sinavSonuc;
					if(temp != null) {
						sinavSonuc = new SinavSonuc(0,temp, personel, sinavTmp.getData());
					}
					else {
						sinavSonuc = new SinavSonuc(0,Double.parseDouble(entry.getValue()), personel, sinavTmp.getData());
					}
					sinavSonucService.add(sinavSonuc);
					result.add(sinavSonuc);
				}
//				else {
//					System.out.println(tmp.getMessage());
//				}
				//System.out.println(entry.getKey()+" - "+entry .getValue());
			}
			return result;
		}
//		else{
//			System.out.println(data.getMessage());
//		}
		
		return null;
	}

	@Override
	public String getByPersonelAndSinavTuruId(Personel personel, int sinavTuruId) {
		List<SinavSonuc> sinavSonuclari = sinavSonucService.getByPersonelAndSinav_SinavTuru_Id(personel, sinavTuruId).getData();
		String resultSinavSonuclari = "";
		Iterator<SinavSonuc> sinavSonuclariItrtr = sinavSonuclari.iterator();
		while (sinavSonuclariItrtr.hasNext()) {
			SinavSonuc sinavSonuc = sinavSonuclariItrtr.next();
			if(sinavSonuc.getSinav().getSinavTuru().getId() == sinavTuruId) {
				resultSinavSonuclari += sinavSonuc.getSinavNot()+" (";
				if(sinavSonuc.getSinav().getTarih() != null)
					resultSinavSonuclari += sinavSonuc.getSinav().getTarih().getYear()+")"+(sinavSonuclariItrtr.hasNext()?"\n":"");
				else
					resultSinavSonuclari += ")"+(sinavSonuclariItrtr.hasNext()?"\n":"");
					
				resultSinavSonuclari += sinavSonuc.getSinavNot()+")"+(sinavSonuclariItrtr.hasNext()?"\n":"");
			}
		}
		return resultSinavSonuclari;
	}

}
