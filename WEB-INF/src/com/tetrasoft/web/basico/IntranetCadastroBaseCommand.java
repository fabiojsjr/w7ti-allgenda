package com.tetrasoft.web.basico;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;

import com.technique.engine.util.ExceptionAbstract;
import com.technique.engine.web.CommandWrapper;
import com.technique.engine.web.UserSession;
import com.technique.engine.xml.XMLData;
import com.tetrasoft.common.web.IntranetBaseCommand;



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
 * Este software pertence à FUTUWARE MULTIMEDIA.
 * A sua utilização é limitada aos termos de uso que acompanha este código.
 * Desenvolvido por FUTUWARE MULTIMEDIA - www.futuware.com.br
 *
 * This software is the proprietary information of FUTUWARE MULTIMEDIA.
 * Use is subject to license terms.
 * Developed by FUTUWARE MULTIMEDIA - www.futuware.com.br
 * </PRE>
 */

public abstract class IntranetCadastroBaseCommand extends IntranetBaseCommand {
	protected static String ACTION_FIRST_PAGE = "firstPage";
	protected static String ACTION_LAST_PAGE = "lastPage";
	protected static String ACTION_NEXT_PAGE = "nextPage";
	protected static String ACTION_PREV_PAGE = "prevPage";

	protected static String CLOSE_PAGE = "template/common/closePage.xsl";
	protected static String PAGE_AJAX_COMBO  = "ajax/combo.xsl";
	protected static String PAGE_AJAX_HIDDEN = "ajax/hidden.xsl";
	protected static String PAGE_AJAX_TEXT	 = "ajax/text.xsl";

	protected static SimpleDateFormat FORMAT_DATE  = new SimpleDateFormat("dd/MM/yyyy");
	protected static SimpleDateFormat FORMAT_DATE2  = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	protected static SimpleDateFormat FORMAT_DATE_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");

	private static String NAV_PROPERTIES = "navBarProperties";

	/** Creates a new instance of CadastroBaseCommand */
	public IntranetCadastroBaseCommand() {
	}

	public CommandWrapper execute(UserSession session, CadastroWrapper cadWrapper) throws ExceptionAbstract {
		CommandWrapper wrapper = new CommandWrapper(session.getSID());
		//XMLData data = wrapper.getXMLData();
		String tmp = session.getAttributeString("currentRow");
		if (tmp!=null && !"".equals(tmp)) cadWrapper.setCurrentRow(Long.parseLong(tmp));
		tmp = session.getAttributeString("totalRows");
		if (tmp!=null && !"".equals(tmp)) cadWrapper.setNumRows(Long.parseLong(tmp));
		return wrapper;
	}

	protected void fillNavAttributes(XMLData data, CadastroWrapper wrapper) {
		data.addParameterTag(NAV_PROPERTIES);
		data.addParameter("currentRow",new Long(wrapper.getCurrentRow()).toString());
		data.addParameter("currentPage",new Long(wrapper.getCurrentPage()).toString());
		data.addParameter("totalRows",new Long(wrapper.getNumRows()).toString());
		data.addParameter("totalPages",new Long(wrapper.getNumPages()).toString());
		data.addParameter("moveFirst",wrapper.moveFirstEnabled());
		data.addParameter("moveLast",wrapper.moveLastEnabled());
	}

    final public static boolean isEmptyNumber(Object str) {
        if (str==null) return true;
        if (str.toString().trim().equals("")) return true;
        if (str.toString().trim().equals("0")) return true;
        return false;
    }

	public String formatValor( double v ) {
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

	public String formatValor2( double v ) {
		try {
			Locale BRASIL = new Locale("pt","BR");
			NumberFormat nf = NumberFormat.getInstance(BRASIL);
			nf.setGroupingUsed(false);
			nf.setMaximumIntegerDigits(10);
			nf.setMinimumIntegerDigits(1);
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2);

			return nf.format( v );
		} catch (Exception e) {
			e.printStackTrace();
			return "0.00";
		}
	}

	public String formatValor( Double value ) {
		return formatValor( value.doubleValue() );
	}

	protected void getResolucao( UserSession session, XMLData data ) {
		String resolucao = session.getAttributeString("resolucao");
		String altura    = session.getAttributeString("altura");

		if( resolucao == null ) {
			resolucao = "800";
		} else {
			try {
				int res = Integer.parseInt(resolucao);
				if( res >= 1024 ) resolucao = "1024";
			} catch (Exception e) {
				resolucao = "800";
			}
			if( !resolucao.equals("1024") && !resolucao.equals("800") ) {
				resolucao = "800";
			}
		}

		try {
			if( Integer.parseInt(altura) < 460 )
				data.addClosedTag("altura", "baixa");
			else
				data.addClosedTag("altura", "alta");
		} catch (Exception e) {
			data.addClosedTag("altura", "alta");
		}
		data.addClosedTag("resolucao", resolucao);
		data.addClosedTag("alturaTotal", altura);
		data.addClosedTag("extra", resolucao.equals("800") ? "155" : "175");

		// System.out.println("resolucao = " + resolucao + " - " + altura);
	}

	protected String convertHashToken( Hashtable<String, String> hash ) {
		String s = "";

		Collection<String> col = hash.values();
		Iterator it = col.iterator();

		while( it.hasNext() ) {
			String s2 = (String)it.next();
			s+= s2 + ";";
		}
		return s;
	}

}