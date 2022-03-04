package desktopApp.ui.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonelWithAllObjects {
	private int id;

	private String tcNo;
	
	private String ad;
	
	private String soyad;
	
	private String sicil;

	private String tel;

	private String birlik;

	private String rutbe;

	private String sinif;
	
//	private String doktoraAlani;
//	
//	private String doktoraTezKonusu;
//	
//	private String doktoraUniversiteEnstitu;
//	
//	private String doktoraBitirdigiTarih;
//	
//	private String yuksekLisansAlani;
//	
//	private String yuksekLisansTezKonusu;
//	
//	private String yuksekLisansUniversiteEnstitu;
//	
//	private String yuksekLisansBitirdigiTarih;
//	
//	private String kurslar;
//
//	private String ales;
//
//	private String ydsYokdilKpds;
	
	private String aciklamalar;
}