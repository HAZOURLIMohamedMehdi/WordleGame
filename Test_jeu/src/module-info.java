module Test_jeu {
	requires javafx.controls;
	requires javafx.graphics;
	requires java.desktop;
	requires javafx.media;
	requires javafx.fxml;
	
	opens application to javafx.graphics, javafx.fxml;
}
