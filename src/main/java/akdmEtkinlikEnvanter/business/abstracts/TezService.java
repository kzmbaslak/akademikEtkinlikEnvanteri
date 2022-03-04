package akdmEtkinlikEnvanter.business.abstracts;

import java.time.LocalDate;
import java.util.List;


import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Sinif;
import akdmEtkinlikEnvanter.entities.concretes.Tez;
import akdmEtkinlikEnvanter.entities.concretes.TezTuru;
import akdmEtkinlikEnvanter.entities.concretes.UniversiteEnstitu;
import akdmEtkinlikEnvanter.entities.concretes.YuksekOgrenim;

public interface TezService {
	DataResult<Tez> add(Tez tez);
	DataResult<Tez> save(Tez tez);
	DataResult<List<Tez>> findAll();
	DataResult<Tez> findById(int id);
	DataResult<List<Tez>> findByPersonel(Personel personel);
	DataResult<List<Tez>> findByPersonelNot(Personel personel);
	DataResult<List<Tez>> findByPersonelAndTezTuruId(Personel personel, int tezTuruId);
	Result deleteByPersonelAndIdNotIn(Personel personel,List<Integer> tezIds);
	DataResult<List<Tez>> findByPersonelAndIdNotIn(Personel personel,List<Integer> tezIds);
	Result delete(Tez tez);
	DataResult<List<Tez>> findByPersonelNotAndTezKonusuContainingIgnoreCase(Personel personel, String tezKonusu);
	DataResult<List<Tez>> findByTezKonusuContainingIgnoreCase(String tezKonusu);
	
	DataResult<List<Integer>> getBitirdigiTarihGroupBy();
	DataResult<Integer> countByBitirdigiTarih(int year);

	DataResult<List<Tez>> findByUniversiteEnstitu(UniversiteEnstitu universiteEnstitu);
	DataResult<List<Tez>> findByYuksekOgrenim(YuksekOgrenim yuksekOgrenim);
	DataResult<List<Tez>> findByBitirdigiTarihBetweenAndTezKonusuContainingIgnoreCaseAndTezTuru_adContainingIgnoreCaseAndYuksekOgrenim_alanAdiContainingIgnoreCaseAndUniversiteEnstitu_adContainingIgnoreCaseAndPersonel_sicilContainingIgnoreCase(
			LocalDate from, LocalDate to, String tezKonusu, String tezTuruAd, String yuksekOgrenimAlanAd, String uniEnstAd, String personelSicil);

	DataResult<Long> countByPersonel_birlikAndPersonel_rutbe_sinifAndTezTuruAndBitirdigiTarihNull(Birlik birlik, Sinif sinif, TezTuru tezTuru);
	DataResult<Long> countByPersonel_birlikAndPersonel_rutbe_sinifAndTezTuruAndBitirdigiTarihNotNull(Birlik birlik, Sinif sinif, TezTuru tezTuru);
}
