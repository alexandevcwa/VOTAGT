package com.votagt;

import com.ormfile.exeption.OrmFileDbSetException;
import com.ormfile.orm.OrmFileRecord;
import com.votagt.model.DatabaseContext;
import com.votagt.model.dto.*;
import java.io.Console;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    /**
     * Context para obtener, insertar y crear base de datos basada en archivos
     */
    private static DatabaseContext databaseContext = new DatabaseContext();
    /**
     * Scanner para ontemer datos del usuario
     */
    private static Scanner scanner = new Scanner(System.in);
    /**
     * roles permitidos para que el usuario opere en el sistema
     */
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

    /**
     * Muestra banner el software
     */
    private static void banner() {
        System.out.println(" █████   █████    ███████    ███████████   █████████     █████████  ███████████\n" +
                "░░███   ░░███   ███░░░░░███ ░█░░░███░░░█  ███░░░░░███   ███░░░░░███░█░░░███░░░█\n" +
                " ░███    ░███  ███     ░░███░   ░███  ░  ░███    ░███  ███     ░░░ ░   ░███  ░ \n" +
                " ░███    ░███ ░███      ░███    ░███     ░███████████ ░███             ░███    \n" +
                " ░░███   ███  ░███      ░███    ░███     ░███░░░░░███ ░███    █████    ░███    \n" +
                "  ░░░█████░   ░░███     ███     ░███     ░███    ░███ ░░███  ░░███     ░███    \n" +
                "    ░░███      ░░░███████░      █████    █████   █████ ░░█████████     █████   \n" +
                "     ░░░         ░░░░░░░       ░░░░░    ░░░░░   ░░░░░   ░░░░░░░░░     ░░░░░    \n" +
                "                                                                               ");
    }

    /**
     * Verificar si el el primer arranque el sistema
     *
     * @return True si es el primer arranque del sistema, deja agregar valores para el usuario administrador
     * del sistema y ina contraseña, luego de esto siempre requerira credenciales
     */
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

        int tipoInicioSesion;
        try {
            tipoInicioSesion = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("[ERROR] " + e.getMessage().toUpperCase());
            return;
        }

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
                displayMenu();
            } else {
                System.out.println("[ERROR] EL USUARIO NO TIENE PERMISOS PARA ACCEDER AL SITEMA");
            }
        } catch (OrmFileDbSetException | NoSuchFieldException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Desplegar menu del sistema
     */
    private static void displayMenu() {
        int option = -1;

        while (option != 99) {
            System.out.println("• ▌ ▄ ·. ▄▄▄ . ▐ ▄ ▄• ▄▌\n" +
                    "·██ ▐███▪▀▄.▀·•█▌▐██▪██▌\n" +
                    "▐█ ▌▐▌▐█·▐▀▀▪▄▐█▐▐▌█▌▐█▌\n" +
                    "██ ██▌▐█▌▐█▄▄▌██▐█▌▐█▄█▌\n" +
                    "▀▀  █▪▀▀▀ ▀▀▀ ▀▀ █▪ ▀▀▀ ");
            System.out.println("[MENU] INGRESA UN APARTADO DEL SISTEMA QUE DESEAS INGRESAR");

            System.out.println("[1] ADMINISTRAR USUARIOSN\n" +
                    "[2] CONTEO DE VOTOS\n" +
                    "[3] EMISIÓN DE VOTO\n" +
                    "[4] GESTION DE CANDIDATOS\n" +
                    "[5] GESTION DE ELECCIONES\n" +
                    "[6] ADMINISTRAR VOTANTES\n" +
                    "[99] SALIR");

            option = scanner.nextInt();
            switch (option) {
                case 1:
                    if (roles.contains(0)) {
                        //TODO logica para administrar usuarios
                        managerUsers();
                    } else {
                        System.out.println("[ALERTA] NO TIENES LOS PERMISOS SUFICIENTES PARA ACCEDER A ESTA OPTIÓN");
                    }
                    break;
                case 6:
                    if (roles.contains(0) || roles.contains(2)) {
                        managerVoters();
                    } else {
                        System.out.println("[ALERTA] NO TIENES LOS PERMISOS SUFICIENTES PARA ACCEDER A ESTA OPTIÓN");
                    }
                    break;
                default:
                    if (option != 99) {
                        System.out.println("[ERROR] OPCIÓN INGRESADA ES INVALIDA");
                    }
                    break;
            }
        }
    }

    /**
     * Administrar usuarios del sistema
     */
    private static void managerUsers() {
        int option = -1;
        while (option != 99) {

            System.out.println(" ▄▄▄· ·▄▄▄▄  • ▌ ▄ ·. ▪   ▐ ▄ ▪  .▄▄ · ▄▄▄▄▄▄▄▄   ▄▄▄· ▄▄▄      ▄• ▄▌.▄▄ · ▄• ▄▌ ▄▄▄· ▄▄▄  ▪        .▄▄ · \n" +
                    "▐█ ▀█ ██▪ ██ ·██ ▐███▪██ •█▌▐███ ▐█ ▀. •██  ▀▄ █·▐█ ▀█ ▀▄ █·    █▪██▌▐█ ▀. █▪██▌▐█ ▀█ ▀▄ █·██ ▪     ▐█ ▀. \n" +
                    "▄█▀▀█ ▐█· ▐█▌▐█ ▌▐▌▐█·▐█·▐█▐▐▌▐█·▄▀▀▀█▄ ▐█.▪▐▀▀▄ ▄█▀▀█ ▐▀▀▄     █▌▐█▌▄▀▀▀█▄█▌▐█▌▄█▀▀█ ▐▀▀▄ ▐█· ▄█▀▄ ▄▀▀▀█▄\n" +
                    "▐█ ▪▐▌██. ██ ██ ██▌▐█▌▐█▌██▐█▌▐█▌▐█▄▪▐█ ▐█▌·▐█•█▌▐█ ▪▐▌▐█•█▌    ▐█▄█▌▐█▄▪▐█▐█▄█▌▐█ ▪▐▌▐█•█▌▐█▌▐█▌.▐▌▐█▄▪▐█\n" +
                    " ▀  ▀ ▀▀▀▀▀• ▀▀  █▪▀▀▀▀▀▀▀▀ █▪▀▀▀ ▀▀▀▀  ▀▀▀ .▀  ▀ ▀  ▀ .▀  ▀     ▀▀▀  ▀▀▀▀  ▀▀▀  ▀  ▀ .▀  ▀▀▀▀ ▀█▄▀▪ ▀▀▀▀ ");
            System.out.println("[MENU] SELECCIONA EL APARTADO QUE AL QUE DESEAS INGRESAR");
            System.out.println("[1] AGREGAR NUEVO USUARIO\n" +
                    "[2] DESHABILITAR USUARIO\n" +
                    "[3] EDUTAR USUARIO\n" +
                    "[99] SALIR");

            option = scanner.nextInt();
            switch (option) {
                case 1:
                    addNewUser();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Registrar nuevo usuario en el sistema
     */
    private static void addNewUser() {
        Usuario usuario = new Usuario();
        System.out.println("[USUARIO] INGRESA PRIMER NOMBRE");
        usuario.setNombre(scanner.next());
        System.out.println("[USUARIO] INGRESA PRIMER APELLIDO");
        usuario.setApellido(scanner.next());
        System.out.println("[USUARIO] INGRESA CORREO ELECTRONICO");
        usuario.setEmail(scanner.next());
        System.out.println("[USUARIO] INGRESA UNA CONTRASEÑA");
        Console console = System.console();
        if (console != null) {
            char[] passwordArray = console.readPassword("[PASSWORD] : ");
            String password = new String(passwordArray);
            usuario.setPassword(password);
        } else {
            usuario.setPassword(scanner.next());
        }
        usuario.setStatus(true);

        try {
            if (databaseContext.insert(new OrmFileRecord<Usuario>(usuario)).save(Usuario.class)) {
                System.out.println("[DB] USUARIO GUARDADO CORRECTAMENTE");
            }
        } catch (IllegalAccessException | IOException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedOperationException e) {
            System.out.println("[ERROR FROM DB] " + e.getMessage().toUpperCase());
        }

        //ASIGNARLE ROLES AL USARIO
        while (!setUserRoles(usuario)) {
            System.out.println("[ROLES] VUELVE A INTENTAR ASIGNARLE ROLES AL USUARIO");
        }
    }

    /**
     * Registro de roles para el un usuario nuevo
     *
     * @param usuario Usuario registrado a quien se le van a asignar roles
     * @see Usuario
     */
    private static boolean setUserRoles(Usuario usuario) {
        System.out.println("[USUARIO ROLES] ASIGNALE ROLES AL USUARIO AGREGADO\n" +
                "0 - ADMINISTRADOR\n" +
                "1 - AUDITOR\n" +
                "2 - REGISTRADOR\n" +
                "3 - VOTANTE\n" +
                "[INFO] ASINGALE ROLES SEPARADOS POR COMAS, EJEMPLO -> 1,2,3,4");
        String roles = scanner.next();
        String[] rolesArray = roles.split(",", 4);
        for (String rol : rolesArray) {
            char[] rolCharArray = rol.toCharArray();
            if (rolCharArray.length > 1) {
                System.out.println("[ALERTA] FORMATO DE ASIGNACIÓN DE ROLES INCORRECTO");
                return false;
            }
            if (rolCharArray.length == 1 && rolCharArray[0] >= 48 && rolCharArray[0] <= 52) {

                UsuariosRoles usuariosRoles = new UsuariosRoles();
                usuariosRoles.setUsuario(usuario);

                Rol usrRol = new Rol();
                usrRol.setId(Integer.parseInt(String.valueOf(rolCharArray[0])));
                usuariosRoles.setRol(usrRol);

                try {
                    if (databaseContext.insert(new OrmFileRecord<UsuariosRoles>(usuariosRoles)).save(UsuariosRoles.class)) {
                        System.out.println("[DB] ROLL [" + String.valueOf(rolCharArray[0]) + "] ASIGNADO CORRECTAMENTE");
                    }
                } catch (IllegalAccessException | IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("[ALERTA] FORMATO DE ASIGNACIÓN DE ROLES INCORRECTO");
                return false;
            }
        }
        return true;
    }

    private static void managerVoters() {
        int option = -1;
        while (option != 99) {

            System.out.println(" ▄▄▄· ·▄▄▄▄  • ▌ ▄ ·. ▪   ▐ ▄ ▪  .▄▄ · ▄▄▄▄▄▄▄▄   ▄▄▄· ▄▄▄       ▌ ▐·      ▄▄▄▄▄ ▄▄▄·  ▐ ▄ ▄▄▄▄▄▄▄▄ ..▄▄ · \n" +
                    "▐█ ▀█ ██▪ ██ ·██ ▐███▪██ •█▌▐███ ▐█ ▀. •██  ▀▄ █·▐█ ▀█ ▀▄ █·    ▪█·█▌▪     •██  ▐█ ▀█ •█▌▐█•██  ▀▄.▀·▐█ ▀. \n" +
                    "▄█▀▀█ ▐█· ▐█▌▐█ ▌▐▌▐█·▐█·▐█▐▐▌▐█·▄▀▀▀█▄ ▐█.▪▐▀▀▄ ▄█▀▀█ ▐▀▀▄     ▐█▐█• ▄█▀▄  ▐█.▪▄█▀▀█ ▐█▐▐▌ ▐█.▪▐▀▀▪▄▄▀▀▀█▄\n" +
                    "▐█ ▪▐▌██. ██ ██ ██▌▐█▌▐█▌██▐█▌▐█▌▐█▄▪▐█ ▐█▌·▐█•█▌▐█ ▪▐▌▐█•█▌     ███ ▐█▌.▐▌ ▐█▌·▐█ ▪▐▌██▐█▌ ▐█▌·▐█▄▄▌▐█▄▪▐█\n" +
                    " ▀  ▀ ▀▀▀▀▀• ▀▀  █▪▀▀▀▀▀▀▀▀ █▪▀▀▀ ▀▀▀▀  ▀▀▀ .▀  ▀ ▀  ▀ .▀  ▀    . ▀   ▀█▄▀▪ ▀▀▀  ▀  ▀ ▀▀ █▪ ▀▀▀  ▀▀▀  ▀▀▀▀ ");
            System.out.println("[MENU] SELECCIONA EL APARTADO QUE AL QUE DESEAS INGRESAR");
            System.out.println("[1] AGREGAR NUEVO VOTANTE\n" +
                    "[2] DESHABILITAR POR FALLECIMIENTO\n" +
                    "[3] EDITAR DATOS DE VOTANTE\n" +
                    "[99] SALIR");

            option = scanner.nextInt();
            switch (option) {
                case 1:
                    addNewVoter();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    break;
            }
        }
    }

    private static void addNewVoter() {
        Votante votante = new Votante();

        System.out.println("[VOTANTE] INGRESE NO. DE CUI");
        votante.setCui(scanner.next());

        System.out.println("[VOTANTE] TIPO DE SEXO (M,F)");
        String sexo = scanner.next();
        if (sexo.equals("M")) {
            votante.setSexo(true);
        } else if (sexo.equals("F")) {
            votante.setSexo(false);
        }

        System.out.println("[VOTANTE] INGRESA FECHA DE NACIMIENTO CON EL SIGUIENTE PATRÓN DD/MM/AAAA");
        String fechaNacimiento = scanner.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaLocal = LocalDate.parse(fechaNacimiento, formatter);
        votante.setFechaNacimiento(fechaLocal);

        System.out.println("[USUARIO] INGRESA UNA DIRECCION");
        votante.setDireccion(scanner.next());

        System.out.println("[USUARIO] SELECCIONA EL PAIS AL QUE PERTENECES");

        try {
            databaseContext.select(Pais.class).getAllRecords().forEach(pais -> {
                System.out.println(pais[0] + " - " + pais[1]);
            });

            int value = scanner.nextInt();

            System.out.println("[USUARIO] SELECCIONA EL DEPARTAMENTO AL QUE PERTENECES");
            databaseContext.select(Departamento.class).getRecordByColumn(Departamento.class.getDeclaredField("pais"))
                    .where(value).forEach(departamento -> {
                        System.out.println(departamento[0] + " - " + departamento[1]);
                    });
            value = scanner.nextInt();

            System.out.println("[USUARIO] SELECCIONA EL MUNICIPIO AL QUE PERTENECES");
            databaseContext.select(Municipio.class).getRecordByColumn(Municipio.class.getDeclaredField("departamento"))
                    .where(value).forEach(municpio -> {
                        System.out.println(municpio[0] + " - " + municpio[1]);
                    });
            value = scanner.nextInt();
            Municipio municipio = new Municipio();
            municipio.setId(value);
            votante.setMunicipio(municipio);

        } catch (IOException | OrmFileDbSetException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        System.out.println("[USUARIO] INGRESA PRIMER NOMBRE");
        votante.setNombre(scanner.next());
        System.out.println("[USUARIO] INGRESA PRIMER APELLIDO");
        votante.setApellido(scanner.next());
        System.out.println("[USUARIO] INGRESA CORREO ELECTRONICO");
        votante.setEmail(scanner.next());
        System.out.println("[USUARIO] INGRESA UNA CONTRASEÑA");
        votante.setPassword(scanner.next());
        votante.setStatus(true);

        try {
            databaseContext.insert(new OrmFileRecord<Votante>(votante)).save(Votante.class);
            System.out.println("[DB] VOTANTE REGISTRADO CORRECTAMENTE");
        } catch (IllegalAccessException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}