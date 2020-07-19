package com.jivko.env_manager.Controller;

import com.jivko.env_manager.Services.ModelService;
import com.jivko.env_manager.Services.ServiceContainer;
import com.jivko.env_manager.Services.XammpControllerService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.*;

public class Controller {

    @FXML
    private Menu mProfiles;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private GridPane mainGrid;

    private ServiceContainer container;

    public Controller() {
        this.container = new ServiceContainer();
    }

    @FXML
    public void initialize() {
        refreshMainGrid();

        MenuItem first = new MenuItem();
        first.setText("bla bla");
        mProfiles.getItems().add(first);
    }

    private void refreshMainGrid(){
        ModelService modelService = (ModelService) this.container.getService("model");
        ArrayList<String> xamppDirs = modelService.getDirs();

        //init main grid headers
        Label xamppDirLabel = new Label("XAMPP instance");
        Label apacheLabel = new Label("Apache");
        Label sqlLabel = new Label("MySQL");
        mainGrid.add(xamppDirLabel, 0, 0);
        mainGrid.add(apacheLabel, 1, 0);
        mainGrid.add(sqlLabel, 2, 0);

        //init body
        //fill dirs
        if(!xamppDirs.isEmpty()) {
            for (int index = 0; index < xamppDirs.size(); index++) {
                Label xamppDir = new Label( xamppDirs.get(index) );
                mainGrid.add(xamppDir, 0, index + 1);

                //fill checboxes
                CheckBox apacheCB = new CheckBox();
                apacheCB.setSelected(false);
                apacheCB.setOnAction(this::apacheCBAction);
                apacheCB.setId(xamppDirs.get(index));
                mainGrid.add(apacheCB, 1, index + 1);

                CheckBox sqlCB = new CheckBox();
                sqlCB.setSelected(false);
                sqlCB.setId(xamppDirs.get(index));
                sqlCB.setOnAction(this::mysqlCBAction);
                mainGrid.add(sqlCB, 2, index + 1);
            }
        }
    }

    public void addXamppDirAction() {
        Stage primaryStage = (Stage) this.mainPane.getScene().getWindow();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory =
                directoryChooser.showDialog(primaryStage);

        System.out.println("addXamppDirAction clicked");
        if(selectedDirectory == null){
            System.out.println("No Directory selected");
        }else{
            System.out.println(selectedDirectory.getAbsolutePath());
            ModelService modelService = (ModelService) this.container.getService("model");
            modelService.addDir(selectedDirectory.getAbsolutePath());

            this.refreshMainGrid();
        }
    }

    public void apacheCBAction(ActionEvent event){
        XammpControllerService xammpControllerService = (XammpControllerService) this.container.getService("control");
        CheckBox source = (CheckBox) event.getSource();
        if(source.isSelected()){
            //find and stop another apache and clear other checkboxes
            ObservableList<Node> childrens = this.mainGrid.getChildren();
            for (Node child : childrens) {
                int column = GridPane.getColumnIndex(child);
                if ( column == 1 &&
                        child.getClass() == CheckBox.class &&
                        ((CheckBox) child).isSelected() &&
                        !source.getId().equals(child.getId())
                ) {
                    xammpControllerService.stopApache(child.getId());
                    ((CheckBox) child).setSelected(false);
                }
            }
            xammpControllerService.startApache(source.getId());
        } else {
            xammpControllerService.stopApache(source.getId());
        }
    }

    public void mysqlCBAction(ActionEvent event){
        XammpControllerService xammpControllerService = (XammpControllerService) this.container.getService("control");
        CheckBox source = (CheckBox) event.getSource();
        if(source.isSelected()){
            //find and stop another apache and clear other checkboxes
            ObservableList<Node> childrens = this.mainGrid.getChildren();
            for (Node child : childrens) {
                int column = GridPane.getColumnIndex(child);
                if ( column == 2 &&
                        child.getClass() == CheckBox.class &&
                        ((CheckBox) child).isSelected() &&
                        !source.getId().equals(child.getId())
                ) {
                    xammpControllerService.stopMySql(child.getId());
                    ((CheckBox) child).setSelected(false);
                }
            }
            xammpControllerService.startMySql(source.getId());
        } else {
            xammpControllerService.stopMySql(source.getId());
        }

        //clear other checkboxes
        ObservableList<Node> childrens = this.mainGrid.getChildren();
        for (Node child : childrens) {
            int column = GridPane.getColumnIndex(child);
//            if (column == 3 && child.getId() == source.getId()) {
//                ((CheckBox) child).setSelected(true);
//            }
        }
    }

}
