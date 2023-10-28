package com.votagt.model.dto;

import com.ormfile.orm.annotations.*;
import com.ormfile.orm.enums.OrmFileDataTypesToString;

import java.util.ArrayList;

@OrmFileEntity
@OrmFileTable
public class EleccionDetalle {

    /**
     * Id del detalle de elección
     */
    @OrmFilePrimaryKey(isAutoIncrement = true)
    @OrmFileColumn(dataType = OrmFileDataTypesToString.INT_STRING)
    private int id;

    /**
     * Elección a la que pertenece el detalle
     */
    @OrmFileForeignKey
    @OrmFileColumn(dataType = OrmFileDataTypesToString.INT_STRING)
    private Eleccion eleccion;

    /**
     * Candidato relacionado con el detalle de la elección
     */
    @OrmFileForeignKey
    @OrmFileColumn(dataType = OrmFileDataTypesToString.INT_STRING)
    private Candidato candidato;

    /**
     * Lista de votos relacionados con el detalle
     */
    private ArrayList<Voto> votos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Eleccion getEleccion() {
        return eleccion;
    }

    public void setEleccion(Eleccion eleccion) {
        this.eleccion = eleccion;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public ArrayList<Voto> getVotos() {
        return votos;
    }

    public void setVotos(ArrayList<Voto> votos) {
        this.votos = votos;
    }
}
