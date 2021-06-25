package br.opet.controller;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.opet.model.Clientes;
import br.opet.model.PItens;
import br.opet.model.Pedidos;
import br.opet.model.Produtos;

@ManagedBean
@SessionScoped
public class ControllerPedidos {
	private final int MODO_VIRTUAL = -1;
	private final int MODO_ALTERAR = 0;
	private final int MODO_INSERIR = 1;
	private String pesquisa_produtos;
	private Date data_inicial;
	private Date data_final;
	private String nome_cliente;
	private Produtos produto_selecionado = new Produtos();
	private int modo;
	private int Qtd;
	private boolean editar_data_pedido;
	private ArrayList<Integer> itens_excluir;
	private ArrayList<PItens> itens_novos;
	private Pedidos ped;

	public ControllerPedidos() {

	}

	public ArrayList<Pedidos> Listar_Pedidos() {
		Pedidos p = new Pedidos();
		return p.Listar_Pedidos();

	}

	public String BT_Pedido_Excluir(Pedidos ped_excluir) {

		if (ped_excluir.Excluir())
			return PageController.moveToPedidoLista();
		else {
			FacesContext contexto = FacesContext.getCurrentInstance();
			contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na exclusão!", ""));
			return null;
		}

	}

	public String BT_Editar(Pedidos ped) {
		modo = MODO_VIRTUAL;
		itens_excluir = new ArrayList<Integer>();
		itens_novos = new ArrayList<PItens>();
		setPed(ped);
		setEditar_data_pedido(true);
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("pedidos", ped);
		return PageController.moveToPedidoNovo();
	}

	public String BT_Produto_Buscar() {
		return PageController.moveToCliente_Buscar();

	}

	public String BT_Excluir_Item(PItens item) {
		if (ped.getId() != 0 && modo == MODO_ALTERAR) {
			itens_excluir.add(item.getId());
		}

		ped.removeItem(item);
		return null;
	}

	public Produtos BT_Atualiza_Produto_selecionado(Produtos prod) {
		setProduto_selecionado(prod);
		setQtd(0);
		return getProduto_selecionado();

	}

	public ArrayList<PItens> Listar_Pedidos_Itens() {
		if (modo == MODO_VIRTUAL) {
			PItens itens = new PItens();
			ArrayList<PItens> lista;
			lista = itens.getPitens(ped.getId());
			for (int i = 0; i < lista.size(); i++) {
				PItens item = lista.get(i);
				ped.AddItens(item);

			}
			modo = MODO_ALTERAR;
		}
		return ped.getPitens();
	}

	public ArrayList<Produtos> FiltraProdutos() {
		Produtos prod = new Produtos();
		return prod.FiltraProdutos(pesquisa_produtos);
	}

	public String BT_Salvar() {
		int erro = 0;
		if (ped.getClientes().getId() == 0) {
			FacesContext contexto = FacesContext.getCurrentInstance();
			contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cliente Inválido!", ""));
			erro = 1;

		}
		if (ped.getForma_Pagamento() == 0) {
			FacesContext contexto = FacesContext.getCurrentInstance();
			contexto.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione a forma de pagamento!", ""));
			erro = 1;
		}
		if (ped.getPitens().size() == 0) {
			FacesContext contexto = FacesContext.getCurrentInstance();
			contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sem Itens no Pedido!", ""));
			erro = 1;
		}

		if (erro != 0)
			return null;

		if (ped.getId() != 0 && modo == MODO_ALTERAR) {
			// incluir e excluir
			if (ped.Alterar(itens_novos, itens_excluir)) {
				return PageController.moveToPedidoLista();
			} else {
				FacesContext contexto = FacesContext.getCurrentInstance();
				contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro: Não foi possivel fazer as alterações", ""));
			}

		} else {
			// inserir no banco de dados;
			if (ped.inserir()) {
				return PageController.moveToPedidoLista();
			} else {
				FacesContext contexto = FacesContext.getCurrentInstance();
				contexto.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: Não foi possivel fazer a Inclusão", ""));
			}
		}
		return null;

	}

	public String BT_Pedido_Continuar_Editacao() {
		setProduto_selecionado(new Produtos());
		return PageController.moveToPedidoNovo();
	}

	public String BT_Novo() {
		modo = MODO_INSERIR;
		ped = new Pedidos();
		ped.setUsuario_id(1);
		ped.setDt_pedido(new Date());
		ped.setDt_entrega(new Date());
		ped.setStatus(0);
		setEditar_data_pedido(false);
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("pedidos", ped);
		return PageController.moveToPedidoNovo();

	}

	public String BT_Produto_Adicionar() {

		int erro = 0;

		FacesContext contexto = FacesContext.getCurrentInstance();
		if (getQtd() == 0) {
			contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Quantidade Inválida", ""));
			erro = 1;
		}

		if (produto_selecionado.getId() == 0) {
			contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Codigo de Produto Inválido", ""));
			erro = 1;

		}

		if (erro != 0)
			return null;
		PItens item = new PItens();

		item.setProduto_id(getProduto_selecionado().getId());
		item.setPreco(getProduto_selecionado().getPreco());
		item.setDescricao(getProduto_selecionado().getDescricao());
		item.setQtd(getQtd());
		ped.AddItens(item);

		if (ped.getId() != 0 && modo == MODO_ALTERAR) {
			itens_novos.add(item);
		}

		produto_selecionado = new Produtos();
		setQtd(0);
		return BT_Pedido_Continuar_Editacao();

	}

	public String BT_Cliente_Selecionado(Clientes cli) {
		ped.setClientes(cli);
		return PageController.moveToPedidoNovo();
	}

	public String BT_List_Pedidos() {
		return PageController.moveToPedidoLista();
	}

	public String getPesquisa_produtos() {
		return pesquisa_produtos;
	}

	public void setPesquisa_produtos(String pesquisa_produtos) {
		this.pesquisa_produtos = pesquisa_produtos;
	}

	public Pedidos getPed() {
		return ped;
	}

	public void setPed(Pedidos ped) {
		this.ped = ped;
	}

	public Produtos getProduto_selecionado() {
		return produto_selecionado;
	}

	public void setProduto_selecionado(Produtos produto_selecionado) {
		this.produto_selecionado = produto_selecionado;
	}

	public int getQtd() {
		return Qtd;
	}

	public void setQtd(int qtd) {
		Qtd = qtd;
	}

	public Date getData_inicial() {
		return data_inicial;
	}

	public void setData_inicial(Date data_inicial) {
		this.data_inicial = data_inicial;
	}

	public Date getData_final() {
		return data_final;
	}

	public void setData_final(Date data_final) {
		this.data_final = data_final;
	}

	public String getNome_cliente() {
		return nome_cliente;
	}

	public void setNome_cliente(String nome_cliente) {
		this.nome_cliente = nome_cliente;
	}

	public boolean isEditar_data_pedido() {
		return editar_data_pedido;
	}

	public void setEditar_data_pedido(boolean editar_data_pedido) {
		this.editar_data_pedido = editar_data_pedido;
	}

}
