package exam;

import database.DataSource;
import database.DatabaseConnection;
import database.SQlQueries;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import onlineserver.OnlineConnectionServer;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Created By: Naman Agarwal
 * User ID: naman2807
 * Package Name: exam
 * Project Name: OnlineAssessmentStudentPortal
 * Date: 30-03-2021
 */

public final class Examination {
    private static final Scanner scanner = new Scanner(System.in);

    private Examination() {
    }

    /**
     * @return Examination instance
     */
    public static Examination getInstance() {
        return new Examination();
    }

    /**
     * This method terminates the exam.
     */
    public void endExam() {
        System.exit(0);
    }

    /**
     * This method is used to start the exam and communicate between the invigilator and student.
     *
     * @param input
     * @param output
     * @param studentResponse
     * @param roll
     */
    public void startExam(BufferedReader input, PrintWriter output, String studentResponse, int roll) {
        int response;
        try {
            int choice;
            output.println(roll);
            studentResponse = input.readLine();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Instruction");
            alert.setTitle("Do the following");
            alert.setHeaderText(studentResponse);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                if (inputChoice() == 1) {
                    String message = "Student had started giving the exam.";
                    OnlineConnectionServer.sendMessageToInvigilator(output, message);
                    getExamQuestions();
                    OnlineConnectionServer.receiveMessageFromInvigilator(input);
                    Thread.sleep(5000L);
                    int choice1 = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter the number provided by invigilator to get viva question."
                    ,"Viva Notification", JOptionPane.PLAIN_MESSAGE));
                    if (choice1 == 2) {
                        getVivaQuestion();
                        OnlineConnectionServer.sendMessageToInvigilator(output, "Student had started giving the viva");
                    } else {
                        System.err.println("Invalid Input");
                        inputChoice();
                    }

                } else {
                    System.err.println("Invalid Input.");
                    inputChoice();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This methods is used for getting exam questions.
     */
    private void getExamQuestions() {
        ObservableList<String> questions = DataSource.getQuestions(DatabaseConnection.getConnection(), 2, SQlQueries.showQuestionQuery());
        printQuestions(questions);
    }

    /**
     * This method is used for getting viva questions.
     */
    private void getVivaQuestion() {
        ObservableList<String> questions = DataSource.getQuestions(DatabaseConnection.getConnection(), 1, SQlQueries.showVivaQuestionQuery());
        printQuestions(questions);

    }

    /**
     * This method is used to take choice input from student.
     *
     * @return integer value
     */
    private int inputChoice() {
        return Integer.parseInt(JOptionPane.showInputDialog(null,"Enter the value asked by invigilator","Start Exam",
                JOptionPane.PLAIN_MESSAGE));
    }

    /**
     * This method is used to print questions to console.
     *
     * @param questionsList
     */
    private void printQuestions(ObservableList<String> questionsList) {
        StringBuilder stringBuilder = new StringBuilder();
        questionsList.forEach(e -> stringBuilder.append(e).append("\n"));
        JOptionPane.showMessageDialog(null, stringBuilder.toString(),"Following are the questions for today's exam:"
                ,JOptionPane.YES_NO_CANCEL_OPTION);
    }
}
