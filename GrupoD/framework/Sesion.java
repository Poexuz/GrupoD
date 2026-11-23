package framework;

import java.util.Optional;

public class Sesion {
    private String usuario;
    private Rol rol;

    public void iniciar(String usuario, Rol rol) {
        this.usuario = usuario;
        this.rol = rol;
    }

    public void cerrar() {
        this.usuario = null;
        this.rol = null;
    }

    public boolean estaLogueado() {
        return usuario != null;
    }

    public Optional<String> usuario() {
        return Optional.ofNullable(usuario);
    }

    public Optional<Rol> rol() {
        return Optional.ofNullable(rol);
    }

    public void verificar(Rol requerido) {
        if (requerido == null) {
            return;
        }
        if (!estaLogueado() || rol == null) {
            throw new PermisoDenegadoException("Inicie sesion");
        }
        if (!rol.puedeHacer(requerido)) {
            throw new PermisoDenegadoException("Permiso insuficiente");
        }
    }
}