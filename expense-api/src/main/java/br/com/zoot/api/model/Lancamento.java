package br.com.zoot.api.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="lancamento")
public class Lancamento {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	@NotNull
	private String descricao;
	
	@NotNull
	@Column(name="data_vencto")
	private LocalDate dataVencimento;

	@Column(name="data_pagto")
	private LocalDate dataPagamento;

	private Double valor;
	private String observ;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name="tipo")
	private TipoLancto tipoLancto;
	
	@ManyToOne
	@JoinColumn(name = "codigo_cat")
	@NotNull
	private Categoria categoria;
	
	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	@NotNull
	private Pessoa pessoa;

	public Lancamento() {
	}

	public Lancamento(Long codigo, 
			          String descricao, 
			          LocalDate dataVencimento,
			          LocalDate dataPagamento, 
			          Double valor, 
			          String observ, 
			          TipoLancto tipoLancto,
			          Categoria categoria, 
			          Pessoa pessoa) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.observ = observ;
		this.tipoLancto = tipoLancto;
		this.categoria = categoria;
		this.pessoa = pessoa;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getObserv() {
		return observ;
	}

	public void setObserv(String observ) {
		this.observ = observ;
	}

	public TipoLancto getTipoLancto() {
		return tipoLancto;
	}

	public void setTipoLancto(TipoLancto tipoLancto) {
		this.tipoLancto = tipoLancto;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lancamento other = (Lancamento) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
