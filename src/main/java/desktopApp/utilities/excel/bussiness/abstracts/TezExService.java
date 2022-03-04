package desktopApp.utilities.excel.bussiness.abstracts;

import java.util.List;

import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Tez;
import akdmEtkinlikEnvanter.entities.concretes.TezTuru;

public interface TezExService {
	public Tez add(Tez tez);
	public List<Tez> add(List<Tez> tezler, String tezKonusu, String tezBitirmeTarihleri);
	public String[] getByPersonelAndTezTuruId(Personel personel, int tezTuruId);
}
