package akdmEtkinlikEnvanter.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.BirlikService;
import akdmEtkinlikEnvanter.business.abstracts.PersonelService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessResult;
import akdmEtkinlikEnvanter.dataAccess.abstracts.BirlikDao;
import akdmEtkinlikEnvanter.dataAccess.abstracts.PersonelDao;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import desktopApp.ui.DesktopApplication;

@Service
public class BirlikManager implements BirlikService{

	@Autowired
	BirlikDao birlikDao;
	@Autowired
	PersonelDao personelDao;
	@Autowired
	PersonelService personelService;
	
	@Override
	public DataResult<Birlik> add(Birlik birlik) {
		Birlik temp = birlikDao.getByAd(birlik.getAd());
		if(temp == null) {
			if(birlik.getAd() == null) {
				DesktopApplication.addConsole("Birlik Adı Null Olamaz!!!", "birlik.add()");
				return new ErrorDataResult<Birlik>(null, "Birlik Adı Null Olamaz!!!");
			}
			else if(birlik.getAd().isEmpty()) {
				DesktopApplication.addConsole("Birlik Adı Boş Bırakılamaz!!!", "birlik.add()");
				return new ErrorDataResult<Birlik>(null, "Birlik Adı Boş Bırakılamaz!!!");
			}
			DesktopApplication.addConsole("Birlik Eklendi. Birlik: "+birlik, "birlik.add()");
			birlikDao.save(birlik);
			return new SuccessDataResult<Birlik>(birlik,"Birlik Eklendi");		
		}

		DesktopApplication.addConsole("Aynı birlikten zaten var. Birlik: "+temp, "birlik.add()");
		return new ErrorDataResult<Birlik>(temp,"Aynı birlikten zaten var.");
	}	

	@Override
	public DataResult<Birlik> save(Birlik birlik) {
		Birlik temp = birlikDao.getById(birlik.getId());
		if(temp.getId() != 0) {
			if(birlik.getAd() == null) {
				return new ErrorDataResult<Birlik>(null, "Birlik Adı Null Olamaz!!!");
			}
			else if(birlik.getAd().isEmpty())
				return new ErrorDataResult<Birlik>(null, "Birlik Adı Boş Bırakılamaz!!!");
			birlikDao.save(birlik);
			return new SuccessDataResult<Birlik>(birlik,"Birlik Güncellendi.");		
		}
		return new ErrorDataResult<Birlik>(birlik,"Bu Birlik Bulunmamaktadır.");
	}
	
	@Override
	public DataResult<List<Birlik>> getAll() {
		// TODO Auto-generated method stub
		return new SuccessDataResult<List<Birlik>>(birlikDao.findAll());
	}

	@Override
	public DataResult<Birlik> getByPersonelId(int personelId) {
		Birlik birlik = birlikDao.getByPersoneller_Id(personelId);
		if(birlik != null) {
			return new SuccessDataResult<Birlik>(birlik,"Birlik döndürüldü.");		
		}
		return new ErrorDataResult<Birlik>(birlik,"Bu Personele Ait Birlik Bulunamadı.");
	}

	@Override
	public DataResult<Birlik> getByAd(String ad) {
		Birlik birlik = birlikDao.getByAd(ad);
		if(birlik != null) {
			return new SuccessDataResult<Birlik>(birlik,"Birlik döndürüldü.");		
		}
		return new ErrorDataResult<Birlik>(birlik,"Bu Personele Ait Birlik Bulunamadı.");
	}

	@Override
	public Result delete(Birlik birlik) {
		Birlik temp = birlikDao.getById(birlik.getId());
		if(temp != null) {
			List<Personel> personeller = personelService.getByBirlik(birlik).getData();
			for (Personel personel : personeller) {
				personel.setBirlik(null);
				personelDao.save(personel);
			}
			birlikDao.delete(birlik);
			return new SuccessResult("Birlik Silindi.");
		}
		return new ErrorResult("Böyle Bir Birlik Bulunmamaktadır.");
		
	}

	@Override
	public DataResult<List<Birlik>> findByAdLike(String ad) {
		List<Birlik> birlikler = birlikDao.findByAdLike(ad);
		return new SuccessDataResult<List<Birlik>>(birlikler, ad+" İçeren Birlikler Döndürüldü.");
		
	}

	@Override
	public DataResult<List<Birlik>> findByAdContainingIgnoreCase(String ad) {
		List<Birlik> birlikler = birlikDao.findByAdContainingIgnoreCase(ad);
		return new SuccessDataResult<List<Birlik>>(birlikler, ad+" İçeren Birlikler Döndürüldü.");
	}

	@Override
	public DataResult<List<Birlik>> findByAdContainingIgnoreCaseOrderByAdAsc(String ad) {
		List<Birlik> birlikler = birlikDao.findByAdContainingIgnoreCaseOrderByAdAsc(ad);
		return new SuccessDataResult<List<Birlik>>(birlikler, ad+" İçeren Birlikler Döndürüldü.");
	}

	@Override
	public DataResult<Birlik> getById(int birlikId) {
		Birlik birlik = birlikDao.getById(birlikId);
		try {
			birlik.getAd();
		}
		catch(Exception e) {
			return new ErrorDataResult<Birlik>(birlik, "Bu Birlik Mevcut Değildir.");
		}
		return new SuccessDataResult<Birlik>(birlik);
	}

	
	@Override
	public DataResult<List<Birlik>> findAllByOrderByAdAsc() {
		List<Birlik> birlikler = birlikDao.findAllByOrderByAdAsc();
		return new SuccessDataResult<List<Birlik>>(birlikler, "Birlikler Sıralanmış Olarak Döndürüldü.");
	}


}
