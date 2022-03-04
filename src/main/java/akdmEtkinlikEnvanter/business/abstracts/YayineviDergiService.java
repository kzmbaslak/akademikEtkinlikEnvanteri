package akdmEtkinlikEnvanter.business.abstracts;

import java.util.List;

import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.YayineviDergi;

public interface YayineviDergiService {
	DataResult<YayineviDergi> add(YayineviDergi yayineviDergi);
	DataResult<YayineviDergi> save(YayineviDergi yayineviDergi);
	Result delete(YayineviDergi yayineviDergi);
	DataResult<YayineviDergi> getById(int yayineviDergiId);
	DataResult<List<YayineviDergi>> getAll();
	DataResult<List<YayineviDergi>> findAllByOrderByAdAsc();
	DataResult<List<YayineviDergi>> getByAdContainingIgnoreCase(String ad);
}
