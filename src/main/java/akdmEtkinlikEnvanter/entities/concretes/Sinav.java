package akdmEtkinlikEnvanter.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_SINAV")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Sinav {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
//	@Temporal(TemporalType.DATE)
	@Column(name = "Tarih")
	private LocalDate tarih;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sinavTuruID")
	private SinavTuru sinavTuru;
	
	@OneToMany(
			fetch = FetchType.LAZY,
			cascade = CascadeType.REMOVE,
			mappedBy = "sinav"
			)
	private List<SinavSonuc> sinavSonuclari;
	
	public String toString() {
		return this.tarih+" / Sınav Türü: "+this.sinavTuru.getAd();
	}

	public Sinav(LocalDate tarih, SinavTuru sinavTuru) {
		super();
		this.tarih = tarih;
		this.sinavTuru = sinavTuru;
	}
	
	
}
