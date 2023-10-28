package com.votagt.model.dto;

import com.ormfile.orm.annotations.OrmFileColumn;
import com.ormfile.orm.annotations.OrmFileEntity;
import com.ormfile.orm.annotations.OrmFilePrimaryKey;
import com.ormfile.orm.annotations.OrmFileTable;
import com.ormfile.orm.enums.OrmFileDataTypesToString;

import java.util.List;

@OrmFileEntity
@OrmFileTable
public class Pais {

    /**
     * Id de la localidad
     */
    @OrmFilePrimaryKey(isAutoIncrement = true)
    @OrmFileColumn(dataType = OrmFileDataTypesToString.INT_STRING)
    protected int id;

    /**
     * Nombre de la localidad
     */
    @OrmFileColumn(dataType = OrmFileDataTypesToString.STRING_STRING, isUnique = true)
    protected String descripcion;

    /**
     * Lista de paises relacionados con el pais
     */
    protected List<Departamento> departamentos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }
}
