package com.intranet.util;

import java.util.List;

import com.technique.engine.util.ExceptionInjection;
import com.technique.engine.util.ExceptionWarning;
import com.tetrasoft.common.data.BasePersistentEntity;

public interface Autocomplete {
	List<BasePersistentEntity> getResultList(Long idUsuario,String key) throws ExceptionWarning, ExceptionInjection;
	String getJsonSearch(String key);
	Long getResultLength(Long idUsuario,String key);
	boolean validateUserAndKey(Long idUser,String key);
	String getQuerySearch(String key);
	String jsonArrayCleaner(String json);
	String getTableName();
}
