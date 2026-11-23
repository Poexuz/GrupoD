package framework;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class Router {
    private final Map<String, Controlador> controladores = new HashMap<>();

    public void registrar(Controlador controlador) {
        Objects.requireNonNull(controlador, "El controlador no puede ser null");
        controladores.put(controlador.recurso(), controlador);
    }

    public String despachar(Solicitud solicitud) {
        Controlador controlador = controladores.get(solicitud.recurso());
        if (controlador == null) {
            throw new FrameworkException("Recurso inexistente: " + solicitud.recurso());
        }

        Function<Solicitud, String> accion = controlador.acciones().get(solicitud.accion());
        if (accion == null) {
            throw new FrameworkException(
                    "Accion inexistente '" + solicitud.accion() + "' para recurso '" + solicitud.recurso() + "'"
            );
        }

        Rol rolRequerido = controlador.permisos().get(solicitud.accion());
        solicitud.sesion().verificar(rolRequerido);

        return accion.apply(solicitud);
    }
}