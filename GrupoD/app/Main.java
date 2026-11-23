package app;

import app.Controladores.ControladorLibros;
import app.Controladores.ControladorPrestamos;
import app.Controladores.ControladorUsuarios;
import app.Servicios.ServicioBiblioteca;
import app.Servicios.ServicioUsuarios;
import framework.Router;
import framework.Servicios;
import framework.Servidor;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int puerto = args.length > 0 ? Integer.parseInt(args[0]) : 3000;

        Servicios servicios = new Servicios();
        servicios.registrar(ServicioUsuarios.class, new ServicioUsuarios());
        servicios.registrar(ServicioBiblioteca.class, new ServicioBiblioteca());

        ServicioUsuarios servicioUsuarios = servicios.obtener(ServicioUsuarios.class);
        ServicioBiblioteca servicioBiblioteca = servicios.obtener(ServicioBiblioteca.class);

        Router router = new Router();
        router.registrar(new ControladorUsuarios(servicioUsuarios));
        router.registrar(new ControladorLibros(servicioBiblioteca));
        router.registrar(new ControladorPrestamos(servicioBiblioteca));

        Servidor servidor = new Servidor(puerto, router, "OK Bienvenido al servidor de Biblioteca");
        servidor.iniciar();
    }
}