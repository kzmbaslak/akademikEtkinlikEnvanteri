package desktopApp.ui;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.util.SuspendResumeLock;

import akdmEtkinlikEnvanter.AkdmEtkinlikEnvanterApplication;
import akdmEtkinlikEnvanter.business.abstracts.BirlikService;
import akdmEtkinlikEnvanter.business.abstracts.KitapService;
import akdmEtkinlikEnvanter.business.concretes.KitapManager;
import akdmEtkinlikEnvanter.dataAccess.abstracts.BelgeDao;
import akdmEtkinlikEnvanter.entities.concretes.Belge;
import akdmEtkinlikEnvanter.entities.concretes.Birlik;
import akdmEtkinlikEnvanter.entities.concretes.Kitap;
import akdmEtkinlikEnvanter.entities.concretes.Personel;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Data;

@Component
@Data
public class DesktopApplication extends Application{
	public static HostServices hostServices; 
	private static String console = "\n";//Consol'a yazılacak tüm bilgiler buraya yazılır.
	public static ConfigurableApplicationContext applicationContext;
	private String templatePath = "/templates/fxml";
	private String cssPath = "/static/fxmlCss";
	
	
	
	@Override
	public void init() {
		
		hostServices = getHostServices();
		applicationContext = new SpringApplicationBuilder(AkdmEtkinlikEnvanterApplication.class)
				.run(getParameters().getRaw().toArray(new String[0]));
		
	}
	
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(templatePath + "/DesktopApplication.fxml"));
		loader.setControllerFactory(applicationContext::getBean);
		AnchorPane root = (AnchorPane)loader.load();
		Scene scene = new Scene(root, Color.LIGHTBLUE);
//		scene.getStylesheets().add(getClass().getResource(cssPath + "/wind7.css").toString());
//		scene.getStylesheets().add(getClass().getResource(cssPath + "/greenTheme.css").toString());
//		scene.getStylesheets().add(getClass().getResource(cssPath + "/bootstrap3.css").toString());
//		scene.getStylesheets().add(getClass().getResource(cssPath + "/ligthTheme.css").toString());
//		scene.getStylesheets().add(getClass().getResource(cssPath + "/style.css").toString());
		
		primaryStage.setTitle("Akademik Etkinlik Envanteri");
//		primaryStage.setResizable(false);
//		primaryStage.setFullScreen(true);
//		primaryStage.setFullScreenExitHint("Full ekrandan çıkmak için ESC ye basınız.");
//		primaryStage.setFullScreenExitKeyCombination(KeyCombination.valueOf("esc"));
		
		primaryStage.setScene(scene); 
		primaryStage.show();
		
		
//		TableView tableView = new TableView();
//		TableColumn<Personel, String> personelCol = new TableColumn<>("PERSONEL");
//		TableColumn<Personel, String> birlikCol = new TableColumn<>("BİRLİK");
//		
//		TableColumn<Personel, String> adCol = new TableColumn<>("AD");
//		TableColumn<Personel, String> soyadCol = new TableColumn<>("SOYAD");
//		TableColumn<Personel, String> sicilCol = new TableColumn<>("SİCİL");
//		TableColumn<Personel, String> telCol = new TableColumn<>("TEL");
//		TableColumn<Personel, String> tcNoCol = new TableColumn<>("TC");
//		TableColumn<Personel, String> birlikAdCol = new TableColumn("BİRLİK AD");
//		TableColumn<Personel, Birlik> birlikIdCol = new TableColumn("BİRLİK ID");
//
//		personelCol.getColumns().addAll(adCol,soyadCol,sicilCol,telCol,tcNoCol);
//		birlikCol.getColumns().addAll(birlikAdCol,birlikIdCol);
//		
//		adCol.setCellValueFactory(new PropertyValueFactory<Personel,String>("ad"));
//		soyadCol.setCellValueFactory(new PropertyValueFactory<Personel,String>("soyad"));
//		sicilCol.setCellValueFactory(new PropertyValueFactory<Personel,String>("sicil"));
//		telCol.setCellValueFactory(new PropertyValueFactory<Personel,String>("tel"));
//		tcNoCol.setCellValueFactory(new PropertyValueFactory<Personel,String>("tcNo"));
//		birlikAdCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Personel,String>, ObservableValue<String>>() {
//
//			@Override
//			public ObservableValue<String> call(CellDataFeatures<Personel, String> param) {
//				// TODO Auto-generated method stub
//				return (ObservableValue<String>) FXCollections.observableArrayList(
//						param.getValue().getBirlik().getAd()
//						);
//			}
//		});
//		birlikIdCol.setCellValueFactory(new PropertyValueFactory("birlik"));
//		
//		
////		tableView.getColumns().add(adCol);
////		tableView.getColumns().add(soyadCol);
////		tableView.getColumns().add(sicilCol);
////		tableView.getColumns().add(telCol);
////		tableView.getColumns().add(tcNoCol);
////		tableView.getColumns().add(birlikCol);
//		tableView.getColumns().addAll(personelCol,birlikCol);
//		VBox vBox = new VBox();
//		vBox.getChildren().add(tableView);
//		ObservableList<Personel> personelObservableList = FXCollections.observableArrayList(
//				new Personel(1,"38732487348","Ahmet","Cenker","kdfı","25484841584",new Birlik("Birlik1"),null,null,null,null),
//				new Personel(2,"87854548877","Osman","Cenker","kdfı","25484841584",new Birlik("Birlik2"),null,null,null,null)
//				);
//		
//		
//		tableView.setItems(personelObservableList);
//		AnchorPane root = new AnchorPane();
//		
//		
//		
//		root.getChildren().addAll(vBox);
//		primaryStage.setScene(new Scene(root));
//		primaryStage.show();
		
	}
	
	public static void addConsole(String text,String location) {
		
		console += "\nMesaj: "+text+" - Konum: "+location;
	}
	
	public String getConsole() {
		return console;
	}
	
	public void clearConsole() {
		console = "\n";
	}
	
	@Override
	public void stop() {
		System.out.println("Uygulama Kapatılıyor...");
		this.applicationContext.stop();
		Platform.exit();
	}

	
}
