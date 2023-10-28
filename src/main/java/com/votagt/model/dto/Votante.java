package com.votagt.model.dto;

import com.ormfile.orm.annotations.*;
import com.ormfile.orm.enums.OrmFileDataTypesToString;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Clase de tipo Data Transfer Objert para votantes
 *
 * @author alexandevcwa
 * @version 1.0.0
 */
@OrmFileEntity
@OrmFileTable
public class Votante {

    /**
     * CUI del votante
     */
    @OrmFilePrimaryKey
    @OrmFileColumn(dataType = OrmFileDataTypesToString.STRING_STRING)
    private String cui;

    /**
     * Sexo del votante
     */
    @OrmFileColumn(dataType = OrmFileDataTypesToString.BOOL_STRING)
    private boolean sexo;

    /**
     * Fecha de nacimiento del votante
     */
    @OrmFileColumn(dataType = OrmFileDataTypesToString.LOCALDATE_STRING)
    private LocalDate fechaNacimiento;

    /**
     * Dirección del votante
     */
    @OrmFileColumn(dataType = OrmFileDataTypesToString.STRING_STRING)
    private String direccion;

    /**
     * Municipio del votante
     */
    @OrmFileForeignKey()
    @OrmFileColumn(dataType = OrmFileDataTypesToString.INT_STRING)
    private Municipio municipio;

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

    //TODO: Falta la propiedad ROL que define que el votante pertenece a un rol del sistema

    private ArrayList<Voto> votos = new ArrayList<>();

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public boolean getSexo() {
        return sexo;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
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

    public ArrayList<Voto> getVotos() {
        return votos;
    }

    public void setVotos(ArrayList<Voto> votos) {
        this.votos = votos;
    }
}
