# Quindío Express

Parcial 1 de Estructuras de Datos — Universidad del Quindío.
Gestión de paquetes y repartidores de un centro de distribución, con estructuras de datos propias y algoritmos recursivos.

## Integrantes
| Nombre | Rol | Módulo |
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

## Qué muestra `Main`
1. **Dev 1 · modelo:** paquetes, validaciones (peso 0, prioridad fuera de 1-5, destino vacío, tiempo 0, código nulo), orden natural por código y repartidores.
2. **Dev 1 · lista propia:** vacía, un elemento, eliminar primero / intermedio / último / inexistente, Iterator, uso con String e Integer.
3. **Dev 1 · jornada:** registro de 32 paquetes y 7 repartidores, códigos e identificaciones repetidas, consulta por código, reglas de asignación y entrega.
4. **Dev 2 · algoritmos con los ejemplos del enunciado:** comparadores, despacho FIFO y prioritario, municipios, recursividad con traza de la pila (14.0 kg y 4 paquetes), búsqueda binaria con traza y divide y vencerás con árbol de llamadas (18.7 kg).
5. **Dev 3 · todo junto sobre la jornada:** 10 despachos prioritarios con asignación y entrega, historial en la lista propia, recursividad sobre los 32 paquetes, búsqueda binaria y paquete de mayor peso.

La salida completa está en `docs/salida_ejemplo.txt`.

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
├── demo/          DemoCore, DemoAlgoritmos, DemoJornada, Titulos
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
