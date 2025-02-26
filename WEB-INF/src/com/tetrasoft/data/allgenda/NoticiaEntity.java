package com.tetrasoft.data.allgenda;

import java.sql.Connection;
import java.util.Date;

import com.technique.engine.app.SystemParameter;
import com.technique.engine.data.DataTypes;
import com.technique.engine.util.ExceptionInfo;
import com.technique.engine.util.ExceptionInjection;
import com.technique.engine.util.ExceptionWarning;
import com.technique.engine.web.UserSession;
import com.tetrasoft.app.adaptor.AbstractAdaptorMetaChar;
import com.tetrasoft.app.sender.SenderInterface;
import com.tetrasoft.common.data.BasePersistentEntity;
import com.tetrasoft.common.data.Listable;
import com.tetrasoft.util.File;
import com.tetrasoft.util.Utils;
import com.tetrasoft.web.ajax.AjaxUploadServlet;

public class NoticiaEntity extends BasePersistentEntity implements Listable {
	public static int ID_FUNCAO = 664;
	
	private static Object[] structurePk = new Object[] {
		"IdNoticia","idNoticia", DataTypes.JAVA_LONG
	};
	private static Object[] structure = new Object[] {
		"IdCategoria","idCategoria", DataTypes.JAVA_LONG,
		"Titulo","titulo",DataTypes.JAVA_STRING,
		"Subtitulo","subtitulo",DataTypes.JAVA_STRING,
		"LinhaFina","linhaFina",DataTypes.JAVA_STRING,
		"Descricao","descricao",DataTypes.JAVA_STRING,
		"Data","data", DataTypes.JAVA_DATE,
//		"Imagem","Imagem", DataTypes.JAVA_STRING,  ******* BLOB *******  
		"LegendaImagem","legendaImagem", DataTypes.JAVA_STRING,
		"Extensao","extensao", DataTypes.JAVA_STRING,
		"Destaque","destaque",DataTypes.JAVA_INTEGER,
		"ExibirHome","exibirHome",DataTypes.JAVA_INTEGER,
		"IdGaleria","idGaleria",DataTypes.JAVA_LONG,
		"Acessos","acessos",DataTypes.JAVA_INTEGER,
		"Comentarios","comentarios",DataTypes.JAVA_INTEGER,
		"Compartilhada","compartilhada",DataTypes.JAVA_INTEGER,
		"Ativo","ativo",DataTypes.JAVA_INTEGER
	};

	public NoticiaEntity() {
	}

	public Object[] getStructure() {
		return structure;
	}

	public Object[] getStructurePk() {
		return structurePk;
	}

	private Long idNoticia = new Long(0);
	private Long idCategoria = new Long(0);
	private String titulo = "";
	private String subtitulo = "";
	private String descricao = "";
	private Date data;
	private String legendaImagem = "";        
	private Integer ativo=1;
	private Integer destaque=0;
	private Integer exibirHome=1;
	private Long idGaleria=0l;
	private Integer acessos=0;
	private Integer comentarios=0;
	private Integer compartilhada=0;
	private String linhaFina = "";
	private String extensao = "";

	public Long getIdNoticia() {
		return idNoticia;
	}

	public void setIdNoticia(Long idNoticia) {
		this.idNoticia = idNoticia;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getTituloWeb() {
		return AbstractAdaptorMetaChar.replaceMetaChar_Simple( this.getTitulo().replace(' ', '-') );
	}

	public String getSubtitulo() {
		return subtitulo;
	}

	public void setSubtitulo(String subtitulo) {
		this.subtitulo = subtitulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDataExtenso() {
		return DATA_EXTENSO2.format( this.getData() );
	}
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getLegendaImagem() {
		return legendaImagem;
	}

	public void setLegendaImagem(String legendaImagem) {
		this.legendaImagem = legendaImagem;
	}

	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

	public Integer getDestaque() {
		return destaque;
	}

	public void setDestaque(Integer destaque) {
		this.destaque = destaque;
	}

	public Integer getExibirHome() {
		return exibirHome;
	}

	public void setExibirHome(Integer exibirHome) {
		this.exibirHome = exibirHome;
	}

	public Long getIdGaleria() {
		return idGaleria;
	}

	public void setIdGaleria(Long idGaleria) {
		this.idGaleria = idGaleria;
	}

	public Integer getAcessos() {
		return acessos;
	}

	public void setAcessos(Integer acessos) {
		this.acessos = acessos;
	}

	public Integer getComentarios() {
		return comentarios;
	}

	public void setComentarios(Integer comentarios) {
		this.comentarios = comentarios;
	}

	public Integer getCompartilhada() {
		return compartilhada;
	}

	public void setCompartilhada(Integer compartilhada) {
		this.compartilhada = compartilhada;
	}

	public String getLinhaFina() {
		return linhaFina;
	}

	public void setLinhaFina(String linhaFina) {
		this.linhaFina = linhaFina;
	}

	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}

	public String getTableName() {
		return "noticia"; 
	}
	
	public String get_IDFieldName() {
		return "idNoticia";
	}

	public String get_NomeFieldName() {
		return "titulo";
	}

	public String get_SelecioneName() {
		return LABEL_SELECIONE;
	}

	public void save( UserSession session ) throws ExceptionInfo, ExceptionWarning, ExceptionInjection {
		boolean novo = false;
		Connection conn = null;
		try {
			conn = getConnection();

//			tratamento especial, pois o conteédo do POST é muito grande
			String value = session.getAttributeString("value");
			value = value.replaceAll("%20", " ");
			value = value.replaceAll("%26", "&");
			value = value.replaceAll("<p>\\+</p>", "<p> </p>");
//			System.out.println(value);
			
			String idNoticia		= value.substring( value.indexOf("idNoticia=")+10, value.indexOf("&", value.indexOf("idNoticia=") ) );
			String descricao	 	= value.substring( value.indexOf("descricao=")+10, value.indexOf("&", value.indexOf("descricao=") ) );
			String legendaImagem	= value.substring( value.indexOf("legendaImagem=")+14, value.indexOf("&", value.indexOf("legendaImagem=") ) );
			String titulo			= value.substring( value.indexOf("titulo=")+7, value.indexOf("&", value.indexOf("titulo=") ) );
			String subtitulo		= value.substring( value.indexOf("subtitulo=")+10, value.indexOf("&", value.indexOf("subtitulo=") ) );
			String linhaFina		= value.substring( value.indexOf("linhaFina=")+10, value.indexOf("&", value.indexOf("linhaFina=") ) );
			String data				= value.substring( value.indexOf("data=")+5, value.indexOf("&", value.indexOf("data=") ) );
			String idCategoria		= value.substring( value.indexOf("idCategoria=")+12, value.indexOf("&", value.indexOf("idCategoria=") ) );
			String idGaleria		= value.substring( value.indexOf("idGaleria=")+10, value.indexOf("&", value.indexOf("idGaleria=") ) );
			String destaque			= value.substring( value.indexOf("destaque=")+9, value.indexOf("&", value.indexOf("destaque=") ) );
			String exibirHome		= value.substring( value.indexOf("exibirHome=")+11, value.indexOf("&", value.indexOf("exibirHome=") ) );
			String inativo			= value.substring( value.indexOf("inativo=")+8, value.indexOf("&", value.indexOf("inativo=") ) );
			
			this.setIdNoticia( new Long(idNoticia) );
			this.setDescricao( descricao );	 	
			this.setLegendaImagem( legendaImagem );	
			this.setTitulo( titulo );			
			this.setSubtitulo( subtitulo );		
			this.setLinhaFina( linhaFina );		
			this.setIdCategoria( new Long( idCategoria ) );
			this.setIdGaleria( new Long( idGaleria ) );	
			this.setDestaque( new Integer( destaque ) );	
			this.setExibirHome( new Integer( exibirHome ) );
			try {
				this.setData( DATE_FORMAT.parse( data ) );		
			} catch (Exception e) {
				this.setData( new Date() );
			}
			try {
				if( inativo.equals("on") ) {
					this.setAtivo(0);
				} else {
					this.setAtivo(1);
				}
			} catch (Exception e) {
			}
			
			NoticiaEntity tmp = new NoticiaEntity();
			tmp.setIdNoticia( this.getIdNoticia() );
			novo = !tmp.getThis(conn);
			
			if( this.getDescricao().contains("mceItemIframe") || this.getDescricao().contains("mceItemVideo") ) {
				this.setDescricao( Utils.corrigirIframeMCE( this.getDescricao() ) );
			}
			
			this.setDescricao( Utils.createVideoMCE( this.getDescricao() ) );
			
			if( novo ) {
//				this.setIdNoticia( getNextId() );
				this.insert(conn);
			} else {
				this.update(conn);
			}
		
			/*
				}else if(imagem.getFileName().toLowerCase().endsWith("zip")){
					File dir = new File(root+"upload/news/"+getIdNoticia());
					File galeria = new File(root+"upload/news/"+getIdNoticia()+"/"+imagem.getFileName());
					if(!galeria.exists()) galeria.mkdirs();
					File.copyFile(imagem.getFile(), galeria);
					new Zip().unzip(galeria, dir);
					galeria.delete();

					GaleriaNoticiaEntity newGal;

					java.io.File files[] = dir.listFiles();
					java.io.File dest;
					java.io.File destThumb;

					// se não for novo, deleta as existentes para inserir as novas.
					if(novo == false){
						newGal = new GaleriaNoticiaEntity();
						ArrayList<GaleriaNoticiaEntity> galOld = newGal.getArray("idNoticia = " + this.getIdNoticia());
						for(GaleriaNoticiaEntity toDelete : galOld){
							toDelete.delete();
						}
					}

					for (int i = 0; i < files.length; i++) {
						dest = new java.io.File(dir.getAbsolutePath());
						dest.mkdirs();
						if(thumb.excedeuLimite(files[i].getPath(), 900, 900))
							thumb.converter(files[i].getPath(), dest.getAbsolutePath() + "/" + files[i].getName(), 900, 900);

						if(files[i].isFile()){
							newGal = new GaleriaNoticiaEntity();
							newGal.setIdNoticia(this.getIdNoticia());
							newGal.setIdGaleria(newGal.getNextId());
							newGal.setNomeFile(files[i].getName());
							newGal.insert();
						}

						destThumb = new java.io.File(dir.getAbsolutePath() + "/thumb");
						destThumb.mkdirs();
						if(thumb.excedeuLimite(files[i].getPath(), 220, 220))
							thumb.converter(files[i].getPath(), destThumb.getAbsolutePath() + "/" + files[i].getName(), 220, 220);
					}
				}
				
				GaleriaNoticiaEntity gal = new GaleriaNoticiaEntity();
				gal.inserirGaleria(data, session);
				
				RSS.gerarRSS();
			 */

		} catch( Exception e ) {
			e.printStackTrace();
			
		} finally {
			try {
				close(conn);
			} catch (Exception e) {
			}
		}
	}

	public void deleteImage(String id){
		// TODO: implementar
	}

	public void deleteGalery(String id){
		try{
			// TODO: implementar
			
			String path = SystemParameter.get(SenderInterface.SID, "systemProperties", "filePath") + "upload/news/" + id;
			File file = new File( path );
			if( file.isDirectory() ) file.delete();

		}catch (Exception e) {
		}
	}

	public boolean contemImagem(Connection conn) {
		java.io.File[] anexos = getAnexos(); 
		if( anexos != null && anexos.length == 1 ) {
			return true;
			
		} else {
			
			boolean hasImage = (this.getExtensao(conn) == null || this.getExtensao().equals("")) ? false: true;
			
			if( !hasImage ) {
				try {
					NoticiaEntity tmp = new NoticiaEntity();
					hasImage = tmp.getThis("idNoticia = " + this.getIdNoticia() + " AND imagem IS NOT NULL", conn);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			return hasImage;
		}
	}
	
	public boolean contemGaleria() {
		if( getAnexos().length > 1 ) {
			return true;
		} else {
			return false;
		}
	}

	public java.io.File[] getAnexos() {
		try {
			String path = SystemParameter.get(SenderInterface.SID, "systemProperties", "filePath") + "upload/news/" + this.getIdNoticia();
			File file = new File( path );
			return file.listFiles();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getExtensao( Connection conn ) {
		if( extensao == null || extensao.equals("") ) {
			corrigirExtensao(conn);
		}
		
		return extensao;
	}
	private void corrigirExtensao( Connection conn ) {
		try {
			String rootPath = SystemParameter.get(SenderInterface.SID, "systemProperties", "filePath");
			String filePath = rootPath + "upload/news/";
			
			for( String ext : AjaxUploadServlet.EXTENSION ) {
				File tmpFile = new File(filePath, this.getIdNoticia() + "." + ext );
				
				if( tmpFile.exists() ) {
					this.setExtensao( ext );
					this.update(conn);
					
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getTituloLimitado(int numeroDeCaracteres){
		String tituloLimitado = "";
		int ind = 0;
		while(ind < numeroDeCaracteres && ind < this.getTitulo().length()){
			tituloLimitado += this.getTitulo().charAt(ind);
			ind++;
		}
		return tituloLimitado + "...";
	}
	
	public String getImagem( Connection conn ) {
		String img = "/allgenda/upload/news/" + this.getIdNoticia() + "." + this.getExtensao(conn);
		
		if( img.endsWith(".") || img.endsWith("null") ) {
			
			if( contemImagem(conn) ) {
				return "/allgenda/image?idNoticia=" + this.getIdNoticia();
			} else {
				return "/allgenda/upload/news/blank.jpg";
			}
		} else {
			return img;
		}
	}
	
	public String getDescricaoReduzida() {
		return getDescricaoReduzida(30);
	}
	public String getDescricaoReduzida( int tamanho ) {
		try {
			String txt = this.getDescricao();
			txt = txt.replaceAll("<.*?>", "").trim();
			
			for( int crop = 0; crop <= tamanho;  crop++ ) {
				if( txt.charAt( txt.length()-1 ) == ' ') {
					break;
				}
				txt = txt.substring( 0, txt.length()-1 );
			}
			
			txt = txt.substring( 0, txt.length()-1 );
			txt += "...";
			
			return txt;
		} catch (Exception e) {
			return "";
		}
	}
	public String getDescricaoReduzida( int tamanho, String txt ) {
		try {
			for( int crop = 0; crop <= tamanho;  crop++ ) {
				if( txt.charAt( txt.length()-1 ) == ' ') {
					break;
				}
				txt = txt.substring( 0, txt.length()-1 );
			}
			
			txt = txt.substring( 0, txt.length()-1 );
			txt += "...";
			
			return txt;
		} catch (Exception e) {
			return "";
		}
	}
	
	public static void main(String[] args) {
		String txt = "Esse é um teste de notécia que foi cortad...";
		
		for( int crop = 0; crop <= 30;  crop++ ) {
			if( txt.charAt( txt.length()-1 ) == ' ') {
				break;
			}
			txt = txt.substring( 0, txt.length()-1 );
		}
		
		txt = txt.substring( 0, txt.length()-1 );
		txt += "...";
		System.out.println( txt );

	}
	
	public String getLinkAmigavel() {
		String str = this.getTitulo().replace('%', ' ').replace('&', ' ').replace('?', ' ').replaceAll(" ", "-");
		str = AbstractAdaptorMetaChar.replaceMetaChar_Simple(str);
		
		return str + "&id=" + this.getIdNoticia(); 
	}
}