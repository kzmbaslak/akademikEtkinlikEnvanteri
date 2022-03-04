package akdmEtkinlikEnvanter.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.TezTuruService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessResult;
import akdmEtkinlikEnvanter.dataAccess.abstracts.TezTuruDao;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.TezTuru;
import desktopApp.ui.DesktopApplication;

@Service
public class TezTuruManager implements TezTuruService{

	@Autowired
	TezTuruDao tezTuruDao;
	
	@Override
	public Result add(TezTuru tezTuru) {
		TezTuru temp = tezTuruDao.getByAd(tezTuru.getAd());
		if(temp == null) {
			if(tezTuru.getAd() == null) {
				DesktopApplication.addConsole("Tez Türü Adı Null Olamaz!!!", "tezTuru.add()");
				return new ErrorDataResult<Personel>(null, "Tez Türü Adı Null Olamaz!!!");
			}
			else if(tezTuru.getAd().isEmpty()) {
				DesktopApplication.addConsole("Tez Türü Adı Boş Bırakılamaz!!!", "tezTuru.add()");
				return new ErrorDataResult<Personel>(null, "Tez Türü Adı Boş Bırakılamaz!!!");
			}
			tezTuruDao.save(tezTuru);
			DesktopApplication.addConsole("Tez Türü Eklendi. Tez Türü: "+tezTuru, "tezTuru.add()");
			return new SuccessDataResult<TezTuru>(tezTuru,"Tez Türü Eklendi.");
		}
		return new ErrorDataResult<TezTuru>(temp, "Tez Türü Zaten Mevcut. ");
	}

	@Override
	public DataResult<TezTuru> getById(int id) {
		TezTuru tezTuru = tezTuruDao.getById(id);
		try {
			tezTuru.getAd();
		}
		catch(Exception e) {
			return new ErrorDataResult<TezTuru>(tezTuru, "Bu Tez Türü Mevcut Değildir.");
		}
		return new SuccessDataResult<TezTuru>(tezTuru);
		
	}

	@Override
	public DataResult<List<TezTuru>> getAll() {
		List<TezTuru> temp = tezTuruDao.findAll();
		if(temp == null)
			return new ErrorDataResult<List<TezTuru>>(null,"Veri tabanında tez türü mevcut değildir.");
		return new SuccessDataResult<List<TezTuru>>(temp);
	}

}
