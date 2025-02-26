package com.tetrasoft.web.finance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import com.technique.engine.app.SystemParameter;
import com.technique.engine.util.ExceptionInjection;
import com.technique.engine.util.ExceptionWarning;
import com.tetrasoft.app.sender.SenderInterface;
import com.tetrasoft.data.cliente.TaskEntity;


public class RelatorioMes {
	
	private String[] titulos = {"","Descri��o","Departamento","Participantes"};	
	
	private List<String> getRangeDatas(String dataInicial, String dataFinal) throws ExceptionWarning {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		List<String> datas = new ArrayList<>();
		String[] datas1 = dataInicial.split("/");
		String[] datas2 = dataFinal.split("/");		
		
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();		
		
		calendar1.set(Integer.parseInt(datas1[2]),(Integer.parseInt(datas1[1])-1),Integer.parseInt(datas1[0]));
		calendar2.set(Integer.parseInt(datas2[2]),Integer.parseInt(datas2[1]),Integer.parseInt(datas2[0]));	
		
		if(calendar1.after(calendar2))
			throw new ExceptionWarning("A data inicial deve ser menor que a Data final");
		
		while( calendar1.getTime().before(calendar2.getTime())) {			
			datas.add(dateFormat.format(calendar1.getTime()).toString());
			calendar1.add(Calendar.MONTH, 1);
		}	
		
		return datas;
	}	
	
	private void criarTitulos(HSSFSheet sheet) {
		
		for(String titulo : titulos) {
			
			HSSFRow row = sheet.createRow(sheet.getLastRowNum()+1);				
			HSSFCell celula = row.createCell(row.getLastCellNum() + 1);			
			celula.setCellValue(titulo);		
			
		}
	}
	
	private void criarRelatorio(HSSFSheet sheet, List<String> dados) throws ExceptionWarning {		
		
		int numeroLinha = 1;
		if(sheet.getLastRowNum() == 0) {	
			
			for(String valor : dados) {					
				HSSFRow row = sheet.createRow(sheet.getLastRowNum()+1);				
				HSSFCell celula = row.createCell(row.getLastCellNum() + 1);			
				celula.setCellValue(valor);				
			}
		
		}else {
			
			for(String valor : dados) {					
				HSSFRow row = sheet.getRow(numeroLinha);				
				HSSFCell celula = row.createCell(row.getLastCellNum());			
				celula.setCellValue(valor);	
				numeroLinha++;
			}		
		}	
	}
	
	public String gerar(String dataInicial, String dataFinal) throws ExceptionWarning, ExceptionInjection {			
		
		FileOutputStream fos = null;
		String id = Long.toString(System.currentTimeMillis());	
		String path = SystemParameter.get(SenderInterface.SID, "systemProperties", "rootPath") + "allgenda" + File.separator + "tmp" + File.separator + "excel" +  File.separator + id + ".xls";
		Workbook wb = new HSSFWorkbook();
		HSSFSheet sheet = (HSSFSheet) wb.createSheet("Relat�rio");
		List<String> dados = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
		List<TaskEntity> listTask = null;
		
		try {
			
			TaskEntity task = new TaskEntity();
			List<String> datas = this.getRangeDatas(dataInicial, dataFinal);		
			String sql = "";
			
			criarTitulos(sheet);
			
			for(String dataAtual : datas) {
				
					sql = 
						"dataHoraPrazo >=" + "'" +  dataAtual + "-01'" + 
						" AND dataHoraPrazo <=" + "'" + dataAtual + "-31'";					
					
					listTask = task.get(sql);
					
					if(listTask.size() > 0 && !sql.equals("")) {				
						
						for(TaskEntity entity : listTask) {		
							dados.add(dateFormat.format(entity.getDataHoraPrazo()));
							dados.add(entity.getDescricao());
							dados.add(entity.getDepartamento());
							dados.add(entity.getParticipantes().toString());						
							criarRelatorio(sheet,dados);	
							dados.clear();
							
						}				
					}	
			}	
			
			//if(listTask == null || listTask.size() < 1)
				//throw new ExceptionWarning("Nenhuma reuni�o foi agendada");
			
			fos = new FileOutputStream(new File(path));
			
			// da um autosize em toda as colunas do relatorio
			for(int i=0;i < sheet.getLastRowNum(); i++ ) {	
				
				if(sheet.getRow(i) != null) {
					
					for(int x=0; x < sheet.getRow(i).getLastCellNum(); x++ ) {									
						sheet.autoSizeColumn(x);
					}						
				}
							
			}		
						
			wb.write(fos);
			
		}catch (FileNotFoundException e) {			
			
			throw new RuntimeException(e.getMessage());
			
		}catch (IOException ex) {			
			
			throw new RuntimeException(ex.getMessage());
			
		}finally {
			
			try {
				
				if(fos != null && wb != null) {
					
					fos.close();
					wb.close();
					
				}
				
				
			} catch (IOException e) {				
				
				System.out.println(e.getMessage());
				throw new RuntimeException(e.getMessage());
				
			}
		}	
		
		return "tmp/excel/" + id + ".xls";
	}

}
