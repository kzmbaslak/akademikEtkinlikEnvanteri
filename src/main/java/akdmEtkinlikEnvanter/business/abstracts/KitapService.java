package akdmEtkinlikEnvanter.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Kitap;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Yayin;
import akdmEtkinlikEnvanter.entities.concretes.YayineviDergi;

public interface KitapService {
	DataResult<Kitap> add(Kitap kitap);
	DataResult<Kitap> add(Kitap kitap, Personel personel);
	DataResult<Kitap> save(Kitap kitap);
	Result delete(Kitap kitap);
	DataResult<Kitap> getById(int kitapId);
	DataResult<List<Kitap>> getAll();
	DataResult<List<Kitap>> findByYazarlar_Id(int yazarId);
	DataResult<List<Kitap>> getByYayineviDergi(YayineviDergi yayineviDergi);
	DataResult<List<Kitap>> findByYayinTarihiBetweenAndAdContainingIgnoreCaseAndYayineviDergi_adContaining(LocalDate from, LocalDate to, String ad, String yayineviDergiAd, int yazarId);

}
