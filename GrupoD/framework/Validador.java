package framework;

@FunctionalInterface
public interface Validador {
    void validar(String valor, String nombreCampo);

    default Validador yLuego(Validador siguiente) {
        return (valor, campo) -> {
            this.validar(valor, campo);
            siguiente.validar(valor, campo);
        };
    }
}