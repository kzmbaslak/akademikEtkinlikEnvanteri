package akdmEtkinlikEnvanter.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import akdmEtkinlikEnvanter.entities.concretes.YayineviDergi;

public interface YayineviDergiDao extends JpaRepository<YayineviDergi, Integer>  {
	YayineviDergi getByAdIgnoreCase(String ad);
	List<YayineviDergi> findAllByOrderByAdAsc();
	List<YayineviDergi> getByAdContainingIgnoreCase(String ad);
}
