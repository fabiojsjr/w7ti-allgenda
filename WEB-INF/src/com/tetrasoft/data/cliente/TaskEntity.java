package com.tetrasoft.data.cliente;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.technique.engine.data.DataTypes;
import com.technique.engine.util.ExceptionInjection;
import com.technique.engine.util.ExceptionWarning;
import com.tetrasoft.common.data.BasePersistentEntity;
import com.tetrasoft.common.data.Listable;
import com.tetrasoft.data.finance.SalaReuniaoEntity;

public class TaskEntity extends BasePersistentEntity implements Listable{
	
	
	private static final long serialVersionUID = 1L;
	public static int ID_FUNCAO = 909;
	public static final int STATUS_ABERTO 		= 0;
	public static final int STATUS_FINALIZADO 	= 1;
	
	public static final int TIPO_LEMBRETE		= 1;
	public static final int TIPO_EMAIL		 	= 2;

	private static Object[] structurePk = new Object[]{
		"IdTask","idTask", DataTypes.JAVA_LONG
	};

	private static Object[] structure = new Object[]{
		"IdEntity","idEntity", DataTypes.JAVA_LONG,
		"IdSala","idSala", DataTypes.JAVA_LONG,
		"Participantes","participantes", DataTypes.JAVA_INTEGER,
		"Departamento","departamento", DataTypes.JAVA_STRING,
		"Emails","emails", DataTypes.JAVA_STRING,
		"NomesParticipantes","nomesParticipantes", DataTypes.JAVA_STRING,
		"EmailSolicitante","emailSolicitante", DataTypes.JAVA_STRING,
		"Solicitante","solicitante", DataTypes.JAVA_STRING,
		"DuracaoReuniao","duracaoReuniao", DataTypes.JAVA_STRING,		
		"Entity","entity", DataTypes.JAVA_STRING,  
		"IdUsuario","idUsuario", DataTypes.JAVA_LONG,
		"IdGoogle","idGoogle", DataTypes.JAVA_STRING,
		"Tipo","tipo", DataTypes.JAVA_INTEGER , 
		"DataHoraPrazo","dataHoraPrazo", DataTypes.JAVA_DATETIME,
		"DataHoraPrazoFinal","dataHoraPrazoFinal", DataTypes.JAVA_DATETIME,		
		"DataHoraExecucao","dataHoraExecucao", DataTypes.JAVA_DATETIME,  
		"DataHoraLembrete","dataHoraLembrete", DataTypes.JAVA_DATETIME,  
		"LembreteEnviado","lembreteEnviado", DataTypes.JAVA_INTEGER , 
		"Titulo","titulo", DataTypes.JAVA_STRING,  
		"Descricao","descricao", DataTypes.JAVA_STRING,  
		"Status","status", DataTypes.JAVA_INTEGER  
	};

	public Object[] getStructure(){
		return structure;
	}

	public Object[] getStructurePk(){
		return structurePk;
	}

	public String getTableName(){
		return "task";
	}

	public String get_IDFieldName(){
		return "idTask";
	}

	public String get_NomeFieldName(){
		return "descricao";
	}

	public String get_SelecioneName(){
		return LABEL_SELECIONE;
	}
	
	public static int getID_FUNCAO() {
		return ID_FUNCAO;
	}
	
	private String emailSolicitante;
	private String solicitante;
	private String duracaoReuniao;
	private String emails;
	private String nomesParticipantes;
	private Long   idTask = new Long(0);
	private Long   idEntity = new Long(0);
	private String entity = "";
	private Long   idUsuario = new Long(0);
	private String   idGoogle;
	private Integer tipo = TIPO_LEMBRETE;
	private Date dataHoraPrazo = new Date();
	private Date dataHoraPrazoFinal = new Date();
	private Date dataHoraExecucao = new Date();
	private Date dataHoraLembrete = new Date();
	private Integer lembreteEnviado = 0;
	private String titulo = "";
	private String descricao = "";
	private Long idSala;
	private String departamento;
	private Integer participantes;	
	
	
	public Long getIdSala() {
		return idSala;
	}

	public void setIdSala(Long idSala) {
		this.idSala = idSala;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}	

	public Integer getParticipantes() {
		return participantes;
	}

	public void setParticipantes(Integer participantes) {
		this.participantes = participantes;
	}

	public Date getDataHoraPrazoFinal() {
		return dataHoraPrazoFinal;
	}

	public void setDataHoraPrazoFinal(Date dataHoraPrazoFinal) {
		this.dataHoraPrazoFinal = dataHoraPrazoFinal;
	}

	public String getDuracaoReuniao() {
		return duracaoReuniao;
	}

	public void setDuracaoReuniao(String duracaoReuniao) {
		this.duracaoReuniao = duracaoReuniao;
	}	

	public String getEmailSolicitante() {
		return emailSolicitante;
	}

	public void setEmailSolicitante(String emailSolicitante) {
		this.emailSolicitante = emailSolicitante;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	private Integer status = 0;

	public Long getIdTask() {
		return idTask;
	}

	public void setIdTask(Long idTask) {
		this.idTask = idTask;
	}

	public Long getIdEntity() {
		return idEntity;
	}
	

	public String getNomesParticipantes() {
		return nomesParticipantes;
	}

	public void setNomesParticipantes(String nomesParticipantes) {
		this.nomesParticipantes = nomesParticipantes;
	}

	public void setIdEntity(Long idEntity) {
		this.idEntity = idEntity;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}	

	public String getIdGoogle() {
		return idGoogle;
	}

	public void setIdGoogle(String idGoogle) {
		this.idGoogle = idGoogle;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}	

	public Date getDataHoraPrazo() {
		return dataHoraPrazo;
	}

	public void setDataHoraPrazo(Date dataHoraPrazo) {
		this.dataHoraPrazo = dataHoraPrazo;
	}

	public Date getDataHoraExecucao() {
		return dataHoraExecucao;
	}

	public void setDataHoraExecucao(Date dataHoraExecucao) {
		this.dataHoraExecucao = dataHoraExecucao;
	}

	public Date getDataHoraLembrete() {
		return dataHoraLembrete;
	}

	public void setDataHoraLembrete(Date dataHoraLembrete) {
		this.dataHoraLembrete = dataHoraLembrete;
	}
	
	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getLembreteEnviado() {
		return lembreteEnviado;
	}

	public void setLembreteEnviado(Integer lembreteEnviado) {
		this.lembreteEnviado = lembreteEnviado;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String separarNomes(String nomesParticipantes) {
		
		String nomes = "";
		
		if(nomesParticipantes != null && !nomesParticipantes.equals("")) {
			
			if(nomesParticipantes.contains(";")) {
			
				String[] nomesList = nomesParticipantes.split(";");
				
				for (String n : nomesList ) 				
					nomes +=  "<p class='reuniaoDescricao2'>" + n + "</p>";				
			}else
				nomes = "<p class='reuniaoDescricao2'>" + nomesParticipantes + "</p>";
		}
		
		return nomes;
	}
	
	public void update( String id, Date data , Connection conn) {
		try {
			this.setIdTask( new Long(id) );
			if( this.getThis(conn) ) {
				this.setDataHoraPrazo( data );
				this.update(conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void save( long idUsuario, String evento, Date data, Connection conn ) { // inclusão direta, clicando pelo calendário
		try {
			
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(data);
			cal.set( GregorianCalendar.HOUR_OF_DAY, 8 );
			data = cal.getTime();
			
			this.setIdTask( getNextId() );
			this.setIdUsuario(idUsuario);
			this.setEntity(null);
			this.setDuracaoReuniao(this.duracaoReuniao);
			this.setEmails(this.emails);
			this.setNomesParticipantes(this.nomesParticipantes);
			this.setEmailSolicitante(this.emailSolicitante);
			this.setSolicitante(this.solicitante);
			this.setIdEntity(null);
			this.setDataHoraExecucao(null);
			this.setDataHoraLembrete(null);
			this.setLembreteEnviado(null);
			this.setTipo(TIPO_LEMBRETE);
			this.setTitulo("");
			this.setIdSala(this.idSala);
			this.setDepartamento(this.departamento);
			this.setParticipantes(this.participantes);			
			this.setDescricao(evento);
			this.setDataHoraPrazo( data );
			this.setStatus( STATUS_ABERTO );
			this.insert(conn);
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	
	
	public void validarHorarioSala(String data, String hora, String idTask, String idSala, String duracao) throws ExceptionWarning, ExceptionInjection {		
		
		if(idTask.indexOf("_") != -1)
			idTask = idTask.replace("_","");		
		
		Integer minutos = 0;
		Integer horas = 0;	
		boolean podeCadastrar = false;		
					
			
		String dataSplit[] = data.split("/");
		String horaSplit[] = hora.split(":");
		Calendar calendarHora = Calendar.getInstance();			
		TaskEntity taskEntity = new TaskEntity();
		List<TaskEntity> listTask = taskEntity.get(" idSala=" + idSala + " AND idTask != " + idTask);
		
		//seta a data a ser cadastrada
		calendarHora.set(Integer.parseInt(dataSplit[2]), Integer.parseInt(dataSplit[1]) - 1, Integer.parseInt(dataSplit[0]), Integer.parseInt(horaSplit[0]), Integer.parseInt(horaSplit[1]),0);	
					
		Calendar calendarValidacao2 = Calendar.getInstance();						
		
		if(calendarHora.getTime().before(calendarValidacao2.getTime()))				
			throw new ExceptionWarning("A reuni�o n�o pode ser marcada numa data anterior");						
		
		Calendar calendarioHoraAux = Calendar.getInstance();
		calendarioHoraAux.setTime(calendarHora.getTime());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		
		//horas com minutos ou somente minutos
		if(duracao.length() > 1){
			horas = Integer.parseInt(duracao.substring(0,1));
			minutos =  Integer.parseInt(duracao.substring(duracao.indexOf(".") + 1, duracao.length()));					
		}else //somente hora					
			horas = Integer.parseInt(duracao);
										
		
		if(horas != 0) // acrescenta a hora
			calendarHora.add(Calendar.MINUTE,(horas * 60));
		if(minutos != 0) // acrescenta 30 minutos				
			calendarHora.add(Calendar.MINUTE, 30);			
		
		for(TaskEntity obj : listTask){							
			
			if((calendarHora.getTime().before(obj.getDataHoraPrazo()) || (dateFormat.format(obj.getDataHoraPrazo()).equals(dateFormat.format(calendarHora.getTime())))))					
				podeCadastrar = true;					
			else if((calendarioHoraAux.getTime().after(obj.getDataHoraPrazoFinal()) || (dateFormat.format(obj.getDataHoraPrazoFinal()).equals(dateFormat.format(calendarioHoraAux.getTime())))))					
				podeCadastrar = true;					
			else 	
				podeCadastrar = false;
			
			if(!podeCadastrar)
				throw new ExceptionWarning("Este hor�rio j� se encontra cadastrado");				
		}				
		
	}
	
	public void validarParticipantes(Integer participantes, Long idSala) throws ExceptionInjection, ExceptionWarning {	
		
		
		try{
			
			if(idSala != null){
				
				SalaReuniaoEntity salaReuniaoAtual = new SalaReuniaoEntity();
				salaReuniaoAtual.getThis(" idSalaReuniao=" + idSala);
				
				if(participantes > salaReuniaoAtual.getCapacidade())					
					throw new ExceptionWarning("A quantidade de participantes excede a capacidade da sala");				
				
			}
			
		}catch(ExceptionWarning e){
			
			throw new ExceptionWarning(e.getMessage());
			
		}		
		
	}
	
	public void validar(TaskEntity task) throws ExceptionWarning {
		
		if(task.getDuracaoReuniao() == null || task.getDuracaoReuniao().equals(""))
			throw new ExceptionWarning("Campo Dura��o � obrigat�rio");
		
		if(task.getDataHoraPrazo() == null) 
			throw new ExceptionWarning("Campo Data � obrigat�rio");
		
		if(task.getDataHoraPrazo() == null) 
			throw new ExceptionWarning("Campo Hora � obrigat�rio");
		
		if(task.getSolicitante() == null || task.getSolicitante().equals("") ) 
			throw new ExceptionWarning("Campo Solicitante � obrigat�rio");
		
		if(task.getIdSala() == null) 
			throw new ExceptionWarning("Campo Salas � obrigat�rio");		
		
		if(task.getParticipantes() == null || task.getParticipantes() == 0 ) 
			throw new ExceptionWarning("Campo Participantes � obrigat�rio");			
		
	}
	
	public void salvar(TaskEntity task) throws ExceptionWarning, ExceptionInjection {
		
		if(task.getIdTask() == null || task.getIdTask() == 0L) {
			
			TaskEntity taskGoogle = new TaskEntity();
			taskGoogle.getThis(" idGoogle = '" + task.getIdGoogle() + "'");
			
			
			if(taskGoogle.getIdTask() == null || taskGoogle.getIdTask() == 0L) {
				task.setIdTask(getNextId());
				task.insert();
			}
				
		}
	}
	
	public void done( String id, String status, Connection conn ) {
		try {
			this.setIdTask( new Long(id) );
			this.getThis(conn);
			
			if( Integer.parseInt(status) == STATUS_FINALIZADO ) {
				this.setDataHoraExecucao( new Date() );
				this.setStatus( STATUS_FINALIZADO );
			} else {
				this.setDataHoraExecucao(null);
				this.setStatus( STATUS_ABERTO );
			}
			
			this.update(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String[] getListEmail(String emails) {
		
		String array[] = emails.split(";");
		
		return array;
	}

	public Connection retrieveConnection() throws ExceptionWarning {
		return getConnection();
	}
}