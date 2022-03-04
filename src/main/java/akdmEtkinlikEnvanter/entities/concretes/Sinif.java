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
@Table(name = "TBL_SINIF")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Sinif {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "Ad")
	private String ad;
	
	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "sinif",
			cascade = CascadeType.ALL
			)
	List<Rutbe> rutbeler;

	public Sinif(String ad) {
		super();
		this.ad = ad;
	}

	public String toString() {
		return this.ad;
	}
	
}
