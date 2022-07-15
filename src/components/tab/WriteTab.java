package components.tab;

import builder.ModalBuilder;
import helpers.LayoutHelper;
import helpers.StageHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import values.Constant;
import views.HomePage;

public abstract class WriteTab extends Tab{
	
	protected VBox container;
	protected Font font = Font.font("Verdana", FontWeight.NORMAL, 14);
	protected Button addBtn;
	protected TextField titleTxt;
	public double h;

	public WriteTab(String s) {
		createBasicLayout();
		createUniqueLayout();
		addBtn.toFront();
		
		setText(s);
		setContent(container);
	}
	
	protected abstract void createUniqueLayout();
	protected abstract void createPun();
	
	private void createBasicLayout() {
		container = new VBox();
		container.setSpacing(25);
		container.setPadding(new Insets(25));
		LayoutHelper.configurePaneBackground(container, Color.WHITE, new CornerRadii(8), Insets.EMPTY);

		titleTxt = new TextField();
		titleTxt.setPrefWidth(250);
		titleTxt.setPrefHeight(40);
		titleTxt.setFont(font);
		titleTxt.setPromptText("Title");
		titleTxt.setBorder(new Border(new BorderStroke(Constant.ORANGE_PRIMARY, BorderStrokeStyle.SOLID, new CornerRadii(3), BorderWidths.DEFAULT)));
		
		addBtn = new Button("Submit");
		addBtn.setStyle("-fx-background-color: #ff7750; -fx-text-fill: #fff; -fx-cursor: hand");
		addBtn.prefWidthProperty().bind(container.widthProperty());
		addBtn.setPrefHeight(35);
		addBtn.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
		
		container.getChildren().add(titleTxt);
		container.getChildren().add(addBtn);
		
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				if(titleTxt.getText().isBlank()) {
					ModalBuilder.createRegisterFailedModal("Title must be filled!").showModal();
					return;
				}
				
				createPun();
			}
		});
	}

	public TextField getTitleTxt() {
		return titleTxt;
	}

	public void setTitleTxt(TextField titleTxt) {
		this.titleTxt = titleTxt;
	}

	public VBox getContainer() {
		return container;
	}

	public void setContainer(VBox container) {
		this.container = container;
	}

	public Button getAddBtn() {
		return addBtn;
	}

	public void setAddBtn(Button addBtn) {
		this.addBtn = addBtn;
	}
	
	

}
