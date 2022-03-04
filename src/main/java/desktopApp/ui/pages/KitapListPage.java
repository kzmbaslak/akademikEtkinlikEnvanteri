package desktopApp.ui.pages;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;

import akdmEtkinlikEnvanter.entities.concretes.Belge;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Kitap;
import akdmEtkinlikEnvanter.entities.concretes.Yayin;
import desktopApp.ui.DesktopApplication;
import desktopApp.ui.controllers.BelgeListController;
import desktopApp.ui.controllers.BirlikController;
import desktopApp.ui.controllers.BirlikListController;
import desktopApp.ui.controllers.KitapListController;
import desktopApp.ui.controllers.YayinListController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class KitapListPage {
	private ConfigurableApplicationContext applicationContext;
	private String templatePath = "/templates/fxml";
	private String cssPath = "/static/fxmlCss";
	
	public void show(List<Kitap> kitaplar) {
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(templatePath + "/KitapList.fxml"));
			
			applicationContext = DesktopApplication.applicationContext;
			loader.setControllerFactory(applicationContext::getBean);
			AnchorPane root = (AnchorPane)loader.load();
			KitapListController kitapListController = loader.getController();
			kitapListController.setKitaplar(kitaplar);
			
			Scene scene = new Scene(root, Color.LIGHTBLUE);
//			Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
			Stage stage = new Stage();

			stage.setTitle("Kitap Liste");
			stage.setScene(scene);
			
			stage.show();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
