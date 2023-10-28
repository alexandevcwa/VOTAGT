package com.votagt.model.dto;

import com.ormfile.orm.annotations.OrmFileColumn;
import com.ormfile.orm.annotations.OrmFileEntity;
import com.ormfile.orm.annotations.OrmFilePrimaryKey;
import com.ormfile.orm.annotations.OrmFileTable;
import com.ormfile.orm.enums.OrmFileDataTypesToString;

import java.util.List;

/**
 * @author alexandevcwa
 * @version 1.0.0
 * Clase de tipo Data Transfer Object para municipios
 */
@OrmFileEntity
@OrmFileTable
public class Municipio {

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
     * Departamento al que pertenece
     */
    @OrmFileColumn(dataType = OrmFileDataTypesToString.INT_STRING)
    private Departamento departamento;

    /**
     * Lista de votantes relacionados con el municipio
     */
    private List<Votante> votantes;

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

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public List<Votante> getVotantes() {
        return votantes;
    }

    public void setVotantes(List<Votante> votantes) {
        this.votantes = votantes;
    }
}
