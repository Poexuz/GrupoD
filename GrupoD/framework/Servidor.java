package framework;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class Servidor {

    private final int puerto;
    private final Router router;
    private final String mensajeBienvenida;

    public Servidor(int puerto, Router router, String mensajeBienvenida) {

        if (puerto <= 0 || puerto > 65535) {
            throw new IllegalArgumentException("Puerto fuera de rango: " + puerto);
        }
        this.puerto = puerto;
        this.router = Objects.requireNonNull(router, "El router no puede ser null");
        this.mensajeBienvenida = mensajeBienvenida != null
                ? mensajeBienvenida
                : "OK Servidor listo";
    }

    public void iniciar() throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(puerto)) {

            System.out.println("Servidor escuchando en el puerto " + puerto);

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                Thread hiloCliente = new Thread(
                        () -> atenderCliente(clienteSocket),
                        "cliente-" + clienteSocket.getPort()
                );
                hiloCliente.start();
            }
        }
    }

    private void atenderCliente(Socket socket) {

        Sesion sesion = new Sesion();

        try (Comunicador comunicador = new Comunicador(socket)) {
            comunicador.enviarLinea(mensajeBienvenida);

            String linea;
            while ((linea = comunicador.leerLinea()) != null) {
                try {
                    Solicitud solicitud = ParsearSolicitud.parsear(linea, sesion);
                    String resultado = router.despachar(solicitud);
                    comunicador.enviarLinea("OK " + resultado);
                } catch (FrameworkException e) {
                    comunicador.enviarLinea("ERROR " + e.getMessage());
                } catch (RuntimeException e) {
                    System.err.println("Error inesperado en " + Thread.currentThread().getName() + ": " + e.getMessage());
                    e.printStackTrace();
                    comunicador.enviarLinea("ERROR Fallo interno del servidor");
                }
            }

        } catch (IOException e) {
            System.out.println("Conexion cerrada para " + Thread.currentThread().getName() + ": " + e.getMessage());
        } finally {
            sesion.cerrar();
        }
    }
}