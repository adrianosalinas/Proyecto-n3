# RompeBloques: Sistema de Contención Magnética Aegis-7

Videojuego arcade clásico de estilo *Brick Breaker* desarrollado en **Java** utilizando el framework de alto rendimiento **LibGDX**. Este proyecto integra patrones avanzados de diseño orientados a objetos (POO) y una arquitectura desacoplada para gestionar estados de juego, físicas de colisiones dinámicas y renderizado por fotogramas.

---

##  Prerrequisitos del Sistema

Antes de iniciar la importación en Eclipse, asegúrese de contar con las siguientes herramientas instaladas y configuradas:

1. **Java Development Kit (JDK):** JDK 8 o JDK 11 (con variables de entorno `JAVA_HOME` correctamente configuradas).
2. **Eclipse IDE:** Versión de Eclipse para Desarrolladores Java (versión 2021-03 o superior recomendada).
3. **Plugin Eclipse Buildship:** Herramienta integrada en las versiones modernas de Eclipse para el soporte nativo de proyectos basados en **Gradle**. (Si no está disponible, puede instalarse desde el *Eclipse Marketplace* buscando "Buildship Gradle Integration").

---

##  Instrucciones de Instalación en Eclipse

Dado que el proyecto utiliza **Gradle** para la gestión de dependencias del ciclo de vida del framework LibGDX, no debe importarse como un proyecto Java estándar. Siga estos pasos estrictos:

### Paso 1: Clonar o Descargar el Proyecto
* Descargue el código fuente en un directorio local de su máquina y extraiga el archivo `.zip` (si aplica).

### Paso 2: Importación a Eclipse
1. Abra **Eclipse IDE** y seleccione su espacio de trabajo (*Workspace*).
2. Diríjase al menú superior y haga clic en **File** -> **Import...**
3. En la ventana emergente, expanda la carpeta **Gradle** y seleccione **Existing Gradle Project**. Haga clic en *Next*.
4. En el apartado **Project root directory**, haga clic en *Browse...* y seleccione la carpeta raíz del proyecto (donde se encuentra el archivo `build.gradle`).
5. Haga clic en *Next* en las pantallas de configuración de Gradle (se recomienda dejar las opciones predeterminadas que detectan automáticamente el *Gradle Wrapper* del sistema).
6. Presione **Finish**. Eclipse comenzará a sincronizar el modelo Gradle y a descargar las dependencias necesarias de LibGDX (este proceso inicial puede demorar unos minutos dependiendo de su conexión a internet).

Al finalizar la sincronización, observará en el *Project Explorer* la estructura modular del proyecto (comúnmente dividida en los subproyectos `core` y `desktop`).

---

##  Instrucciones de Ejecución

Para iniciar el videojuego desde el entorno de Eclipse, se debe lanzar la aplicación nativa de escritorio:

1. En el panel **Project Explorer**, expanda el subproyecto modular enfocado en la ejecución de escritorio (generalmente nombrado como **`desktop`**).
2. Navegue por los paquetes hasta localizar la clase lanzadora principal, habitualmente denominada **`DesktopLauncher.java`** (Ubicada en `src/` o `src/main/java`).
3. Haga clic derecho sobre el archivo `DesktopLauncher.java`.
4. En el menú contextual, seleccione **Run As** -> **Java Application**.
5. La ventana gráfica de LibGDX se desplegará de inmediato inicializando el lienzo virtual a una resolución interna de **800 unidades de ancho**.

*Nota: Si se presenta un error de carga de recursos (`AssetError`), verifique en las configuraciones de lanzamiento de Eclipse (`Run Configurations...`) que el "Working Directory" esté apuntando correctamente a la carpeta del submódulo `assets` del proyecto.*

---

##  Controles del Operador

Una vez que la simulación dimensional esté activa en el estado `JUGANDO`, los controles de la paleta magnética responden a:
* **Desplazamiento a la Izquierda:** Presione la flecha direccional **`LEFT`** (Izquierda) o la tecla convencional **`A`**.
* **Desplazamiento a la Derecha:** Presione la flecha direccional **`RIGHT`** (Derecha) o la tecla convencional **`D`**.
* **Estructura del Escenario:** La paleta cuenta con restricciones matemáticas internas que le impiden exceder los márgenes perimetrales de la pantalla (`800` unidades de ancho virtual).

---

##  Resumen Arquitectónico del Código (`io.github.some_example_name`)

El proyecto ha sido completamente refactorizado para cumplir con los estándares de diseño limpio (Clean Code) y principios SOLID evaluados en los requerimientos del módulo:

* **GM-4 (Clase Abstracta e Interfaz):** La clase abstracta base `ElementoJuego` rige el ciclo por fotograma, mientras que la interfaz `Colisionable` define el contrato geométrico y de reacción ante impactos implementado por `Paddle` y `Block`.
* **GM-5 (Encapsulamiento):** Protección estricta de variables de estado con visibilidad `private` / `protected` y acceso controlado por métodos mutadores y de lectura validados.
* **GM-6 (Patrón Singleton):** Centralización del estado de la sesión, vidas, nivel actual y puntaje en la clase única global `GestorJuego`.
* **GM-7 (Patrón Strategy):** Variación en tiempo de ejecución del comportamiento mecánico y de dificultad mediante las implementaciones `EasyStrategy`, `NormalStrategy` y `HardStrategy`.
* **GM-8 (Patrón Template Method):** Esqueleto algorítmico inmutable estructurado en `ElementoJuego.procesarFrame()`, delegando pasos específicos a los ganchos (*hooks*) de las subclases de manera segura.
* **GM-9 (Patrón Builder):** Construcción desacoplada y fluida de componentes individuales y grillas complejas a través de `BlockBuilder` y `NivelBuilder`.
