package listener;

import components.ChangePictureModal;
import helpers.Session;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class PictureButtonListener implements EventHandler<ActionEvent>{

	private ChangePictureModal modal;
	private Button btn;
	private int idx;
	
	public PictureButtonListener(ChangePictureModal modal, Button btn, int idx) {
		this.modal = modal;
		this.btn = btn;
		this.idx = idx;
	}

	public void handle(ActionEvent arg0) {
		modal.getSelected().setStyle("-fx-border-width: 0; -fx-cursor: hand");
		btn.setStyle("-fx-cursor: hand; -fx-border-width: 4; -fx-border-color: #fc8c04; -fx-border-radius: 16px; -fx-background-insets: 6px");
		modal.setSelected(btn);
		Session.getSession().getUser().setProfilePicture(idx);
	}

}
