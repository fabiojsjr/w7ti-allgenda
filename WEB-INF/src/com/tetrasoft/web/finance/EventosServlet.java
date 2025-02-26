package com.tetrasoft.web.finance;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.technique.engine.util.ExceptionInjection;
import com.technique.engine.util.ExceptionWarning;
import com.tetrasoft.data.cliente.TaskEntity;
import com.tetrasoft.data.finance.EventoEntity;

@WebServlet("/eventosServlet")
public class EventosServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	//busca as reunioes do calendario
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
			req.setCharacterEncoding("UTF8");
			resp.setCharacterEncoding("UTF-8");			
			String id = req.getParameter("id");		
			List<EventoEntity> listEventos = new ArrayList<EventoEntity>();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			TaskEntity entity = new TaskEntity();
			List<TaskEntity> listTask = null;
			String json = "";
			
			try {
				
				if(id != null && !id.equals("")) {
					listTask = entity.get(" idSala=" + id);
				}else {
					listTask = entity.get(" 1=1");					
				}								
				
				if(listTask.size() > 0) {					
					
					for(TaskEntity ob : listTask) {
						EventoEntity evento = new EventoEntity();
						evento.setName(ob.getIdTask().toString() + "|" + ob.getTipo().toString());
						evento.setTitle(ob.getSolicitante());
						evento.setStart(dateFormat.format(ob.getDataHoraPrazo()));
						evento.setEnd(dateFormat.format(ob.getDataHoraPrazoFinal()));
						evento.setAllDay(false);
						evento.setColor("red");
						evento.setUrl("calendarioEdit.jsp?id=" + ob.getIdTask() + "&tipo=" + ob.getTipo() + "&idCalendario=0");
						listEventos.add(evento);					
					}
					
					Gson gson = new Gson();
					
					json = gson.toJson(listEventos);					
					
					resp.getWriter().write(json);
				}				
				
			} catch (ExceptionWarning e) {
				
				e.printStackTrace();
				
			} catch (ExceptionInjection e) {
				
				e.printStackTrace();
			}	
	}	
	
	//busca reunioes reunioesList
	/*@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF8");
		response.setCharacterEncoding("UTF-8");			
		String idSalaReuniao = request.getParameter("idSalaReuniao");	
		String json = "";
		List<TaskEntity> reuniaoAtual = null;
		List<TaskEntity> proximasReunioes = null;
		
		try {	
			
			List<TaskEntity> listReunioes = new ArrayList<TaskEntity>();
			SalaReuniaoEntity salaReuniao = new SalaReuniaoEntity();
			TaskEntity task = new TaskEntity();
			if(idSalaReuniao != null && !idSalaReuniao.equals("")){					
				salaReuniao.getThis(" idSalaReuniao =" + idSalaReuniao);
				
				reuniaoAtual = task.get(" idSala=" + idSalaReuniao + " AND NOW() BETWEEN dataHoraPrazo AND dataHoraPrazoFinal ORDER BY dataHoraPrazo ASC LIMIT 1");
				
				if(reuniaoAtual != null && reuniaoAtual.size() > 0)
					proximasReunioes = task.get(" idSala=" + idSalaReuniao + " AND dataHoraPrazo > '" +  reuniaoAtual.get(0).getDataHoraPrazo() + "' ORDER BY dataHoraPrazo ASC LIMIT 6");
				else
					proximasReunioes = task.get(" idSala=" + idSalaReuniao + " AND dataHoraPrazo >= NOW() ORDER BY dataHoraPrazo ASC LIMIT 6");
					
				if(reuniaoAtual != null && reuniaoAtual.size() > 0)
					listReunioes.add(reuniaoAtual.get(0));
				else
					listReunioes.add(null);
				
				if(proximasReunioes.size() > 0){
					
					for(TaskEntity ob : proximasReunioes){
						listReunioes.add(ob);
					}	
					
				}		
		}
			
			Gson gson = new Gson();
			
			json = gson.toJson(listReunioes);	
			
			response.getWriter().write(json);
		
		} catch (ExceptionWarning e) {
			
			e.printStackTrace();
			
		} catch (ExceptionInjection e) {
		
			e.printStackTrace();
		}	
		
	}*/
	
	//Salvar eventos para o google calendar
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF8");
		response.setCharacterEncoding("UTF-8");			
		String idGoogle = request.getParameter("idGoogle");	
		String dataInicialRequest = request.getParameter("dataInicial");
		String dataFinalRequest = request.getParameter("dataFinal");
		String sala = request.getParameter("sala");
		String descricao = request.getParameter("descricao");
		String solicitante = request.getParameter("solicitante");		
		TaskEntity task = new TaskEntity();
		
		if(idGoogle != null && !idGoogle.equals("")) {
			
			//separa a hora da data(inicial)
			String[] horarioInicialSplit = dataInicialRequest.split("T");		
			String dataInicial = horarioInicialSplit[0];			
			String[] dataInicialSplit = dataInicial.split("-");		
			
			String horaInicial = horarioInicialSplit[1];
			String[] horaInicialSplit = horaInicial.split(":");
			
			//separa a hora da data(inicial)
			String[] horarioFinalSplit = dataFinalRequest.split("T");		
			String dataFinal = horarioFinalSplit[0];			
			String[] dataFinalSplit = dataFinal.split("-");
			
			String horaFinal = horarioFinalSplit[1];
			String[] horaFinalSplit = horaFinal.split(":");			
			
			Calendar calendar1 = Calendar.getInstance();
			Calendar calendar2 = Calendar.getInstance();
			
			calendar1.set(Integer.parseInt(dataInicialSplit[0]),Integer.parseInt(dataInicialSplit[1])-1,Integer.parseInt(dataInicialSplit[2]), Integer.parseInt(horaInicialSplit[0]),Integer.parseInt(horaInicialSplit[1]),Integer.parseInt(horaInicialSplit[1]));
			calendar2.set(Integer.parseInt(dataFinalSplit[0]),Integer.parseInt(dataFinalSplit[1])-1,Integer.parseInt(dataFinalSplit[2]), Integer.parseInt(horaFinalSplit[0]),Integer.parseInt(horaFinalSplit[1]),Integer.parseInt(horaFinalSplit[1]));
						
			task.setIdGoogle(idGoogle);
			task.setIdSala(Long.parseLong(sala));
			task.setDataHoraPrazo(calendar1.getTime());
			task.setDataHoraPrazoFinal(calendar2.getTime());			
			task.setDescricao(descricao);	
			task.setSolicitante(solicitante);
			task.setDescricao(descricao);
			
			try {
				
				task.salvar(task);				
				
			} catch (ExceptionWarning e) {
				
				System.out.println(e.getMessage());
				response.getWriter().write(e.getMessage());
				throw new RuntimeException(e.getMessage());
				
			}catch (ExceptionInjection e) {
				
				System.out.println(e.getMessage());
				response.getWriter().write(e.getMessage());
				throw new RuntimeException(e.getMessage());
			}
			
		}	
		
	}
	
}
