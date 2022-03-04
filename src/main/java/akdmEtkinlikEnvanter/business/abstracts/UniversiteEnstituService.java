package akdmEtkinlikEnvanter.business.abstracts;

import java.util.List;

import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.UniversiteEnstitu;

public interface UniversiteEnstituService {
	DataResult<UniversiteEnstitu> add(UniversiteEnstitu universiteEnstitu);
	DataResult<UniversiteEnstitu> save(UniversiteEnstitu universiteEnstitu);
	Result delete(UniversiteEnstitu universiteEnstitu);
	DataResult<List<UniversiteEnstitu>> getAll();
	DataResult<UniversiteEnstitu> getById(int id);
	DataResult<List<UniversiteEnstitu>> findByAdContainingIgnoreCase(String ad);
	DataResult<List<UniversiteEnstitu>> getAllByOrderByAdAsc();

}
