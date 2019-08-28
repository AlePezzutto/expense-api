package br.com.zoot.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zoot.api.event.RecursoCriadoEvent;
import br.com.zoot.api.model.Pessoa;
import br.com.zoot.api.service.PessoaService;

@RestController
@RequestMapping("/api/v1/pessoa")
public class PessoaResource {

	@Autowired
	private PessoaService service;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping()
	public ResponseEntity<List<Pessoa>> listarPessoas(){
		List<Pessoa> lstRet = service.obterPessoas();
		return ResponseEntity.ok().body(lstRet);
	}
	
	@GetMapping("/{idPessoa}")
	public ResponseEntity<Pessoa> listarPessoa(@PathVariable Long idPessoa){
		Pessoa pessoa = service.obterPessoa(idPessoa);
		return ResponseEntity.ok().body(pessoa);
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> inserir(@Valid @RequestBody Pessoa pessoa, 
			                                 HttpServletResponse response) {
		
		Pessoa newPessoa = service.inserir(pessoa);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, newPessoa.getIdPessoa()));

		return ResponseEntity.status(HttpStatus.CREATED).body(newPessoa);
		
	}
	
	@DeleteMapping("/{idPessoa}")
	public void remover(@PathVariable Long idPessoa) {
		service.remover(idPessoa);
	}
		
	@PutMapping("/{idPessoa}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long idPessoa, @Valid @RequestBody Pessoa pessoa) {
		Pessoa onEdit = service.atualizar(idPessoa, pessoa);
		return ResponseEntity.ok().body(onEdit);
	}
	
	@PutMapping("/{idPessoa}/ativo")
	public ResponseEntity<Pessoa> atualizarAtivo(@PathVariable Long idPessoa, @RequestBody Boolean ativo) {
		
		System.out.println("idPessoa ===> " + idPessoa);
		System.out.println("ativo ===> " + ativo);
				
		Pessoa onEdit = service.atualizarAtivo(idPessoa, ativo);
		return ResponseEntity.ok().body(onEdit);
	}
	
}
