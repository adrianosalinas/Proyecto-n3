package io.github.some_example_name;



public class GestorJuego {
    // 1. Instancia única privada del Singleton
    private static GestorJuego instancia;

    // Enumeración para controlar los estados de la pantalla
    public enum Estado { JUGANDO, GAME_OVER, PAUSA }

    private int vidas;
    private int puntaje;
    private int nivel;
    private Estado estadoActual;
    public void alternarPausa() {
        if (this.estadoActual == Estado.JUGANDO) {
            this.estadoActual = Estado.PAUSA;
        } else if (this.estadoActual == Estado.PAUSA) {
            this.estadoActual = Estado.JUGANDO;
        }
    }
    // 2. Constructor privado para evitar que otras clases usen "new GestorJuego()"
    private GestorJuego() {
        resetearJuego();
    }

    // 3. Método público global para obtener la única instancia activa
    public static GestorJuego getInstancia() {
        if (instancia == null) {
            instancia = new GestorJuego();
        }
        return instancia;
    }

    // Lógica de Reinicio Completo exigida
    public void resetearJuego() {
        this.vidas = 3;
        this.puntaje = 0; 
        this.nivel = 1;
        this.estadoActual = Estado.JUGANDO;
    }

    // Métodos de comportamiento encapsulados
    public void restarVida() {
        this.vidas--;
        if (this.vidas <= 0) {
            this.estadoActual = Estado.GAME_OVER; 
        }
    }

    public void sumarPuntaje() {
        this.puntaje++;
    }

    public void avanzarNivel() {
        this.nivel++;
    }

    // Getters y Setters encapsulados 
    public int getVidas() { return vidas; }
    public int getPuntaje() { return puntaje; }
    public int getNivel() { return nivel; }
    public Estado getEstadoActual() { return estadoActual; }
}