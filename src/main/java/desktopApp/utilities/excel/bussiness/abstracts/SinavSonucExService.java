package desktopApp.utilities.excel.bussiness.abstracts;


import java.util.List;

import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Sinav;
import akdmEtkinlikEnvanter.entities.concretes.SinavSonuc;

public interface SinavSonucExService {
	public SinavSonuc add(SinavSonuc sinavSonuc);
	public List<SinavSonuc> add(String sinavVeTarih, Sinav sinav, Personel personel);
	public String getByPersonelAndSinavTuruId(Personel personel, int sinavTuruId);
}
