package br.com.senac.g4crm.controller;

import java.io.IOException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.senac.g4crm.domain.Cliente;
import br.com.senac.g4crm.domain.ClienteDado;
import br.com.senac.g4crm.service.ClienteDadoService;
import br.com.senac.g4crm.service.ClienteService;
import br.com.senac.g4crm.to.ClienteTO;

@RestController
@RequestMapping("/cliente")
public class ClienteController extends BaseController<ClienteService> {
	
	@Autowired
	ClienteDadoService clienteDadoService;
	
	@GetMapping("/lista")
	@Produces(MediaType.APPLICATION_JSON)
	public String listAll() throws JsonProcessingException {
		return mapper.writeValueAsString(service.listAll());
	}
	
	@PostMapping("/insere")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setCliente(@RequestBody String stringClienteTO) throws JsonParseException, JsonMappingException, IOException {
		ClienteTO clienteTO = mapper.readValue(stringClienteTO, ClienteTO.class);
		Cliente clienteSalvo = service.inserir(clienteTO.getCliente());
		if(clienteTO.getDadosAdicionais()!=null) {
			clienteTO.getDadosAdicionais();
			for(ClienteDado dado: clienteTO.getDadosAdicionais()) {
				dado.setCliente(clienteSalvo);
			}
			clienteDadoService.inserir(clienteTO.getDadosAdicionais());
		}
		
		return Response.status(Status.OK).build();	
	}
	
	@PostMapping("/edita")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editCliente(@RequestBody String stringClienteTO) throws JsonParseException, JsonMappingException, IOException {
		ClienteTO clienteTO = mapper.readValue(stringClienteTO, ClienteTO.class);
		Cliente clienteSalvo = service.inserir(clienteTO.getCliente());
		clienteDadoService.excluirByCliente(clienteTO.getCliente()); //Limpa a lista de dados adicionais do cliente para lancar os novos valores.
		if(clienteTO.getDadosAdicionais()!=null) {
			clienteTO.getDadosAdicionais();
			for(ClienteDado dado: clienteTO.getDadosAdicionais()) {
				dado.setCliente(clienteSalvo);
			}
			clienteDadoService.inserir(clienteTO.getDadosAdicionais());
		}
		return Response.status(Status.OK).build();	
	}
	
	@GetMapping("/listaclientedado")
	@Produces(MediaType.APPLICATION_JSON)
	public String listClienteDado() throws JsonProcessingException {
		return mapper.writeValueAsString(clienteDadoService.listaClienteDados());
	}
	
    
    @PostMapping("/listadadoscliente")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String listDadosCliente(@RequestBody String stringCliente) throws IOException {
        return mapper.writeValueAsString(clienteDadoService.listaDadosCliente(mapper.readValue(stringCliente, Cliente.class)));
    }


}
