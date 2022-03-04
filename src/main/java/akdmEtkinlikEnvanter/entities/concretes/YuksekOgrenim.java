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
//import javax.validation.constraints.NotEmpty;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_YUKSEK_OGRENIM")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "tezler"})
public class YuksekOgrenim {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "AlanAdi",unique = true)
//	@NotEmpty(message = "Alan Adı Boş Bırakılamaz!!!")
	private String alanAdi;

	@OneToMany(
			fetch = FetchType.LAZY,
			cascade = CascadeType.REMOVE,
			mappedBy = "yuksekOgrenim"
			)
	private List<Tez> tezler;

	public YuksekOgrenim(String alanAdi) {
		super();
		this.alanAdi = alanAdi;
	}
	
	public String toString() {
		return this.alanAdi;
	}
	
	
}
