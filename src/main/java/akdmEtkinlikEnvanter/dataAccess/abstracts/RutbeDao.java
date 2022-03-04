package akdmEtkinlikEnvanter.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import akdmEtkinlikEnvanter.entities.concretes.Rutbe;

public interface RutbeDao extends JpaRepository<Rutbe, Integer> {
	Rutbe getByAd(String ad);
	List<Rutbe> findByAdContainingIgnoreCase(String ad);
	List<Rutbe> findByAdContainingIgnoreCaseAndSinif_ad(String ad, String sinifAd);
	List<Rutbe> findBySinif_id(int sinifId);
	
}
