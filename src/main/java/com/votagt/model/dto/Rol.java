package com.votagt.model.dto;

import com.ormfile.orm.annotations.OrmFileColumn;
import com.ormfile.orm.annotations.OrmFileEntity;
import com.ormfile.orm.annotations.OrmFilePrimaryKey;
import com.ormfile.orm.annotations.OrmFileTable;
import com.ormfile.orm.enums.OrmFileDataTypesToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase de tipo Data Transfer Object para roles de usuarios
 *
 * @author alexandevcwa
 * @version 1.0.0
 */
@OrmFileEntity
@OrmFileTable
public class Rol {
    /**
     * Id que identifica al Rol
     */
    @OrmFilePrimaryKey(isAutoIncrement = true)
    @OrmFileColumn(dataType = OrmFileDataTypesToString.INT_STRING)
    private int id;

    /**
     * Nombre del Rol
     */
    @OrmFileColumn(dataType = OrmFileDataTypesToString.STRING_STRING, isUnique = true)
    private String rolName;

    /**
     * Lista de usuarios relacionados con el Id del Rol
     */
    private List<UsuariosRoles> usuariosRoles;

    /**
     * Lista de votantes relacionados el rol
     */
    private ArrayList<Votante> votantes;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }

    public List<UsuariosRoles> getUsuariosRoles() {
        return usuariosRoles;
    }

    public void setUsuariosRoles(List<UsuariosRoles> usuariosRoles) {
        this.usuariosRoles = usuariosRoles;
    }

    public ArrayList<Votante> getVotantes() {
        return votantes;
    }

    public void setVotantes(ArrayList<Votante> votantes) {
        this.votantes = votantes;
    }
}
