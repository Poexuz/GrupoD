package app.Servicios;

import app.Modelos.Usuario;
import framework.FrameworkException;
import framework.Rol;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServicioUsuarios {
    private final Map<String, Usuario> usuarios = new ConcurrentHashMap<>();

    public ServicioUsuarios() {
        usuarios.put("ana", new Usuario("ana", "1234", Rol.USUARIO));
        usuarios.put("admin", new Usuario("admin", "admin123", Rol.ADMIN));
    }

    public Usuario autenticar(String nombre, String clave) {
        Usuario u = usuarios.get(nombre);
        if (u == null || !u.clave().equals(clave)) {
            throw new FrameworkException("Credenciales invalidas");
        }
        return u;
    }
}