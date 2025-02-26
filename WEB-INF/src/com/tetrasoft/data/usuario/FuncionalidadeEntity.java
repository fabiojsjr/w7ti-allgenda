package com.tetrasoft.data.usuario;

import com.technique.engine.data.DataTypes;
import com.tetrasoft.common.data.BasePersistentEntity;

public class FuncionalidadeEntity extends BasePersistentEntity {

	private static Object[] structurePk = new Object[] {
		"IdFuncionalidade","idFuncionalidade", DataTypes.JAVA_LONG
	};

	private static Object[] structure = new Object[] {
		"NomeEN","nomeEN", DataTypes.JAVA_STRING,
		"NomePT","nomePT", DataTypes.JAVA_STRING,
		"Grupo","grupo", DataTypes.JAVA_INTEGER,
		"Icone","icone", DataTypes.JAVA_STRING,
		"Comando1","comando1", DataTypes.JAVA_STRING,
		"Comando2","comando2", DataTypes.JAVA_STRING,
		"Comando3","comando3", DataTypes.JAVA_STRING,
		"Label1","label1", DataTypes.JAVA_STRING,
		"Label2","label2", DataTypes.JAVA_STRING,
		"Label3","label3", DataTypes.JAVA_STRING,
		"Oculto","oculto", DataTypes.JAVA_INTEGER
	};

	public Object[] getStructurePk() {
		return structurePk;
	}

	public Object[] getStructure() {
		return structure;
	}

	public String getTableName() {
		return "funcionalidade";
	}

	private Long idFuncionalidade=null;
	private String nomeEN=null;
	private String nomePT=null;
	private Integer grupo=null;
	private String icone=null;
	private String comando1=null;
	private String comando2=null;
	private String comando3=null;
	private String label1=null;
	private String label2=null;
	private String label3=null;
	private Integer oculto=new Integer(0);

	public Long getIdFuncionalidade() {
		return idFuncionalidade;
	}

	public void setIdFuncionalidade(Long idFuncionalidade) {
		this.idFuncionalidade = idFuncionalidade;
	}

	public String getNome( String idioma ) {
		if( idioma.equals("PT") ) {
			return getNomeEN();
		} else {
			return getNomePT();
		}
	}
	public String getNomeEN() {
		return nomeEN;
	}

	public void setNomeEN(String nomeEN) {
		this.nomeEN = nomeEN;
	}

	public String getNomePT() {
		return nomePT;
	}

	public void setNomePT(String nomePT) {
		this.nomePT = nomePT;
	}

	public void setGrupo(Integer value) {
		grupo = value;
	}
	public Integer getGrupo() {
		return grupo;
	}

	public String getIcone() {
		return icone;
	}
	public void setIcone(String value) {
		this.icone=value;
	}

	public String getComando1() {
		return comando1;
	}
	public void setComando1(String value) {
		this.comando1=value;
	}

	public String getComando2() {
		return comando2;
	}
	public void setComando2(String value) {
		this.comando2=value;
	}

	public String getComando3() {
		return comando3;
	}
	public void setComando3(String value) {
		this.comando3=value;
	}
	public String getLabel1() {
		return label1;
	}

	public void setLabel1(String label1) {
		this.label1 = label1;
	}

	public String getLabel2() {
		return label2;
	}

	public void setLabel2(String label2) {
		this.label2 = label2;
	}

	public String getLabel3() {
		return label3;
	}

	public void setLabel3(String label3) {
		this.label3 = label3;
	}

	public Integer getOculto() {
		return oculto;
	}

	public void setOculto(Integer oculto) {
		this.oculto = oculto;
	}
}