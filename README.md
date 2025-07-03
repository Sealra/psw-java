## Cobertura de Pruebas

Este proyecto utiliza **JaCoCo** para generar reportes de cobertura

### Exclusiones de Cobertura

Las siguientes clases están excluidas del análisis de cobertura:

- **`ProgramaFidelidad.java`**: Clase principal que contiene únicamente lógica de interfaz de usuario (CLI)

### Configuración de Exclusiones (pom.xml)

```xml
<excludes>
    <exclude>com/fidelidad/ProgramaFidelidad.class</exclude>
</excludes>
```

### Comandos

#### Para ejecutar programa

```bash

mvn clean package

mvn exec:java

```

#### Para tests y cobertura

```bash

mvn clean test jacoco:report

open target/site/jacoco/index.html


```
