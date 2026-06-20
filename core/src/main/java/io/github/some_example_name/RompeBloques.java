package io.github.some_example_name;

import java.util.ArrayList;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class RompeBloques extends ApplicationAdapter {
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;       
    private BitmapFont font;
    private ShapeRenderer shape;
    private PingBall ball;
    private Paddle pad;
    private ArrayList<Block> blocks = new ArrayList<>();
    
    // Eliminamos las variables primitivas locales 'vidas', 'puntaje' y 'nivel' 
    // porque ahora residen dentro de GestorJuego.getInstancia()

    @Override
    public void create () { 
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 480, camera);
        viewport.apply();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(3, 2);
        
        // Inicializamos los bloques usando el nivel del Singleton
        crearBloques(2 + GestorJuego.getInstancia().getNivel());
        
        shape = new ShapeRenderer();
        ball = new PingBall(Gdx.graphics.getWidth()/2-10, 41, 10, 5, 7, true);
        pad = new Paddle(Gdx.graphics.getWidth()/2-50, 40, 100, 10);
        
    }
    @Override
    public void resize(int width, int height) {
    	viewport.update(width, height, true);
    }
    @Override
    public void render () { 
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // --- NUEVO: CONTROL DE PAUSA CON LA TECLA 'P' ---
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            GestorJuego.getInstancia().alternarPausa();
        }
        
        camera.update();
        shape.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);
        
        // --- CONTROL DE ESTADO: EVALUACIÓN DE GAME OVER ---
        if (GestorJuego.getInstancia().getEstadoActual() == GestorJuego.Estado.GAME_OVER) {
            batch.begin();
            
            // 1. GAME OVER
            font.getData().setScale(4, 3); 
            font.draw(batch, "GAME OVER", 220 , 240 + 100); 
            
            // 2. PUNTAJE
            font.getData().setScale(2.5f, 2);
            font.draw(batch, "Puntaje Final: " + GestorJuego.getInstancia().getPuntaje(), 240 , 240 + 20);
            
            // 3. INSTRUCCIÓN DE VOLVER A JUGAR
            font.getData().setScale(1.8f, 1.5f);
            font.draw(batch, "Presiona ENTER para volver a jugar", 180 , 190);
            
            // 4. INSTRUCCIÓN DE HUD
            font.getData().setScale(3, 2);
            
            batch.end();
            
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                GestorJuego.getInstancia().resetearJuego();
                crearBloques(2 + GestorJuego.getInstancia().getNivel());
                ball = new PingBall(pad.getX() + pad.getWidth()/2 - 5, pad.getY() + pad.getHeight() + 11, 10, 5, 7, true);
            }
            return;
        }
        
        // --- ACTUALIZACIÓN DE LA PELOTA  ---
        if (GestorJuego.getInstancia().getEstadoActual() == GestorJuego.Estado.JUGANDO) {
            if (ball.estaQuieto()) {
                ball.setXY(pad.getX() + pad.getWidth()/2, pad.getY() + pad.getHeight() + 11);
                if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) ball.setEstaQuieto(false);
            } else {
                ball.update();
            }
            
            if (ball.getY() < 0) {
                GestorJuego.getInstancia().restarVida(); 
                ball = new PingBall(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11, 10, 5, 7, true);
            }
            
            if (blocks.size() == 0) {
                GestorJuego.getInstancia().avanzarNivel();
                crearBloques(2 + GestorJuego.getInstancia().getNivel());
                ball = new PingBall(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11, 10, 5, 7, true);
            }       
        }
        // --- DIBUJADO Y COLISIONES ---
        shape.begin(ShapeRenderer.ShapeType.Filled);
        pad.draw(shape);
        ball.draw(shape);
        
        // comprobar impacto con la paleta si no está pausado
        if (GestorJuego.getInstancia().getEstadoActual() == GestorJuego.Estado.JUGANDO) {
        	ball.resetColor();
            ball.checkCollision(pad);
        }
        ball.checkCollision(pad);
        for (int i = 0; i < blocks.size(); i++) {
            Block b = blocks.get(i);
            b.draw(shape); // Siempre dibujamos el bloque
            
            //comprobamos impactos y destruimos bloques si no está pausado
            if (GestorJuego.getInstancia().getEstadoActual() == GestorJuego.Estado.JUGANDO) {
            	ball.resetColor();
            	ball.checkCollision(b);
                
                if (b.isDestroyed()) {
                    blocks.remove(i);
                    GestorJuego.getInstancia().sumarPuntaje();
                    i--;
                }
            }
        }
        shape.end();

        // --- NUEVO: DIBUJAR LETRERO DE PAUSA SOBRE EL JUEGO ---
        if (GestorJuego.getInstancia().getEstadoActual() == GestorJuego.Estado.PAUSA) {
            batch.begin();
            font.getData().setScale(4, 3);
            font.draw(batch, "PAUSA", 310, 290);
            font.getData().setScale(3, 2);
            batch.end();
        }
     // --- FLUJO NORMAL DE JUEGO (SI ESTÁ JUGANDO) ---
        camera.update();
        shape.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);
     // Dibujamos el HUD usando coordenadas lógicas fijas del mundo (800x480)
        batch.begin();
        // Vidas arriba a la izquierda 
        font.draw(batch, "Vidas: " + GestorJuego.getInstancia().getVidas(), 10, 470);
        
        // Puntos arriba en el centro  
        font.draw(batch, "Puntos: " + GestorJuego.getInstancia().getPuntaje(), 320, 470);
        
        // Nivel arriba a la derecha  
        font.draw(batch, "Nivel: " + GestorJuego.getInstancia().getNivel(), 620, 470);
        batch.end();
        
        
        
        // Verificar caída de la pelota
        if (ball.getY() < 0) {
            GestorJuego.getInstancia().restarVida(); // Usamos el método del Singleton
            ball = new PingBall(pad.getX()+ (pad.getWidth()/2) -5, pad.getY()+pad.getHeight()+11, 10, 5, 7, true);
        }
        
        // Avanzar de nivel si se limpian los bloques
        if (blocks.size() == 0) {
            GestorJuego.getInstancia().avanzarNivel();
            crearBloques(2 + GestorJuego.getInstancia().getNivel());
            ball = new PingBall(pad.getX() + (pad.getWidth()/2) -5, pad.getY()+pad.getHeight()+11, 10, 5, 7, true);
        }       
        
        shape.begin(ShapeRenderer.ShapeType.Filled);
        pad.draw(shape);
        ball.draw(shape);
        ball.checkCollision(pad);
        
     // Dibujar bloques y procesar destrucción
        for (int i = 0; i < blocks.size(); i++) {
            Block b = blocks.get(i);
            b.draw(shape);
            ball.resetColor();
            ball.checkCollision(b);
            
            if (b.isDestroyed()) {
                blocks.remove(i);
                GestorJuego.getInstancia().sumarPuntaje(); // Sumamos puntos al Singleton
                i--;
            }
        }
        shape.end();
    }

    private void crearBloques(int filas) {
        blocks.clear();
        int anchoBloque = 70;
        int altoBloque = 25;
        int espacio = 5;
        
        for (int f = 0; f < filas; f++) {
            for (int col = 0; col < 10; col++) {
                // Volvemos a las coordenadas fijas
                int px = col * (anchoBloque + espacio) + 25;
                int py = 400 - (f * (altoBloque + espacio)); // 
                blocks.add(new Block(px, py, anchoBloque, altoBloque));
            }
        }
    }
}