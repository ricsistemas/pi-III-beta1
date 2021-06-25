package br.opet.controller;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.opet.model.Clientes;

@ManagedBean
@RequestScoped
public class ControllerClientes {

	private String pesquisa_nome = "";

	public ControllerClientes() {
	}

	public ArrayList<Clientes> List_Cliente_Nome() {
		Clientes cli = new Clientes();
		return cli.filtra_nome(getPesquisa_nome());
	}

	public String BT_Excluir(Clientes cli) {

		if (!cli.excluir()) {
			FacesContext contexto = FacesContext.getCurrentInstance();
			contexto.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possivel excluir o cliente!", ""));
			return null;
		}
		return PageController.moveToCliente_Listar();

	}

	public String BT_Salvar(Clientes cli) {
		FacesContext contexto = FacesContext.getCurrentInstance();
		int erro = 0;
		Date date = new Date();
		try {
			date = cli.getDt_nascimento();
		} catch (Exception e) {
			contexto.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data de Nascimento invalida!", ""));
			erro = 1;

		}
		if (date == null)
			erro = 1;

		if (erro == 1)
			return null;

		if (cli.getId() == 0) {
			if (cli.inserir())
				return PageController.moveToCliente_Listar();
			else {
				contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Incluir", ""));
				return null;

			}

		} else {
			if (cli.alterar())

				return PageController.moveToCliente_Listar();
			else {
				contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na Alteração", ""));
				return null;

			}

		}

	}

	public String BT_Editar(Clientes cli) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("clientes", cli);
		return PageController.moveToCliente_Editar();
	}

	public String BT_Novo() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("clientes", new Clientes());
		return PageController.moveToCliente_Editar();
	}

	public String getPesquisa_nome() {
		return pesquisa_nome;
	}

	public void setPesquisa_nome(String pesquisa_nome) {
		this.pesquisa_nome = pesquisa_nome;
	}

}
