package com.tetrasoft.common.data;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.technique.engine.app.SystemParameter;
import com.technique.engine.data.AbstractPersistentEntity;
import com.technique.engine.data.ConnectionPool;
import com.technique.engine.data.DataTypes;
import com.technique.engine.util.ExceptionInfo;
import com.technique.engine.util.ExceptionInjection;
import com.technique.engine.util.ExceptionWarning;
import com.technique.engine.util.StringUtil;
import com.technique.engine.util.TechCommonKeys;
import com.technique.engine.xml.XMLData;
import com.tetrasoft.app.sender.EmailSender;

public abstract class AdaptorPersistentEntity extends AbstractPersistentEntity {
	public static SimpleDateFormat DATE_FORMAT1 = new SimpleDateFormat("dd/MM/yyyy");
	public static SimpleDateFormat DATE_FORMAT1_BR  = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt","BR"));
	public static SimpleDateFormat DATE_FORMAT1_BR2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("pt","BR"));
	public static SimpleDateFormat DATE_FORMAT1_BR3 = new SimpleDateFormat("dd/MMM/yyyy", new Locale("pt","BR"));
	public static SimpleDateFormat DATE_FORMAT1_BR4 = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", new Locale("pt","BR"));
	public static SimpleDateFormat DATE_FORMAT2 = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat DATE_FORMAT2b = new SimpleDateFormat("yyyyMM");
	public static SimpleDateFormat DATE_FORMAT3 = new SimpleDateFormat("yyyyMMddHHmmss");
	public static SimpleDateFormat DATE_FORMAT4 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	public static SimpleDateFormat DATE_FORMAT5 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	public static SimpleDateFormat DATE_FORMAT6 = new SimpleDateFormat("dd/MM HH:mm");
	public static SimpleDateFormat DATE_FORMAT7 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat DATA_EXTENSO = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy", new Locale("pt","BR"));
	public static SimpleDateFormat DATA_EXTENSO2 = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt","BR"));
	public static String TOKEN 			= "CNFLGST";
    public static int STATUS_ATIVO   	= 1;
    public static int STATUS_INATIVO 	= 0;
	public static int STATUS_NAO_LIDO 	= 0;
	public static int STATUS_LIDO 		= 1;
    
    public static String ESPACO_BRANCO = "&#160;";
    public final static String SID = "Allgenda";

    public final static String LABEL_SELECIONE="-- selecione --";
    public final static String LABEL_RECORDNOTFOUND = "Nenhum registro foi encontrado.";

    final public static void populateIdiomas(XMLData data) {
    	data.openGroup("idiomas");
    		data.addParameterTag("record");
    		data.addParameter("id", "0");
    		data.addParameter("nome", "Portugu�s");

    		data.addParameterTag("record");
    		data.addParameter("id", "1");
    		data.addParameter("nome", "Ingl�s");

    		data.addParameterTag("record");
    		data.addParameter("id", "2");
    		data.addParameter("nome", "Espanhol");
    	data.closeGroup();
    }

    public static void populateStatus(XMLData data) {
    	data.openGroup("status");
    		data.addParameterTag("record");
    		data.addParameter("id", Integer.toString(STATUS_ATIVO) );
    		data.addParameter("nome", "Ativo");

    		data.addParameterTag("record");
    		data.addParameter("id", Integer.toString(STATUS_INATIVO) );
    		data.addParameter("nome", "Inativo");
    	data.closeGroup();
    }

    final public static String firstUpperCase( String s1 ) {
        String s = "";
        try {
            if( s1.length() <= 2 ) return s1;

            s1 = s1.toLowerCase();
            for( int i = 0; i < s1.length(); i++ ) {
                boolean go = false;

                if( i == 0 ) {
                    s = s1.substring(0,1).toUpperCase();
                    i++;

                } else {
                    if( s1.substring(i-1,i).equals(" ") ) {
                        int posI = i-1;
                        int posF = s1.indexOf(" ", i);

                        if( posF == -1 || (posF-posI > 4) ) go = true;
                    }
                }

                if( go  ) s += s1.substring(i,i+1).toUpperCase();
                if( !go ) s += s1.substring(i,i+1).toLowerCase();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }

    public final static String[] replaceArray = new String[] {
            "&quot;","\"",
            "&amp;","&",
            "&lt;","<",
            "&gt;",">",
            "&apos;","'"
    };


    static {
        try {
            ConnectionPool.getInstance().initialize(SID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** retrieves the database type declared in class DataTypes
     */
    final public int getDatabaseType() {
        return DataTypes.DB_MYSQL;
    }

    /** retrieves the system identification
     */
    final public String getSID() {
        return SID;
    }

    final public static boolean isEmpty(Object str) {
        if (str==null) return true;
        if (str.toString().trim().equals("")) return true;
        return false;
    }

    final public static boolean isEmptyNumber(Object str) {
        if (str==null) return true;
        if (str.toString().trim().equals("")) return true;
        if (str.toString().trim().equals("0")) return true;
        return false;
    }

    final public static void validateEmail(String email) throws ExceptionInfo {
        if (email.indexOf("@")==-1) throw new ExceptionInfo("E-mail inv�lido!");
    }

    final public static boolean isNull(Object o) {
        if (o==null) return true;
        if (o.toString().trim().equals("")) return true;
        return false;
    }

    final public static String removeSpaces(String str) {
        if (str==null) return null;
        return str.trim();
    }

    final public static String xmlToText(String content) {
        if (content==null) return "";
        for (int i=0; i<replaceArray.length; i+=2) {
            content = StringUtil.replace(content,replaceArray[i],replaceArray[i+1]);
        }
        return content;
    }

    public int populateList(Listable listableObject, String tagGroupName, boolean withSelect, String sqlWhere, XMLData data) throws ExceptionWarning, ExceptionInjection {
        Connection conn=null;
        try {
            conn=getConnection();
            return populateList(listableObject,tagGroupName,withSelect,sqlWhere,data,conn);
        } finally {
            close(conn);
        }
    }
    
    public int populateList(Listable listableObject, String tagGroupName, boolean withSelect, String sqlWhere, XMLData data, Connection conn) throws ExceptionWarning, ExceptionInjection {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int counter=0;
        try {
            String sql = "select "+listableObject.get_IDFieldName()+" id, "+listableObject.get_NomeFieldName()+" nome from "+listableObject.getTableName()+getFormatedWhere(sqlWhere);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            data.openGroup(tagGroupName);
            if (withSelect) {
                data.addParameterTag("record");
                data.addParameter("id","0");
                data.addParameter("nome", listableObject.get_SelecioneName());
            }
            while (rs.next()) {
                data.addParameterTag("record");
                data.addParameter("id",rs.getLong("id")+"");
                data.addParameter("nome", rs.getString("nome"));
                counter++;
            }
            data.closeGroup();
            return counter;
        } catch (SQLException e) {
            throw new ExceptionWarning("Erro de acesso ao banco de dados. Mensagem:"+e.getMessage());
        } finally {
            close(ps,rs);
        }
    }

	final public long getCount(String where, Connection conn) throws ExceptionWarning, ExceptionInjection {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try	{
			String sql = "select count(*) from " + getTableName() + getFormatedWhere(where);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();			
			if(rs.next()) {
				long l = rs.getLong(1);
				return l;
			}
			return 0L;
		} catch(SQLException e)	{
			SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @AbstractEntity.getCount(" + where + ")", e);
			System.out.println(e.getMessage());
			throw new ExceptionWarning("SQLException in getCount method", e.getMessage());
		} finally {
			close(ps, rs);
		}
	}

    final public void validaDataMaxMin(Date date, String campo) throws ExceptionInfo {
		if (date == null) return;
		String d1 = DATE_FORMAT2.format(date);
		int ano = Integer.parseInt(d1.substring(0, 4));
		if (ano < 2000) throw new ExceptionInfo("O ano do campo " + campo + " n�o pode ser inferior a 2000");
		if (ano > 2050) throw new ExceptionInfo("O ano do campo " + campo + " n�o pode ser superior a 2050");
	}
    // -------------------------------------------------------------------------
    // Renato V. Filipov
    // -------------------------------------------------------------------------
    final public synchronized void executeQuery( String query, Connection conn ) throws Exception {
        Statement  dbSt = null;
        ResultSet  dbRs = null;

        try {
            dbSt = conn.createStatement();
            try {
                dbRs = dbSt.executeQuery( query );
            } catch (Exception e) {
                dbSt.execute( query );
            }
        } catch( Exception e2 ) {
        	System.out.println( e2.getMessage() + " - " + e2.getCause() );
            System.out.println("query = " + query);
            throw new Exception( e2 );
        } finally {
            try {
                if(dbSt  != null) dbSt.close();
                if(dbRs  != null) dbRs.close();
            } catch (Exception e) {
            }
        }
    }
    public synchronized void executeQuery( String query ) throws Exception {
        Connection conn = null;
        try {
            conn = getConnection();
            executeQuery(query, conn);
        } catch( Exception e2 ) {
        	System.out.println( e2.getMessage() + " - " + e2.getCause() );
            System.out.println("query = " + query);
            throw new Exception( e2 );
        } finally {
            close(conn);
        }
    }
    // -------------------------------------------------------------------------
    // Renato V. Filipov
    // -------------------------------------------------------------------------
    final public long[] getArray( String pk, String query ) throws Exception {
        Connection conn  = null;
        Statement  dbSt  = null;
        ResultSet  dbRs  = null;

        try {
            conn = getConnection();
            dbSt = conn.createStatement();
            dbRs = dbSt.executeQuery( query );

            ArrayList<Long> a = new ArrayList<Long>();

            while( dbRs.next() ) {
                a.add( new Long(dbRs.getLong(pk)) );
            }

            long[] retorno = new long[a.size()];

            for( int i = 0; i < a.size(); i++ ) {
                Long id = (Long)a.get(i);
                retorno[i] = id.longValue();
            }

            return retorno;
        } catch( Exception e ) {
            e.printStackTrace();
            throw new Exception( e );
        } finally {
            close(conn);
            try {
            	dbSt.close();
            	dbRs.close();
			} catch (Exception e) {
			}
        }
    }
    // -------------------------------------------------------------------------
    // Renato V. Filipov
    // -------------------------------------------------------------------------
    final public ResultSet getResultSet( String query ) throws Exception {
        Connection conn = null;
        Statement  dbSt = null;
        ResultSet  dbRs = null;

        try {
            conn = getConnection();
            dbSt = conn.createStatement();
            dbRs = dbSt.executeQuery( query );
            return dbRs;
        } catch( Exception e ) {
            System.out.println("query = " + query);
            throw new Exception( e );
        } finally {
            close(conn);
            try {
                if(dbSt  != null) dbSt.close();
                if(dbRs  != null) dbRs.close();
            } catch (Exception e) {
            }
        }
    }

    public static String replaceChars( String value ) {
        value = value.toLowerCase();
		value = value.replace(' ','_');
		value = value.replace('�','c');
		value = value.replace('�','a');
		value = value.replace('�','a');
		value = value.replace('�','a');
		value = value.replace('�','a');
		value = value.replace('�','e');
		value = value.replace('�','e');
		value = value.replace('�','e');
		value = value.replace('�','i');
		value = value.replace('�','i');
		value = value.replace('�','i');
		value = value.replace('�','o');
		value = value.replace('�','o');
		value = value.replace('�','o');
		value = value.replace('�','o');
		value = value.replace('�','u');
		value = value.replace('�','u');
		value = value.replace('�','u');
		value = value.replace('@','_');
		value = value.replace('#','_');
		value = value.replace('$','_');
		value = value.replace('%','_');
		value = value.replace('�','_');
		value = value.replace('&','_');
		value = value.replace('*','_');
		value = value.replace('(','_');
		value = value.replace(')','_');
		value = value.replace('|','_');
		value = value.replace('\\','_');
		value = value.replace('/','_');
		value = value.replace('�','_');
		value = value.replace(' ','_');
		value = value.replace('`','_');
		value = value.replace('~','_');
		value = value.replace('^','_');
		value = value.replace(';','_');
		value = value.replace(':','_');
		value = value.replace('<','_');
		value = value.replace('>','_');
		value = value.replace('[','_');
		value = value.replace(']','_');
		value = value.replace('{','_');
		value = value.replace('}','_');
		value = value.replace('�','_');
		value = value.replace('�','_');

        return value;
    }

    final public static String formatValor( double v, int decimal ) {
        try {
            Locale BRASIL = new Locale("pt","BR");
            NumberFormat nf = NumberFormat.getInstance(BRASIL);
            nf.setGroupingUsed(false);
            nf.setMaximumIntegerDigits(10);
            nf.setMinimumIntegerDigits(1);
            nf.setMinimumFractionDigits(decimal);
            nf.setMaximumFractionDigits(decimal);

            return nf.format( v );
        } catch (Exception e) {
            e.printStackTrace();
            return "0.00";
        }
    }

    final public static String formatValor2( double v ) {
    	return formatValor(v,2);
    }

    final public static String formatValor( double v ) {
        try {
            Locale BRASIL = new Locale("pt","BR");
            NumberFormat nf = NumberFormat.getInstance(BRASIL);
            nf.setGroupingUsed(true);
            nf.setMaximumIntegerDigits(10);
            nf.setMinimumIntegerDigits(1);
            nf.setMinimumFractionDigits(2);
            nf.setMaximumFractionDigits(2);

            if( v == -0 ) v = 0;

            return v >= 0 ? nf.format( v ) : "(" + nf.format(0-v) + ")";
        } catch (Exception e) {
            e.printStackTrace();
            return "0.00";
        }
    }

    final public synchronized ArrayList<String> executeQueryArray( String query, Connection conn ) throws Exception {
        Statement  dbSt = null;
        ResultSet  dbRs = null;

        ArrayList<String> a = new ArrayList<String>();

        try {
            conn = getConnection();
            dbSt = conn.createStatement();
            try {
                dbRs = dbSt.executeQuery( query );
                while( dbRs.next() )
                	a.add( dbRs.getString(1) );

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch( Exception e2 ) {
            System.out.println("query = " + query);
            e2.printStackTrace();
            throw new Exception( e2 );
        } finally {
            try {
                if(dbSt  != null) dbSt.close();
                if(dbRs  != null) dbRs.close();
            } catch (Exception e) {
            }
        }

        return a;
    }

    final public synchronized ArrayList<String> executeQueryArray( String query ) throws Exception {
        Connection conn = null;

        ArrayList<String> a = new ArrayList<String>();

        try {

        	conn = getConnection();
			a = executeQueryArray(query, conn);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close( conn );
		}

		return a;
    }

    public static final String[] HTMLCHARS_TO_STRING = new String[] {
        "&quot;","'",
        "&nbsp;"," ",
        "�","�",
        "&ccedil;","�",
        "&Ccedil;","�",
        "&atilde;","�",
        "&otilde;","�",
        "&Atilde;","�",
        "&Otilde;","�",
        "&ecirc;","�",
        "&ocirc;","�",
        "&euml;","e",
        "&reg;","(R)",
        "�","&#174;",
        "&agrave;","�",
        "&acirc;","�",
        "&Acirc;","�",
        "&Ecirc;","�",
        "&aacute;","�",
        "&eacute;","�",
        "&iacute;","�",
        "&oacute;","�",
        "&uacute;","�",
        "&Aacute;","�",
        "&Eacute;","�",
        "&Iacute;","�",
        "&Oacute;","�",
        "&Uacute;","�",
        "&copy;","(c)",
        "&amp;","&#38;",
        "&gt;","&#62;",
        "&lt;","&#60;",
        "&euro;","$Euro",
        "&Agrave;","�",
        "&#034;", "'",
   };

	private static final char[] FIRST_CHAR =
		(" !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ"
		+ "[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~ E ,f'.++^%S<O Z  ''\"\".-"
		+ "-~Ts>o ZY !C#$Y|$'(�<--(_�+23'u .,1�>113?������A���������D���"
		+ "���X������Ds���������������� �������������t�")
		.toCharArray();
	/** Para a normaliza��o dos caracteres de 32 a 255, segundo caracter */
	private static final char[] SECOND_CHAR =
		("                                                            "
		+ "\\                                   $  r . + o  E          "
		+ "  M  e     #  =   C <  R  -..     . >424     E E            "
		+ "   E E     hs    e e         h     e e     h ")
		.toCharArray();
	/**
	 * Efetua as seguintes normaliza��es para obten��o de certificados:
	 * - Elimina acentos e cedilhas dos nomes;
	 * - Converte aspas duplas em simples;
	 * - Converte algumas letras estrangeiras para seus equivalentes ASCII
	 * (como �, convertido para ss)
	 * - P�e um "\" antes de v�rgulas, permitindo nomes como
	 * "Verisign, Corp." e de "\", permitindo nomes como " a \ b ";
	 * - Converte os sinais de = para -
	 * - Alguns caracteres s�o removidos:
	 * -> os superiores a 255,
	 * mesmo que possam ser representados por letras latinas normais
	 * (como s, = U+015E = Latin Capital Letter S With Cedilla);
	 * -> os caracteres de controle (exceto tab, que � trocado por um espa�o)
	 * @param str A string a normalizar.
	 * @return A string normalizada.
	 */

	public static String normalize(String str) {
		char[] chars = str.toCharArray();
		StringBuffer ret = new StringBuffer(chars.length * 2);
		for (int i = 0; i < chars.length; ++i) {
			char aChar = chars[i];
			if (aChar == ' ' || aChar == '\t') {
				ret.append(' '); // convertido para espa�o
			} else if (aChar > ' ' && aChar < 256) {
				if (FIRST_CHAR[aChar - ' '] != ' ') {
					ret.append(FIRST_CHAR[aChar - ' ']); // 1 caracter
					System.out.print(".");
				}
				if (SECOND_CHAR[aChar - ' '] != ' ') {
					System.out.print(",");
					ret.append(SECOND_CHAR[aChar - ' ']); // 2 caracteres
				}
			}
		}

		return ret.toString();
	}

	final public void saveBlob( File file, Connection conn, String tabela, String idNome, String idValor ) throws ExceptionInfo, ExceptionWarning {
		PreparedStatement dbSt = null;

		boolean closeConn = false;
		try {
			if( conn == null ) {
				conn = getConnection();
				closeConn = true;
			}

			dbSt = conn.prepareStatement("UPDATE " + tabela + " SET imagem = ? WHERE " + idNome + " = ?");

			FileInputStream stream = new FileInputStream( file );
			dbSt.setString( 2, idValor );
			dbSt.setBinaryStream( 1, stream, (int)file.length() );

			dbSt.executeUpdate();
			stream.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInfo( e.getMessage() );
		} finally {
			if( closeConn ) {
				close( conn, dbSt );
			} else {
				close( dbSt );
			}
		}
	}

	final public void saveBlob( File file, Connection conn, String tabela, String idNome, String idValor, String campo ) throws ExceptionInfo, ExceptionWarning {
		PreparedStatement dbSt = null;
		
		boolean closeConn = false;
		try {
			if( conn == null ) {
				conn = getConnection();
				closeConn = true;
			}
			
			dbSt = conn.prepareStatement("UPDATE " + tabela + " SET " + campo + " = ? WHERE " + idNome + " = ?");
			
			FileInputStream stream = new FileInputStream( file );
			dbSt.setString( 2, idValor );
			dbSt.setBinaryStream( 1, stream, (int)file.length() );
			
			dbSt.executeUpdate();
			stream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInfo( e.getMessage() );
		} finally {
			if( closeConn ) {
				close( conn, dbSt );
			} else {
				close( dbSt );
			}
		}
	}
	
	final public static Locale BRASIL = new Locale("pt","BR");
	final public static NumberFormat nf = NumberFormat.getInstance(BRASIL);
	static {
		nf.setGroupingUsed(true);
		nf.setMaximumIntegerDigits(10);
		nf.setMinimumIntegerDigits(1);
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
	}
	
	final public static NumberFormat nf2 = NumberFormat.getInstance(BRASIL);
	static {
		nf2.setGroupingUsed(true);
		nf2.setMaximumIntegerDigits(10);
		nf2.setMinimumIntegerDigits(1);
		nf2.setMinimumFractionDigits(0);
		nf2.setMaximumFractionDigits(0);
	}

    final public void sendMessage( String _msg, String titulo, String[] destEmail ) {
    	EmailSender.enviarMensagemThread(titulo, _msg, destEmail);
    }

	private static long	lastId	= 0L;

	final public Long getNextId() {
		synchronized (AdaptorPersistentEntity.class) {
			int servidor = 1;
			long nextID = lastId;
			while (nextID == lastId) {
				nextID = System.currentTimeMillis();

				nextID += ( (servidor-1) * 1000000000000L );
			}
			lastId=nextID;
			return nextID;
		}
	}
}
