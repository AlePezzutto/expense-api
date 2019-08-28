package br.com.zoot.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zoot.api.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, 
                                              LancamentoRepositoryQuery {

	
}
