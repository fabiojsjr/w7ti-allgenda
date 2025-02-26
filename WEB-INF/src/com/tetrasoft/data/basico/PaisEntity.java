package com.tetrasoft.data.basico;

import com.technique.engine.data.DataTypes;
import com.tetrasoft.common.data.BasePersistentEntity;
import com.tetrasoft.common.data.Listable;

public class PaisEntity extends BasePersistentEntity implements Listable {

    private static Object[] structurePk = new Object[] {
      "IdPais","idPais", DataTypes.JAVA_LONG
    };
    private static Object[] structure = new Object[] {
      "Nome","nome", DataTypes.JAVA_STRING,
    };
    
    private Long idPais;
    private String nome;

    public Long getIdPais() {
		return idPais;
	}

	public void setIdPais(Long idPais) {
		this.idPais = idPais;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Object[] getStructurePk() {
        return structurePk;
    }

    public Object[] getStructure() {
        return structure;
    }

    public String getTableName() {
        return "pais";
    }

    public String get_IDFieldName() {
        return "idPais";
    }

    public String get_NomeFieldName() {
        return "nome";
    }

    public String get_SelecioneName() {
        return LABEL_SELECIONE;
    }
}

