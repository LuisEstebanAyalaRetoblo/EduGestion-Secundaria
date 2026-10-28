package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conectar {
    private Connection conexion;

    public void conectar() {
        String host = variableRequerida("EDUGESTION_DB_HOST", false).trim();
        String puerto = variableRequerida("EDUGESTION_DB_PORT", false).trim();
        String baseDatos = variableRequerida("EDUGESTION_DB_NAME", false).trim();
        String usuario = variableRequerida("EDUGESTION_DB_USER", false);
        String password = variableRequerida("EDUGESTION_DB_PASSWORD", true);

        validarPuerto(puerto);
        if (host.matches(".*[/?#&\\s].*") || baseDatos.matches(".*[/?#&\\s].*")) {
            throw new IllegalStateException("EDUGESTION_DB_HOST y EDUGESTION_DB_NAME no deben contener espacios ni separadores de URL.");
        }
        String url = "jdbc:mysql://" + host + ":" + puerto + "/" + baseDatos
                + "?useSSL=false&useUnicode=true&characterEncoding=UTF-8";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("No se pudo establecer la conexión", e);
        }
    }

    private static String variableRequerida(String nombre, boolean permiteVacio) {
        String valor = System.getenv(nombre);
        if (valor == null || (!permiteVacio && valor.trim().isEmpty())) {
            throw new IllegalStateException("Falta configurar la variable de entorno " + nombre + ".");
        }
        return valor;
    }

    private static void validarPuerto(String valor) {
        try {
            int puerto = Integer.parseInt(valor);
            if (puerto >= 1 && puerto <= 65535) {
                return;
            }
        } catch (NumberFormatException e) {
            // No incluir el valor recibido en el mensaje de error.
        }
        throw new IllegalStateException("EDUGESTION_DB_PORT debe ser un número entre 1 y 65535.");
    }

    public Connection getConexion() {
        return conexion;
    }

    public void desconectar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo cerrar la conexión", e);
        }
    }
}
