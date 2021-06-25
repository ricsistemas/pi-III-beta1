package br.opet.model;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.opet.dao.ClientesDao;

@ManagedBean
@RequestScoped
public class Clientes extends ClientesDao {
	private int id;
	private String nome_completo;
	private String genero;
	private String cpf;
	private String rg;
	private String celular;
	private String telefone;
	private Date dt_nascimento;
	private Enderecos endereco = new Enderecos();

	public Clientes() {

	}

	public boolean alterar() {
		return super._update(this);
	}

	public boolean inserir() {

		return super._inserir(this);

	}

	public String getNome_completo() {
		return nome_completo;
	}

	public void setNome_completo(String nome_completo) {
		this.nome_completo = nome_completo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Date getDt_nascimento() {
		return dt_nascimento;
	}

	public void setDt_nascimento(Date dt_nascimento) {
		this.dt_nascimento = dt_nascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Enderecos getEndereco() {
		return endereco;
	}

	public void setEndereco(Enderecos endereco) {
		this.endereco = endereco;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Clientes> filtra_nome(String pesquisa_nome) {
		return super._filtra_nomes(pesquisa_nome);
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public boolean excluir() {
		// TODO Auto-generated method stub
		return super._excluir(this.getId());
	}

}
