package framework;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Servicios {
    private final Map<Class<?>, Object> registro = new HashMap<>();

    public <T> void registrar(Class<T> tipo, T instancia) {
        Objects.requireNonNull(tipo, "El tipo del servicio no puede ser null");
        Objects.requireNonNull(instancia, "La instancia del servicio no puede ser null");
        registro.put(tipo, instancia);
    }

    public <T> T obtener(Class<T> tipo) {
        Object instancia = registro.get(tipo);
        if (instancia == null) {
            throw new FrameworkException("Servicio no registrado: " + tipo.getSimpleName());
        }
        return tipo.cast(instancia);
    }
}