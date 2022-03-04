package desktopApp.ui.pages;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;

import akdmEtkinlikEnvanter.entities.concretes.UniversiteEnstitu;
import desktopApp.ui.DesktopApplication;
import desktopApp.ui.controllers.UniversiteEnstituController;
import desktopApp.ui.controllers.UniversiteEnstituListController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class UniversiteEnstituPage {
	private ConfigurableApplicationContext applicationContext;
	private String templatePath = "/templates/fxml";
	private String cssPath = "/static/fxmlCss";
	
	public void show(UniversiteEnstitu universiteEnstitu) {
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(templatePath + "/UniversiteEnstitu.fxml"));
			
			applicationContext = DesktopApplication.applicationContext;
			loader.setControllerFactory(applicationContext::getBean);
			AnchorPane root = (AnchorPane)loader.load();
			UniversiteEnstituController universiteEnstituController = loader.getController();
			universiteEnstituController.setUniversiteEnstitu(universiteEnstitu);
			
			Scene scene = new Scene(root, Color.LIGHTBLUE);
//			Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
			Stage stage = new Stage();
			stage.setTitle("Üniversite/Enstitü");
			stage.setScene(scene);
			
			stage.show();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
