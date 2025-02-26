package com.tetrasoft.data.basico;

import com.technique.engine.data.DataTypes;
import com.tetrasoft.common.data.BasePersistentEntity;
import com.tetrasoft.common.data.Listable;

public class EstadoEntity extends BasePersistentEntity implements Listable {
	
	public static int ID_FUNCAO = 669;
	
    private static Object[] structurePk = new Object[] {
      "IdEstado","idEstado", DataTypes.JAVA_LONG
    };
    private static Object[] structure = new Object[] {
      "Nome","nome", DataTypes.JAVA_STRING,
      "IdPais","idPais", DataTypes.JAVA_LONG,
      "Abreviacao","abreviacao", DataTypes.JAVA_STRING,
      "Dirty", "dirty", DataTypes.JAVA_INTEGER,
      "Ativo", "ativo", DataTypes.JAVA_INTEGER
    };

    public Object[] getStructurePk() {
        return structurePk;
    }

    public Object[] getStructure() {
        return structure;
    }

    public String getTableName() {
        return "estado";
    }

    private Long idEstado=null;
    public void setIdEstado(Long value) {
       idEstado = value;
    }
    public Long getIdEstado() {
       return idEstado;
    }

    private String nome=null;
    public void setNome(String value) {
       nome = value;
    }
    public String getNome() {
       return nome == null ? "" : nome;
    }

    private String abreviacao=null;
    public void setAbreviacao(String value) {
       if (value!=null) value=value.toUpperCase();
       abreviacao = value;
    }
    public String getAbreviacao() {
       return abreviacao;
    }

    public String get_IDFieldName() {
        return "idEstado";
    }

    public String get_NomeFieldName() {
        return "nome";
    }

    public String get_SelecioneName() {
        return LABEL_SELECIONE;
    }

    public Long getIdPais() {
        return idPais;
    }

    public void setIdPais(Long value) {
        this.idPais=value;
    }

    private Integer dirty = new Integer(1);
    public Integer getDirty() {
        return dirty;
    }

    public void setDirty(Integer value) {
        this.dirty=value;
    }

    private Long idPais=null;

    private Integer ativo = null;

	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}
    
}

