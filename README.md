# Quindío Express

Parcial 1 de Estructuras de Datos — Universidad del Quindío.
Gestión de paquetes y repartidores de un centro de distribución, con estructuras de datos propias y algoritmos recursivos.

## Integrantes
| Rol | Nombre | Módulo |
|-----|--------|--------|
| Dev 1 | | Modelo, lista propia y centro de distribución |
| Dev 2 | | Colecciones, despacho y algoritmos |
| Dev 3 | | Análisis (A, C, D), integración y entrega |

## Requisitos
- Java 25
- Maven 3.9+

## Compilar y ejecutar
```bash
mvn compile
```
O ejecutar `Main` desde IntelliJ.

## Estructura
```
src/main/java/co/edu/uniquindio/quindioexpress/
├── modelo/        Paquete, Repartidor
├── estructuras/   IColeccion, Nodo, ListaSimplementeEnlazada, ListaIterator
├── servicio/      CentroDistribucion
├── datos/         DatosPrueba
├── comparadores/  ComparadorPrioridad, ComparadorPeso, ComparadorTiempo, ComparadorDespacho
├── operaciones/   ReporteMunicipios, Despacho
├── algoritmos/    Recursivos, BusquedaBinaria, DivideYVenceras
├── demo/          DemoCore, DemoAlgoritmos
└── Main.java
docs/              Partes A, C y D
```

## Quién hizo qué
(se completa al final)

## Flujo de trabajo
Ver [CONTRIBUTING.md](CONTRIBUTING.md).
