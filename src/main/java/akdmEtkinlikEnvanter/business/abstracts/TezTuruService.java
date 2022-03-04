package akdmEtkinlikEnvanter.business.abstracts;

import java.util.List;

import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.TezTuru;

public interface TezTuruService {
	Result add(TezTuru tezTuru);
	DataResult<TezTuru> getById(int id);
	DataResult<List<TezTuru>> getAll();
}
