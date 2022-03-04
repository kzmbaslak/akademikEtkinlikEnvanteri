package desktopApp.utilities.excel.bussiness.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.RutbeService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.entities.concretes.Rutbe;
import desktopApp.utilities.excel.bussiness.abstracts.RutbeExService;

@Service
public class RutbeParseManager implements RutbeExService{

	@Autowired
	RutbeService rutbeService;
	
	@Override
	public Rutbe add(Rutbe rutbe) {
		DataResult<Rutbe> result = rutbeService.add(rutbe);
		if(result != null) {
			return result.getData();
		}
		return null;
	}

}
