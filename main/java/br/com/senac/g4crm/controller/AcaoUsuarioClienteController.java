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
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.senac.g4crm.domain.AcaoUsuarioCliente;
import br.com.senac.g4crm.domain.Cliente;
import br.com.senac.g4crm.service.AcaoUsuarioClienteService;

@RestController
@RequestMapping("/acaousuariocliente")
public class AcaoUsuarioClienteController extends BaseController<AcaoUsuarioClienteService> {

	@PostMapping("/salva")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salva(@RequestBody String stringAcaoUsuarioCliente) throws JsonParseException, JsonMappingException, IOException {
		return Response.status(Status.OK).entity(service.salvar(mapper.readValue(stringAcaoUsuarioCliente, AcaoUsuarioCliente.class))).build();	
	}
	
	@PostMapping("/lista")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response listByCliente(@RequestBody String stringCliente) throws JsonParseException, JsonMappingException, IOException {
		return Response.status(Status.OK).entity(service.findByCliente(mapper.readValue(stringCliente, Cliente.class))).build();	
	}
	
	@GetMapping("/listatotalporacao")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listaTotalPorAcao() {
		return Response.status(Status.OK).entity(service.listaTotalAcoesRegistradas()).build();
	}
}
