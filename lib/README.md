# Controlador JDBC externo

La demo no abre conexiones a MySQL. El build conserva el contrato JDBC del WAR usando Connector/J **26.10.0**.

Configura `EDUGESTION_JDBC_JAR` con la ruta del JAR externo o pasa `-Dmysql.connector.jar=/ruta/real/mysql-connector-j-26.10.0.jar` a Ant. No descargamos ni versionamos JAR automáticamente.

Coordenadas oficiales de Maven Central: `com.mysql:mysql-connector-j:26.10.0`.

SHA-256 esperado:

```
cef70caf532833c9ab8f41f74773ce22c08bf503fad215c9c2ee1e9db87e759e
```

La referencia original de NetBeans a 5.1.16 se conserva como antecedente; el build utilizado es `build-vscode.xml`. No uses el build antiguo para esta demo. La integración con autenticación moderna y TLS de MySQL queda pendiente de una entrega posterior.
