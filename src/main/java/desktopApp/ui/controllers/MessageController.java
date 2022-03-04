package desktopApp.ui.controllers;

import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.core.utilities.result.Result;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import lombok.Data;

@Component
@Data
public class MessageController{

	@FXML
	private Text txtMessage;
	
	private Result result;
	
	private String message;
	
	public void setResult(Result result) {
		this.result = result;
		if(result != null) {
			displayMessage(result);
		}
	}
	
	private void displayMessage(Result result) {
		if(!result.isSuccess())
			txtMessage.setFill(Color.RED);
		else
			txtMessage.setFill(Color.DODGERBLUE);
		txtMessage.setText(result.getMessage());
		
	}
}
