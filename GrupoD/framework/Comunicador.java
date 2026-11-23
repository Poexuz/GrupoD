package framework;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Comunicador implements Closeable {
    private final Socket socket;
    private final BufferedReader entrada;
    private final PrintWriter salida;

    public Comunicador(Socket socket) throws IOException {
        this.socket = socket;
        this.entrada = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
        );
        this.salida = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
    }

    public String leerLinea() throws IOException {
        return entrada.readLine();
    }

    public void enviarLinea(String mensaje) {
        salida.println(mensaje);
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}