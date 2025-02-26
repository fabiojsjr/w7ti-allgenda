package com.tetrasoft.app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.technique.engine.util.ExceptionInjection;
import com.technique.engine.util.ExceptionWarning;
import com.tetrasoft.data.cliente.ConfigEntity;
import com.tetrasoft.data.cliente.TaskEntity;
import com.tetrasoft.util.SendMailSparkpost;



public class AllgendaController extends Thread {
//	private static final String EMAIL_ADMIN = "fabiojsjr@gmail.com";

	static {
		System.out.println("Welcome to AllgendaController 1.0");
		System.out.println("======================================================");
		System.out.println("total memory:" + Runtime.getRuntime().totalMemory() + " bytes");
		System.out.println("developed by: Fabio J. S. Jr - sistemas@w7ti.com");
		System.out.println("======================================================");
	}

	
	public static int      PULSE         	= 1*60*1000; // 1 minuto
	public static String   HORA_CERTA    	= "09:03";
	public static String[] EMAIL_ADMIN   	= new String[]{"sistemas@w7ti.com"};

	
	public static void main(String[] args) {
		AllgendaController obj = new AllgendaController();

		if( args.length > 0 ) {
			try {
				Thread.sleep(3*60*60*1000); // 3 horas
			} catch (Exception e) {
			}
			System.exit(-1);
		}

		obj.start();
	}

	SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
	SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMdd");
	static SimpleDateFormat sdf4 = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	public AllgendaController() {
	
	}

	
	public void run() {


		boolean goOn = true ;
		boolean debug = true ;
		
		TaskEntity task = new TaskEntity() ;

		while (goOn) {
			try {
				Date   dataAtual = new Date();
				String horaAtual = sdf2.format( dataAtual );
				System.out.println( dataAtual );

				long tempo = 0;

				if( debug || horaAtual.equals( HORA_CERTA ) ) { // uma vez por dia

				}
				
	            ArrayList a = task.getArraySelect("select * from task where status = 0 and lembreteEnviado = 0");

	            for( int i = 0; i < a.size(); i++ ) {
	            			task = (TaskEntity)a.get(i);
	            			
	            			System.err.println("email - " + task.getEmailSolicitante());
	            			System.err.println("data  - " + sdf4.format(task.getDataHoraPrazo()));
	            			System.err.println("descr - " + task.getDescricao());
	            			System.err.println("depto - " + task.getDepartamento());
	            			
	            			task.setDataHoraLembrete( new Date());
	            			task.setLembreteEnviado( task.getLembreteEnviado() + 1);
	            			task.update();
	            			
	            			SendMailSparkpost.send("Allgenda - Confirmar Agendamento", ConfigEntity.CONFIG.get("email"), task.getEmailSolicitante(), "text/html", new StringBuffer(getConteudo(task.getIdTask())) );
	            }

				sleep(PULSE);

//				task.getThis("status = 0 and lembreteEnviado = 0") ;
				
				
//				SendMailSparkpost.send("Allgenda - Confirmar Agendamento", ConfigEntity.CONFIG.get("email"), emailSolicitante, "text/html", new StringBuffer(getConteudo()) );
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}

	}
	
	public static String getConteudo(long idTask) throws ExceptionWarning, ExceptionInjection {
		String conteudo = "";
		
		TaskEntity task = new TaskEntity() ;
		task.getThis("idTask = " + idTask);
		
		conteudo += "<tr>";
		conteudo += "	<td colspan='3' align='left'>" +
				"			<b>Olá, </b>" +
				"			<br/><br/>" +
				"			Seja muito bem vindo à  " + ConfigEntity.CONFIG.get("nome") + "." +
				"			<br/><br/>" +
				"			Precisamos da sua confirmação de agendamento na sala de reunião." +
				"			<br/><br/>" +
				"			<br/>Data: " + sdf4.format(task.getDataHoraPrazo()) +
				"			<br/>Sala: " + task.getIdSala() +
				"			<br/>Duração: " + (int) ( new Double(task.getDuracaoReuniao()).doubleValue() * 60 ) + " minutos" +
				"			<br/>Participantes: " + task.getParticipantes() +
				"			<br/>Departamento: " + task.getDepartamento() +
				"			<br/><br/>" +
				"			Por favor, clique no link abaixo para confirmar:" +
				"			<br/>" +
				"			http://atallsolutions.com.br/allgenda?confirma=LAkjsdaldjalkdjaldkjaldkjaldj" +
				"			<br/><br/>" +
				"			Atenciosamente," +
				"			<br/><br/><br/>" +
				"			Equipe  " + ConfigEntity.CONFIG.get("nome") +		
				"		</td>";
		conteudo += "</tr>";

		
		return conteudo ;
	}

	
}	