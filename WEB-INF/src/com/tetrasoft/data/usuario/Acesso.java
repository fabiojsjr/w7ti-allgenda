package com.tetrasoft.data.usuario;

public abstract class Acesso {
	

	public static final int DESENVOLVEDOR = 1;
	public static final int ADMIN = 2;
	public static final int OPERADOR = 3;
	public static final int PACIENTE = 4;

	/**
	 * Caso alguma classe implemente
	 * @return id generico da classe
	 */
	public abstract int getIdFuncionalidade();

}
