package com.tetrasoft.data.cliente;

import java.util.ArrayList;
import java.util.HashMap;

import com.technique.engine.data.DataTypes;
import com.tetrasoft.common.data.BasePersistentEntity;

@SuppressWarnings("rawtypes")
public class ConfigEntity extends BasePersistentEntity {
    private static Object[] structurePk = new Object[] {
    	"Campo","campo", DataTypes.JAVA_STRING,
    };
    private static Object[] structure = new Object[] {
    	"Config","config", DataTypes.JAVA_STRING,
    };
    
    private String campo;
    private String config;
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
	}

	@Override
	public Object[] getStructure() {
		return structure;
	}
	@Override
	public Object[] getStructurePk() {
		return structurePk;
	}
	@Override
	public String getTableName() {
		return "config";
	}
	
	public static HashMap<String, String> CONFIG = new HashMap<String, String>();
	static {
		try {
			ConfigEntity c = new ConfigEntity();
			ArrayList    a = c.getAllArray();
			for (int i = 0; i < a.size(); i++) {
				c = (ConfigEntity)a.get(i);
				CONFIG.put( c.getCampo(), c.getConfig() );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}