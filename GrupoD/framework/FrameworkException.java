package framework;

/**
 * Excepcion base unchecked del framework: representa fallas previstas o de
 * protocolo/negocio/seguridad que el servidor traduce en respuestas "ERROR ...".
 * Se elije RuntimeException para no contaminar las firmas Function<Solicitud, String>
 * con checked exceptions y permitir que el manejador central las capture limpiamente.
 */
public class FrameworkException extends RuntimeException {
    public FrameworkException(String mensaje) {
        super(mensaje);
    }

    public FrameworkException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

}