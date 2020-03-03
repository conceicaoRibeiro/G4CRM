package br.com.senac.g4crm.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BaseController<T> {
	
	@Autowired
	protected T service;
	
	protected ObjectMapper mapper = new ObjectMapper();
	
	protected String retorno;

}
