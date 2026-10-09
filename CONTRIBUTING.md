# Guía de contribución

## Ramas
- `main`: solo entra por Pull Request. Nada de commits directos.
- `feature/core` (Dev 1), `feature/algoritmia` (Dev 2), `feature/docs-integracion` (Dev 3).

## Flujo
1. Dev 1 abre el PR de contrato (`Paquete`, `Repartidor`, `IColeccion`) y entra primero a `main`.
2. Cada uno trabaja en su rama, con commits pequeños y frecuentes.
3. `git pull origin main` antes de abrir el PR.
4. Todo PR lo aprueba otro integrante antes del merge.
5. Nadie cambia una firma del contrato sin avisar en el chat del equipo.

## Mensajes de commit (en español, con prefijo)
- `feat:` funcionalidad nueva. Ej: `feat: lista enlazada con eliminar`
- `fix:` corrección. Ej: `fix: validación de prioridad`
- `docs:` documentación. Ej: `docs: tabla A.1`
- `refactor:`, `test:`, `chore:`

## Reglas
- Cada dev edita solo los paquetes de su módulo.
- Nunca subir `.idea/` ni `target/`.
