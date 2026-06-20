package io.github.some_example_name;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

// PingBall hereda de la clase abstracta
public class PingBall extends ElementoJuego {
    
    // Eliminamos 'x' e 'y' de aquí porque ya se heredan de ElementoJuego
    private int size;
    private int xSpeed;
    private int ySpeed;
    private Color color = Color.WHITE;
    private boolean estaQuieto;
    
    public PingBall(int x, int y, int size, int xSpeed, int ySpeed, boolean iniciaQuieto) {
        super(x, y, size, size); 
        
        this.size = size;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.estaQuieto = iniciaQuieto;
    }
    
    // Métodos Getter y Setter propios de PingBall
    public boolean estaQuieto() {
        return estaQuieto;
    }
    
    public void setEstaQuieto(boolean bb) {
        estaQuieto = bb;
    }
    
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Implementar el método abstracto del padre
    @Override
    public void draw(ShapeRenderer shape){
        shape.setColor(color);
        // Usamos 'x' e 'y' que vienen protegidos desde ElementoJuego
        shape.circle(x, y, size); 
    }
    
    public void update() {
        if (estaQuieto) return;
        
        x += xSpeed;
        y += ySpeed;
        
        // ancho estatico de la pantalla
        if (x - size < 0 || x + size > 800) { 
            xSpeed = -xSpeed;
        }
        
        // alto estatico de la pantalla
        if (y + size > 480) { 
            ySpeed = -ySpeed;
        }
    }
    
    public void checkCollision(Colisionable elemento) {
        if(collidesWith(elemento)){
            color = Color.GREEN;
            ySpeed = -ySpeed;
            
            elemento.reaccionarAlGolpe(); 
        }
    }
    public void resetColor() {
        this.color = Color.WHITE;
    }
    
    // Usamos la interfaz Colisionable para hacer el método más universal
    private boolean collidesWith(Colisionable elemento) {
        boolean intersectaX = (elemento.getX() + elemento.getWidth() >= x - size) && (elemento.getX() <= x + size);
        boolean intersectaY = (elemento.getY() + elemento.getHeight() >= y - size) && (elemento.getY() <= y + size);        
        return intersectaX && intersectaY;
    }
}