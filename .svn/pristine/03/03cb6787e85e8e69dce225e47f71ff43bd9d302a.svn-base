package com.tetrasoft.data.marketing;

import com.technique.engine.data.DataTypes;
import com.technique.engine.util.ExceptionWarning;
import com.tetrasoft.common.data.BasePersistentEntity;

public class EmailMarketingEnviadoEntity extends BasePersistentEntity {
	
	private static Object[] structurePk = new Object[] {
		"IdEmailMarketing","idEmailMarketing", DataTypes.JAVA_LONG,
		"IdUsuario","idUsuario", DataTypes.JAVA_LONG
	};
	private static Object[] structure = new Object[] {
		"Erro","erro", DataTypes.JAVA_INTEGER
	};
	
	public EmailMarketingEnviadoEntity(Long idEmailMarketing, Long idUsuario, Integer erro) throws ExceptionWarning{
		
		this.idUsuario = idUsuario;
		this.erro = erro;
		this.idEmailMarketing = idEmailMarketing;
		
		this.insert();
	}

	private Long idEmailMarketing = null;
	private Long idUsuario = null;
	private Integer erro = null;

	public Object[] getStructure() {
		return structure;
	}
	public Object[] getStructurePk() {
		return structurePk;
	}
	public String getTableName() {
		return "emailMarketingEnviado";
	}
	public Long getIdEmailMarketing() {
		return idEmailMarketing;
	}
	public void setIdEmailMarketing(Long idEmailMarketing) {
		this.idEmailMarketing = idEmailMarketing;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public void setErro(Integer erro) {
		this.erro = erro;
	}
	public Integer getErro() {
		return erro;
	}
}