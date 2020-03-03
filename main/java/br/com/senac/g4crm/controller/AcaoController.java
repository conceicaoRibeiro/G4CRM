package br.com.senac.g4crm.controller;

import java.util.stream.Collectors;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.senac.g4crm.service.AcaoService;

@RestController
@RequestMapping("/acao")
public class AcaoController extends BaseController<AcaoService> {
	
	@GetMapping("/lista")
	@Produces(MediaType.APPLICATION_JSON)
	public String listAll() throws JsonProcessingException {
		return mapper.writeValueAsString(service.listAll().stream().filter(a -> !"Mudança de etapa no funil de vendas".equals(a.getDescricao())).collect(Collectors.toList()));
	}

}
