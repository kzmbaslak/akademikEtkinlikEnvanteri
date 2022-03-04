package akdmEtkinlikEnvanter.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.SinavTuruService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessDataResult;
import akdmEtkinlikEnvanter.dataAccess.abstracts.SinavTuruDao;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.SinavTuru;
import desktopApp.ui.DesktopApplication;

@Service
public class SinavTuruManager implements SinavTuruService{

	@Autowired
	SinavTuruDao sinavTuruDao;
	
	@Override
	public DataResult<SinavTuru> add(SinavTuru sinavTuru) {
		SinavTuru temp = sinavTuruDao.getByAd(sinavTuru.getAd());
		if(temp == null) {
			if(sinavTuru.getAd() == null) {
				DesktopApplication.addConsole("Sınav Türü Adı Null Olamaz!!!", "sinavTuru.add()");
				return new ErrorDataResult<SinavTuru>(null, "Sınav Türü Adı Null Olamaz!!!");
			}
			else if(sinavTuru.getAd().isEmpty()) {
				DesktopApplication.addConsole("Sınav Türü Adı Boş Bırakılamaz!!!", "sinavTuru.add()");
				return new ErrorDataResult<SinavTuru>(null, "Sınav Türü Adı Boş Bırakılamaz!!!");
			}
			
			sinavTuruDao.save(sinavTuru);
			DesktopApplication.addConsole("Sınav Türü Eklendi. Sınav Türü: "+sinavTuru, "sinavTuru.add()");
			return new SuccessDataResult<SinavTuru>(sinavTuru,"Sınav Türü Eklendi.");
		}
		DesktopApplication.addConsole("Sınav Türü Zaten Mevcut Sınav Türü: "+temp, "sinavTuru.add()");
		return new ErrorDataResult<SinavTuru>(temp, "Sınav Türü Zaten Mevcut");
	}

	@Override
	public DataResult<SinavTuru> getById(int id) {
		SinavTuru sinavTuru = sinavTuruDao.getById(id);
		try {
			sinavTuru.getAd();
		}
		catch(Exception e) {
			return new ErrorDataResult<SinavTuru>(sinavTuru, "Bu Sınav Türü Mevcut Değildir.");
		}
		return new SuccessDataResult<SinavTuru>(sinavTuru);
	}

	@Override
	public DataResult<List<SinavTuru>> getAll() {
		List<SinavTuru> temp = sinavTuruDao.findAll();
		if(temp == null)
			return new ErrorDataResult<List<SinavTuru>>(null,"Veri tabanında sınav türü mevcut değildir.");
		return new SuccessDataResult<List<SinavTuru>>(temp);
	}

}
