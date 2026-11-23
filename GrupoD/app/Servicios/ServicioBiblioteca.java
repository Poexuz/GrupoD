package app.Servicios;

import app.Modelos.Libro;
import app.Modelos.Prestamo;
import framework.FrameworkException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServicioBiblioteca {
    private final Map<String, Libro> catalogo = new LinkedHashMap<>();
    private final List<Prestamo> prestamosActivos = new ArrayList<>();

    public synchronized String altaLibro(String id, String titulo, int copias) {
        if (catalogo.containsKey(id)) {
            throw new FrameworkException("Ya existe un libro con id: " + id);
        }
        catalogo.put(id, new Libro(id, titulo, copias));
        return "Libro registrado: [" + id + "] " + titulo + " (" + copias + " copias)";
    }

    public synchronized String listarLibros() {
        if (catalogo.isEmpty()) {
            return "No hay libros cargados";
        }
        return catalogo.values().stream()
                .map(l -> l.getId() + " - " + l.getTitulo()
                        + " (disp: " + l.getCopiasDisponibles() + "/" + l.getCopiasTotales() + ")")
                .collect(Collectors.joining(" | "));
    }

    public synchronized String buscarLibro(String consulta) {
        String filtro = consulta.toLowerCase();
        List<String> hallazgos = catalogo.values().stream()
                .filter(l -> l.getId().toLowerCase().contains(filtro)
                        || l.getTitulo().toLowerCase().contains(filtro))
                .map(l -> l.getId() + ": " + l.getTitulo() + " [disp=" + l.getCopiasDisponibles() + "]")
                .toList();

        if (hallazgos.isEmpty()) {
            throw new FrameworkException("No se encontraron libros para: " + consulta);
        }
        return String.join(" | ", hallazgos);
    }

    public synchronized String pedirPrestamo(String idLibro, String usuario) {
        Libro libro = Optional.ofNullable(catalogo.get(idLibro))
                .orElseThrow(() -> new FrameworkException("Libro inexistente: " + idLibro));

        boolean yaTienePrestamo = prestamosActivos.stream()
                .anyMatch(p -> p.idLibro().equals(idLibro) && p.usuario().equals(usuario));
        if (yaTienePrestamo) {
            throw new FrameworkException("El usuario '" + usuario + "' ya tiene prestado el libro " + idLibro);
        }

        if (!libro.tieneDisponibles()) {
            throw new FrameworkException("No hay copias disponibles de: " + idLibro);
        }

        libro.prestarCopia();
        prestamosActivos.add(new Prestamo(idLibro, usuario));
        return "Prestamo confirmado para '" + usuario + "' -> libro " + idLibro;
    }

    public synchronized String devolverPrestamo(String idLibro, String usuario) {
        Prestamo encontrado = prestamosActivos.stream()
                .filter(p -> p.idLibro().equals(idLibro) && p.usuario().equals(usuario))
                .findFirst()
                .orElseThrow(() -> new FrameworkException(
                        "No existe prestamo activo de '" + usuario + "' para el libro " + idLibro
                ));

        prestamosActivos.remove(encontrado);
        Optional.ofNullable(catalogo.get(idLibro)).ifPresent(Libro::devolverCopia);
        return "Devolucion registrada para libro " + idLibro;
    }

    public synchronized String listarPrestamos() {
        if (prestamosActivos.isEmpty()) {
            return "Sin prestamos activos";
        }
        return prestamosActivos.stream()
                .map(p -> p.usuario() + " -> " + p.idLibro())
                .collect(Collectors.joining(", "));
    }
}