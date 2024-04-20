package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class RejoindreSalon extends GridPane {
	public static RejoindreSalon objetRejoindreSalon=null;
	private Button option;
	private Label []nomCode;
	private TextField []nameAndCode;
	private VBox head;
	
	public RejoindreSalon() {

		super();

		this.option = new Button();
		this.nomCode = new Label[2];
		this.nameAndCode = new TextField [2];
		this.head = new VBox();

		 for (int i = 0; i < nameAndCode.length; i++) {
		        nameAndCode[i] = new TextField();
		    }
		 
		option = new Button("Join");
		option.textFillProperty().set(Color.GREEN);
		nomCode[0] = new Label("Player's Name");
		nomCode[0].textFillProperty().set(Color.WHITE);
		createLabel();
		createTextField();
		nomCode[1] = new Label("Code");
		nomCode[1].textFillProperty().set(Color.WHITE);
		createLabelS();
		createTextFields();
		createButtonsOptions();
		setBackground(Background.fill(Color.BLACK));
		addRow(1, head);
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
	public VBox getHead() {
		return head;
	}
	
	void createLabelS() {
		nomCode[1].setMinHeight(80);
		nomCode[1].setMinWidth(200);
		nomCode[1].setBackground(Background.fill(Color.TRANSPARENT));

		this.nomCode[1].setStyle("-fx-font-size:25px;");
		this.nomCode[1].setAlignment(Pos.CENTER);
		head.getChildren().add(nomCode[1]);
		head.setMargin(nomCode[1], new Insets(0, 0, 0,200));
		//nomCode[0].setPadding(new Insets(0, 0, 0, 170));
	}
	
	void createTextField() {
		nameAndCode[0].setMinHeight(40);
		nameAndCode[0].setMinWidth(250);

		nameAndCode[0].addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (" ".equals(event.getCharacter())) {
                event.consume();  }
        });
		
		head.getChildren().add(nameAndCode[0]);
		head.setMargin(nameAndCode[0], new Insets(10, 0, 0, 270));
	}
	
	void createTextFields() {
		nameAndCode[1].setMinHeight(40);
		nameAndCode[1].setMinWidth(250);

		nameAndCode[1].addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (" ".equals(event.getCharacter())) {
                event.consume();  }
        });
		
		head.getChildren().add(nameAndCode[1]);
		head.setMargin(nameAndCode[1], new Insets(0, 0, 0, 270));
	}
	
	void createButtonsOptions() {

		option.setMinHeight(60);
		option.setMinWidth(150);
		
		option.setStyle(
				"-fx-border-color:#949494;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;");
		option.setBackground(Background.fill(Color.TRANSPARENT));
		head.getChildren().add(option);
		head.setMargin(option, new Insets(70, 0, 0, 320));

	}

}
