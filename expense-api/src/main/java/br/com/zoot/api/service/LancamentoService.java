package br.com.zoot.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.zoot.api.model.Lancamento;
import br.com.zoot.api.repository.LancamentoRepository;
import br.com.zoot.api.repository.filter.LancamentoFilter;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository repo;
	
	public List<Lancamento> obterLancamentos(){
		return repo.findAll();
	}
	
	// http://localhost:8080/api/v1/lancamento?descricao=mensal&dataVenctoDe=2017-08-10&dataVenctoAte=2018-12-31
	public Page<Lancamento> pesquisarLancamentos(LancamentoFilter lanctoFilter, Pageable pageable){
		return repo.filtrar(lanctoFilter, pageable);
	}
		
	public Lancamento obterLancamento(Long codigo) {
		return repo.findById(codigo)
				   .orElseThrow(() -> new EmptyResultDataAccessException(1));
	}
	
	public Lancamento inserir(Lancamento lanc) {
		lanc.setCodigo(null);
		return repo.save(lanc);
	}
	
	public Lancamento atualizar(Long codigo, Lancamento lanc) {
		Lancamento onEdit = obterLancamento(codigo);
		BeanUtils.copyProperties(lanc, onEdit, "codigo");
		return repo.save(onEdit);
	}
	
	public void remover(Long codigo) {
		repo.deleteById(codigo);
	}
	
	
//	public Page<Lancamento> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
//		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
//		return repo.findAll(pageRequest);
//		
//	}
	
	
	
}
