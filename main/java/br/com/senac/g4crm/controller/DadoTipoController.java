package br.com.senac.g4crm.controller;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.senac.g4crm.service.DadoTipoService;

@RestController
@RequestMapping("/dadotipo")
public class DadoTipoController extends BaseController<DadoTipoService>{
	
	@GetMapping("/lista")
	@Produces(MediaType.APPLICATION_JSON)
	public String listDadoTipo() throws JsonProcessingException {
		return mapper.writeValueAsString(service.listaDadoTipo());
	}

}
