package akdmEtkinlikEnvanter.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.KursService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessResult;
import akdmEtkinlikEnvanter.dataAccess.abstracts.KursDao;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Tez;
import desktopApp.ui.DesktopApplication;

@Service
public class KursManager implements KursService{
	
	@Autowired
	KursDao kursDao;
	@Override
	public DataResult<Kurs> add(Kurs kurs) {
		Kurs temp = kursDao.getByAd(kurs.getAd());
		
		//System.out.println(kurs.getAd());
		if(temp == null) {
			if(kurs.getAd() == null) {
				DesktopApplication.addConsole("Kurs Adı Null Olamaz!!!", "kurs.add()");
				return new ErrorDataResult<Kurs>(null, "Kurs Adı Null Olamaz!!!");
			}
			else if(kurs.getAd().isEmpty()) {
				DesktopApplication.addConsole("Kurs Adı Boş Bırakılamaz!!!", "kurs.add()");
				return new ErrorDataResult<Kurs>(null, "Kurs Adı Boş Bırakılamaz!!!");
			}
			
			kursDao.save(kurs);
			DesktopApplication.addConsole("Kurs Eklendi. Kurs: "+kurs, "kurs.add()");
			return new SuccessDataResult<Kurs>(kurs,"Kurs Eklendi.");
		}
		DesktopApplication.addConsole("Aynı kurstan zaten var. Kurs: "+temp, "kurs.add()");
		return new ErrorDataResult<Kurs>(temp, "Aynı kurstan zaten var.");
	}

	@Override
	public DataResult<Kurs> add(Kurs kurs, Personel personel) {
		Kurs temp = kursDao.getByAd(kurs.getAd());
		//Kurs temp = kursDao.getByAd(kurs.getAd());
		//System.out.println(kurs.getAd());
		if(temp == null) {
			if(kurs.getAd() == null) {
				return new ErrorDataResult<Kurs>(null, "Kurs Adı Null Olamaz!!!");
			}
			else if(kurs.getAd().isEmpty())
				return new ErrorDataResult<Kurs>(null, "Kurs Adı Boş Bırakılamaz!!!");

			//System.out.println(kurs.getAd()+" "+personel.getAd());
			kursDao.save(kurs);
			return new SuccessDataResult<Kurs>(kurs,"Kurs Eklendi.");
		}
		else {
			Kurs tmp = kursDao.getByAdAndPersoneller_Id(kurs.getAd(), personel.getId());
			if(tmp == null) {
				temp.getPersoneller().add(personel);
				kursDao.save(temp);
				return new SuccessDataResult<Kurs>(kurs,"Kurs Düzenlendi");
			}
		}
		return new ErrorDataResult<Kurs>(temp, "Aynı kurstan zaten var.");
	}

	@Override
	public DataResult<List<Kurs>> findAll() {
		List<Kurs> temp = kursDao.findAll();
		if(temp == null)
			return new ErrorDataResult<List<Kurs>>(null,"Veri tabanında kurs mevcut değildir.");
		return new SuccessDataResult<List<Kurs>>(temp);
	}

	@Override
	public DataResult<List<Kurs>> getByPersoneller_IdOrderByAdAsc(int personelId) {
		List<Kurs> kurslar = kursDao.getByPersoneller_IdOrderByAdAsc(personelId);
		if(kurslar != null) {
			return new SuccessDataResult<List<Kurs>>(kurslar,"Kurslar Döndürüldü.");		
		}
		return new ErrorDataResult<List<Kurs>>(kurslar,"Bu Personele Ait Kurs Bulunamadı.");
	}

	@Override
	public DataResult<List<Kurs>> findByPersoneller_Id(int personelId) {
		List<Kurs> kurslar = kursDao.findByPersoneller_Id(personelId);
		if(kurslar != null) {
			return new SuccessDataResult<List<Kurs>>(kurslar,"Kurslar Döndürüldü.");		
		}
		return new ErrorDataResult<List<Kurs>>(kurslar,"Bu Personele Ait Kurs Bulunamadı.");
	}

	@Override
	public DataResult<List<Kurs>> findByIdNotIn(List<Integer> kurslarId) {
		if(kurslarId.size() == 0)
			kurslarId.add(-1);
		List<Kurs> kurslarOther = kursDao.findByIdNotIn(kurslarId);
		if(kurslarOther != null) {
			return new SuccessDataResult<List<Kurs>>(kurslarOther,"Kurslar Döndürüldü.");		
		}
		return new ErrorDataResult<List<Kurs>>(kurslarOther,"Bu Personele Ait Olmayan Kurs Bulunamadı.");
	}

	@Override
	public DataResult<Kurs> save(Kurs kurs) {
		Kurs temp = kursDao.getById(kurs.getId());
		if(temp.getId() != 0) {
			if(kurs.getAd() == null) {
				return new ErrorDataResult<Kurs>(null, "Kurs Adı Null Olamaz!!!");
			}
			else if(kurs.getAd().isEmpty())
				return new ErrorDataResult<Kurs>(null, "Kurs Adı Boş Bırakılamaz!!!");
			kursDao.save(kurs);
			return new SuccessDataResult<Kurs>(kurs,"Kurs Güncellendi.");
		}
		return new ErrorDataResult<Kurs>(temp, "Kurs Mevcut Değildir.");
	}

	@Override
	public DataResult<List<Kurs>> findByIdNotInAndAdContainingIgnoreCaseOrderByAdAsc(List<Integer> kurslarId, String ad) {
		
		List<Kurs> kurslar = kursDao.findByIdNotInAndAdContainingIgnoreCaseOrderByAdAsc(kurslarId, ad);
		return new SuccessDataResult<List<Kurs>>(kurslar, ad+" İçeren Kurslar Döndürüldü.");
	}

	@Override
	public Result delete(Kurs kurs) {
		Kurs temp = kursDao.getById(kurs.getId());
		if(temp != null) {
//			List<Personel> personeller = personelDao.getByBirlik(birlik);
//			for (Personel personel : personeller) {
//				personel.setBirlik(null);
//				personelDao.save(personel);
//			}
			kursDao.delete(kurs);
			return new SuccessResult("Kurs Silindi.");
		}
		return new ErrorResult("Böyle Bir Birlik Bulunmamaktadır.");
	}

	@Override
	public DataResult<Kurs> findById(int id) {
		Kurs kurs = kursDao.getById(id);
		try {
			kurs.getAd();
		}
		catch(Exception e) {
			return new ErrorDataResult<Kurs>(kurs, "Bu Kurs Mevcut Değildir.");
		}
		return new SuccessDataResult<Kurs>(kurs);
	}

	@Override
	public DataResult<List<Kurs>> findByAdContainingIgnoreCase(String ad) {
		List<Kurs> kurslar = kursDao.findByAdContainingIgnoreCase(ad);
		return new SuccessDataResult<List<Kurs>>(kurslar, ad+" İçeren Kurslar Döndürüldü.");
	}

	@Override
	public DataResult<List<Kurs>> findAllByOrderByAdAsc() {
		List<Kurs> kurslar = kursDao.findAllByOrderByAdAsc();
		return new SuccessDataResult<List<Kurs>>(kurslar,"Kurslar Sıralanmış Olarak Döndürüldü.");
	}
	
}
