package com.votagt.model.dto;

import com.ormfile.orm.annotations.OrmFileColumn;
import com.ormfile.orm.annotations.OrmFileEntity;
import com.ormfile.orm.annotations.OrmFilePrimaryKey;
import com.ormfile.orm.annotations.OrmFileTable;
import com.ormfile.orm.enums.OrmFileDataTypesToString;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase de tipo Data Transfer Object para usuarios
 *
 * @author alexandevcwa
 * @version 1.0.0
 */
@OrmFileEntity
@OrmFileTable
public class Usuario {

    /**
     * Id del usuarios
     */
    @OrmFilePrimaryKey(isAutoIncrement = true)
    @OrmFileColumn(dataType = OrmFileDataTypesToString.INT_STRING)
    private int id;

    /**
     * Nombre
     */
    @OrmFileColumn(dataType = OrmFileDataTypesToString.STRING_STRING)
    public String nombre;

    /**
     * Apellido
     */
    @OrmFileColumn(dataType = OrmFileDataTypesToString.STRING_STRING)
    public String apellido;

    /**
     * Correo electrónico
     */
    @OrmFileColumn(dataType = OrmFileDataTypesToString.STRING_STRING, isUnique = true)
    public String email;

    /**
     * Contraseña
     */
    @OrmFileColumn(dataType = OrmFileDataTypesToString.STRING_STRING)
    public String password;

    /**
     * Estado, (Activo = True, Inactivo = False)
     */
    @OrmFileColumn(dataType = OrmFileDataTypesToString.BOOL_STRING)
    public boolean status;

    /**
     * Lista de roles relacionados con el usuarios
     */
    private ArrayList<UsuariosRoles> usuariosRoles = new ArrayList<>();

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<UsuariosRoles> getUsuariosRoles() {
        return usuariosRoles;
    }

    public void setUsuariosRoles(ArrayList<UsuariosRoles> usuariosRoles) {
        this.usuariosRoles = usuariosRoles;
    }
}
