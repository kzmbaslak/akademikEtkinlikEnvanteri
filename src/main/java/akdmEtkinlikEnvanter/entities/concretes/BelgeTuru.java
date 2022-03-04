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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_BELGE_TURU")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BelgeTuru {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "Ad")
	String ad;
	
	@OneToMany(
			fetch = FetchType.LAZY,
			cascade = CascadeType.REMOVE,
			mappedBy = "belgeTuru"
			)
	List<Belge> belgeler;
	
	public String toString() {
		return this.ad;
	}
}