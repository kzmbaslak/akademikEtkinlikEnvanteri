package akdmEtkinlikEnvanter.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.YayineviDergiService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessResult;
import akdmEtkinlikEnvanter.dataAccess.abstracts.YayineviDergiDao;
import akdmEtkinlikEnvanter.entities.concretes.Belge;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Kitap;
import akdmEtkinlikEnvanter.entities.concretes.YayineviDergi;

@Service
public class YayineviDergiManager implements YayineviDergiService{

	@Autowired
	YayineviDergiDao yayineviDergiDao;
	
	@Override
	public DataResult<YayineviDergi> add(YayineviDergi yayineviDergi) {
		if(yayineviDergi == null)
			return new ErrorDataResult<YayineviDergi>(null, "Yayinevi/Dergi Null Olamaz!!!");
		
		YayineviDergi temp = yayineviDergiDao.getByAdIgnoreCase(yayineviDergi.getAd());
		if(temp == null) {
			if(yayineviDergi.getAd() == null) {
				return new ErrorDataResult<YayineviDergi>(null, "Yayinevi/Dergi Adı Null Olamaz!!!");
			}
			else if(yayineviDergi.getAd().isEmpty()) {
				return new ErrorDataResult<YayineviDergi>(null, "Yayinevi/Dergi Adı Boş Bırakılamaz!!!");
			}
			yayineviDergiDao.save(yayineviDergi);
			return new SuccessDataResult<YayineviDergi>(yayineviDergi,"Yayinevi/Dergi Eklendi");		
		}

		return new ErrorDataResult<YayineviDergi>(temp,"Aynı isimde bir Yayinevi/Dergi zaten var.");
	}

	@Override
	public DataResult<YayineviDergi> save(YayineviDergi yayineviDergi) {
		YayineviDergi temp = yayineviDergiDao.getById(yayineviDergi.getId());
		if(temp.getId() != 0) {
			if(yayineviDergi.getAd() == null) {
				return new ErrorDataResult<YayineviDergi>(null, "Yayinevi/Dergi Adı Null Olamaz!!!");
			}
			else if(yayineviDergi.getAd().isEmpty()) {
				return new ErrorDataResult<YayineviDergi>(null, "Yayinevi/Dergi Adı Boş Bırakılamaz!!!");
			}
			yayineviDergiDao.save(yayineviDergi);
			return new SuccessDataResult<YayineviDergi>(yayineviDergi,"Yayinevi/Dergi Güncellendi");
		}
		return new ErrorDataResult<YayineviDergi>(temp,"Bu Yayinevi/Dergi mevcut değildir.");
	}

	@Override
	public DataResult<YayineviDergi> getById(int yayineviDergiId) {
		YayineviDergi yayineviDergi = yayineviDergiDao.getById(yayineviDergiId);
		try {
			yayineviDergi.getAd();
		}
		catch(Exception e) {
			return new ErrorDataResult<YayineviDergi>(yayineviDergi, "Bu Yayinevi/Dergi Mevcut Değildir.");
		}
		return new SuccessDataResult<YayineviDergi>(yayineviDergi);
	}

	@Override
	public DataResult<List<YayineviDergi>> getAll() {
		return new SuccessDataResult<List<YayineviDergi>>(yayineviDergiDao.findAll());
	}

	@Override
	public Result delete(YayineviDergi yayineviDergi) {
		YayineviDergi temp = yayineviDergiDao.getById(yayineviDergi.getId());
		if(temp != null) {
			
			yayineviDergiDao.delete(yayineviDergi);
			return new SuccessResult("Yayınevi Dergi Silindi.");
		}
		return new ErrorResult("Böyle Bir Yayınevi Dergi Bulunmamaktadır.");
	}

	@Override
	public DataResult<List<YayineviDergi>> findAllByOrderByAdAsc() {
		return new SuccessDataResult<List<YayineviDergi>>(yayineviDergiDao.findAllByOrderByAdAsc());
	}

	@Override
	public DataResult<List<YayineviDergi>> getByAdContainingIgnoreCase(String ad) {

		List<YayineviDergi> yayineviDergiler = yayineviDergiDao.getByAdContainingIgnoreCase(ad);
		return new SuccessDataResult<List<YayineviDergi>>(yayineviDergiler, ad+" İçeren Yayınevi/Dergiler Döndürüldü.");
	}

}
