package com.votagt;

import com.ormfile.exeption.OrmFileDbSetException;
import com.ormfile.orm.OrmFileRecord;
import com.sun.jdi.PrimitiveValue;
import com.votagt.model.DatabaseContext;
import com.votagt.model.dto.Rol;
import com.votagt.model.dto.Usuario;
import com.votagt.model.dto.UsuariosRoles;
import com.votagt.model.dto.Votante;

import javax.lang.model.util.ElementScanner6;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Main {

    private static DatabaseContext databaseContext = new DatabaseContext();
    private static Scanner scanner = new Scanner(System.in);

    private static ArrayList<Integer> roles = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        banner();
        long arg = Arrays.stream(args).count();
        if (arg == 0) {
            firstStartUp("null");
            login();
        } else {
            firstStartUp(args[0]);
        }
    }

    private static void banner() {
        System.out.println("____    ____   ______   .___________.     ___        _______ .___________.\n" +
                "\\   \\  /   /  /  __  \\  |           |    /   \\      /  _____||           |\n" +
                " \\   \\/   /  |  |  |  | `---|  |----`   /  ^  \\    |  |  __  `---|  |----`\n" +
                "  \\      /   |  |  |  |     |  |       /  /_\\  \\   |  | |_ |     |  |     \n" +
                "   \\    /    |  `--'  |     |  |      /  _____  \\  |  |__| |     |  |     \n" +
                "    \\__/      \\______/      |__|     /__/     \\__\\  \\______|     |__|     \n" +
                "                                                                          ");
    }

    private static boolean firstStartUp(String buildTag) throws IOException {

        if (buildTag.equals("-db")) {
            databaseContext.database().create();
        }

        try {
            int totalUsers = databaseContext.select(Usuario.class).getAllRecords().size();
            if (totalUsers == 1) {

                Usuario usr = new Usuario();
                usr.setId(0);
                usr.setNombre("ADMIN");
                usr.setApellido("ADMIN");
                usr.setEmail("ADMIN");

                System.out.println("INGRESE UNA CONTRASEÑA PARA CONFIGURAR EL USUARIO ADMINISTRADOR");
                usr.setPassword(scanner.nextLine());
                usr.setStatus(true);

                Rol rol = new Rol();
                rol.setId(0);

                UsuariosRoles roles = new UsuariosRoles();
                roles.setUsuario(usr);
                roles.setRol(rol);

                OrmFileRecord<Usuario> record_usuario = new OrmFileRecord<>(usr);
                databaseContext.insert(record_usuario).save(Usuario.class);

                OrmFileRecord<UsuariosRoles> record_rol = new OrmFileRecord<>(roles);
                databaseContext.insert(record_rol).save(UsuariosRoles.class);
                return true;
            } else {
                return false;
            }
        } catch (IOException | OrmFileDbSetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inicio se sesión para usuario o votante
     */
    private static void login() {
        System.out.println("\n[1] INICIO DE SESESIÓN COMO USUARIO");
        System.out.println("[2] INICIO DE SESION COMO VOTANTE");
        int tipoInicioSesion = scanner.nextInt();
        System.out.println("[INFO] INGRESE SU CORREO ELECTRONICO");
        String value = scanner.next();

        Class<?> userType = null;
        if (tipoInicioSesion == 1) {
            userType = Usuario.class;
        } else if (tipoInicioSesion == 2) {
            userType = Votante.class;
        } else {
            return;
        }

        try {
            Optional<String[]> result = databaseContext.select(userType)
                    .getRecordByColumn(userType.getDeclaredField("email"))
                    .where(value)
                    .stream()
                    .findFirst();
            if (result.isEmpty()) {
                System.out.println("[ERROR] EL CORREO INGRESADO NO ESTA ASOCIADO A NINGUNA CUENTA DEL SISTEMA");
            } else {
                System.out.println("[INFO] INGRESE CONTRASEÑA");
                value = scanner.next();
                if (userType.equals(Usuario.class) && (value.equals(result.get()[4]))) {
                    //TODO: OBTENER LOS PERMISOS A LAS PANTALLAS DE USUARIO
                    obtaingGrants(result.get()[0]);
                } else if ((userType.equals(Votante.class)) && (value.equals(result.get()[8]))) {
                    System.out.println("[INFO] INGRESA TU NÚMERO DE CUI PARA VERIFICAR TU IDENTIDAD");
                    value = scanner.next();
                    if (value.equals(result.get()[0])) {
                        roles.clear();
                        roles.add(3);         //Definir el array de roles en 1 unidad
                    }
                } else {
                    System.out.println("[ERROR] CONTRASEÑA INCORRECTA");
                    return;
                }
            }

        } catch (IOException | OrmFileDbSetException | NoSuchFieldException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Obtener los roles permitidos del usuario para acceder a areas del sistema
     *
     * @param userId ID de usuario que inicio sesión
     */
    private static void obtaingGrants(String userId) {
        try {
            var result = databaseContext.select(UsuariosRoles.class).getRecordByColumn(UsuariosRoles.class.getDeclaredField("usuario")).where(userId);
            if (result != null) {
                roles.clear();
                result.forEach(grant -> {
                    roles.add(Integer.parseInt(grant[1]));
                });
            } else {
                System.out.println("[ERROR] EL USUARIO NO TIENE PERMISOS PARA ACCEDER AL SITEMA");
            }
        } catch (OrmFileDbSetException | NoSuchFieldException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void displayMenu() {

    }
}