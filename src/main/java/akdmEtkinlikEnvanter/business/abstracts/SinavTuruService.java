package akdmEtkinlikEnvanter.business.abstracts;

import java.util.List;

import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.entities.concretes.SinavTuru;

public interface SinavTuruService {
	DataResult<SinavTuru> add(SinavTuru sinavTuru);
	DataResult<SinavTuru> getById(int id);
	DataResult<List<SinavTuru>> getAll();
}
