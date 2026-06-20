package io.github.some_example_name;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import java.util.Random;

//Hereda de ElementoJuego e implementa Colisionable
public class Block extends ElementoJuego implements Colisionable {
    
    //Atributos privados (encapsulamiento)
    private Color cc;
    private boolean destroyed;
    
    public Block(int x, int y, int width, int height) {
        super(x, y, width, height); // Llama al constructor de la clase abstracta
        this.destroyed = false;
        Random r = new Random(x + y);
        this.cc = new Color(0.1f + r.nextFloat(), r.nextFloat(), r.nextFloat(), 1f);
    }

    @Override
    public void draw(ShapeRenderer shape) {
        shape.setColor(cc);
        shape.rect(x, y, width, height); // x, y, width, height vienen de ElementoJuego
    }

    //Getters y Setters para las propiedades de la clase
    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
    @Override
    public void reaccionarAlGolpe() {
        this.setDestroyed(true); // El bloque cambia su estado a destruido
    }
}