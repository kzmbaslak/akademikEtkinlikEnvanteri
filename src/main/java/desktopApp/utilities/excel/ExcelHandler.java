package desktopApp.utilities.excel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.business.abstracts.BirlikService;
import akdmEtkinlikEnvanter.business.abstracts.KitapService;
import akdmEtkinlikEnvanter.business.abstracts.PersonelService;
import akdmEtkinlikEnvanter.business.abstracts.SinavTuruService;
import akdmEtkinlikEnvanter.business.abstracts.SinifService;
import akdmEtkinlikEnvanter.business.abstracts.TezService;
import akdmEtkinlikEnvanter.business.abstracts.TezTuruService;
import akdmEtkinlikEnvanter.business.abstracts.YayinService;
import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessResult;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import akdmEtkinlikEnvanter.entities.concretes.Rutbe;
import akdmEtkinlikEnvanter.entities.concretes.Sinav;
import akdmEtkinlikEnvanter.entities.concretes.SinavSonuc;
import akdmEtkinlikEnvanter.entities.concretes.SinavTuru;
import akdmEtkinlikEnvanter.entities.concretes.Tez;
import akdmEtkinlikEnvanter.entities.concretes.TezTuru;
import akdmEtkinlikEnvanter.entities.concretes.UniversiteEnstitu;
import akdmEtkinlikEnvanter.entities.concretes.Sinif;
import akdmEtkinlikEnvanter.entities.concretes.YuksekOgrenim;
import desktopApp.ui.DesktopApplication;
import desktopApp.ui.pages.MessagePage;
import desktopApp.utilities.excel.bussiness.abstracts.BirlikExService;
import desktopApp.utilities.excel.bussiness.abstracts.KitapExService;
import desktopApp.utilities.excel.bussiness.abstracts.KursExService;
import desktopApp.utilities.excel.bussiness.abstracts.PersonelExService;
import desktopApp.utilities.excel.bussiness.abstracts.RutbeExService;
import desktopApp.utilities.excel.bussiness.abstracts.SinavExService;
import desktopApp.utilities.excel.bussiness.abstracts.SinavSonucExService;
import desktopApp.utilities.excel.bussiness.abstracts.SinavTuruExService;
import desktopApp.utilities.excel.bussiness.abstracts.SinifExService;
import desktopApp.utilities.excel.bussiness.abstracts.TezExService;
import desktopApp.utilities.excel.bussiness.abstracts.TezTuruExService;
import desktopApp.utilities.excel.bussiness.abstracts.UniversiteEnstituExService;
import desktopApp.utilities.excel.bussiness.abstracts.YayinExService;
import desktopApp.utilities.excel.bussiness.abstracts.YuksekOgrenimExService;
import desktopApp.utilities.textOperations.StringSpliter;

@Component
public class ExcelHandler extends Thread {

	
	TezTuruService tezTuruService;
	SinavTuruService sinavTuruService;
	PersonelService personelService;
	TezService tezService;
	SinifService sinifService;
	BirlikService birlikService;
	
	BirlikExService birlikExService;
	KursExService kursExService;
	PersonelExService personelExService;
	RutbeExService rutbeExService;
	SinavExService sinavExService;
	SinavSonucExService sinavSonucExService;
	SinavTuruExService sinavTuruExService;
	TezExService tezExService;
	TezTuruExService tezTuruExService;
	UniversiteEnstituExService universiteEnstituExService;
	SinifExService sinifExService;
	YuksekOgrenimExService yuksekOgrenimExService;
	YayinExService yayinExService;
	KitapExService kitapExService;
	
	
	
	
	public void pullValueOfExcelToDatabase(String filePath) throws Exception {
		int rowNum = 0;
		int columnNumber = 0;
		try {
			
			FileInputStream file = new FileInputStream(new File(filePath));
			
			// .xlsx uzantılı dosyayı okumak
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			
			if(workbook.getSheet("Tablo") == null) {
				throw new Exception("Tablo isimde sayfa bulunamadı. Envanter tablosunun adı \"Tablo\" olmak zorundadır. ");
			}
			XSSFSheet sheet = workbook.getSheet("Tablo");
//			XSSFSheet sheet = workbook.getSheetAt(1);
			
			//iterator yardımıyla satırları gezmek
			Iterator<Row> rowIterator = sheet.iterator();
			
			
			
			
			TezTuru doktora = tezTuruService.getById(1).getData();
			TezTuru yuksekLisans = tezTuruService.getById(2).getData();
			SinavTuru ales = sinavTuruService.getById(1).getData();
			SinavTuru yds = sinavTuruService.getById(2).getData();

			rowIterator.next();
			rowNum = 1;
			while(rowIterator.hasNext()) {
				if(rowNum % 100 == 0)
					System.out.println(rowNum+". Satır İşleniyor...");
				Row row = rowIterator.next();
				rowNum++;
				
				Birlik birlik = new Birlik();
				Sinif sinif = new Sinif();
				Rutbe rutbe = new Rutbe();
				List<YuksekOgrenim> yuksekOgrenimler = new ArrayList<YuksekOgrenim>();
//				List<YuksekOgrenim> doktoraYO = new ArrayList<YuksekOgrenim>();
//				List<YuksekOgrenim>  yuksekLisansYO = new ArrayList<YuksekOgrenim>();
				Kurs kurs = new Kurs();
				Personel personel = new Personel();
				Sinav sinav = new Sinav();
				List<SinavSonuc> sinavSonuclari = new ArrayList<>();
				List<UniversiteEnstitu> universiteEnstituler = new ArrayList<UniversiteEnstitu>();
				List<Tez> tezler = new ArrayList<Tez>();
				
				String yuksekOgrnmAlanAdi = null;
				String yuksekOgrnmTezKonusu = null;
				String yuksekOgrnmBitirdigiTarih;
				String universiteEnstituAd;
				String sinavVeTarih;
				//her satırdaki hücreleri gezmek
				Iterator<Cell> cellIterator = row.cellIterator();
				
				columnNumber = 0;
				while(cellIterator.hasNext()) {
					columnNumber++;
					Cell cell = cellIterator.next();
					
					switch(columnNumber) {
						case 2:
							birlik.setAd(getCellValue(cell));//ok
							birlik = birlikExService.add(birlik);
							personel.setBirlik(birlik);
							break;
						case 3:
							sinif.setAd(getCellValue(cell));//ok
							sinif = sinifExService.add(sinif);
							break;
						case 4:
							String[] temp = new StringSpliter().spliteName(getCellValue(cell)).getData();
							if(temp != null) {
								personel.setAd(temp[0]);//---------------------------------
								personel.setSoyad(temp[1]);
							}
							break;
						case 5:
							rutbe.setAd(getCellValue(cell));//ok
							rutbe.setSinif(sinif);
							rutbe = rutbeExService.add(rutbe);
							personel.setRutbe(rutbe);
							break;
						case 6://Sicil
							personel.setSicil(getCellValue(cell));//-----------------------
							break;
						case 7://tel
							personel.setTel(getCellValue(cell));
							DataResult<Personel> result = personelExService.add(personel);
							if(!result.isSuccess()) {
								if(result.getData() == null) {
									System.out.println("Satır:"+rowNum+" "+personel.getAd()+" "+personel.getSoyad()
											+" İsimli Pesonel Eklenemedi. ---- Hata:"+result.getMessage());
								}
								else {
									System.out.println("Satır:"+rowNum+" "+
											personel.getAd()+" "+personel.getSoyad()+" İle "+
											result.getData().getAd()+" "+result.getData().getSoyad()+" Aynı Sicile Sahiptir."
											+" ---- Hata:"+result.getMessage());
								}
								
							}
							personel = result.getData();
							
							break;
						case 8://Doktora Alanı
							yuksekOgrnmAlanAdi = getCellValue(cell);
							if(yuksekOgrnmAlanAdi == null) {
//								columnNumber = 11;
								int counter = 0;
								while(cellIterator.hasNext() && counter < 3) {
									counter++;
									cellIterator.next();
									columnNumber++;
								}
//								cellIterator.next();
//								cellIterator.next();
//								cellIterator.next();
							}
							
							yuksekOgrenimler = yuksekOgrenimExService.add(
									yuksekOgrnmAlanAdi);
							break;
						case 9://doktora tez konusu
							yuksekOgrnmTezKonusu = getCellValue(cell);
							break;
						case 10://Doktora Üniversite  //Birden Fazla üniversite denetlenecek
							universiteEnstituAd = getCellValue(cell);
							universiteEnstituler = universiteEnstituExService.add(universiteEnstituAd);
							break;
						case 11://Doktora Bitirdiği tarih
							yuksekOgrnmBitirdigiTarih = getCellValue(cell);
							if(yuksekOgrenimler != null && personel != null) {
								Iterator<UniversiteEnstitu> uniEnst = universiteEnstituler.iterator();
								for (YuksekOgrenim yuksekOgrenim : yuksekOgrenimler) {
									Tez tez = new Tez();
									tez.setYuksekOgrenim(yuksekOgrenim);
									tez.setBitirdigiTarih(LocalDate.now());//***
									tez.setPersonel(personel);
									tez.setTezTuru(doktora);
									tez.setUniversiteEnstitu((uniEnst.hasNext())?uniEnst.next():null);
									
									tezler.add(tez);
								}
								tezExService.add(tezler, yuksekOgrnmTezKonusu, yuksekOgrnmBitirdigiTarih);
								
							}
							tezler.clear();
							break;
						case 12://Yüksek Lisans

							yuksekOgrnmAlanAdi = getCellValue(cell);
							if(yuksekOgrnmAlanAdi == null) {
//								columnNumber = 15;
								int counter = 0;
								while(cellIterator.hasNext() && counter < 3) {
									counter++;
									cellIterator.next();
									columnNumber++;
								}
//								cellIterator.next();
//								cellIterator.next();
//								cellIterator.next();
							}
							
							yuksekOgrenimler = yuksekOgrenimExService.add(
									yuksekOgrnmAlanAdi);
							break;
						case 13:

							yuksekOgrnmTezKonusu = getCellValue(cell);
							
							break;
						case 14://Birden Fazla üniversite denetlenecek
							universiteEnstituAd = getCellValue(cell);
							universiteEnstituler = universiteEnstituExService.add(universiteEnstituAd);
							break;
						case 15://Yüksek lisans bitirdigi tarih

							yuksekOgrnmBitirdigiTarih = getCellValue(cell);
							if(yuksekOgrenimler != null) {
								Iterator<UniversiteEnstitu> uniEnst = universiteEnstituler.iterator();
								for (YuksekOgrenim yuksekOgrenim : yuksekOgrenimler) {
									Tez tez = new Tez();
									tez.setYuksekOgrenim(yuksekOgrenim);
									tez.setBitirdigiTarih(LocalDate.now());
									tez.setPersonel(personel);
									tez.setTezTuru(yuksekLisans);
									tez.setUniversiteEnstitu((uniEnst.hasNext())?uniEnst.next():null);
									
									tezler.add(tez);
								}
								tezExService.add(tezler, yuksekOgrnmTezKonusu, yuksekOgrnmBitirdigiTarih);
								
							}
							tezler.clear();
							break;
						case 16://kurslar
							String kursAdi = getCellValue(cell);
							kursExService.add(kursAdi,personel);
							break;
						case 17:
							sinavVeTarih = getCellValue(cell);
							sinav.setSinavTuru(ales);
							sinavSonuclari = sinavSonucExService.add(sinavVeTarih,sinav, personel);
							break;
						case 18:

							sinavVeTarih = getCellValue(cell);
							sinav.setSinavTuru(yds);
							sinavSonuclari = sinavSonucExService.add(sinavVeTarih,sinav, personel);
							break;
						case 19:
							if(personel == null)
								break;
							personel.setAciklama(getCellValue(cell));
							personel = personelExService.save(personel);
							break;
						case 20://yayinlar
							String yayinAdi = getCellValue(cell);
							yayinExService.add(yayinAdi,personel);
							break;
						case 21://kitaplar
							String kitapAdi = getCellValue(cell);
							kitapExService.add(kitapAdi,personel);
							break;
						default:
							break;
					}
					
					//cell veri tiplerinin kontrolü
					
						
					
				}//personel kaydedilecek tez kaydedilecek
			}
			DesktopApplication.addConsole(filePath+" 'deki Veriler Veritabanına Aktarıldı.", "excelHandler.pullValueOfExcelToDatabase()");
			System.out.println(filePath+" 'deki Veriler Veritabanına Aktarıldı.");
			file.close();
			
		} catch (Exception e) {
			System.out.println(rowNum+". Satırda ve "+columnNumber+". Sütunda Hata Oluşmuştur.");
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public void pullValueOfDatabaseToExcel(String diractoryPath) throws Exception {
		
		//blank workbook
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		//create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Tablo");
		pullData(sheet);
		
		sheet = workbook.createSheet("Durum Çizelgesi");
		pullStateChart(sheet, workbook);
		
		//dosya sistemhnde workbook un yazdırılması
        try {
        	File file = new File(diractoryPath+"/Akademik Etkinlik Envanter Tablosu.xlsx");
        	int counter = 0;
        	while(file.exists()) {
        		counter++;
        		file = new File(diractoryPath+"/Akademik Etkinlik Envanter Tablosu"+counter+".xlsx");
        	}
        	FileOutputStream out = new FileOutputStream(file);
        	workbook.write(out);
        	out.close();
        	workbook.close();
        	DesktopApplication.addConsole("Veriler "+diractoryPath+" 'a Aktarıldı.", "excelHandler.pullValueOfDatabaseToExcel()");
        	System.out.println("Veriler "+diractoryPath+" 'a Aktarıldı.");
		
		}
        catch (Exception e) {
        	throw e;
		}
		
		
	}
	

//	private void pullData(XSSFSheet sheet) {
//		Map<Integer, Object[]> dat = new TreeMap<>();
//		dat.put(1, new Object[] {"SIRA NU.", "BİRLİĞİ",	"UNVANI", "ADI", "SOYADI", "RÜTBESİ", "SİCİLİ",	"TEL.NU.",
//				"DOKTORA ALANI",	"DOKTORA TEZ KONUSU", "DOKTORA ÜNİVERSİTESİ/ ENSTİTÜ", "DOKTORA BİTİRDİĞİ TARİH",
//				"YÜKSEK LİSANS ALANI", "YÜKSEK LİSANS TEZ KONUSU", "YÜKSEK LİSANS ÜNİVERSİTESİ/ ENSTİTÜ",
//				"YÜKSEK LİSANS BİTİRDİĞİ TARİH", "KURSLAR",	"ALES", "YDS/ YÖKDİL/ KPDS",	"AÇIKLAMALAR"});
//
//		List<Personel> personeller = personelService.findAll().getData();
//		int keyNum = 1;
//		for (Personel personel : personeller) {
//			Object obj[] = new Object[20];
//			obj[0] = keyNum;
//			obj[1] = personel.getBirlik().getAd();
//			obj[2] = personel.getRutbe().getSinif().getAd();
//			obj[3] = personel.getAd();
//			obj[4] = personel.getSoyad();
//			obj[5] = personel.getRutbe().getAd();
//			obj[6] = personel.getSicil();
//			obj[7] = personel.getTel();
//			
//			String[] doktoraTezler = tezExService.getByPersonelAndTezTuruId(personel, 1);
//			obj[8] = doktoraTezler[0];
//			obj[9] = doktoraTezler[1];
//			obj[10] = doktoraTezler[2];
//			obj[11] = doktoraTezler[3];
//			
//			doktoraTezler = tezExService.getByPersonelAndTezTuruId(personel, 2);
//			obj[12] = doktoraTezler[0];
//			obj[13] = doktoraTezler[1];
//			obj[14] = doktoraTezler[2];
//			obj[15] = doktoraTezler[3];
//			
//			obj[16] = kursExService.getByPersonel(personel);
//			
//			String sinavSonuclari = sinavSonucExService.getByPersonelAndSinavTuruId(personel, 1);
//			obj[17] = sinavSonuclari;
//			
//			sinavSonuclari = sinavSonucExService.getByPersonelAndSinavTuruId(personel, 2);
//			obj[18] = sinavSonuclari;
//			
//			obj[19] = personel.getAciklama();
//			
//			dat.put(++keyNum, obj);
//		}
//
//		
//		Set<Integer> keyset = dat.keySet();
//		int rowNum = 0;
//		for (Integer key : keyset) {
//			Row row = sheet.createRow(rowNum++);
//			
//			Object[] objArray = dat.get(key);
//			int cellNum = 0;
//			for (Object obj : objArray) {
//				Cell cell = row.createCell(cellNum++);
//				if(obj instanceof String)
//					cell.setCellValue((String) obj);
//				if(obj instanceof Integer)
//					cell.setCellValue((Integer) obj);
//			}
//		}
//		
//	}

	private void pullData(XSSFSheet sheet) {
		Map<Integer, Object[]> dat = new TreeMap<>();
		dat.put(1, new Object[] {"SIRA NU.", "BİRLİĞİ",	"UNVANI", "ADI SOYADI", "RÜTBESİ", "SİCİLİ",	"TEL.NU.",
				"DOKTORA ALANI",	"DOKTORA TEZ KONUSU", "DOKTORA ÜNİVERSİTESİ/ ENSTİTÜ", "DOKTORA BİTİRDİĞİ TARİH",
				"YÜKSEK LİSANS ALANI", "YÜKSEK LİSANS TEZ KONUSU", "YÜKSEK LİSANS ÜNİVERSİTESİ/ ENSTİTÜ",
				"YÜKSEK LİSANS BİTİRDİĞİ TARİH", "KURSLAR",	"ALES", "YDS/ YÖKDİL/ KPDS",	"AÇIKLAMALAR", "YAYIN", "KİTAP"});

		List<Personel> personeller = personelService.findAll().getData();
		int keyNum = 1;
		for (Personel personel : personeller) {
			Object obj[] = new Object[22];
			obj[0] = keyNum;
			obj[1] = personel.getBirlik().getAd();
			obj[2] = personel.getRutbe().getSinif().getAd();
			obj[3] = personel.getAd() +" "+personel.getSoyad();
			obj[4] = personel.getRutbe().getAd();
			obj[5] = personel.getSicil();
			obj[6] = personel.getTel();
			
			String[] doktoraTezler = tezExService.getByPersonelAndTezTuruId(personel, 1);
			obj[7] = doktoraTezler[0];
			obj[8] = doktoraTezler[1];
			obj[9] = doktoraTezler[2];
			obj[10] = doktoraTezler[3];
			
			doktoraTezler = tezExService.getByPersonelAndTezTuruId(personel, 2);
			obj[11] = doktoraTezler[0];
			obj[12] = doktoraTezler[1];
			obj[13] = doktoraTezler[2];
			obj[14] = doktoraTezler[3];
			
			obj[15] = kursExService.getByPersonel(personel);
			
			String sinavSonuclari = sinavSonucExService.getByPersonelAndSinavTuruId(personel, 1);
			obj[16] = sinavSonuclari;
			
			sinavSonuclari = sinavSonucExService.getByPersonelAndSinavTuruId(personel, 2);
			obj[17] = sinavSonuclari;
			
			obj[18] = personel.getAciklama();

			obj[19] = yayinExService.getByPersonel(personel);
			obj[20] = kitapExService.getByPersonel(personel);
			
			dat.put(++keyNum, obj);
		}

		
		Set<Integer> keyset = dat.keySet();
		int rowNum = 0;
		for (Integer key : keyset) {
			Row row = sheet.createRow(rowNum++);
			
			Object[] objArray = dat.get(key);
			int cellNum = 0;
			for (Object obj : objArray) {
				Cell cell = row.createCell(cellNum++);
				if(obj instanceof String)
					cell.setCellValue((String) obj);
				if(obj instanceof Integer)
					cell.setCellValue((Integer) obj);
			}
		}
		
	}
	
	private void pullStateChart(XSSFSheet sheet, XSSFWorkbook workbook) {
		int rowNum = 0;
		Row row = sheet.createRow(rowNum);

		DataResult<List<Sinif>> resultSinif = sinifService.getAll();
		DataResult<List<Personel>> resultPersonel = personelService.findAll();
		DataResult<List<Birlik>> resultBirlik = birlikService.getAll();
		if(!resultSinif.isSuccess() || !resultPersonel.isSuccess() || !resultBirlik.isSuccess())
			return;
		List<Sinif> siniflar = resultSinif.getData();
		List<Personel> personeller = resultPersonel.getData();
		List<Birlik> birlikler = resultBirlik.getData();
		
		int countSinif = sinifService.count().getData().intValue();
		row.createCell(0).setCellValue("SIRA NU.");
		row.createCell(1).setCellValue("BİRLİK");
		row.createCell(2).setCellValue("DOKTORA MEZUN");
		row.createCell(3 + countSinif).setCellValue("DOKTORA DEVAM");
		row.createCell(4 + 2 * countSinif).setCellValue("Y.LİSANS MEZUN");
		row.createCell(5 + 3 * countSinif).setCellValue("Y.LİSANS DEVAM");
		row.createCell(6 + 4 * countSinif).setCellValue("TOPLAM");
		//										   row    col
		sheet.addMergedRegion(new CellRangeAddress(rowNum, 1, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(rowNum, 1, 1, 1));
		int startColCell = 2;
		for (int i = 0; i < 5; i++) {
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, (startColCell + (countSinif + 1) * i), ((startColCell + (countSinif + 1) * i) + countSinif)));
//			System.out.println((startColCell + (countSinif + 1) * i)+" , "+ ((startColCell + (countSinif + 1) * i) + countSinif));
		}
		
		
		rowNum++;
		row = sheet.createRow(rowNum);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < siniflar.size(); j++) {
				row.createCell(startColCell + j + i * (siniflar.size() + 1)).setCellValue(siniflar.get(j).getAd());
			}
			row.createCell(startColCell + siniflar.size() + i * (siniflar.size() + 1)).setCellValue("TOPLAM");
		}
		

		TezTuru doktora = tezTuruService.getById(1).getData();
		TezTuru yuksekLisans = tezTuruService.getById(2).getData();
		int sum[] = new int[(siniflar.size() + 1) * 5];//Tüm Toplamlar
		int siraNo = 1;
		for (Birlik birlik : birlikler) {
			int colNum = 0;
			row = sheet.createRow(++rowNum);
			row.createCell(colNum++).setCellValue(siraNo++);
			row.createCell(colNum++).setCellValue(birlik.getAd());
			
			int tempSum[] = new int[(siniflar.size() + 1) * 5];//Ara toplamlar
			int subtotal = 0;
			int more = 0;
			for (int i = 0; i < tempSum.length; i++) {
				if(i != 0 && ( i - siniflar.size() * (more + 1)) - more == 0) {
					//ara toplamların yazılması
//					System.out.println(i+"   "+more);
					
					if(i != tempSum.length - 1) {
						
						CellStyle style = workbook.createCellStyle();
						style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
						style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
						
						
						row.createCell(colNum++).setCellValue(subtotal);
						tempSum[i] = subtotal;
						
						row.getCell(colNum-1).setCellStyle(style);
						
					}
					else {

						CellStyle style = workbook.createCellStyle();
						style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
						style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
						
						row.createCell(colNum++).setCellValue(tempSum[i]);
						
						row.getCell(colNum-1).setCellStyle(style);
					}
//					System.out.println(i+"   "+ ((i % (siniflar.size() + 1)) + tempSum.length * 4/5));
					tempSum[(i % ((siniflar.size() + 1)) + tempSum.length * 4/5)] += subtotal;
//					System.out.println(tempSum[14]+"   subTot:"+subtotal);
					subtotal = 0;
					more++;
					sum[i] += tempSum[i];
				}
				else if(i < (siniflar.size() + 1) * 4){
					//sınıfa göre tez saysısının yazılması
					int count = 0;
					if(i < (siniflar.size() + 1) || i >= (siniflar.size() + 1) * 2 && i < (siniflar.size() + 1) * 3) {
						count = tezService.countByPersonel_birlikAndPersonel_rutbe_sinifAndTezTuruAndBitirdigiTarihNotNull(
								birlik, siniflar.get((i - more) % siniflar.size()),
								i >= (2 * (siniflar.size() + 1))?yuksekLisans:doktora )
								.getData().intValue();
					}
					else {
						count = tezService.countByPersonel_birlikAndPersonel_rutbe_sinifAndTezTuruAndBitirdigiTarihNull(
								birlik, siniflar.get((i - more) % siniflar.size()),
								i >= (2 * (siniflar.size() + 1))?yuksekLisans:doktora )
								.getData().intValue();
					}
					row.createCell(colNum++).setCellValue(count);
					tempSum[i] = count;
					sum[i] += count;
					subtotal += count;
					tempSum[(i % (siniflar.size() + 1)) + tempSum.length * 4/5] += count;
					
				}
				else {
					//tüm tezlerin toplamlarının yazılması
					
					
					CellStyle style = workbook.createCellStyle();
					style.setFillForegroundColor(IndexedColors.GOLD.getIndex());
					style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					
					row.createCell(colNum++).setCellValue(tempSum[i]);
					sum[i] += tempSum[i];
					row.getCell(colNum-1).setCellStyle(style);
				}
			}
//			row.createCell(colNum).setCellValue(value);
//			(colNum - startColCell)
//			row.createCell(colNum).setCellValue(value);
		}
		
		row = sheet.createRow(++rowNum);
		int colNum = 0;
		row.createCell(colNum++).setCellValue("TOPLAM");
		colNum++;
		for (int i = 0; i < sum.length; i++) {
			row.createCell(colNum++).setCellValue(sum[i]);
		}
	}
	
	String getCellValue(Cell cell) {
		
//		if(cell.getCellType().name().equals("NUMERIC")) {
////			return Double.toString(cell.getNumericCellValue());
//			return (int)cell.getNumericCellValue()+"";
//		}
//		else if(cell.getCellType().name().equals("STRING")) {
//			return cell.getStringCellValue();
//		}
//		return null;
		if(cell.getCellType().equals(CellType.NUMERIC)) {
//			return Double.toString(cell.getNumericCellValue());
			return (int)cell.getNumericCellValue()+"";
		}
		else if(cell.getCellType().equals(CellType.STRING)) {
			return cell.getStringCellValue();
		}
		return null;
	}

	//Rutbe tamamlanacak

	@Autowired
	public ExcelHandler(TezTuruService tezTuruService,SinavTuruService sinavTuruService, PersonelService personelService,
			TezService tezService, SinifService sinifService, BirlikService birlikService,
			BirlikExService birlikExService, KursExService kursExService, PersonelExService personelExService,
			RutbeExService rutbeExService,SinavExService sinavExService,SinavSonucExService sinavSonucExService,
			SinavTuruExService sinavTuruExService, TezExService tezExService, TezTuruExService tezTuruExService,
			UniversiteEnstituExService universiteEnstituExService,SinifExService sinifExService,
			YuksekOgrenimExService yuksekOgrenimExService, YayinExService yayinExService, KitapExService kitapExService) {
		super();
		this.tezTuruService = tezTuruService;
		this.sinavTuruService = sinavTuruService;
		this.personelService = personelService;
		this.tezService = tezService;
		this.sinifService = sinifService;
		this.birlikService = birlikService; 
		
		this.birlikExService = birlikExService;
		this.kursExService = kursExService;
		this.personelExService = personelExService;
		this.rutbeExService = rutbeExService;
		this.sinavExService = sinavExService;
		this.sinavSonucExService = sinavSonucExService;
		this.sinavTuruExService = sinavTuruExService;
		this.tezExService = tezExService;
		this.tezTuruExService = tezTuruExService;
		this.universiteEnstituExService = universiteEnstituExService;
		this.sinifExService = sinifExService;
		this.yuksekOgrenimExService = yuksekOgrenimExService;
		this.yayinExService = yayinExService;
		this.kitapExService = kitapExService;
		
		TezTuru doktora = new TezTuru();
		TezTuru yuksekLisans = new TezTuru();
		doktora.setAd("Doktora");
		tezTuruService.add(doktora);
		yuksekLisans.setAd("Yüksek Lisans");
		tezTuruService.add(yuksekLisans);
		
		SinavTuru ales = new SinavTuru();
		ales.setAd("ALES");
		SinavTuru yds = new SinavTuru();
		yds.setAd("YDS/YÖKDİL/KPDS");
		sinavTuruService.add(ales);
		sinavTuruService.add(yds);
	}

	public ExcelHandler() {
		super();
	}
	
}
