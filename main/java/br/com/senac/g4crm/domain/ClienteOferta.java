package br.com.senac.g4crm.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;


@Entity
@Table(name = "cliente_oferta")
public class ClienteOferta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private ClienteOfertaId clienteOfertaId;
	
	@Column(name = "cliente_oferta_descricao")
	private String descricao;
	
	@OneToOne
	@JoinColumn(name="funil_etapa_id")
	private FunilEtapa funilEtapa;
	
	@Column(name = "cliente_oferta_preco")
	private Double preco;
	
	@Column(name = "cliente_oferta_status")
	private String status;
	
	@Transient
	@JsonProperty(access = Access.READ_ONLY)
	private String funilEtapaDescricao;

	public ClienteOfertaId getClienteOfertaId() {
		return clienteOfertaId;
	}

	public void setClienteOfertaId(ClienteOfertaId clienteOfertaId) {
		this.clienteOfertaId = clienteOfertaId;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public FunilEtapa getFunilEtapa() {
		return funilEtapa;
	}

	public void setFunilEtapa(FunilEtapa funilEtapa) {
		this.funilEtapa = funilEtapa;
	}
	
	@Transient
	public String getFunilEtapaDescricao() {
		if(funilEtapa==null) {
			return "Não adicionado";
		}
		return funilEtapa.getDescricao();
	}
	
	public void setFunilEtapaDescricao() {
		
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
