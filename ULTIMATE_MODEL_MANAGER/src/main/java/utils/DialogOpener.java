package utils;

import java.io.File;
import java.io.IOException;

import controllers.EditDependencyParameter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import parameters.DependencyParameter;

public class DialogOpener {
	
	public static String openPrismFileDialog(Stage stage) {
	    FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Choose a Prism Model File");
	    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PRISM file extensions", FileUtils.VALID_PRISM_FILE_EXTENSIONS));
	    File selectedFile = fileChooser.showOpenDialog(stage);
	    return (selectedFile != null) ? selectedFile.getAbsolutePath() : null; 
	}
	
	public static void openDialogWindow(Stage ownerStage, String path, String title) throws IOException {
	    FXMLLoader loader = new FXMLLoader(DialogOpener.class.getResource(path));
	    Parent root = loader.load();
	    // Create a new Stage
	    Stage dialogStage = new Stage();
	    // Set the owner of the dialog stage to the passed stage.
	    dialogStage.initOwner(ownerStage);
	    // Use APPLICATION_MODAL to block events to the owner until this dialog is closed.
	    dialogStage.initModality(Modality.WINDOW_MODAL);
	    dialogStage.setTitle(title);
	    dialogStage.setScene(new Scene(root));
	    dialogStage.showAndWait(); // Alternatively, show() if you don't need to wait.
	}
	/*
	 * Overloaded method to allow the passing of a dependency Parameter
	 */
	public static void openDialogWindow(Stage ownerStage, String path, String title, DependencyParameter dp) throws IOException {
	    FXMLLoader loader = new FXMLLoader(DialogOpener.class.getResource(path));
	    Parent root = loader.load();
	    EditDependencyParameter controller = loader.getController();
	    controller.setDP(dp);
	    // Create a new Stage
	    Stage dialogStage = new Stage();
	    // Set the owner of the dialog stage to the passed stage.
	    dialogStage.initOwner(ownerStage);
	    // Use APPLICATION_MODAL to block events to the owner until this dialog is closed.
	    dialogStage.initModality(Modality.WINDOW_MODAL);
	    dialogStage.setTitle(title);
	    dialogStage.setScene(new Scene(root));
	    dialogStage.showAndWait(); // Alternatively, show() if you don't need to wait.
	}

}
