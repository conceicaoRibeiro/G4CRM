package br.com.senac.g4crm.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cliente_dado")
public class ClienteDado implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "cliente_dado_id")
	private Integer clienteDadoId;
	
	@Column(name = "cliente_dado_valor")
	private String valor;
	
	@Column(name = "cliente_dado_status")
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@OneToOne
	@JoinColumn(name = "dado_tipo_id")
	private DadoTipo dadoTipo;

	public Integer getClienteDadoId() {
		return clienteDadoId;
	}

	public void setClienteDadoId(Integer clienteDadoId) {
		this.clienteDadoId = clienteDadoId;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public DadoTipo getDadoTipo() {
		return dadoTipo;
	}

	public void setDadoTipo(DadoTipo dadoTipo) {
		this.dadoTipo = dadoTipo;
	}

}
