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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_KURS")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Kurs {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "Ad")
	private String ad;
	
	@ManyToMany(
			fetch = FetchType.EAGER,
			cascade = CascadeType.MERGE
			)
	@JoinColumn(name = "PersonelId")
	@JoinTable(name = "FK_KURS_PERSONEL")
	private List<Personel> personeller = new ArrayList<>();
	
	public Kurs(String ad) {
		this.ad = ad;
	}
	
	public String toString() {
		return this.ad;
	}

	
	
}
