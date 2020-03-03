package br.com.senac.g4crm.controller;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.senac.g4crm.service.NivelInstrucaoService;

@RestController
@RequestMapping("/nivelinstrucao")
public class NivelInstrucaoController extends BaseController<NivelInstrucaoService> {
	
	@GetMapping("/lista")
	@Produces(MediaType.APPLICATION_JSON)
	public String listAll() throws JsonProcessingException {
		return mapper.writeValueAsString(service.listAll());
	}

}
