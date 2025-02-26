package com.intranet.util;

import com.tetrasoft.common.data.BasePersistentEntity;

public class ValidateUtils {

    private final ValidateType tipo; // Use ValidateType directly
    private final BasePersistentEntity tipoInstancia;
    private final Object value;
    private final String fieldName;
	@SuppressWarnings("unused")
    private final Long logado;

    // Constructor without logado
    public ValidateUtils(ValidateType tipo, BasePersistentEntity tipoInstancia, Object value, String fieldName) {
        this(tipo, tipoInstancia, value, fieldName, null); // Reuse constructor
    }

    // Constructor with logado
    public ValidateUtils(ValidateType tipo, BasePersistentEntity tipoInstancia, Object value, String fieldName, Long logado) {
        this.tipo = tipo;
        this.tipoInstancia = tipoInstancia;
        this.value = value;
        this.fieldName = fieldName;
        this.logado = logado;
    }

    // Validate method
    public boolean validate() throws Exception {
        if (tipo.equals(ValidateType.cpf_cnpj)) {
            BasePersistentEntity data = tipoInstancia;
            
           
				 var queryResult = data.executeQueryArray(
                        String.format("SELECT %s FROM %s WHERE %s = '%s'",
                                data.getStructurePk()[0], data.getTableName(), this.fieldName, value.toString()));
            
            return queryResult.isEmpty();
        }
        throw new IllegalArgumentException("Unsupported validation type: " + tipo); // More specific error handling
    }
}
