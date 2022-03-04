package akdmEtkinlikEnvanter.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Sinav;
import akdmEtkinlikEnvanter.entities.concretes.SinavSonuc;

public interface SinavSonucDao extends JpaRepository<SinavSonuc, Integer> {
	public SinavSonuc getByPersonelAndSinav(Personel personel, Sinav sinav);
	public List<SinavSonuc> getByPersonelAndSinav_SinavTuru_Id(Personel personel, int sinavTuruId);
	public List<SinavSonuc> getByPersonel(Personel personel);
	public List<SinavSonuc> getBySinav(Sinav sinav);
}
