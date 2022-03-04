package akdmEtkinlikEnvanter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import akdmEtkinlikEnvanter.business.abstracts.BirlikService;
import akdmEtkinlikEnvanter.business.abstracts.KursService;
import akdmEtkinlikEnvanter.business.abstracts.PersonelService;
import akdmEtkinlikEnvanter.business.abstracts.RutbeService;
import akdmEtkinlikEnvanter.business.abstracts.SinavService;
import akdmEtkinlikEnvanter.business.abstracts.SinavSonucService;
import akdmEtkinlikEnvanter.business.abstracts.SinavTuruService;
import akdmEtkinlikEnvanter.business.abstracts.TezService;
import akdmEtkinlikEnvanter.business.abstracts.TezTuruService;
import akdmEtkinlikEnvanter.business.abstracts.UniversiteEnstituService;
import akdmEtkinlikEnvanter.business.abstracts.SinifService;
import akdmEtkinlikEnvanter.business.abstracts.YuksekOgrenimService;
import akdmEtkinlikEnvanter.dataAccess.abstracts.TezTuruDao;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.SinavTuru;
import akdmEtkinlikEnvanter.entities.concretes.Tez;
import akdmEtkinlikEnvanter.entities.concretes.TezTuru;
import desktopApp.ui.DesktopApplication;
import desktopApp.utilities.excel.ExcelHandler;
import desktopApp.utilities.excel.bussiness.abstracts.BirlikExService;
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
import desktopApp.utilities.excel.bussiness.abstracts.YuksekOgrenimExService;
import javafx.application.Application;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;


//@SpringBootApplication(scanBasePackageClasses = {AkdmEtkinlikEnvanterApplication.class, DesktopApplication.class, ExcelHandler.class})
@SpringBootApplication(scanBasePackages = { "akdmEtkinlikEnvanter", "desktopApp" })
//@EnableSwagger2
public class AkdmEtkinlikEnvanterApplication implements CommandLineRunner{


	BirlikService birlikService;
	KursService kursService;
	PersonelService personelService;
	RutbeService rutbeService;
	SinavService sinavService;
	SinavSonucService sinavSonucService;
	SinavTuruService sinavTuruService;
	TezService tezService;
	TezTuruService tezTuruService;
	UniversiteEnstituService universiteEnstituService;
	SinifService sinifService;
	YuksekOgrenimService yuksekOgrenimService;
	
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
	
	
	@Autowired
	ExcelHandler excelHandler;
	@Autowired
	TezTuruDao tezTuruDao;
	
	public static String console;//Consol'a yazılacak tüm bilgiler buraya yazılır.
	
	public static void main(String[] args) {
		
		Application.launch(DesktopApplication.class, args);
		//SpringApplication.run(AkdmEtkinlikEnvanterApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		//new UiMain().main(args);
		

		
//		TezTuru doktora = new TezTuru();
//		TezTuru yuksekLisans = new TezTuru();
//		doktora.setAd("Doktora");
//		tezTuruService.add(doktora);
//		yuksekLisans.setAd("Yüksek Lisans");
//		tezTuruService.add(yuksekLisans);
//		
//		SinavTuru ales = new SinavTuru();
//		ales.setAd("ALES");
//		SinavTuru yds = new SinavTuru();
//		yds.setAd("YDS/YÖKDİL/KPDS");
//		sinavTuruService.add(ales);
//		sinavTuruService.add(yds);
		
		
		/*DataResult<List<Birlik>> result = birlikService.getAll();
		List<Birlik> birlikler = result.getData();
		for (Birlik b : birlikler) {
			System.out.println(b.getAd());
		}*/
//		excelHandler.pullValueOfExcelToDatabase("akademikEnvanter.xlsx");
		//this.pullValueOfExcel();
	}

	
//	@Autowired
//	public AkdmEtkinlikEnvanterApplication(BirlikService birlikService, KursService kursService,
//			PersonelService personelService, RutbeService rutbeService, SinavService sinavService,
//			SinavSonucService sinavSonucService, SinavTuruService sinavTuruService, TezService tezService,
//			TezTuruService tezTuruService, UniversiteEnstituService universiteEnstituService, SinifService unvanService,
//			YuksekOgrenimService yuksekOgrenimService) {
//		super();
//		this.birlikService = birlikService;
//		this.kursService = kursService;
//		this.personelService = personelService;
//		this.rutbeService = rutbeService;
//		this.sinavService = sinavService;
//		this.sinavSonucService = sinavSonucService;
//		this.sinavTuruService = sinavTuruService;
//		this.tezService = tezService;
//		this.tezTuruService = tezTuruService;
//		this.universiteEnstituService = universiteEnstituService;
//		this.sinifService = unvanService;
//		this.yuksekOgrenimService = yuksekOgrenimService;
//		
//	}
//	
	@Autowired
	public AkdmEtkinlikEnvanterApplication(BirlikService birlikService, KursService kursService,
			PersonelService personelService, RutbeService rutbeService, SinavService sinavService,
			SinavSonucService sinavSonucService, SinavTuruService sinavTuruService, TezService tezService,
			TezTuruService tezTuruService, UniversiteEnstituService universiteEnstituService, SinifService sinifService,
			YuksekOgrenimService yuksekOgrenimService, BirlikExService birlikExService, KursExService kursExService,
			PersonelExService personelExService, RutbeExService rutbeExService, SinavExService sinavExService,
			SinavSonucExService sinavSonucExService, SinavTuruExService sinavTuruExService, TezExService tezExService,
			TezTuruExService tezTuruExService, UniversiteEnstituExService universiteEnstituExService,
			SinifExService sinifExService, YuksekOgrenimExService yuksekOgrenimExService, ExcelHandler excelHandler,
			TezTuruDao tezTuruDao) {
		super();
		this.birlikService = birlikService;
		this.kursService = kursService;
		this.personelService = personelService;
		this.rutbeService = rutbeService;
		this.sinavService = sinavService;
		this.sinavSonucService = sinavSonucService;
		this.sinavTuruService = sinavTuruService;
		this.tezService = tezService;
		this.tezTuruService = tezTuruService;
		this.universiteEnstituService = universiteEnstituService;
		this.sinifService = sinifService;
		this.yuksekOgrenimService = yuksekOgrenimService;
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
		this.excelHandler = excelHandler;
		this.tezTuruDao = tezTuruDao;
	}

//	@Bean
//    public Docket api() { 
//        return new Docket(DocumentationType.SWAGGER_2)  
//          .select()                                  
//          .apis(RequestHandlerSelectors.basePackage("akdmEtkinlikEnvanter"))      
//          .build();                                           
//    }
	
	
	
}
