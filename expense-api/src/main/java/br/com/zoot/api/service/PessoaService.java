package br.com.zoot.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.zoot.api.model.Pessoa;
import br.com.zoot.api.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repo;
	
	public List<Pessoa> obterPessoas(){
		return repo.findAll();
	}
	
	public Pessoa obterPessoa(Long idPessoa) {
		return repo.findById(idPessoa).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}

	public Pessoa inserir(Pessoa pessoa) {
		pessoa.setIdPessoa(null);
		return repo.save(pessoa);
	}
	
	public Pessoa atualizar(Long idPessoa, Pessoa pessoa) {
		Pessoa onEdit = obterPessoa(idPessoa);
		BeanUtils.copyProperties(pessoa, onEdit, "idPessoa");
		return repo.save(onEdit);
	}
	
	public void remover(Long idPessoa) {
		repo.deleteById(idPessoa);
	}

	public Pessoa atualizarAtivo(Long idPessoa, Boolean ativo) {
		Pessoa onEdit = obterPessoa(idPessoa);
		onEdit.setAtivo(ativo);
		return repo.save(onEdit);
	}
}
