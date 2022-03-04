package akdmEtkinlikEnvanter.entities.concretes;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_UNIVERSITE_ENSTITU")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UniversiteEnstitu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "Ad")
	private String ad;
	
	@OneToMany(
			fetch = FetchType.LAZY,
			cascade = CascadeType.REMOVE,
			mappedBy = "universiteEnstitu"
			)
	private List<Tez> tezler;
	
	public UniversiteEnstitu(String ad) {
		this.ad = ad;
	}
	
	public String toString() {
		return this.ad;
	}
}
