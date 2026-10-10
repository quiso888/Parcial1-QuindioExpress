# Parte A. Análisis y diseño

> Borrador para discutir en equipo. Cada fila debe poder explicarla cualquiera de los tres.

## A.1 Selección de colecciones

| # | Necesidad | Estructura elegida | Operación principal | Complejidad esperada | Alternativa descartada | Justificación |
|---|-----------|--------------------|---------------------|----------------------|------------------------|---------------|
| 1 | Pendientes en el orden exacto de registro | `Queue<Paquete>` con `ArrayDeque` | `offer` (registrar) / `poll` (atender) | O(1) | `ArrayList` con `remove(0)`: O(n) porque desplaza todos los elementos | Es un FIFO puro: el primero que llega es el primero que se atiende, sin mover nada. |
| 2 | Consultar rápido un paquete por código | `HashMap<String, Paquete>` (en el código `LinkedHashMap`) | `get(codigo)` / `containsKey` | O(1) promedio | `ArrayList` con búsqueda lineal: O(n) | La operación dominante es buscar por código muchas veces al día. El hash va directo al bucket. |
| 3 | Municipios destino sin duplicados | `Set<String>` con `LinkedHashSet` (o `HashSet`) | `add` | O(1) promedio | `ArrayList` con `contains` antes de agregar: O(n) por inserción, O(n²) total | El Set garantiza unicidad por contrato. `LinkedHashSet` además respeta el orden de aparición del ejemplo. |
| 4 | Municipios ordenados alfabéticamente y sin repetidos | `TreeSet<String>` | `add` / recorrido en orden | O(log m) por inserción | `HashSet` y ordenar en cada reporte: O(m log m) cada vez | Queda ordenado al insertar. Se sacrifica el O(1) del hash por O(log m). |
| 5 | Atender primero la mayor prioridad | `PriorityQueue<Paquete>` con `ComparadorDespacho` | `offer` / `poll` | O(log n) | Lista ordenada: insertar en orden cuesta O(n) | Solo importa cuál es el siguiente, no el orden total. El heap da el mínimo en O(log n). |
| 6 | Historial de entregados en orden de entrega | En el JCF: `LinkedList`. En el código: **lista simplemente enlazada propia** (obligatorio) | agregar al final y recorrer | O(1) agregar (con referencia a la cola), O(n) recorrer | `ArrayList`: O(1) amortizado, pero redimensiona y copia el arreglo | Solo se agrega al final y se recorre en orden. El enunciado exige la estructura propia. |
| 7 | Asociar código con su objeto Paquete | `Map<String, Paquete>` con `LinkedHashMap` | `put` / `get` / `containsKey` | O(1) promedio | `TreeMap`: O(log n); solo valdría si se necesitara ordenado por código | Relación clave-valor. `containsKey` valida que el código no exista antes de registrar. `LinkedHashMap` además conserva el orden de registro para auditoría. |

Otras decisiones del código:

- Repartidores en `LinkedHashMap<String, Repartidor>`: identificación única en O(1) y orden de registro predecible para elegir repartidor.
- Asignaciones en `HashMap<String, Repartidor>` (código de paquete → repartidor que lo lleva).
- Agrupación municipio → paquetes en `Map<String, List<Paquete>>` con `computeIfAbsent`.

## A.2 Tiempo frente a memoria: `ArrayList<Paquete>` vs `HashMap<String, Paquete>` con 500.000 paquetes

Operación dominante: buscar un paquete por su código.

| Criterio | `ArrayList<Paquete>` | `HashMap<String, Paquete>` |
|----------|----------------------|----------------------------|
| Tiempo esperado de búsqueda | O(n). En promedio revisa n/2 = 250.000 paquetes; si no existe, los 500.000. | O(1) promedio: calcula `hashCode()` del código, va al bucket y compara con `equals` unos pocos elementos. Peor caso O(log n) desde Java 8 (buckets se vuelven árboles). |
| Memoria adicional | Solo un arreglo de referencias: unos 500.000 × 4-8 bytes ≈ 2 a 4 MB (más el espacio libre por la capacidad). | Un nodo por entrada (hash, clave, valor, siguiente ≈ 32-48 bytes) más la tabla de buckets (con factor de carga 0,75 hay 1.048.576 posiciones ≈ 4-8 MB). Total extra del orden de 20 a 30 MB. |
| Ventaja | Poca memoria, conserva el orden de inserción, acceso por posición O(1). | Búsqueda casi constante sin importar el tamaño; `containsKey` valida duplicados en O(1). |
| Desventaja | Cada búsqueda o validación de duplicado recorre la lista. Con miles de consultas al día son miles de millones de comparaciones. | Más memoria; no conserva orden (por eso se usa `LinkedHashMap`, que agrega dos referencias por nodo); depende de un buen `hashCode`. |

**Decisión:** `HashMap` (en el código `LinkedHashMap`). No porque "sea más rápido" en abstracto, sino porque:

1. la operación dominante es la búsqueda, y con n = 500.000 pasar de ~250.000 comparaciones a ~1 por consulta es una diferencia de cinco órdenes de magnitud;
2. el costo es memoria lineal (unos 20-30 MB), que es barata frente al tiempo de CPU que se ahorra;
3. el mismo mapa resuelve la validación de códigos únicos.

Tercera opción a mencionar: `ArrayList` **ordenado** por código con búsqueda binaria. Búsqueda O(log n) (unas 19 comparaciones para 500.000) con poca memoria, pero cada inserción en orden cuesta O(n) por el desplazamiento. Sirve para reportes que ya vienen ordenados, no como registro principal.

> Las cifras de memoria son aproximadas (dependen de la JVM y de si usa referencias comprimidas). Verifíquenlas antes de entregar.
