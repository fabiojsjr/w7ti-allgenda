/*
 * Excel.java
 *
 * Created on 24 de Agosto de 2005, 13:59
 */

package com.tetrasoft.common;

// JAVA PACKAGES
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

import com.tetrasoft.app.sender.SenderInterface;
import com.tetrasoft.util.File;
import com.technique.engine.app.SystemParameter;
import com.technique.engine.util.ExceptionWarning;
import com.technique.engine.util.TechCommonKeys;

/**
 * <PRE>
 *
 * </PRE>
 *
 * @author Renato V. Filipov
 * @version 1.0.0a
 * @see <P>
 * <B>Revision History:</B>
 * <PRE>
 * ==================================================================================================================
 * date       By                      Version    Comments
 * ---------- ----------------------- --------   --------------------------------------------------------------------
 * 2003       Renato V. Filipov        1.0.0a     initial release
 *            Renato V. Filipov        1.0.0b     walkthrough
 * ==================================================================================================================
 * </PRE><P>
 * <PRE>
 * <B>©FUTUWARE MULTIMEDIA - Copyright 2003.  <I>All Rights Reserved.</I></B>
 * Este software Tetrasoft.
 * A sua utilização é limitada aos termos de uso que acompanha este código.
 * Desenvolvido por FUTUWARE MULTIMEDIA - www.futuware.com.br
 *
 * This software is the proprietary information of Tetrasoft.
 * Use is subject to license terms.
 * Developed by FUTUWARE MULTIMEDIA - www.futuware.com.br
 * </PRE>
 */

public class Excel {
	private SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private SimpleDateFormat DATE_FORMAT 	  = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat DATE_FORMAT_MES_ANO  = new SimpleDateFormat("MM/yyyy");
    
    public static int TRATAR_DATE_TIME_FORMAT 	= 1;
    public static int TRATAR_LIMPA_TAGS  		= 2;
    public static int TRATAR_DATE_FORMAT 		= 3;
    public static int TRATAR_DATE_FORMAT_MES_ANO = 4;

    FileOutputStream out;
    File file;

    HSSFWorkbook   wb;
    HSSFSheet      s;
    HSSFRow        r;
    HSSFCell       c;
    HSSFDataFormat df;

    HSSFCellStyle cs;
    HSSFCellStyle cs2;
    HSSFCellStyle cs3;
    HSSFCellStyle cs4;

    HSSFFont f;
    HSSFFont f2;
    HSSFFont f3;

    // =========================================================================
    // =========================================================================
    // =========================================================================
    public HSSFSheet getSheet() {
        return s;
    }

    /** Style para o título: fonte 12, azul, negrito */
    public HSSFCellStyle getCellStyle() {
        return cs;
    }
    /** Style para o texto: fonte 10, preto, decimal(2) */
    public HSSFCellStyle getCellStyle2() {
        return cs2;
    }
    /** Style para o texto: fonte 10, preto, texto */
    public HSSFCellStyle getCellStyle3() {
        return cs3;
    }
    /** Style para o texto: fonte 10, preto, texto, negrito */
    public HSSFCellStyle getCellStyle4() {
        return cs4;
    }

    public HSSFWorkbook getWorkbook() {
        return wb;
    }

    // =========================================================================
    // =========================================================================
    // =========================================================================
    public void createFile( String nomeArquivo, String nomePlanilha, String extra ) {
        try {
        	file = new File( nomeArquivo );
            out = new FileOutputStream( file ); // create a new file

            wb = new HSSFWorkbook(); // create a new workbook
            s  = wb.createSheet();   // create a new sheet

            r = null; // declare a row object reference
            c = null; // declare a cell object reference

            df = wb.createDataFormat();

            // create 3 cell styles
            cs  = wb.createCellStyle();
            cs2 = wb.createCellStyle();
            cs3 = wb.createCellStyle();
            cs4 = wb.createCellStyle();

            // create 2 fonts objects
            f  = wb.createFont();
            f.setFontHeightInPoints((short) 12);       // set font 1 to 12 point type
            f.setColor( (short)0xc );                  // make it blue
            f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // make it bold (arial is the default font)

            f2 = wb.createFont();
            f2.setFontHeightInPoints((short) 10);       // set font 2 to 10 point type

            cs.setFont(f); // set the font
            cs.setDataFormat(HSSFDataFormat.getBuiltinFormat("text")); // set the cell format to text see HSSFDataFormat for a full list
            cs.setAlignment( HSSFCellStyle.ALIGN_CENTER ); // set the alignment

            cs2.setFont(f2); // set the font
            cs2.setDataFormat(df.getFormat("#,##0.00")); // set the cell format

            cs3.setFont(f2); // set the font
            
            f3 = wb.createFont();
            f2.setFontHeightInPoints((short) 10);       // set font 2 to 10 point type
            f3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 	// make it bold
            cs4.setFont(f3); // set the font

            wb.setSheetName(0, nomePlanilha); // set the sheet name

            r = s.createRow(0);
            c = r.createCell((short) 0 );
            c.setCellStyle(cs);
            c.setCellValue( nomePlanilha );

            c = r.createCell((short) 1 );
            c.setCellStyle(cs3);
            c.setCellValue( DATE_TIME_FORMAT.format( new java.util.Date() ) + " - " + extra );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeFile() {
        try {
            // write the workbook to the output stream
            // close our file
            wb.write(out);
            out.close();
            if (file!=null) file.rpl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================================================================
    // =========================================================================
    // =========================================================================
    public static void main( String[] args ) {
        Excel obj = new Excel();
        obj.createFile("teste.xls", "Título da Planilha", "");

        HSSFSheet s = obj.getSheet();
//        s.addMergedRegion( new Region( 0, (short)0, 0, (short)9 ) );

        HSSFRow  r = null;
        HSSFCell c = null;

        for( int rownum = 3; rownum < 30; rownum++) {
            r = s.createRow(rownum); // create a row

            // r.setRowNum(( short ) rownum);
            // create 10 cells (0-9) (the += 2 becomes apparent later
            for( int cellnum = 0; cellnum < 10; cellnum += 2) {
                // create a numeric cell
                s.setColumnWidth((short)cellnum, (short) ((50 * 3) / ((double) 1 / 20)));
                c = r.createCell((short)cellnum);

                // do some goofy math to demonstrate decimals
                c.setCellStyle( obj.getCellStyle2() );
                c.setCellValue(rownum * 10000 + cellnum
                        + (((double) rownum / 1000)
                        + ((double) cellnum / 10000)));
            }
        }

        obj.closeFile();
    }

	public String populateQuery( Connection conn, String query, HashMap<Integer, Integer> tratamentoEspecial, String nomeArquivo ) throws Exception {
		Statement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			ResultSetMetaData metaData = rs.getMetaData();
			int totalColunas = metaData.getColumnCount();
			
			Excel excel = new Excel();
            String path = SystemParameter.get( SenderInterface.SID, TechCommonKeys.TAG_SYSTEM_PROPERTIES, TechCommonKeys.SYSTEM_ROOT_PATH );
            excel.createFile(path + "allgenda/tmp/excel/" + nomeArquivo, nomeArquivo, "");
            HSSFSheet 		s  = excel.getSheet();
            HSSFRow   		r  = null;
            HSSFCell  		c  = null;
            
            int linExcel = 1;
            int colExcel = 0;

            r = s.createRow(linExcel); // create a row
            for( int i = 1; i <= totalColunas; i++ ) {
            	c = r.createCell((short)colExcel); // Na. coluna: valor
		        c.setCellStyle( excel.getCellStyle4() );
		        c.setCellValue( metaData.getColumnLabel(i) );
		        colExcel++;
            }
            
			while( rs.next() ) {
				linExcel++;
				colExcel = 0;

				r = s.createRow(linExcel); // create a row
				
				for( int i = 1; i <= totalColunas; i++ ) {
					String texto = rs.getString(i);
					
					if( tratamentoEspecial != null ) {
						if( tratamentoEspecial.containsKey(i) ) {
							int acao = tratamentoEspecial.get(i);
							if( acao == TRATAR_DATE_TIME_FORMAT ) {
								try {
									texto = DATE_TIME_FORMAT.format( rs.getTimestamp(i) );
								} catch (Exception e) {
									texto = "";
								}
								
							} else if( acao == TRATAR_DATE_FORMAT ) {
								try {
									texto = DATE_FORMAT.format( rs.getDate(i) );
								} catch (Exception e) {
									texto = "";
								}
							} else if( acao == TRATAR_DATE_FORMAT_MES_ANO ) {
								try {
									GregorianCalendar cal = new GregorianCalendar();
									cal.setTime(DATE_FORMAT_MES_ANO.parse(rs.getString(i)));
									texto = DATE_FORMAT_MES_ANO.format(cal.getTime());
								} catch (Exception e) {
									texto = "";
								}
								
							} else if( acao == TRATAR_LIMPA_TAGS ) {
								texto = texto.replaceAll("<.*?>", "");
								texto = texto.replaceAll(">", "");
								texto = texto.replaceAll("<", "");
								texto = texto.replaceAll("&nbsp;", " ");
								texto = texto.replaceAll("&nbsp", " ").trim();
							}
						}
					}
					
//			        c = r.createCell( (short)colExcel ); // Na. coluna: valor
//			        c.setCellStyle( excel.getCellStyle2() );
//					try {
//						c.setCellValue( Double.parseDouble(texto));
//					} catch (Exception e) {
//						c.setCellValue( texto );
//					}			        
//			        colExcel++;
					
			        c = r.createCell( (short)colExcel ); // Na. coluna: valor
			        c.setCellStyle( excel.getCellStyle2() );
			        c.setCellValue( texto );
			        colExcel++;

				}
			}
			
			excel.closeFile();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				st.close();
			} catch (Exception e) {
			}
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
		
		return ("/allgenda/tmp/excel/" + nomeArquivo);
	}
	
	public String populateQuery( Connection conn, ArrayList<String> query, HashMap<Integer, Integer> tratamentoEspecial, String nomeArquivo ) throws Exception {
		Statement st = null;
		ResultSet rs = null;
		
		try {
			
			Excel excel = new Excel();
            String path = SystemParameter.get( SenderInterface.SID, TechCommonKeys.TAG_SYSTEM_PROPERTIES, TechCommonKeys.SYSTEM_ROOT_PATH );
            excel.createFile(path + "allgenda/tmp/excel/" + nomeArquivo, nomeArquivo, "");
            HSSFSheet 		s  = excel.getSheet();
            HSSFRow   		r  = null;
            HSSFCell  		c  = null;
            
            int linExcel = 1;
            int colExcel = 0;
	
            for(int j=0; j < query.size(); j++){
            	st = conn.createStatement();
            	rs = st.executeQuery(query.get(j));
            	ResultSetMetaData metaData = rs.getMetaData();
            	int totalColunas = metaData.getColumnCount();
            	
	            for( int i = 1; i <= totalColunas; i++ ) {
	            	r = s.createRow(linExcel); // create a row
	            	c = r.createCell((short)colExcel); // Na. coluna: valor
			        c.setCellStyle( excel.getCellStyle4() );
			        c.setCellValue( metaData.getColumnLabel(i) );
			        colExcel++;
	            }
	            
				while( rs.next() ) {
					linExcel++;
					colExcel = 0;
	
					r = s.createRow(linExcel); // create a row
					
					for( int i = 1; i <= totalColunas; i++ ) {
						String texto = rs.getString(i);
						
						if( tratamentoEspecial != null ) {
							if( tratamentoEspecial.containsKey(i) ) {
								int acao = tratamentoEspecial.get(i);
								if( acao == TRATAR_DATE_TIME_FORMAT ) {
									try {
										texto = DATE_TIME_FORMAT.format( rs.getTimestamp(i) );
									} catch (Exception e) {
										texto = "";
									}
									
								} else if( acao == TRATAR_DATE_FORMAT ) {
									try {
										texto = DATE_FORMAT.format( rs.getDate(i) );
									} catch (Exception e) {
										texto = "";
									}
								} else if( acao == TRATAR_DATE_FORMAT_MES_ANO ) {
									try {
										GregorianCalendar cal = new GregorianCalendar();
										cal.setTime(DATE_FORMAT_MES_ANO.parse(rs.getString(i)));
										texto = DATE_FORMAT_MES_ANO.format(cal.getTime());
										if(cal.get(Calendar.MONTH)==11){
											linExcel++;
											colExcel = 0;
										}
									} catch (Exception e) {
										texto = "";
									}
									
								} else if( acao == TRATAR_LIMPA_TAGS ) {
									texto = texto.replaceAll("<.*?>", "");
									texto = texto.replaceAll(">", "");
									texto = texto.replaceAll("<", "");
									texto = texto.replaceAll("&nbsp;", " ");
									texto = texto.replaceAll("&nbsp", " ").trim();
								}
							}
						}
						
				        c = r.createCell( (short)colExcel ); // Na. coluna: valor
				        c.setCellStyle( excel.getCellStyle2() );
						try {
							c.setCellValue( Double.parseDouble(texto));
						} catch (Exception e) {
							c.setCellValue( texto );
						}			        
				        colExcel++;
				        
				        if(i==1){
				        	try {
				        		GregorianCalendar calAtual = new GregorianCalendar();
				        		calAtual.setTime(DATE_FORMAT_MES_ANO.parse(rs.getString(i)));
				        		System.out.println("Atual: "+calAtual.getTime());
				        		
				        		GregorianCalendar calAnterior = new GregorianCalendar();
				        		rs.previous();
				        		calAnterior.setTime(DATE_FORMAT_MES_ANO.parse(rs.getString(i)));
				        		calAnterior.add(Calendar.YEAR, +1);
				        		System.out.println("Aterior: "+calAnterior.getTime());
				        		
				        		rs.next();
				        		if(calAtual.equals(calAnterior)){
				        			linExcel++;
				        			System.out.println("cria linha com delta %");
				        			//cria linha com delta %
				        		}
				        		
							} catch (Exception e) {
				        		rs.next();
				        		e.printStackTrace();
							}
				        }
					}
				}
				linExcel++;
				linExcel++;
				colExcel = 0;

			}
				
			excel.closeFile();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				st.close();
			} catch (Exception e) {
			}
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
		
		return ("/ip/tmp/excel/" + nomeArquivo);
	}

	public void populateCabecalho(StringTokenizer cabecalho, HSSFCell c, HSSFRow r, HSSFCellStyle cs, int linExcel, HSSFSheet s) {
        r  = s.createRow(linExcel);
        cs = getCellStyle2();
        cs.setFillBackgroundColor( org.apache.poi.hssf.util.HSSFColor.GREY_25_PERCENT.index );
        cs.setFillForegroundColor( org.apache.poi.hssf.util.HSSFColor.GREY_25_PERCENT.index );
        cs.setFillPattern( HSSFCellStyle.SOLID_FOREGROUND );

        int i = 1;
        
        while( cabecalho.hasMoreTokens() ) {
            c = r.createCell((short)(i-1));
            c.setCellStyle(cs);
            c.setCellValue( cabecalho.nextToken() );
            
            i++;
        }
	}
	
	public Object[] iniciar( Excel excel, String nome, String header ) throws ExceptionWarning {
		String path = SystemParameter.get(SenderInterface.SID, TechCommonKeys.TAG_SYSTEM_PROPERTIES, TechCommonKeys.SYSTEM_ROOT_PATH );
		excel.createFile(path + "allgenda/tmp/excel/" + nome + "-" + System.currentTimeMillis() + ".xls", "IDRS", "");
		HSSFSheet s = excel.getSheet();
		HSSFCellStyle cs  = excel.getWorkbook().createCellStyle();
		// HSSFDataFormat df = excel.getWorkbook().createDataFormat();
		HSSFRow  r = null;
		HSSFCell c = null;
		StringTokenizer cabecalho = new StringTokenizer(header, ";");
		int total = cabecalho.countTokens();
		excel.populateCabecalho(cabecalho, c, r, cs, 0, s);
		return new Object[]{ s, total };
	}

	public void novaLinha(int linha, Excel excel, HSSFSheet s, String[] dados ) {
		HSSFCellStyle cs;
		HSSFRow r;
		HSSFCell c;
		r = s.createRow(linha);

		for( int coluna = 0; coluna < dados.length; coluna++ ) {
			c = r.createCell( (short)(coluna) );
			c.setCellValue( dados[coluna] );
			
			// teste se for campo DECIMAL
            try {
                c.setCellValue( Double.parseDouble(dados[coluna]) );
                cs = excel.getCellStyle3();
                cs.setDataFormat(df.getFormat("#,##0.00"));
                c.setCellStyle(cs);
            } catch (Exception e) {
                c.setCellValue( dados[coluna] );
            }			
		}
	}
	
}