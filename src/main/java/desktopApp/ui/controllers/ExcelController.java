package desktopApp.ui.controllers;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessResult;
import desktopApp.ui.pages.MessagePage;
import desktopApp.utilities.excel.ExcelHandler;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

@Component
public class ExcelController {
	
	@Autowired
	private ExcelHandler excelHandler;
	
	public void importData(ActionEvent e) { 
		
		try {
			String path = getChooseFilePath();
			
			excelHandler.pullValueOfExcelToDatabase(path);
			if(path.equals(""))
				new MessagePage().show(new ErrorResult("Veriler Aktarılamadı."));
			else
				new MessagePage().show(new SuccessResult("Veriler İçe Aktarıldı."));
		}
		catch(Exception ex) {
			new MessagePage().show(new ErrorResult("Veriler Aktarılamadı."));
		}
		
		
	}
	
	public void exportData(ActionEvent e) {
		System.out.println("Exprot data");
	}
	
	private String getChooseFilePath() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("Excel File", "*.xlsx"));
		File file = fc.showOpenDialog(null);
		
		if(file != null)
			return file.getAbsolutePath();
		return "";
	}
}
