package akdmEtkinlikEnvanter.entities.concretes;


import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_BELGE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Belge {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "Ad")
	private String ad;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "belgeTuruId")
	private BelgeTuru belgeTuru;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "personelId")
	private Personel personel;
	
	@Column(name = "dosya")
	private File file;
	
	public String toString() {
		return this.ad;
	}
}
