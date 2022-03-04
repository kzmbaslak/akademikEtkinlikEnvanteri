package akdmEtkinlikEnvanter.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import akdmEtkinlikEnvanter.entities.concretes.Birlik;

public interface BirlikDao extends JpaRepository<Birlik, Integer> {
	Birlik getByAd(String ad);
	Birlik getByPersoneller_Id(int personelId);
	List<Birlik> findByAdLike(String ad);
	List<Birlik> findByAdContainingIgnoreCase(String ad);
	List<Birlik> findByAdContainingIgnoreCaseOrderByAdAsc(String ad);
	List<Birlik> findAllByOrderByAdAsc();
}
