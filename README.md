# Sistema de Fidelidad de Clientes

Este proyecto implementa un sistema de fidelización de clientes, donde los usuarios acumulan puntos por cada compra registrada. Según su cantidad de puntos, cada cliente pertenece a un **nivel de fidelidad** que otorga **multiplicadores de puntos** en futuras compras. Además, se premia la **frecuencia y recurrencia** mediante bonificaciones.

---

## Características

- Registro, actualización y eliminación de clientes.
- Registro de compras con ID, cliente y fecha.
- Asignación de puntos base por compras (1 punto cada $100, redondeo hacia abajo).
- Multiplicadores según nivel:
  - Bronce: ×1.0 (0–499 pts)
  - Plata: ×1.2 (500–1499 pts)
  - Oro: ×1.5 (1500–2999 pts)
  - Platino: ×2.0 (3000+ pts)
- Bonificación de +10 pts por 3 o más compras en el mismo día.
- Racha de compras consecutivas (streak).
- Recalculo automático del nivel tras cada compra.

---

## Diseño

El sistema está diseñado siguiendo una arquitectura **orientada a objetos**, con separación clara entre:

- **Modelos** (`Cliente`, `Compra`, `NivelFidelidad`)
- **Repositorios** en memoria (`ClienteRepository`, `CompraRepository`)
- **Servicios** de lógica de negocio (`FidelidadService`, `ClienteService`)

## Estructura
```
src/
├── cl.fidelidad.model        # Modelos de dominio
├── cl.fidelidad.repository   # Repositorios en memoria
├── cl.fidelidad.service      # Lógica del sistema
├── cl.fidelidad              # Clase Main (opcional)
└── test/                     # Pruebas JUnit 5

```

## Requisitos

- Java 11 o superior

- Maven 3.6+

- JUnit 5 (para pruebas unitarias)

- JaCoCo (para cobertura de pruebas)

## Cómo compilar, ejecutar y probar

Desde la raíz del proyecto:

```
# Compilar y verificar el proyecto
mvn clean verify

# Ejecutar pruebas
mvn test
```

Esto generará también un reporte de cobertura con JaCoCo en:

```
target/site/jacoco/index.html

```
Puedes abrir ese archivo con tu navegador para ver el detalle visual.

## Ejemplo de salida de pruebas (JUnit + Maven)

```
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running cl.fidelidad.service.FidelidadServiceTest
Tests run: 8, Failures: 0, Errors: 0, Skipped: 0

Running cl.fidelidad.model.NivelFidelidadTest
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0

...

Results:
Tests run: 30, Failures: 0, Errors: 0, Skipped: 0

```

## DEtalle de las Pruebas
Las pruebas están implementadas usando JUnit. Algunos de los casos testeados incluyen:

- Registro correcto de clientes y validación de correos.

- Acumulación de puntos y asignación correcta del nivel.

- Aplicación de bono por múltiples compras en el mismo día.

- Validación de excepción si el cliente no existe al registrar una compra.

## ¿Qué tipo de cobertura he medido y por qué?

Este proyecto mide cobertura de pruebas mediante JaCoCo, herramienta integrada a través de Maven.

Se analizaron los siguientes tipos de cobertura:

- Cobertura de instrucciones: % de líneas ejecutadas por las pruebas.

- Cobertura de métodos: métodos invocados al menos una vez.

- Cobertura de clases: clases alcanzadas por las pruebas.

### ¿Por qué?

Medir cobertura permite:

- Verificar qué tanto del código ha sido probado.

- Detectar partes del sistema que podrían estar sin validar.

- Asegurar un desarrollo guiado por pruebas (TDD).

- Satisfacer criterios de calidad y entrega del curso.
- 
## Licencia
Este proyecto se distribuye bajo los términos de la licencia:

[Ver LICENSE](https://github.com/Pruebas-de-Software-INF331/Tarea3/blob/master/LICENSE.txt)

## Consideraciones adicionales

- Se siguió un enfoque de desarrollo TDD (Test Driven Development).

- Se priorizó la separación de responsabilidades: datos (modelo), persistencia (repositorio) y lógica (servicio).

- Las pruebas cubren tanto escenarios exitosos como casos de error (por ejemplo, cliente no existente).
