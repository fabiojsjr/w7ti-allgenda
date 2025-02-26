package com.tetrasoft.common;

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
 * <B>�FUTUWARE MULTIMEDIA - Copyright 2003.  <I>All Rights Reserved.</I></B>
 * Este software pertence � FUTUWARE MULTIMEDIA.
 * A sua utiliza��o � limitada aos termos de uso que acompanha este c�digo.
 * Desenvolvido por FUTUWARE MULTIMEDIA - www.futuware.com.br
 *
 * This software is the proprietary information of FUTUWARE MULTIMEDIA.
 * Use is subject to license terms.
 * Developed by FUTUWARE MULTIMEDIA - www.futuware.com.br
 * </PRE>
 */

public class DateTimeUtil {
    public static String diasSemana[] = {
        "Domingo",
        "Segunda",
        "Ter�a",
        "Quarta",
        "Quinta",
        "Sexta",
        "S�bado"
    };

    public static String meses[] = {
        "Janeiro",
        "Fevereiro",
        "Mar�o",
        "Abril",
        "Maio",
        "Junho",
        "Julho",
        "Agosto",
        "Setembro",
        "Outubro",
        "Novembro",
        "Dezembro"
    };
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    public String getMes( int index ) {
        return meses[index];
    }
    // -------------------------------------------------------------------------
    public String getDiaSemana( int index ) {
        return diasSemana[index];
    }
    // -------------------------------------------------------------------------
}
