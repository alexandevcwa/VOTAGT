package com.votagt.model;

import com.ormfile.exeption.OrmFileDbSetWarning;
import com.ormfile.orm.OrmFileDbContext;
import com.ormfile.orm.OrmFileDbSet;
import com.votagt.model.dto.*;

import java.util.ArrayList;
import java.util.logging.Logger;

public class DatabaseContext extends OrmFileDbContext {

    private static final Logger logger = Logger.getLogger(DatabaseContext.class.getName());

    public DatabaseContext() {
        super(() -> {
            ArrayList<OrmFileDbSet> dbSets = new ArrayList<>();
            try {
                dbSets.add(new OrmFileDbSet(Candidato.class));
                dbSets.add(new OrmFileDbSet(Departamento.class));
                dbSets.add(new OrmFileDbSet(Eleccion.class));
                dbSets.add(new OrmFileDbSet(EleccionDetalle.class));
                dbSets.add(new OrmFileDbSet(Municipio.class));
                dbSets.add(new OrmFileDbSet(Pais.class));
                dbSets.add(new OrmFileDbSet(Rol.class));
                dbSets.add(new OrmFileDbSet(Usuario.class));
                dbSets.add(new OrmFileDbSet(UsuariosRoles.class));
                dbSets.add(new OrmFileDbSet(Votante.class));
                dbSets.add(new OrmFileDbSet(Voto.class));
            } catch (OrmFileDbSetWarning e) {
                logger.warning(e.getMessage());
            }
            return dbSets;
        });
    }
}
