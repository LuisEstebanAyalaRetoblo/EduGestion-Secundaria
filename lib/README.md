# Bibliotecas de desarrollo

No se instalaron ni descargaron dependencias. Esta carpeta aún no contiene JAR.

El nuevo build espera `mysql-connector-java-5.1.16-bin.jar` aquí o una ruta externa
configurada como `mysql.connector.jar`. NO utiliza `build/` como entrada.

Copia disponible en el ZIP: `build/web/WEB-INF/lib/mysql-connector-java-5.1.16-bin.jar`.
Versión comprobada en su manifiesto: MySQL Connector/J 5.1.16.
SHA-256:

```
e2d1339ea279d867fb825e0f1a455014bbee360e2a4a1e425033474d2335525d
```

Pendiente de aprobación: conservar una copia idéntica en esta carpeta. No actualizar
el controlador automáticamente. Revisar compatibilidad con el servidor MySQL y su
método de autenticación antes de ejecutar.

Para autocompletado, utilizar `Java: Configure Classpath` y agregar las API
`javax.servlet-api.jar` (y JSP/JSTL si se necesitan) de GlassFish 4.1.1. Esas rutas
son locales: configurarlas por equipo. El build obtiene las API de GLASSFISH_HOME.
No empaquetar las API del servidor en el WAR ni incorporar bibliotecas jakarta.*.
