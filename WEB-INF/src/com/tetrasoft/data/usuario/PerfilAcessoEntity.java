package com.tetrasoft.data.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.technique.engine.data.DataTypes;
import com.technique.engine.util.ExceptionInfo;
import com.technique.engine.util.ExceptionInjection;
import com.technique.engine.util.ExceptionWarning;
import com.technique.engine.web.UserSession;
import com.tetrasoft.common.data.BasePersistentEntity;
import com.tetrasoft.common.data.Listable;

public class PerfilAcessoEntity extends BasePersistentEntity implements Listable {

	private static Object[] structurePk = new Object[] {
		"IdPerfilAcesso","idPerfilAcesso", DataTypes.JAVA_LONG
	};
	private static Object[] structure = new Object[] {
		"Nome","nome", DataTypes.JAVA_STRING
	};

	private Long idPerfilAcesso = 0l;
	private String nome = "";

	public Long getIdPerfilAcesso() {
		return idPerfilAcesso;
	}

	public void setIdPerfilAcesso(Long idPerfilAcesso) {
		this.idPerfilAcesso = idPerfilAcesso;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Object[] getStructurePk() {
		return structurePk;
	}

	public Object[] getStructure() {
		return structure;
	}

	public String getTableName() {
		return "perfil_acesso";
	}

	public String get_IDFieldName() {
		return "idPerfilAcesso";
	}

	public String get_NomeFieldName() {
		return "nome";
	}

	public String get_SelecioneName() {
		return LABEL_SELECIONE;
	}

	public Connection retrieveConnection() throws ExceptionWarning {
		return getConnection();
	}

	public void save(UserSession session) throws ExceptionInfo, ExceptionWarning, ExceptionInjection {
		Connection conn=null;
		PreparedStatement ps = null;
		try {
			conn=getConnection();

			this.setIdPerfilAcesso( new Long( session.getAttributeString("id") ) );
			if( !this.getThis(conn) ) {
				this.setIdPerfilAcesso( getNextId() );
				this.setNome( session.getAttributeString("nome") );
				if (isNull(this.getNome())) throw new ExceptionInfo("Informe um nome para este perfil!");
				this.insert();
			}

			PerfilFuncionalidadeEntity pf = new PerfilFuncionalidadeEntity();
			ps = conn.prepareStatement("delete from "+pf.getTableName()+" where idPerfilAcesso = "+this.getIdPerfilAcesso());
			ps.execute();

			FuncionalidadeEntity f = new FuncionalidadeEntity();
			ArrayList a = f.getArray("1=1 ORDER BY idFuncionalidade ");
			for( int i = 0; i < a.size(); i++ ) {
				f = (FuncionalidadeEntity)a.get(i);

				pf.setIdFuncionalidade( f.getIdFuncionalidade() );
				pf.setIdPerfilAcesso( this.getIdPerfilAcesso() );

				if( session.getAttributeString("check_I_" + f.getIdFuncionalidade() ) == null ) {
					pf.setIncluir(0);
				} else {
					pf.setIncluir(1);
				}

				if( session.getAttributeString("check_A_" + f.getIdFuncionalidade() ) == null ) {
					pf.setAlterar(0);
				} else {
					pf.setAlterar(1);
				}

				if( session.getAttributeString("check_E_" + f.getIdFuncionalidade() ) == null ) {
					pf.setExcluir(0);
				} else {
					pf.setExcluir(1);
				}

				if( session.getAttributeString("check_C_" + f.getIdFuncionalidade() ) == null ) {
					pf.setConsultar(0);
				} else {
					pf.setConsultar(1);
				}

				pf.insert(conn);
			}
		} catch (Exception e) {
			if( e.toString().indexOf("Informe um nome para este perfil!") >= 0 )
				this.setIdPerfilAcesso( new Long(0) );

			throw new ExceptionInfo(e.getMessage());
		} finally {
			close(conn,ps);
		}
	}

	public boolean semPermissaoAcesso( int funcionalidade ) throws ExceptionWarning, ExceptionInjection {
		PerfilFuncionalidadeEntity pf = new PerfilFuncionalidadeEntity();
		if( pf.getThis("idPerfilAcesso = " + this.getIdPerfilAcesso() + " AND idFuncionalidade = " + funcionalidade + " AND (incluir = 1 OR alterar = 1 OR consultar = 1 OR excluir = 1)") ) {
			return false;
		} else {
			return true;
		}
	}

	public boolean semPermissaoAcesso( int funcionalidade, String tipo ) throws ExceptionWarning, ExceptionInjection {
		PerfilFuncionalidadeEntity pf = new PerfilFuncionalidadeEntity();
		if( pf.getThis("idPerfilAcesso = " + this.getIdPerfilAcesso() + " AND idFuncionalidade = " + funcionalidade + " AND " + tipo + " = 1 ") ) {
			return false;
		} else {
			return true;
		}
	}

}