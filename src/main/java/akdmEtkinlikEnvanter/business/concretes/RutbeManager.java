package akdmEtkinlikEnvanter.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.RutbeService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessResult;
import akdmEtkinlikEnvanter.dataAccess.abstracts.PersonelDao;
import akdmEtkinlikEnvanter.dataAccess.abstracts.RutbeDao;
import akdmEtkinlikEnvanter.entities.concretes.Belge;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Rutbe;
import desktopApp.ui.DesktopApplication;

@Service
public class RutbeManager implements RutbeService{

	@Autowired
	RutbeDao rutbeDao;
	@Autowired
	PersonelDao personelDao;
	
	
	@Override
	public DataResult<Rutbe> add(Rutbe rutbe) {
		Rutbe temp = rutbeDao.getByAd(rutbe.getAd());
		if(temp == null) {
			if(rutbe.getAd() == null) {
				DesktopApplication.addConsole("Rütbe Adı Null Olamaz!!!", "rutbe.add()");
				return new ErrorDataResult<Rutbe>(null, "Rütbe Adı Null Olamaz!!!");
			}
			else if(rutbe.getAd().isEmpty()) {
				DesktopApplication.addConsole("Rütbe Adı Boş Bırakılamaz!!!", "rutbe.add()");
				return new ErrorDataResult<Rutbe>(null, "Rütbe Adı Boş Bırakılamaz!!!");
			}
			else if(rutbe.getSinif() == null) {
				DesktopApplication.addConsole("Rütbenin Sınıfı Null Olamaz!!!", "rutbe.add()");
				return new ErrorDataResult<Rutbe>(null, "Rütbenin Sınıfı Null Olamaz!!!");
			}
			rutbeDao.save(rutbe);
			DesktopApplication.addConsole("Rütbe Eklendi. Rütbe: "+rutbe, "rutbe.add()");
			return new SuccessDataResult<Rutbe>(rutbe,"Rütbe Eklendi.");
		}
		DesktopApplication.addConsole("Aynı Rütbe Mevcut. Rütbe: "+temp, "rutbe.add()");
		return new ErrorDataResult<Rutbe>(temp, "Aynı Rütbe Mevcut.");
	}

	@Override
	public DataResult<List<Rutbe>> getAll() {
		return new SuccessDataResult<List<Rutbe>>(rutbeDao.findAll());
	}

	@Override
	public DataResult<Rutbe> save(Rutbe rutbe) {
		Rutbe temp = rutbeDao.getById(rutbe.getId());
		if(temp.getId() != 0) {
			if(rutbe.getAd() == null) {
				return new ErrorDataResult<Rutbe>(null, "Rütbe Adı Null Olamaz!!!");
			}
			else if(rutbe.getAd().isEmpty())
				return new ErrorDataResult<Rutbe>(null, "Rütbe Adı Boş Bırakılamaz!!!");
			else if(rutbe.getSinif() == null)
				return new ErrorDataResult<Rutbe>(null, "Rütbenin Sınıfı Null Olamaz!!!");
			
			rutbeDao.save(rutbe);
			return new SuccessDataResult<Rutbe>(rutbe,"Rütbe Güncellendi.");
		}
		return new ErrorDataResult<Rutbe>(rutbe, "Bu Rütbe Mevcut Değildir.");
	}

	@Override
	public Result delete(Rutbe rutbe) {
		Rutbe temp = rutbeDao.getById(rutbe.getId());
		if(temp != null) {
			List<Personel> personeller = personelDao.getByRutbe(rutbe);
			for (Personel personel : personeller) {
				personel.setRutbe(null);
				personelDao.save(personel);
			}
			rutbeDao.delete(rutbe);
			return new SuccessResult("Rütbe Silindi.");
		}
		return new ErrorResult("Böyle Bir Rütbe Bulunmamaktadır.");
	}

	@Override
	public DataResult<List<Rutbe>> findByAdContainingIgnoreCase(String ad) {
		List<Rutbe> rutbeler = rutbeDao.findByAdContainingIgnoreCase(ad);
		return new SuccessDataResult<List<Rutbe>>(rutbeler, ad+" İçeren Rütbeler Döndürüldü.");
	}

	@Override
	public DataResult<List<Rutbe>> findByAdContainingAndSinif_ad(String ad, String sinifAd) {
		List<Rutbe> rutbeler;
		if(sinifAd.isEmpty())
			rutbeler = rutbeDao.findByAdContainingIgnoreCase(ad);
		else
			rutbeler = rutbeDao.findByAdContainingIgnoreCaseAndSinif_ad(ad, sinifAd);
		return new SuccessDataResult<List<Rutbe>>(rutbeler, ad+" İçeren ve "+sinifAd+" Sınıfına Ait Rütbeler Döndürüldü.");
	}
	
	@Override
	public DataResult<Rutbe> getById(int rutbeId) {
		Rutbe rutbe = rutbeDao.getById(rutbeId);
		try {
			rutbe.getAd();
		}
		catch(Exception e) {
			return new ErrorDataResult<Rutbe>(rutbe, "Bu Rütbe Mevcut Değildir.");
		}
		return new SuccessDataResult<Rutbe>(rutbe);
	}

	@Override
	public DataResult<List<Rutbe>> findBySinif_id(int sinifId) {
		List<Rutbe> rutbeler = rutbeDao.findBySinif_id(sinifId);
		if(rutbeler.size() > 0)
			return new SuccessDataResult<List<Rutbe>>(rutbeler, "Rütbeler Döndürüldü.");
		else
			return new ErrorDataResult<>("Bu sınıfa ait rütbe bulunmamaktadır.");
	}



}
