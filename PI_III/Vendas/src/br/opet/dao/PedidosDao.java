package br.opet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.opet.model.Base;
import br.opet.model.PItens;
import br.opet.model.Pedidos;

public class PedidosDao extends Base {
	private Connection conexao;

	protected boolean _inserir(Pedidos ped) {
		Connection conexao = null;
		PreparedStatement stmt = null;
		boolean insert = false;
		try {
			conexao = MeuDB.criaConexao(false);
			String sql = "insert into Pedidos(dt_Pedido, dt_entrega, cliente_id, forma_pagamento, vl_pedido, isdelete ) values(?, ?, ?, ?, ?, 0)";
			stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setDate(1, new java.sql.Date(ped.getDt_pedido().getTime()));
			stmt.setDate(2, new java.sql.Date(ped.getDt_entrega().getTime()));
			stmt.setInt(3, ped.getClientes().getId());
			stmt.setInt(4, ped.getForma_Pagamento());
			stmt.setDouble(5, ped.getVl_pedido());
			stmt.executeUpdate();
			final ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				ped.setId(rs.getInt(1));
			}
			PItens pitem = new PItens();
			pitem.inserirItens(conexao, ped.getPitens(), ped.getId());
			conexao.commit();
			insert = true;

		} catch (SQLException e) {
			try {
				conexao.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("** Cancelado erro **");
		}

		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conexao.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return insert;

	}

	protected ArrayList<Pedidos> _Listar_Pedidos() {
		ArrayList<Pedidos> lista = new ArrayList<>();
		Connection conexao = null;
		ResultSet Resultado = null;
		PreparedStatement stmt = null;
		try {
			conexao = MeuDB.criaConexao(true);
			String query = "select p.id,  p.dt_pedido, p.dt_entrega, p.Status_pedido,cliente_id,"
					+ " p.forma_pagamento, p.vl_pedido, c.nome_completo from pedidos p"
					+ " join clientes c on c.id = p.cliente_id  where p.isdelete =0";
			stmt = conexao.prepareStatement(query);
			Resultado = stmt.executeQuery();
			while (Resultado.next()) {
				Pedidos ped = new Pedidos();
				ped.getClientes().setId(Resultado.getInt("cliente_id"));
				ped.getClientes().setNome_completo(Resultado.getString("nome_completo"));
				ped.setId(Resultado.getInt("id"));
				ped.setDt_pedido(Resultado.getDate("dt_pedido"));
				ped.setDt_entrega(Resultado.getDate("dt_entrega"));
				ped.setForma_Pagamento(Resultado.getInt("forma_pagamento"));
				ped.setStatus(Resultado.getInt("status_pedido"));
				ped.setVl_pedido(Resultado.getDouble("vl_pedido"));
				ped.getClientes().setNome_completo(Resultado.getString("nome_completo"));
				lista.add(ped);
			}

		} catch (Exception e) {
			try {
				stmt.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Resultado.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				conexao.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			System.err.println(e.getMessage());
			System.err.println("Deu ruim aqui =>Got an exception! ");

		}

		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Resultado.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conexao.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lista;
	}

	protected boolean _Alterar(Pedidos ped, ArrayList<PItens> itens_novos, ArrayList<Integer> itens_excluir) {
		Connection conexao = null;
		boolean update = false;
		try {
			conexao = MeuDB.criaConexao(false);
			String sql = "update pedidos set dt_Pedido=?, dt_entrega=?, cliente_id=?, forma_pagamento=?, vl_pedido=?  where id =?";// tira
																																	// data
																																	// pedido
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setDate(1, new java.sql.Date(ped.getDt_pedido().getTime()));
			stmt.setDate(2, new java.sql.Date(ped.getDt_entrega().getTime()));
			stmt.setInt(3, ped.getClientes().getId());
			stmt.setInt(4, ped.getForma_Pagamento());
			stmt.setDouble(5, ped.getVl_pedido());
			stmt.setInt(6, ped.getId());
			stmt.executeUpdate();
			// itens
			PItens pitem = new PItens();
			pitem.inserirItens(conexao, itens_novos, ped.getId());
			pitem.excluirItens(conexao, itens_excluir);
			conexao.commit();
			update = true;
		} catch (SQLException e) {
			try {
				conexao.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("** Cancelado erro **");
		}

		try {
			conexao.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return update;

	}

	protected boolean _Excluir(int id) {
		conexao = null;
		boolean excluido = false;
		try {
			conexao = MeuDB.criaConexao(false);
			String sql = "update Pedidos set isdelete=1 where id =?";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.executeUpdate();
			conexao.commit();
			excluido = true;
			stmt.close();
			conexao.close();

		} catch (SQLException e1) {
			try {
				conexao.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			e1.printStackTrace();

		}

		return excluido;
	}

}
