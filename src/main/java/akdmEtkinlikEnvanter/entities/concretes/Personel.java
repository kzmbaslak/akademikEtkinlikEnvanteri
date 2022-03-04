package akdmEtkinlikEnvanter.entities.concretes;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_PERSONEL")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Personel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "TcNo")
	private String tcNo = "";
	
	@Column(name = "Ad")
	private String ad = "";
	
	@Column(name = "Soyad")
	private String soyad = "";
	
	@Column(name = "Sicil")
	private String sicil = "";

	@Column(name = "Tel")
	private String tel = "";

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "birlikID")
	private Birlik birlik;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rutbeID")
	private Rutbe rutbe;

	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "personel",
			cascade = CascadeType.REMOVE
			)
	private List<SinavSonuc> sinavlar;
	
	@ManyToMany(
			fetch = FetchType.LAZY,
			mappedBy = "personeller"
			)
	private List<Kurs> kurslar = new ArrayList<>();
	
	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "personel",
			cascade = CascadeType.REMOVE
			)
	private List<Tez> tezler = new ArrayList<>();
	
	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "personel"
			)
	private List<Belge> belgeler;
	
	@ManyToMany(
			fetch = FetchType.LAZY,
			mappedBy = "yazarlar"
			)
	private List<Yayin> yayinlar = new ArrayList<>();
	
	@ManyToMany(
			fetch = FetchType.LAZY,
			mappedBy = "yazarlar"
			)
	private List<Kitap> kitaplar = new ArrayList<>();
	
	private String aciklama = "";
	
	public String getSinavNames(int sinavTuruId) {
		String result = "";
		for (SinavSonuc sinavSonuc : this.sinavlar) {
			Sinav sinav = sinavSonuc.getSinav();
			if(sinav.getSinavTuru().getId() == sinavTuruId) {
				result += sinavSonuc.getSinavNot() + " ("+sinav.getTarih()+")\n";
			}
		}
		return result;
	}
	
	public String toString() {
		return this.ad+" / Sicil:"+this.sicil;
	}
}
