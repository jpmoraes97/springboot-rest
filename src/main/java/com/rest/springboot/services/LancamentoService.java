package com.rest.springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.rest.springboot.models.Lancamento;
import com.rest.springboot.models.Pessoa;
import com.rest.springboot.repositories.LancamentoRepository;
import com.rest.springboot.repositories.PessoaRepository;
import com.rest.springboot.services.exceptions.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired 
	private LancamentoRepository lancamentoRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Lancamento buscar(Long id) {
		Lancamento lancamento = lancamentoRepository.findLancamentoById(id);
		if (lancamento == null)
			throw new EmptyResultDataAccessException(1);
		return lancamento;
	}
	
	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.findPessoaById(lancamento.getPessoa().getId());
		if (pessoa == null || !pessoa.getAtivo())
			throw new PessoaInexistenteOuInativaException();
		return lancamentoRepository.save(lancamento);
		
	}
	
	public void deletar(Long id) {
		Lancamento lancamento = lancamentoRepository.findLancamentoById(id);
		if (lancamento == null)
			throw new EmptyResultDataAccessException(1);
		lancamentoRepository.delete(lancamento);
	}
	
	
	
	
	
	
	
}
