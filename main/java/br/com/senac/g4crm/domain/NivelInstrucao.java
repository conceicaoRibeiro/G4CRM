package br.com.senac.g4crm.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "nivel_instrucao")
public class NivelInstrucao implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "nivel_instrucao_id")
	private Integer nivelInstrucaoId;
	
	@Column(name = "nivel_instrucao_descricao")
	private String descricao;

	@Column(name = "nivel_instrucao_status")
	private String status;

	public Integer getNivelInstrucaoId() {
		return nivelInstrucaoId;
	}

	public void setNivelInstrucaoId(Integer nivelInstrucaoId) {
		this.nivelInstrucaoId = nivelInstrucaoId;
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
