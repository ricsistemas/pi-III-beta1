package br.opet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.opet.model.PItens;
import br.opet.model.Produtos;

public class PItensDao extends Produtos {

	protected void _inserirItens(Connection conexao, ArrayList<PItens> pitens, int pedido_codigo) throws SQLException {

		String sql = "insert into pitens(pedido_id,produto_id,quantidade,preco,desconto_valor,desconto_percentual ) values(?,?,?,?,?,?)";

		PreparedStatement stmt = conexao.prepareStatement(sql);
		for (int i = 0; i < pitens.size(); i++) {
			PItens p = pitens.get(i);
			stmt.setInt(1, pedido_codigo);
			stmt.setInt(2, p.getProduto_id());
			stmt.setInt(3, p.getQtd());
			stmt.setDouble(4, p.getPreco());
			stmt.setDouble(5, p.getDesconto_vl());
			stmt.setDouble(6, p.getDesconto_perc());
			stmt.executeUpdate();
		}

		stmt.close();
	}

	public ArrayList<PItens> _getPitens(int pedido_id) {
		ArrayList<PItens> lista = new ArrayList<PItens>();
		ResultSet Resultado = null;
		PreparedStatement stmt = null;
		Connection conexao = null;

		try {
			conexao = MeuDB.criaConexao(true);
			String query = "select i.id, i.produto_id, i.quantidade,i.preco, i.desconto_valor, desconto_percentual,p.descricao from pitens i"
					+ " inner join produtos p on p.id = i.produto_id where i.pedido_id =?";
			stmt = conexao.prepareStatement(query);
			stmt.setInt(1, pedido_id);
			Resultado = stmt.executeQuery();

			while (Resultado.next()) {
				PItens item = new PItens();
				item.setId(Resultado.getInt("id"));
				item.setProduto_id(Resultado.getInt("produto_id"));
				item.setQtd(Resultado.getInt("quantidade"));
				item.setPreco(Resultado.getDouble("preco"));
				item.setDesconto_vl(Resultado.getDouble("desconto_valor"));
				item.setDesconto_perc(Resultado.getDouble("desconto_percentual"));
				item.setDescricao(Resultado.getString("descricao"));
				lista.add(item);

			}
			stmt.close();
			Resultado.close();
			conexao.close();

		} catch (Exception e) {
			System.err.println("Deu ruim aqui =>Got an exception! ");
			System.err.println(e.getMessage());

		}

		return lista;

	}

	protected void _excluirItens(Connection conexao, ArrayList<Integer> itens_excluir) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		String sql = "delete from pitens where id =?";

		for (Integer item : itens_excluir) {
			stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, item);
			stmt.execute();
			stmt.close();
		}

	}

}
