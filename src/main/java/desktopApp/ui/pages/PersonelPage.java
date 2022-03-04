package desktopApp.ui.pages;

import java.io.IOException;

import org.springframework.context.ConfigurableApplicationContext;

import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import desktopApp.ui.DesktopApplication;
import desktopApp.ui.controllers.PersonelController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PersonelPage {
	
	private ConfigurableApplicationContext applicationContext;
	private String templatePath = "/templates/fxml";
	private String cssPath = "/static/fxmlCss";
	
	public void show(Personel personel) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(templatePath + "/Personel.fxml"));
			
			applicationContext = DesktopApplication.applicationContext;
			loader.setControllerFactory(applicationContext::getBean);
			AnchorPane root = (AnchorPane)loader.load();
			PersonelController personelController = loader.getController();
			personelController.setPersonel(personel);
			
			Scene scene = new Scene(root, Color.LIGHTBLUE);
//			scene.getStylesheets().add(getClass().getResource(cssPath + "/greenTheme.css").toString());
//			Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
			Stage stage = new Stage();
			stage.setTitle("Personel");
			stage.setScene(scene);
			
			stage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
