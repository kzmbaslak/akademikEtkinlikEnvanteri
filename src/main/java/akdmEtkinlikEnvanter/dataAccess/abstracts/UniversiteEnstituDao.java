package akdmEtkinlikEnvanter.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import akdmEtkinlikEnvanter.entities.concretes.UniversiteEnstitu;

public interface UniversiteEnstituDao extends JpaRepository<UniversiteEnstitu, Integer> {
	UniversiteEnstitu getByAd(String ad);
	List<UniversiteEnstitu> findByAdContainingIgnoreCase(String ad);
	List<UniversiteEnstitu> getAllByOrderByAdAsc();
}
