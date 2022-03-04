package akdmEtkinlikEnvanter.dataAccess.abstracts;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import akdmEtkinlikEnvanter.entities.concretes.Kurs;

public interface KursDao extends JpaRepository<Kurs, Integer> {
	Kurs getByAd(String ad);
	Kurs getByAdAndPersoneller_Id(String ad, int personelId);
	List<Kurs> getByPersoneller_IdOrderByAdAsc(int personelId);
	List<Kurs> findByPersoneller_Id(int personelId);
	List<Kurs> findByIdNotIn(List<Integer> kurslarId);
	List<Kurs> findByIdNotInAndAdContainingIgnoreCaseOrderByAdAsc(List<Integer> kurslarId, String ad);
	List<Kurs> findByAdContainingIgnoreCase(String ad);
	List<Kurs> findAllByOrderByAdAsc();
}