package akdmEtkinlikEnvanter.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import akdmEtkinlikEnvanter.entities.concretes.Sinif;

public interface SinifDao extends JpaRepository<Sinif, Integer> {
	Sinif getByAdIgnoreCase(String ad);
}
