package akdmEtkinlikEnvanter.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Sinif;
import akdmEtkinlikEnvanter.entities.concretes.Tez;
import akdmEtkinlikEnvanter.entities.concretes.TezTuru;
import akdmEtkinlikEnvanter.entities.concretes.UniversiteEnstitu;
import akdmEtkinlikEnvanter.entities.concretes.YuksekOgrenim;

public interface TezDao extends JpaRepository<Tez, Integer> {
	Tez getByTezKonusu(String tezKonusu);
	Tez getByTezKonusuIgnoreCase(String tezKonusu);
	Tez getByTezKonusuAndPersonel(String tezKonusu,Personel personel);
	Tez getByTezKonusuAndPersonelAndYuksekOgrenim(String tezKonusu,Personel personel,YuksekOgrenim yuksekOgrenim);
	List<Tez> findByPersonel(Personel personel);
	List<Tez> findByPersonelNot(Personel personel);
	List<Tez> findByPersonelAndTezTuruId(Personel personel, int tezTuruId);
	void deleteByPersonelAndIdNotIn(Personel personel,List<Integer> tezIds);
	List<Tez> findByPersonelAndIdNotIn(Personel personel,List<Integer> tezIds);
	List<Tez> findByPersonelNotAndTezKonusuContainingIgnoreCase(Personel personel, String tezKonusu);
	List<Tez> findByTezKonusuContainingIgnoreCase(String tezKonusu);
	
	@Query("select extract(year from t.bitirdigiTarih) as bT from Tez t group by extract(year from t.bitirdigiTarih) order by bT asc") 
	List<Integer> getBitirdigiTarihGroupBy();
	
	@Query("select count(t) from Tez t where extract(year from t.bitirdigiTarih)=:year")
	int countByBitirdigiTarih(@Param("year")int year);
	
	List<Tez> findByUniversiteEnstitu(UniversiteEnstitu universiteEnstitu);
	List<Tez> findByYuksekOgrenim(YuksekOgrenim yuksekOgrenim);
	
	List<Tez> findByBitirdigiTarihBetweenAndTezKonusuContainingIgnoreCaseAndTezTuru_adContainingIgnoreCaseAndYuksekOgrenim_alanAdiContainingIgnoreCaseAndUniversiteEnstitu_adContainingIgnoreCaseAndPersonel_sicilContainingIgnoreCase(
			LocalDate from, LocalDate to, String tezKonusu, String tezTuruAd, String yuksekOgrenimAlanAd, String uniEnstAd, String personelSicil);
	List<Tez> findByTezKonusuContainingIgnoreCaseAndTezTuru_adContainingIgnoreCaseAndYuksekOgrenim_alanAdiContainingIgnoreCaseAndUniversiteEnstitu_adContainingIgnoreCaseAndPersonel_sicilContainingIgnoreCase(
			String tezKonusu, String tezTuruAd, String yuksekOgrenimAlanAd, String uniEnstAd, String personelSicil);
	List<Tez> findByBitirdigiTarihBetweenAndTezKonusuContainingIgnoreCaseAndTezTuru_adContainingIgnoreCaseAndYuksekOgrenim_alanAdiAndUniversiteEnstitu_adContainingIgnoreCaseAndPersonel_sicilContainingIgnoreCase(
			LocalDate from, LocalDate to, String tezKonusu, String tezTuruAd, String yuksekOgrenimAlanAd, String uniEnstAd, String personelSicil);
	List<Tez> findByTezKonusuContainingIgnoreCaseAndTezTuru_adContainingIgnoreCaseAndYuksekOgrenim_alanAdiAndUniversiteEnstitu_adContainingIgnoreCaseAndPersonel_sicilContainingIgnoreCase(
			String tezKonusu, String tezTuruAd, String yuksekOgrenimAlanAd, String uniEnstAd, String personelSicil);
	Long countByPersonel_birlikAndPersonel_rutbe_sinifAndTezTuruAndBitirdigiTarihNull(Birlik birlik, Sinif sinif, TezTuru tezTuru);
	Long countByPersonel_birlikAndPersonel_rutbe_sinifAndTezTuruAndBitirdigiTarihNotNull(Birlik birlik, Sinif sinif, TezTuru tezTuru);
}
