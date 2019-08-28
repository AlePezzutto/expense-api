package br.com.zoot.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zoot.api.event.RecursoCriadoEvent;
import br.com.zoot.api.model.Categoria;
import br.com.zoot.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/api/v1/categoria")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository repo;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping()
	public ResponseEntity<List<Categoria>> listarCategorias(){
		List<Categoria> lstRet = repo.findAll();
		return ResponseEntity.ok().body(lstRet);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> listarCategoria(@PathVariable Long codigo){
		return repo.findById(codigo)
		           .map(record -> ResponseEntity.ok().body(record))
		           .orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<Categoria> inserir(@Valid @RequestBody Categoria categoria, 
			                                 HttpServletResponse response) {
		
		Categoria newCat = repo.save(categoria);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, newCat.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(newCat);
		
	}
	
	
}
