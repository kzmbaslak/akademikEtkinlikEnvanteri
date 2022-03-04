package akdmEtkinlikEnvanter.business.abstracts;

import java.util.List;

import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Personel;

public interface KursService {
	DataResult<Kurs> add(Kurs kurs);
	DataResult<Kurs> add(Kurs kurs, Personel personel);
	DataResult<Kurs> save(Kurs kurs);
	DataResult<Kurs> findById(int id);
	DataResult<List<Kurs>> findAll();
	DataResult<List<Kurs>> getByPersoneller_IdOrderByAdAsc(int personelId);
	DataResult<List<Kurs>> findByPersoneller_Id(int personelId);
	DataResult<List<Kurs>> findByIdNotIn(List<Integer> kurslarId);
	DataResult<List<Kurs>> findByIdNotInAndAdContainingIgnoreCaseOrderByAdAsc(List<Integer> kurslarId, String ad);
	Result delete(Kurs kurs);
	DataResult<List<Kurs>> findByAdContainingIgnoreCase(String ad);
	DataResult<List<Kurs>> findAllByOrderByAdAsc();
}
