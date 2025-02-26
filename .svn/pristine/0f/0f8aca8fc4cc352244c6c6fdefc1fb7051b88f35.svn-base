package com.tetrasoft.data.cliente;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

import com.technique.engine.data.DataTypes;
import com.tetrasoft.common.data.BasePersistentEntity;
import com.tetrasoft.common.data.Listable;
import com.tetrasoft.common.data.Mailing;

public class MailingEntity extends BasePersistentEntity implements Listable, Mailing {
	public static int ID_FUNCAO = 658;

	private static Object[] structurePk = new Object[]{
		"IdMailing", "idMailing", DataTypes.JAVA_LONG
	};

	private static Object[] structure = new Object[]{
		"IdFilial","idFilial",DataTypes.JAVA_LONG,
		"Nome","nome",DataTypes.JAVA_STRING,
		"DataHoraCadastro","dataHoraCadastro",DataTypes.JAVA_DATETIME,
		"Contato","contato",DataTypes.JAVA_STRING, 
		"Endereco","endereco",DataTypes.JAVA_STRING, 
		"Cidade","cidade",DataTypes.JAVA_STRING,  
		"Estado","estado",DataTypes.JAVA_STRING, 
		"Cep","cep",DataTypes.JAVA_STRING,  
		"Pais","pais",DataTypes.JAVA_STRING,  
		"Telefone1","telefone1",DataTypes.JAVA_STRING,
		"Telefone2","telefone2",DataTypes.JAVA_STRING,
		"Email","email",DataTypes.JAVA_STRING,
		"Usuario","usuario",DataTypes.JAVA_STRING,
		"Senha","senha",DataTypes.JAVA_STRING,
		"Ativo","ativo",DataTypes.JAVA_INTEGER
	};
	
	public Object[] getStructure(){
		return structure;
	}

	public Object[] getStructurePk(){
		return structurePk;
	}

	public String getTableName(){
		return "mailing";
	}

	public String get_IDFieldName(){
		return "idMailing";
	}

	public String get_NomeFieldName(){
		return "nome";
	}

	public String get_SelecioneName(){
		return LABEL_SELECIONE;
	}

	private Long idMailing = new Long(0);
	private Long idFilial=0l;
	private String nome= "";
	private Date dataHoraCadastro;
	private String contato= "";	
	private String endereco= "";	
	private String cidade= "";	
	private String estado= "";	
	private String cep= "";	
	private String pais= "";	
	private String telefone1= "";
	private String telefone2= "";
	private String email= "";
	private String usuario= "PT";
	private String senha = "";
	private Integer ativo = 1;

	public Long getIdMailing() {
		return idMailing;
	}

	public void setIdMailing(Long idMailing) {
		this.idMailing = idMailing;
	}

	public Long getIdFilial() {
		return idFilial;
	}

	public void setIdFilial(Long idFilial) {
		this.idFilial = idFilial;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataHoraCadastro() {
		return dataHoraCadastro;
	}

	public void setDataHoraCadastro(Date dataHoraCadastro) {
		this.dataHoraCadastro = dataHoraCadastro;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

	public void save() throws Exception {
		if( this.getIdMailing() == null || this.getIdMailing() == 0l ) {
			this.setIdMailing( getNextId() );
			this.setDataHoraCadastro( new Date() );
			this.insert();
		} else {
			MailingEntity tmp = new MailingEntity();
			tmp.setIdMailing( this.getIdMailing() );
			tmp.getThis();
			
			this.setDataHoraCadastro( tmp.getDataHoraCadastro() );
			this.update();
		}
	}
	
	@Override
	public String getEmailsExternos(Connection conn) {
		String retorno = "";
		try {
			MailingEntity entity = new MailingEntity();
			@SuppressWarnings("rawtypes")
			ArrayList a = entity.getArray("ativo = 1 AND email != '' ORDER BY idMailing", conn);
			for( int i = 0; i < a.size(); i++ ) {
				entity = (MailingEntity)a.get(i);
				retorno += entity.getEmail() + ";";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retorno;
	}

	@Override
	public String getNomesExternos(Connection conn) {
		String retorno = "";
		try {
			MailingEntity entity = new MailingEntity();
			@SuppressWarnings("rawtypes")
			ArrayList a = entity.getArray("ativo = 1 AND email != '' ORDER BY idMailing", conn);
			for( int i = 0; i < a.size(); i++ ) {
				entity = (MailingEntity)a.get(i);
				retorno += entity.getNome() + ";";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retorno;
	}
}