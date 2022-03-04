package desktopApp.utilities.excel.bussiness.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.SinifService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.entities.concretes.Sinif;
import desktopApp.utilities.excel.bussiness.abstracts.SinifExService;

@Service
public class SinifParseManager implements SinifExService{
	
	@Autowired
	SinifService sinifService;
	
	
	public Sinif add(Sinif sinif) {
		DataResult<Sinif> result = sinifService.add(sinif);
		return result.getData();
	}
}
