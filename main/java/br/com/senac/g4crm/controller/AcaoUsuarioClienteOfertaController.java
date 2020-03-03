package br.com.senac.g4crm.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.senac.g4crm.domain.AcaoUsuarioClienteOferta;
import br.com.senac.g4crm.domain.ClienteOferta;
import br.com.senac.g4crm.service.AcaoUsuarioClienteOfertaService;

@RestController
@RequestMapping("/acaousuarioclienteoferta")
public class AcaoUsuarioClienteOfertaController extends BaseController<AcaoUsuarioClienteOfertaService> {

	@PostMapping("/salva")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvar(@RequestBody String stringAcaoUsuarioClienteOferta) throws JsonParseException, JsonMappingException, IOException {
		return Response.status(Status.OK).entity(service.salvar(mapper.readValue(stringAcaoUsuarioClienteOferta, AcaoUsuarioClienteOferta.class))).build();	
	}
	
	@PostMapping("/lista")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response lista(@RequestBody String stringClienteOferta) throws JsonParseException, JsonMappingException, IOException {
		return Response.status(Status.OK).entity(service.findByClienteOferta(mapper.readValue(stringClienteOferta, ClienteOferta.class))).build();	
	}
	
	@GetMapping("/listaporperiodo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listaPorPeriodo(@RequestParam  String dataInicial, @RequestParam String dataFinal) throws ParseException {
		return Response.status(Status.OK).entity(service.listByPeriodo(dataInicial, dataFinal)).build();
	}
}
