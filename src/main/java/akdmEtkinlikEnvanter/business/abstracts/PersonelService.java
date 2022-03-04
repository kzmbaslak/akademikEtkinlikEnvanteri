package akdmEtkinlikEnvanter.business.abstracts;

import java.util.List;

import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Personel;

public interface PersonelService {
	DataResult<Personel> add(Personel personel);
	DataResult<Personel> save(Personel personel);
	DataResult<Personel> getBySicilIgnoreCase(String sicil);
	DataResult<Personel> getById(int id);
	DataResult<List<Personel>> findAll();
	Result delete(Personel personel);
	DataResult<List<Personel>> getByBirlik(Birlik birlik);
	DataResult<Personel> getBySinavlar_Id(int sinavSonucId);
	DataResult<List<Personel>> findByAdContainingIgnoreCaseAndSoyadContainingIgnoreCaseAndSicilContainingIgnoreCaseAndTcNoContainingIgnoreCaseAndTelContainingIgnoreCaseAndAciklamaContainingIgnoreCaseAndBirlik_adContainingIgnoreCaseAndRutbe_adContainingIgnoreCase(String ad,String soyad, String sicil, String tcNo, String tel, String aciklama,String birlikAd, String rutbeAd);
	DataResult<List<Personel>> findByAdContainingIgnoreCase(String personelAd);
	DataResult<List<Personel>> findAllByOrderByAdAsc();
	DataResult<List<Personel>> findByIdNotInOrderByAdAsc(List<Integer> personellerId);
	DataResult<List<Personel>> getByYayinlar_IdOrderByAdAsc(int yayinId);
	DataResult<List<Personel>> getByKitaplar_IdOrderByAdAsc(int kitapId);
}
