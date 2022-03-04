package desktopApp.ui.pages;

import java.io.IOException;

import akdmEtkinlikEnvanter.core.utilities.result.Result;
import desktopApp.ui.DesktopApplication;
import desktopApp.ui.controllers.MessageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MessagePage {
	
	private String templatePath = "/templates/fxml";
	private String cssPath = "/static/fxmlCss";
	MessageController messageController;
	
	public void show(Result result) {
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(templatePath + "/Message.fxml"));
			
			loader.setControllerFactory(DesktopApplication.applicationContext::getBean);
			AnchorPane root = (AnchorPane)loader.load();
			
			messageController = loader.getController();
			
			messageController.setResult(result);
			
			Scene scene = new Scene(root, Color.LIGHTBLUE);
//			Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
			Stage stage = new Stage();
			stage.setTitle("Mesaj");
			stage.setScene(scene);
			
			stage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void replace(Result result) {
		if(messageController != null) {
			messageController.setResult(result);
		}
		else {
			show(result);
			messageController.setResult(result);
		}
	}
	
}
