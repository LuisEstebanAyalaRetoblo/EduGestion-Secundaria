# Revisión de publicación de la demo

Revisión realizada el 28 de octubre de 2026, antes de publicar el código de esta entrega.

## Alcance

- Repositorio autorizado: `LuisEstebanAyalaRetoblo/EduGestion-Secundaria`.
- Rama `main`, sincronizada inicialmente con `origin/main`; árbol inicial sin cambios ajenos.
- Se revisaron todos los commits alcanzables tras actualizar las referencias remotas. Antes del commit de demo existía una revisión inicial.
- Se revisaron fuentes, configuraciones, documentación, adjuntos versionables y capturas. El informe académico no se publica.
- Búsqueda de claves privadas, tokens conocidos, credenciales literales, correos y posibles documentos/contactos personales.
- Las referencias de Ant a `${gfv3.password}` son sustituciones, no contraseñas. Los campos HTML `type="password"` no contienen credenciales. Una coincidencia numérica en el CSS distribuido de Bootstrap no representa un documento personal.
- No se detectaron secretos ni datos personales reales en el contenido revisado. Esto no equivale a una garantía absoluta de ausencia de secretos desconocidos.

## Medidas de esta entrega

- Rutas reales de autenticación y aplicación bloqueadas; demo pública sin autenticación ni sesión institucional.
- POST de demo rechazado; formularios solo previsualizan sin enviar escrituras.
- Recursos locales con versiones fijadas; sin dependencia de CDN durante la exposición.
- Datos sintéticos e inmutables compartidos por módulos; sin archivos de base de datos ni dumps.
- JAR, WAR, salidas de compilación, configuraciones privadas, certificados y documentos académicos excluidos del versionado.
- JavaDoc en las clases nuevas; README distingue implementación, simulación y propuesta futura.
- MySQL no se inicia y no se ejecutan sentencias SQL.

## Limitaciones

El código institucional original conserva riesgos documentados en README y no está habilitado para producción. No se ha realizado una auditoría de seguridad exhaustiva ni un pentest. Publicar el repositorio no publica el servicio local de GlassFish.

El cambio de visibilidad se limita al repositorio autorizado y se realizará únicamente después de confirmar compilación, pruebas y push sin sobrescritura forzada. Las credenciales existentes se utilizan desde el llavero; no se copian al repositorio ni a esta documentación.

## Dependencia local distribuida

Bootstrap 5.3.3, licencia MIT, obtenido del paquete npm servido por jsDelivr; no se instaló un framework nuevo.

- `bootstrap.min.css` SHA-256: `3c8f27e6009ccfd710a905e6dcf12d0ee3c6f2ac7da05b0572d3e0d12e736fc8`
- `bootstrap.bundle.min.js` SHA-256: `0833b2e9c3a26c258476c46266e6877fc75218625162e0460be9a3a098a61c6c`
