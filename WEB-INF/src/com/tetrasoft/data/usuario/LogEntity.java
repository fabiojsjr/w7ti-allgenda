package com.tetrasoft.data.usuario;

// JAVA PACKAGES
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.technique.engine.data.DataTypes;
import com.technique.engine.util.ExceptionInfo;
import com.technique.engine.util.ExceptionWarning;
import com.technique.engine.xml.XMLData;
import com.tetrasoft.common.data.BasePersistentEntity;
import com.tetrasoft.common.data.Listable;

public class LogEntity extends BasePersistentEntity implements Listable {
	public static int TIPO_SITE 	= 1;
	public static int TIPO_INTRANET = 2;
	public static int TIPO_ANDROID  = 3;

	protected static String LABEL_SELECIONE="-- selecione --";

	private static Object[] structurePk = new Object[] {
		"IdLog","idLog", DataTypes.JAVA_LONG
	};
	private static Object[] structure = new Object[] {
		"DataHora","dataHora", DataTypes.JAVA_DATETIME,
		"IdUsuario","idUsuario", DataTypes.JAVA_LONG,
		"Tabela","tabela", DataTypes.JAVA_STRING,
		"Campo","campo", DataTypes.JAVA_STRING,
		"IdCampo","idCampo", DataTypes.JAVA_LONG,
		"IpOrigem","ipOrigem", DataTypes.JAVA_STRING,
		"IpServidor","ipServidor", DataTypes.JAVA_STRING,
		"Tipo","tipo", DataTypes.JAVA_INTEGER,
		"Observacoes","observacoes", DataTypes.JAVA_STRINGBUFFER
	};

	private Long idLog=null;
	private Date dataHora=null;
	private Long idUsuario=null;
	private String tabela=null;
	private String campo=null;
	private Long idCampo=null;
	private String ipOrigem=null;
	private String ipServidor=null;
	private StringBuffer observacoes=null;
	private Long total=null;
	private Integer tipo=TIPO_SITE;

	public Long getIdLog() {
		return idLog;
	}

	public void setIdLog(Long idLog) {
		this.idLog = idLog;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getTabela() {
		return tabela;
	}

	public void setTabela(String tabela) {
		this.tabela = tabela;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public Long getIdCampo() {
		return idCampo;
	}

	public void setIdCampo(Long idCampo) {
		this.idCampo = idCampo;
	}

	public String getIpOrigem() {
		return ipOrigem;
	}

	public void setIpOrigem(String ipOrigem) {
		this.ipOrigem = ipOrigem;
	}

	public String getIpServidor() {
		return ipServidor;
	}

	public void setIpServidor(String ipServidor) {
		this.ipServidor = ipServidor;
	}

	public StringBuffer getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(StringBuffer observacoes) {
		this.observacoes = observacoes;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public LogEntity(){
	}

	public LogEntity(long idUsuario, String tabela, String campo, String ipOrigem, String ipServidor, long idCampo, String observacoes ) { // constructor de log automético
		setIdUsuario( new Long(idUsuario) );
	setTabela( tabela );
	setCampo( campo );
	setTipo( TIPO_SITE );	
	setIdCampo( new Long(idCampo) );
	setObservacoes( new StringBuffer(observacoes) );
	setIpOrigem( ipOrigem == null ? "" : ipOrigem );
	setIpServidor( ipServidor == null ? "" : ipServidor );

	try {
		save(null);
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("Erro no log: " + e.getMessage() );
	}
	}

	public LogEntity(long idUsuario, String tabela, String campo, String ipOrigem, long idCampo, String observacoes ) { // constructor de log automético
		setIdUsuario( new Long(idUsuario) );
		setTabela( tabela );
		setCampo( campo );
		setTipo( TIPO_SITE );	
		setIdCampo( new Long(idCampo) );
		setObservacoes( new StringBuffer(observacoes) );
		setIpOrigem( ipOrigem == null ? "" : ipOrigem );
		setIpServidor("");

		try {
			save(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro no log: " + e.getMessage() );
		}
	}

	public LogEntity(int tipo, long idUsuario, String tabela, String campo, String ipOrigem, long idCampo, String observacoes ) {
		setIdUsuario( new Long(idUsuario) );
		setTabela( tabela );
		setCampo( campo );
		setTipo( tipo );	
		setIdCampo( new Long(idCampo) );
		setObservacoes( new StringBuffer(observacoes) );
		setIpOrigem( ipOrigem == null ? "" : ipOrigem );
		setIpServidor( "" );

		try {
			save(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro no log: " + e.getMessage() );
		}
	}

	public LogEntity(int tipo, long idUsuario, String tabela, String campo, String ipOrigem, String ipServidor, long idCampo, String observacoes) { // constructor de log automético
		setIdUsuario( new Long(idUsuario) );
		setTabela( tabela );
		setCampo( campo );
		setTipo( tipo );	
		setIdCampo( new Long(idCampo) );
		setObservacoes( new StringBuffer(observacoes) );
		setIpOrigem( ipOrigem == null ? "" : ipOrigem );
		setIpServidor( ipServidor == null ? "" : ipServidor );

		try {
			save(null);
		} catch (Exception e) {
			//            e.printStackTrace();
			System.out.println("Erro no log: " + e.getMessage() );
		}
	}

	public LogEntity(int tipo, long idUsuario, String tabela, String campo, String ipOrigem, String ipServidor, long idCampo, String observacoes, Connection conn) { // constructor de log automético
		try {
			setIdUsuario( new Long(idUsuario) );
			setTabela( tabela );
			setCampo( campo );
			setTipo( tipo );	
			setIdCampo( new Long(idCampo) );
			setObservacoes( new StringBuffer(observacoes) );
			setIpOrigem( ipOrigem == null ? "" : ipOrigem );
			setIpServidor( ipServidor == null ? "" : ipServidor );

			boolean ok = true;
			/*
			if( observacoes.equals("Site acessado") ) {
				if( campo.equals("/allgenda/site/") || campo.equals("/allgenda/site/index.jsp") ) {
					if( checkLogHome( ipOrigem, campo, conn) ) {
						ok = false;
					}
				}
			}
			*/
			
			if( ok ) {
				save(null, conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro no log: " + e.getMessage() );
		}
	}

	private boolean checkLogHome(String ipOrigem, String campo, Connection conn) {
		try {
			String query = "dataHora >= DATE_SUB( NOW(), INTERVAL 5 MINUTE ) AND campo = '" + campo + "' AND ipOrigem = '" + ipOrigem + "' ";
			
			LogEntity tmp = new LogEntity();
			return tmp.getThis(query);
			
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	public LogEntity(int tipo, Date data, long idUsuario, String tabela, String campo, String ipOrigem, String ipServidor, long idCampo, String observacoes) { // constructor de log automético
		setIdUsuario( new Long(idUsuario) );
		setTabela( tabela );
		setCampo( campo );
		setIdCampo( new Long(idCampo) );
		setTipo( tipo );	
		setObservacoes( new StringBuffer(observacoes) );
		setIpOrigem( ipOrigem == null ? "" : ipOrigem );
		setIpServidor( ipServidor == null ? "" : ipServidor );

		try {
			save(data);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro no log: " + e.getMessage() );
		}
	}

	public void prepareUpdate(XMLData data, String id) throws ExceptionInfo, ExceptionWarning {
		Connection conn=null;
		try {
			this.setIdLog(new Long(id));
			conn = getConnection();
			this.getThis(conn);
			populateXML(data,"log");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInfo("Nãofoi possivel carregar este registro!");
		} finally {
			close(conn);
		}
	}

	public void prepareInsert(XMLData data) throws ExceptionInfo, ExceptionWarning {
		Connection conn=null;
		try {
			this.populateXML(data,"log");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInfo("Nãofoi possivel executar esta operaééo! Motivo:"+e.getMessage());
		} finally {
			close(conn);
		}
	}

	private void validateFields(LogEntity log) throws ExceptionInfo, ExceptionWarning {
		if (isEmpty(log.getIdUsuario()))   throw new ExceptionInfo("O preenchimento do campo USUéRIO é obrigatério!");
		if (isEmpty(log.getTabela()))   throw new ExceptionInfo("O preenchimento do campo TABELA é obrigatério!");
		if (isEmpty(log.getCampo()))    throw new ExceptionInfo("O preenchimento do campo CAMPO é obrigatério!");
		if (isEmpty(log.getIdCampo()))  throw new ExceptionInfo("O preenchimento do campo ID_CAMPO é obrigatério!");
	}

	public void validateInsert(LogEntity log) throws ExceptionInfo, ExceptionWarning {
		validateFields(log);
		dataHora = new Date();
		setIdLog(getNextId());
	}

	public void validateUpdate(LogEntity log) throws ExceptionInfo, ExceptionWarning {
		validateFields(log);
	}

	public void save( Date data, Connection conn ) throws ExceptionInfo, ExceptionWarning {
		boolean novo = false;
		try {
			if (getIdLog()==null) setIdLog(new Long(0));
			if (this.getIdLog().longValue()==0) novo = true;

			if (novo) {
				validateInsert(this);
			} else {
				validateUpdate(this);
			}

			if (novo) {
				setIdLog( getNextId() );

				if( data == null ) {
					dataHora = new Date();
				} else {
					dataHora = data;
				}

				this.insert(conn);
			} else {
				this.update(new String[]{"idUsuario","tabela","campo","idCampo","ipOrigem","ipServidor","observacoes"},conn);
			}
		} catch (ExceptionInfo e) {
			e.printStackTrace();
			System.out.println("Erro no log: " + e.getMessage() );
		}

		String detalhe = "";
		if( this.getObservacoes().toString().equals("Site acessado") ) detalhe = " - " + this.getCampo();
		System.out.println("ALLGENDA: " + DATE_TIME_FORMAT.format( new Date() ) + " - " + this.getIpOrigem() +  " - Log: " + getObservacoes().toString() + detalhe );    	
	}

	public void save( Date data ) throws ExceptionInfo, ExceptionWarning {
		Connection conn=null;
		try {
			conn=getConnection();
			save( data, conn );
		} catch (ExceptionInfo e) {
			e.printStackTrace();
		} finally {
			try {
				close(conn);
			} catch (Exception e0) {
			}
		}
	}

	public void delete(String id) throws ExceptionInfo, ExceptionWarning {
		this.setIdLog(new Long(id));
		this.delete();
	}

	public void populateRelated(XMLData data) throws ExceptionInfo, ExceptionWarning {
		Connection conn = null;
		try {
			conn = getConnection();
			populateList(new UsuarioEntity(),"usuario",true,"ORDER BY nome",data, conn);
			populateTabelas( data );
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}

	public void populateTabelas(XMLData data) throws Exception {
		try {
			data.openGroup("tabela");
			data.addParameterTag("record");
			data.addParameter("nome", "--- todas ---");
			data.closeGroup();
		} catch( Exception e ) {
			throw new Exception( e.getMessage() );
		}
	}

	public String tratarAgrupamento( String str ) {
		if( str.indexOf("(") <= 0 ) {
			return str;
		} else {
			if( str.indexOf("hour")  >= 0 ) return "horério";
			if( str.indexOf("week")  >= 0 ) return "dia da semana";
			if( str.indexOf("month") >= 0 ) return "dia do més";

			return "";
		}
	}

	public void getResumo( XMLData data, Connection conn, String data1, String data2, String hora1, String hora2, String idUsuario, String tabela, String idCampo, 
			String ordem, String acao, String agrupamento, String ipOrigem, String limitador, String tipoGrafico, String base, long startRow, long numElements) 
					throws ExceptionWarning, ExceptionInfo {

		Statement  dbSt = null;
		ResultSet  dbRs = null;

		Locale BRASIL = new Locale("pt","BR");
		NumberFormat nf = NumberFormat.getInstance(BRASIL);
		nf.setGroupingUsed(true);
		nf.setMaximumIntegerDigits(10);
		nf.setMinimumIntegerDigits(1);
		nf.setMinimumFractionDigits(0);
		nf.setMaximumFractionDigits(0);

		try {
			// criaééo do query
			StringBuffer query = new StringBuffer();

			if( base.equals("1") ) { // intranet
				query.append(
						"  FROM log, usuario " +
								" WHERE log.idUsuario = usuario.idUsuario "
						);
			} else { // site
				query.append(
						"  FROM log " +
								" WHERE 1=1 "
						);
			}

			if( !isEmpty(data1) ) {
				try {
					Date d = DATE_FORMAT.parse(data1);
					query.append(" AND log.dataHora >= " + getJava2DBDate(d) );
				} catch (Exception e) {
				}
			}
			if( !isEmpty(data2) ) {
				try {
					Date d = DATE_FORMAT.parse(data2);
					Calendar cal = new GregorianCalendar();
					cal.setTime(d);
					cal.add( Calendar.DATE, 1 );
					d = cal.getTime();
					query.append(" AND log.dataHora <= " + getJava2DBDate(d) );
				} catch (Exception e) {
				}
			}

			if( !isEmpty(hora1) ) {
				query.append(" AND hour( log.dataHora ) >= " + hora1 );
			}
			if( !isEmpty(hora2) ) {
				query.append(" AND hour( log.dataHora ) <= " + hora2 );
			}

			if( tabela == null ) tabela = "";
			if( !tabela.equals("--- todas ---") ) {
				query.append(" AND log.tabela = '" + tabela + "'" );
			}

			if( !idUsuario.equals("0") ) {
				query.append(" AND log.idUsuario = " + idUsuario );
			}

			if( !isEmpty(idCampo) ) {
				query.append(" AND ( log.idCampo = " + idCampo + " OR log.observacoes like '%" + idCampo + "%' ) ");
			}

			if( !ipOrigem.equals("0") ) {
				query.append(" AND log.ipOrigem LIKE '%" + ipOrigem + "%'");
			}

			if( !isEmpty(acao) ) {
				query.append(" AND log.observacoes like '%" + acao + "%' ");
			}

			// criaééo das conexées
			if( conn == null ) conn = getConnection();
			dbSt = conn.createStatement();

			dbRs = dbSt.executeQuery("SELECT COUNT(*) AS X " + query.toString() );
			if( dbRs.next() )
				setTotal( new Long( dbRs.getString("X") ) );

			StringBuffer graficoX = new StringBuffer("");
			StringBuffer graficoY = new StringBuffer("");

			// agrupamento
			if( !isEmpty(agrupamento) ) {
				query.append(" GROUP BY " + agrupamento );
				query.append(" ORDER BY " + agrupamento );

				dbRs = dbSt.executeQuery(
						"SELECT " + agrupamento + ", COUNT(*) AS X " +
								query.toString() );

				data.openGroup("lista");

				while( dbRs.next() ) {
					String y = dbRs.getString( agrupamento ).replaceAll(";", " ").replaceAll(","," ");

					if( agrupamento.indexOf("dayofweek") >= 0 ) {
						y = replaceWeek( y );
					}

					data.addParameterTag("record");
					data.addParameter("obs",  y );
					data.addParameter("usuario", nf.format( dbRs.getInt("X") ) );

					graficoX.append( y                   + ";" );
					graficoY.append( dbRs.getString("X") + ";" );
				}

				data.closeGroup();

				setTotal( new Long(1) );

				// listagem
			} else {
				query.append(" ORDER BY " + ordem);
				query.append(" LIMIT " +  (startRow - 1L) + "," + numElements );

				dbRs = dbSt.executeQuery(
						"SELECT log.dataHora,                 " +
								"       log.tabela,                   " +
								"       log.campo,                    " +
								"       log.idCampo,                  " +
								"       log.ipOrigem,                 " +
								"       log.observacoes,              " +
								"       usuario.nome                  " +
								query.toString()
						);

				SimpleDateFormat SDF1 = new SimpleDateFormat("yyyy-MM-dd");

				data.openGroup("lista");
				while( dbRs.next() ) {
					data.addParameterTag("record");

					try {
						Date d = dbRs.getDate("log.dataHora");
						Time t = dbRs.getTime("log.dataHora");
						data.addParameter("data", DATE_FORMAT.format( d ) + " " + TIME_FORMAT.format(t) );
					} catch (Exception eData) {
						Date d = SDF1.parse( dbRs.getString("log.dataHora").substring(0,10) );
						data.addParameter("data", DATE_FORMAT.format( d ) + " " + dbRs.getString("log.dataHora").substring(11,19) );
					}
					data.addParameter("db",   dbRs.getString("log.campo") + " " + dbRs.getString("log.idCampo") );
					data.addParameter("ip",   dbRs.getString("log.ipOrigem") );
					data.addParameter("usuario", dbRs.getString("usuario.nome") );
					data.addParameter("obs",  dbRs.getString("observacoes").replaceAll(";"," ").replaceAll(","," ") );
				}
				data.closeGroup();
			}

		} catch( Exception e2 ) {
			e2.printStackTrace();
			throw new ExceptionInfo( e2.getMessage() );
		} finally {
			close(conn);
			try {
				if(dbSt  != null) dbSt.close();
				if(dbRs  != null) dbRs.close();
			} catch (Exception e) {
			}
		}
	}

	public String replaceWeek( String str ) {
		if( str.equals("1") ) return "Dom";
		else if( str.equals("2") ) return "Seg";
		else if( str.equals("3") ) return "Ter";
		else if( str.equals("4") ) return "Qua";
		else if( str.equals("5") ) return "Qui";
		else if( str.equals("6") ) return "Sex";
		else if( str.equals("7") ) return "Sab";
		else return "";
	}

	public boolean insert() throws ExceptionWarning {
		Connection conn = null;
		try {
			conn = getConnection();
			boolean flag = insert(conn);
			return flag;
		} finally {
			close(conn);
		}
	}
	public boolean insert( Connection conn ) throws ExceptionWarning {
		boolean retorno = false;
		while( !retorno ) {
			try {
				retorno = super.insert(conn); 
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace( new PrintWriter(sw) );

				if( sw.toString().contains("Duplicate entry") ) {
					setIdLog( getNextId() );
					System.out.println("DUP - novo: " + getIdLog());
				} else {
					throw new ExceptionWarning( e.getMessage() );
				}
			}
		}

		return retorno;
	} 

	public Object[] getStructure() {
		return structure;
	}

	public Object[] getStructurePk() {
		return structurePk;
	}

	public String getTableName() {
		return "log";
	}

	public Connection retrieveConnection() throws ExceptionWarning {
		return getConnection();
	}

	public String get_IDFieldName() {
		return "idLog";
	}

	public String get_NomeFieldName() {
		return "idCampo";
	}

	public String get_SelecioneName() {
		return LABEL_SELECIONE;
	}
}