package akdmEtkinlikEnvanter.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_YAYINEVI_DERGI")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YayineviDergi {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "Ad")
	private String ad;
	
	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "yayineviDergi"
			)
	private List<Yayin> yayinlar;
	
	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "yayineviDergi"
			)
	private List<Kitap> kitaplar;
	
	public String toString() {
		return this.ad;
	}
}
