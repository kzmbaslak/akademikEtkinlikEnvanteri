package akdmEtkinlikEnvanter.entities.concretes;

import java.util.List;

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
@Table(name = "TBL_RUTBE")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Rutbe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "Ad")
	private String ad;

	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "rutbe"
			)
	private List<Personel> personeller;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sinifID")
	private Sinif sinif;
	
//	public Rutbe(String ad) {
//		super();
//		this.ad = ad;
//	}
	
	public Rutbe(String ad, Sinif sinif) {
		super();
		this.ad = ad;
		this.sinif = sinif;
	}
	
	public String toString() {
		return this.ad;
	}
	
}
