# Memoria técnica

## Objetivo
Integrar algoritmos clásicos en una aplicación temática Fate, manteniendo una representación procedural del dominio.

## Representación
Los datos se guardan en arreglos paralelos: nombre, identidad, clase, poder, costo, mana, vida, Master, Noble Phantasm, historia, color e imagen.

## Complejidad
- Merge Sort: O(n log n), memoria O(n).
- Voraz: O(n log n) por ordenamiento.
- Mochila: O(nP), memoria O(nP).
- Backtracking: exponencial en peor caso, con poda por clases repetidas.
- BFS: O(V+E).
- Dijkstra implementado con selección lineal: O(V²+E).
- Huffman: O(n log n) para construcción del árbol.

## Combate
Se seleccionan dos índices desde el panel web. Cada ronda calcula daño pseudoaleatorio a partir del poder y registra la evolución de vida hasta que una Servant cae o se alcanza un máximo de rondas.
