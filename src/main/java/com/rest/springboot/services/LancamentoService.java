package com.rest.springboot.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.rest.springboot.models.Lancamento;
import com.rest.springboot.models.Pessoa;
import com.rest.springboot.repositories.LancamentoRepository;
import com.rest.springboot.repositories.PessoaRepository;
import com.rest.springboot.services.exceptions.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	private static final String TIME_ZONE = "America/Sao_Paulo";

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
		validarPessoa(lancamento);
		return lancamentoRepository.save(lancamento);
		
	}
	
	public Lancamento atualizar(Lancamento lancamento, Long id) {
		Lancamento toUpdate = lancamentoRepository.findLancamentoById(id);
		if (!lancamento.getPessoa().equals(toUpdate.getPessoa())) 
			validarPessoa(lancamento);	
		
		BeanUtils.copyProperties(lancamento, toUpdate, "id");
		return lancamentoRepository.save(toUpdate);
		}
			
	public void deletar(Long id) {
		Lancamento lancamento = lancamentoRepository.findLancamentoById(id);
		if (lancamento == null)
			throw new EmptyResultDataAccessException(1);
		lancamentoRepository.delete(lancamento);
	}
	
	//Valida se a pessoa de um lancamento existe e valida o status da pessoa!
	private void validarPessoa(Lancamento lancamento) {
		Pessoa pessoa = null;
		if (lancamento.getPessoa().getId() != null)
			pessoa = pessoaRepository.findPessoaById(lancamento.getPessoa().getId());
		if (pessoa == null || !pessoa.getAtivo())
			throw new PessoaInexistenteOuInativaException();
	}
	
	@Scheduled(cron = "0 12 21 * * MON-FRI", zone = TIME_ZONE)
	public void avisarSobreLancamentosVencidos() {
		System.out.println("metodo sendo executado");
	}
	
	
	
	
	
}
