package akdmEtkinlikEnvanter.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.BelgeTuruService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessResult;
import akdmEtkinlikEnvanter.dataAccess.abstracts.BelgeDao;
import akdmEtkinlikEnvanter.dataAccess.abstracts.BelgeTuruDao;
import akdmEtkinlikEnvanter.entities.concretes.Belge;
import akdmEtkinlikEnvanter.entities.concretes.BelgeTuru;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Sinav;

@Service
public class BelgeTuruManager implements BelgeTuruService {
	
	@Autowired
	BelgeTuruDao belgeTuruDao;
	
	@Override
	public DataResult<BelgeTuru> add(BelgeTuru belgeTuru) {
		if(belgeTuru == null)
			return new ErrorDataResult<BelgeTuru>(null, "Belge Türü Null Olamaz!!!");
		
		BelgeTuru temp = belgeTuruDao.getByAdIgnoreCase(belgeTuru.getAd());
		if(temp == null) {
			if(belgeTuru.getAd() == null) {
				return new ErrorDataResult<BelgeTuru>(null, "Belge Türü Adı Null Olamaz!!!");
			}
			else if(belgeTuru.getAd().isEmpty()) {
				return new ErrorDataResult<BelgeTuru>(null, "Belge Türü Adı Boş Bırakılamaz!!!");
			}
			belgeTuruDao.save(belgeTuru);
			return new SuccessDataResult<BelgeTuru>(belgeTuru,"Belge Türü Eklendi");		
		}

		return new ErrorDataResult<BelgeTuru>(temp,"Aynı isimde bir belge türü zaten var.");
	}

	@Override
	public DataResult<BelgeTuru> save(BelgeTuru belgeTuru) {
		BelgeTuru temp = belgeTuruDao.getById(belgeTuru.getId());
		if(temp.getId() != 0) {
			if(belgeTuru.getAd() == null) {
				return new ErrorDataResult<BelgeTuru>(null, "Belge Türü Adı Null Olamaz!!!");
			}
			else if(belgeTuru.getAd().isEmpty()) {
				return new ErrorDataResult<BelgeTuru>(null, "Belge Türü Adı Boş Bırakılamaz!!!");
			}
			belgeTuruDao.save(belgeTuru);
			return new SuccessDataResult<BelgeTuru>(belgeTuru,"Belge Türü Güncellendi");		
		}
		return new ErrorDataResult<BelgeTuru>(temp,"Bu belge türü bulunmamaktadır.");
	}

	@Override
	public DataResult<BelgeTuru> getById(int belgeTuruId) {
		BelgeTuru belgeTuru = belgeTuruDao.getById(belgeTuruId);
		try {
			belgeTuru.getAd();
		}
		catch(Exception e) {
			return new ErrorDataResult<BelgeTuru>(belgeTuru, "Bu Belge Mevcut Değildir.");
		}
		return new SuccessDataResult<BelgeTuru>(belgeTuru);
	}

	@Override
	public DataResult<List<BelgeTuru>> getAll() {
		return new SuccessDataResult<List<BelgeTuru>>(belgeTuruDao.findAll());
	}

	@Override
	public Result delete(BelgeTuru belgeTuru) {
		BelgeTuru temp = belgeTuruDao.getById(belgeTuru.getId());
		if(temp != null) {
			System.out.println();
			belgeTuruDao.delete(belgeTuru);
			return new SuccessResult("Belge Türü Silindi.");
		}
		return new ErrorResult("Böyle Bir Belge Türü Bulunmamaktadır.");
	}

}
