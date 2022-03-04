package akdmEtkinlikEnvanter.business.abstracts;

import java.util.List;

import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;

public interface BirlikService{	
	DataResult<Birlik> add(Birlik birlik);
	DataResult<Birlik> save(Birlik birlik);
	DataResult<Birlik> getById(int birlikId);
	DataResult<List<Birlik>> getAll();
	DataResult<Birlik> getByPersonelId(int personelId);
	DataResult<Birlik> getByAd(String ad);
	Result delete(Birlik birlik);
	DataResult<List<Birlik>> findByAdLike(String ad);
	DataResult<List<Birlik>> findByAdContainingIgnoreCase(String ad);
	DataResult<List<Birlik>> findByAdContainingIgnoreCaseOrderByAdAsc(String ad);
	DataResult<List<Birlik>> findAllByOrderByAdAsc();
}
                          