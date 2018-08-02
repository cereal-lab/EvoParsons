package evoparsons.psifx;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * FXML Controller class for Report.fxml
 * This controller is used to load result screen for the student
 * Note: stname,honame,pno,time,totalpuzzle,correctpuzzle are provided by the puzzleController once the student decides to Exit the application.
 * Time is the total time the student has worked on the application (Not to be confused with the time per puzzle)
 * @author himankvats
 */
public class ReportController implements BiInitializable {
    @FXML
    private Label login;
    @FXML
    private Label server;
    @FXML
    private Label duration;
    @FXML
    private Label date;    
    @FXML
    private Label puzzles;    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeAgain();
    }    

    @FXML
    private void handleDoneButtonAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML 
    private void handleBackButtonAction(ActionEvent event) {
        Navigate.to(View.PUZZLE);
    }

	@Override
	public void initializeAgain() {
        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d yyyy");
        Duration d = Duration.millis(PuzzleController.stats.timeSpan);   
        login.setText(String.format("%s, %d", AuthController.auth.login, AuthController.studentId));
        server.setText(String.format("%s:%d", AuthController.auth.host, AuthController.auth.port));
        duration.setText(String.format("%d minutes", (long)d.toMinutes()));
        puzzles.setText(String.format("%d/%d", PuzzleController.stats.numCorrectPuzzles, PuzzleController.stats.numTotalPuzzles));
        date.setText(dateFormat.format(Calendar.getInstance().getTime()));		
	}
    
}
