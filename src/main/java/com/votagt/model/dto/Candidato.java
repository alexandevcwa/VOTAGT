package com.votagt.model.dto;

import com.ormfile.orm.annotations.OrmFileColumn;
import com.ormfile.orm.annotations.OrmFileEntity;
import com.ormfile.orm.annotations.OrmFilePrimaryKey;
import com.ormfile.orm.annotations.OrmFileTable;
import com.ormfile.orm.enums.OrmFileDataTypesToString;

import java.util.ArrayList;

/**
 * @author alexandevcwa
 * @version 1.0.0
 * Clase de tipo Data Transfer Object para almacenar candidatos
 */
@OrmFileEntity
@OrmFileTable
public class Candidato {
    /**
     * Id del candidato
     */
    @OrmFilePrimaryKey(isAutoIncrement = true)
    @OrmFileColumn(dataType = OrmFileDataTypesToString.INT_STRING)
    private int id;
    /**
     * Nombre del candidato
     */
    @OrmFileColumn(dataType = OrmFileDataTypesToString.STRING_STRING)
    private String nombre;
    /**
     * Formaciön academica del candidato
     */
    @OrmFileColumn(dataType = OrmFileDataTypesToString.STRING_STRING)
    private String formacion;
    /**
     * Experiencia del candidato
     */
    @OrmFileColumn(dataType = OrmFileDataTypesToString.STRING_STRING)
    private String experiencia;

    private ArrayList<EleccionDetalle> eleccionDetalles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFormacion() {
        return formacion;
    }

    public void setFormacion(String formacion) {
        this.formacion = formacion;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public ArrayList<EleccionDetalle> getEleccionDetalles() {
        return eleccionDetalles;
    }

    public void setEleccionDetalles(ArrayList<EleccionDetalle> eleccionDetalles) {
        this.eleccionDetalles = eleccionDetalles;
    }
}
