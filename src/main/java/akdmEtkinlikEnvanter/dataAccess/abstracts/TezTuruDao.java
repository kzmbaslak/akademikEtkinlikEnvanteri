package akdmEtkinlikEnvanter.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import akdmEtkinlikEnvanter.entities.concretes.TezTuru;

public interface TezTuruDao extends JpaRepository<TezTuru, Integer>{
	TezTuru getByAd(String ad);
}
