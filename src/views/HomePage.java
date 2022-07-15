package views;

import java.util.ArrayList;
import components.PunCard;
import database.Database;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import models.Pun;

public class HomePage extends Page{
	
	private ScrollPane scroll;
	private VBox container;

	public HomePage() {
		super();
	}

	@Override
	protected void initializeContent() {
		ArrayList<Pun> punList = loadPun();

		container = new VBox();
		container.setSpacing(40);
		container.setPadding(new Insets(25, 25, 100, 25));
		container.setAlignment(Pos.CENTER);
		
		ObservableList<Node> list = container.getChildren();
		for (Pun pun : punList) {
			PunCard card = new PunCard(pun);
			card.maxWidthProperty().bind(container.widthProperty().divide(3));
			
			list.add(card);
		}
		
		scroll = new ScrollPane();
		scroll.setBackground(null);
		scroll.setContent(container);
		scroll.translateYProperty().bind(navbar.heightProperty());

		container.prefWidthProperty().bind(scroll.widthProperty());
		
		this.root.getChildren().add(scroll);
	}

	private ArrayList<Pun> loadPun() {
		ArrayList<Pun> punList = Database.getInstance().getAllPun();
		
		return punList;
	}

}
