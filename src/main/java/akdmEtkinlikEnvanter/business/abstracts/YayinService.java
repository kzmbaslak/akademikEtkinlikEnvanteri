package akdmEtkinlikEnvanter.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Yayin;
import akdmEtkinlikEnvanter.entities.concretes.YayineviDergi;

public interface YayinService {
	DataResult<Yayin> add(Yayin yayin);
	DataResult<Yayin> add(Yayin yayin, Personel personel);
	DataResult<Yayin> save(Yayin yayin);
	Result delete(Yayin yayin);
	DataResult<Yayin> getById(int yayinId);
	DataResult<List<Yayin>> getAll();
	DataResult<List<Yayin>> findByYazarlar_Id(int yazarId);
	DataResult<List<Yayin>> getByYayineviDergi(YayineviDergi yayineviDergi);
	DataResult<List<Yayin>> findByYayinTarihiBetweenAndAdContainingIgnoreCaseAndYayineviDergi_adContaining(LocalDate from, LocalDate to, String ad, String yayineviDergiAd, int yazarId);
}
