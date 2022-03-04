package desktopApp.ui.pages;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;

import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.YayineviDergi;
import desktopApp.ui.DesktopApplication;
import desktopApp.ui.controllers.BirlikController;
import desktopApp.ui.controllers.BirlikListController;
import desktopApp.ui.controllers.YayineviDergiListController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class YayineviDergiListPage {
	private ConfigurableApplicationContext applicationContext;
	private String templatePath = "/templates/fxml";
	private String cssPath = "/static/fxmlCss";
	
	public void show(List<YayineviDergi> yayineviDergiler) {
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(templatePath + "/YayineviDergiList.fxml"));
			
			applicationContext = DesktopApplication.applicationContext;
			loader.setControllerFactory(applicationContext::getBean);
			AnchorPane root = (AnchorPane)loader.load();
			YayineviDergiListController yayineviDergiListController = loader.getController();
			yayineviDergiListController.setYayineviDergiler(yayineviDergiler);
			
			Scene scene = new Scene(root, Color.LIGHTBLUE);
//			Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
			Stage stage = new Stage();

			stage.setTitle("Yayinevi/Dergi Liste");
			stage.setScene(scene);
			
			stage.show();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
