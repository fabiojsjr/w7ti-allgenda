package com.tetrasoft.data.cliente;

import java.sql.Connection;

import com.technique.engine.data.DataTypes;
import com.technique.engine.web.UserSession;
import com.tetrasoft.common.data.BasePersistentEntity;

public class MensagemTemplateEntity extends BasePersistentEntity {
	private static Object[] structurePk = new Object[] {
		"IdTemplate","idTemplate", DataTypes.JAVA_LONG,
	};
	private static Object[] structure = new Object[] {
		"Nome","nome", DataTypes.JAVA_STRING,
		"Template","template", DataTypes.JAVA_STRING,
	};

	private Long idTemplate;
	private String nome;
	private String template;

	public Object[] getStructurePk() {
		return structurePk;
	}

	public Object[] getStructure() {
		return structure;
	}

	public String getTableName() {
		return "mensagem_template";
	}

	public Long getIdTemplate() {
		return idTemplate;
	}

	public void setIdTemplate(Long idTemplate) {
		this.idTemplate = idTemplate;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public boolean salvar(long usuario, UserSession session, String value) {
		value = value.replaceAll("%20", " ");
		
		String assunto	 = value.substring( value.indexOf("ASSUNTO=")+8, value.indexOf("&", value.indexOf("ASSUNTO=") ) );
		String mensagem	 = value.substring( value.indexOf("MENSAGEM2=")+10, value.indexOf("&", value.indexOf("MENSAGEM2=") ) );
		String template	 = value.substring( value.indexOf("TEMPLATE=")+9, value.indexOf("&", value.indexOf("TEMPLATE=") ) );

		Connection conn = null;
		try {
			conn = getConnection();
			
			boolean novo = true;
			this.setIdTemplate( new Long(template) );
			if( this.getThis(conn) ) {
				novo = false;
			}
			
			this.setNome( assunto );
			this.setTemplate( mensagem );
			if( novo ) {
				this.insert(conn);
			} else {
				this.update(conn);
			}

			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (Exception e2) {
			}
		}
	}
} 