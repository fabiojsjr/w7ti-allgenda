package com.tetrasoft.data.usuario;

import com.technique.engine.data.DataTypes;
import com.tetrasoft.common.data.BasePersistentEntity;

public class PerfilFuncionalidadeEntity extends BasePersistentEntity {

	private static Object[] structurePk = new Object[] {
		"IdPerfilAcesso","idPerfilAcesso", DataTypes.JAVA_LONG,
		"IdFuncionalidade","idFuncionalidade", DataTypes.JAVA_LONG
	};

	private static Object[] structure = new Object[] {
		"Incluir","incluir", DataTypes.JAVA_INTEGER,
		"Alterar","alterar", DataTypes.JAVA_INTEGER,
		"Excluir","excluir", DataTypes.JAVA_INTEGER,
		"Consultar","consultar", DataTypes.JAVA_INTEGER
	};
	
	private Long idPerfilAcesso=null;
	private Long idFuncionalidade=null;
	private Integer incluir=new Integer(0);
	private Integer alterar=new Integer(0);
	private Integer excluir=new Integer(0);
	private Integer consultar=new Integer(0);
	
	public Long getIdPerfilAcesso() {
    	return idPerfilAcesso;
    }

	public void setIdPerfilAcesso(Long idPerfilAcesso) {
    	this.idPerfilAcesso = idPerfilAcesso;
    }

	public Long getIdFuncionalidade() {
    	return idFuncionalidade;
    }

	public void setIdFuncionalidade(Long idFuncionalidade) {
    	this.idFuncionalidade = idFuncionalidade;
    }

	public Integer getIncluir() {
    	return incluir;
    }

	public void setIncluir(Integer incluir) {
    	this.incluir = incluir;
    }

	public Integer getAlterar() {
    	return alterar;
    }

	public void setAlterar(Integer alterar) {
    	this.alterar = alterar;
    }

	public Integer getExcluir() {
    	return excluir;
    }

	public void setExcluir(Integer excluir) {
    	this.excluir = excluir;
    }

	public Integer getConsultar() {
    	return consultar;
    }

	public void setConsultar(Integer consultar) {
    	this.consultar = consultar;
    }

	public Object[] getStructurePk() {
		return structurePk;
	}

	public Object[] getStructure() {
		return structure;
	}

	public String getTableName() {
		return "perfil_funcionalidade";
	}
}