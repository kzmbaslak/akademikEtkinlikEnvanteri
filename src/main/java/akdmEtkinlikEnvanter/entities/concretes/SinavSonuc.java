package akdmEtkinlikEnvanter.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_SINAV_SONUC")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SinavSonuc {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "sinavNot")
	private double sinavNot;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PersonelId")
	private Personel personel;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SinavId")
	private Sinav sinav;
	
	public String toString() {
		return this.sinavNot+"";
	}
}
