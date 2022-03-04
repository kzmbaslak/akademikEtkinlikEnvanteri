package akdmEtkinlikEnvanter.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Sinav;
import akdmEtkinlikEnvanter.entities.concretes.SinavTuru;

public interface SinavService {
	DataResult<Sinav> add(Sinav sinav);
	DataResult<Sinav> save(Sinav sinav);
	Result delete(Sinav sinav);
	DataResult<List<Sinav>> findAll();
	DataResult<List<Sinav>> findBySinavTuru(SinavTuru sinavTuru);
	DataResult<Sinav> getById(int sinavId);
	DataResult<List<Sinav>>  findByTarihBetweenAndSinavTuru_AdContainingIgnoreCase(LocalDate from, LocalDate to, String sinavTuru);
}
