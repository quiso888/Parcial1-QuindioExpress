# Parte C. Análisis algorítmico y pruebas

Todas las trazas de este documento las imprime el programa al ejecutar `Main` (métodos `...ConTraza`), así que se pueden verificar en vivo durante la sustentación.

## C.1 Pruebas de escritorio y pila de llamadas

### pesoTotalPorMunicipio (Recursivos.pesoTotal)

Datos: PQ101 Armenia 4.5, PQ203 Salento 3.0, PQ415 Armenia 7.2, PQ730 Armenia 2.3. Consulta: "Armenia".

```java
private static double pesoTotal(List<Paquete> paquetes, String municipio, int i) {
    if (i == paquetes.size()) return 0;                          // caso base
    double aporte = destino coincide ? peso : 0;
    return aporte + pesoTotal(paquetes, municipio, i + 1);       // caso recursivo
}
```

| Pregunta | Respuesta |
|----------|-----------|
| Caso base | `i == paquetes.size()` (ya no quedan paquetes): retorna 0 |
| Caso recursivo | `aporte(i) + f(i + 1)` |
| Cómo avanza al caso base | `i` aumenta en 1 en cada llamada; la lista no cambia |
| Parámetros de cada llamada | la misma lista, el mismo municipio, e `i` = 0, 1, 2, 3, 4 |
| Llamadas pendientes | cada llamada queda esperando el resultado de `f(i + 1)` para sumarle su aporte |
| Cuándo se alcanza el caso base | en la quinta llamada, `f(4)`, porque el tamaño es 4 |
| Orden de los retornos | inverso al de las llamadas: f(4), f(3), f(2), f(1), f(0) |
| Valor de cada retorno | 0.0, 2.3, 9.5, 9.5, 14.0 |

**Pila de llamadas** (crece hacia abajo; se desapila de abajo hacia arriba):

| Llamada | i | Paquete | ¿Coincide? | Queda pendiente | Retorna |
|---------|---|---------|------------|-----------------|---------|
| 1 | 0 | PQ101 Armenia | Sí | 4.5 + f(1) | 14.0 |
| 2 | 1 | PQ203 Salento | No | 0.0 + f(2) | 9.5 |
| 3 | 2 | PQ415 Armenia | Sí | 7.2 + f(3) | 9.5 |
| 4 | 3 | PQ730 Armenia | Sí | 2.3 + f(4) | 2.3 |
| 5 | 4 | (caso base) | — | nada | 0.0 |

Estado de la pila en el momento de mayor profundidad:

```
| f(4)  -> caso base, retorna 0.0      |  <- tope
| f(3)  esperando: 2.3 + f(4)          |
| f(2)  esperando: 7.2 + f(3)          |
| f(1)  esperando: 0.0 + f(2)          |
| f(0)  esperando: 4.5 + f(1)          |
| main                                 |
```

Salida real del programa:

```
f(i=0) PQ101 Armenia -> queda pendiente 4.5 + f(1)
  f(i=1) PQ203 Salento -> queda pendiente 0.0 + f(2)
    f(i=2) PQ415 Armenia -> queda pendiente 7.2 + f(3)
      f(i=3) PQ730 Armenia -> queda pendiente 2.3 + f(4)
        f(i=4) caso base -> retorna 0.0
      f(i=3) retorna 2.3
    f(i=2) retorna 9.5
  f(i=1) retorna 9.5
f(i=0) retorna 14.0
```

### contarPrioridadMinima (Recursivos.contar)

Datos: prioridades 5, 2, 4, 1, 5, 3, 4. Prioridad mínima: 4.

| Pregunta | Respuesta |
|----------|-----------|
| Caso base | `i == paquetes.size()`: retorna 0 |
| Caso recursivo | `(1 si prioridad(i) >= 4, si no 0) + g(i + 1)` |
| Avance | `i + 1` |
| Parámetros | la misma lista, mínimo 4, `i` = 0 … 7 |
| Llamadas pendientes | g(0) a g(6), cada una esperando su `+ g(i + 1)` |
| Cuándo se alcanza el caso base | en la octava llamada, g(7) |
| Orden de retornos | g(7), g(6), g(5), g(4), g(3), g(2), g(1), g(0) |
| Valores de retorno | 0, 1, 1, 2, 2, 3, 3, 4 |

| Llamada | i | Prioridad | ¿≥ 4? | Queda pendiente | Retorna |
|---------|---|-----------|-------|-----------------|---------|
| 1 | 0 | 5 | Sí | 1 + g(1) | 4 |
| 2 | 1 | 2 | No | 0 + g(2) | 3 |
| 3 | 2 | 4 | Sí | 1 + g(3) | 3 |
| 4 | 3 | 1 | No | 0 + g(4) | 2 |
| 5 | 4 | 5 | Sí | 1 + g(5) | 2 |
| 6 | 5 | 3 | No | 0 + g(6) | 1 |
| 7 | 6 | 4 | Sí | 1 + g(7) | 1 |
| 8 | 7 | (caso base) | — | nada | 0 |

Resultado: **4 paquetes**.

## C.2 Búsqueda binaria

Precondición: la lista está ordenada por código. Lista: PQ105, PQ214, PQ318, PQ421, PQ567, PQ731, PQ842, PQ942 (posiciones 0 a 7).

Buscar **PQ731**:

| Paso | ini | fin | medio | código[medio] | Comparación | Decisión |
|------|-----|-----|-------|---------------|-------------|----------|
| 1 | 0 | 7 | 3 | PQ421 | PQ421 < PQ731 | ini = 4 |
| 2 | 4 | 7 | 5 | PQ731 | igual | encontrado en 5 |

Buscar **PQ500** (no existe):

| Paso | ini | fin | medio | código[medio] | Decisión |
|------|-----|-----|-------|---------------|----------|
| 1 | 0 | 7 | 3 | PQ421 | ini = 4 |
| 2 | 4 | 7 | 5 | PQ731 | fin = 4 |
| 3 | 4 | 4 | 4 | PQ567 | fin = 3 |
| — | 4 | 3 | — | — | ini > fin, retorna -1 |

Qué pasa si se incumple la precondición: el programa busca PQ105 en la jornada sin ordenar y retorna -1 aunque el paquete existe. Después de `Collections.sort` lo encuentra.

## C.3 Divide y vencerás: mayorPeso

Pesos 4.5, 12.0, 3.2, 18.7, 7.4, 5.1 (posiciones 0 a 5). Árbol de llamadas real:

```
[0..5] dividir en [0..2] y [3..5]
  [0..2] dividir en [0..1] y [2..2]
    [0..1] dividir en [0..0] y [1..1]
      [0..0] caso base -> 4.5
      [1..1] caso base -> 12.0
    [0..1] combinar max(4.5, 12.0) -> 12.0
    [2..2] caso base -> 3.2
  [0..2] combinar max(12.0, 3.2) -> 12.0
  [3..5] dividir en [3..4] y [5..5]
    [3..4] dividir en [3..3] y [4..4]
      [3..3] caso base -> 18.7
      [4..4] caso base -> 7.4
    [3..4] combinar max(18.7, 7.4) -> 18.7
    [5..5] caso base -> 5.1
  [3..5] combinar max(18.7, 5.1) -> 18.7
[0..5] combinar max(12.0, 18.7) -> 18.7
```

| Etapa | Dónde se ve |
|-------|-------------|
| Caso base | `ini == fin`: un solo paquete, es el mayor de su rango |
| División | `medio = (ini + fin) / 2` |
| Subproblema izquierdo | `mayorPeso(ini, medio)` |
| Subproblema derecho | `mayorPeso(medio + 1, fin)` |
| Combinación | el de mayor peso entre `izquierdo` y `derecho` |
| Resultado final | la llamada sobre [0..5] retorna PQ318, 18.7 kg |

Hay 11 llamadas (2n − 1) y la profundidad máxima es 4 (⌈log₂ 6⌉ + 1).

## C.4 Complejidad temporal y espacial

| Operación | Estructura / algoritmo | Tiempo | Espacio extra |
|-----------|------------------------|--------|---------------|
| Registrar paquete (con validación) | `LinkedHashMap.containsKey` + `put` + `ArrayDeque.offer` | O(1) promedio | O(1) |
| Consultar por código | `LinkedHashMap.get` | O(1) promedio | O(1) |
| Municipios sin repetir | `LinkedHashSet` | O(n) | O(m) |
| Municipios ordenados | `TreeSet` | O(n log m) | O(m) |
| Agrupar por municipio | `LinkedHashMap` + listas | O(n) | O(n) |
| Siguiente por llegada | `ArrayDeque.poll` | O(1) | — |
| Siguiente prioritario | `PriorityQueue.poll` | O(log n) | O(n) para construirla |
| Ordenar por comparador | `List.sort` (TimSort) | O(n log n) | O(n) |
| Asignación automática | recorrido de repartidores | O(r) | O(1) |
| Registrar entrega | `ArrayDeque.remove(obj)` + `agregar` en lista propia | O(n) + O(1) | O(1) |
| Lista propia: agregar / eliminar / obtener(i) | nodos con cabeza y cola | O(1) / O(n) / O(i) | O(1) por nodo |
| pesoTotalPorMunicipio, contarPrioridadMinima | recursión lineal | O(n) | O(n) por la pila |
| Búsqueda binaria iterativa / recursiva | | O(log n) | O(1) / O(log n) |
| mayorPeso | divide y vencerás, T(n) = 2T(n/2) + O(1) | O(n) | O(log n) por la pila |

## C.5 Comparación de alternativas y riesgo de StackOverflowError

- La recursión lineal apila una llamada por paquete. Con 500.000 paquetes la profundidad sería 500.001 y con la pila por defecto de la JVM (del orden de 512 KB a 1 MB) se produce `StackOverflowError`. Para ese volumen se usaría un ciclo (O(1) de espacio) o un enfoque divide y vencerás, cuya profundidad es O(log n) ≈ 20.
- `mayorPeso` también podría resolverse con un ciclo en O(n) y O(1) de espacio. Divide y vencerás no es más rápido aquí; el valor está en la estrategia (subproblemas independientes, combinables y paralelizables).
- Los métodos recursivos usan `List.get(i)` en O(1). Si se aplicaran sobre la lista enlazada propia con `obtener(i)`, que cuesta O(i), el total sería O(n²).
- La búsqueda binaria solo compensa si la lista ya está ordenada o se consulta muchas veces: ordenar cuesta O(n log n), más que una búsqueda lineal O(n).
