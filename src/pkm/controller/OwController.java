package pkm.controller;

import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import pkm.Pkm;
import pkm.SpriteAnimation;

public class OwController {
	
	private static final int COLUMNS = 4;
    private static final int COUNT = 4;
    private static final int OFFSET_X = 13;
    private static final int OFFSET_Y = 5;
    private static final int WIDTH = 63;
    private static final int HEIGHT = 63;
    
    public static double posX = 0;
    public static double posY = 0;
    
    private int prob = 1;
    public static boolean battle;
    
    AnimationTimer timer;
    
    boolean isUpPressed, isDownPressed, isLeftPressed, isRightPressed;
    double rotation = 0.0;
	
	@FXML
    private AnchorPane root;

    @FXML
    private ImageView imgView;
    
    @FXML
    private ImageView sprite;
	
	@FXML
	public void initialize() {
		battle = false;
		spriteInit();
	}

	
    //----------------------------------------------------------------------
	
	/**
	 * 
	 */
	private void spriteInit() {
		System.out.println("posX : " + posX);
		System.out.println("posY : " + posY);
		sprite.setX(posX);
		sprite.setY(posY);
		sprite.viewportProperty().addListener(listener);
		sprite.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));
		final Animation animFront = new SpriteAnimation(
			sprite,
			Duration.millis(1000),
			COUNT, COLUMNS,
			OFFSET_X, OFFSET_Y,
			WIDTH, HEIGHT
		);
		animFront.setCycleCount(Animation.INDEFINITE);
		
		final Animation animBack = new SpriteAnimation(
				sprite,
				Duration.millis(1000),
				COUNT, COLUMNS,
				OFFSET_X, OFFSET_Y+(3*HEIGHT),
				WIDTH, HEIGHT
		);
		animBack.setCycleCount(Animation.INDEFINITE);
		
		final Animation animLeft = new SpriteAnimation(
				sprite,
				Duration.millis(1000),
				COUNT, COLUMNS,
				OFFSET_X, OFFSET_Y+(HEIGHT),
				WIDTH, HEIGHT
		);
		animLeft.setCycleCount(Animation.INDEFINITE);
		
		final Animation animRight = new SpriteAnimation(
				sprite,
				Duration.millis(1000),
				COUNT, COLUMNS,
				OFFSET_X, OFFSET_Y+(2*HEIGHT),
				WIDTH, HEIGHT
		);
		animRight.setCycleCount(Animation.INDEFINITE);
		
		sprite.setFocusTraversable(true);
		sprite.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch(event.getCode()) {
				case UP: if(!isLeftPressed && !isRightPressed) {isUpPressed = true;} break;
				case DOWN: if (!isLeftPressed && !isRightPressed) {isDownPressed = true;} break;
				case LEFT: if(!isUpPressed && !isDownPressed) {isLeftPressed = true;} break;
				case RIGHT: if(!isUpPressed && !isDownPressed) {isRightPressed = true;} break;
				default:
					break;
				}
			}
		});
		sprite.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				switch(event.getCode()) {
				case UP: isUpPressed = false; animBack.pause(); break;
				case DOWN: isDownPressed = false; animFront.pause(); break;
				case LEFT: isLeftPressed = false; animLeft.pause(); break;
				case RIGHT: isRightPressed = false; animRight.pause(); break;
				default:
					break;
				}
			}        
		});
		
		AnimationTimer timer = new AnimationTimer(){
            @Override
            public void handle(long now) {
                if(isUpPressed) {
                	animBack.play();
//                	System.out.println("Up : " + sprite.getY());
                	if(sprite.getY() >= 0) {
                		sprite.setY(sprite.getY() - 2.0 * (Math.cos(Math.toRadians(rotation))));
                		posY = sprite.getY();
                	}
                }
                if(isDownPressed) {
                	animFront.play();
//                	System.out.println("Down : " + sprite.getY()+(sprite.getImage().getHeight()-198));
                	if(sprite.getY()+(sprite.getImage().getHeight()-198) <= 410.0) {
                		sprite.setY(sprite.getY() + 2.0 * (Math.cos(Math.toRadians(rotation))));
                		posY = sprite.getY();
                	}
                }
                
            	if(isLeftPressed){
            		animLeft.play();
//            		System.out.println("Left : " + sprite.getX());
            		if(sprite.getX() >= 0) {            			
            			sprite.setX(sprite.getX() - 2.0 * (Math.cos(Math.toRadians(rotation))));
            			sprite.setY(sprite.getY() - 2.0 * (Math.sin(Math.toRadians(rotation))));
                		posX = sprite.getX();
            			posY = sprite.getY();
            		}
                }
                if(isRightPressed){
                	animRight.play();
//                	System.out.println("Right : " + sprite.getX() + (sprite.getImage().getWidth()-218));
                	if(sprite.getX()+(sprite.getImage().getWidth()-218) <= 606.0) {
                		sprite.setX(sprite.getX() + 2.0 * (Math.cos(Math.toRadians(rotation))));
                		sprite.setY(sprite.getY() + 2.0 * (Math.sin(Math.toRadians(rotation))));
                		posX = sprite.getX();
            			posY = sprite.getY();
                	}
                }  
            }

        };
        timer.start();
	}
	
	ChangeListener<Rectangle2D> listener =  new ChangeListener<Rectangle2D>() {
		@Override
		public void changed(ObservableValue<? extends Rectangle2D> observable, Rectangle2D oldValue,
				Rectangle2D newValue) {
			if(newValue != oldValue && !battle) {
				Random rand = new Random();
				if(rand.nextInt(prob) > 80) {
					sprite.viewportProperty().removeListener(listener);
					System.out.println("beforeBattle posX = " + sprite.getX());
					System.out.println("beforeBattle posY = " + sprite.getY());
					Pkm pkm = new Pkm();
					pkm.loadBattle();
				}
				if (prob < 101) {
					prob = prob + 10;
				}
			}
		}
	};
}
