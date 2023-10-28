package com.votagt.model.dto;

import com.ormfile.orm.annotations.OrmFileColumn;
import com.ormfile.orm.annotations.OrmFileEntity;
import com.ormfile.orm.annotations.OrmFileForeignKey;
import com.ormfile.orm.annotations.OrmFileTable;
import com.ormfile.orm.enums.OrmFileDataTypesToString;

@OrmFileEntity
@OrmFileTable
public class UsuariosRoles {

    @OrmFileForeignKey
    @OrmFileColumn(dataType = OrmFileDataTypesToString.INT_STRING)
    private Usuario usuario;

    @OrmFileForeignKey
    @OrmFileColumn(dataType = OrmFileDataTypesToString.INT_STRING)
    private Rol rol;


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
