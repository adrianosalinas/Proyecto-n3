package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

//Hereda de ElementoJuego e implementa Colisionable
public class Paddle extends ElementoJuego implements Colisionable {
    
    public Paddle(int x, int y, int ancho, int alto) {
        super(x, y, ancho, alto); // Pasa los datos al padre
    }

    @Override
    public void draw(ShapeRenderer shape){
        shape.setColor(Color.BLUE);
        
        // Solo permitimos mover la paleta si el estado es JUGANDO
        if (GestorJuego.getInstancia().getEstadoActual() == GestorJuego.Estado.JUGANDO) {
            int x2 = x;
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) x2 = x - 7;
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x2 = x + 7; 
            if (Gdx.input.isKeyPressed(Input.Keys.A)) x2 = x - 7;
            if (Gdx.input.isKeyPressed(Input.Keys.D)) x2 = x + 7; 
            if (x2 > 0 && x2 + width < 800) { 
                x = x2;
            }
        }
        
        // Dibujamos el rectángulo de la paleta siempre (incluso en pausa)
        shape.rect(x, y, width, height);
    }
    @Override
    public void reaccionarAlGolpe() {}
        // La paleta es indestructible, no hacemos nada con su estado.
    
}