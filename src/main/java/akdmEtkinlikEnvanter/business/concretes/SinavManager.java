package akdmEtkinlikEnvanter.business.concretes;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.SinavService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessResult;
import akdmEtkinlikEnvanter.dataAccess.abstracts.SinavDao;
import akdmEtkinlikEnvanter.entities.concretes.Sinav;
import akdmEtkinlikEnvanter.entities.concretes.SinavTuru;
import akdmEtkinlikEnvanter.entities.concretes.Tez;
import desktopApp.ui.DesktopApplication;

@Service
public class SinavManager implements SinavService{

	@Autowired
	SinavDao sinavDao;
	
	@Override
	public DataResult<Sinav> add(Sinav sinav) {
		Sinav temp = sinavDao.getByTarihAndSinavTuru(sinav.getTarih(), sinav.getSinavTuru());
		if(temp == null) {
			if(sinav.getTarih() == null) {
				DesktopApplication.addConsole("Sınav Tarihi Null Olamaz!!!", "sinav.add()");
				return new ErrorDataResult<Sinav>(null, "Sınav Tarihi Null Olamaz!!!");
			}
			else if(sinav.getSinavTuru() == null) {
				DesktopApplication.addConsole("Sınav Türü Null Olamaz!!!", "sinav.add()");
				return new ErrorDataResult<Sinav>(null, "Sınav Türü Null Olamaz!!!");
			}
			
			sinavDao.save(sinav);
			DesktopApplication.addConsole("Sınav Eklendi. Sınav: "+sinav, "sinav.add()");
			return new SuccessDataResult<Sinav>(sinav,"Sınav Eklendi.");
		}
		DesktopApplication.addConsole("Aynı tarihe ait aynı tipte zaten bir sınav var. Sınav: "+temp, "sinav.add()");
		return new ErrorDataResult<Sinav>(temp, "Aynı tarihe ait aynı tipte zaten bir sınav var.");
	}

	@Override
	public DataResult<List<Sinav>> findAll() {
		return new SuccessDataResult<List<Sinav>>(sinavDao.findAll()," Birlikler Getirildi.");
	}

	@Override
	public DataResult<List<Sinav>> findBySinavTuru(SinavTuru sinavTuru) {
		return new SuccessDataResult<List<Sinav>>(sinavDao.findBySinavTuru(sinavTuru)," Sınavlar Getirildi.");
	}

	@Override
	public DataResult<Sinav> save(Sinav sinav) {
		Sinav temp = sinavDao.getById(sinav.getId());
		if(temp.getId() != 0) {
			if(sinav.getSinavTuru() == null) {
				return new ErrorDataResult<Sinav>(null, "Sınav Türü Null Olamaz!!!");
			}
			else if(sinav.getTarih() == null)
				return new ErrorDataResult<Sinav>(null, "Sınav Tarihi Null Olamaz!!!");
			sinavDao.save(sinav);
			return new SuccessDataResult<Sinav>(sinav,"Sınav Güncellendi.");		
		}
		return new ErrorDataResult<Sinav>(sinav,"Bu Sınav Bulunmamaktadır.");
	}

	@Override
	public Result delete(Sinav sinav) {
		Sinav temp = sinavDao.getById(sinav.getId());
		if(temp != null) {
			sinavDao.delete(sinav);
			return new SuccessResult("Sınav Silindi.");
		}
		return new ErrorResult("Böyle Bir Sınav Bulunmamaktadır.");
	}

	@Override
	public DataResult<Sinav> getById(int sinavId) {
		Sinav sinav = sinavDao.getById(sinavId);
		try {
			sinav.getSinavTuru();
		}
		catch(Exception e) {
			return new ErrorDataResult<Sinav>(sinav, "Bu Sınav Mevcut Değildir.");
		}
		return new SuccessDataResult<Sinav>(sinav);
		
	}

	@Override
	public DataResult<List<Sinav>> findByTarihBetweenAndSinavTuru_AdContainingIgnoreCase(LocalDate from, LocalDate to, String sinavTuru) {
		List<Sinav> sinavlar = null;
		if(from == null && to == null) {
			sinavlar = sinavDao.findBySinavTuru_adContainingIgnoreCase(sinavTuru);
		}
		else {
			if(from == null)
				from = LocalDate.of(1900, 1, 1);
			if(to == null)
				to = LocalDate.now();
			sinavlar = sinavDao.findByTarihBetweenAndSinavTuru_AdContainingIgnoreCase(from, to, sinavTuru);
		}
			
		return new SuccessDataResult<List<Sinav>>(sinavlar, "Sınavlar Döndürüldü");	
	}

}
