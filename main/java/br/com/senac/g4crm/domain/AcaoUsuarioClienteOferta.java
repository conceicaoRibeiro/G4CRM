package br.com.senac.g4crm.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "acao_usuario_cliente_oferta")
public class AcaoUsuarioClienteOferta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "acao_usuario_cliente_oferta_id")
	private Integer acaoUsuarioClienteOfertaId;
	
	@ManyToOne
	private Acao acao;
	
	@ManyToOne
	private ClienteOferta clienteOferta;
	
	@Column(name = "acao_usuario_cliente_oferta_descricao")
	private String descricao;
	
	@Column(name = "acao_usuario_cliente_oferta_data")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private Date dataAcao;
	
	@OneToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	public Integer getAcaoUsuarioClienteOfertaId() {
		return acaoUsuarioClienteOfertaId;
	}

	public void setAcaoUsuarioClienteOfertaId(Integer acaoUsuarioClienteOfertaId) {
		this.acaoUsuarioClienteOfertaId = acaoUsuarioClienteOfertaId;
	}

	public Acao getAcao() {
		return acao;
	}

	public void setAcao(Acao acao) {
		this.acao = acao;
	}

	public ClienteOferta getClienteOferta() {
		return clienteOferta;
	}

	public void setClienteOferta(ClienteOferta clienteOferta) {
		this.clienteOferta = clienteOferta;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataAcao() {
		return dataAcao;
	}

	public void setDataAcao(Date data) {
		this.dataAcao = data;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
