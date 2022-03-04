package desktopApp.utilities.excel.bussiness.abstracts;

import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.entities.concretes.Personel;

public interface PersonelExService {
	public DataResult<Personel> add(Personel personel);
	public Personel save(Personel personel);
}
