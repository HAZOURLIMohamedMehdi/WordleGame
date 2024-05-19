package application;

import java.io.File;
import java.util.ArrayList;

import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

public class WinMultiPlayer extends GridPane{
	
	private Button[]option;
	 private ArrayList<Label>nomPrenom=new ArrayList<Label>();
	 private Label[] score;
	 private HBox[] apropos;
	 private VBox head;	 
	 private ScrollPane scrollPane;
	 
	 public WinMultiPlayer()
	 {
		 super();
			this.option =new Button[2];
			this.score = new Label[4];
			this.apropos = new HBox[5];
			this.head = new VBox();
			option[0] = new Button("Back");
			option[1] = new Button("statistique");

			nomPrenom.add(new Label("Hazourli Mehdi"));
			nomPrenom.add(new Label("Bourouina Akram"));
			nomPrenom.add(new Label("Mejai Wajdi"));
			nomPrenom.add(new Label("Geyer Rayane"));
			
			score[0] = new Label("score 2/0");
			score[1] = new Label("score 1/0");
			score[2] = new Label("score 0/0");
			score[3] = new Label("score 0/0");
			
			createLabel();
			
			scrollPane = new ScrollPane();
			scrollPane.setMaxHeight(400);
			scrollPane.setMaxWidth(570);
			scrollPane.setContent(head);
			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(false);
			scrollPane.setStyle("-fx-background-color:BLACK;-fx-border:none;");
			setMargin(scrollPane, new Insets(100, 0, 0, 150));
			createButtonsOptions();
			addRow(1, scrollPane);
			addRow(2, apropos[4]);
			desactive();
	 }	
	 
	 void createButtonsOptions() {
			
		    apropos[4] = new HBox();
			option[0].setBackground(Background.fill(Color.TRANSPARENT));
			option[0].setMinHeight(60);
			option[0].setMinWidth(150);
			apropos[4].getChildren().add(option[0]);
			apropos[4].setMargin(option[0], new Insets(70, 0, 0, 150));
			
			option[1].setBackground(Background.fill(Color.TRANSPARENT));
			option[1].setMinHeight(60);
			option[1].setMinWidth(150);
			apropos[4].getChildren().add(option[1]);
			apropos[4].setMargin(option[1], new Insets(70, 0, 0, 170));
					
		} 
	 
		void createLabel() {
			for(int i=0;i<4;i++) {
				apropos[i] = new HBox();
				nomPrenom.get(i).setMinHeight(40);
				nomPrenom.get(i).setMinWidth(200);
				nomPrenom.get(i).setAlignment(Pos.CENTER);
				nomPrenom.get(i).setBackground(Background.fill(Color.GREEN));
				score[i].setMinHeight(40);
				score[i].setMinWidth(200);
				this.score[i].setAlignment(Pos.CENTER);

			}
			apropos[0].getChildren().addAll(nomPrenom.get(0),score[0]);
			apropos[0].setSpacing(70);
			apropos[0].setPadding(new Insets(40, 0, 0,10));
			
			for(int i=1;i<4;i++) {
				apropos[i].getChildren().addAll(nomPrenom.get(i),score[i]);
				apropos[i].setSpacing(70);
				apropos[i].setPadding(new Insets(40, 0, 0,10));	
			}
			head.getChildren().addAll(apropos[0],apropos[1],apropos[2],apropos[3]);
		}
	 
		public VBox getHead() {
			return 	head;
		}
		
		public void playAudio(final String filePath) {
			File file=new File(filePath);
			   Media media=new Media(file.toURI().toString());
			   MediaPlayer mediaplayer=new MediaPlayer(media);
			Task task = new Task<Void>() {
				    @Override public Void call() {
				    	mediaplayer.play();
				    	return null;
				    }
				};
				new Thread(task).start();
		}
		
		public void desactive() {
			setBackground(Background.fill(Color.BLACK));
			option[0].textFillProperty().set(Color.WHITE);
			option[0].setStyle("-fx-border-color:#949494;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(255,255,255), 10, 0, 0, 0);");
			option[1].textFillProperty().set(Color.WHITE);
			option[1].setStyle("-fx-border-color:#949494;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(255,255,255), 10, 0, 0, 0);");
			for(int i=0;i<4;i++) {
				nomPrenom.get(i).textFillProperty().set(Color.WHITE);
				nomPrenom.get(i).setStyle("-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:18px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(255,255,255	), 10, 0, 0, 0);");
				
				this.score[i].textFillProperty().set(Color.WHITE);
				this.score[i].setStyle("-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:18px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(255,255,255	), 10, 0, 0, 0);");

			}
		}
		public void active() {
			setBackground(Background.fill(Color.WHITE));
			option[0].setStyle("-fx-border-color:BLACK;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(0,0,0), 10, 0, 0, 0);");
			option[0].textFillProperty().set(Color.BLACK);
			option[1].setStyle("-fx-border-color:BLACK;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(0,0,0), 10, 0, 0, 0);");
			option[1].textFillProperty().set(Color.BLACK);
			for(int i=0;i<4;i++) {
				nomPrenom.get(i).textFillProperty().set(Color.BLACK);
				nomPrenom.get(i).setStyle("-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:18px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(0,0,0), 10, 0, 0, 0);");
				
				this.score[i].textFillProperty().set(Color.BLACK);
				this.score[i].setStyle("-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:18px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(0,0,0), 10, 0, 0, 0);");

			}
		}
	 
}
