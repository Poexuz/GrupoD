package framework;

import java.util.List;

public record Solicitud(
        String recurso,
        String accion,
        List<String> parametros,
        Sesion sesion
) {
    public String parametro(int indice, String nombre) {
        if (indice < 0 || indice >= parametros.size() || parametros.get(indice).isBlank()) {
            throw new SolicitudInvalidaException("Falta parametro obligatorio: " + nombre);
        }
        return parametros.get(indice).trim();
    }

    public String parametroOpcional(int indice) {
        if (indice < 0 || indice >= parametros.size()) {
            return "";
        }
        return parametros.get(indice).trim();
    }
}