package br.com.senac.g4crm.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "funil_etapa")
public class FunilEtapa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "funil_etapa_id")
	private Integer funilEtapaId;
	
	@Column(name = "funil_etapa_descricao")
	private String descricao;
	
	@Column(name = "funil_etapa_status")
	private String status;

	public Integer getFunilEtapaId() {
		return funilEtapaId;
	}

	public void setFunilEtapaId(Integer funilEtapaId) {
		this.funilEtapaId = funilEtapaId;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
