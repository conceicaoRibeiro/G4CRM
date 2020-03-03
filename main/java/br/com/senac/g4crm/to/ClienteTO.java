package br.com.senac.g4crm.to;

import java.util.List;

import br.com.senac.g4crm.domain.Cliente;
import br.com.senac.g4crm.domain.ClienteDado;

public class ClienteTO {
	
	private Cliente cliente;
	
	private List<ClienteDado> dadosAdicionais;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ClienteDado> getDadosAdicionais() {
		return dadosAdicionais;
	}

	public void setDadosAdicionais(List<ClienteDado> dadosAdicionais) {
		this.dadosAdicionais = dadosAdicionais;
	}

}
