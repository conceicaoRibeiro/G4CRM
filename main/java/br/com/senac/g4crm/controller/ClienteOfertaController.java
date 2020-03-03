package br.com.senac.g4crm.controller;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.senac.g4crm.domain.ClienteOferta;
import br.com.senac.g4crm.domain.ClienteOfertaId;
import br.com.senac.g4crm.domain.Oferta;
import br.com.senac.g4crm.service.ClienteOfertaService;

@RestController
@RequestMapping("/clienteoferta")
public class ClienteOfertaController extends BaseController<ClienteOfertaService> {

	@PostMapping("/salva")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvar(@RequestBody String stringClienteOferta) throws JsonParseException, JsonMappingException, IOException {
		return Response.status(Status.OK).entity(service.salvar(mapper.readValue(stringClienteOferta, ClienteOferta.class))).build();	
	}
	
	@PostMapping("/atualiza")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atualizar(@RequestBody String stringClienteOferta) throws JsonParseException, JsonMappingException, IOException {
		return Response.status(Status.OK).entity(service.atualizar(mapper.readValue(stringClienteOferta, ClienteOferta.class))).build();	
	}
	
	@PostMapping("/buscaporid")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response buscarPorClienteOfertaId(@RequestBody String stringClienteOfertaId) throws JsonParseException, JsonMappingException, IOException {
		return Response.status(Status.OK).entity(service.buscar(mapper.readValue(stringClienteOfertaId, ClienteOfertaId.class))).build();	
	}
	
	@PostMapping("/listaporoferta")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response listaPorOferta(@RequestBody String stringOferta) throws JsonParseException, JsonMappingException, IOException {
		return Response.status(Status.OK).entity(service.listaPorOferta(mapper.readValue(stringOferta, Oferta.class))).build();	
	}
	
	@GetMapping("/lista")
	@Produces(MediaType.APPLICATION_JSON)
	public String listaTodos() throws JsonProcessingException {
		return mapper.writeValueAsString(service.listaPorFunilEtapa());
	}
	
}
