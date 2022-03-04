package akdmEtkinlikEnvanter.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Rutbe;
import akdmEtkinlikEnvanter.entities.concretes.Sinif;

public interface PersonelDao extends JpaRepository<Personel, Integer> {
	Personel getBySicilIgnoreCase(String sicil);
	Personel getBySicilIgnoreCaseAndRutbe_Sinif(String sicil, Sinif sinif);
	List<Personel> getByBirlik(Birlik birlik);
//	List<Personel> getByBelge(Belge belge);
	List<Personel> getByRutbe(Rutbe rutbe);
	Personel getBySinavlar_Id(int sinavSonucId);
	List<Personel> findByAdContainingIgnoreCaseAndSoyadContainingIgnoreCaseAndSicilContainingIgnoreCaseAndTcNoContainingIgnoreCaseAndTelContainingIgnoreCaseAndAciklamaContainingIgnoreCaseAndBirlik_adContainingIgnoreCaseAndRutbe_adContainingIgnoreCase(String ad,String soyad, String sicil, String tcNo, String tel, String aciklama,String birlikAd, String rutbeAd);
	List<Personel> findByAdContainingIgnoreCaseAndSoyadContainingIgnoreCaseAndSicilContainingIgnoreCaseAndTcNoContainingIgnoreCaseAndTelContainingIgnoreCaseAndAciklamaContainingIgnoreCase(String ad,String soyad, String sicil, String tcNo, String tel, String aciklama);
	List<Personel> findByAdContainingIgnoreCaseAndSoyadContainingIgnoreCaseAndSicilContainingIgnoreCaseAndTcNoContainingIgnoreCaseAndTelContainingIgnoreCaseAndAciklamaContainingIgnoreCaseAndBirlikIsNotNullAndRutbeIsNull(String ad,String soyad, String sicil, String tcNo, String tel, String aciklama);
	List<Personel> findByAdContainingIgnoreCaseAndSoyadContainingIgnoreCaseAndSicilContainingIgnoreCaseAndTcNoContainingIgnoreCaseAndTelContainingIgnoreCaseAndAciklamaContainingIgnoreCaseAndBirlikIsNullAndRutbeIsNotNull(String ad,String soyad, String sicil, String tcNo, String tel, String aciklama);
	List<Personel> findByAdContainingIgnoreCase(String personelAd);
	List<Personel> findAllByOrderByAdAsc();
	List<Personel> findByIdNotInOrderByAdAsc(List<Integer> kurslarId);
	List<Personel> getByYayinlar_IdOrderByAdAsc(int yayinId);
	List<Personel> getByKitaplar_IdOrderByAdAsc(int kitapId);
}
