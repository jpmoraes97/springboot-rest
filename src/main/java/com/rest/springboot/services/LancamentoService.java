package com.rest.springboot.services;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.rest.springboot.dto.EstatisticaLancamentoPessoa;
import com.rest.springboot.mail.Mailer;
import com.rest.springboot.models.Lancamento;
import com.rest.springboot.models.Pessoa;
import com.rest.springboot.models.Usuario;
import com.rest.springboot.repositories.LancamentoRepository;
import com.rest.springboot.repositories.PessoaRepository;
import com.rest.springboot.repositories.UsuarioRepository;
import com.rest.springboot.services.exceptions.PessoaInexistenteOuInativaException;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class LancamentoService {
	
	//private static final String TIME_ZONE = "America/Sao_Paulo";

	@Autowired 
	private LancamentoRepository lancamentoRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private Mailer mailer;
	
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
	
	@Scheduled(cron = "0 4 15 * * MON-FRI")
	public void avisarSobreLancamentosVencidos() {
		List<Lancamento> vencidos = lancamentoRepository
					.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate.now());
		List<Usuario> destinatarios = usuarioRepository
				.findByPermissoesDescricao("ROLE_PESQUISAR_LANCAMENTO");
		
		mailer.avisarSobreLancamentosVencidos(vencidos, destinatarios);	
	}
	
	
	public byte[] relatorioPorPessoa(LocalDate inicio, LocalDate fim) throws Exception {
		List<EstatisticaLancamentoPessoa> dados = lancamentoRepository.porPessoa(inicio, fim);
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("DT_INICIO", Date.valueOf(inicio));
		parametros.put("DT_FIM", Date.valueOf(fim));
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		
		InputStream inputStream = this.getClass()
				.getResourceAsStream("/relatorios/lancamentos-por-pessoa.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, 
				new JRBeanCollectionDataSource(dados));
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}
	
}