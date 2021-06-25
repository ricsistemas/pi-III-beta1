package br.opet.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import br.opet.dao.PItensDao;

public class PItens extends PItensDao {

	private int id;
	private int pedido_id;
	private int produto_id;
	private int qtd;
	private double desconto_vl;
	private double desconto_perc;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPedido_id() {
		return pedido_id;
	}

	public void setPedido_id(int pedido_id) {
		this.pedido_id = pedido_id;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public double getDesconto_perc() {
		return desconto_perc;
	}

	public void setDesconto_perc(double desconto_perc) {
		this.desconto_perc = desconto_perc;
	}

	public double getDesconto_vl() {
		return desconto_vl;
	}

	public void setDesconto_vl(double desconto_vl) {
		this.desconto_vl = desconto_vl;
	}

	public void inserirItens(Connection conexao, ArrayList<PItens> pitens, int pedido_codigo) throws SQLException {
		// TODO Auto-generated method stub
		super._inserirItens(conexao, pitens, pedido_codigo);

	}

	public int getProduto_id() {
		return produto_id;
	}

	public void setProduto_id(int produto_id) {
		this.produto_id = produto_id;
	}

	public ArrayList<PItens> getPitens(int pedido_id) {
		// TODO Auto-generated method stub
		return super._getPitens(pedido_id);
	}

	public void excluirItens(Connection conexao, ArrayList<Integer> itens_excluir) throws SQLException {
		// TODO Auto-generated method stub
		super._excluirItens(conexao, itens_excluir);

	}

}
