package desktopApp.utilities.excel.bussiness.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.UniversiteEnstituService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.entities.concretes.UniversiteEnstitu;
import desktopApp.utilities.excel.bussiness.abstracts.UniversiteEnstituExService;
import desktopApp.utilities.textOperations.StringSpliter;

@Service
public class UniversiteEnstituParseManager implements UniversiteEnstituExService{

	@Autowired
	UniversiteEnstituService universiteEnstituService;
	
	@Override
	public List<UniversiteEnstitu> add(String universiteEnstituAd) {
		StringSpliter stringSpliter = new StringSpliter();
		DataResult<List<String>> adlar = stringSpliter.stringSplitForNumber(universiteEnstituAd);
		List<UniversiteEnstitu> result = new ArrayList<>();
		
		if(adlar.isSuccess()) {
			for (String ad: adlar.getData()) {
				ad = ad.replace("\n", " ");
				UniversiteEnstitu temp = new UniversiteEnstitu(ad);
				temp = universiteEnstituService.add(temp).getData();
				result.add(temp);
				
			}
			
			return result;
		}
		return null;
		
	}

}
