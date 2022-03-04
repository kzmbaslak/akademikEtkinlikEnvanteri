package desktopApp.ui.pages;

import java.io.IOException;

import org.springframework.context.ConfigurableApplicationContext;

import akdmEtkinlikEnvanter.entities.concretes.BelgeTuru;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import desktopApp.ui.DesktopApplication;
import desktopApp.ui.controllers.BelgeTuruController;
import desktopApp.ui.controllers.BirlikController;
import desktopApp.ui.controllers.PersonelController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BelgeTuruPage {
	private ConfigurableApplicationContext applicationContext;
	private String templatePath = "/templates/fxml";
	private String cssPath = "/static/fxmlCss";
	
	public void show(BelgeTuru belgeTuru) {
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(templatePath + "/BelgeTuru.fxml"));
			
			applicationContext = DesktopApplication.applicationContext;
			loader.setControllerFactory(applicationContext::getBean);
			AnchorPane root = (AnchorPane)loader.load();
			BelgeTuruController belgeTuruController = loader.getController();
			belgeTuruController.setBelgeTuru(belgeTuru);
			
			Scene scene = new Scene(root, Color.LIGHTBLUE);
//			Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
			Stage stage = new Stage();
			stage.setTitle("Belge Türü");
			stage.setScene(scene);
			
			stage.show();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
