package desktopApp.utilities.excel.bussiness.abstracts;

import java.util.List;

import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Yayin;

public interface YayinExService {
	public Yayin add(Yayin kurs);
	public List<Yayin> add(String yayinAdi,Personel personel);
	public String getByPersonel(Personel personel);
}
