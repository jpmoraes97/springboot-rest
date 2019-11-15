package com.rest.springboot.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.rest.springboot.models.Pessoa;
import com.rest.springboot.repositories.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa buscar(Long id) {
		Pessoa pessoa = pessoaRepository.getOne(id);
		if (pessoa == null)
			throw new EmptyResultDataAccessException(1);
		return pessoa;
	}
	
	public Pessoa atualizar(Long id, Pessoa pessoa) {
		Pessoa pessoaAtualizada = pessoaRepository.getOne(id);
		if (pessoaAtualizada == null)
			throw new EmptyResultDataAccessException(1);
		
		BeanUtils.copyProperties(pessoa, pessoaAtualizada, "id");
		return pessoaRepository.save(pessoaAtualizada);
	}
	
	public void atualizarPropriedadeAtivo(Long id, Boolean ativo) {
		Pessoa pessoaAtualizada = buscar(id);
		pessoaAtualizada.setAtivo(ativo);
		pessoaRepository.save(pessoaAtualizada);
	}
	
	public void deletar(Long id) {
	    Pessoa pessoa = pessoaRepository.getOne(id);
		if (pessoa == null)
			throw new EmptyResultDataAccessException(1);
		pessoaRepository.delete(pessoa);			
	}
	
}