package br.opet.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import br.opet.dao.PedidosDao;

public class Pedidos extends PedidosDao {
	private int id;
	private Clientes clientes;
	private int usuario_id;
	private Date dt_pedido;
	private Date dt_entrega;
	private int status;
	private Double vl_pedido;
	private int Forma_Pagamento;
	private HashMap<Integer, PItens> pitens;
	private String pesquisa_produtos;

	public void setVl_pedido(Double vl_pedido) {
		this.vl_pedido = vl_pedido;
	}

	public Pedidos() {
		clientes = new Clientes();
		pitens = new HashMap<Integer, PItens>();
	}

	public void removeItem(PItens item) {
		pitens.remove(item.getProduto_id());

	}

	public Clientes getClientes() {
		return clientes;
	}

	public void setClientes(Clientes clientes) {
		this.clientes = clientes;
	}

	public Date getDt_pedido() {
		return dt_pedido;
	}

	public void setDt_pedido(Date dt_pedido) {
		this.dt_pedido = dt_pedido;
	}

	public Date getDt_entrega() {
		return dt_entrega;
	}

	public void setDt_entrega(Date dt_entrega) {
		this.dt_entrega = dt_entrega;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Double getVl_pedido() {
		return vl_pedido;
	}

	public ArrayList<PItens> getPitens() {
		ArrayList<PItens> lista_Nova = new ArrayList<PItens>();

		lista_Nova.addAll(pitens.values());
		double valor = 0.0;

		for (PItens produto : lista_Nova) {
			valor += produto.getPreco() * produto.getQtd();
		}

		this.vl_pedido = valor;

		return lista_Nova;
	}

	public String getPesquisa_produtos() {
		return pesquisa_produtos;
	}

	public void setPesquisa_produtos(String pesquisa_produtos) {
		this.pesquisa_produtos = pesquisa_produtos;
	}

	public void AddItens(PItens item) {
		pitens.put(item.getProduto_id(), item);

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean inserir() {
		return super._inserir(this);
	}

	public int getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(int usuario_id) {
		this.usuario_id = usuario_id;
	}

	public int getForma_Pagamento() {
		return Forma_Pagamento;
	}

	public void setForma_Pagamento(int forma_Pagamento) {
		Forma_Pagamento = forma_Pagamento;
	}

	public ArrayList<Pedidos> Listar_Pedidos() {
		return super._Listar_Pedidos();
	}

	public boolean Alterar(ArrayList<PItens> itens_novos, ArrayList<Integer> itens_excluir) {

		return super._Alterar(this, itens_novos, itens_excluir);

	}

	public boolean Excluir() {
		// TODO Auto-generated method stub
		return super._Excluir(this.getId());
	}

}
