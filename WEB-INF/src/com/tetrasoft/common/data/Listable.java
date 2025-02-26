package com.tetrasoft.common.data;

public interface Listable {
    /** Listable Interface - retorna o nome do campo que representa o ID
     */
    public String get_IDFieldName();

    /** Listable Interface - retorna o nome do campo que representa o nome
     */
    public String get_NomeFieldName();

    public String getTableName();

    /** Listable Interface - String que representa o label quando existir a opçao "Selecione"
     */
    public String get_SelecioneName();
}
