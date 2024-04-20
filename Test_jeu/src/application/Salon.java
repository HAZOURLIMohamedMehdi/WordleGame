package application;

import java.io.File;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Salon extends GridPane {
    public static Salon objetSalon=null;
	Button[] option;
	VBox body;

	public Salon() {
		super();
		this.option = new Button[2];
		this.body = new VBox();
		option[0] = new Button("Creer un salon");
		option[0].textFillProperty().set(Color.GREEN);
		option[1] = new Button("Rejoinde");
		option[1].textFillProperty().set(Color.ORANGE);
		createButtonsOptions();

		setBackground(Background.fill(Color.BLACK));
		addRow(0, body);
	}
	
	void createButtonsOptions() {

		int var = 0;
		for (int i = 0; i < 2; i++) {
			option[i].setId("button" + i);
			option[i].setStyle(
					"-fx-border-color:#949494;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;");
			option[i].setBackground(Background.fill(Color.TRANSPARENT));
			option[i].setMinHeight(60);
			option[i].setMinWidth(230);
			body.getChildren().add(option[i]);
			if (var == 0) {
				body.setMargin(option[var], new Insets(270, 0, 0, 270));
			} else {
				body.setMargin(option[var], new Insets(40, 0, 0, 270));
			}
			var++;
		}
	}
	
	public VBox getBody() {
		return body;
	}
	
	void interactionPageSalonCreer(Stage sc, Scene scene) {
		option[0].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg) {
				if (SalonCreer.objetSalonCreer == null) {
					SalonCreer.objetSalonCreer = new SalonCreer();
				}
				playAudio("sound/sound.wav");
				scene.setRoot(SalonCreer.objetSalonCreer);
				sc.setScene(scene);
				responsivitySalonCreer(sc, SalonCreer.objetSalonCreer);
			}

		});

	}
	
	void interactionRejoindreSalon(Stage sc, Scene scene) {
		option[1].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg) {
				if (RejoindreSalon.objetRejoindreSalon == null) {
					RejoindreSalon.objetRejoindreSalon = new RejoindreSalon();
				}
				playAudio("sound/sound.wav");
				scene.setRoot(RejoindreSalon.objetRejoindreSalon);
				sc.setScene(scene);
				responsivityRejoindreSalon(sc, RejoindreSalon.objetRejoindreSalon);
			}

		});

	}
	
	public void playAudio(final String filePath) {
		File file = new File(filePath);
		Media media = new Media(file.toURI().toString());
		MediaPlayer mediaplayer = new MediaPlayer(media);
		Task task = new Task<Void>() {
			@Override
			public Void call() {
				mediaplayer.play();
				return null;
			}
		};
		new Thread(task).start();
	}
	
	public void responsivitySalonCreer(Stage arg0, SalonCreer GP) {
		arg0.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg, Number arg1, Number arg2) {
				if (arg1.doubleValue() < arg2.doubleValue()) {
					GP.setMargin(GP.getHead(), new Insets(0, 0, 0, (arg2.doubleValue() - 750) / 2));
				}
				if (arg1.doubleValue() > arg2.doubleValue()) {
					GP.setMargin(GP.getHead(), new Insets(0, 0, 0,
							(arg2.doubleValue() - 750 - (arg1.doubleValue() - arg2.doubleValue())) / 2));
				}

			};
		});
	}
	
	public void responsivityRejoindreSalon(Stage arg0, RejoindreSalon GP) {
		arg0.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg, Number arg1, Number arg2) {
				if (arg1.doubleValue() < arg2.doubleValue()) {
					GP.setMargin(GP.getHead(), new Insets(0, 0, 0, (arg2.doubleValue() - 750) / 2));
				}
				if (arg1.doubleValue() > arg2.doubleValue()) {
					GP.setMargin(GP.getHead(), new Insets(0, 0, 0,
							(arg2.doubleValue() - 750 - (arg1.doubleValue() - arg2.doubleValue())) / 2));
				}

			};
		});
	}
}
