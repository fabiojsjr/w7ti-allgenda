package com.tetrasoft.data.allgenda;

import com.technique.engine.data.DataTypes;
import com.tetrasoft.common.data.BasePersistentEntity;

public class CategoriaEntity extends BasePersistentEntity {
	public static int ID_FUNCAO = 657;

	private static Object[] structurePk = new Object[]{
		"IdCategoria", "idCategoria", DataTypes.JAVA_LONG
	};

	private static Object[] structure = new Object[]{
		"OrdemMenu","ordemMenu",DataTypes.JAVA_INTEGER,
		"Nome","nome",DataTypes.JAVA_STRING,
		"Cor","cor",DataTypes.JAVA_STRING, 
		"Ativo","ativo",DataTypes.JAVA_INTEGER
	};
	
	public Object[] getStructure(){
		return structure;
	}

	public Object[] getStructurePk(){
		return structurePk;
	}

	public String getTableName(){
		return "categoria";
	}

	public String get_IDFieldName(){
		return "idCategoria";
	}

	private Long idCategoria = new Long(0);
	private Integer ordemMenu = 0;
	private String nome= "";
	private String cor= "";	
	private Integer ativo = 1;

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCor() {
		if( cor == null || cor.equals("") ) cor = "#0198CD";
		
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}
	
	public Integer getOrdemMenu() {
		return ordemMenu;
	}

	public void setOrdemMenu(Integer ordemMenu) {
		this.ordemMenu = ordemMenu;
	}

	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

	public void save() throws Exception {
		CategoriaEntity tmp = new CategoriaEntity();
		tmp.setIdCategoria( this.getIdCategoria() );
		
		if( tmp.getThis() ) { // jé tem no banco
			this.update();
		} else {
			this.insert();
		}
	}
}