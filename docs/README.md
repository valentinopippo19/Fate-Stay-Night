# Fate/stay night — Algorithm Arena 2.0 / V4

## Trabajo Práctico Integrador en Java

Proyecto académico inspirado en **Fate/stay night**, orientado a la aplicación práctica de técnicas clásicas de algoritmos y estructuras de datos.

La propuesta combina una interfaz gráfica de escritorio, un panel web interactivo, un sistema de combate entre Servants y diferentes algoritmos de optimización, búsqueda, grafos y compresión.

> **Objetivo académico:** demostrar el uso de algoritmos sin construir el dominio mediante POO tradicional. La información de los personajes se mantiene en arreglos y la lógica se implementa mediante procedimientos y métodos estáticos.

<img width="2400" height="1350" alt="portada" src="https://github.com/user-attachments/assets/0df317cd-3d66-46f8-ac84-c3497b261937" />

# 1. Técnicas algorítmicas implementadas

El proyecto incorpora las cuatro técnicas obligatorias del trabajo práctico y agrega algoritmos complementarios.

### 1.1 Divide y Conquista — Merge Sort

Se utiliza **Merge Sort** para ordenar/rankear los Servants según su nivel de poder.

- Divide el conjunto en dos partes.
- Ordena recursivamente cada mitad.
- Combina ambas partes ordenadas.
- Complejidad temporal: **O(n log n)**.
- Complejidad espacial: **O(n)**.

### 1.2 Algoritmos Voraces — Greedy

Selecciona Servants intentando maximizar la relación:

```text
poder / costo
```

Se establece un presupuesto máximo y se van incorporando los candidatos con mejor eficiencia mientras sea posible.

- Estrategia local: elegir el mejor ratio disponible.
- Complejidad de ordenamiento: **O(n log n)**.
- La solución greedy no necesariamente coincide con el óptimo global de todos los casos.

### 1.3 Programación Dinámica — Mochila 0/1

Se utiliza el problema clásico de **Knapsack 0/1**.

Cada Servant posee:

- poder;
- costo.

El objetivo es obtener el máximo poder posible sin superar un presupuesto.

Complejidad aproximada:

```text
O(n · P)
```

donde `n` es la cantidad de Servants y `P` el presupuesto.

### 1.4 Backtracking

Se utiliza búsqueda con retroceso para construir equipos que cumplan determinadas restricciones.

El algoritmo:

1. selecciona un candidato;
2. verifica restricciones;
3. continúa recursivamente;
4. retrocede si la elección no permite completar una solución;
5. conserva una combinación válida.

La complejidad en el peor caso es exponencial, aunque las restricciones permiten realizar poda.

---

# 2. Algoritmos adicionales

La versión ampliada incorpora tres familias adicionales.

## 2.1 Grafos — BFS y Dijkstra

Los Servants pueden representarse como vértices de un grafo.

Las relaciones de afinidad o similitud se utilizan para determinar las aristas.

### BFS

**Breadth-First Search** permite recorrer el grafo por niveles y obtener caminos mínimos cuando las aristas tienen el mismo costo.

Complejidad:

```text
O(V + E)
```

donde:

- `V` = cantidad de vértices;
- `E` = cantidad de aristas.

### Dijkstra

Permite obtener distancias mínimas cuando las conexiones poseen costos no negativos.

En este proyecto puede interpretarse como la búsqueda del camino de menor costo entre Servants relacionados.

---

## 2.2 Huffman

Se incorpora **Huffman Coding** como algoritmo de compresión sin pérdida.

Se utiliza una historia textual de un Servant para demostrar:

1. cálculo de frecuencias;
2. construcción del árbol;
3. selección de los símbolos menos frecuentes;
4. generación de códigos binarios;
5. estimación de la cantidad de bits utilizada.

Complejidad aproximada:

```text
O(n log n)
```

para la construcción del árbol mediante una cola de prioridad.

---

# 3. Servants

El panel utiliza diez personajes femeninos asociados al universo Fate:

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

Cada registro contiene información como:

- nombre;
- identidad;
- clase;
- Master;
- historia;
- Noble Phantasm;
- poder;
- vida;
- costo;
- imagen.

Los datos se almacenan proceduralmente mediante arreglos en `Datos.java`.

---

# 4. Panel web

La aplicación cuenta con un panel web accesible mediante:

```text
http://localhost:8080
```

La interfaz se divide en cuatro sectores principales.

## Arena

Página principal del proyecto.

Presenta:

- introducción;
- cantidad de Servants;
- cantidad de familias de algoritmos;
- personajes destacados.

## Historial

Permite visualizar las fichas de los Servants.

Cada tarjeta muestra:

- imagen;
- nombre;
- identidad;
- clase;
- Master;
- historia;
- estadísticas;
- Noble Phantasm.

También incluye búsqueda por personaje, clase o Master.

## Combate

Permite seleccionar dos Servants diferentes y ejecutar un combate.

El panel muestra:

- Servant A;
- Servant B;
- imágenes;
- estadísticas;
- resultado;
- vida restante;
- registro del combate.

## Algoritmos

El panel permite ejecutar visualmente las diferentes técnicas:

- Merge Sort;
- Greedy;
- Programación Dinámica;
- Backtracking;
- BFS / Grafos;
- Huffman.

Los resultados aparecen directamente en el panel web.

---

# 5. Sistema de combate

El sistema de combate funciona por turnos.

El usuario selecciona:

```text
SERVANT A
VS
SERVANT B
```

El servidor recibe los identificadores mediante la API y calcula el enfrentamiento.

Se muestran:

- atacante;
- defensor;
- daño;
- vida;
- ganador;
- registro de eventos.

El combate se utiliza como una capa interactiva para relacionar los datos de los Servants con el resto del proyecto.

---

# 6. Imágenes locales

En la versión V4 las imágenes de los personajes se sirven **localmente** desde el proyecto.

Ubicación:

```text
src/main/resources/web/
```

Archivos esperados:

```text
Saber Artoria.jpg
Rin Tohsaka.jpg
Sakura Matou.jpg
Rider Medusa.jpg
Illyasviel von Einzbern.jpg
Saber Alter.jpg
Jeanne d'Arc.jpg
Scathach.jpg
Mash Kyrielight.jpg
Nero Claudius.jpg
```

Esto evita depender de imágenes alojadas en servidores externos.

Las imágenes aparecen en:

- Arena;
- Historial;
- selección de combate;
- fichas de Servants.

El JavaScript utiliza rutas locales y posee una imagen de fallback si algún archivo no puede cargarse.

---

# 7. Videos — Opening y Ending

La versión V4 incorpora un ciclo completo de reproducción multimedia.

## Opening

El opening se muestra al ingresar al panel web.

Archivo:

```text
src/main/resources/web/videos/opening.mp4
```

El reproductor:

- intenta iniciar automáticamente;
- utiliza volumen;
- muestra controles;
- permite entrar al panel;
- permite saltar el opening;
- detiene completamente el audio cuando se salta o finaliza.

### Restricción del navegador

Los navegadores modernos pueden bloquear el **autoplay con sonido**.

Por eso el proyecto intenta reproducir el opening con volumen y, si el navegador bloquea la reproducción automática, el botón:

```text
ENTRAR AL ARENA
```

permite iniciar/reanudar el video mediante una interacción explícita del usuario.

---

# 8. Ending

El ending se reproduce al presionar:

```text
SALIR
```

Archivo:

```text
src/main/resources/web/videos/ending.mp4
```

Antes de iniciar el ending se detiene el opening para evitar superposición de audio.

El ending se reproduce con volumen y dispone de controles.

Cuando el usuario presiona:

```text
VOLVER
```

el programa:

1. pausa el ending;
2. lleva `currentTime` a cero;
3. silencia el elemento;
4. cierra el overlay;
5. recarga la página.

De esta forma el audio del ending no continúa reproduciéndose en segundo plano.

---

# 9. Ciclo multimedia

El comportamiento general es:

```text
INICIO
  │
  ▼
OPENING + AUDIO
  │
  ├── ENTAR AL ARENA ──────► PANEL WEB
  │
  └── SALTAR OPENING ─────► PANEL WEB
                              │
                              ▼
                           SALIR
                              │
                              ▼
                         ENDING + AUDIO
                              │
                              ▼
                           VOLVER
                              │
                              ▼
                    AUDIO DETENIDO
                              │
                              ▼
                         PÁGINA INICIAL
```

La detención explícita de los elementos `<video>` evita que el audio quede activo al cambiar de pantalla.

---

# 10. Estructura del proyecto

```text
fate-stay-night-algorithm-arena/
│
├── pom.xml
├── README.md
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── fate/
│   │   │       ├── Main.java
│   │   │       ├── Algoritmos.java
│   │   │       ├── Datos.java
│   │   │       ├── DesktopApp.java
│   │   │       └── WebServer.java
│   │   │
│   │   └── resources/
│   │       └── web/
│   │           ├── index.html
│   │           ├── style.css
│   │           ├── app.js
│   │           ├── *.jpg
│   │           └── videos/
│   │               ├── opening.mp4
│   │               └── ending.mp4
│
├── docs/
│   ├── memoria-tecnica/
│   ├── complejidades/
│   ├── casos-de-prueba/
│   └── uml/
│
└── Datos/
    └── documentación y scripts
```

---

# 11. Organización del código

### `Algoritmos.java`

Contiene la lógica algorítmica.

Incluye las implementaciones relacionadas con:

- Merge Sort;
- Greedy;
- Knapsack 0/1;
- Backtracking;
- BFS;
- Dijkstra;
- Huffman;
- operaciones auxiliares.

### `Datos.java`

Contiene la información procedural de los personajes.

No se utiliza una clase `Servant` para modelar cada personaje.

### `DesktopApp.java`

Contiene la interfaz gráfica desarrollada con **Java Swing**.

Su función principal es presentar la aplicación de escritorio y proporcionar acceso al panel web.

### `WebServer.java`

Implementa el servidor HTTP utilizando:

```java
com.sun.net.httpserver.HttpServer
```

Expone:

- archivos estáticos;
- endpoints de personajes;
- endpoint de combate;
- comunicación con el frontend.

### `index.html`

Define la estructura del panel web.

### `style.css`

Define la presentación visual.

### `app.js`

Controla:

- navegación;
- renderizado de personajes;
- imágenes;
- selección de Servants;
- combate;
- ejecución de algoritmos;
- reproducción del opening;
- reproducción del ending;
- detención del audio.

---

# 12. Principio de diseño: sin POO de dominio

Una condición importante del trabajo es que los algoritmos se implementen sin construir un modelo de dominio basado en objetos.

Por eso se evita crear:

```text
class Servant
class Character
class Team
class Repository
class Factory
class Strategy
```

y jerarquías de herencia destinadas exclusivamente al dominio.

En cambio, los datos se manejan mediante:

```text
String[]
int[]
double[]
boolean[]
```

y métodos/procedimientos estáticos.

La POO se utiliza únicamente donde resulta necesaria para el framework o la interfaz, por ejemplo:

- Swing;
- servidor HTTP;
- clases técnicas de Java.

---

# 13. Ejecución

## Maven

Desde la raíz:

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass=fate.Main
```

## Java directo

```bash
javac -d out src/main/java/fate/*.java
java -cp out fate.Main
```

## Servidor web

Desde la aplicación de escritorio:

```text
Abrir versión Web
```

Luego acceder a:

```text
http://localhost:8080
```

También puede iniciarse directamente:

```bash
java -cp out fate.WebServer
```

---

# 14. Recursos multimedia

Los archivos multimedia deben ubicarse en:

```text
src/main/resources/web/videos/
```

con los nombres:

```text
opening.mp4
ending.mp4
```

Se recomienda utilizar archivos propios, licenciados o cuyo uso esté permitido para la entrega académica.

La aplicación no necesita modificar el código Java para reemplazar estos videos: basta con sustituir los archivos manteniendo sus nombres.

---

# 15. Imágenes

Las imágenes se ubican directamente en:

```text
src/main/resources/web/
```

Si se reemplaza una imagen, debe conservarse el nombre utilizado por `Datos.java`.

Ejemplo:

```text
Rin Tohsaka.jpg
```

Si se desea utilizar otro nombre, debe actualizarse la ruta correspondiente en `Datos.java`.

---

# 16. APIs principales

El frontend se comunica con el servidor mediante endpoints HTTP.

Entre las operaciones principales se encuentran:

```text
GET  /api/personajes
POST /api/combate
```

La respuesta de personajes permite construir las tarjetas dinámicamente.

El endpoint de combate recibe los identificadores de los dos Servants seleccionados y devuelve el resultado del enfrentamiento.

---

# 17. Flujo general del sistema

```text
                    ┌──────────────────┐
                    │     Main.java    │
                    └────────┬─────────┘
                             │
                ┌────────────┴────────────┐
                ▼                         ▼
        ┌──────────────┐          ┌──────────────┐
        │ DesktopApp   │          │ WebServer    │
        │    Swing     │          │ HttpServer   │
        └──────────────┘          └──────┬───────┘
                                         │
                                         ▼
                                  ┌──────────────┐
                                  │   Frontend   │
                                  │ HTML/CSS/JS  │
                                  └──────┬───────┘
                                         │
                   ┌─────────────────────┼─────────────────────┐
                   ▼                     ▼                     ▼
              Personajes              Combate             Algoritmos
                   │                     │                     │
                   └─────────────────────┴─────────────────────┘
                                         │
                                         ▼
                                  ┌──────────────┐
                                  │ Algoritmos   │
                                  │ estáticos    │
                                  └──────────────┘
```

---

# 18. Complejidades

| Algoritmo | Técnica | Complejidad aproximada |
|---|---|---|
| Merge Sort | Divide y Conquista | O(n log n) |
| Greedy | Voraz | O(n log n) |
| Knapsack 0/1 | Programación Dinámica | O(n·P) |
| Backtracking | Retroceso | Exponencial |
| BFS | Grafos | O(V+E) |
| Dijkstra | Grafos | O(V²) o O((V+E) log V) según implementación |
| Huffman | Compresión | O(n log n) |

---

# 19. Casos de prueba recomendados

## Merge Sort

Verificar que:

- los Servants queden ordenados;
- el mayor poder aparezca primero;
- no se pierdan elementos.

## Greedy

Verificar que:

- nunca se supere el presupuesto;
- se priorice la relación poder/costo.

## Knapsack

Verificar que:

- el costo total no supere el presupuesto;
- el poder obtenido sea máximo para el presupuesto utilizado.

## Backtracking

Verificar que:

- no se repitan clases;
- se respeten las restricciones;
- el algoritmo pueda retroceder ante una combinación inválida.

## BFS

Verificar:

- recorrido desde un nodo inicial;
- ausencia de visitas duplicadas;
- distancia mínima en grafos no ponderados.

## Dijkstra

Verificar:

- distancias mínimas;
- actualización correcta de costos;
- ausencia de pesos negativos.

## Huffman

Verificar:

- cálculo de frecuencias;
- construcción del árbol;
- generación de códigos;
- reducción de bits respecto de una representación fija cuando corresponda.

## Combate

Verificar:

- dos Servants distintos;
- reducción de vida;
- existencia de una ganadora;
- registro correcto del combate.

## Multimedia

Verificar:

- opening al iniciar;
- sonido del opening;
- salto del opening;
- ausencia de audio después de saltarlo;
- ending al salir;
- sonido del ending;
- detención completa del ending al presionar `VOLVER`.

---

# 20. Opening

Recurso de referencia:

https://github.com/user-attachments/assets/474988b0-5d95-46a1-aa82-d38faf0d1196

El archivo local utilizado por el proyecto debe llamarse:

```text
opening.mp4
```

y colocarse en:

```text
src/main/resources/web/videos/
```

---

# 21. Ending

Recurso de referencia:

https://github.com/user-attachments/assets/520b85e6-a684-4a7d-a29a-aef80a8a54f3

El archivo local utilizado por el proyecto debe llamarse:

```text
ending.mp4
```

y colocarse en:

```text
src/main/resources/web/videos/
```

---

# 22. Referencia visual — Rin Tohsaka

https://github.com/user-attachments/assets/fd2bf63b-bd27-44f2-8c2c-66371c36844f

La imagen final utilizada por el panel puede almacenarse localmente con el nombre:

```text
Rin Tohsaka.jpg
```

---

# 23. Entrega

Para una entrega académica completa se recomienda verificar que el ZIP contenga:

- código fuente;
- `pom.xml`;
- README;
- documentación;
- diagramas;
- casos de prueba;
- imágenes;
- videos o instrucciones para agregarlos;
- frontend web;
- implementación de algoritmos;
- aplicación Swing;
- servidor HTTP.

---

# 24. Resumen del proyecto

**Fate/stay night — Algorithm Arena** combina:

```text
Java
│
├── Programación procedural
├── Divide y Conquista
├── Greedy
├── Programación Dinámica
├── Backtracking
├── Grafos
├── BFS
├── Dijkstra
├── Huffman
│
├── Java Swing
├── HttpServer
├── HTML
├── CSS
├── JavaScript
│
├── Sistema de combate
├── Base procedural de Servants
├── Imágenes locales
├── Opening
└── Ending
```

El resultado es un trabajo práctico integrador que relaciona los contenidos algorítmicos con una aplicación visual e interactiva, manteniendo la restricción de no utilizar POO de dominio para representar los personajes y equipos.
