package desktopApp.ui.pages;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;

import akdmEtkinlikEnvanter.entities.concretes.Kurs;
import desktopApp.ui.DesktopApplication;
import desktopApp.ui.controllers.KursListController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class KursListPage {
	private ConfigurableApplicationContext applicationContext;
	private String templatePath = "/templates/fxml";
	private String cssPath = "/static/fxmlCss";
	
	public void show(List<Kurs> kurslar) {
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(templatePath + "/KursList.fxml"));
			
			applicationContext = DesktopApplication.applicationContext;
			loader.setControllerFactory(applicationContext::getBean);
			AnchorPane root = (AnchorPane)loader.load();
			KursListController kursListController = loader.getController();
			kursListController.setKurslar(kurslar);
			
			Scene scene = new Scene(root, Color.LIGHTBLUE);
//			Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
			Stage stage = new Stage();
			stage.setTitle("Kurs Liste");
			stage.setScene(scene);
			
			stage.show();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
