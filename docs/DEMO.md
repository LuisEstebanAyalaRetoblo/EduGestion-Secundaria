# Guion de exposición y alcance verificable

## Recorrido de cinco minutos

1. Inicio: explicar que es un prototipo Java EE, sin base de datos ni credenciales reales.
2. Panel: 10 estudiantes, 5 docentes, 5 secciones y 5 cursos. Participación diaria de muestra: 80 %, contando presentes y tardanza.
3. Estudiantes: buscar «Alfa», filtrar por grado, abrir una ficha. Abrir «Nuevo estudiante» y previsualizar; advertir que no guarda.
4. Matrículas: matrícula anual 2026 y apoderado ficticio coherentes con el listado.
5. Calificaciones: Matemática, pesos 30/30/40; primer trimestre definitivo de muestra, segundo solicitado y tercero abierto con Beta provisional.
6. Asistencia: filtrar FALTA; se muestra únicamente Estudiante Zeta.
7. Horarios: filtrar una sección. Solo se representa un bloque diario, no toda la carga semanal.
8. Solicitudes: pulsar una acción para explicar que no autoriza ni persiste.

## Correspondencia con requisitos

RF-01 y RF-08 no se prueban: login y cambio real de contraseña bloqueados. RF-02 aparece como presentación de Dirección, sin impersonar una sesión real. RF-03 a RF-07 se representan visualmente con datos de muestra; no se declara persistencia ni cumplimiento académico definitivo.

RNF-01: Java 8/GlassFish. RNF-03: vistas internas bajo WEB-INF y entrada pública deliberada. RNF-04: recursos locales. RNF-06: textos en español y estilos adaptables. RNF-07: datos ficticios, controlador y JSP separados. RNF-02 y la validación de credenciales de RNF-05 permanecen pendientes de integración.

## Datos y cálculos

Los nombres Alfa a Kappa, docentes Álamo a Estrella y apoderados numerados son sintéticos. No hay documentos o contactos personales. Cada grado tiene dos estudiantes y una sección A. Los cinco cursos comparten responsables consistentes en las pantallas.

Solo se calcula el ejemplo de Matemática. Una evaluación pendiente produce promedio nulo, mostrado como raya y PROVISIONAL. Los estados de cierre y la fecha de recuperación son contenido ilustrativo fijo, no un motor de autorizaciones.

`DatosDemoTest` verifica identificadores, grados, fórmula, ausencia distinta de cero, conflictos del horario de muestra e inmutabilidad. Esto no implementa validación de horarios reales.

## Seguridad de la entrega

`ProduccionPendienteFilter` bloquea los flujos JDBC institucionales. No crea usuarios ni permite eludir autenticación. La demo no utiliza `idUsuario`, `rol` o `idDocente` de sesión.

No se debe retirar el filtro sin implementar autorización por operación, CSRF, transacciones, validaciones y almacenamiento seguro de contraseñas. Las rutas antiguas se conservan para etapas posteriores, no están habilitadas para uso real.

## Modelo futuro

La propuesta de 37 tablas conserva identidad, matrícula anual, cambios históricos de sección y docente, tres trimestres, evaluaciones ponderadas, cierres, recuperaciones y auditoría. No se generaron migraciones ni tablas. Los diagramas originales del informe deben conservarse como ANTES; los diagramas futuros aprobados serán DESPUÉS. No se incluye el informe con datos personales en el repositorio público.

## Comprobaciones de esta entrega

La validación HTTP cubre once rutas de demo, las tres variantes de trimestre, dos fichas y recursos locales. Se comprueba render JSP sin expresiones sin procesar. POST de demo devuelve 405; rutas reales quedan bloqueadas o redirigidas sin acceder al DAO; una ruta desconocida devuelve 404.

Se completó una prueba de navegador Chrome headless con perfil temporal: once pantallas, recursos exclusivamente locales, búsquedas y filtros combinados, estado vacío, formulario visual sin persistencia, navegación, avisos y menú móvil a 390 px. Se revisaron capturas reales del panel, las calificaciones y la vista móvil. Esto no sustituye una evaluación de accesibilidad completa ni garantiza todos los dispositivos. Las capturas están en `docs/screenshots/`.
