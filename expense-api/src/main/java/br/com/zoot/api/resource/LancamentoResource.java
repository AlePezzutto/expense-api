package br.com.zoot.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import br.com.zoot.api.model.Lancamento;
import br.com.zoot.api.repository.filter.LancamentoFilter;
import br.com.zoot.api.service.LancamentoService;

@RestController
@RequestMapping("/api/v1/lancamento")
public class LancamentoResource {

	@Autowired
	private LancamentoService service;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	//@GetMapping
	//public ResponseEntity<List<Lancamento>> obterLancamentos(){
	//	List<Lancamento> lstRet = service.obterLancamentos();
	//	return ResponseEntity.ok().body(lstRet);
	//}
	
	@GetMapping
	public ResponseEntity<Page<Lancamento>> pesquisar(LancamentoFilter filter, Pageable pageable){
		Page<Lancamento> page = service.pesquisarLancamentos(filter, pageable);
		return ResponseEntity.ok().body(page);
	}
	
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> obterLancamento(@PathVariable Long codigo){
		Lancamento lanc = service.obterLancamento(codigo);
		return ResponseEntity.ok().body(lanc);
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> inserir(@Valid @RequestBody Lancamento lancto, 
			                                  HttpServletResponse response) {
		
		Lancamento newLancto = service.inserir(lancto);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, newLancto.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(newLancto);
		
	}
	
	@DeleteMapping("/{codigo}")
	public void remover(@PathVariable Long codigo) {
		service.remover(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Lancamento> atualizar(@PathVariable Long codigo, @Valid @RequestBody Lancamento lancto) {
		Lancamento onEdit = service.atualizar(codigo, lancto);
		return ResponseEntity.ok().body(onEdit);
	}
	
}
