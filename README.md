# Sistema de Fidelidad de Clientes

Este proyecto implementa un sistema de fidelización de clientes donde los usuarios acumulan puntos por cada compra realizada. Según sus puntos acumulados, el cliente pertenece a un nivel de fidelidad que define un multiplicador para futuras compras. Además, se otorgan bonificaciones por compras consecutivas o múltiples compras en un mismo día.

## Características

- Registro y gestión de clientes.
- Registro de compras asociadas a un cliente y fecha.
- Asignación de puntos por compras según el nivel del cliente.
- Bonificación por 3 o más compras en el mismo día (+10 puntos).
- Actualización automática de nivel de fidelidad:
  - **Bronce**: 0–499 puntos (multiplicador 1.0)
  - **Plata**: 500–1499 puntos (multiplicador 1.2)
  - **Oro**: 1500–2999 puntos (multiplicador 1.5)
  - **Platino**: 3000+ puntos (multiplicador 2.0)
- Seguimiento de racha de compras consecutivas (streak).

## Estructura del Proyecto
src/
- cl.fidelidad.model/ # Modelos: Cliente, Compra, NivelFidelidad
- cl.fidelidad.repository/ # Repositorios en memoria para Cliente y Compra
- cl.fidelidad.service/ # Servicios ClienteService y FidelidadService
- test/ # Pruebas JUnit

## Requisitos

- Java 11 o superior
- Maven 3.6+
- JUnit 5 para pruebas

## Cómo Ejecutar

```
mvn clean install

mvn test
```

## Pruebas
Las pruebas están implementadas usando JUnit. Algunos de los casos testeados incluyen:

- Registro correcto de clientes y validación de correos.

- Acumulación de puntos y asignación correcta del nivel.

- Aplicación de bono por múltiples compras en el mismo día.

- Validación de excepción si el cliente no existe al registrar una compra.

## Enfoque de Desarrollo (TDD)

Durante el desarrollo de este proyecto, se siguió un enfoque TDD para asegurar la calidad desde el inicio. Cada nueva funcionalidad (como el registro de compras o el cálculo de puntos) fue acompañada por una prueba unitaria previa, usando JUnit 5.

Esto permitió verificar que el comportamiento deseado estuviera bien definido antes de codificar, y facilitó la detección temprana de errores.

Las clases con pruebas incluyen:

- `FidelidadServiceTest`
- `ClienteServiceTest`

## Licencia

[Licencia](https://github.com/Pruebas-de-Software-INF331/Tarea3/blob/master/LICENSE.txt)

## ¿Qué tipo de cobertura he medido?

Se implementó la herramienta JaCoCo (Java Code Coverage), que genera reportes automáticos tras ejecutar las pruebas unitarias en el proyecto.

El tipo principal de cobertura medida fue:

- Cobertura de instrucciones: porcentaje de líneas de código ejecutadas durante las pruebas.

- Cobertura de métodos: qué métodos fueron invocados al menos una vez por las pruebas.

- Cobertura de clases: qué clases del sistema fueron ejercitadas por las pruebas.

- Cobertura de ramas (opcional, si se activó): analiza decisiones if/else, switch, etc., y verifica si se recorrieron todas las rutas lógicas.

## ¿Por qué?

Porque medir cobertura permite evaluar la efectividad de las pruebas unitarias, identificar partes del código que no están siendo testeadas y mejorar la calidad del software. Además, es un requisito de la entrega que valida que se siguió un enfoque de desarrollo basado en pruebas (TDD).
