# Quindío Express

Parcial 1 de Estructuras de Datos — Universidad del Quindío.
Gestión de paquetes y repartidores de un centro de distribución, con estructuras de datos propias y algoritmos recursivos.

## Integrantes
| Rol | Nombre | Módulo |
|-----|--------|--------|
| Santiago Padilla| | Modelo, lista propia y centro de distribución |
| Nicolas Muños | | Colecciones, despacho y algoritmos |
| Sergio Ramos | | Análisis (A, C, D), integración y entrega |

## Requisitos
- Java 25 (el `pom.xml` sigue al repo guía). El código no usa nada nuevo: compila con Java 17 o superior.
- Maven 3.9+ (opcional; también se puede compilar solo con `javac`).

## Compilar y ejecutar

**IntelliJ:** abrir la carpeta como proyecto Maven y ejecutar `Main`.

**Maven:**
```bash
mvn compile
java -cp target/classes co.edu.uniquindio.quindioexpress.Main
```
Si tu JDK es más viejo que 25, cambia `maven.compiler.release` en el `pom.xml` a tu versión (17, 21...).

**Solo javac (Linux / macOS / Git Bash):**
```bash
javac -encoding UTF-8 -d out $(find src/main/java -name "*.java")
java -cp out co.edu.uniquindio.quindioexpress.Main
```

**Solo javac (PowerShell en Windows):**
```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src\main\java -Filter *.java).FullName
java "-Dstdout.encoding=UTF-8" -cp out co.edu.uniquindio.quindioexpress.Main
```
Si las tildes salen como `?`, es la consola: agrega `-Dstdout.encoding=UTF-8` al comando `java` o ejecuta `chcp 65001` antes.

## Estructura
```
src/main/java/co/edu/uniquindio/quindioexpress/
├── modelo/        Paquete (Comparable por código), Repartidor
├── estructuras/   IColeccion<T>, Nodo<T>, ListaSimplementeEnlazada<T>, ListaIterator<T>
├── servicio/      CentroDistribucion (registro, consulta, asignación, entrega, historial)
├── datos/         DatosPrueba (jornada y ejemplos del enunciado)
├── comparadores/  ComparadorPrioridad, ComparadorPeso, ComparadorTiempo, ComparadorDespacho
├── operaciones/   ReporteMunicipios (Set, TreeSet, Map), Despacho (Queue, PriorityQueue)
├── algoritmos/    Recursivos, BusquedaBinaria, DivideYVenceras
├── demo/          DemoCore, DemoAlgoritmos, Titulos
└── Main.java
docs/              Partes A, C y D (base para el PDF)
```

## Decisiones que hay que poder defender
- **Prioridad 1 a 5.** El enunciado dice 0-10 en la descripción y 1-5 en las reglas. Se validó 1-5 con las constantes `PRIORIDAD_MIN` y `PRIORIDAD_MAX` de `Paquete`; si el profesor dice otra cosa se cambia en una línea.
- **La lista propia no expone nodos.** `Nodo` tiene visibilidad de paquete y la lista no tiene `getInicio()`. Se recorre con for-each o con `obtener(i)`.
- **La lista guarda la cola.** Así `agregar` al final es O(1), que es lo que hace el historial.
- **Los recursivos no tienen ciclos** y reciben `List` (get en O(1)), no la lista propia (obtener en O(i)).
- **La búsqueda binaria no verifica el orden** (costaría O(n)); la precondición está en el Javadoc y la demo muestra qué pasa si se incumple.

## Quién hizo qué
(se completa al final)

## Flujo de trabajo
Ver [CONTRIBUTING.md](CONTRIBUTING.md).
