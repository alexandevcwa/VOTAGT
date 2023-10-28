package com.votagt.model.dto;

import com.ormfile.orm.annotations.OrmFileColumn;
import com.ormfile.orm.annotations.OrmFileEntity;
import com.ormfile.orm.annotations.OrmFilePrimaryKey;
import com.ormfile.orm.annotations.OrmFileTable;
import com.ormfile.orm.enums.OrmFileDataTypesToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@OrmFileEntity
@OrmFileTable
public class Eleccion {

    /**
     * Id de elección
     */
    @OrmFilePrimaryKey(isAutoIncrement = true)
    @OrmFileColumn(dataType = OrmFileDataTypesToString.INT_STRING)
    private int id;

    /**
     * Titulo de la elecciön
     */
    @OrmFileColumn(dataType = OrmFileDataTypesToString.STRING_STRING)
    private String titulo;

    /**
     * Proposito de la eleccion
     */
    @OrmFileColumn(dataType = OrmFileDataTypesToString.STRING_STRING)
    private String proposito;

    /**
     * Fecha de inicio de la elección
     */
    @OrmFileColumn(dataType = OrmFileDataTypesToString.LOCALDATE_STRING)
    private LocalDate fechaIni;

    /**
     * Fecha final de la elección
     */
    @OrmFileColumn(dataType = OrmFileDataTypesToString.LOCALDATE_STRING)
    private LocalDate fechaFin;

    /**
     * Fecha de inicio de inscripción de votantes
     */
    @OrmFileColumn(dataType = OrmFileDataTypesToString.LOCALDATETIME_STRING)
    private LocalDateTime fechaInsIni;

    /**
     * Fecha final de inscripción de votantes
     */
    @OrmFileColumn(dataType = OrmFileDataTypesToString.LOCALDATETIME_STRING)
    private LocalDateTime fechaInsFin;

    /**
     * Fecha de inicio de emisión de votos
     */
    @OrmFileColumn(dataType = OrmFileDataTypesToString.LOCALDATETIME_STRING)
    private LocalDateTime fechaVtoIni;

    /**
     * Fecha final de votantes
     */
    @OrmFileColumn(dataType = OrmFileDataTypesToString.LOCALDATETIME_STRING)
    private LocalDateTime fechaVtoFin;

    /**Detalle de elección*/
    private ArrayList<EleccionDetalle> eleccionesDetalle;
}
