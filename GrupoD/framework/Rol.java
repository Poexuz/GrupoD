package framework;

public enum Rol {
    USUARIO,
    ADMIN;

    public boolean puedeHacer(Rol requerido) {
        if (requerido == null) {
            return true;
        }
        return this == ADMIN || this == requerido;
    }
}