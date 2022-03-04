package akdmEtkinlikEnvanter.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import akdmEtkinlikEnvanter.entities.concretes.Sinav;
import akdmEtkinlikEnvanter.entities.concretes.SinavTuru;

public interface SinavDao extends JpaRepository<Sinav, Integer> {

	public Sinav getByTarihAndSinavTuru(LocalDate tarih, SinavTuru sinavTuru);
	public List<Sinav> findBySinavTuru(SinavTuru sinavTuru);
//	public Sinav getById(int sinavId);
	public List<Sinav>  findByTarihBetweenAndSinavTuru_AdContainingIgnoreCase(LocalDate from, LocalDate to, String sinavTuru);

	public List<Sinav> findBySinavTuru_adContainingIgnoreCase(String sinavTuruAd);
}
