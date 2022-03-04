package desktopApp.utilities.excel.bussiness.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.PersonelService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import desktopApp.utilities.excel.bussiness.abstracts.PersonelExService;

@Service
public class PersonelParseManager implements PersonelExService{

	@Autowired
	PersonelService personelService;
	
	@Override
	public DataResult<Personel> add(Personel personel) {
		DataResult<Personel> result = personelService.add(personel);
		return result;
	}

	@Override
	public Personel save(Personel personel) {
		DataResult<Personel> result = personelService.save(personel);
		return result.getData();
	}

}
