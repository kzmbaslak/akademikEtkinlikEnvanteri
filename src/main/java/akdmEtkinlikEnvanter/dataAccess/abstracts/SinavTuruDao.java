package akdmEtkinlikEnvanter.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import akdmEtkinlikEnvanter.entities.concretes.SinavTuru;

public interface SinavTuruDao extends JpaRepository<SinavTuru, Integer> {
	public SinavTuru getByAd(String ad);
}
