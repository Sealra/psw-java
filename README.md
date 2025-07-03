# Tarjeta de Fidelidad Gamificada

## Descripción del Diseño

### Arquitectura del Sistema

El proyecto sigue un patrón de arquitectura por capas con SRP:

```
┌─────────────────────────────────────────────────────────────┐
│                    Capa de Presentación                    │
│                   ProgramaFidelidad.java                   │
│                    (Interfaz CLI)                          │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────┴───────────────────────────────────────┐
│                    Capa de Servicios                       │
│  ClienteService │ CompraService │ PuntosService            │
│                       (Lógica)                             │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────┴───────────────────────────────────────┐
│                 Capa de Persistencia                       │
│     ClienteRepository │ CompraRepository                   │
│              (Almacenamiento en Memoria)                   │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────┴───────────────────────────────────────┐
│                    Capa de Modelo                          │
│          Cliente │ Compra │ Nivel (Enum)                   │
│                     (Entidades)                            │
└─────────────────────────────────────────────────────────────┘
```

## Instrucciones para Compilar, Ejecutar y Probar

### Requisitos

- **Java SE 21** o superior
- **Maven 3.6+** para gestión de dependencias

### Compilar

```bash
# Clonar el repositorio
git clone https://github.com/Sealra/psw-java.git
cd psw-java

mvn clean compile
```

### Ejecutar

```bash
mvn exec:java
```

### Pruebas

```bash
# Ejecutar todas las pruebas
mvn test

# Ejecutar pruebas con reporte de cobertura
mvn clean test jacoco:report

# Ver reporte de cobertura
open target/site/jacoco/index.html
```

## Ejemplo de Salida de Tests

### Ejecución Exitosa de Tests

```
=======
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.fidelidad.service.PuntosServiceTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.119 s -- in com.fidelidad.service.PuntosServiceTest
[INFO] Running com.fidelidad.service.ClienteServiceTest
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.043 s -- in com.fidelidad.service.ClienteServiceTest
[INFO] Running com.fidelidad.service.CompraServiceTest
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.033 s -- in com.fidelidad.service.CompraServiceTest
[INFO] Running com.fidelidad.repository.ClienteRepositoryTest
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.019 s -- in com.fidelidad.repository.ClienteRepositoryTest
[INFO] Running com.fidelidad.repository.CompraRepositoryTest
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.017 s -- in com.fidelidad.repository.CompraRepositoryTest
[INFO] Running com.fidelidad.model.CompraTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.008 s -- in com.fidelidad.model.CompraTest
[INFO] Running com.fidelidad.model.NivelTest
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.004 s -- in com.fidelidad.model.NivelTest
[INFO] Running com.fidelidad.model.ClienteTest
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.007 s -- in com.fidelidad.model.ClienteTest
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 48, Failures: 0, Errors: 0, Skipped: 0
```

## Licencia

```
MIT License

Copyright (c) 2025 Sealra

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## Otras Consideraciones del Curso

### Metodología TDD (Test-Driven Development)

Este proyecto fue desarrollado siguiendo la metodología TDD:

1. **Red**: Escribir tests que fallen inicialmente
2. **Green**: Implementar el código mínimo para pasar los tests
3. **Refactor**: Mejorar el código manteniendo los tests verdes

## ¿Qué Tipo de Cobertura He Medido y Por Qué?

A través de Jacoco, automáticamente se mide la cobertura de instrucciones, ramas, líneas, métodos y clases. Así asegurando que el código crítico, escrito y basado en CSR está probado.

### Exclusiones Justificadas

**Archivo excluido**: `ProgramaFidelidad.java`

**Razones técnicas**:

- Contiene únicamente lógica de interfaz de usuario y la lógica ya está completamente probada en servicios
  > > > > > > > Stashed changes
