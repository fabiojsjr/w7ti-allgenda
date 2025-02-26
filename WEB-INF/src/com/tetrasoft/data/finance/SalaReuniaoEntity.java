package com.tetrasoft.data.finance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.technique.engine.data.DataTypes;
import com.technique.engine.util.ExceptionInjection;
import com.technique.engine.util.ExceptionWarning;
import com.tetrasoft.common.data.BasePersistentEntity;
import com.tetrasoft.common.data.Listable;
import com.tetrasoft.data.cliente.TaskEntity;

public class SalaReuniaoEntity extends BasePersistentEntity implements Listable {
	
	private static final long serialVersionUID = 1L;
	public static int ID_FUNCAO = 907;
	private String nome;
	private Integer numero;
	private Integer capacidade;
	private Integer ativo;
	private Long idSalaReuniao;
	private String googleClientKey;
	private String googleApiKey;
	private String googleCalendarKey;
	
	private static Object[] structurePk = new Object[] {"IdSalaReuniao","idSalaReuniao", DataTypes.JAVA_LONG};
	
	private static Object[] structure = new Object[] {
			"Nome","nome", DataTypes.JAVA_STRING,	
			"Numero","numero",DataTypes.JAVA_INTEGER,
			"Ativo","ativo",DataTypes.JAVA_INTEGER,
			"Capacidade","capacidade",DataTypes.JAVA_INTEGER,
			"GoogleClientKey","googleClientKey",DataTypes.JAVA_STRING,
			"GoogleApiKey","googleApiKey",DataTypes.JAVA_STRING,
			"GoogleCalendarKey","googleCalendarKey",DataTypes.JAVA_STRING			
		};
	

	public static int getID_FUNCAO() {return ID_FUNCAO;}
	public static void setID_FUNCAO(int iD_FUNCAO) {ID_FUNCAO = iD_FUNCAO;}

	public String getNome() {return nome;}
	public void setNome(String nome) {this.nome = nome;}
	
	public Integer getNumero() {return numero;}
	public void setNumero(Integer numero) {this.numero = numero;}

	public Integer getCapacidade() {return capacidade;}
	public void setCapacidade(Integer capacidade) {this.capacidade = capacidade;}

	public Integer getAtivo() {return ativo;}
	public void setAtivo(Integer ativo) {this.ativo = ativo;}

	public Long getIdSalaReuniao() {return idSalaReuniao;}
	public void setIdSalaReuniao(Long idSalaReuniao) {this.idSalaReuniao = idSalaReuniao;}	
	
	public String getGoogleClientKey() {return googleClientKey;}
	public void setGoogleClientKey(String googleClientKey) {this.googleClientKey = googleClientKey;}	
	
	public String getGoogleApiKey() {return googleApiKey;}
	public void setGoogleApiKey(String googleApiKey) {this.googleApiKey = googleApiKey;}	
	
	public String getGoogleCalendarKey() {return googleCalendarKey;}
	public void setGoogleCalendarKey(String googleCalendarKey) {this.googleCalendarKey = googleCalendarKey;}
	
	
	@Override
	public Object[] getStructure() {return structure;}
	
	@Override
	public Object[] getStructurePk() {return structurePk;}

	@Override
	public String get_IDFieldName() {return "idSalaReuniao";}

	@Override
	public String get_NomeFieldName() {return "nome";}

	@Override
	public String get_SelecioneName() { return LABEL_SELECIONE;}

	@Override
	public String getTableName() {return "salaReuniao";}
	
	
	public void save(boolean jaExiste) throws ExceptionWarning, ExceptionInjection {
		
		SalaReuniaoEntity pe = new SalaReuniaoEntity();
		pe.setNome(this.nome);
		pe.setNumero(this.numero);	
		pe.setCapacidade(this.capacidade);
		pe.setAtivo(1);
		pe.setIdSalaReuniao(this.idSalaReuniao);
		pe.setGoogleClientKey(this.googleClientKey);
		pe.setGoogleApiKey(this.googleApiKey);
		pe.setGoogleCalendarKey(this.googleCalendarKey);
		
		if( jaExiste ) {
			this.update();
		} else {
			this.insert();
		}

	 }
	
	public void deletar(Long idReuniao) throws ExceptionWarning {
		
		TaskEntity taskEntity = new TaskEntity();
		
		String sql = "DELETE FROM " +  taskEntity.getTableName()  + " WHERE idTask = ?";
		
		Connection conn = getConnection();
		
		try {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setLong(1, idReuniao);
			ps.execute();
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}	
		
	}

}
