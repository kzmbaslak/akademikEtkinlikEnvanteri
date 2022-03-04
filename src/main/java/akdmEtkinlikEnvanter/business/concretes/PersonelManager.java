package akdmEtkinlikEnvanter.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.BelgeService;
import akdmEtkinlikEnvanter.business.abstracts.PersonelService;
import akdmEtkinlikEnvanter.business.abstracts.TezService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessResult;
import akdmEtkinlikEnvanter.dataAccess.abstracts.KursDao;
import akdmEtkinlikEnvanter.dataAccess.abstracts.PersonelDao;
import akdmEtkinlikEnvanter.entities.concretes.Belge;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Rutbe;
import akdmEtkinlikEnvanter.entities.concretes.Tez;
import desktopApp.ui.DesktopApplication;

@Service
public class PersonelManager implements PersonelService{

	@Autowired
	PersonelDao personelDao;
	@Autowired
	KursDao kursDao;
	@Autowired
	TezService tezService;
	@Autowired
	BelgeService belgeService;
	
	@Override
	public DataResult<Personel> add(Personel personel) {
//		Personel temp = personelDao.getBySicilIgnoreCase(personel.getSicil());
		if(personel.getRutbe() == null) {
			DesktopApplication.addConsole("Personel Rütbesi Null Olamaz!!!","personel.add()");
			return new ErrorDataResult<Personel>(null, "Personel Rütbesi Null Olamaz!!!");
		}
		Personel temp = personelDao.getBySicilIgnoreCaseAndRutbe_Sinif(personel.getSicil(), personel.getRutbe().getSinif());
		
		
		if(temp == null) {
			if(personel.getSicil() == null) {
				DesktopApplication.addConsole("Personel Sicili Null Olamaz!!!","personel.add()");
				return new ErrorDataResult<Personel>(null, "Personel Sicili Null Olamaz!!!");
			}
			else if(personel.getSicil().isEmpty()) {
				DesktopApplication.addConsole("Personel Sicili Boş Bırakılamaz!!!","personel.add()");
				return new ErrorDataResult<Personel>(null, "Personel Sicili Boş Bırakılamaz!!!");
			}
			else if(personel.getAd().isEmpty()) {
				DesktopApplication.addConsole("Personel Adı Boş Bırakılamaz!!!","personel.add()");
				return new ErrorDataResult<Personel>(null, "Personel Adı Boş Bırakılamaz!!!");
			}
			else if(personel.getSoyad().isEmpty()) {
				DesktopApplication.addConsole("Personel Soyadı Boş Bırakılamaz!!!","personel.add()");
				return new ErrorDataResult<Personel>(null, "Personel Soyadı Boş Bırakılamaz!!!");
			}
			else if(personel.getTcNo() != null && personel.getTcNo().length() != 11 && personel.getTcNo().length() != 0) {
				DesktopApplication.addConsole("TC No 11 karakter olmak zorundadır. ","personel.add()");
				return new ErrorDataResult<Personel>(personel, "TC No 11 karakter olmak zorundadır. ");
			}
			personelDao.save(personel);
			DesktopApplication.addConsole("Personel Eklendi."+personel,"personel.add()");
			return new SuccessDataResult<Personel>(personel,"Personel Eklendi.");
		}

		DesktopApplication.addConsole("Personel Zaten Mevcut. Personel = "+personel,"personel.add()");
		return new ErrorDataResult<Personel>(temp, "Personel Zaten Mevcut. ");
	}
	
	@Override
	public DataResult<Personel> save(Personel personel) {
		Personel temp = personelDao.getById(personel.getId());
		if(temp.getId() != 0 && personel.getId() != 0) {
			if(personel.getSicil() == null) {
				DesktopApplication.addConsole("Personel Sicili Null Olamaz!!!","personel.save()");
				return new ErrorDataResult<Personel>(null, "Personel Sicili Null Olamaz!!!");
			}
			else if(personel.getSicil().isEmpty()) {
				DesktopApplication.addConsole("Personel Sicili Boş Bırakılamaz!!!","personel.save()");
				return new ErrorDataResult<Personel>(null, "Personel Sicili Boş Bırakılamaz!!!");
			}
			else if(personel.getTcNo() != null && personel.getTcNo().length() != 11 && personel.getTcNo().length() != 0) {
				DesktopApplication.addConsole("TC No 11 karakterden küçük olamaz.","personel.save()");
				return new ErrorDataResult<Personel>(null, "TC No 11 karakterden küçük olamaz.");
			}
				
			personelDao.save(personel);
			DesktopApplication.addConsole("Personel Güncellendi. Personel: "+personel,"personel.save()");
			return new SuccessDataResult<Personel>(personel,"Personel Güncellendi.");
		}
		DesktopApplication.addConsole("Personel Mevcut Değildir.","personel.save()");
		return new ErrorDataResult<Personel>(personel, "Personel Mevcut Değildir.");
	}
	
	@Override
	public DataResult<Personel> getBySicilIgnoreCase(String sicil) {
		Personel personel = personelDao.getBySicilIgnoreCase(sicil);
		if(personel != null)
			return new SuccessDataResult<Personel>(personel);
		return new ErrorDataResult<Personel>(personel, "Bu Sicilde Personel Bulunamadı.");
	}
	
	@Override
	public DataResult<Personel> getById(int id) {
		Personel personel = personelDao.getById(id);
		try {
			personel.getTcNo();
		}
		catch(Exception e) {
			return new ErrorDataResult<Personel>(personel, "Bu Personel Mevcut Değildir.");
		}
		return new SuccessDataResult<Personel>(personel);
	}
	
	@Override
	public DataResult<List<Personel>> findAll() {
		List<Personel> temp = personelDao.findAll();
		if(temp == null)
			return new ErrorDataResult<List<Personel>>(null,"Veri tabanında personel mevcut değildir.");
		return new SuccessDataResult<List<Personel>>(temp);
	}

	@Override
	public Result delete(Personel personel) {
		if(personel != null) {
			List<Kurs> kurslar = kursDao.findByPersoneller_Id(personel.getId());
			for (Kurs kurs : kurslar) {
				kurs.getPersoneller().removeIf(p -> p.getId() == personel.getId());
				kursDao.save(kurs);
			}
			List<Tez> tezler = tezService.findByPersonel(personel).getData();
			for (Tez tez : tezler) {
				tezService.delete(tez);
			}
			List<Belge> belgeler = belgeService.findByPersonel(personel).getData();
			for (Belge belge : belgeler) {
				belgeService.delete(belge);
			}
			personelDao.delete(personel);
			return new SuccessResult("Personel Silindi.");
		}
		return new ErrorResult("Böyle Bir Personel Bulunmamaktadır.");
		
	}

	@Override
	public DataResult<List<Personel>> getByBirlik(Birlik birlik) {
		List<Personel> temp = personelDao.getByBirlik(birlik);
		if(temp == null)
			return new ErrorDataResult<List<Personel>>(null,"Veri tabanında bu birliğe ait personel mevcut değildir.");
		return new SuccessDataResult<List<Personel>>(temp);
	}

	@Override
	public DataResult<Personel> getBySinavlar_Id(int sinavSonucId) {
		Personel temp = personelDao.getBySinavlar_Id(sinavSonucId);
		if(temp == null)
			return new ErrorDataResult<Personel>(null,"Veri tabanında bu sınav sonucuna ait personel mevcut değildir.");
		return new SuccessDataResult<Personel>(temp);
	}

	@Override
	public DataResult<List<Personel>> findByAdContainingIgnoreCaseAndSoyadContainingIgnoreCaseAndSicilContainingIgnoreCaseAndTcNoContainingIgnoreCaseAndTelContainingIgnoreCaseAndAciklamaContainingIgnoreCaseAndBirlik_adContainingIgnoreCaseAndRutbe_adContainingIgnoreCase(
			String ad, String soyad, String sicil, String tcNo, String tel, String aciklama, String birlikAd,
			String rutbeAd) {
		List<Personel> personeller = new ArrayList<>();
		if(birlikAd.isEmpty()) {
			if(rutbeAd.isEmpty()) {
				personeller = personelDao.findByAdContainingIgnoreCaseAndSoyadContainingIgnoreCaseAndSicilContainingIgnoreCaseAndTcNoContainingIgnoreCaseAndTelContainingIgnoreCaseAndAciklamaContainingIgnoreCase(ad, soyad, sicil, tcNo, tel, aciklama);
				return new SuccessDataResult<List<Personel>>(personeller, "Personeller Döndürüldü");
			}
			else {
				personeller = personelDao.findByAdContainingIgnoreCaseAndSoyadContainingIgnoreCaseAndSicilContainingIgnoreCaseAndTcNoContainingIgnoreCaseAndTelContainingIgnoreCaseAndAciklamaContainingIgnoreCaseAndBirlikIsNullAndRutbeIsNotNull(ad, soyad, sicil, tcNo, tel, aciklama);
			}
		}
		else if(rutbeAd.isEmpty()) {
			personeller = personelDao.findByAdContainingIgnoreCaseAndSoyadContainingIgnoreCaseAndSicilContainingIgnoreCaseAndTcNoContainingIgnoreCaseAndTelContainingIgnoreCaseAndAciklamaContainingIgnoreCaseAndBirlikIsNotNullAndRutbeIsNull(ad, soyad, sicil, tcNo, tel, aciklama);
		}
		personeller.addAll(personelDao.findByAdContainingIgnoreCaseAndSoyadContainingIgnoreCaseAndSicilContainingIgnoreCaseAndTcNoContainingIgnoreCaseAndTelContainingIgnoreCaseAndAciklamaContainingIgnoreCaseAndBirlik_adContainingIgnoreCaseAndRutbe_adContainingIgnoreCase(ad, soyad, sicil, tcNo, tel, aciklama, birlikAd, rutbeAd));
		return new SuccessDataResult<List<Personel>>(personeller, "Personeller Döndürüldü");
	}

	@Override
	public DataResult<List<Personel>> findByAdContainingIgnoreCase(String personelAd) {
		List<Personel> personeller = personelDao.findByAdContainingIgnoreCase(personelAd);
		return new SuccessDataResult<List<Personel>>(personeller, "Personeller Döndürüldü");
	}

	@Override
	public DataResult<List<Personel>> findAllByOrderByAdAsc() {
		List<Personel> personeller = personelDao.findAllByOrderByAdAsc();
		return new SuccessDataResult<List<Personel>>(personeller, "Personeller Sıralanmış Olarak Döndürüldü.");
	}

	@Override
	public DataResult<List<Personel>> findByIdNotInOrderByAdAsc(List<Integer> personellerId) {
		List<Personel> personeller = personelDao.findByIdNotInOrderByAdAsc(personellerId);
		return new SuccessDataResult<List<Personel>>(personeller,"Personeller Döndürüldü.");
	}

	@Override
	public DataResult<List<Personel>> getByYayinlar_IdOrderByAdAsc(int yayinId) {
		List<Personel> personeller = personelDao.getByYayinlar_IdOrderByAdAsc(yayinId);
		if(personeller != null) {
			return new SuccessDataResult<List<Personel>>(personeller,"Personeller Döndürüldü.");		
		}
		return new ErrorDataResult<List<Personel>>(personeller,"Bu Yyına Ait Personel Bulunamadı.");
	}

	@Override
	public DataResult<List<Personel>> getByKitaplar_IdOrderByAdAsc(int kitapId) {
		List<Personel> personeller = personelDao.getByKitaplar_IdOrderByAdAsc(kitapId);
		if(personeller != null) {
			return new SuccessDataResult<List<Personel>>(personeller,"Personeller Döndürüldü.");		
		}
		return new ErrorDataResult<List<Personel>>(personeller,"Bu Kitaba Ait Personel Bulunamadı.");
	}
	
}