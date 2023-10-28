package com.votagt.model.dto;

import com.ormfile.orm.annotations.*;
import com.ormfile.orm.enums.OrmFileDataTypesToString;

import java.util.ArrayList;

@OrmFileEntity
@OrmFileTable
public class Voto {

    /**
     * Id de voto
     */
    @OrmFilePrimaryKey(isAutoIncrement = true)
    @OrmFileColumn(dataType = OrmFileDataTypesToString.INT_STRING)
    private int id;

    /**
     * Id de elecciones detalle
     */
    @OrmFileForeignKey
    @OrmFileColumn(dataType = OrmFileDataTypesToString.INT_STRING)
    private EleccionDetalle eleccionDetalle;

    /**
     * Id de votante
     */
    @OrmFilePrimaryKey
    @OrmFileColumn(dataType = OrmFileDataTypesToString.INT_STRING)
    private Votante votante;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EleccionDetalle getEleccionDetalle() {
        return eleccionDetalle;
    }

    public void setEleccionDetalle(EleccionDetalle eleccionDetalle) {
        this.eleccionDetalle = eleccionDetalle;
    }

    public Votante getVotante() {
        return votante;
    }

    public void setVotante(Votante votante) {
        this.votante = votante;
    }
}
