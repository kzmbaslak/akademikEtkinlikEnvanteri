package akdmEtkinlikEnvanter.business.abstracts;

import java.util.List;

import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.BelgeTuru;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;

public interface BelgeTuruService {

	DataResult<BelgeTuru> add(BelgeTuru belgeTuru);
	DataResult<BelgeTuru> save(BelgeTuru belgeTuru);
	DataResult<BelgeTuru> getById(int belgeTuruId);
	DataResult<List<BelgeTuru>> getAll();
	Result delete(BelgeTuru belgeTuru);
}