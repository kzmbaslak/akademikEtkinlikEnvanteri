package akdmEtkinlikEnvanter.entities.concretes;

import java.io.File;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_TEZ")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tez {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "TezKonusu")
	private String tezKonusu = "";//unique
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tezTuruId")
	private TezTuru tezTuru;

//	@Temporal(TemporalType.DATE)
	@Column(name = "BitirdigiTarih")
	private LocalDate bitirdigiTarih;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "YuksekOgrenimId")
	private YuksekOgrenim yuksekOgrenim;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UniveriteEnstituId")
	private UniversiteEnstitu universiteEnstitu;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PersonelId")
	private Personel personel;

	private File file;
	
	public String toString() {
//		return "id:"+this.id+" / "+this.tezKonusu+" / Personel: "+this.personel.getAd()+" "+this.personel.getSoyad()+" / Tez Türü: "+this.tezTuru.getAd()+" / Bitirdiği Tarih: "+this.bitirdigiTarih; 
//		return this.tezKonusu+" / "+this.bitirdigiTarih; 
		return this.tezKonusu;
	}

	
	
}
