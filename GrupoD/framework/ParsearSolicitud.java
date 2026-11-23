package framework;

import java.util.Arrays;
import java.util.List;

public class ParsearSolicitud {

    private ParsearSolicitud() {
    }

    public static Solicitud parsear(String texto, Sesion sesion) {
        if (texto == null || texto.isBlank()) {
            throw new SolicitudInvalidaException("Solicitud vacia. Formato esperado: recurso/accion[/parametros]");
        }
        String[] partes = texto.split("/", -1);
        if (partes.length < 2 || partes[0].isBlank() || partes[1].isBlank()) {
            throw new SolicitudInvalidaException("Formato invalido. Use: recurso/accion[/parametros]");
        }

        String recurso = partes[0].trim();
        String accion = partes[1].trim();
        List<String> parametros = Arrays.stream(partes)
                .skip(2)
                .toList();
        return new Solicitud(recurso, accion, parametros, sesion);
    }
}