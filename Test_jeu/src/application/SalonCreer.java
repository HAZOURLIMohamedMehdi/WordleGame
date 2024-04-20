package application;

import java.io.File;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SalonCreer extends GridPane{

    public static SalonCreer objetSalonCreer=null;
	private Button option;
	private Label []nomCode;
	private TextField nomSalon;
	private VBox head;
	
	public SalonCreer() {

		super();

		this.option = new Button();
		this.nomCode = new Label[2];
		this.nomSalon = new TextField();
		this.head = new VBox();

		option = new Button("Creer");
		option.textFillProperty().set(Color.GREEN);
		nomCode[0] = new Label("Nom de salon");
		nomCode[0].textFillProperty().set(Color.WHITE);
		createLabel();
		createTextField();
		nomCode[1] = new Label("Code A copy :ZOF255s");
		nomCode[1].textFillProperty().set(Color.WHITE);
		createLabelS();
		createButtonsOptions();
		setBackground(Background.fill(Color.BLACK));
		addRow(1, head);

	}
	void interactionRoom(Stage sc, Scene scene) {
		option.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg) {
				if (Room.objetRoom == null) {
					Room.objetRoom = new Room();
				}
				playAudio("sound/sound.wav");
				scene.setRoot(Room.objetRoom);
				sc.setScene(scene);
				responsivityRoom(sc,Room.objetRoom);
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
	void createButtonsOptions() {

		option.setMinHeight(60);
		option.setMinWidth(150);
		
		option.setStyle(
				"-fx-border-color:#949494;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;");
		option.setBackground(Background.fill(Color.TRANSPARENT));
		head.getChildren().add(option);
		head.setMargin(option, new Insets(10, 0, 0, 320));

	}


	void createTextField() {
		nomSalon.setMinHeight(40);
		nomSalon.setMinWidth(250);

		nomSalon.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (" ".equals(event.getCharacter())) {
                event.consume();  }
        });
		
		head.getChildren().add(nomSalon);
		head.setMargin(nomSalon, new Insets(10, 0, 0, 270));
	}

	public VBox getHead() {
		return head;
	}

	void createLabel() {
		nomCode[0].setMinHeight(80);
		nomCode[0].setMinWidth(200);
		nomCode[0].setBackground(Background.fill(Color.TRANSPARENT));

		this.nomCode[0].setStyle("-fx-font-size:25px;");
		this.nomCode[0].setAlignment(Pos.CENTER);
		head.getChildren().add(nomCode[0]);
		head.setMargin(nomCode[0], new Insets(230, 0, 0,250));
		//nomCode[0].setPadding(new Insets(0, 0, 0, 170));
	}
	
	void createLabelS() {
		nomCode[1].setMinHeight(80);
		nomCode[1].setMinWidth(200);
		nomCode[1].setBackground(Background.fill(Color.TRANSPARENT));

		this.nomCode[1].setStyle("-fx-font-size:25px;");
		this.nomCode[1].setAlignment(Pos.CENTER);
		head.getChildren().add(nomCode[1]);
		head.setMargin(nomCode[1], new Insets(0, 0, 0,250));
		//nomCode[0].setPadding(new Insets(0, 0, 0, 170));
	}

	public void responsivityRoom(Stage arg0, Room GP) {
		arg0.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg, Number arg1, Number arg2) {
				if (arg1.doubleValue() < arg2.doubleValue()) {
					GP.setMargin(GP.getBody(), new Insets(0, 0, 0, (arg2.doubleValue() - 750) / 2));
				}
				if (arg1.doubleValue() > arg2.doubleValue()) {
					GP.setMargin(GP.getBody(), new Insets(0, 0, 0,
							(arg2.doubleValue() - 750 - (arg1.doubleValue() - arg2.doubleValue())) / 2));
				}

			};
		});
	}
}
