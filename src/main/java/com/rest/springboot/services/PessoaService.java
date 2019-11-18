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
	
	public Pessoa salvar(Pessoa pessoa) {
		pessoa.getContatos().forEach(c -> c.setPessoa(pessoa));
		return pessoaRepository.save(pessoa);
	}
	
	public Pessoa buscar(Long id) {
		Pessoa pessoa = pessoaRepository.findPessoaById(id);
		if (pessoa == null)
			throw new EmptyResultDataAccessException(1);
		return pessoa;
	}
	
	public Pessoa atualizar(Long id, Pessoa pessoa) {
		Pessoa pessoaAtualizada = pessoaRepository.findPessoaById(id);
		if (pessoaAtualizada == null)
			throw new EmptyResultDataAccessException(1);
		
		pessoaAtualizada.getContatos().clear();
		pessoaAtualizada.getContatos().addAll(pessoa.getContatos());
		pessoaAtualizada.getContatos().forEach(c -> c.setPessoa(pessoaAtualizada));
		
		BeanUtils.copyProperties(pessoa, pessoaAtualizada, "id", "contatos");
		return pessoaRepository.save(pessoaAtualizada);
	}
	
	public void atualizarPropriedadeAtivo(Long id, Boolean ativo) {
		Pessoa pessoaAtualizada = buscar(id);
		pessoaAtualizada.setAtivo(ativo);
		pessoaRepository.save(pessoaAtualizada);
	}
	
	public void deletar(Long id) {
	    Pessoa pessoa = pessoaRepository.findPessoaById(id);
		if (pessoa == null)
			throw new EmptyResultDataAccessException(1);
		pessoaRepository.delete(pessoa);			
	}
	
}