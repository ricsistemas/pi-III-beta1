package br.opet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.opet.model.Enderecos;

public class EnderecosDao {

	protected void _inserirEnd(Connection conexao, Enderecos e) throws SQLException {

		String sql = "insert into Enderecos (cliente_id, Cep, logradouro, numero,Estado, " + "Cidade, " + "Bairro, "
				+ "Complemento, " + "Local_Descricao, " + "Principal, " + " isdelete) "
				+ "values(?,?,?,?,?,?,?,?,?,1,0)";
		PreparedStatement stmt = null;
		stmt = conexao.prepareStatement(sql);
		stmt.setInt(1, e.getCliente_id());
		stmt.setString(2, e.getCep());
		stmt.setString(3, e.getLogradouro());
		stmt.setInt(4, e.getNumero());
		stmt.setString(5, e.getEstado());
		stmt.setString(6, e.getCidade());
		stmt.setString(7, e.getBairro());
		stmt.setString(8, e.getCompl());
		stmt.setString(9, e.getLocal());
		stmt.executeUpdate();
		stmt.close();
	}

	protected void _update(Connection conexao, Enderecos ende) throws SQLException {
		// TODO Auto-generated method stub Connection conexao = null;

		String sql = "update enderecos set Cep=?, logradouro=?, Estado=?,Cidade=?,Bairro=?,Complemento=?,numero=? where id =?";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		stmt.setString(1, ende.getCep());
		stmt.setString(2, ende.getLogradouro());
		stmt.setString(3, ende.getEstado());
		stmt.setString(4, ende.getCidade());
		stmt.setString(5, ende.getBairro());
		stmt.setString(6, ende.getCompl());
		stmt.setInt(7, ende.getNumero());
		stmt.setInt(8, ende.getId());
		stmt.executeUpdate();
		stmt.close();

	}
}
