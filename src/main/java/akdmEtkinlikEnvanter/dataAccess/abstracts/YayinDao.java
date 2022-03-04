package akdmEtkinlikEnvanter.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import akdmEtkinlikEnvanter.entities.concretes.Yayin;
import akdmEtkinlikEnvanter.entities.concretes.YayineviDergi;

public interface YayinDao extends JpaRepository<Yayin, Integer>  {
	Yayin getByAdIgnoreCase(String ad);
	List<Yayin> findByYazarlar_Id(int yazarId);
	Yayin getByAdAndYazarlar_Id(String ad, int personelId);
	List<Yayin> getByYayineviDergi(YayineviDergi yayineviDergi);
	List<Yayin> findByYayinTarihiBetweenAndAdContainingIgnoreCase(LocalDate from, LocalDate to, String ad);
	List<Yayin> findByAdContainingIgnoreCaseAndYayineviDergi_ad(String ad, String yayineviDergiAd);
	List<Yayin> findByAdContainingIgnoreCase(String ad);
	List<Yayin> findByYayinTarihiBetweenAndAdContainingIgnoreCaseAndYayineviDergi_ad(LocalDate from, LocalDate to, String ad, String yayineviDergiAd);
	
	
}
