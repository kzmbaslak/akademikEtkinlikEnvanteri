package akdmEtkinlikEnvanter.entities.concretes;

import java.io.File;
import java.time.LocalDate;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_YAYIN")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Yayin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "ad")
	private String ad;
	
	@Column(name = "YayinTarih")
	private LocalDate yayinTarihi;
	
	@ManyToMany(
			fetch = FetchType.EAGER,
			cascade = CascadeType.MERGE
			)
	@JoinColumn(name = "PersonelId")
	@JoinTable(name = "FK_YAYIN_PERSONEL")
	private List<Personel> yazarlar = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "yayineviDergiId")
	private YayineviDergi yayineviDergi;
	
	private File file;
	
	public Yayin(String ad) {
		this.ad = ad;
	}
	
	public String toString() {
		return this.ad;
	}
}
