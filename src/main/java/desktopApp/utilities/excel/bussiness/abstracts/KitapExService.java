package desktopApp.utilities.excel.bussiness.abstracts;

import java.util.List;

import akdmEtkinlikEnvanter.entities.concretes.Kitap;
import akdmEtkinlikEnvanter.entities.concretes.Personel;

public interface KitapExService {
	public Kitap add(Kitap kurs);
	public List<Kitap> add(String kitapAdi,Personel personel);
	public String getByPersonel(Personel personel);
}
