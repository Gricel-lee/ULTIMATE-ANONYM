package controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import parameters.DependencyParameter;
import parameters.DependencyParameterCell;
import parameters.ExternalParameter;
import parameters.ExternalParameterCell;
import parameters.UncategorisedParameter;
import project.Project;
import sharedContext.SharedContext;
import utils.DialogOpener;
import utils.FileUtils;
import utils.Font;

public class ParameterController {
	
	@FXML private Label modelDetails;
	
	@FXML private Button addDependencyParamButton;
	@FXML private Button addExternalParamButton;
	
	@FXML private ListView<UncategorisedParameter> uParamList;
	@FXML private ListView<DependencyParameter> dParamList; // each HBox will be generated by ParameterDisplay
	@FXML private ListView<ExternalParameter> eParamList;
	
	@FXML private VBox undefinedParametersVBox;
	@FXML private VBox parameterDialogs;

    private SharedContext sharedContext = SharedContext.getInstance();
    private Project project = sharedContext.getProject();

	@FXML
	public void initialize() {
		setListCells();
		firstcall();
		setModelChangeListener();
	}
	
	private void firstcall() {
        Platform.runLater(() -> {
        	if (project.getCurrentModel() != null) {
				try {
					modelDetails.setText("Model ID: " + project.getCurrentModel().getModelId() + "\nFile: " + FileUtils.removePrismFileExtension(project.getCurrentModel().getFilePath()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            uParamList.setItems(project.getCurrentModel().getUncategorisedParameters());
	            dParamList.setItems(project.getCurrentModel().getDependencyParameters());
	            eParamList.setItems(project.getCurrentModel().getExternalParameters());
        	}
        });
	}
	
	private void setModelChangeListener() {
        // When a new model is selected, update the ListView with its uncategorised parameters
        project.currentModelProperty().addListener((obs, oldModel, newModel) -> {
            if (newModel != null) {
                // Retrieve the list of Uncategorised Parameters from the new model.
                Platform.runLater(() -> {
                    uParamList.setItems(newModel.getUncategorisedParameters());
                    dParamList.setItems(project.getCurrentModel().getDependencyParameters());
    	            eParamList.setItems(project.getCurrentModel().getExternalParameters());
                    try {
        				modelDetails.setText("Model ID: " + project.getCurrentModel().getModelId() + "\nFile: " + FileUtils.removePrismFileExtension(project.getCurrentModel().getFilePath()));
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
                });
            }
        });
	}
	
	private void setListCells() {
    	// Customise the appearance of each item in the list view
        uParamList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(UncategorisedParameter item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    Label label = new Label(item.getName()); // Display the model ID
                    label.setStyle(Font.UC_LIST_FONT); // Apply font styling
                    setGraphic(label); // Set the label as the cell's graphic
                    setText(null); // Clear any text (not needed with graphic)
                }
            }
        });
        
        // set dParam to the unit cell update
        dParamList.setCellFactory(listView -> {
            DependencyParameterCell cell = new DependencyParameterCell();
            cell.setDependencyUnitListener(new DependencyParameterCell.DependencyUnitListener() {
                @Override
                public void onEdit(DependencyParameter dp) {
                	try {
                        DialogOpener.openDialogWindow(sharedContext.getMainStage(), "/dialogs/edit_Dependency_param.fxml", "Edit Dependency Parameter", dp);
                	} catch (IOException e) {
                		e.printStackTrace();
                	}
                }

                @Override
                public void onRemove(DependencyParameter dp) {
                	String name = dp.getName();
                    // Handle remove action for the dependency parameter dp
                    project.getCurrentModel().removeDependencyParameter(dp);
                    project.getCurrentModel().addUncategorisedParameter(new UncategorisedParameter(name));
                }
            });
            return cell;
        });
        
        // set dParam to the unit cell update
        eParamList.setCellFactory(listView -> {
            ExternalParameterCell cell = new ExternalParameterCell();
            cell.setExternalUnitListener(new ExternalParameterCell.ExternalUnitListener() {
                @Override
                public void onEdit(ExternalParameter ep) {
                	try {
                        DialogOpener.openDialogWindow(sharedContext.getMainStage(), "/dialogs/edit_external_param.fxml", "Edit Dependency Parameter", ep);
                	} catch (IOException e) {
                		e.printStackTrace();
                	}
                }

                @Override
                public void onRemove(ExternalParameter ep) {
                	String name = ep.getName();
                    // Handle remove action for the external parameter ep
                    project.getCurrentModel().removeExternalParameter(ep);
                    project.getCurrentModel().addUncategorisedParameter(new UncategorisedParameter(name));
                }
            });
            return cell;
        });

	}
	
	// TODO handle the IOE better here with an alerter and same for the edit button above
	@FXML
	private void addDepParam() throws IOException {
		DialogOpener.openDialogWindow(sharedContext.getMainStage(),"/dialogs/add_Dependency_param.fxml", "Add Dependency Parameter");
	}
	
	@FXML
	private void addEParam() throws IOException {
		DialogOpener.openDialogWindow(sharedContext.getMainStage(),"/dialogs/add_external_param.fxml", "Add External Parameter");
	}
}
