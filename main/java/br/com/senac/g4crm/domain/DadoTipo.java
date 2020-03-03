package br.com.senac.g4crm.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dado_tipo")
public class DadoTipo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "dado_tipo_id")
	private Integer dadoTipoId;
	
	@Column(name = "dado_tipo_descricao")
	private String descricao;
	
	@Column(name = "dado_tipo_status")
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "categoria_dado_id")
	private CategoriaDado categoriaDado;
	
	@Column(name = "dado_tipo_obrigatorio")
	private String obrigatorio;
	
	@Column(name = "dado_tipo_padrao")
	private String padrao;
	
	@Column(name = "dado_tipo_mascara")
	private String mascara;

	public Integer getDadoTipoId() {
		return dadoTipoId;
	}

	public void setDadoTipoId(Integer dadoTipoId) {
		this.dadoTipoId = dadoTipoId;
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

	public CategoriaDado getCategoriaDado() {
		return categoriaDado;
	}

	public void setCategoriaDado(CategoriaDado categoriaDado) {
		this.categoriaDado = categoriaDado;
	}

	public String getObrigatorio() {
		return obrigatorio;
	}

	public void setObrigatorio(String obrigatorio) {
		this.obrigatorio = obrigatorio;
	}

	public String getPadrao() {
		return padrao;
	}

	public void setPadrao(String padrao) {
		this.padrao = padrao;
	}

	public String getMascara() {
		return mascara;
	}

	public void setMascara(String mascara) {
		this.mascara = mascara;
	}

}
