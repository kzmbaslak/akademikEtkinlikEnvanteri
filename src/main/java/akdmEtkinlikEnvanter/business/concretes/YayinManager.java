package akdmEtkinlikEnvanter.business.concretes;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.YayinService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessResult;
import akdmEtkinlikEnvanter.dataAccess.abstracts.YayinDao;
import akdmEtkinlikEnvanter.entities.concretes.Belge;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Tez;
import akdmEtkinlikEnvanter.entities.concretes.Yayin;
import akdmEtkinlikEnvanter.entities.concretes.YayineviDergi;
import desktopApp.utilities.javaFxOperations.FileOperations;

@Service
public class YayinManager implements YayinService{

	@Autowired
	YayinDao yayinDao;

	@Autowired
	FileOperations fileOperations;
	final String destinationPath = System.getProperty("user.dir")+"/yayınlar";
	
	@Override
	public DataResult<Yayin> add(Yayin yayin) {
		if(yayin == null)
			return new ErrorDataResult<Yayin>(null, "Yayin Null Olamaz!!!");
		
		Yayin temp = yayinDao.getByAdIgnoreCase(yayin.getAd());
		if(temp == null) {
			if(yayin.getAd() == null) {
				return new ErrorDataResult<Yayin>(null, "Yayin Adı Null Olamaz!!!");
			}
			else if(yayin.getAd().isEmpty()) {
				return new ErrorDataResult<Yayin>(null, "Yayin Adı Boş Bırakılamaz!!!");
			}
			else if(yayin.getYazarlar() == null) {
				return new ErrorDataResult<Yayin>(null, "Yayin Yazarı Null Olamaz!!!");
			}
			//dosya işlemleri
			if(yayin.getFile() != null) {
				if(yayin.getFile().exists()) {
					DataResult<String> result = fileOperations.copyFile(yayin.getFile().getAbsolutePath(), destinationPath);
					if(!result.isSuccess())
						return new ErrorDataResult<>(result.getMessage());
					else
						yayin.setFile(new File(result.getData()));
				}
				else
					return new ErrorDataResult<Yayin>(yayin,"Yayına Ait Dosya Bulunamadı.");
				
			}
			yayinDao.save(yayin);
			return new SuccessDataResult<Yayin>(yayin,"Yayin Eklendi");		
		}

		return new ErrorDataResult<Yayin>(temp,"Bu Yayınevine Ait Aynı isimde bir yayın zaten var.");
	}
	

	@Override
	public DataResult<Yayin> add(Yayin yayin, Personel personel) {
		if(yayin == null)
			return new ErrorDataResult<Yayin>(null, "Yayin Null Olamaz!!!");
		
		Yayin temp = yayinDao.getByAdIgnoreCase(yayin.getAd());
		if(temp == null) {
			if(yayin.getAd() == null) {
				return new ErrorDataResult<Yayin>(null, "Yayin Adı Null Olamaz!!!");
			}
			else if(yayin.getAd().isEmpty()) {
				return new ErrorDataResult<Yayin>(null, "Yayin Adı Boş Bırakılamaz!!!");
			}
			else if(yayin.getYazarlar() == null) {
				return new ErrorDataResult<Yayin>(null, "Yayin Yazarı Null Olamaz!!!");
			}
			//dosya işlemleri
			if(yayin.getFile() != null) {
				if(yayin.getFile().exists()) {
					DataResult<String> result = fileOperations.copyFile(yayin.getFile().getAbsolutePath(), destinationPath);
					if(!result.isSuccess())
						return new ErrorDataResult<>(result.getMessage());
					else
						yayin.setFile(new File(result.getData()));
				}
				else
					return new ErrorDataResult<Yayin>(yayin,"Yayına Ait Dosya Bulunamadı.");
				
			}
			yayinDao.save(yayin);
			return new SuccessDataResult<Yayin>(yayin,"Yayin Eklendi");		
		}
		else {
			Yayin tmp = yayinDao.getByAdAndYazarlar_Id(yayin.getAd(), personel.getId());
			if(tmp == null) {
				temp.getYazarlar().add(personel);
				yayinDao.save(temp);
				return new SuccessDataResult<Yayin>(yayin,"Yayın Düzenlendi");
			}
		}
		return new ErrorDataResult<Yayin>(temp,"Bu Yayınevine Ait Aynı isimde bir yayın zaten var.");
	}

	@Override
	public DataResult<Yayin> save(Yayin yayin) {
		Yayin temp = yayinDao.getById(yayin.getId());
		if(temp.getId() != 0) {
			if(yayin.getAd() == null) {
				return new ErrorDataResult<Yayin>(null, "Yayin Adı Null Olamaz!!!");
			}
			else if(yayin.getAd().isEmpty()) {
				return new ErrorDataResult<Yayin>(null, "Yayin Adı Boş Bırakılamaz!!!");
			}
			else if(yayin.getYazarlar() == null) {
				return new ErrorDataResult<Yayin>(null, "Yayin Yazarı Null Olamaz!!!");
			}
			//dosya işlemleri
			else if(yayin.getFile() != null) {
				if(!yayin.getFile().exists())
					return new ErrorDataResult<Yayin>(yayin,"Teze Ait Dosya Bulunamadı.");
				else {
					if(temp.getFile() != null) {
						if(!temp.getFile().equals(yayin.getFile())) {
							temp.getFile().delete();
							DataResult<String> result = fileOperations.copyFile(yayin.getFile().getAbsolutePath(), destinationPath);
							if(!result.isSuccess())
								return new ErrorDataResult<>(result.getMessage());
							else
								yayin.setFile(new File(result.getData()));
						}					
					}
					else {
						DataResult<String> result = fileOperations.copyFile(yayin.getFile().getAbsolutePath(), destinationPath);
						if(!result.isSuccess())
							return new ErrorDataResult<>(result.getMessage());
						else
							yayin.setFile(new File(result.getData()));
					}
				}
					
			}
			else if(temp.getFile() != null) {
				temp.getFile().delete();
			}
			yayinDao.save(yayin);
			return new SuccessDataResult<Yayin>(yayin,"Yayin Güncellendi.");
		}
		return new ErrorDataResult<Yayin>(temp,"Bu yayın mevcut değildir.");
	}

	@Override
	public DataResult<Yayin> getById(int yayinId) {
		Yayin yayin = yayinDao.getById(yayinId);
		try {
			yayin.getAd();
		}
		catch(Exception e) {
			return new ErrorDataResult<Yayin>(yayin, "Bu Yayin Mevcut Değildir.");
		}
		return new SuccessDataResult<Yayin>(yayin);
	}

	@Override
	public DataResult<List<Yayin>> getAll() {
		return new SuccessDataResult<List<Yayin>>(yayinDao.findAll());
	}

	@Override
	public Result delete(Yayin yayin) {
		Yayin temp = yayinDao.getById(yayin.getId());
		if(temp != null) {
			//dosya işlemleri
			Result result = fileOperations.deleteFile(yayin.getFile());
			if(!result.isSuccess())
				return result;
			
			yayinDao.delete(yayin);
			return new SuccessResult("Yayın Silindi.");
		}
		return new ErrorResult("Böyle Bir Yayın Bulunmamaktadır.");
	}

	@Override
	public DataResult<List<Yayin>> findByYazarlar_Id(int yazarId) {
		List<Yayin> yayinlar = yayinDao.findByYazarlar_Id(yazarId);
		if(yayinlar != null) {
			return new SuccessDataResult<List<Yayin>>(yayinlar,"Yayınlar Döndürüldü.");		
		}
		return new ErrorDataResult<List<Yayin>>(yayinlar,"Bu Personele Ait Yayın Bulunamadı.");
	}

	@Override
	public DataResult<List<Yayin>> getByYayineviDergi(YayineviDergi yayineviDergi) {
		List<Yayin> yayinlar = yayinDao.getByYayineviDergi(yayineviDergi);
		if(yayinlar != null) {
			return new SuccessDataResult<List<Yayin>>(yayinlar,"Kitaplar Döndürüldü.");		
		}
		return new ErrorDataResult<List<Yayin>>(yayinlar,"Bu Yayınevi/Dergiye Ait Yayın Bulunamadı.");
	}

	@Override
	public DataResult<List<Yayin>> findByYayinTarihiBetweenAndAdContainingIgnoreCaseAndYayineviDergi_adContaining(
			LocalDate from, LocalDate to, String ad, String yayineviDergiAd, int yazarId) {
		List<Yayin> yayinlar;
		if(from == null && to == null) {
			if(!yayineviDergiAd.replace(" ", "").isEmpty()) {
				yayinlar = yayinDao.findByAdContainingIgnoreCaseAndYayineviDergi_ad(ad, yayineviDergiAd);
			}
			else {
				yayinlar = yayinDao.findByAdContainingIgnoreCase(ad);
			}
		}
		else {
			if(from == null)
				from = LocalDate.of(1900, 1, 1);
			if(to == null)
				to = LocalDate.now();
			if(!yayineviDergiAd.isEmpty())
				yayinlar = yayinDao.findByYayinTarihiBetweenAndAdContainingIgnoreCaseAndYayineviDergi_ad(
						from, to, ad, yayineviDergiAd);
			else
				yayinlar = yayinDao.findByYayinTarihiBetweenAndAdContainingIgnoreCase(
						from, to, ad);
		}
		
		if(yazarId != 0) {
			List<Yayin> resultYayinlar = new ArrayList<>();
			for (Yayin yayin : yayinlar) {
				for (Personel personel : yayin.getYazarlar()) {
					if(personel.getId() == yazarId) {
						resultYayinlar.add(yayin);
						break;
					}	
				}
			}
			return new SuccessDataResult<List<Yayin>>(resultYayinlar, "Yayınlar Döndürüldü");	
		}
		return new SuccessDataResult<List<Yayin>>(yayinlar, "Yayınlar Döndürüldü");	
		
		
	}


}
