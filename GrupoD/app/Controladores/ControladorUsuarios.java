package app.Controladores;

import app.Modelos.Usuario;
import app.Servicios.ServicioUsuarios;
import framework.Controlador;
import framework.Rol;
import framework.Solicitud;
import framework.Validadores;
import java.util.Map;
import java.util.function.Function;


public class ControladorUsuarios implements Controlador {
    private final ServicioUsuarios servicioUsuarios;

    public ControladorUsuarios(ServicioUsuarios servicioUsuarios) {
        this.servicioUsuarios = servicioUsuarios;
    }

    @Override
    public String recurso() {
        return "usuarios";
    }

    @Override
    public Map<String, Function<Solicitud, String>> acciones() {
        return Map.of(
                "login", this::login,
                "logout", this::logout,
                "quien-soy", this::quienSoy
        );
    }

    @Override
    public Map<String, Rol> permisos() {
        return Map.of(
                "quien-soy", Rol.USUARIO
        );
    }

    private String login(Solicitud s) {
        String nombre = s.parametro(0, "usuario");
        String clave = s.parametro(1, "clave");
        Validadores.largoMaximo(40).validar(nombre, "usuario");
        Validadores.noVacio().validar(clave, "clave");

        Usuario u = servicioUsuarios.autenticar(nombre, clave);
        s.sesion().iniciar(u.nombre(), u.rol());
        return "Sesion iniciada como " + u.nombre() + " (" + u.rol() + ")";
    }

    private String logout(Solicitud s) {
        s.sesion().cerrar();
        return "Sesion cerrada";
    }

    private String quienSoy(Solicitud s) {
        String user = s.sesion().usuario().orElse("anonimo");
        String rol = s.sesion().rol().map(Enum::name).orElse("SIN_ROL");
        return "Usuario: " + user + " | Rol: " + rol;
    }
}