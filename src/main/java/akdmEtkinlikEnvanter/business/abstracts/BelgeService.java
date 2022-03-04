package akdmEtkinlikEnvanter.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Belge;
import akdmEtkinlikEnvanter.entities.concretes.BelgeTuru;
import akdmEtkinlikEnvanter.entities.concretes.Personel;

public interface BelgeService {
	DataResult<Belge> add(Belge belge);
	DataResult<Belge> save(Belge belge);
	Result delete(Belge belge);
	DataResult<Belge> getById(int belgeId);
	DataResult<List<Belge>> getAll();
	DataResult<List<Belge>> findByPersonel(Personel personel);
	DataResult<List<Belge>> getByBelgeTuru(BelgeTuru belgeTuru);
	DataResult<List<Belge>> findByAdContainingIgnoreCaseAndBelgeTuru_adContainingAndPersonel_sicilContaining(String ad, String belgeTuruAd, String personelSicil);


}
