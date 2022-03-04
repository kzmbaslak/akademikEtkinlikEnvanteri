package akdmEtkinlikEnvanter.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import akdmEtkinlikEnvanter.entities.concretes.YuksekOgrenim;

public interface YuksekOgrenimDao extends JpaRepository<YuksekOgrenim, Integer> {
	YuksekOgrenim getByAlanAdi(String alanAdi);
	List<YuksekOgrenim> getAllByOrderByAlanAdiAsc();
}
