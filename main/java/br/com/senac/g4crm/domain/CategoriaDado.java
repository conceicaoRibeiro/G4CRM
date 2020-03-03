package br.com.senac.g4crm.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categoria_dado")
public class CategoriaDado implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "categoria_dado_id")
	private Integer categoriaDadoId;
	
	@Column(name = "categoria_dado_descricao")
	private String descricao;
	
	@Column(name = "categoria_dado_status")
	private String status;

	public Integer getCategoriaDadoId() {
		return categoriaDadoId;
	}

	public void setCategoriaDadoId(Integer categoriaDadoId) {
		this.categoriaDadoId = categoriaDadoId;
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
