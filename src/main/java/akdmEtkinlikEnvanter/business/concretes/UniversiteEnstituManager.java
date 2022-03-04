package akdmEtkinlikEnvanter.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.UniversiteEnstituService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessResult;
import akdmEtkinlikEnvanter.dataAccess.abstracts.UniversiteEnstituDao;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.UniversiteEnstitu;
import desktopApp.ui.DesktopApplication;

@Service
public class UniversiteEnstituManager implements UniversiteEnstituService{

	@Autowired
	UniversiteEnstituDao universiteEnstituDao;
	
	@Override
	public DataResult<UniversiteEnstitu> add(UniversiteEnstitu universiteEnstitu) {
		UniversiteEnstitu temp = universiteEnstituDao.getByAd(universiteEnstitu.getAd());
		if(temp == null) {
			if(universiteEnstitu.getAd() == null) {
				DesktopApplication.addConsole("Üniversite/Enstitü Adı Null Olamaz!!!", "univeristeEnstitu.add()");
				return new ErrorDataResult<UniversiteEnstitu>(null, "Üniversite/Enstitü Adı Null Olamaz!!!");
			}
			else if(universiteEnstitu.getAd().isEmpty()) {
				DesktopApplication.addConsole("Üniversite/Enstitü Adı Boş Bırakılamaz!!!", "univeristeEnstitu.add()");
				return new ErrorDataResult<UniversiteEnstitu>(null, "Üniversite/Enstitü Adı Boş Bırakılamaz!!!");
			}
			
			universiteEnstituDao.save(universiteEnstitu);
			DesktopApplication.addConsole("Üniversite/Enstitü Eklendi. Üniversite/Enstitü: "+universiteEnstitu, "univeristeEnstitu.add()");
			return new SuccessDataResult<UniversiteEnstitu>(universiteEnstitu,"Üniversite/Enstitü Eklendi.");
		}
		DesktopApplication.addConsole("Üniversite/Enstitü Zaten Mevcut. Üniversite/Enstitü: "+temp, "univeristeEnstitu.add()");
		return new ErrorDataResult<UniversiteEnstitu>(temp, "Üniversite/Enstitü Zaten Mevcut");
	}

	@Override
	public DataResult<List<UniversiteEnstitu>> getAll() {
		List<UniversiteEnstitu> temp = universiteEnstituDao.findAll();
		if(temp == null)
			return new ErrorDataResult<List<UniversiteEnstitu>>(null,"Veri tabanında üniversite/enstitü mevcut değildir.");
		return new SuccessDataResult<List<UniversiteEnstitu>>(temp);
	}

	@Override
	public DataResult<UniversiteEnstitu> save(UniversiteEnstitu universiteEnstitu) {
		UniversiteEnstitu temp = universiteEnstituDao.getById(universiteEnstitu.getId());
		if(temp.getId() != 0) {
			if(universiteEnstitu.getAd() == null) {
				DesktopApplication.addConsole("Üniversite/Enstitü Adı Null Olamaz!!!", "univeristeEnstitu.save()");
				return new ErrorDataResult<UniversiteEnstitu>(null, "Üniversite/Enstitü Adı Null Olamaz!!!");
			}
			else if(universiteEnstitu.getAd().isEmpty()) {
				DesktopApplication.addConsole("Üniversite/Enstitü Adı Boş Bırakılamaz!!!", "univeristeEnstitu.save()");
				return new ErrorDataResult<UniversiteEnstitu>(null, "Üniversite/Enstitü Adı Boş Bırakılamaz!!!");
			}
			universiteEnstituDao.save(universiteEnstitu);
			DesktopApplication.addConsole("Üniversite/Enstitü Güncellendi. Üniversite/Enstitü: "+universiteEnstitu, "univeristeEnstitu.save()");
			return new SuccessDataResult<UniversiteEnstitu>(universiteEnstitu,"Üniversite/Enstitü Güncellendi.");		
		}
		DesktopApplication.addConsole("Bu Üniversite/Enstitü Bulunmamaktadır. Üniversite/Enstitü: "+universiteEnstitu, "univeristeEnstitu.save()");
		return new ErrorDataResult<UniversiteEnstitu>(universiteEnstitu,"Bu Üniversite/Enstitü Bulunmamaktadır.");
	}

	@Override
	public Result delete(UniversiteEnstitu universiteEnstitu) {
		UniversiteEnstitu temp = universiteEnstituDao.getById(universiteEnstitu.getId());
		if(temp != null) {
			
			universiteEnstituDao.delete(universiteEnstitu);
			return new SuccessResult("Üniversite/Enstitü Silindi.");
		}
		return new ErrorResult("Böyle Bir Üniversite/Enstitü Bulunmamaktadır.");
	}

	@Override
	public DataResult<UniversiteEnstitu> getById(int id) {
		UniversiteEnstitu universiteEnstitu = universiteEnstituDao.getById(id);
		try {
			universiteEnstitu.getAd();
		}
		catch(Exception e) {
			return new ErrorDataResult<UniversiteEnstitu>(universiteEnstitu, "Bu Üniversite Enstitü Mevcut Değildir.");
		}
		return new SuccessDataResult<UniversiteEnstitu>(universiteEnstitu);
	}

	@Override
	public DataResult<List<UniversiteEnstitu>> findByAdContainingIgnoreCase(String ad) {
		List<UniversiteEnstitu> universiteler = universiteEnstituDao.findByAdContainingIgnoreCase(ad);
		return new SuccessDataResult<List<UniversiteEnstitu>>(universiteler, ad+" İçeren Üniversite ve Enstitüler Döndürüldü.");
	}

	@Override
	public DataResult<List<UniversiteEnstitu>> getAllByOrderByAdAsc() {
		List<UniversiteEnstitu> universiteler = universiteEnstituDao.getAllByOrderByAdAsc();
		return new SuccessDataResult<List<UniversiteEnstitu>>(universiteler,"Üniversite ve Enstitüler Döndürüldü.");

	}

}
