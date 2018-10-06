package main.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class UiController {

    @FXML
    private TextField in;
    @FXML
    private TextField out;
    @FXML
    private TextField key;
    @FXML
    private ToggleGroup type;

    private String inPath;
    private String outPath;
    private String keyStr;
    private int cipherType;

    private EncryptionController cryptController;

    public void initialize(){
        cryptController = new EncryptionController(this);

        type.selectedToggleProperty().addListener(
                (observable, oldValue, newValue) ->
                        cipherType = (Integer)type.getSelectedToggle().getUserData());
    }

    public void OnSelectFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открытие файла");
        File file = fileChooser.showOpenDialog(null);
        if (file!=null && file.exists()){
            in.setText(file.getAbsolutePath());
        }
    }

    public void DoEncryption(ActionEvent actionEvent) {

        if (!validate()){
            return;
        }
        cryptController.encryption(inPath,outPath,keyStr,cipherType,EncryptionController.ENC_MOD);
    }

    public void OnSwap(ActionEvent actionEvent) {
        String inPath = in.getText().trim();
        in.setText(out.getText().trim());
        out.setText(inPath);
    }

    public void DoDecryption(ActionEvent actionEvent) {
        if (!validate()){
            return;
        }
        cryptController.encryption(inPath,outPath,keyStr,cipherType,EncryptionController.DEC_MOD);

    }

    public void OnOpenFolder(ActionEvent actionEvent) {
        if (out.getText().trim().isEmpty()){
           DirectoryChooser directoryChooser = new DirectoryChooser();
           directoryChooser.setTitle("Выбор папки сохранения");
           File file = directoryChooser.showDialog(null);
           if (file!=null && file.exists()){
               out.setText(file.getAbsolutePath() + "\\encryptedfile");
           }
        }else {
            if (Files.exists(Paths.get(out.getText().trim()))) {
                File file = new File(out.getText().trim());
                try {
                    if (file.getParentFile() == null) {
                        return;
                    }
                    Desktop.getDesktop().open(file.getParentFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private boolean validate(){
        inPath = in.getText().trim();
        outPath = out.getText().trim();
        keyStr = key.getText().trim();
        if (keyStr.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Введите ключ!");
            alert.show();
        }
        if (keyStr.length()<4){
            while (keyStr.length()<4){
                keyStr+="0";
            }
        }

        if (in.getText().trim().isEmpty() || out.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Не все файлы выбраны!");
            alert.show();
            return false;
        }
        if (!Files.exists(Paths.get(inPath))){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Указанного файла не существует!");
            alert.show();
            return false;
        }
        if (Files.exists(Paths.get(outPath))){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Файл уже существует, перезаписать?",
                    ButtonType.CANCEL,ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult()==ButtonType.YES){
                try {
                    Files.delete(Paths.get(outPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                return false;
            }

        }
        return true;
    }

    public void showSimpleAlert(Alert.AlertType type,String message){
        Platform.runLater(() -> {
            Alert alert = new Alert(type,message);
            alert.show();
        });
    }

}
