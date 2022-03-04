package akdmEtkinlikEnvanter.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import akdmEtkinlikEnvanter.entities.concretes.Belge;
import akdmEtkinlikEnvanter.entities.concretes.BelgeTuru;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Tez;

public interface BelgeDao extends JpaRepository<Belge, Integer> {
	  Belge getByPersonelAndAdIgnoreCase(Personel personel, String ad);
	  List<Belge> findByPersonel(Personel personel);
	  List<Belge> getByBelgeTuru(BelgeTuru belgeTuru);
	  List<Belge> findByAdContainingIgnoreCaseAndBelgeTuru_adAndPersonel_sicilContaining(String ad, String belgeTuruAd, String personelSicil);
	  List<Belge> findByAdContainingIgnoreCaseAndBelgeTuru_adContainingAndPersonel_sicilContaining(String ad, String belgeTuruAd, String personelSicil);

}
