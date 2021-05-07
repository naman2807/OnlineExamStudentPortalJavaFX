package sample;

import database.DataSource;
import database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import onlineserver.OnlineConnectionServer;

import javax.swing.*;
import java.util.Optional;

public class Controller {
    @FXML
    private TextField id;
    @FXML
    private TextField userName;
    @FXML
    private TextField password;
    @FXML
    private Button submit;
    @FXML
    private Button cancel;

    public void initialize() {
        submit.setDisable(true);
        cancel.setDisable(true);
    }

    public void handleKeyPressed() {
        if (!id.getText().isEmpty() || !id.getText().trim().isEmpty()) {
            if (!userName.getText().isEmpty() || !userName.getText().trim().isEmpty()) {
                if (!password.getText().isEmpty() || !password.getText().trim().isEmpty()) {
                    submit.setDisable(false);
                    cancel.setDisable(false);
                } else {
                    submit.setDisable(true);
                    cancel.setDisable(true);
                }
            } else {
                submit.setDisable(true);
                cancel.setDisable(true);
            }
        } else {
            submit.setDisable(true);
            cancel.setDisable(true);
        }
    }

    public void loginToExam() {
        int rollOfStudent = Integer.parseInt(id.getText());
        String userNameOfStudent = userName.getText();
        String password1 = password.getText();
        if (DataSource.checkStudentValidity(DatabaseConnection.getConnection(), rollOfStudent,userNameOfStudent, password1)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Instruction");
            alert.setTitle("You had logged in successfully.");
            alert.setHeaderText("Press OK to mark your attendance.");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                OnlineConnectionServer.activateServer(rollOfStudent, true);
            }
        } else {
            JOptionPane.showMessageDialog(null,"Unable to log in.\nPlease check your credentials"
            ,"Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void clearFields() {
        id.clear();
        userName.clear();
        password.clear();
        submit.setDisable(true);
        cancel.setDisable(true);
    }
}
