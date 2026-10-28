# EduGestión Secundaria — WebEducativa

Preparación de desarrollo para Windows y macOS, 28 de octubre de 2026.

## Estado y alcance

Proyecto Java EE 7 Web Profile, Java 8, Servlet 3.1, JSP/JSTL y DAO con JDBC.
El ZIP original se extrajo con autorización a esta carpeta. No se modificaron
`build.xml`, `nbproject/` ni `web/`. Posteriormente se autorizó modificar únicamente
`src/java/Conexion/Conectar.java` para externalizar la conexión MySQL. No se instalaron herramientas ni
se compiló, desplegó o ejecutó la aplicación.

La configuración está preparada; el entorno todavía NO está listo para ejecutar.
La construcción nueva no consume clases, JSP generadas ni JAR desde `build/`.
No hay un script SQL en el proyecto. No se ha comprobado una base de datos real.

## Inventario de Windows

| Componente | Hallazgo |
|---|---|
| Java Runtime | Oracle JRE 8u471, `C:/Program Files/Java/jre1.8.0_471`; metadatos del ejecutable 8.0.4710.9 |
| JDK 8 | No encontrado; no se encontró `javac.exe` en las ubicaciones revisadas |
| JDK 17 | Existe un instalador 17.0.12 en Descargas; esto NO demuestra instalación y no sustituye el JDK 8 del proyecto |
| Apache Ant | No encontrado en PATH ni ubicaciones revisadas |
| GlassFish | No encontrado en ubicaciones revisadas; el proyecto original referencia 4.1.1 |
| MySQL Workbench | Instalado, versión 8.0.41 |
| Cliente mysql | Incluido con Workbench, metadatos 8.0.41.0; fuera de PATH |
| MySQL Server | No encontrado; Workbench y el cliente no son el servidor |
| VS Code | Instalación encontrada en AppData/Local/Programs/Microsoft VS Code |
| Extensiones | Encontradas extensiones .NET; no se encontraron las Java/JSP/SQL recomendadas |

Se revisaron PATH, variables de entorno, registros de desinstalación, ubicaciones
Java/MySQL/Oracle en Program Files, Program Files (x86), carpetas habituales de
herramientas en C:/, AppData/Local/Programs, Documents, Desktop, Downloads y
ubicaciones habituales de JDK/Scoop del usuario. Esto no es una búsqueda exhaustiva
de todos los discos. El inventario de servicios/CIM está restringido por permisos.
`java -version` a través del acceso Oracle de PATH no respondió y fue interrumpido;
la versión del JRE se identifica por sus archivos, no por una prueba de funcionamiento.

## Archivos de preparación

- `.vscode/extensions.json`: recomendaciones, sin instalación automática.
- `.vscode/settings.json`: UTF-8, fuentes originales, sin formato ni compilación
  automáticos, separación de fuentes y salidas generadas.
- `.vscode/tasks.json`: verificar dependencias y generar WAR; no despliega.
- `.gitignore`: excluye la configuración local y las nuevas salidas generadas.
- `build-vscode.xml`: construcción Ant independiente, sin importar scripts NetBeans.
- `config/development.local.properties.example`: ejemplo de rutas por equipo.
- `config/glassfish-web.xml`: configuración equivalente al descriptor encontrado
  en el build del ZIP, conservada fuera de archivos generados.
- `lib/README.md`: versión, procedencia y SHA-256 del JDBC esperado.

## Herramientas y versiones de referencia

- JDK **8 completo** para Ant, javac y GlassFish; no basta un JRE.
- Apache Ant **1.10.15** como referencia reproducible. La validación admite
  posteriores compatibles con Java 8; documentar la versión elegida en ambos equipos.
- GlassFish **4.1.1** para reproducir el entorno original con `javax.*`.
  `GLASSFISH_HOME` apunta a su subcarpeta `glassfish`, que contiene `modules/`.
- MySQL Connector/J **5.1.16**, misma copia auditada; no se actualizó.
- MySQL Server: versión pendiente de decidir con el esquema y autenticación originales.
  No asumir compatibilidad del controlador antiguo con MySQL moderno.

La actualización de componentes se evaluará aparte. Esta base reproduce el proyecto
original para desarrollo local; no constituye una validación para producción.

## Extensiones de VS Code

Aceptar la instalación de recomendaciones solo cuando se autorice instalar herramientas:

| Extensión | ID | Uso |
|---|---|---|
| Language Support for Java by Red Hat | `redhat.java` | Análisis y autocompletado Java |
| Debugger for Java | `vscjava.vscode-java-debug` | Depuración futura, no configurada aún |
| Project Manager for Java | `vscjava.vscode-java-dependency` | Fuentes y classpath del proyecto Ant |
| JSP Language Support | `samuel-weinhardt.vscode-jsp-lang` | Edición y resaltado JSP |
| SQLTools | `mtxr.sqltools` | Consulta de bases, opcional |
| SQLTools MySQL/MariaDB/TiDB | `mtxr.sqltools-driver-mysql` | Driver del editor, distinto del JDBC de Java |

VS Code ya edita SQL como texto; las extensiones SQL son útiles para conectar y
consultar, pero no son necesarias para compilar el WAR. No guardar contraseñas en
archivos compartidos del proyecto.

El runtime del servidor de lenguaje Java es independiente del JDK 8 del proyecto.
La extensión Red Hat puede incluir su propio runtime según plataforma; si necesita
un JDK moderno externo, se seleccionará conforme a la versión instalada. No apuntar
`java.jdt.ls.java.home` al JRE 8. No cambiar Java 8 de la aplicación por ese motivo.

Tras instalar, ejecutar `Java: Configure Java Runtime` y seleccionar un **JDK 8**
para el proyecto sin Maven/Gradle (JavaSE-1.8, predeterminado). En este proyecto no
se fijó una ruta de JDK inexistente ni se confió en sustituciones de variables en
settings que dependen de cada extensión. Si se requiere un ajuste JSON local:

```json
{
  "java.configuration.runtimes": [
    { "name": "JavaSE-1.8", "path": "RUTA_LOCAL_REAL_AL_JDK8", "default": true }
  ]
}
```

Utilizar `Java: Configure Classpath` para agregar `javax.servlet-api.jar` desde
GlassFish y, cuando corresponda, las API JSP/JSTL. Mantener esas rutas como ajustes
locales del equipo. El build tiene su propio classpath explícito y no depende del
classpath del editor. El resaltado JSP no sustituye la validación del servidor.

## Bibliotecas

La carpeta `lib/` no contiene aún el conector. Pendiente de aprobación: copiar allí
el mismo JAR incluido en el ZIP, sin descargarlo ni alterar versión:

```
lib/mysql-connector-java-5.1.16-bin.jar
SHA-256 e2d1339ea279d867fb825e0f1a455014bbee360e2a4a1e425033474d2335525d
```

El build verifica ese SHA-256 y se detiene si falta o difiere. No utilizar la carpeta
`build/` como almacenamiento permanente de bibliotecas. La suma confirma identidad
con la copia auditada; no sustituye verificar la procedencia del proveedor.

El servidor debe aportar estos archivos en `modules/`:

```
javax.servlet-api.jar
javax.servlet.jsp-api.jar
javax.servlet.jsp.jstl-api.jar
javax.servlet.jsp.jstl.jar
```

Las API del servidor no se incluyen en `WEB-INF/lib`. El WAR solo incorpora el
conector JDBC auditado. Nuevas dependencias requieren revisar el build explícito.

## Configuración Windows (pendiente de herramientas y aprobación)

Los siguientes comandos son instrucciones; no se ejecutaron en esta etapa.
Sustituir todas las rutas de ejemplo por las reales. No ejecutar instaladores
hasta aprobar las herramientas y versiones.

En PowerShell, antes de iniciar VS Code desde esa misma consola:

```powershell
$env:JAVA8_HOME = 'C:/tools/jdk8'
$env:ANT_HOME = 'C:/tools/apache-ant-1.10.15'
$env:GLASSFISH_HOME = 'C:/tools/glassfish-4.1.1/glassfish'
$env:JAVA_HOME = $env:JAVA8_HOME
# Abrir esta carpeta como raíz; cerrar otras instancias si no heredan el entorno.
code .
```

Son variables de la sesión; no modifican PATH ni el registro. Si VS Code ya estaba
abierto, puede conservar un entorno anterior: cerrar sus ventanas y abrirlo de
nuevo desde la consola configurada. No definir JAVA_HOME global para el editor.

Como alternativa para las rutas internas de Ant, copiar el ejemplo a
`config/development.local.properties` y completar `jdk.home`, `glassfish.home` y,
si procede, `mysql.connector.jar`. Usar `/` y no poner comillas en .properties.
Las tareas VS Code siguen necesitando JAVA8_HOME y ANT_HOME para lanzar Ant.

Verificaciones futuras de herramientas (no inician el sistema):

```powershell
& "$env:JAVA8_HOME/bin/java.exe" -version
& "$env:JAVA8_HOME/bin/javac.exe" -version
& "$env:JAVA8_HOME/bin/java.exe" -jar "$env:ANT_HOME/lib/ant-launcher.jar" -version
& "$env:JAVA8_HOME/bin/java.exe" -jar "$env:ANT_HOME/lib/ant-launcher.jar" -f build-vscode.xml verify
```

## Configuración macOS

Usar JDK 8 y las mismas versiones de Ant, GlassFish y JDBC. Elegir JDK para Intel o
Apple Silicon según el equipo. La compatibilidad del GlassFish antiguo con el macOS
concreto todavía no está comprobada. No cambiar paquetes javax por jakarta.

Ejemplo para zsh, con rutas reales y una distribución JDK que se registre en macOS:

```sh
export JAVA8_HOME="$(/usr/libexec/java_home -v 1.8)"
export ANT_HOME="$HOME/tools/apache-ant-1.10.15"
export GLASSFISH_HOME="$HOME/tools/glassfish-4.1.1/glassfish"
export JAVA_HOME="$JAVA8_HOME"
code .
```

También puede asignarse JAVA8_HOME directamente a `.../Contents/Home` del JDK.
Abrir VS Code desde ese entorno; el icono de macOS puede no heredar las variables.

```sh
"$JAVA8_HOME/bin/java" -version
"$JAVA8_HOME/bin/javac" -version
"$JAVA8_HOME/bin/java" -jar "$ANT_HOME/lib/ant-launcher.jar" -f build-vscode.xml verify
```

Conservar UTF-8 y mayúsculas/minúsculas exactas de archivos, paquetes y tablas.
Los únicos nombres de ejecutable específicos del sistema se resuelven en Ant.

## Generar WAR desde VS Code (cuando las dependencias estén completas)

1. Abrir **esta carpeta WebEducativa**, no la carpeta padre.
2. Terminal → Run Task → `EduGestion: verificar dependencias (sin compilar)`.
3. Ejecutar `EduGestion: generar WAR (sin desplegar)` o Ctrl+Shift+B.
4. Resultado esperado: `dist-vscode/WebEducativa.war`.

Equivalente PowerShell:

```powershell
& "$env:JAVA8_HOME/bin/java.exe" -jar "$env:ANT_HOME/lib/ant-launcher.jar" -f build-vscode.xml war
```

Equivalente macOS:

```sh
"$JAVA8_HOME/bin/java" -jar "$ANT_HOME/lib/ant-launcher.jar" -f build-vscode.xml war
```

Cada construcción compila desde `src/java/` en una carpeta nueva bajo `.dev-build/`.
No reutiliza .class antiguos ni elimina archivos originales. Las carpetas nuevas
acumulan resultados por ejecución; cualquier limpieza debe limitarse a esas salidas.
El WAR incorpora `web/`, clases recién compiladas, JDBC y el descriptor conservado.
Las JSP se compilan al desplegar/solicitar páginas, igual que la configuración original
con `compile.jsps=false`: generar el WAR no demuestra que funcionen todas las vistas.
No hay tareas de inicio, despliegue automático ni conexión a la base de datos.

## Rutas antiguas y NetBeans

`build-vscode.xml` NO importa `build.xml`, `nbproject/build-impl.xml` ni las
propiedades privadas. No necesita `libs.CopyLibs.classpath` ni NetBeans instalado.
Las rutas locales se suministran por entorno o el archivo .properties ignorado.
Se conservan intactas las rutas antiguas para no modificar nbproject sin aprobación.
El build original de NetBeans sigue requiriendo sus ajustes originales.

`config/glassfish-web.xml` conserva la configuración que solo estaba en build/:
class-loader delegate=true y keepgenerated=true. El build nuevo la empaqueta desde
config/, sin leer build/. No se cambió el descriptor fuente `web/WEB-INF/web.xml`.

## Base de datos y pasos pendientes

La conexión se configura exclusivamente mediante estas variables de entorno,
leídas por `System.getenv` en `Conexion/Conectar.java` al conectar:

| Variable | Contenido |
|---|---|
| `EDUGESTION_DB_HOST` | Host MySQL, sin protocolo ni ruta; IPv6 con corchetes |
| `EDUGESTION_DB_PORT` | Puerto numérico entre 1 y 65535 |
| `EDUGESTION_DB_NAME` | Nombre de la base de datos existente |
| `EDUGESTION_DB_USER` | Cuenta MySQL configurada localmente |
| `EDUGESTION_DB_PASSWORD` | Contraseña de esa cuenta |

Las cinco variables son obligatorias: no hay usuario, contraseña ni dirección
predeterminados en el código. La contraseña no se recorta ni se muestra. Se admite
un valor vacío si el proceso realmente recibe esa variable con valor vacío, para
preservar compatibilidad con entornos locales existentes; una variable ausente produce
un error de configuración. Algunas versiones de PowerShell eliminan la variable al
asignarle cadena vacía: para ese caso no se debe asumir que ha quedado configurada.
Host/base no admiten espacios ni separadores de ruta o parámetros JDBC.

Se conservan el driver `com.mysql.jdbc.Driver`, DriverManager, la apertura y cierre
de conexiones y las opciones JDBC `useSSL=false`, Unicode y UTF-8. No se introdujo
pool, JNDI, framework ni dependencia nueva. Se mantienen las firmas públicas usadas
por los DAO. La construcción del WAR no necesita estas variables: son del runtime.
El archivo `development.local.properties` configura Ant, no la conexión MySQL.
No se cargan archivos `.env` automáticamente.

### Windows: variables de la sesión sin escribir secretos en archivos

Ejecutar manualmente en PowerShell cuando se configure el entorno. Las preguntas
no contienen valores reales y la contraseña se solicita de forma oculta:

```powershell
$env:EDUGESTION_DB_HOST = Read-Host 'Host MySQL'
$env:EDUGESTION_DB_PORT = Read-Host 'Puerto MySQL'
$env:EDUGESTION_DB_NAME = Read-Host 'Base de datos'
$env:EDUGESTION_DB_USER = Read-Host 'Usuario MySQL'
$claveSegura = Read-Host 'Contraseña MySQL' -AsSecureString
$claveBstr = [Runtime.InteropServices.Marshal]::SecureStringToBSTR($claveSegura)
try {
    $env:EDUGESTION_DB_PASSWORD = [Runtime.InteropServices.Marshal]::PtrToStringBSTR($claveBstr)
} finally {
    [Runtime.InteropServices.Marshal]::ZeroFreeBSTR($claveBstr)
    Remove-Variable claveSegura, claveBstr
}
```

### macOS: variables de la sesión (zsh)

```sh
read 'EDUGESTION_DB_HOST?Host MySQL: '
read 'EDUGESTION_DB_PORT?Puerto MySQL: '
read 'EDUGESTION_DB_NAME?Base de datos: '
read 'EDUGESTION_DB_USER?Usuario MySQL: '
read -s 'EDUGESTION_DB_PASSWORD?Contraseña MySQL: '; printf '\n'
export EDUGESTION_DB_HOST EDUGESTION_DB_PORT EDUGESTION_DB_NAME
export EDUGESTION_DB_USER EDUGESTION_DB_PASSWORD
```

### Herencia del entorno de GlassFish

La JVM del dominio GlassFish debe iniciarse desde esa misma sesión configurada.
Definir las variables en VS Code no cambia el entorno de un dominio ya iniciado.
Cuando se autorice ejecutar el sistema, reiniciar el dominio desde la sesión con
las variables, incluso si se utiliza VS Code para editar o desplegar. Si GlassFish
funciona como servicio, configurar su entorno por el mecanismo del servicio y
reiniciarlo; no basta abrir una terminal. No iniciar el servidor en esta etapa.
No guardar secretos en README, tareas, propiedades compartidas, comandos literales
del historial, capturas o listados de variables de entorno.

### Compatibilidad y validación

El cambio usa únicamente API y sintaxis disponibles en Java 8 (`System.getenv`,
String, Integer, excepciones y JDBC). No añade dependencias. Compatibilidad revisada
estáticamente; no se compiló porque sigue faltando un JDK con javac. No se conectó
la aplicación a MySQL y no se verificó el funcionamiento mediante ejecución.

Antes de ejecutar:

1. Aprobar y disponer de JDK 8, Ant y GlassFish; registrar versiones exactas.
2. Aprobar incorporar el JAR JDBC auditado fuera de build/.
3. Instalar/configurar extensiones con aprobación; seleccionar JDK y classpath.
4. Ejecutar verify y generar WAR; resolver errores reales si los hubiera.
5. Recuperar el SQL o respaldo original; confirmar las 17 tablas del diagnóstico,
   sus claves, restricciones únicas y datos iniciales (incluido director).
6. Decidir MySQL Server y autenticación compatibles; Workbench no lo reemplaza.
7. Configurar las cinco variables en el entorno de la JVM de GlassFish; no guardar sus valores en Git.
8. Solo entonces configurar dominio/despliegue y verificar cada rol y flujo.

Estado de validación de esta entrega: se comprueba sintaxis JSON/XML y comparación
binaria de archivos originales contra el ZIP; NO se valida compilación, empaquetado,
JSP, conexión MySQL ni comportamiento de la aplicación por faltar dependencias.

## Referencias de herramientas

- [VS Code: proyectos Java](https://code.visualstudio.com/docs/java/java-project)
- [Red Hat Java y runtime del editor](https://marketplace.visualstudio.com/items?itemName=redhat.java)
- [JSP Language Support](https://marketplace.visualstudio.com/items?itemName=samuel-weinhardt.vscode-jsp-lang)
- [SQLTools](https://marketplace.visualstudio.com/items?itemName=mtxr.sqltools)
- [SQLTools MySQL](https://marketplace.visualstudio.com/items?itemName=mtxr.sqltools-driver-mysql)
- [Apache Ant](https://ant.apache.org/)
- [Connector/J: autenticación añadida en 5.1.46](https://dev.mysql.com/blog-archive/mysql-connector-java-5-1-46/)
