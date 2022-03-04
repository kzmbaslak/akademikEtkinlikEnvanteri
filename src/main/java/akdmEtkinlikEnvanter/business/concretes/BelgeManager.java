package akdmEtkinlikEnvanter.business.concretes;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.BelgeService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessResult;
import akdmEtkinlikEnvanter.dataAccess.abstracts.BelgeDao;
import akdmEtkinlikEnvanter.dataAccess.abstracts.PersonelDao;
import akdmEtkinlikEnvanter.entities.concretes.Belge;
import akdmEtkinlikEnvanter.entities.concretes.BelgeTuru;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Tez;
import desktopApp.utilities.javaFxOperations.FileOperations;

@Service
public class BelgeManager implements BelgeService{

	@Autowired
	BelgeDao belgeDao;
	@Autowired
	PersonelDao personelDao;
	
	@Autowired
	FileOperations fileOperations;
	final String destinationPath = System.getProperty("user.dir")+"/belgeler";
	
	@Override
	public DataResult<Belge> add(Belge belge) {
		if(belge == null)
			return new ErrorDataResult<Belge>(null, "Belge Null Olamaz!!!");
		
		Belge temp = belgeDao.getByPersonelAndAdIgnoreCase(belge.getPersonel(), belge.getAd());
		if(temp == null) {
			if(belge.getAd() == null) {
				return new ErrorDataResult<Belge>(null, "Belge Adı Null Olamaz!!!");
			}
			else if(belge.getAd().isEmpty()) {
				return new ErrorDataResult<Belge>(null, "Belge Adı Boş Bırakılamaz!!!");
			}
			else if(belge.getBelgeTuru() == null) {
				return new ErrorDataResult<Belge>(null, "Belge Türü Null Olamaz!!!");
			}
			else if(belge.getFile() == null) {
				return new ErrorDataResult<Belge>(null,"Belge Dosyası Null Olamaz.");
			}
			//dosya işlemleri
			else if(!belge.getFile().exists()) {
				return new ErrorDataResult<Belge>(null, "Dosya Mevcut Değildir.!!!");
			}
			else{
				DataResult<String> result = fileOperations.copyFile(belge.getFile().getAbsolutePath(), destinationPath);
				if(!result.isSuccess())
					return new ErrorDataResult<>(result.getMessage());
				else
					belge.setFile(new File(result.getData()));
			}
			belgeDao.save(belge);
			return new SuccessDataResult<Belge>(belge,"Belge Eklendi");		
		}

		return new ErrorDataResult<Belge>(temp,"Bu Personele Ait Aynı isimde bir belge zaten var.");
		
	}

	@Override
	public DataResult<Belge> save(Belge belge) {
		Belge temp = belgeDao.getById(belge.getId());
		if(temp.getId() != 0) {
			if(belge.getAd() == null) {
				return new ErrorDataResult<Belge>(null, "Belge Adı Null Olamaz!!!");
			}
			else if(belge.getAd().isEmpty()) {
				return new ErrorDataResult<Belge>(null, "Belge Adı Boş Bırakılamaz!!!");
			}
			else if(belge.getBelgeTuru() == null) {
				return new ErrorDataResult<Belge>(null, "Belge Türü Null Olamaz!!!");
			}
			//dosya işlemleri
			else if(belge.getFile() == null)
				return new ErrorDataResult<Belge>(null,"Belge Dosyası Null Olamaz.");
			else if(!belge.getFile().exists()) {
				return new ErrorDataResult<Belge>(null, "Dosya Mevcut Değildir.!!!");
			}
			else {
				if(temp.getFile() != null) {
					if(!temp.getFile().equals(belge.getFile())) {
						temp.getFile().delete();
						DataResult<String> result = fileOperations.copyFile(belge.getFile().getAbsolutePath(), destinationPath);
						if(!result.isSuccess())
							return new ErrorDataResult<>(result.getMessage());
						else
							belge.setFile(new File(result.getData()));
					}					
				}
				else {
					DataResult<String> result = fileOperations.copyFile(belge.getFile().getAbsolutePath(), destinationPath);
					if(!result.isSuccess())
						return new ErrorDataResult<>(result.getMessage());
					else
						belge.setFile(new File(result.getData()));
				}
			}
			belgeDao.save(belge);
			return new SuccessDataResult<Belge>(belge,"Belge Güncellendi");		
		}
		return new ErrorDataResult<Belge>(temp,"Bu belge mevcut değildir.");
	}
	
	@Override
	public Result delete(Belge belge) {
		Belge temp = belgeDao.getById(belge.getId());
		if(temp != null) {
			//dosya işlemleri
			Result result = fileOperations.deleteFile(belge.getFile());
			if(!result.isSuccess())
				return new ErrorResult("Belgeye Ait Dosya Silinemedi. Dosya Açık Olabilir.");
			
			belgeDao.delete(belge);
			return new SuccessResult("Belge Silindi.");
		}
		return new ErrorResult("Böyle Bir Belge Bulunmamaktadır.");
		
	}

	@Override
	public DataResult<Belge> getById(int belgeId) {
		Belge belge = belgeDao.getById(belgeId);
		try {
			belge.getAd();
		}
		catch(Exception e) {
			return new ErrorDataResult<Belge>(belge, "Bu Belge Mevcut Değildir.");
		}
		return new SuccessDataResult<Belge>(belge);
	}

	@Override
	public DataResult<List<Belge>> getAll() {
		return new SuccessDataResult<List<Belge>>(belgeDao.findAll());
	}

	@Override
	public DataResult<List<Belge>> findByPersonel(Personel personel) {
		List<Belge> belgeler = belgeDao.findByPersonel(personel);
		if(belgeler == null) {
			return new ErrorDataResult<List<Belge>>(null,"Bu personele ait belge bulunamadı!!!");
		}
		
		return new SuccessDataResult<List<Belge>>(belgeler,"Belgeler bulundu.");
	}

	@Override
	public DataResult<List<Belge>> getByBelgeTuru(BelgeTuru belgeTuru) {
		return new SuccessDataResult<List<Belge>>(belgeDao.getByBelgeTuru(belgeTuru), "Belgeler Döndürüldü");
	}

	@Override
	public DataResult<List<Belge>> findByAdContainingIgnoreCaseAndBelgeTuru_adContainingAndPersonel_sicilContaining
	(String ad, String belgeTuruAd, String personelSicil) {
		List<Belge> belgeler;
		if(!belgeTuruAd.isEmpty())
			belgeler = belgeDao.findByAdContainingIgnoreCaseAndBelgeTuru_adAndPersonel_sicilContaining(ad, belgeTuruAd, personelSicil);
		else
			belgeler = belgeDao.findByAdContainingIgnoreCaseAndBelgeTuru_adContainingAndPersonel_sicilContaining(ad, belgeTuruAd, personelSicil);
		
			
		return new SuccessDataResult<List<Belge>>(belgeler, "Belgeler Döndürüldü");	
	}
}
