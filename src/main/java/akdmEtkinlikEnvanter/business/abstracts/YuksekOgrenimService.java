package akdmEtkinlikEnvanter.business.abstracts;

import java.util.List;

import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.YuksekOgrenim;

public interface YuksekOgrenimService {
	DataResult<YuksekOgrenim> add(YuksekOgrenim yuksekOgrenim);
	DataResult<YuksekOgrenim> save(YuksekOgrenim yuksekOgrenim);
	Result delete(YuksekOgrenim yuksekOgrenim);

	DataResult<List<YuksekOgrenim>> getAll();

	DataResult<YuksekOgrenim> getById(int id);

	DataResult<List<YuksekOgrenim>> getAllByOrderByAlanAdiAsc();
}
