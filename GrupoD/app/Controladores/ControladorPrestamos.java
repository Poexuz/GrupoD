package app.Controladores;

import app.Servicios.ServicioBiblioteca;
import framework.Controlador;
import framework.Rol;
import framework.Solicitud;
import framework.Validadores;
import java.util.Map;
import java.util.function.Function;

public class ControladorPrestamos implements Controlador {
    private final ServicioBiblioteca biblioteca;

    public ControladorPrestamos(ServicioBiblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    @Override
    public String recurso() {
        return "prestamos";
    }

    @Override
    public Map<String, Function<Solicitud, String>> acciones() {
        return Map.of(
                "pedir", this::pedir,
                "devolver", this::devolver,
                "listar", s -> biblioteca.listarPrestamos()
        );
    }

    @Override
    public Map<String, Rol> permisos() {
        return Map.of(
                "pedir", Rol.USUARIO,
                "devolver", Rol.USUARIO,
                "listar", Rol.USUARIO
        );
    }

    private String pedir(Solicitud s) {
        String idLibro = s.parametro(0, "idLibro");
        Validadores.largoMaximo(20).validar(idLibro, "idLibro");
        String usuario = s.sesion().usuario().orElseThrow();
        return biblioteca.pedirPrestamo(idLibro, usuario);
    }

    private String devolver(Solicitud s) {
        String idLibro = s.parametro(0, "idLibro");
        Validadores.largoMaximo(20).validar(idLibro, "idLibro");
        String usuario = s.sesion().usuario().orElseThrow();
        return biblioteca.devolverPrestamo(idLibro, usuario);
    }
}