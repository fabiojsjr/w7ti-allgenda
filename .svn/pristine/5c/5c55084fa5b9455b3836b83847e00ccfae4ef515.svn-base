package com.tetrasoft.data.basico;

import com.technique.engine.data.DataTypes;
import com.tetrasoft.common.data.BasePersistentEntity;

public class CidadeEntity extends BasePersistentEntity {

	private static Object[] structurePk = new Object[] {
		"Zip","zip", DataTypes.JAVA_INTEGER
	};
	private static Object[] structure = new Object[] {
		"Cidade","cidade", DataTypes.JAVA_STRING,
		"Estado","estado", DataTypes.JAVA_STRING,
		"Latitude","latitude", DataTypes.JAVA_DOUBLE,
		"Longitude","longitude", DataTypes.JAVA_DOUBLE,
		"Condado","condado", DataTypes.JAVA_STRING
	};

	public Object[] getStructurePk() {
		return structurePk;
	}

	public Object[] getStructure() {
		return structure;
	}

	public String getTableName() {
		return "cidade";
	}

	private Integer zip=null;
	private String  cidade=null;
	private String  estado=null;
	private Double  latitude=null;
	private Double  longitude=null;
	private String  condado=null;

	public Integer getZip() {
		return zip;
	}

	public void setZip(Integer zip) {
		this.zip = zip;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getCondado() {
		return condado;
	}

	public void setCondado(String condado) {
		this.condado = condado;
	}
}
