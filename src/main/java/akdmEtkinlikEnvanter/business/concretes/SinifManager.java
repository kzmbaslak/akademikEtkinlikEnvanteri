package akdmEtkinlikEnvanter.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graphbuilder.math.func.SinFunction;

import akdmEtkinlikEnvanter.business.abstracts.RutbeService;
import akdmEtkinlikEnvanter.business.abstracts.SinifService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessResult;
import akdmEtkinlikEnvanter.dataAccess.abstracts.SinifDao;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Sinif;
import desktopApp.ui.DesktopApplication;

@Service
public class SinifManager implements SinifService{

	@Autowired
	SinifDao sinifDao;
	@Autowired
	RutbeService rutbeService;
	
	@Override
	public DataResult<Sinif> add(Sinif sinif) {
		Sinif temp = sinifDao.getByAdIgnoreCase(sinif.getAd());
		if(temp == null) {
			if(sinif.getAd() == null) {
				DesktopApplication.addConsole("Sınıf Adı Null Olamaz!!!", "sinif.add()");
				return new ErrorDataResult<Sinif>(null, "Sınıf Adı Null Olamaz!!!");
			}
			else if(sinif.getAd().isEmpty()) {
				DesktopApplication.addConsole("Sınıf Adı Boş Bırakılamaz!!!", "sinif.add()");
				return new ErrorDataResult<Sinif>(null, "Sınıf Adı Boş Bırakılamaz!!!");
			}
			sinif = sinifDao.save(sinif);
			DesktopApplication.addConsole("Sınıf Eklendi. Sınıf: "+sinif, "sinif.add()");
			return new SuccessDataResult<Sinif>(sinif,"Sınıf Eklendi");
		}
		DesktopApplication.addConsole("Sınıf adından zaten var. Sınıf: "+temp, "sinif.add()");
		return new ErrorDataResult<Sinif>(temp,"Sınıf adından zaten var.");
		
	}

	@Override
	public DataResult<Sinif> findByAd(String ad) {
		Sinif sinif = sinifDao.getByAdIgnoreCase(ad);
		if(sinif != null) {
			return new SuccessDataResult<Sinif>(sinif,"Sınıf Bulundu"); 
		}
		return new ErrorDataResult<Sinif>(null,"Sınıf bulunamadı.");
	}

	@Override
	public DataResult<List<Sinif>> getAll() {

		return new SuccessDataResult<List<Sinif>>(sinifDao.findAll());
	}

	@Override
	public DataResult<Sinif> save(Sinif sinif) {
		Sinif temp = sinifDao.getByAdIgnoreCase(sinif.getAd());
		if(temp.getId() != 0) {
			if(sinif.getAd() == null) {
				return new ErrorDataResult<Sinif>(null, "Sınıf Adı Null Olamaz!!!");
			}
			else if(sinif.getAd().isEmpty())
				return new ErrorDataResult<Sinif>(null, "Sınıf Adı Boş Bırakılamaz!!!");
			sinif = sinifDao.save(sinif);
			return new SuccessDataResult<Sinif>(sinif,"Sınıf Güncellendi.");
		}
		return new ErrorDataResult<Sinif>(sinif,"Bir Değişiklik Yapmadınız");
	}

	@Override
	public Result delete(Sinif sinif) {
		Sinif temp = sinifDao.getById(sinif.getId());
		if(temp != null) {
			Result result = rutbeService.findBySinif_id(sinif.getId());
			if(!result.isSuccess()) {
				sinifDao.delete(sinif);
				return new SuccessResult("Sınıf Silindi.");
			}
			else
				return new ErrorResult("Bu Sınıfa Ait Rütbeler Bulunmaktadır. Rütbeler Silinmeden Bu Sınıf Silinemez.");
				
		}
		return new ErrorResult("Böyle Bir Sınıf Bulunmamaktadır.");
	}

	@Override
	public DataResult<Sinif> getById(int sinifId) {
		Sinif sinif = sinifDao.getById(sinifId);
		try {
			sinif.getAd();
		}
		catch(Exception e) {
			return new ErrorDataResult<Sinif>(sinif, "Bu Sınıf Mevcut Değildir.");
		}
		return new SuccessDataResult<Sinif>(sinif);
	}

	@Override
	public DataResult<Long> count() {
		return new SuccessDataResult<Long>(sinifDao.count(),"Sınıf sayısı döndürüldü");
	}

}
