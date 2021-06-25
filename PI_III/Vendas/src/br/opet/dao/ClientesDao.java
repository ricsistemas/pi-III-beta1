package br.opet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.opet.model.Base;
import br.opet.model.Clientes;
import br.opet.model.Enderecos;

public class ClientesDao extends Base {
	private Connection conexao;

	public ClientesDao() {

	}

	protected boolean _inserir(Clientes cli) {
		Connection conexao = null;
		PreparedStatement stmt = null;
		boolean insert = false;
		try {
			conexao = MeuDB.criaConexao(false);
			String sql = "insert into Clientes (nome_completo,genero,cpf,rg,celular,telefone,dt_nascimento,isdelete) "
					+ " values(?,?,?,?,?,?,?,0)";
			stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, cli.getNome_completo());
			stmt.setString(2, cli.getGenero());
			stmt.setString(3, cli.getCpf());
			stmt.setString(4, cli.getRg());
			stmt.setString(5, cli.getCelular());
			stmt.setString(6, cli.getTelefone());
			stmt.setDate(7, new java.sql.Date(cli.getDt_nascimento().getTime()));
			stmt.executeUpdate();
			stmt.close();
			final ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				cli.setId(rs.getInt(1));
				cli.getEndereco().setCliente_id(cli.getId());
			}

			cli.getEndereco().inserirEnd(conexao, cli.getEndereco());
			conexao.commit();
			insert = true;

		} catch (SQLException e) {
			try {
				conexao.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(e.getMessage());
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

	protected boolean _update(Clientes cli) {
		conexao = null;
		PreparedStatement stmt = null;
		boolean update = false;
		try {

			conexao = MeuDB.criaConexao(false);
			String sql = "update Clientes set nome_completo=?, genero=?,cpf=?,rg=?,celular=?,telefone=?,dt_nascimento=? where id=?";
			stmt = conexao.prepareStatement(sql);
			stmt.setString(1, cli.getNome_completo());
			stmt.setString(2, cli.getGenero());
			stmt.setString(3, cli.getCpf());
			stmt.setString(4, cli.getRg());
			stmt.setString(5, cli.getCelular());
			stmt.setString(6, cli.getTelefone());
			stmt.setDate(7, new java.sql.Date(cli.getDt_nascimento().getTime()));
			stmt.setInt(8, cli.getId());
			stmt.executeUpdate();
			Enderecos ende = cli.getEndereco();
			ende.update(conexao);
			conexao.commit();
			update = true;

		} catch (SQLException e) {
			try {
				conexao.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(e.getMessage());
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

	protected ArrayList<Clientes> _filtra_nomes(String pesquisa_nome) {
		ArrayList<Clientes> lista = new ArrayList<>();
		conexao = null;
		ResultSet Resultado = null;
		PreparedStatement stmt = null;
		try {
			conexao = MeuDB.criaConexao(true);
			String query = "SELECT c.id, c.nome_completo, c.cpf, c.rg,c.dt_nascimento, e.id as endereco_id, e.cep, e.logradouro,e.estado,e.cidade,e.bairro,e.complemento,e.numero  from clientes c"
					+ " left join enderecos e on e.cliente_id = c.id where c.isdelete=0 and nome_completo like " + "'%"
					+ pesquisa_nome + "%'";
			stmt = conexao.prepareStatement(query);
			// stmt.setString(1, s);
			Resultado = stmt.executeQuery();
			while (Resultado.next()) {
				Clientes cli = new Clientes();
				cli.setId(Resultado.getInt("id"));
				cli.setNome_completo(Resultado.getString("nome_completo"));
				cli.setCpf(Resultado.getString("cpf"));
				cli.setRg(Resultado.getString("rg"));
				cli.setDt_nascimento(Resultado.getDate("dt_nascimento"));

				cli.getEndereco().setId(Resultado.getInt("endereco_id"));
				cli.getEndereco().setCep(Resultado.getString("cep"));
				cli.getEndereco().setLogradouro(Resultado.getString("logradouro"));
				cli.getEndereco().setEstado(Resultado.getString("estado"));
				cli.getEndereco().setCidade(Resultado.getString("cidade"));
				cli.getEndereco().setBairro(Resultado.getString("bairro"));
				cli.getEndereco().setCompl(Resultado.getString("complemento"));
				cli.getEndereco().setNumero(Resultado.getInt("numero"));

				lista.add(cli);
			}
			stmt.close();
			Resultado.close();
			conexao.close();
		} catch (SQLException e) {

		}
		return lista;
	}

	public boolean _excluir(int cliente_id) {
		conexao = null;
		boolean excluido = false;
		try {
			conexao = MeuDB.criaConexao(false);
			String sql = "update Clientes set isdelete=1 where id =?";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, cliente_id);
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
