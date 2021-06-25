package br.opet.controller;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class PageController {
	private static final long serialVersionUID = 1L;

	public String moveToHome() {
		return "/index.xhtml";
	}

	public static String moveToPainel() {
		return "/painel.xhtml";
	}

	public static String moveToPedidoNovo() {
		return "/pedidos/pedidos.xhtml";
	}

	public static String moveToPedidoLista() {
		return "/pedidos/pedidos_listar.xhtml";
	}

	public static String moveToCliente_Buscar() {
		return "/pedidos/pesquisar_cliente.xhtml";
	}

	public static String moveToProduto_Editar() {
		return "/produtos/produtos.xhtml";

	}

	public static String moveToProduto_Listar() {
		return "/produtos/produtos_listar.xhtml";

	}

	public static String moveToProduto_Buscar() {
		return "/pedidos/pesquisar_produtos.xhtml";

	}

	public static String moveToCliente_Listar() {
		return "/clientes/clientes_listar.xhtml";

	}

	public static String moveToCliente_Editar() {
		return "/clientes/clientes.xhtml";
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void navigateTo(String href) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("MYSERVERADDRESS" + href);
		} catch (IOException ex) {
		}
	}

}
