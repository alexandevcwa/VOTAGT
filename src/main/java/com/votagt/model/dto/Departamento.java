package com.votagt.model.dto;

import com.ormfile.orm.annotations.*;
import com.ormfile.orm.enums.OrmFileDataTypesToString;

import java.util.List;

@OrmFileEntity
@OrmFileTable
public class Departamento {

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
     * Pais al que pertenece el departamento
     */
    @OrmFileForeignKey
    @OrmFileColumn(dataType = OrmFileDataTypesToString.INT_STRING)
    private Pais pais;

    /**
     * Lista de municipios que contiene el departamento
     */
    private List<Municipio> municipios;

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

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }
}
