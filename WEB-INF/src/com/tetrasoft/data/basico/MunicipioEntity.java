package com.tetrasoft.data.basico;

import com.technique.engine.data.DataTypes;
import com.tetrasoft.common.data.BasePersistentEntity;
import com.tetrasoft.common.data.Listable;

public class MunicipioEntity extends BasePersistentEntity implements Listable {
	private static Object[] structure = new Object[]{
		"IdMunicipio","idMunicipio", DataTypes.JAVA_LONG,
	};
	private static Object[] structurePk = new Object[]{
		"IdEstado","idEstado", DataTypes.JAVA_LONG,
		"Descricao","descricao", DataTypes.JAVA_STRING,
	};
	
	private Long idMunicipio;
	private Long idEstado;
	private String descricao;
	
	public Long getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Long idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public Long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	}

	public String getDescricao() {
		return descricao == null ? "" : descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String get_IDFieldName() {
		return "idMunicipio";
	}

	public String get_NomeFieldName() {
		return "descricao";
	}

	public String get_SelecioneName() {
		return LABEL_SELECIONE;
	}

	public Object[] getStructure() {
		return structure;
	}

	public Object[] getStructurePk() {
		return structurePk;
	}

	public String getTableName() {
		return "municipio";
	}

}
