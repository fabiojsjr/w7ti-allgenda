package com.tetrasoft.data.marketing;

import java.util.Date;

import com.technique.engine.data.DataTypes;
import com.technique.engine.util.ExceptionWarning;
import com.tetrasoft.common.data.BasePersistentEntity;

public class EmailMarketingEntity extends BasePersistentEntity {
	
	private static Object[] structurePk = new Object[] {
		"IdEmailMarketing","idEmailMarketing", DataTypes.JAVA_LONG,
		"IdUsuario","idUsuario", DataTypes.JAVA_LONG
	};
	private static Object[] structure = new Object[] {
		"PathArquivo","pathArquivo", DataTypes.JAVA_STRING,
		"PathImagem","pathImagem", DataTypes.JAVA_STRING,
		"DataDeEnvio","dataDeEnvio", DataTypes.JAVA_DATE
	};
	
	public EmailMarketingEntity(Long idUsuario, String pathArquivo, String pathImagem, Date dataDeEnvio) throws ExceptionWarning{
		
		this.idUsuario = idUsuario;
		this.pathArquivo = pathArquivo;
		this.pathImagem = pathImagem;
		this.dataDeEnvio = dataDeEnvio;
		
		this.idEmailMarketing = getNextId();
		
		this.insert();
	}

	private Long idEmailMarketing = null;
	private Long idUsuario = null;
	private String pathArquivo = null;
	private String pathImagem = null;
	private Date dataDeEnvio = null;

	public Object[] getStructure() {
		return structure;
	}
	public Object[] getStructurePk() {
		return structurePk;
	}
	public String getTableName() {
		return "emailMarketing";
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
	public String getPathArquivo() {
		return pathArquivo;
	}
	public void setPathArquivo(String pathArquivo) {
		this.pathArquivo = pathArquivo;
	}
	public String getPathImagem() {
		return pathImagem;
	}
	public void setPathImagem(String pathImagem) {
		this.pathImagem = pathImagem;
	}
	public Date getDataDeEnvio() {
		return dataDeEnvio;
	}
	public void setDataDeEnvio(Date dataDeEnvio) {
		this.dataDeEnvio = dataDeEnvio;
	}
	
	
	
}