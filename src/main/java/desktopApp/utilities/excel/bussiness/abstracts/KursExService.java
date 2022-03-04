package desktopApp.utilities.excel.bussiness.abstracts;

import java.util.List;

import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Personel;

public interface KursExService {
	public Kurs add(Kurs kurs);
	public List<Kurs> add(String kursAdi,Personel personel);
	public String getByPersonel(Personel personel);
}
