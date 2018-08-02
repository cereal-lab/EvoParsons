package evoparsons.psifx;


import java.net.URL;
import java.util.ResourceBundle;

import evoparsons.psifx.models.Auth;
import evoparsons.rmishared.BrokerClient;
import evoparsons.rmishared.BrokerInterface;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

/**
 * FXML Controller class for login.fxml
 * In this controller we accept three values from the user for Username, hostname and port. 
 * This values are passed to PuzzleController to establish the connection to server.
 *  
 * //// We can also define values of port and hostname via property file if we want to configure it to one particular server. 
 * @author himankvats
 */
public class AuthController implements BiInitializable {
    private static final int CONNECT_RETRY = 3;
    private static final int MINPORT = 1000;
    private static final int MAXPORT = 2000;
    public static Auth auth; 
    public static BrokerInterface broker;
    public static int studentId;
    
    @FXML 
    private ProgressBar connectionProgress;
    @FXML
    private TextField login;
    private ContextMenu loginError;
    @FXML
    private TextField host;
    private ContextMenu hostError;
    @FXML
    private TextField port;
    private ContextMenu portError;
    @FXML
    private Button loginButton;
    @FXML
    private Label statusMsg;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String prevLogin = AppPSI.getConfig().getProp("login");
        if (!prevLogin.isEmpty()) login.setText(prevLogin);
        if (login.getText().isEmpty()) login.requestFocus();
        else loginButton.requestFocus();
        host.setText(AppPSI.getConfig().host == null ? "" : AppPSI.getConfig().host);
        port.setText(AppPSI.getConfig().port.isPresent() ? AppPSI.getConfig().port.get().toString() : "");
        connectionProgress.setManaged(false);
        login.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                handleLoginButtonAction(new ActionEvent());
            }});
        host.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                handleLoginButtonAction(new ActionEvent());
            }});            
        port.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                handleLoginButtonAction(new ActionEvent());
            }});                            
    }        

    private void tryConnect(int attempt) {
        if (attempt >= CONNECT_RETRY) {
            connectionProgress.setVisible(false);
            connectionProgress.setManaged(false);
            loginButton.setDisable(false);
            //statusMsg.setTextFill(Paint.valueOf("red"));
            statusMsg.setText("Connection failed!");        
            return;
        }
        class Tmp {
            public BrokerInterface b;
            public int studentId;
            public Tmp(BrokerInterface b, int studentId) {
                this.b = b;
                this.studentId = studentId;
            }
        }
        Task<Tmp> connect = new Task<Tmp>() {
            @Override 
            protected Tmp call() throws Exception {
                BrokerInterface b = BrokerClient.connect(auth.host, auth.port);
                int studentId = b.getStudentID(auth.login);
                return new Tmp(b, studentId);
            }
        };
        connect.setOnSucceeded(state -> {
            Tmp tmp = connect.getValue();
            System.out.printf("[BrokerInterface] Attempt %d. Connection succeeded. Id %d%n", attempt, tmp.studentId);
            Platform.runLater(() -> 
            {                
                broker = tmp.b;
                studentId = tmp.studentId;
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                    connectionProgress.setVisible(false);
                    connectionProgress.setManaged(false);
                    statusMsg.setText("Connected!");
                    //AppPSI.getStage().setResizable(true);                    
                    Navigate.to(View.PUZZLE);
                }));
                timeline.play();
            });
        });
        connect.setOnFailed(state -> {
            System.out.printf("[BrokerInterface] Attempt %d. Connection failed%n", attempt);
            Platform.runLater(() -> 
            {
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3.0), e -> tryConnect(attempt + 1)));
                timeline.play();
            });
        });
        //connect.run();
        new Thread(connect).start();
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {        
        if (login.getText().trim().isEmpty()) {
            loginError = new ContextMenu();
            loginError.getItems().add(new MenuItem("Student name is required"));
            loginError.show(login, Side.TOP, 0, 5);            
            return;
        } else {
            if (loginError != null)
            {
                loginError.hide();
                loginError = null;
            }
        }
        if (host.getText().trim().isEmpty()) {
            hostError = new ContextMenu();
            hostError.getItems().add(new MenuItem("Host name is required"));
            hostError.show(host, Side.TOP, 0, 5);            
            return;
        } else {
            if (hostError != null)
            {
                hostError.hide();
                hostError = null;
            }
        }     
        int p = 0;   
        try {
            p = Integer.parseInt(port.getText());  
            if (portError != null) {
                portError.hide();
                portError = null;
            }
        } catch (NumberFormatException e) {
        }
        if ((p < MINPORT) || (p > MAXPORT)) {
            portError = new ContextMenu();
            portError.getItems().add(new MenuItem(String.format("Port should be a number between %d and %d", MINPORT, MAXPORT)));
            portError.show(port, Side.TOP, 0, 5);
            return;            
        }
        auth = new Auth(login.getText().trim(), host.getText(), p);
        AppPSI.getConfig().setProp("login", auth.login);
        AppPSI.getConfig().setProp("evoparsons.host", auth.host);
        AppPSI.getConfig().setProp("evoparsons.port", String.valueOf(auth.port));
        AppPSI.getConfig().save();
        connectionProgress.setManaged(true);
        connectionProgress.setVisible(true);
        statusMsg.setText("Connecting...");
        loginButton.setDisable(true);
        tryConnect(0);
    }

	@Override
	public void initializeAgain() {
		
	}

    
}
