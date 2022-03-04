package akdmEtkinlikEnvanter.business.abstracts;

import java.util.List;

import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Sinav;
import akdmEtkinlikEnvanter.entities.concretes.SinavSonuc;

public interface SinavSonucService {
	DataResult<SinavSonuc> add(SinavSonuc sinavSonuc);
	DataResult<SinavSonuc> save(SinavSonuc sinavSonuc);
	DataResult<List<SinavSonuc>> getByPersonelAndSinav_SinavTuru_Id(Personel personel, int sinavTuruId);
	DataResult<List<SinavSonuc>> getByPersonel(Personel personel);
	Result delete(SinavSonuc sinavSonuc);
	DataResult<List<SinavSonuc>> getBySinav(Sinav sinav);
	DataResult<SinavSonuc> getById(int sinavSonucId);
	
}
