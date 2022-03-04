package akdmEtkinlikEnvanter.business.concretes;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.KitapService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessResult;
import akdmEtkinlikEnvanter.dataAccess.abstracts.KitapDao;
import akdmEtkinlikEnvanter.entities.concretes.Belge;
import akdmEtkinlikEnvanter.entities.concretes.Kitap;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Tez;
import akdmEtkinlikEnvanter.entities.concretes.Yayin;
import akdmEtkinlikEnvanter.entities.concretes.YayineviDergi;
import desktopApp.utilities.javaFxOperations.FileOperations;

@Service
public class KitapManager implements KitapService {

	@Autowired
	KitapDao kitapDao;

	@Autowired
	FileOperations fileOperations;
	final String destinationPath = System.getProperty("user.dir")+"/kitaplar";
	
	@Override
	public DataResult<Kitap> add(Kitap kitap) {
		if(kitap == null)
			return new ErrorDataResult<Kitap>(null, "Kitap Null Olamaz!!!");
		
		Kitap temp = kitapDao.getByYayineviDergiAndAdIgnoreCase(kitap.getYayineviDergi(), kitap.getAd());
		if(temp == null) {
			if(kitap.getAd() == null) {
				return new ErrorDataResult<Kitap>(null, "Kitap Adı Null Olamaz!!!");
			}
			else if(kitap.getAd().isEmpty()) {
				return new ErrorDataResult<Kitap>(null, "Kitap Adı Boş Bırakılamaz!!!");
			}
			else if(kitap.getYazarlar() == null) {
				return new ErrorDataResult<Kitap>(null, "Kitabın Yazarı Null Olamaz!!!");
			}
			//dosya işlemleri
			if(kitap.getFile() != null && kitap.getFile().exists()) {
				DataResult<String> result = fileOperations.copyFile(kitap.getFile().getAbsolutePath(), destinationPath);
				if(!result.isSuccess())
					return new ErrorDataResult<>(result.getMessage());
				else
					kitap.setFile(new File(result.getData()));
			}
			kitapDao.save(kitap);
			return new SuccessDataResult<Kitap>(kitap,"Kitap Eklendi");		
		}

		return new ErrorDataResult<Kitap>(temp,"Bu Yayınevine Ait Aynı isimde bir kitap zaten var.");
		
	}
	
	@Override
	public DataResult<Kitap> add(Kitap kitap, Personel personel) {
		if(kitap == null)
			return new ErrorDataResult<Kitap>(null, "Kitap Null Olamaz!!!");
		
		Kitap temp = kitapDao.getByYayineviDergiAndAdIgnoreCase(kitap.getYayineviDergi(), kitap.getAd());
		if(temp == null) {
			if(kitap.getAd() == null) {
				return new ErrorDataResult<Kitap>(null, "Kitap Adı Null Olamaz!!!");
			}
			else if(kitap.getAd().isEmpty()) {
				return new ErrorDataResult<Kitap>(null, "Kitap Adı Boş Bırakılamaz!!!");
			}
			else if(kitap.getYazarlar() == null) {
				return new ErrorDataResult<Kitap>(null, "Kitabın Yazarı Null Olamaz!!!");
			}
			//dosya işlemleri
			if(kitap.getFile() != null && kitap.getFile().exists()) {
				DataResult<String> result = fileOperations.copyFile(kitap.getFile().getAbsolutePath(), destinationPath);
				if(!result.isSuccess())
					return new ErrorDataResult<>(result.getMessage());
				else
					kitap.setFile(new File(result.getData()));
			}
			kitapDao.save(kitap);
			return new SuccessDataResult<Kitap>(kitap,"Kitap Eklendi");		
		}
		else {
			Kitap tmp = kitapDao.getByAdAndYazarlar_Id(kitap.getAd(), personel.getId());
			if(tmp == null) {
				temp.getYazarlar().add(personel);
				kitapDao.save(temp);
				return new SuccessDataResult<Kitap>(kitap,"Kitap Düzenlendi");
			}
		}
		return new ErrorDataResult<Kitap>(temp,"Bu Yayınevine Ait Aynı isimde bir kitap zaten var.");
	}
	
	@Override
	public DataResult<Kitap> save(Kitap kitap) {
		Kitap temp = kitapDao.getById(kitap.getId());
		if(temp.getId() != 0) {
			if(kitap.getAd() == null) {
				return new ErrorDataResult<Kitap>(null, "Kitap Adı Null Olamaz!!!");
			}
			else if(kitap.getAd().isEmpty()) {
				return new ErrorDataResult<Kitap>(null, "Kitap Adı Boş Bırakılamaz!!!");
			}
			else if(kitap.getYazarlar() == null) {
				return new ErrorDataResult<Kitap>(null, "Kitabın Yazarı Null Olamaz!!!");
			}
			//dosya işlemleri
			else if(kitap.getFile() != null) {
				if(!kitap.getFile().exists())
					return new ErrorDataResult<Kitap>(kitap,"Kitaba Ait Dosya Bulunamadı.");
				else {
					if(temp.getFile() != null) {
						if(!temp.getFile().equals(kitap.getFile())) {
							temp.getFile().delete();
							DataResult<String> result = fileOperations.copyFile(kitap.getFile().getAbsolutePath(), destinationPath);
							if(!result.isSuccess())
								return new ErrorDataResult<>(result.getMessage());
							else
								kitap.setFile(new File(result.getData()));
						}					
					}
					else {
						DataResult<String> result = fileOperations.copyFile(kitap.getFile().getAbsolutePath(), destinationPath);
						if(!result.isSuccess())
							return new ErrorDataResult<>(result.getMessage());
						else
							kitap.setFile(new File(result.getData()));
					}
				}
					
			}
			else if(temp.getFile() != null) {
				temp.getFile().delete();
			}
			kitapDao.save(kitap);
			return new SuccessDataResult<Kitap>(kitap,"Kitap Güncellendi");			
		}
		return new ErrorDataResult<Kitap>(temp,"Bu kitap mevcut değildir.");
	}

	@Override
	public DataResult<Kitap> getById(int kitapId) {
		Kitap kitap = kitapDao.getById(kitapId);
		try {
			kitap.getAd();
		}
		catch(Exception e) {
			return new ErrorDataResult<Kitap>(kitap, "Bu Kitap Mevcut Değildir.");
		}
		return new SuccessDataResult<Kitap>(kitap);
	}

	@Override
	public DataResult<List<Kitap>> getAll() {
		return new SuccessDataResult<List<Kitap>>(kitapDao.findAll());
	}

	@Override
	public Result delete(Kitap kitap) {
		Kitap temp = kitapDao.getById(kitap.getId());
		if(temp != null) {
			//dosya işlemleri
			Result result = fileOperations.deleteFile(kitap.getFile());
			if(!result.isSuccess())
				return new ErrorResult("Kitaba Ait Dosya Silinemedi. Dosya Açık Olabilir.");
			
			kitapDao.delete(kitap);
			return new SuccessResult("Kitap Silindi.");
		}
		return new ErrorResult("Böyle Bir Kitap Bulunmamaktadır.");
	}

	@Override
	public DataResult<List<Kitap>> findByYazarlar_Id(int yazarId) {
		List<Kitap> kitaplar = kitapDao.findByYazarlar_Id(yazarId);
		if(kitaplar != null) {
			return new SuccessDataResult<List<Kitap>>(kitaplar,"Kitaplar Döndürüldü.");		
		}
		return new ErrorDataResult<List<Kitap>>(kitaplar,"Bu Personele Ait Kitap Bulunamadı.");
	}

	@Override
	public DataResult<List<Kitap>> getByYayineviDergi(YayineviDergi yayineviDergi) {
		List<Kitap> kitaplar = kitapDao.getByYayineviDergi(yayineviDergi);
		if(kitaplar != null) {
			return new SuccessDataResult<List<Kitap>>(kitaplar,"Kitaplar Döndürüldü.");		
		}
		return new ErrorDataResult<List<Kitap>>(kitaplar,"Bu Yayınevine Ait Kitap Bulunamadı.");
	}

	@Override
	public DataResult<List<Kitap>> findByYayinTarihiBetweenAndAdContainingIgnoreCaseAndYayineviDergi_adContaining(
			LocalDate from, LocalDate to, String ad, String yayineviDergiAd, int yazarId) {
		List<Kitap> kitaplar;
		if(from == null && to == null) {
			if(!yayineviDergiAd.isEmpty())
				kitaplar = kitapDao.findByAdContainingIgnoreCaseAndYayineviDergi_ad(ad, yayineviDergiAd);
			else
				kitaplar = kitapDao.findByAdContainingIgnoreCaseAndYayineviDergi_adContaining(ad, yayineviDergiAd);
		}
		else {
			if(from == null)
				from = LocalDate.of(1900, 1, 1);
			if(to == null)
				to = LocalDate.now();
			if(!yayineviDergiAd.isEmpty())
				kitaplar = kitapDao.findByYayinTarihiBetweenAndAdContainingIgnoreCaseAndYayineviDergi_ad(
						from, to, ad, yayineviDergiAd);
			else
				kitaplar = kitapDao.findByYayinTarihiBetweenAndAdContainingIgnoreCaseAndYayineviDergi_adContaining(
						from, to, ad, yayineviDergiAd);
		}
		
		if(yazarId != 0) {
			List<Kitap> resultKitaplar = new ArrayList<>();
			for (Kitap kitap : kitaplar) {
				for (Personel personel : kitap.getYazarlar()) {
					if(personel.getId() == yazarId) {
						resultKitaplar.add(kitap);
						break;
					}	
				}
			}
			return new SuccessDataResult<List<Kitap>>(resultKitaplar, "Kitaplar Döndürüldü");	
		}
		return new SuccessDataResult<List<Kitap>>(kitaplar, "Kitaplar Döndürüldü");	
		
		
	}


}
