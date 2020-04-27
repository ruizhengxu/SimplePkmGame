package pkm.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import pkm.Attaque;
import pkm.AttaqueBallOmbre;
import pkm.AttaqueCoupDeCorne;
import pkm.AttaqueDirectToxik;
import pkm.AttaqueMorsure;
import pkm.AttaqueSeisme;
import pkm.Pkm;
import pkm.Pokemon;
import pkm.Rect;

public class PkmController {
	
	/*
	 * FXML declarations
	 */
	@FXML
    private VBox root;

    @FXML
    private GridPane gridPaneCombat;

    @FXML
    private ImageView imgViewPkmSvg;

    @FXML
    private Label lblNomPkmSvg;

    @FXML
    private Label lblNivPkmSvg;

    @FXML
    private Label lblHPPkmSvg;

    @FXML
    private Label lblHPResPkmSvg;

    @FXML
    private Rectangle hpBarFixed;

    @FXML
    private Rectangle hpBarPkmSvg;
    
    private Rect hpBarPkmSvgHidden;

    @FXML
    private ImageView imgViewPkmPlayer;

    @FXML
    private Label lblNomPkmPlayer;

    @FXML
    private Rectangle hpBarFixed1;

    @FXML
    private Rectangle hpBarPkmPlayer;
    
    private Rect hpBarPkmPlayerHidden;

    @FXML
    private Label lblNivPkmPlayer;

    @FXML
    private Label lblHPPkmPlayer;

    @FXML
    private Label lblHPResPkmPlayer;

    @FXML
    private HBox hbox;

    @FXML
    private TextArea ta;

    @FXML
    private GridPane gridPaneChoix;

    @FXML
    private Button btnItem;

    @FXML
    private Button btnPkm;

    @FXML
    private Button btnAttaque;

    @FXML
    private Button btnFuite;
    
    /*
     * Declarations
     */
    
	private Pokemon pkmSvg = new Pokemon(94, "Ectolasma", "Spectre", 36, 100, 55, 59, 109, 70, 104, new Attaque[]{new AttaqueBallOmbre()});
	private static Pokemon pkmPlayer = new Pokemon(34, "Nidoking" , "Sol", 36, 115, 97, 71, 69, 70, 77, new Attaque[] {new AttaqueSeisme(), new AttaqueDirectToxik(), new AttaqueCoupDeCorne(), new AttaqueMorsure()});
	
	private Map<String, Attaque> mapAttaques = new HashMap<>();
	
	Text text = new Text("Coup critique");
	
	private double percentage;
		
	private Timeline timeline;
	
	OwController ow = new OwController();
	
    @FXML
    public void initialize() {
    	this.hpBarPkmPlayer.setWidth((double)pkmPlayer.getHPRestant()/(double)pkmPlayer.getHP()*hpBarFixed.getWidth());
    	this.mapAttaquesInit();
    	this.labelInit();
    	hpBarPkmPlayerHidden = new Rect(pkmPlayer.getHPRestant(), 0);
    	hpBarPkmSvgHidden = new Rect(pkmSvg.getHP(), 0);
    	ta.setText("Que doit faire " + pkmPlayer.getNom() + " ?");
    	ta.setFont(Font.font("Futura", 15));
    }
    
    @FXML
    void changePkm(ActionEvent event) {
    	ta.setText("Ne peut pas changer de Pokémon !");
    }
    
    @FXML
    void choisirAttaques(ActionEvent event) {
    	ta.setText("Que doit faire " + pkmPlayer.getNom() + " ?");
    	btnAttaque.setText(pkmPlayer.getAttaques().get(0).getNom());
    	btnAttaque.setOnAction(new EventHandlerAttaque());
    	btnPkm.setText(pkmPlayer.getAttaques().get(1).getNom());
    	btnPkm.setOnAction(new EventHandlerAttaque());
    	btnItem.setText(pkmPlayer.getAttaques().get(2).getNom());
    	btnItem.setOnAction(new EventHandlerAttaque());
    	btnFuite.setText(pkmPlayer.getAttaques().get(3).getNom());
    	btnFuite.setOnAction(new EventHandlerAttaque());
    	System.out.println("choisirAttaques posX = " + OwController.posX);
		System.out.println("choisirAttaques posY = " + OwController.posY);
    }
    
    @FXML
    void fuite(ActionEvent event) {
    	Random rand = new Random();
    	if(rand.nextInt(101) > 60) {
    		Platform.exit();
    	}
    	else {
    		ta.setText("Fuite Impossible !!");
    	}
    }
    
    @FXML
    void utiliseItem(ActionEvent event) {
    	ta.setText("Ne peut pas utiliser d'items !");
    }
    
    //----------------------------------------------------------------------
    
    public void mapAttaquesInit() {
    	this.mapAttaques.put("Séisme", new AttaqueSeisme());
    	this.mapAttaques.put("Morsure", new AttaqueMorsure());
    	this.mapAttaques.put("Balle Ombre", new AttaqueBallOmbre());
    	this.mapAttaques.put("Coup De Corne", new AttaqueCoupDeCorne());
    	this.mapAttaques.put("Direct Toxik", new AttaqueDirectToxik());
    }
    
    public class EventHandlerAttaque implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
	    	System.out.println("EventHandlerAttaque posX = " + OwController.posX);
			System.out.println("EventHandlerAttaque posY = " + OwController.posY);
			Button btn = (Button) event.getSource();
			if(!pkmPlayer.isKO()) {
				joueurAttaque(btn.getText());
			}
			
			if(!pkmSvg.isKO()) {
				Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1.5), e -> ennemiAttaque()));
				Platform.runLater(timeLine::play);
			}
		}
    }
    
    private void joueurAttaque(String nomAttaque) {
    	System.out.println("joueurAttaque posX = " + OwController.posX);
		System.out.println("joueurAttaque posY = " + OwController.posY);
    	pkmPlayer.utiliserAttaque(pkmPlayer.getIndexAttaque(nomAttaque), pkmSvg, ta);
    	
    	percentage = (double)pkmSvg.getHPRestant()/(double)pkmSvg.getHP();
    	lblHPResPkmSvg.textProperty().bind(Bindings.concat(this.hpBarPkmSvgHidden.widthProperty()));
    	
    	KeyValue kvPkmSvgBar = new KeyValue(hpBarPkmSvg.widthProperty(), percentage*hpBarFixed.getWidth());
    	KeyFrame kfPkmSvgBar = new KeyFrame(Duration.seconds(1.5), kvPkmSvgBar);
    	
    	KeyValue kvPkmSvgLbl = new KeyValue(hpBarPkmSvgHidden.widthProperty(), pkmSvg.getHPRestant());
    	KeyFrame kfPkmSvgLbl = new KeyFrame(Duration.seconds(1.5), kvPkmSvgLbl);
    	
    	timeline = new Timeline(kfPkmSvgBar, kfPkmSvgLbl);
    	timeline.play();
    	
		setBtnDisable();
		if(pkmSvg.isKO()) {
			Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(2.3), e -> pkmKo(pkmSvg)));
			Platform.runLater(timeLine::play);
		} else {
			Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(3), e -> setBtnAble()));
			timeLine.play();
		}
    }
    
    private void ennemiAttaque() {
    	setBtnDisable();
    	Random rand = new Random();
    	int indexAttaque = rand.nextInt(pkmSvg.getAttaques().size());
    	pkmSvg.utiliserAttaque(indexAttaque, pkmPlayer, ta);
    	
    	percentage = (double)pkmPlayer.getHPRestant()/(double)pkmPlayer.getHP();
    	lblHPResPkmPlayer.textProperty().bind(Bindings.concat(this.hpBarPkmPlayerHidden.widthProperty()));
    	
    	KeyValue kvPkmPlayerBar = new KeyValue(hpBarPkmPlayer.widthProperty(), percentage*hpBarFixed.getWidth());
    	KeyFrame kfPkmPlayerBar = new KeyFrame(Duration.seconds(1.5), kvPkmPlayerBar);
    	
    	KeyValue kvPkmPlayerLbl = new KeyValue(hpBarPkmPlayerHidden.widthProperty(), pkmPlayer.getHPRestant());
    	KeyFrame kfPkmPlayerLbl = new KeyFrame(Duration.seconds(1.5), kvPkmPlayerLbl);
    	
    	timeline = new Timeline(kfPkmPlayerBar, kfPkmPlayerLbl);
    	timeline.play();
    	
    	if(pkmPlayer.isKO()) {
    		Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(2.3), e -> pkmKo(pkmPlayer)));
			Platform.runLater(timeLine::play);
    	} else {
    		btnAttaque.setText("Attaque");
    		btnAttaque.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					ta.setText("Que doit faire " + pkmPlayer.getNom() + " ?");
			    	btnAttaque.setText(pkmPlayer.getAttaques().get(0).getNom());
			    	btnAttaque.setOnAction(new EventHandlerAttaque());
			    	btnPkm.setText(pkmPlayer.getAttaques().get(1).getNom());
			    	btnPkm.setOnAction(new EventHandlerAttaque());
			    	btnItem.setText(pkmPlayer.getAttaques().get(2).getNom());
			    	btnItem.setOnAction(new EventHandlerAttaque());
			    	btnFuite.setText(pkmPlayer.getAttaques().get(3).getNom());
			    	btnFuite.setOnAction(new EventHandlerAttaque());	
				}
			});
    		btnFuite.setText("Fuite");
    		btnFuite.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Random rand = new Random();
			    	if(rand.nextInt(101) > 60) {
			    		Platform.exit();
			    	}
			    	else {
			    		ta.setText("Fuite Impossible !!");
			    	}
				}
			});
    		btnItem.setText("Items");
    		btnItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					ta.setText("Ne peut pas utiliser d'items !");
				}
			});
    		btnPkm.setText("Pokémon");
    		btnPkm.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					ta.setText("Ne peut pas changer de Pokémon !");
				}
			});
    	}
    }
 
    private void pkmKo(Pokemon pkm) {
    	if (pkm.equals(pkmPlayer)) {
    		ta.appendText("\n" + pkmPlayer.getNom() + " est K.O\nVous avez perdu");
    		imgViewPkmPlayer.setVisible(false);
    		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
    			Pkm p = new Pkm();
				p.loadOW();
			}));
	    	Platform.runLater(timeline::play);
    	} else {
    		ta.appendText("\n" + pkmSvg.getNom() + " est K.O\nVous remportez le combat !");
			imgViewPkmSvg.setVisible(false);
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
				Pkm p = new Pkm();
				p.loadOW();
			}));
	    	Platform.runLater(timeline::play);
    	}
	}
    
    //------------------------------------------------------------------------

	private void labelInit() {
    	this.lblNomPkmSvg.setText(pkmSvg.getNom());
    	this.lblNivPkmSvg.setText(String.valueOf(pkmSvg.getNiveau()));
    	this.lblHPPkmSvg.setText(String.valueOf(pkmSvg.getHP()));
    	this.lblHPResPkmSvg.setText(String.valueOf(pkmSvg.getHPRestant()));
    	this.lblNomPkmPlayer.setText(pkmPlayer.getNom());
    	this.lblNivPkmPlayer.setText(String.valueOf(pkmPlayer.getNiveau()));
    	this.lblHPPkmPlayer.setText(String.valueOf(pkmPlayer.getHP()));
    	this.lblHPResPkmPlayer.setText(String.valueOf(pkmPlayer.getHPRestant()));
    }
  
	private void setBtnDisable() {
		this.btnAttaque.setDisable(true);
		this.btnPkm.setDisable(true);
		this.btnItem.setDisable(true);
		this.btnFuite.setDisable(true);
	}
	
	private void setBtnAble() {
		this.btnAttaque.setDisable(false);
		this.btnPkm.setDisable(false);
		this.btnItem.setDisable(false);
		this.btnFuite.setDisable(false);
	}
}
