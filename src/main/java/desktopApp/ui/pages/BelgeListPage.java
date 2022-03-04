package desktopApp.ui.pages;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;

import akdmEtkinlikEnvanter.entities.concretes.Belge;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import desktopApp.ui.DesktopApplication;
import desktopApp.ui.controllers.BelgeListController;
import desktopApp.ui.controllers.BirlikController;
import desktopApp.ui.controllers.BirlikListController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BelgeListPage {
	private ConfigurableApplicationContext applicationContext;
	private String templatePath = "/templates/fxml";
	private String cssPath = "/static/fxmlCss";
	
	public void show(List<Belge> belgeler) {
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(templatePath + "/BelgeList.fxml"));
			
			applicationContext = DesktopApplication.applicationContext;
			loader.setControllerFactory(applicationContext::getBean);
			AnchorPane root = (AnchorPane)loader.load();
			BelgeListController belgeListController = loader.getController();
			belgeListController.setBelgeler(belgeler);
			
			Scene scene = new Scene(root, Color.LIGHTBLUE);
//			Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
			Stage stage = new Stage();

			stage.setTitle("Belge Liste");
			stage.setScene(scene);
			
			stage.show();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
