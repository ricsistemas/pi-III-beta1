package br.opet.controller;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.opet.model.Produtos;

@ManagedBean
@SessionScoped

public class ControllerProdutos {
	private String pesquisa_produto = "";

	public ControllerProdutos() {

	}

	public ArrayList<Produtos> Listar_Produtos_Descricao() {
		Produtos prod = new Produtos();
		return prod.Filtra_Produto_Nome(getPesquisa_produto());
	}

	public String BT_Excluir(Produtos prod) {
		if (prod.excluir()) {
			return PageController.moveToProduto_Listar();
		} else
			return null;
	}

	public String BT_Salvar(Produtos prod) {

		if (prod.getId() == 0) {
			if (prod.insert())
				return PageController.moveToProduto_Listar();
		} else {
			if (prod.alterar())
				return PageController.moveToProduto_Listar();
		}

		return null;

	}

	public String BT_Listar_produtos() {
		return PageController.moveToProduto_Listar();

	}

	public String BT_Alterar(Produtos prod) {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("produtos", prod);
		return PageController.moveToProduto_Editar();
	}

	public String BT_Novo() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("produtos", new Produtos());
		return PageController.moveToProduto_Editar();
	}

	public ArrayList<Produtos> listar_produtos() {

		Produtos prod = new Produtos();
		return prod.listar();
	}

	public String getPesquisa_produto() {
		return pesquisa_produto;
	}

	public void setPesquisa_produto(String pesquisa_produto) {
		this.pesquisa_produto = pesquisa_produto;
	}

}
