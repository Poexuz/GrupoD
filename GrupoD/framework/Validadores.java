package framework;

public final class Validadores {

    private Validadores() {
    }

    public static Validador noVacio() {
        return (valor, campo) -> {
            if (valor == null || valor.isBlank()) {
                throw new SolicitudInvalidaException("El campo '" + campo + "' no puede estar vacio");
            }
        };
    }

    public static Validador largoMaximo(int max) {
        return noVacio().yLuego((valor, campo) -> {
            if (valor.trim().length() > max) {
                throw new SolicitudInvalidaException(
                        "El campo '" + campo + "' supera el largo maximo de " + max + " caracteres"
                );
            }
        });
    }

    public static Validador enteroPositivo() {
        return noVacio().yLuego((valor, campo) -> {
            try {
                int n = Integer.parseInt(valor.trim());
                if (n <= 0) {
                    throw new SolicitudInvalidaException(
                            "El campo '" + campo + "' debe ser un entero positivo (recibido: " + valor + ")"
                    );
                }
            } catch (NumberFormatException e) {
                throw new SolicitudInvalidaException(
                        "El campo '" + campo + "' debe ser un numero entero valido (recibido: " + valor + ")"
                );
            }
        });
    }

    public static Validador rangoEntero(int min, int max) {
        return noVacio().yLuego((valor, campo) -> {
            try {
                int n = Integer.parseInt(valor.trim());
                if (n < min || n > max) {
                    throw new SolicitudInvalidaException(
                            "El campo '" + campo + "' debe estar entre " + min + " y " + max
                    );
                }
            } catch (NumberFormatException e) {
                throw new SolicitudInvalidaException(
                        "El campo '" + campo + "' debe ser un numero entero valido"
                );
            }
        });
    }
}