package akdmEtkinlikEnvanter.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import akdmEtkinlikEnvanter.entities.concretes.Kitap;
import akdmEtkinlikEnvanter.entities.concretes.Yayin;
import akdmEtkinlikEnvanter.entities.concretes.YayineviDergi;


public interface KitapDao extends JpaRepository<Kitap, Integer>  {
	Kitap getByYayineviDergiAndAdIgnoreCase(YayineviDergi yayineviDergi, String ad);
	List<Kitap> findByYazarlar_Id(int yazarId);
	Kitap getByAdAndYazarlar_Id(String ad, int personelId);
	List<Kitap> getByYayineviDergi(YayineviDergi yayineviDergi);
	List<Kitap> findByYayinTarihiBetweenAndAdContainingIgnoreCaseAndYayineviDergi_adContaining(LocalDate from, LocalDate to, String ad, String yayineviDergiAd);
	List<Kitap> findByAdContainingIgnoreCaseAndYayineviDergi_ad(String ad, String yayineviDergiAd);
	List<Kitap> findByAdContainingIgnoreCaseAndYayineviDergi_adContaining(String ad, String yayineviDergiAd);
	List<Kitap> findByYayinTarihiBetweenAndAdContainingIgnoreCaseAndYayineviDergi_ad(LocalDate from, LocalDate to, String ad, String yayineviDergiAd);
}
