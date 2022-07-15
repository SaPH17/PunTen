package views;

import components.tab.CompoundTab;
import components.tab.HomographicTab;
import components.tab.HomophonicTab;
import components.tab.RecursiveTab;
import components.tab.VisualTab;
import components.tab.WriteTab;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class WritePage extends Page{

	private VBox container, wrapper;
	private TabPane tabPane;
	
	@Override
	protected void initializeContent() {
		container = new VBox();
		container.setSpacing(40);
		container.setPadding(new Insets(25));
		container.setAlignment(Pos.TOP_CENTER);
		container.translateYProperty().bind(navbar.heightProperty());

		wrapper = new VBox();
		wrapper.maxWidthProperty().bind(container.widthProperty().divide(2.425));
		wrapper.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 12, 0, 0, 0);");	

		tabPane = new TabPane();
		this.getStylesheets().add(getClass().getResource("/components/tab/style/tab-pane.css").toExternalForm());
		tabPane.getStyleClass().add("tabPane");
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        tabPane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(8), Insets.EMPTY)));

        WriteTab homophonicTab = new HomophonicTab("Homophunic Pun");
        WriteTab visualTab = new VisualTab("Visual Pun");
        WriteTab homographicTab = new HomographicTab("Homographic Pun");
        WriteTab compoundTab = new CompoundTab("Compound Pun");
        WriteTab recursiveTab = new RecursiveTab("Recursive Pun");
        
        tabPane.setPrefHeight(homophonicTab.h + 55);
        tabPane.getTabs().addAll(homophonicTab, visualTab, homographicTab, compoundTab, recursiveTab);
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			public void changed(ObservableValue<? extends Tab> ov, Tab oldTab, Tab newTab) {
				WriteTab t = (WriteTab) newTab;
				tabPane.setPrefHeight(t.h + 55);
			}
		});
        
        wrapper.getChildren().add(tabPane);
        container.getChildren().add(wrapper);
		this.root.getChildren().add(container);
	}

}
