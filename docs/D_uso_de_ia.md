# Parte D. Uso justificado de inteligencia artificial

> Cada integrante agrega sus filas el mismo día que hace la consulta. Las filas deben ser reales.

## D.1 Bitácora

| Fecha | Integrante | Parte | Herramienta | Qué preguntaron | Por qué la consultaron | Cómo verificaron la respuesta | Qué modificaron                          |
|-------|-----------|-------|-------------|-----------------|------------------------|-------------------------------|------------------------------------------|
| 2026-10-09 | Santi | Plan | Claude | Plan técnico y reparto del parcial| Organizar el trabajo del equipo y no dejar requisitos sin dueño | Se comparó cada punto con el enunciado; se detectó la contradicción del rango de prioridad (0-10 vs 1-5) | Se agregó y colaboró en la documentación |

| | | | | | | |                                          |

## D.2 Una respuesta cuestionable de IA

> Debe ser un caso que realmente les haya pasado. Estos son candidatos típicos que vale la pena revisar en lo que les responda la IA:

| Respuesta original | Problema detectado | Análisis | Solución final |
|--------------------|--------------------|----------|----------------|
| Comparador de despacho escrito como `Comparator.comparingInt(Paquete::getPrioridad).thenComparingInt(Paquete::getTiempoEstimado).reversed()` | `reversed()` invierte TODO el comparador, no solo la prioridad: el tiempo queda de mayor a menor | Comportamiento: con PQ731 (p5, 45 min) y PQ105 (p5, 25 min) despacha primero PQ731, pero la regla 2 exige el de menor tiempo. Restricción incumplida: regla 2 del despacho | `ComparadorDespacho` compara prioridad descendente, luego tiempo ascendente y luego código, cada criterio por separado. La demo verifica PQ105, PQ731, PQ942, PQ420 |
| Lista "propia" con `private LinkedList<T> datos` por dentro, o con `public Nodo<T> getInicio()` | Viola el enunciado: no puede usar colecciones de Java por dentro ni exponer el primer nodo | Estructura utilizada: es un envoltorio de una colección del JCF. Con `getInicio()` el código externo recorre los nodos y depende de la implementación | `Nodo` con visibilidad de paquete, `cabeza` y `cola` privados sin getters, recorrido solo con `ListaIterator` |
| Búsqueda binaria directamente sobre la lista de paquetes en orden de llegada | Incumple la precondición: los datos no están ordenados por código | Comportamiento: buscar PQ105 en la jornada sin ordenar retorna -1 aunque el paquete existe (lo muestra la demo) | Ordenar una copia con `Collections.sort` (orden natural por código) y documentar la precondición en el Javadoc |
