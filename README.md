# FATE / Algorithm War 5.0

## Proyecto Integrador de Programación III — Java 21

Proyecto académico inspirado en el universo de *Fate*, desarrollado con **Java 21, Java Swing, `HttpServer` y HTML/CSS/JavaScript**. Combina una aplicación de escritorio, un panel web interactivo, un sistema de combate entre Servants y un laboratorio de algoritmos y estructuras de datos.

El proyecto integra técnicas de ordenamiento, optimización, búsqueda, grafos, compresión y toma de decisiones mediante una interfaz visual inspirada en un videojuego.

> **Objetivo académico:** demostrar la aplicación práctica de los algoritmos estudiados en Programación III, manteniendo la representación de los personajes mediante arreglos paralelos y métodos estáticos, sin utilizar una jerarquía de POO tradicional para modelar el dominio.

<img width="2400" height="1350" alt="Portada de FATE / Algorithm War" src="https://github.com/user-attachments/assets/0df317cd-3d66-46f8-ac84-c3497b261937" />

---

# 1. Funcionalidades principales

El proyecto reúne las siguientes funcionalidades:

- Base de diez Servants con información narrativa y estadística.
- Fichas visuales con imágenes y datos individuales.
- Consulta de historias y videos asociados a los personajes.
- Posibilidad de cargar videos MP4 desde la computadora.
- Opening al ingresar y ending al salir del panel web.
- Sistema de combate interactivo por turnos.
- Barras de vida (HP) y mana (MP).
- Acciones de ataque, Noble Phantasm y defensa.
- Inteligencia artificial rival basada en Minimax y poda alfa-beta.
- Registro de eventos del combate.
- Laboratorio interactivo de algoritmos ejecutados desde Java.
- Panel de complejidad temporal y espacial.
- Interfaz gráfica de escritorio desarrollada con Java Swing.
- Servidor HTTP local para conectar el frontend con la lógica Java.
- Recursos gráficos y multimedia servidos localmente.

---

# 2. Contenidos de Programación III

El proyecto contempla los contenidos principales de algoritmos y estructuras de datos de la asignatura.

| Contenido | Implementación o aplicación |
|---|---|
| Análisis de complejidad | Panel de complejidad temporal y espacial |
| Divide y conquista | MergeSort y QuickSort |
| Ordenamiento | Ranking de Servants por poder |
| Algoritmos voraces | Selección Greedy por ratio poder/costo |
| Programación dinámica | Mochila 0/1 y optimización de combos |
| Grafos | Red de afinidad o similitud entre Servants |
| BFS | Recorrido por niveles |
| DFS | Recorrido en profundidad |
| Dijkstra | Caminos mínimos con pesos no negativos |
| Floyd-Warshall | Caminos mínimos entre todos los pares de vértices |
| Detección de ciclos | Identificación de ciclos en grafos |
| Prim | Árbol de recubrimiento mínimo |
| Kruskal | Árbol de recubrimiento mínimo |
| Backtracking | Formación de equipos y problema de las ocho reinas |
| Exploración exhaustiva | Evaluación de combinaciones posibles |
| Branch & Bound | Optimización mediante cotas y poda |
| Minimax | Evaluación de decisiones de la IA de combate |
| Poda alfa-beta | Reducción de ramas en la búsqueda Minimax |
| Huffman | Compresión sin pérdida de historias textuales |

La tabla describe las implementaciones previstas para esta versión. La presencia efectiva de cada algoritmo debe verificarse en el código fuente y en la versión ejecutable del proyecto.

## 2.1 Divide y conquista: MergeSort y QuickSort

Se incorporan dos algoritmos clásicos de ordenamiento.

**MergeSort**

Divide el conjunto en subconjuntos, los ordena recursivamente y combina los resultados.

- Complejidad temporal: `O(n log n)`.
- Complejidad espacial auxiliar: `O(n)` en la implementación clásica.
- Aplicación: ordenar y generar rankings de Servants según su poder.

**QuickSort**

Selecciona un pivote, particiona el conjunto y ordena recursivamente las particiones.

- Complejidad temporal promedio: `O(n log n)`.
- Complejidad temporal en el peor caso: `O(n²)`.
- El consumo de memoria auxiliar depende de la implementación y de la profundidad de recursión.

Ambos algoritmos permiten comparar estrategias de ordenamiento y analizar sus costos.

## 2.2 Algoritmos voraces: Greedy

El algoritmo Greedy selecciona candidatos priorizando la relación:

```text
poder / costo
```

El objetivo es construir un equipo eficiente sin superar un presupuesto establecido.

El procedimiento general consiste en:

1. calcular o consultar la relación poder/costo;
2. ordenar los candidatos según el criterio;
3. recorrer los candidatos;
4. incorporar los que cumplan las restricciones presupuestarias.

La complejidad temporal del ordenamiento es `O(n log n)`.

**Importante:** esta estrategia es voraz y no garantiza el óptimo global para todas las variantes del problema de selección.

## 2.3 Programación dinámica: mochila 0/1

Se utiliza el problema clásico de la mochila 0/1 para seleccionar Servants maximizando el poder total sin superar un presupuesto.

Cada candidato tiene, como mínimo:

- poder;
- costo.

La recurrencia clásica considera si conviene excluir o incluir cada elemento, respetando la capacidad disponible.

Complejidad temporal:

```text
O(n · P)
```

Con una tabla bidimensional completa, la complejidad espacial es `O(n · P)`. Algunas implementaciones pueden reducir el espacio utilizado.

Donde:

- `n` es la cantidad de elementos;
- `P` es el presupuesto entero.

Esta complejidad es **pseudopolinómica**, ya que depende del valor numérico del presupuesto, no solamente del tamaño de la entrada.

La programación dinámica también puede utilizarse para optimizar combinaciones de acciones o combos, si esa funcionalidad está implementada en la versión entregada.

## 2.4 Backtracking y exploración exhaustiva

Backtracking explora combinaciones de decisiones y retrocede cuando una elección incumple las restricciones o no permite completar una solución.

El procedimiento general consiste en:

1. seleccionar un candidato;
2. comprobar las restricciones;
3. continuar recursivamente;
4. detectar soluciones completas;
5. retroceder cuando una rama no resulta válida.

Se aplica a la formación de equipos y al problema de las ocho reinas.

En el problema de las ocho reinas, se busca ubicar ocho reinas en un tablero de ajedrez sin que dos compartan fila, columna o diagonal.

La complejidad depende del problema y de la estrategia de búsqueda. En numerosos problemas combinatorios es exponencial en el peor caso, aunque la poda puede reducir la cantidad de estados explorados.

## 2.5 Branch & Bound

Branch & Bound es una técnica de optimización que explora un espacio de soluciones utilizando cotas para descartar ramas que no pueden mejorar la mejor solución conocida.

Sus componentes principales son:

- ramificación del espacio de búsqueda;
- cálculo de cotas;
- conservación de la mejor solución factible;
- poda de ramas que no pueden superarla.

Su complejidad puede seguir siendo exponencial en el peor caso. La eficacia depende de la calidad de las cotas y del orden de exploración.

## 2.6 Grafos: BFS y DFS

Los Servants pueden representarse como vértices de un grafo. Las relaciones de afinidad o similitud representan las aristas.

**BFS — Breadth-First Search**

Recorre el grafo por niveles.

- Complejidad temporal: `O(V + E)`.
- Encuentra caminos mínimos en grafos no ponderados, medidos por cantidad de aristas.

**DFS — Depth-First Search**

Explora cada rama en profundidad antes de retroceder.

- Complejidad temporal: `O(V + E)`.
- Permite explorar componentes y puede utilizarse como base para detectar ciclos, según el tipo de grafo y la implementación.

Donde `V` es la cantidad de vértices y `E` la cantidad de aristas.

## 2.7 Dijkstra

Dijkstra calcula caminos mínimos desde un vértice inicial cuando las aristas tienen pesos no negativos.

En el proyecto, los pesos pueden representar costos de conexión o distancias entre Servants relacionados.

Complejidad temporal habitual:

- `O(V²)` con selección lineal del siguiente vértice.
- `O((V + E) log V)` con una cola de prioridad adecuada y una representación compatible.

No debe utilizarse Dijkstra con pesos negativos.

## 2.8 Floyd-Warshall

Floyd-Warshall calcula los caminos mínimos entre todos los pares de vértices.

Complejidad temporal:

```text
O(V³)
```

Complejidad espacial:

```text
O(V²)
```

Es útil cuando se necesita consultar distancias entre múltiples pares de nodos y el grafo puede representarse mediante una matriz de distancias.

## 2.9 Árboles de recubrimiento mínimo: Prim y Kruskal

Ambos algoritmos buscan construir un árbol de recubrimiento mínimo en un grafo no dirigido, ponderado y conexo.

**Prim**

Construye progresivamente el árbol, incorporando vértices mediante aristas de costo mínimo.

- Complejidad: `O(V²)` con selección lineal.
- Otras implementaciones pueden mejorar el costo utilizando una cola de prioridad.

**Kruskal**

Ordena las aristas por peso y agrega aquellas que no generan ciclos, normalmente mediante una estructura de conjuntos disjuntos.

- Complejidad temporal habitual: `O(E log E)`.

En grafos desconectados, el resultado puede ser un bosque de recubrimiento mínimo, en lugar de un único árbol que conecte todos los vértices.

## 2.10 Minimax y poda alfa-beta

Minimax permite evaluar decisiones en juegos adversariales mediante un árbol de estados.

En el sistema de combate, puede utilizarse para elegir acciones según una función de utilidad que evalúe estados favorables o desfavorables para la IA.

La poda alfa-beta descarta ramas que no pueden mejorar la decisión obtenida.

La complejidad de Minimax en el árbol completo es aproximadamente:

```text
O(b^d)
```

Donde:

- `b` es el factor de ramificación;
- `d` es la profundidad de búsqueda.

La poda alfa-beta conserva el mismo peor caso asintótico, pero puede reducir significativamente los nodos explorados. Con un ordenamiento favorable de las jugadas, el costo puede aproximarse a `O(b^(d/2))`.

## 2.11 Huffman

Huffman es un algoritmo de compresión sin pérdida basado en códigos de prefijo.

El proceso general consiste en:

1. calcular las frecuencias de los símbolos;
2. seleccionar los símbolos menos frecuentes;
3. combinarlos para construir el árbol;
4. generar los códigos binarios;
5. utilizar los códigos para representar el texto.

La construcción del árbol mediante una cola de prioridad tiene una complejidad aproximada de `O(k log k)`, donde `k` es la cantidad de símbolos distintos.

Puede utilizarse una historia de un Servant para demostrar la construcción de códigos y comparar la cantidad de bits empleada.

La reducción efectiva depende de las frecuencias del texto y del costo de almacenar la información necesaria para decodificarlo.

---

# 3. Arquitectura del sistema

La aplicación combina Java, Swing, un servidor HTTP y un frontend web.

```text
Main.java
├── DesktopApp.java
│   └── Interfaz de escritorio Swing
│
└── WebServer.java
    ├── API HTTP
    ├── Archivos estáticos
    └── Comunicación con Algoritmos.java
                  │
                  ▼
            Algoritmos.java
                  │
                  ▼
               Datos.java

Frontend
├── index.html
├── style.css
└── app.js
    ├── Servant Archive
    ├── Character Story + Video
    ├── Combat Console
    ├── Algorithm Lab
    └── Complexity Dashboard
```

## 3.1 Responsabilidades de los archivos

### `Main.java`

Es el punto de entrada de la aplicación y coordina el modo de ejecución disponible.

### `Algoritmos.java`

Contiene las implementaciones algorítmicas y las operaciones auxiliares.

Incluye los algoritmos de ordenamiento, optimización, grafos, búsqueda, compresión y toma de decisiones que estén incorporados en la versión final.

### `Datos.java`

Contiene la información procedural de los personajes y las referencias a los recursos asociados.

### `DesktopApp.java`

Implementa la interfaz gráfica de escritorio mediante Java Swing. Puede ofrecer acceso al panel web.

### `WebServer.java`

Implementa el servidor HTTP mediante:

```java
com.sun.net.httpserver.HttpServer
```

Sus responsabilidades incluyen servir los archivos estáticos y exponer los endpoints necesarios para acceder a los personajes, algoritmos, complejidad y combate.

### `index.html`

Define la estructura del panel web.

### `style.css`

Controla la presentación visual, la distribución de los componentes y los estilos de la interfaz.

### `app.js`

Controla la navegación, el renderizado de personajes, las fichas, las imágenes, los videos, la selección de Servants, el combate y la comunicación con la API.

## 3.2 Restricción de representación procedural

Los personajes se representan mediante arreglos paralelos e índices.

Ejemplos:

```java
String[] NOMBRE;
int[] PODER;
int[] VIDA;
int[] COSTO;
int[] MANA;
String[] ARMA;
String[] HISTORIA;
```

Los arreglos concretos deben corresponder con los declarados en `Datos.java`.

No se utiliza una clase de dominio como `Servant` para representar individualmente a cada personaje, ni jerarquías de entidades creadas exclusivamente para modelar el universo del proyecto.

La orientación a objetos se utiliza donde resulta necesaria para la infraestructura técnica de Java, por ejemplo, Swing, HTTP y la gestión del estado de una sesión de combate.

---

# 4. Servants y fichas de personajes

La aplicación utiliza diez personajes femeninos asociados al universo Fate:

1. Saber / Artoria
2. Rin Tohsaka
3. Sakura Matou
4. Rider / Medusa
5. Illyasviel von Einzbern
6. Saber Alter
7. Jeanne d'Arc
8. Scathach
9. Mash Kyrielight
10. Nero Claudius

Cada ficha puede incluir:

- nombre;
- identidad;
- clase;
- Master;
- historia;
- arma;
- Noble Phantasm;
- habilidades;
- poder;
- vida;
- mana;
- costo;
- defensa;
- imagen.

Los datos se almacenan proceduralmente en `Datos.java`.

La interfaz presenta las fichas visuales y puede ofrecer un modal con la historia individual y un reproductor de video asociado al personaje.

Los campos efectivamente disponibles deben coincidir con los datos definidos en el código.

---

# 5. Panel web

El panel web se encuentra disponible localmente en:

```text
http://localhost:8080
```

La aplicación se organiza en las siguientes secciones.

## 5.1 Arena

Es la pantalla principal del proyecto.

Puede presentar la introducción, la cantidad de Servants, las familias de algoritmos y los personajes destacados.

## 5.2 Servant Archive / Historial

Permite consultar las fichas de los personajes.

Cada tarjeta puede mostrar imagen, nombre, identidad, clase, Master, historia, estadísticas y Noble Phantasm.

La búsqueda permite localizar personajes por nombre, clase o Master, si está habilitada en la versión ejecutada.

## 5.3 Character Story + Video

Permite consultar la historia individual y acceder al video asociado al personaje.

Las rutas de los videos se configuran en `Datos.java` o en la estructura equivalente de la implementación.

También puede ofrecer la opción de cargar un archivo MP4 desde la computadora.

## 5.4 Combat Console / Combate

Permite seleccionar dos Servants diferentes y ejecutar un enfrentamiento por turnos.

La interfaz muestra los personajes seleccionados, sus estadísticas, las acciones disponibles y el resultado de la batalla.

## 5.5 Algorithm Lab / Algoritmos

Permite ejecutar las técnicas algorítmicas desde la interfaz.

Los resultados deben ser generados por las implementaciones Java invocadas por el backend, no por simulaciones independientes en JavaScript.

## 5.6 Complexity Dashboard / Complejidad

Presenta información sobre la complejidad temporal y espacial de los algoritmos.

Las estimaciones deben interpretarse de acuerdo con la implementación concreta, los parámetros utilizados y las estructuras de datos elegidas.

---

# 6. Sistema de combate

El sistema de combate funciona por turnos.

El jugador selecciona dos Servants y utiliza las acciones disponibles en la consola.

## 6.1 Acciones

- **ATAQUE:** aplica daño estándar.
- **NOBLE PHANTASM:** aplica daño aumentado y consume mana.
- **DEFENDER:** reduce el siguiente daño recibido, de acuerdo con las reglas implementadas.

## 6.2 Información presentada

El panel puede mostrar:

- Servant A;
- Servant B;
- imágenes;
- estadísticas;
- barras de HP y MP;
- atacante y defensor;
- acción realizada;
- daño;
- vida restante;
- ganador;
- registro de eventos.

## 6.3 Inteligencia artificial

La IA rival evalúa las acciones posibles mediante una función de utilidad tipo Minimax y utiliza poda alfa-beta para descartar ramas que no pueden mejorar la decisión.

El backend mantiene el estado de la batalla mediante un identificador de sesión.

El flujo general consiste en:

1. iniciar una batalla;
2. seleccionar o recibir una acción;
3. actualizar el estado del combate;
4. calcular los efectos de la acción;
5. permitir la respuesta de la IA;
6. consultar el estado actualizado;
7. determinar si existe una ganadora.

Los detalles de daño, defensa, consumo de mana, evaluación de estados y condiciones de victoria deben coincidir con la implementación real.

---

# 7. Recursos multimedia

## 7.1 Opening

El opening se muestra al ingresar al panel web.

Archivo esperado:

```text
src/main/resources/web/videos/opening.mp4
```

El reproductor puede intentar iniciar automáticamente y mostrar controles.

### Restricciones del navegador

Los navegadores modernos pueden bloquear la reproducción automática con sonido. Por eso debe existir una interacción explícita del usuario, como el botón:

```text
ENTRAR AL ARENA
```

Esta interacción permite iniciar o reanudar el video si el navegador bloqueó el autoplay.

El botón para entrar o saltar el opening debe detener o silenciar correctamente el reproductor, evitando que el audio continúe en segundo plano.

## 7.2 Ending

El ending se reproduce al presionar:

```text
SALIR
```

Archivo esperado:

```text
src/main/resources/web/videos/ending.mp4
```

Antes de iniciar el ending se debe detener el opening para evitar la superposición de audio.

Al presionar:

```text
VOLVER
```

el comportamiento previsto es:

1. pausar el ending;
2. reiniciar su posición de reproducción;
3. silenciar el elemento;
4. cerrar el overlay;
5. regresar a la página inicial.

La detención explícita del reproductor evita que el audio continúe reproduciéndose en segundo plano.

## 7.3 Videos por personaje

Las rutas se declaran en `Datos.VIDEO` o en la estructura equivalente de la implementación.

Ejemplos de nombres de archivo:

```text
/videos/characters/saber.mp4
/videos/rin-tohsaka.mp4
/videos/characters/sakura.mp4
```

Para agregar un video permanente, colocá el MP4 en:

```text
src/main/resources/web/videos/characters/
```

y utilizá el nombre correspondiente a la ruta configurada en `Datos.java`.

La versión contempla un video local de referencia para Rin Tohsaka.

### Cargar un video desde la computadora

La opción:

```text
CARGAR VIDEO LOCAL
```

permite seleccionar un MP4 desde la computadora y reproducirlo inmediatamente en el navegador.

Esta selección es temporal: no incorpora automáticamente el archivo al servidor ni lo hace disponible para otros usuarios.

Para que el video quede integrado permanentemente al proyecto, debe copiarse al directorio correspondiente y configurarse su ruta.

## 7.4 Imágenes de los Servants

Las imágenes se sirven localmente desde:

```text
src/main/resources/web/
```

Si se reemplaza una imagen, debe conservarse el nombre esperado por `Datos.java` o actualizarse la referencia correspondiente.

Las imágenes se utilizan en las fichas, la selección de combate y las pantallas del panel.

El frontend puede contar con una imagen de fallback si un archivo no puede cargarse.

## 7.5 Recomendaciones para los recursos

- Conservá los nombres y las rutas configurados en el código.
- Verificá que el servidor pueda acceder a los archivos estáticos.
- Utilizá archivos propios, licenciados o cuyo uso esté permitido para la entrega académica.
- Si no se incluyen videos en el ZIP por su tamaño o licencia, agregá instrucciones claras para incorporarlos.

---

# 8. API HTTP

El frontend se comunica con el servidor mediante endpoints HTTP.

Las operaciones documentadas para esta versión son:

| Endpoint | Propósito |
|---|---|
| `GET /api/personajes` | Obtener los datos de los Servants |
| `/api/algoritmos` | Acceder a las operaciones del laboratorio algorítmico |
| `/api/complejidad` | Consultar información de complejidad |
| `/api/combate/iniciar` | Crear una sesión de combate |
| `/api/combate/accion` | Procesar una acción del combate |
| `/api/combate/estado` | Consultar el estado de una batalla |

Los métodos HTTP, parámetros, formatos de solicitud y estructuras de respuesta deben verificarse en `WebServer.java`. No se asume que todas las rutas utilicen el mismo método HTTP.

El backend recibe las solicitudes del frontend, ejecuta las operaciones correspondientes y devuelve los resultados.

---

# 9. Estructura del proyecto

La siguiente estructura es orientativa. Debe ajustarse a los archivos que realmente existan en el repositorio.

```text
fate-algorithm-arena/
├── pom.xml
├── README.md
│
├── src/
│   └── main/
│       ├── java/
│       │   └── fate/
│       │       ├── Main.java
│       │       ├── DesktopApp.java
│       │       ├── WebServer.java
│       │       ├── Datos.java
│       │       └── Algoritmos.java
│       │
│       └── resources/
│           └── web/
│               ├── index.html
│               ├── app.js
│               ├── style.css
│               ├── *.jpg
│               └── videos/
│                   ├── opening.mp4
│                   ├── ending.mp4
│                   ├── rin-tohsaka.mp4
│                   └── characters/
│                       └── LEEME.txt
│
├── docs/
│   ├── memoria-tecnica/
│   ├── complejidades/
│   ├── casos-de-prueba/
│   └── uml/
│
└── Datos/
    └── documentación y scripts, si corresponde
```

La carpeta `docs/` puede utilizarse para organizar la memoria técnica, los análisis de complejidad, los casos de prueba y los diagramas UML requeridos por la materia.

---

# 10. Complejidades algorítmicas

Las siguientes complejidades corresponden a implementaciones habituales. El costo exacto depende de las estructuras de datos y del código utilizado.

| Algoritmo | Complejidad temporal | Complejidad espacial / observaciones |
|---|---|---|
| MergeSort | `O(n log n)` | `O(n)` auxiliar en la implementación clásica |
| QuickSort | `O(n log n)` promedio; `O(n²)` peor caso | Depende de la recursión y la implementación |
| Greedy con ordenamiento | `O(n log n)` | Depende del almacenamiento de candidatos |
| Mochila 0/1 con DP | `O(n·P)` | `O(n·P)` con tabla completa |
| BFS | `O(V + E)` | `O(V)` para estructuras de recorrido |
| DFS | `O(V + E)` | `O(V)` para visitados y pila/recursión |
| Dijkstra con selección lineal | `O(V²)` | `O(V)` adicional, sin contar la representación del grafo |
| Floyd-Warshall | `O(V³)` | `O(V²)` para la matriz de distancias |
| Prim con selección lineal | `O(V²)` | Depende de la representación del grafo |
| Kruskal | `O(E log E)` | Depende de las estructuras auxiliares |
| Backtracking | Exponencial en el peor caso | Depende de la profundidad y del estado de búsqueda |
| Exploración exhaustiva | Generalmente exponencial | Depende de la generación y el almacenamiento de combinaciones |
| Branch & Bound | Exponencial en el peor caso | Puede reducir nodos mediante cotas y poda |
| Minimax | `O(b^d)` | Depende de la profundidad y la estrategia de búsqueda |
| Poda alfa-beta | Peor caso `O(b^d)`; puede aproximarse a `O(b^(d/2))` con ordenamiento favorable | No elimina el peor caso exponencial |
| Huffman | `O(k log k)` para construir el árbol | `O(k)` para el árbol y estructuras asociadas |

Donde:

- `n`: cantidad de elementos.
- `P`: presupuesto entero de la mochila.
- `V`: cantidad de vértices.
- `E`: cantidad de aristas.
- `b`: factor de ramificación del árbol de juego.
- `d`: profundidad de búsqueda.
- `k`: cantidad de símbolos distintos procesados por Huffman.

En Huffman, la complejidad de construcción se expresa en función de la cantidad de símbolos distintos, no necesariamente del largo total del texto.

---

# 11. Casos de prueba y criterios de aceptación

Esta sección establece los controles recomendados para verificar el funcionamiento del proyecto.

**Importante:** los casos que no se hayan ejecutado deben considerarse pendientes. No deben presentarse como pruebas exitosas hasta comprobar sus resultados.

## 11.1 MergeSort y QuickSort

- Verificar que la salida esté ordenada según el criterio elegido.
- Comprobar que no se pierdan ni dupliquen elementos.
- Probar arreglos vacíos o unitarios cuando corresponda.
- Confirmar el sentido del ranking: mayor a menor poder o viceversa.

## 11.2 Greedy

- Comprobar que el costo total no supere el presupuesto.
- Verificar que los candidatos se evalúen según el ratio poder/costo.
- Comparar con un resultado óptimo de referencia en instancias pequeñas.
- Documentar que Greedy no garantiza el óptimo global en todos los casos.

## 11.3 Mochila 0/1 y optimización de combos

- Verificar que no se supere el presupuesto o la restricción de recursos.
- Comparar el resultado con casos pequeños cuyo óptimo pueda calcularse manualmente.
- Comprobar el tratamiento de presupuestos nulos y elementos demasiado costosos.
- Verificar que la reconstrucción de la solución coincida con el valor óptimo obtenido.

## 11.4 Backtracking y exploración exhaustiva

- Verificar que se respeten todas las restricciones.
- Comprobar que no se repitan candidatos cuando esté prohibido.
- Probar casos sin solución y casos con varias soluciones.
- Para ocho reinas, comprobar que ninguna reina comparta fila, columna o diagonal.

## 11.5 Branch & Bound

- Comparar con una solución exacta de referencia en instancias pequeñas.
- Verificar que las cotas no descarten soluciones óptimas.
- Comprobar el comportamiento cuando no existe una solución factible.

## 11.6 BFS, DFS y detección de ciclos

- Probar grafos conectados y desconectados.
- Comprobar que los nodos visitados no se procesen repetidamente.
- En BFS, verificar distancias mínimas por cantidad de aristas en grafos no ponderados.
- En DFS, comprobar el recorrido en profundidad.
- En detección de ciclos, probar grafos con y sin ciclos.

## 11.7 Dijkstra y Floyd-Warshall

- Comparar distancias con ejemplos pequeños calculados manualmente.
- Verificar el tratamiento de nodos inaccesibles.
- Para Dijkstra, confirmar que no se admitan pesos negativos.
- Para Floyd-Warshall, revisar la inicialización de la matriz y las distancias entre todos los pares.

## 11.8 Prim y Kruskal

- Comprobar que el resultado conecte los vértices cuando el grafo es conexo.
- Verificar el costo total del árbol de recubrimiento mínimo.
- Probar grafos desconectados.
- Documentar si se devuelve un bosque de recubrimiento mínimo o se informa que no existe un árbol que conecte todos los vértices.

## 11.9 Minimax y poda alfa-beta

- Verificar que la IA elija una acción válida.
- Probar estados sencillos con una mejor jugada conocida.
- Comparar el valor de Minimax con y sin poda en instancias pequeñas.
- Comprobar que la poda alfa-beta no modifique el valor minimax obtenido.

## 11.10 Huffman

- Verificar el conteo de frecuencias.
- Comprobar la construcción del árbol.
- Confirmar que los códigos generados sean códigos de prefijo.
- Verificar que la decodificación recupere el texto original, si está implementada.
- Comparar la cantidad de bits con una codificación de referencia y contemplar el costo de almacenar la tabla o el árbol.

## 11.11 Combate y API

- Crear un combate con dos Servants válidos.
- Comprobar que ataque, Noble Phantasm y defensa respeten las reglas implementadas.
- Verificar el consumo de mana, la reducción de HP y la condición de victoria.
- Comprobar que las consultas de estado reflejen las acciones procesadas.
- Probar identificadores inválidos, acciones desconocidas y sesiones inexistentes.
- Verificar que los endpoints devuelvan respuestas y errores coherentes.

## 11.12 Multimedia e interfaz

- Verificar que el opening aparezca al ingresar.
- Probar la reproducción con y sin autoplay permitido.
- Comprobar que entrar o saltar el opening detenga su audio.
- Verificar que `SALIR` inicie el ending sin superposición de audio.
- Comprobar que `VOLVER` detenga y reinicie el ending y regrese a la pantalla inicial.
- Probar la reproducción de videos de personajes.
- Comprobar la carga de un MP4 local.
- Verificar el comportamiento ante imágenes o videos inexistentes.

---

# 12. Ejecución del proyecto

## 12.1 Requisitos

- **JDK 21** instalado.
- Maven para utilizar los comandos Maven.
- Navegador moderno para acceder al panel web.

Verificá la instalación de Java:

```bash
java -version
javac -version
```

## 12.2 Ejecución con Maven

Desde la raíz del proyecto, compilar y empaquetar:

```bash
mvn clean package
```

Para iniciar la aplicación en modo web:

```bash
java -cp target/fate-algorithm-arena-5.0.0.jar fate.Main web
```

Esta instrucción supone que el JAR generado contiene las clases necesarias en ese formato. Si Maven genera un archivo con otro nombre o se requieren dependencias adicionales, utilizá el artefacto definido en `pom.xml`.

Alternativamente, si el plugin de ejecución está configurado:

```bash
mvn exec:java -Dexec.mainClass=fate.Main -Dexec.args=web
```

Después, abrir en el navegador:

```text
http://localhost:8080
```

## 12.3 Ejecución sin Maven

Desde la raíz del proyecto, utilizando JDK 21:

```bash
javac -encoding UTF-8 -d out src/main/java/fate/*.java
```

Luego:

```bash
java -cp out fate.Main web
```

La ejecución directa supone que el proyecto no requiere dependencias adicionales y que los recursos se sirven desde las rutas esperadas. Si no se cumple esta condición, utilizá Maven y revisá la configuración del proyecto.

## 12.4 Acceso desde Swing

Si la aplicación de escritorio ofrece la opción correspondiente, seleccioná:

```text
Abrir versión Web
```

El servidor debe estar iniciado antes de acceder a `http://localhost:8080`.

---

# 13. Referencias y recursos gráficos

Los siguientes recursos provienen de enlaces de referencia utilizados durante el desarrollo. Verificá que los archivos estén disponibles localmente y que su uso sea adecuado para la entrega.

## 13.1 Opening

Recurso de referencia:

https://github.com/user-attachments/assets/474988b0-5d95-46a1-aa82-d38faf0d1196

El archivo utilizado por el proyecto debe llamarse:

```text
opening.mp4
```

y ubicarse en:

```text
src/main/resources/web/videos/
```

## 13.2 Ending

Recurso de referencia:

https://github.com/user-attachments/assets/520b85e6-a684-4a7d-a29a-aef80a8a54f3

El archivo utilizado por el proyecto debe llamarse:

```text
ending.mp4
```

y ubicarse en:

```text
src/main/resources/web/videos/
```

## 13.3 Rin Tohsaka

Referencia visual:

https://github.com/user-attachments/assets/fd2bf63b-bd27-44f2-8c2c-66371c36844f

Imagen adicional:

<img width="735" height="490" alt="Referencia visual de Rin Tohsaka" src="https://github.com/user-attachments/assets/af12e14e-279d-4165-aa6b-48d4aa275aeb" />

El archivo local puede almacenarse como:

```text
Rin Tohsaka.jpg
```

en:

```text
src/main/resources/web/
```

Si se reemplaza el archivo, debe conservarse el nombre esperado por el código o actualizarse la ruta correspondiente.

---

# 13.4 Arturia Pendragon

https://github.com/user-attachments/assets/52f86456-5546-4a48-8a1a-d8ccec5d1d0f

<img width="735" height="517" alt="fate grend orter" src="https://github.com/user-attachments/assets/2ca3d26b-1922-4d44-8128-a1f4e2243101" />

# Enlace de anime

https://vww.animeflv.one/ver/fate-stay-night-1

# Enlace del manga

https://mws3a.com/encyclopedia-of-weight-loss/

# 14. Resumen del proyecto

**FATE / Algorithm War 5.0** integra:

```text
Java 21
│
├── Programación procedural
├── Arreglos paralelos y métodos estáticos
│
├── Ordenamiento
│   ├── MergeSort
│   └── QuickSort
│
├── Optimización
│   ├── Greedy
│   ├── Programación dinámica
│   ├── Mochila 0/1
│   └── Branch & Bound
│
├── Búsqueda
│   ├── Backtracking
│   ├── Exploración exhaustiva
│   └── Ocho reinas
│
├── Grafos
│   ├── BFS
│   ├── DFS
│   ├── Dijkstra
│   ├── Floyd-Warshall
│   ├── Detección de ciclos
│   ├── Prim
│   └── Kruskal
│
├── Inteligencia de juegos
│   ├── Minimax
│   └── Poda alfa-beta
│
├── Compresión
│   └── Huffman
│
├── Java Swing
├── HttpServer y API HTTP
├── HTML, CSS y JavaScript
│
├── Archivo de Servants
├── Fichas e historias individuales
├── Combate interactivo con IA
├── Laboratorio de algoritmos
├── Panel de complejidad
│
├── Imágenes locales
├── Videos por personaje
├── Opening
└── Ending
```

El resultado es un trabajo práctico integrador que conecta técnicas algorítmicas con una aplicación visual e interactiva, respetando la restricción de no modelar los personajes mediante POO de dominio tradicional.

La documentación describe el alcance previsto del proyecto. La versión definitiva debe reflejar las funcionalidades que estén implementadas y puedan comprobarse durante la compilación y la ejecución.
