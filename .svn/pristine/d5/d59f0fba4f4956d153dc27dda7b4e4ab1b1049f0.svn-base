package com.tetrasoft.app.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import com.tetrasoft.app.util.RegularExp;

/**
 *
 * @author  Administrator
 */
public class AdaptorStatus extends RegularExp implements java.io.Serializable {
    public static final int STATUS_ERROR = 0;
    public static final int STATUS_NO_MATCH = 1;
    public static final int STATUS_OK = 100;
    private int status = 0;
    private int htmlError = 0;
    private Exception exception;
    private Date data;
    private long horaDivulgacao;
    private String titulo;
    private String editoria;
    private String olho;
    private String author;
    private ArrayList imagesUrls;
    private Long idColuna;
    private long idVeiculo;
    private StringBuffer conteudo;
    private String pais;
    private String estado;
    private String jornalista;
    private boolean versaoOnline;

    /** Creates a new instance of AdaptorStatus */
    public AdaptorStatus() {
        setVersaoOnline(false);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int value) {
        this.status = value;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception value) {
        this.exception = value;
    }

    public Date getData() {
        if (data == null)
            data = new Date();
        return data;
    }

    public void setData(Date value) {
        this.data = value;
    }

    public String getTitulo() {
        if (titulo == null)
            titulo = "";
        return titulo;
    }

    public void setTitulo(String value) {
        if (value != null)
            value = value.trim();
        this.titulo = value;
    }

    public String getOlho() {
        return olho;
    }

    public void setOlho(String value) {
        this.olho = value;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String value) {
        this.author = value;
    }

    public ArrayList getImagesUrls() {
        return imagesUrls;
    }

    public void setImagesUrls(ArrayList value) {
        this.imagesUrls = value;
    }

    public Long getIdColuna() {
        return idColuna;
    }

    public void setIdColuna(Long value) {
        this.idColuna = value;
    }

    public long getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(long value) {
        this.idVeiculo = value;
    }

    public StringBuffer getConteudo() {
        if (conteudo == null)
            conteudo = new StringBuffer();
        return conteudo;
    }

    public void setConteudo(StringBuffer value) {
        this.conteudo = value;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String value) {
        this.pais = value;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String value) {
        this.estado = value;
    }

    public String getJornalista() {
        return jornalista;
    }

    public void setJornalista(String value) {
        this.jornalista = value;
    }

    public boolean getVersaoOnline() {
        return versaoOnline;
    }

    public void setVersaoOnline(boolean value) {
        this.versaoOnline = value;
    }

    public int getHtmlError() {
        return htmlError;
    }

    public void setHtmlError(int value) {
        this.htmlError = value;
    }

    public String getEditoria() {
        if (editoria == null)
            editoria = "";
        return editoria;
    }

    public void setEditoria(String value) {
        this.editoria = value;
    }

    private String resumo;
	public static Pattern patInvalid = Pattern.compile("[’‘“¨”]", Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
	public static Pattern patInvalid2 = Pattern.compile("[––—•]", Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
	// Metacaracteres devem ser escapados: . ? * + ^ $ | [ ] { } ( ) \ -
	public static Pattern patValid = Pattern.compile("[^a-z|0-9|çáéíóúâêîôûäëïöüàèìòùãõ /!@#=%¨&_':;<>,~ªº¹²³\"\\-\\.\\?\\*\\+\\^\\$\\|\\[\\]\\{\\}\\(\\)\\\\]", Pattern.CASE_INSENSITIVE + Pattern.DOTALL);

	public static int[] hashInvalid1 = new int[]{ 145, 146, 147, 148 };
	public static int[] hashInvalid2 = new int[]{ 149, 150, 151 };
/*  Caracteres especiais: ’‘“'”––—•
    ’ = 146
    ‘ = 145
    “ = 147
    ' = 39
    ” = 148
    – = 150
    – = 150
    — = 151
    • = 149
*/
    static Pattern patHtmlTag = Pattern.compile("<.*?>|[—“¨”–]", Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
	static Pattern patHtmlComent = Pattern.compile("<!--.*?-->", Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
    static Pattern patHtmlFont = Pattern.compile("<font.*?>");
    static Pattern patHtmlScript = Pattern.compile("<script.*?>.*?</script.*?>", Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
    static Pattern patHtmlDiv = Pattern.compile("<div.*?>", Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
    static Pattern patHtmlPara = Pattern.compile("<p.*?>", Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
    static Pattern patHtmlHRef = Pattern.compile("<(/|)a.*?>", Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
    //static Pattern patHtmlHRef = Pattern.compile("<a.*?>.*?</a.*?>", Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
    //static Pattern patHtmlImg = Pattern.compile("<img.*?>.*?</img.*?>", Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
	static Pattern patHtmlTituloRepl = Pattern.compile("[‘’“”]", Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
	static Pattern patHtmlTituloRepl2 = Pattern.compile("[——]", Pattern.CASE_INSENSITIVE + Pattern.DOTALL);

    public void normalize() {
        try {
			String cont = getConteudo().toString();

            try {
  			    setTitulo(AbstractAdaptorMetaChar.replaceMetaChar(replaceAll(patHtmlTituloRepl2,replaceAll(patHtmlTituloRepl,replaceAll(patHtmlTag, getTitulo(), ""),"'"),"-")));
  			    setTitulo( AbstractAdaptorMetaChar.replaceMetaChar_2(getTitulo()) );
			    setEditoria(AbstractAdaptorMetaChar.replaceMetaChar(replaceAll(patHtmlTag, getEditoria(), "").replaceAll("&#38;", "e")));

    			resumo = replaceAll(patHtmlTag, cont, "");
    			if (resumo.length() > 240)
    				resumo = resumo.substring(0, 240) + "...";
            } catch (Exception e2) {
            }

	        cont = replaceAll(patHtmlFont, cont, "<font class=texto>");
	        cont = replaceAll(patHtmlScript, cont, "");
	        cont = replaceAll(patHtmlDiv, cont, "<div class=texto");
	        cont = replaceAll(patHtmlHRef, cont, "");
	        cont = replaceAll(patHtmlPara, cont, "<p class=texto>");
	        cont = replaceAll(patInvalid, cont, "'");
	        cont = replaceAll(patInvalid2, cont, "-");
			cont = replaceAll(patHtmlComent, cont, "");
			cont = replaceAll(patHtmlTituloRepl, cont, "'");
			cont = replaceAll(patHtmlTituloRepl2, cont, "-");
	        setConteudo(new StringBuffer(cont));

	        if (getConteudo() == null || getConteudo().length() < 10)
	            setStatus(STATUS_ERROR);
	        if (getTitulo() == null || getTitulo().length() < 3)
	            setStatus(STATUS_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
        	setStatus(STATUS_ERROR);
        }
    }

    /**
     * @return
     */
    public long getHoraDivulgacao() {
        return horaDivulgacao;
    }

    /**
     * @param l
     */
    public void setHoraDivulgacao(long l) {
        horaDivulgacao = l;
    }

    public static void main( String[] args ) {
        AdaptorStatus obj = new AdaptorStatus();
        obj.setTitulo("tit");
        obj.setEditoria("edit");
        obj.setData( new Date() );
        
        String teste = "??'\"";
        char[] c = teste.toCharArray();
        byte[] b = teste.getBytes();
        
        for( int i = 0; i < c.length; i++ ) {
			System.out.println(c[i] + "-" + b[i]);
		}
        
/*        
        obj.setConteudo( new StringBuffer("e ? ? reqwrtwetwe ‘ ewtwe twtwewt ’ twewtwetwe – ") );
        System.out.println("ANTES = " + obj.getConteudo().toString());
        obj.normalize();
        System.out.println("DEPOIS = " + obj.getConteudo().toString());
*/        
    }
    
}