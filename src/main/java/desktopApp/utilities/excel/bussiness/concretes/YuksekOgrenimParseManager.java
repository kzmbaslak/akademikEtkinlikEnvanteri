package desktopApp.utilities.excel.bussiness.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.YuksekOgrenimService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.entities.concretes.YuksekOgrenim;
import desktopApp.utilities.excel.bussiness.abstracts.YuksekOgrenimExService;
import desktopApp.utilities.textOperations.StringSpliter;

@Service
public class YuksekOgrenimParseManager implements YuksekOgrenimExService{

	@Autowired
	YuksekOgrenimService yuksekOgrenimService;
	
	@Override
	public List<YuksekOgrenim> add(String alanAdi) {
		
		StringSpliter stringSpliter = new StringSpliter();
		DataResult<List<String>> alanAdlari = stringSpliter.stringSplitForNumber(alanAdi);
		List<YuksekOgrenim> result = new ArrayList<>();
		
		if(alanAdlari.isSuccess()) {
			for (String alnAdi: alanAdlari.getData()) {
				alnAdi = alnAdi.replace("\n", " ");
				YuksekOgrenim temp = new YuksekOgrenim(alnAdi);
				temp = yuksekOgrenimService.add(temp).getData();
				result.add(temp);
				
			}
			return result;
		}
		return null;
	}
}
