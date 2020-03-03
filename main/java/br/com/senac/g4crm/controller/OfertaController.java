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

import br.com.senac.g4crm.domain.Oferta;
import br.com.senac.g4crm.service.OfertaService;

@RestController
@RequestMapping("/oferta")
public class OfertaController extends BaseController<OfertaService> {
	
	@GetMapping("/lista")
	@Produces(MediaType.APPLICATION_JSON)
	public String listAll() throws JsonProcessingException {
		return mapper.writeValueAsString(service.listAll());
	}
	
	@PostMapping("/salva")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editCliente(@RequestBody String stringOferta) throws JsonParseException, JsonMappingException, IOException {
		service.salvar(mapper.readValue(stringOferta, Oferta.class));
		return Response.status(Status.OK).build();	
	}
	

}
