package desktopApp.utilities.excel.bussiness.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.BirlikService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import desktopApp.utilities.excel.bussiness.abstracts.BirlikExService;

@Service
public class BirlikParseManager implements BirlikExService{

	@Autowired
	BirlikService birlikService;
	
	public Birlik add(Birlik birlik) {
		if(birlik.getAd() == null)
			return birlik;
		DataResult<Birlik> result = birlikService.add(birlik);
		birlik.setAd(birlik.getAd().replace("\n", " "));
		return result.getData();
	}

}
