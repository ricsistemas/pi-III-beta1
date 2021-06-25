package br.opet.model;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.opet.dao.ProdutosDao;

@ManagedBean
@SessionScoped
public class Produtos extends ProdutosDao {
	private int id;
	private String Descricao;
	private Double custo;
	private Double preco;

	public Produtos() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return Descricao;
	}

	public void setDescricao(String descricao) {
		Descricao = descricao;
	}

	public Double getCusto() {
		return custo;
	}

	public void setCusto(Double custo) {
		this.custo = custo;
	}

	public boolean insert() {
		return super._inserir(this);

	}

	public ArrayList<Produtos> listar() {
		return super.listar();
	}

	public boolean alterar() {
		// TODO Auto-generated method stub
		return super._alterar(this);
	}

	public boolean excluir() {
		return super._excluir(this);

	}

	public ArrayList<Produtos> FiltraProdutos(String pesquisa_produtos) {

		return super._FiltraProdutos(pesquisa_produtos);
	}

	public ArrayList<Produtos> Filtra_Produto_Nome(String pesquisa_produto) {
		return super._Filtra_Produto_Nome(pesquisa_produto);
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

}
