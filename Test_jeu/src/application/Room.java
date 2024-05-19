package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Room extends GridPane {
	public static Room objetRoom = null;
	public static ArrayList<HBox> players = new ArrayList<HBox>();
	private HBox header;
	public static List<Bot> listBot=new ArrayList<Bot>();
	private static VBox body = new VBox();
	private HBox head;
	private Label[] labelsOfHead;
	private ScrollPane scrollPane;
	public static Thread threadVerifiyClients;
	public static ArrayList<String> clients;
	private Button startButton;
	private ChoiceBox itemBar;
	private TextField numberGames;
	private int t=0;
    public MainPageMultiplayer mainPageMultiplayer;
	public Room() {
		super();
		startButton();
		clientsThread();
		initialPlayers();
		initHead(70);
		addRow(0, head);
		scrollPane = new ScrollPane();
		scrollPane.setMaxHeight(400);
		scrollPane.setMaxWidth(570);
		scrollPane.setContent(body);
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(false);
		scrollPane.setStyle("-fx-background-color:BLACK;-fx-border:none;");
		setMargin(scrollPane, new Insets(100, 0, 0, 100));
		addRow(1, scrollPane);
		addRow(2, startButton);
		desactive();
		level();
	}

	public void startButton() {
		startButton = new Button("Start");
		startButton.textFillProperty().set(Color.WHITE);
		startButton.setBackground(Background.fill(Color.TRANSPARENT));
		startButton.setMinHeight(60);
		startButton.setMinWidth(150);
		startButton.setStyle(
				"-fx-border-color:#949494;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(255,255,255), 10, 0, 0, 0);");
		setMargin(startButton, new Insets(0, 0, 0, 300));
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				LoseMultiplayer  b= new LoseMultiplayer();
				mainPageMultiplayer=new MainPageMultiplayer(6, new Wordle(),b);
				b.ShowWord(mainPageMultiplayer,t);
				((Stage) startButton.getScene().getWindow()).setScene(MultiplayerConfig.startGame(mainPageMultiplayer, t, Integer.parseInt(numberGames.getText())));
				
				level();

			}
		});
	}

	public static void clientsThread() {
		threadVerifiyClients = new Thread(() -> {

			Platform.runLater(() -> {
				if (MultiplayerConfig.getPlayers().size() == 1) {
					addPlayer(MultiplayerConfig.getPlayers().get(MultiplayerConfig.getPlayers().size() - 1), true,
							true);
				} else {
					addPlayer(MultiplayerConfig.getPlayers().get(MultiplayerConfig.getPlayers().size() - 1), true,
							false);
				}
				for (int i = 0; i < players.size(); i++) {
					players.get(i).getChildren().get(1).setStyle(
							"-fx-font-size:20px;-fx-effect: dropshadow(gaussian, rgb(255,255,255), 5, 0, 0, 0);");
					((Label) players.get(i).getChildren().get(1)).textFillProperty().set(Color.WHITE);
					if (players.get(i).getChildren().size() == 3) {
						((Button) players.get(i).getChildren().get(2)).textFillProperty().set(Color.WHITE);
						players.get(i).getChildren().get(2).setStyle(
								"-fx-border-color:#949494;-fx-font-size:15px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(255,255,255), 4, 0, 0, 0);");
					}
					players.get(i).setStyle(
							"-fx-border-color:#949494;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(255,255,255), 4, 0, 0, 0);");
					if (i == 0) {
						players.get(i).setStyle(players.get(i).getStyle() + "-fx-border-style: dashed");
					} else {
						players.get(i).setStyle(players.get(i).getStyle() + "-fx-border-style: solid");

					}
				}
				for (int z = 2; z < players.size(); z++) {
					int a = z;
					System.out.println("position " + a);
					((Button) players.get(z).getChildren().get(2)).setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent arg0) {
							System.out.println("position " + a);
							Server.closePlayer(a);
							body.getChildren().remove(players.get(a));
							players.remove(a);
							
						}
					});

				}

			});

		});
		threadVerifiyClients.start();
	}

	public void initialPlayers() {
		players.add(new HBox());
		Circle circle = new Circle(40, 40, 38);
		Label userName = new Label();
		userName.setText("ADD BOT");

		players.get(players.size() - 1).getChildren().addAll(circle, userName);

		players.get(players.size() - 1).setPadding(new Insets(7, 0, 0, 220));
		players.get(players.size() - 1).setMargin(userName, new Insets(20, 0, 0, 0));
		players.get(players.size() - 1).setMinSize(550, 90);
		body.getChildren().add(players.get(players.size() - 1));
		body.setSpacing(20);
		players.get(players.size() - 1).setSpacing(20);
		players.get(players.size() - 1).setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				Platform.runLater(() -> {
					addPlayer("BOT" + (players.size() - 2), false, false);
					desactive();
					Easybot easy=new Easybot();
					listBot.add(easy);
				});

			}

		});
	}

	public void initHead(int height) {
		head = new HBox();
		head.setMinHeight(height);
		head.setPadding(new Insets(0, 0, 0, 30));
		initLabelsOfHead();
		itemBar = new ChoiceBox();
		itemBar.setStyle("-fx-min-width: 170; -fx-min-height: 40;");
		itemBar.getItems().add("Easy");
		itemBar.getItems().add("Medium");
		itemBar.getItems().add("Hard");

		numberGames = new TextField();
		numberGames.setMinHeight(35);
		numberGames.setMinWidth(40);
		head.getChildren().add(numberGames);
		head.setMargin(numberGames, new Insets(10, 0, 0, 10));

		head.getChildren().add(itemBar);
		head.setMargin(itemBar, new Insets(10, 0, 0, 10));

		head.getChildren().add(this.labelsOfHead[1]);
		head.setMargin(this.labelsOfHead[1], new Insets(10, 0, 0, 300));

		// head.setMargin(this.labelsOfHead[1], new Insets(10, 0, 0, 20));

	}

	public void level()

	{
		itemBar.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			String selectedText = (String) newValue;
			System.out.println("Texte sélectionné : " + selectedText);
			if (selectedText.equals("Easy")) {
				t = 5;
			} else if (selectedText.equals("Medium")) {
				t = 6;
			} else if (selectedText.equals("Hard")) {
				t = 7;
			}
		});
	}

	public void initLabelsOfHead() {

		labelsOfHead = new Label[2];

		labelsOfHead[0] = new Label();
		labelsOfHead[0].setMinHeight(45);
		labelsOfHead[0].setMinWidth(350);

		Image parameter = new Image("file:photo/parameter.png");
		ImageView parameterImageView = new ImageView(parameter);
		parameterImageView.setFitHeight(30);
		parameterImageView.setFitWidth(30);

		labelsOfHead[1] = new Label();
		labelsOfHead[1].setMinHeight(45);
		labelsOfHead[1].setMinWidth(45);

		labelsOfHead[1].setGraphic(parameterImageView);
		labelsOfHead[1].setAlignment(Pos.CENTER);
		labelsOfHead[1].setFocusTraversable(false);
		labelsOfHead[1].setBackground(Background.fill(Color.TRANSPARENT));
		for (int i = 1; i < 2; i++) {
			labelsOfHead[i].setFocusTraversable(false);
		}
	}

	public static void addPlayer(String name, boolean humain, boolean admin) {
		players.add(new HBox());
		Circle circle = new Circle(40, 40, 38);
		Label userName = new Label();
		userName.setText(name);
		if (!admin) {
			System.out.println("admin fffff");
			Button deleteOption = new Button("Delete");
			deleteOption.setBackground(Background.fill(Color.TRANSPARENT));
			deleteOption.setMinHeight(40);
			deleteOption.setMinWidth(110);
			deleteOption.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
			body.getChildren().remove(players.get(players.size()-1));
				if(!humain) {
					listBot.remove(0);
				}
				}
			});
			players.get(players.size() - 1).getChildren().addAll(circle, userName, deleteOption);
			players.get(players.size() - 1).setMargin(deleteOption, new Insets(17, 0, 0, 220));
		
		
		} else {
			players.get(players.size() - 1).getChildren().addAll(circle, userName);
		}
		players.get(players.size() - 1).setPadding(new Insets(7, 0, 0, 25));
		players.get(players.size() - 1).setMargin(userName, new Insets(20, 0, 0, 0));
		players.get(players.size() - 1).setMinSize(550, 90);
		body.getChildren().add(players.get(players.size() - 1));
		body.setSpacing(20);
		players.get(players.size() - 1).setSpacing(20);
	}

	public void active() {

	}

	public void desactive() {
		setBackground(Background.fill(Color.BLACK));
		for (int i = 0; i < players.size(); i++) {
			players.get(i).getChildren().get(1)
					.setStyle("-fx-font-size:20px;-fx-effect: dropshadow(gaussian, rgb(255,255,255), 5, 0, 0, 0);");
			((Label) players.get(i).getChildren().get(1)).textFillProperty().set(Color.WHITE);
			if (players.get(i).getChildren().size() == 3) {
				((Button) players.get(i).getChildren().get(2)).textFillProperty().set(Color.WHITE);
				players.get(i).getChildren().get(2).setStyle(
						"-fx-border-color:#949494;-fx-font-size:15px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(255,255,255), 4, 0, 0, 0);");
			}
			players.get(i).setStyle(
					"-fx-border-color:#949494;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(255,255,255), 4, 0, 0, 0);");
			if (i == 0) {
				players.get(i).setStyle(players.get(i).getStyle() + "-fx-border-style: dashed");
			} else {
				players.get(i).setStyle(players.get(i).getStyle() + "-fx-border-style: solid");

			}
		}

		this.labelsOfHead[1].setStyle(
				"-fx-border-color:#949494;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(148, 148, 148), 10, 0, 0, 0);");

	}

	public VBox getBody() {
		return body;
	}

}
