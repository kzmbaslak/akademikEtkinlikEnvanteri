package akdmEtkinlikEnvanter.business.abstracts;

import java.util.List;

import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Rutbe;
public interface RutbeService {
	DataResult<Rutbe> add(Rutbe rutbe);
	DataResult<Rutbe> save(Rutbe rutbe);
	DataResult<List<Rutbe>> getAll();
	Result delete(Rutbe rutbe);
	DataResult<List<Rutbe>> findByAdContainingIgnoreCase(String ad);
	DataResult<List<Rutbe>> findByAdContainingAndSinif_ad(String ad, String sinifAd);
	DataResult<Rutbe> getById(int rutbeId);
	DataResult<List<Rutbe>> findBySinif_id(int sinifId);

}
