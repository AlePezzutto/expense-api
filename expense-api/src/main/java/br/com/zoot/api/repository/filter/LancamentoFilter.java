package br.com.zoot.api.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class LancamentoFilter {

	private String descricao;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dataVenctoDe;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dataVenctoAte;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public LocalDate getDataVenctoDe() {
		return dataVenctoDe;
	}
	public void setDataVenctoDe(LocalDate dataVenctoDe) {
		this.dataVenctoDe = dataVenctoDe;
	}
	public LocalDate getDataVenctoAte() {
		return dataVenctoAte;
	}
	public void setDataVenctoAte(LocalDate dataVenctoAte) {
		this.dataVenctoAte = dataVenctoAte;
	}
	
}
