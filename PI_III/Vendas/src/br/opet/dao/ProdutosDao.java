package br.opet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.opet.model.Produtos;

public class ProdutosDao {
	protected boolean _inserir(Produtos prod) {
		Connection conexao = null;
		PreparedStatement stmt = null;

		String desc = prod.getDescricao();

		if (desc.length() > 79)
			desc = desc.substring(0, 79);

		boolean insert = false;
		try {
			conexao = MeuDB.criaConexao(false);
			String sql = "insert into Produtos (descricao,custo,preco,isdelete) values(?,?,?,0)";
			stmt = conexao.prepareStatement(sql);
			stmt.setString(1, desc);
			stmt.setDouble(2, prod.getCusto());
			stmt.setDouble(3, prod.getPreco());
			stmt.executeUpdate();
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

	protected boolean _alterar(Produtos prod) {
		Connection conexao = null;
		PreparedStatement stmt = null;
		boolean update = false;
		try {
			conexao = MeuDB.criaConexao(false);
			String sql = "update Produtos set descricao=?,custo=?,preco=? where id =? ";
			stmt = conexao.prepareStatement(sql);

			stmt.setString(1, prod.getDescricao());
			stmt.setDouble(2, prod.getCusto());
			stmt.setDouble(3, prod.getPreco());
			stmt.setInt(4, prod.getId());
			stmt.executeUpdate();
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
		return update;
	}

	protected ArrayList<Produtos> listar() {
		ArrayList<Produtos> lista = new ArrayList<Produtos>();
		Connection conexao = null;
		try {
			conexao = MeuDB.criaConexao(true);
			String query = "SELECT id, descricao,custo, preco   from Produtos where isdelete =0";

			Statement st = conexao.createStatement();
			ResultSet Resultado = st.executeQuery(query);

			while (Resultado.next()) {
				Produtos prod = new Produtos();
				prod.setId(Resultado.getInt("id"));
				prod.setDescricao(Resultado.getString("descricao"));
				prod.setCusto(Resultado.getDouble("custo"));
				prod.setPreco(Resultado.getDouble("preco"));
				lista.add(prod);

			}
			st.close();
			Resultado.close();
			conexao.close();

		} catch (Exception e) {
			System.err.println("Deu ruim aqui =>Got an exception! ");
			System.err.println(e.getMessage());

			return null;
		}
		return lista;
	}

	protected boolean _excluir(Produtos prod) {
		Connection conexao = null;
		PreparedStatement stmt = null;
		boolean excluir = false;
		try {
			conexao = MeuDB.criaConexao(false);
			String sql = "update Produtos set isdelete=1 where id =? ";
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, prod.getId());
			stmt.executeUpdate();
			conexao.commit();
			excluir = true;

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
		return excluir;

	}

	protected ArrayList<Produtos> _FiltraProdutos(String pesquisa_produtos) {

		ArrayList<Produtos> lista = new ArrayList<>();
		Connection conexao = null;
		ResultSet Resultado = null;
		PreparedStatement stmt = null;
		try {
			conexao = MeuDB.criaConexao(true);

			pesquisa_produtos = "'%" + pesquisa_produtos + "%'";

			String query = "SELECT id, descricao, custo, preco  from Produtos where isdelete=0 and descricao like ?";
			stmt = conexao.prepareStatement(query);
			stmt.setString(1, pesquisa_produtos);

			Resultado = stmt.executeQuery();
			while (Resultado.next()) {
				Produtos prod = new Produtos();
				prod.setId(Resultado.getInt("id"));
				prod.setDescricao(Resultado.getString("descricao"));
				prod.setCusto(Resultado.getDouble("custo"));
				prod.setPreco(Resultado.getDouble("preco"));
				lista.add(prod);
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

	protected ArrayList<Produtos> _Filtra_Produto_Nome(String pesquisa_produto) {
		ArrayList<Produtos> lista = new ArrayList<>();
		Connection conexao = null;
		ResultSet Resultado = null;
		PreparedStatement stmt = null;
		try {
			conexao = MeuDB.criaConexao(true);
			String query = "SELECT id, descricao,custo, preco  from produtos where isdelete=0 and descricao like" + "'%"
					+ pesquisa_produto + "%'";
			stmt = conexao.prepareStatement(query);
			// stmt.setString(1, s);

			Resultado = stmt.executeQuery();
			while (Resultado.next()) {
				Produtos prod = new Produtos();
				prod.setId(Resultado.getInt("id"));
				prod.setDescricao(Resultado.getString("descricao"));
				prod.setCusto(Resultado.getDouble("custo"));
				prod.setPreco(Resultado.getDouble("preco"));
				lista.add(prod);
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

}
