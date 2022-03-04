package desktopApp.ui.pages;

import java.io.IOException;

import org.springframework.context.ConfigurableApplicationContext;

import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.YuksekOgrenim;
import desktopApp.ui.DesktopApplication;
import desktopApp.ui.controllers.BirlikController;
import desktopApp.ui.controllers.YuksekOgrenimController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class YuksekOgrenimPage {
	private ConfigurableApplicationContext applicationContext;
	private String templatePath = "/templates/fxml";
	private String cssPath = "/static/fxmlCss";
	
	public void show(YuksekOgrenim yuksekOgrenim) {
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(templatePath + "/YuksekOgrenim.fxml"));
			
			applicationContext = DesktopApplication.applicationContext;
			loader.setControllerFactory(applicationContext::getBean);
			AnchorPane root = (AnchorPane)loader.load();
			YuksekOgrenimController yuksekOgrenimController = loader.getController();
			yuksekOgrenimController.setYuksekOgrenim(yuksekOgrenim);
			
			Scene scene = new Scene(root, Color.LIGHTBLUE);
//			Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
			Stage stage = new Stage();
//			stage.setTitle("Yüksek Öğrenim");
			stage.setTitle("Alan");
			stage.setScene(scene);
			
			stage.show();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
