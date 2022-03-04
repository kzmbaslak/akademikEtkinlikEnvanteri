package akdmEtkinlikEnvanter.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import akdmEtkinlikEnvanter.entities.concretes.BelgeTuru;

public interface BelgeTuruDao extends JpaRepository<BelgeTuru, Integer>{
	BelgeTuru getByAdIgnoreCase(String ad);
}
