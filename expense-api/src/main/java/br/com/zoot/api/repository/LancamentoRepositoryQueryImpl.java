package br.com.zoot.api.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.zoot.api.model.Lancamento;
import br.com.zoot.api.repository.filter.LancamentoFilter;

public class LancamentoRepositoryQueryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lanctoFilter, Pageable pageable) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		// Restrições
		Predicate[] predicates =  criarRestricoes(lanctoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Lancamento> query = entityManager.createQuery(criteria);
		
		handlePaginacao(query, pageable);
		
		return new PageImpl<Lancamento>(query.getResultList(), pageable, total(lanctoFilter));
	}
	


	private Predicate[] criarRestricoes(LancamentoFilter filter, 
			                            CriteriaBuilder builder, 
			                            Root<Lancamento> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(filter.getDescricao())) {
			String aux = String.format("%%%s%%", filter.getDescricao().toLowerCase());
			predicates.add(builder.like(builder.lower(root.get("descricao")), aux));
		}
		
		if(!StringUtils.isEmpty(filter.getDataVenctoDe())) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataVencimento"), filter.getDataVenctoDe()));
		}
		
		if(!StringUtils.isEmpty(filter.getDataVenctoAte())) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataVencimento"), filter.getDataVenctoAte()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void handlePaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private long total(LancamentoFilter lanctoFilter) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		// Restrições
		Predicate[] predicates =  criarRestricoes(lanctoFilter, builder, root);
		criteria.where(predicates);
		criteria.select(builder.count(root));
		
		return entityManager.createQuery(criteria).getSingleResult();
	}	
}
