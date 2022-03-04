package akdmEtkinlikEnvanter.business.concretes;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akdmEtkinlikEnvanter.business.abstracts.TezService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessResult;
import akdmEtkinlikEnvanter.dataAccess.abstracts.TezDao;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Sinif;
import akdmEtkinlikEnvanter.entities.concretes.Tez;
import akdmEtkinlikEnvanter.entities.concretes.TezTuru;
import akdmEtkinlikEnvanter.entities.concretes.UniversiteEnstitu;
import akdmEtkinlikEnvanter.entities.concretes.YuksekOgrenim;
import desktopApp.ui.DesktopApplication;
import desktopApp.utilities.javaFxOperations.FileOperations;

@Service
public class TezManager implements TezService{

	@Autowired
	TezDao tezDao;
	@Autowired 
	FileOperations fileOperations;
	
	final String destinationPath = System.getProperty("user.dir")+"/tezler";
	
//	@Override
//	public DataResult<Tez> add(Tez tez) {
//		if(tez == null)
//			return new ErrorDataResult<Tez>(null, "Tez Null Olamaz");
//		if(tez.getFile() != null && !tez.getFile().exists())
//			return new ErrorDataResult<Tez>(tez,"Teze Ait Dosya Bulunamadı.");
//		tez.setTezKonusu(tez.getTezKonusu().trim());
//		if(tez.getTezKonusu() != null) {
//			if(tez.getTezKonusu().toUpperCase().equals("TEZSİZ") || tez.getTezKonusu().equalsIgnoreCase("DERS AŞAMASI") || tez.getTezKonusu().equals("!")) {
//				
////				Tez temp = tezDao.getByTezKonusuAndPersonel(tez.getTezKonusu(), tez.getPersonel());
//				Tez temp = tezDao.getByTezKonusuAndPersonelAndYuksekOgrenim(tez.getTezKonusu(), tez.getPersonel(), tez.getYuksekOgrenim());
//				if(temp == null) {
//					//dosya işlemleri
//					if(tez.getFile() != null) {
//						if(tez.getFile().exists()) {
//							DataResult<String> result = fileOperations.copyFile(tez.getFile().getAbsolutePath(), destinationPath);
//							if(!result.isSuccess())
//								return new ErrorDataResult<>(result.getMessage());
//							else
//								tez.setFile(new File(result.getData()));
//						}
//						else
//							return new ErrorDataResult<Tez>(tez,"Teze Ait Dosya Bulunamadı.");
//						
//					}
//					tezDao.save(tez);
//					DesktopApplication.addConsole("Tez Eklendi. Tez: "+tez, "tez.add()");
//					return new SuccessDataResult<Tez>(tez,"Tez Eklendi.");
//				}
//				DesktopApplication.addConsole("Bu tez zaten kayıtlı. Tez: "+tez, "tez.add()");
//				return new ErrorDataResult<Tez>(tez,"Bu tez zaten kayıtlı.");
//				
//			}
//		}
//		Tez temp = tezDao.getByTezKonusuIgnoreCase(tez.getTezKonusu());
//		if(temp == null) {
//			if(tez.getTezKonusu() == null) {
//				DesktopApplication.addConsole("Tez Konusu Null Olamaz!!! Tez:"+tez, "tez.add()");
//				return new ErrorDataResult<Tez>(null, "Tez Konusu Null Olamaz!!!");
//			}
//			else if(tez.getTezKonusu().isEmpty()) {
//				DesktopApplication.addConsole("Tez Konusu Boş Bırakılamaz!!! Tez:"+tez, "tez.add()");
//				return new ErrorDataResult<Tez>(null, "Tez Konusu Boş Bırakılamaz!!!");
//			}
//			else if(tez.getTezTuru() == null) {
//				DesktopApplication.addConsole("Tez Türü Boş Bırakılamaz!!! Tez:"+tez, "tez.add()");
//				return new ErrorDataResult<Tez>(null, "Tez Türü Boş Bırakılamaz!!!");
//			}
//			else if(tez.getUniversiteEnstitu() == null) {
//				DesktopApplication.addConsole("Üniversite Enstitü Boş Bırakılamaz!!! Tez:"+tez, "tez.add()");
//				return new ErrorDataResult<Tez>(null, "Üniversite Enstitü Boş Bırakılamaz!!!");
//			}
//			else if(tez.getYuksekOgrenim() == null) {
//				DesktopApplication.addConsole("Yüksek Öğrenim Boş Bırakılamaz!!! Tez:"+tez, "tez.add()");
//				return new ErrorDataResult<Tez>(null, "Yüksek Öğrenim Boş Bırakılamaz!!!");
//			}
//			else if(tez.getPersonel() == null) {
//				DesktopApplication.addConsole("Personel Boş Bırakılamaz!!! Tez:"+tez, "tez.add()");
//				return new ErrorDataResult<Tez>(null, "Personel Boş Bırakılamaz!!!");
//			}
//			//dosya işlemleri
//			if(tez.getFile() != null && tez.getFile().exists()) {
//				DataResult<String> result = fileOperations.copyFile(tez.getFile().getAbsolutePath(), destinationPath);
//				if(!result.isSuccess())
//					return new ErrorDataResult<>(result.getMessage());
//				else
//					tez.setFile(new File(result.getData()));
//			}
//			tezDao.save(tez);
//			DesktopApplication.addConsole("Tez Eklendi. Tez: "+tez, "tez.add()");
//			return new SuccessDataResult<Tez>(tez,"Tez Eklendi.");
//		}
//		DesktopApplication.addConsole("Tez Zaten Mevcut. Tez: "+temp, "tez.add()");
//		return new ErrorDataResult<Tez>(temp, "Tez Zaten Mevcut");
//	}
	
	@Override
	public DataResult<Tez> add(Tez tez) {
		if(tez == null)
			return new ErrorDataResult<Tez>(null, "Tez Null Olamaz");
		if(tez.getFile() != null && !tez.getFile().exists())
			return new ErrorDataResult<Tez>(tez,"Teze Ait Dosya Bulunamadı.");
		tez.setTezKonusu(tez.getTezKonusu().trim());
		if(tez.getTezKonusu() != null) {
			Tez temp;
			if(tez.getTezKonusu().toUpperCase().equals("TEZSİZ") || tez.getTezKonusu().equalsIgnoreCase("DERS AŞAMASI") || tez.getTezKonusu().equals("!"))
				temp = tezDao.getByTezKonusuAndPersonelAndYuksekOgrenim(tez.getTezKonusu(), tez.getPersonel(), tez.getYuksekOgrenim());
			else
				temp = tezDao.getByTezKonusuIgnoreCase(tez.getTezKonusu());
			if(temp == null) {
				
				if(tez.getTezKonusu() == null) {
					DesktopApplication.addConsole("Tez Konusu Null Olamaz!!! Tez:"+tez, "tez.add()");
					return new ErrorDataResult<Tez>(null, "Tez Konusu Null Olamaz!!!");
				}
				else if(tez.getTezKonusu().isEmpty()) {
					DesktopApplication.addConsole("Tez Konusu Boş Bırakılamaz!!! Tez:"+tez, "tez.add()");
					return new ErrorDataResult<Tez>(null, "Tez Konusu Boş Bırakılamaz!!!");
				}
				else if(tez.getTezTuru() == null) {
					DesktopApplication.addConsole("Tez Türü Boş Bırakılamaz!!! Tez:"+tez, "tez.add()");
					return new ErrorDataResult<Tez>(null, "Tez Türü Boş Bırakılamaz!!!");
				}
				else if(tez.getUniversiteEnstitu() == null) {
					DesktopApplication.addConsole("Üniversite Enstitü Boş Bırakılamaz!!! Tez:"+tez, "tez.add()");
					return new ErrorDataResult<Tez>(null, "Üniversite Enstitü Boş Bırakılamaz!!!");
				}
				else if(tez.getYuksekOgrenim() == null) {
					DesktopApplication.addConsole("Yüksek Öğrenim Boş Bırakılamaz!!! Tez:"+tez, "tez.add()");
					return new ErrorDataResult<Tez>(null, "Yüksek Öğrenim Boş Bırakılamaz!!!");
				}
				else if(tez.getPersonel() == null) {
					DesktopApplication.addConsole("Personel Boş Bırakılamaz!!! Tez:"+tez, "tez.add()");
					return new ErrorDataResult<Tez>(null, "Personel Boş Bırakılamaz!!!");
				}
				//dosya işlemleri
				if(tez.getFile() != null) {
					if(tez.getFile().exists()) {
						DataResult<String> result = fileOperations.copyFile(tez.getFile().getAbsolutePath(), destinationPath);
						if(!result.isSuccess())
							return new ErrorDataResult<>(result.getMessage());
						else
							tez.setFile(new File(result.getData()));
					}
					else
						return new ErrorDataResult<Tez>(tez,"Teze Ait Dosya Bulunamadı.");
					
				}
				tezDao.save(tez);
				DesktopApplication.addConsole("Tez Eklendi. Tez: "+tez, "tez.add()");
				return new SuccessDataResult<Tez>(tez,"Tez Eklendi.");
			}
			DesktopApplication.addConsole("Tez Zaten Mevcut. Tez: "+temp, "tez.add()");
			return new ErrorDataResult<Tez>(temp, "Tez Zaten Mevcut");
		}
		else {
			return new ErrorDataResult<Tez>(null, "Tez Konusu Null Olamaz.");
			
		}
	}
	
	@Override
	public DataResult<Tez> save(Tez tez) {
		Tez temp = tezDao.getById(tez.getId());
		if(temp.getId() != 0) {
			if(tez.getTezKonusu() == null) {
				DesktopApplication.addConsole("Tez Konusu Null Olamaz!!!", "tez.save()");
				return new ErrorDataResult<Tez>(null, "Tez Konusu Null Olamaz!!!");
			}
			else if(tez.getTezKonusu().isEmpty()) {
				DesktopApplication.addConsole("Tez Konusu Boş Bırakılamaz!!!", "tez.save()");
				return new ErrorDataResult<Tez>(null, "Tez Konusu Boş Bırakılamaz!!!");
			}
			//dosya işlemleri
			else if(tez.getFile() != null) {
				if(!tez.getFile().exists())
					return new ErrorDataResult<Tez>(tez,"Teze Ait Dosya Bulunamadı.");
				else {
					if(temp.getFile() != null) {
						if(!temp.getFile().equals(tez.getFile())) {
							temp.getFile().delete();
							DataResult<String> result = fileOperations.copyFile(tez.getFile().getAbsolutePath(), destinationPath);
							if(!result.isSuccess())
								return new ErrorDataResult<>(result.getMessage());
							else
								tez.setFile(new File(result.getData()));
						}					
					}
					else {
						DataResult<String> result = fileOperations.copyFile(tez.getFile().getAbsolutePath(), destinationPath);
						if(!result.isSuccess())
							return new ErrorDataResult<>(result.getMessage());
						else
							tez.setFile(new File(result.getData()));
					}
				}
					
			}
			else if(temp.getFile() != null) {
				temp.getFile().delete();
			}
			tezDao.save(tez);
			DesktopApplication.addConsole("Tez Güncellendi."+tez, "tez.save()");
			return new SuccessDataResult<Tez>(tez,"Tez Güncellendi.");
		}
		DesktopApplication.addConsole("Tez Mevcut Değildir.", "tez.save()");
		return new ErrorDataResult<Tez>(tez, "Tez Mevcut Değildir.");
	}
	
	@Override
	public DataResult<List<Tez>> findAll() {
		List<Tez> tezler = tezDao.findAll();
		return new SuccessDataResult<List<Tez>>(tezler,"Tezler Döndürüldü.");
	}
	
	@Override
	public DataResult<List<Tez>> findByPersonel(Personel personel) {
		List<Tez> tezler = tezDao.findByPersonel(personel);
		if(tezler == null) {
			return new ErrorDataResult<List<Tez>>(null,"Bu personele ait tez bulunamadı!!!");
		}
		
		return new SuccessDataResult<List<Tez>>(tezler,"Tezler bulundu.");
	}

	@Override
	public DataResult<List<Tez>> findByPersonelNot(Personel personel) {
		List<Tez> tezler = tezDao.findByPersonelNot(personel);
		if(tezler == null) {
			return new ErrorDataResult<List<Tez>>(null,"Bu personele ait olmayan tez bulunamadı!!!");
		}
		
		return new SuccessDataResult<List<Tez>>(tezler,"Tezler bulundu.");
		
	}


	@Override
	public DataResult<List<Tez>> findByPersonelAndTezTuruId(Personel personel, int tezTuruId) {
		List<Tez> tezler = tezDao.findByPersonelAndTezTuruId(personel, tezTuruId);
		if(tezler == null) {
			return new ErrorDataResult<List<Tez>>(null,"Bu personele ve Tez Türüne ait tez bulunamadı!!!");
		}
		
		return new SuccessDataResult<List<Tez>>(tezler,"Tezler bulundu.");
	}
	
	@Override
	@Transactional
	public Result deleteByPersonelAndIdNotIn(Personel personel, List<Integer> tezIds) {
		tezDao.deleteByPersonelAndIdNotIn(personel, tezIds);
		return new SuccessResult("Personele ait bu liste dışındaki veriler silindi.");
	}

	@Override
	public DataResult<List<Tez>> findByPersonelAndIdNotIn(Personel personel, List<Integer> tezIds) {
		List<Tez> temp = tezDao.findByPersonelAndIdNotIn(personel, tezIds);
		return new SuccessDataResult<List<Tez>>(temp, "Personele Ait Olmayanlar Döndürüldü.");
	}

	@Override
	public Result delete(Tez tez) {
		Tez temp = tezDao.getById(tez.getId());
		if(temp != null) {
			
			//dosya işlemleri
			Result result = fileOperations.deleteFile(tez.getFile());
			if(!result.isSuccess())
				return result;
				
			tezDao.delete(tez);
			return new SuccessResult("Tez Silindi.");
		}
		return new ErrorResult("Böyle Bir Tez Bulunmamaktadır.");
	}

	@Override
	public DataResult<List<Tez>> findByPersonelNotAndTezKonusuContainingIgnoreCase(Personel personel, String tezKonusu) {
		List<Tez> tezler = null;
		if(personel != null) {
			tezler = tezDao.findByPersonelNotAndTezKonusuContainingIgnoreCase(personel, tezKonusu);
		}
		else
			tezler = tezDao.findByTezKonusuContainingIgnoreCase(tezKonusu);
		return new SuccessDataResult<List<Tez>>(tezler, tezKonusu+" İçeren Tezler Döndürüldü.");
	}


	@Override
	public DataResult<List<Tez>> findByTezKonusuContainingIgnoreCase(String tezKonusu) {
		List<Tez> tezler = tezDao.findByTezKonusuContainingIgnoreCase(tezKonusu);
		return new SuccessDataResult<List<Tez>>(tezler, tezKonusu+" İçeren Tezler Döndürüldü.");
	}
	
	@Override
	public DataResult<List<Integer>> getBitirdigiTarihGroupBy() {
		List<Integer> bitirdigiTarihlerGroup = tezDao.getBitirdigiTarihGroupBy();
		return new SuccessDataResult<List<Integer>>(bitirdigiTarihlerGroup,"Tarihler Başarıyla Getirildi.");
	}

	@Override
	public DataResult<Integer> countByBitirdigiTarih(int year) {
		int countBitirdigiTarih = tezDao.countByBitirdigiTarih(year);
		return new SuccessDataResult<Integer>(countBitirdigiTarih,"Tarih Tekrar Sayısı Bulundu.");
	}

	@Override
	public DataResult<List<Tez>> findByUniversiteEnstitu(UniversiteEnstitu universiteEnstitu) {
		List<Tez> tezler = tezDao.findByUniversiteEnstitu(universiteEnstitu);
		return new SuccessDataResult<List<Tez>>(tezler, universiteEnstitu.getAd()+" Üniversitesine Ait Tezler Döndürüldü.");
	}

	@Override
	public DataResult<List<Tez>> findByYuksekOgrenim(YuksekOgrenim yuksekOgrenim) {
		List<Tez> tezler = tezDao.findByYuksekOgrenim(yuksekOgrenim);
		return new SuccessDataResult<List<Tez>>(tezler, yuksekOgrenim.getAlanAdi()+" Alan Adına Ait Tezler Döndürüldü.");
	}

	@Override
	public DataResult<Tez> findById(int id) {
		Tez tez = tezDao.getById(id);
		try {
			tez.getTezKonusu();
		}
		catch(Exception e) {
			return new ErrorDataResult<Tez>(tez, "Bu Tez Mevcut Değildir.");
		}
		return new SuccessDataResult<Tez>(tez);
	}


	@Override
	public DataResult<List<Tez>> findByBitirdigiTarihBetweenAndTezKonusuContainingIgnoreCaseAndTezTuru_adContainingIgnoreCaseAndYuksekOgrenim_alanAdiContainingIgnoreCaseAndUniversiteEnstitu_adContainingIgnoreCaseAndPersonel_sicilContainingIgnoreCase(
			LocalDate from, LocalDate to, String tezKonusu, String tezTuruAd, String yuksekOgrenimAlanAd, String uniEnstAd,
			String personelSicil) {
		List<Tez> tezler;
		if(from == null && to == null) {
			if(!yuksekOgrenimAlanAd.isEmpty())
				tezler = tezDao.findByTezKonusuContainingIgnoreCaseAndTezTuru_adContainingIgnoreCaseAndYuksekOgrenim_alanAdiAndUniversiteEnstitu_adContainingIgnoreCaseAndPersonel_sicilContainingIgnoreCase(tezKonusu, tezTuruAd, yuksekOgrenimAlanAd, uniEnstAd, personelSicil);
			else
				tezler = tezDao.findByTezKonusuContainingIgnoreCaseAndTezTuru_adContainingIgnoreCaseAndYuksekOgrenim_alanAdiContainingIgnoreCaseAndUniversiteEnstitu_adContainingIgnoreCaseAndPersonel_sicilContainingIgnoreCase(tezKonusu, tezTuruAd, yuksekOgrenimAlanAd, uniEnstAd, personelSicil);
		}
		else {
			if(from == null)
				from = LocalDate.of(1900, 1, 1);
			if(to == null)
				to = LocalDate.now();
			if(!yuksekOgrenimAlanAd.isEmpty())
				tezler = tezDao.findByBitirdigiTarihBetweenAndTezKonusuContainingIgnoreCaseAndTezTuru_adContainingIgnoreCaseAndYuksekOgrenim_alanAdiAndUniversiteEnstitu_adContainingIgnoreCaseAndPersonel_sicilContainingIgnoreCase(
						from, to, tezKonusu, tezTuruAd, yuksekOgrenimAlanAd, uniEnstAd, personelSicil);
			else
				tezler = tezDao.findByBitirdigiTarihBetweenAndTezKonusuContainingIgnoreCaseAndTezTuru_adContainingIgnoreCaseAndYuksekOgrenim_alanAdiContainingIgnoreCaseAndUniversiteEnstitu_adContainingIgnoreCaseAndPersonel_sicilContainingIgnoreCase(
					from, to, tezKonusu, tezTuruAd, yuksekOgrenimAlanAd, uniEnstAd, personelSicil);
		}
			
		return new SuccessDataResult<List<Tez>>(tezler, "Tezler Döndürüldü");		
	}

	@Override
	public DataResult<Long> countByPersonel_birlikAndPersonel_rutbe_sinifAndTezTuruAndBitirdigiTarihNull(
			Birlik birlik, Sinif sinif, TezTuru tezTuru) {
		Long tezCount = tezDao.countByPersonel_birlikAndPersonel_rutbe_sinifAndTezTuruAndBitirdigiTarihNull(birlik, sinif, tezTuru);
		if(tezCount == 0 )
			return new ErrorDataResult<Long>(tezCount, "Tez bulunamadı.");
		return new SuccessDataResult<Long>(tezCount, "Tezler Döndürüldü");
	}

	@Override
	public DataResult<Long> countByPersonel_birlikAndPersonel_rutbe_sinifAndTezTuruAndBitirdigiTarihNotNull(
			Birlik birlik, Sinif sinif, TezTuru tezTuru) {
		Long tezCount = tezDao.countByPersonel_birlikAndPersonel_rutbe_sinifAndTezTuruAndBitirdigiTarihNotNull(birlik, sinif, tezTuru);
		if(tezCount == 0 )
			return new ErrorDataResult<Long>(tezCount, "Tez bulunamadı.");
		return new SuccessDataResult<Long>(tezCount, "Tezler Döndürüldü");
	}


}
