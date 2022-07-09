package frontend;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class StatusPane extends BorderPane {

	private final Label statusLabel;

	private static final String BACKGROUND_COLOR = "-fx-background-color: #4EBCF8";
	private static final String FONT_SIZE = "-fx-font-size: 16";

	public StatusPane() {
		setStyle(BACKGROUND_COLOR);
		statusLabel = new Label("");
		statusLabel.setAlignment(Pos.CENTER);
		statusLabel.setStyle(FONT_SIZE);
		setCenter(statusLabel);
	}
	
	public void updateStatus(String text) {
		statusLabel.setText(text);
	}

}