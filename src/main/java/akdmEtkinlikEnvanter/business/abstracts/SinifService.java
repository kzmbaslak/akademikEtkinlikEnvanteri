package akdmEtkinlikEnvanter.business.abstracts;

import java.util.List;

import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Sinif;

public interface SinifService {
	DataResult<Sinif> add(Sinif sinif);
	DataResult<Sinif> save(Sinif sinif);
	DataResult<Sinif> findByAd(String ad);
	DataResult<List<Sinif>> getAll();
	Result delete(Sinif sinif);
	DataResult<Sinif> getById(int sinifId);
	DataResult<Long> count();
}
