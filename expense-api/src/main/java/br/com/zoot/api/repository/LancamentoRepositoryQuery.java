package br.com.zoot.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.zoot.api.model.Lancamento;
import br.com.zoot.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	public Page<Lancamento> filtrar(LancamentoFilter lanctoFilter, Pageable pageable);
}
