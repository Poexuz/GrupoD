package app.Controladores;

import app.Servicios.ServicioBiblioteca;
import framework.Controlador;
import framework.Rol;
import framework.Solicitud;
import framework.Validadores;
import java.util.Map;
import java.util.function.Function;


public class ControladorLibros implements Controlador {
    private final ServicioBiblioteca biblioteca;

    public ControladorLibros(ServicioBiblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    @Override
    public String recurso() {
        return "libros";
    }

    @Override
    public Map<String, Function<Solicitud, String>> acciones() {
        return Map.of(
                "alta", this::alta,
                "listar", s -> biblioteca.listarLibros(),
                "buscar", this::buscar
        );
    }

    @Override
    public Map<String, Rol> permisos() {
        return Map.of(
                "alta", Rol.ADMIN,
                "listar", Rol.USUARIO,
                "buscar", Rol.USUARIO
        );
    }

    private String alta(Solicitud s) {
        String id = s.parametro(0, "id");
        String titulo = s.parametro(1, "titulo");
        String copiasStr = s.parametro(2, "copias");

        Validadores.largoMaximo(20).validar(id, "id");
        Validadores.largoMaximo(120).validar(titulo, "titulo");
        Validadores.rangoEntero(1, 500).validar(copiasStr, "copias");

        int copias = Integer.parseInt(copiasStr);
        return biblioteca.altaLibro(id, titulo, copias);
    }

    private String buscar(Solicitud s) {
        String consulta = s.parametro(0, "consulta");
        Validadores.largoMaximo(80).validar(consulta, "consulta");
        return biblioteca.buscarLibro(consulta);
    }
}