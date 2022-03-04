package akdmEtkinlikEnvanter.business.concretes;

import java.util.List;

//import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.YuksekOgrenimService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessResult;
import akdmEtkinlikEnvanter.dataAccess.abstracts.YuksekOgrenimDao;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.YuksekOgrenim;
import desktopApp.ui.DesktopApplication;

@Service
public class YuksekOgrenimManager implements YuksekOgrenimService{

	@Autowired
	YuksekOgrenimDao yuksekOgrenimDao;
	
	@Override
//	public DataResult<YuksekOgrenim> add(@Valid YuksekOgrenim yuksekOgrenim) {
	public DataResult<YuksekOgrenim> add( YuksekOgrenim yuksekOgrenim) {
		YuksekOgrenim temp = yuksekOgrenimDao.getByAlanAdi(yuksekOgrenim.getAlanAdi());
		if(temp == null) {
			if(yuksekOgrenim.getAlanAdi() == null) {
				DesktopApplication.addConsole("Yüksek Öğrenim Alan Adı Null Olamaz!!!", "yuksekOgrenim.add()");
				return new ErrorDataResult<YuksekOgrenim>(null, "Yüksek Öğrenim Alan Adı Null Olamaz!!!");
			}
			else if(yuksekOgrenim.getAlanAdi().isEmpty()) {
				DesktopApplication.addConsole("Yüksek Öğrenim Alan Adı Boş Bırakılamaz!!!",  "yuksekOgrenim.add()");
				return new ErrorDataResult<YuksekOgrenim>(null, "Yüksek Öğrenim Alan Adı Boş Bırakılamaz!!!");
			}
			yuksekOgrenimDao.save(yuksekOgrenim);
			DesktopApplication.addConsole("Yüksek Öğrenim Eklendi. Yüksek Öğrenim: "+yuksekOgrenim,  "yuksekOgrenim.add()");
			return new SuccessDataResult<YuksekOgrenim>(yuksekOgrenim,"Yüksek Öğrenim Eklendi.");
		}
		DesktopApplication.addConsole("Yüksek Öğrenim Zaten Mevcut. Yüksek Öğrenim: "+temp,  "yuksekOgrenim.add()");
		return new ErrorDataResult<YuksekOgrenim>(temp, "Yüksek Öğrenim Zaten Mevcut");
		
	}
	
	public DataResult<List<YuksekOgrenim>> getAll(){
		return new SuccessDataResult<List<YuksekOgrenim>>(this.yuksekOgrenimDao.findAll(),"Veriler Başarıyla Getirildi.");
	}

	@Override
	public DataResult<YuksekOgrenim> getById(int id) {
		YuksekOgrenim yuksekOgrenim = yuksekOgrenimDao.getById(id);
		try {
			yuksekOgrenim.getAlanAdi();
		}
		catch(Exception e) {
			return new ErrorDataResult<YuksekOgrenim>(yuksekOgrenim, "Bu Yüksek Öğrenim Mevcut Değildir.");
		}
		return new SuccessDataResult<YuksekOgrenim>(yuksekOgrenim);
		
	}

	@Override
	public DataResult<YuksekOgrenim> save(YuksekOgrenim yuksekOgrenim) {
		YuksekOgrenim temp = yuksekOgrenimDao.getById(yuksekOgrenim.getId());
		if(temp.getId() != 0) {
			if(yuksekOgrenim.getAlanAdi() == null) {
				DesktopApplication.addConsole( "Alan Adı Null Olamaz!!!",  "yuksekOgrenim.save()");
				return new ErrorDataResult<YuksekOgrenim>(null, "Alan Adı Null Olamaz!!!");
			}
			else if(yuksekOgrenim.getAlanAdi().isEmpty()) {
				DesktopApplication.addConsole("Alan Adı Boş Bırakılamaz!!!",  "yuksekOgrenim.save()");
				return new ErrorDataResult<YuksekOgrenim>(null, "Alan Adı Boş Bırakılamaz!!!");
			}
			yuksekOgrenimDao.save(yuksekOgrenim);
			DesktopApplication.addConsole("Yüksek Öğrenim Güncellendi. Yüksek Öğrenim: "+yuksekOgrenim,  "yuksekOgrenim.save()");
			return new SuccessDataResult<YuksekOgrenim>(yuksekOgrenim,"Yüksek Öğrenim Güncellendi.");		
		}
		DesktopApplication.addConsole("Bu Yüksek Öğrenim Bulunmamaktadır. Yüksek Öğrenim: "+yuksekOgrenim,  "yuksekOgrenim.save()");
		return new ErrorDataResult<YuksekOgrenim>(yuksekOgrenim,"Bu Yüksek Öğrenim Bulunmamaktadır.");
	}

	@Override
	public Result delete(YuksekOgrenim yuksekOgrenim) {
		YuksekOgrenim temp = yuksekOgrenimDao.getById(yuksekOgrenim.getId());
		if(temp != null) {
			
			yuksekOgrenimDao.delete(yuksekOgrenim);
			return new SuccessResult("Yüksek Öğrenim Silindi.");
		}
		return new ErrorResult("Böyle Bir Yüksek Öğrenim Bulunmamaktadır.");
		
	}

	@Override
	public DataResult<List<YuksekOgrenim>> getAllByOrderByAlanAdiAsc() {
		return new SuccessDataResult<List<YuksekOgrenim>>(this.yuksekOgrenimDao.getAllByOrderByAlanAdiAsc(),"Veriler Başarıyla Getirildi.");
	}

	

}
