package akdmEtkinlikEnvanter.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.SinavSonucService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessResult;
import akdmEtkinlikEnvanter.dataAccess.abstracts.SinavSonucDao;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Sinav;
import akdmEtkinlikEnvanter.entities.concretes.SinavSonuc;
import desktopApp.ui.DesktopApplication;

@Service
public class SinavSonucManager implements SinavSonucService{

	@Autowired
	SinavSonucDao sinavSonucDao;
	
	@Override
	public DataResult<SinavSonuc> add(SinavSonuc sinavSonuc) {
		SinavSonuc temp = sinavSonucDao.getByPersonelAndSinav(sinavSonuc.getPersonel(), sinavSonuc.getSinav());
		if(temp == null) {
			if(sinavSonuc.getPersonel() == null) {
				DesktopApplication.addConsole("Sınava sonucuna ait personel Null Olamaz!!!", "sinavSonuc.add()");
				return new ErrorDataResult<SinavSonuc>(null, "Sınava sonucuna ait personel Null Olamaz!!!");
			}
			else if(sinavSonuc.getSinav() == null) {
				DesktopApplication.addConsole("Sınav türü null olamaz", "sinavSonuc.add()");
				return new ErrorDataResult<SinavSonuc>(null, "Sınav türü null olamaz");
			}
			else if(sinavSonuc.getSinavNot() < 0 || sinavSonuc.getSinavNot() > 100) {
				DesktopApplication.addConsole("Sınav 0 - 100 arasında olmalıdır.", "sinavSonuc.add()");
				return new ErrorDataResult<SinavSonuc>(null, "Sınav 0 - 100 arasında olmalıdır.");
			}
			
			sinavSonucDao.save(sinavSonuc);
			DesktopApplication.addConsole("Sınav Sonucu Eklendi. Sınav Sonucu: "+sinavSonuc, "sinavSonuc.add()");
			return new SuccessDataResult<SinavSonuc>(sinavSonuc,"Sınav Sonucu Eklendi.");
		}
		DesktopApplication.addConsole("Bir personelin aynı sınavdan sadece bir notu olabilir. Sınav Sonucu: "+temp, "sinavSonuc.add()");
		return new ErrorDataResult<SinavSonuc>(temp, "Bir personelin aynı sınavdan sadece bir notu olabilir.");
	}

	@Override
	public DataResult<SinavSonuc> save(SinavSonuc sinavSonuc) {
		SinavSonuc temp = sinavSonucDao.getById(sinavSonuc.getId());
		if(temp.getId() != 0) {
			if(sinavSonuc.getPersonel() == null) {
				return new ErrorDataResult<SinavSonuc>(null, "Sınava sonucuna ait personel Null Olamaz!!!");
			}
			else if(sinavSonuc.getSinav() == null)
				return new ErrorDataResult<SinavSonuc>(null, "Sınav türü null olamaz");
//			SinavSonuc tmp = sinavSonucDao.getByPersonelAndSinav(sinavSonuc.getPersonel(), sinavSonuc.getSinav());
			
			sinavSonucDao.save(sinavSonuc);
			return new SuccessDataResult<SinavSonuc>(sinavSonuc,"Sınav Sonucu Güncellendi.");
			
			
		}
		return new ErrorDataResult<SinavSonuc>(temp, "Bu Sınav Sonucu Mevcut Değildir.");
	}

	@Override
	public Result delete(SinavSonuc sinavSonuc) {
		SinavSonuc temp = sinavSonucDao.getById(sinavSonuc.getId());
		if(temp != null) {
			sinavSonucDao.delete(sinavSonuc);
			return new SuccessResult("Sınav Sonucu Silindi.");
		}
		return new ErrorResult("Böyle Bir Sınav Sonucu Bulunmamaktadır.");
	}
	
	@Override
	public DataResult<List<SinavSonuc>> getByPersonelAndSinav_SinavTuru_Id(Personel personel, int sinavTuruId) {
		List<SinavSonuc> temp = sinavSonucDao.getByPersonelAndSinav_SinavTuru_Id(personel, sinavTuruId);
		return new SuccessDataResult<List<SinavSonuc>>(temp, "Sınavlar Döndürüldü.");
	}

	@Override
	public DataResult<List<SinavSonuc>> getByPersonel(Personel personel) {
		List<SinavSonuc> temp = sinavSonucDao.getByPersonel(personel);
		return new SuccessDataResult<List<SinavSonuc>>(temp, "Sınavlar Döndürüldü.");
	}

	@Override
	public DataResult<List<SinavSonuc>> getBySinav(Sinav sinav) {
		List<SinavSonuc> temp = sinavSonucDao.getBySinav(sinav);
		return new SuccessDataResult<List<SinavSonuc>>(temp, "Sınavlar Döndürüldü.");
	}

	@Override
	public DataResult<SinavSonuc> getById(int sinavSonucId) {
		SinavSonuc sinavSonuc = sinavSonucDao.getById(sinavSonucId);
		try {
			sinavSonuc.getSinavNot();
		}
		catch(Exception e) {
			return new ErrorDataResult<SinavSonuc>(sinavSonuc, "Bu Sınav Sonucu Mevcut Değildir.");
		}
		return new SuccessDataResult<SinavSonuc>(sinavSonuc);
	}

}
