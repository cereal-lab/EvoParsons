package evoparsons.psifx;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import evoparsons.psifx.models.Stats;
import evoparsons.rmishared.Fragment;
import evoparsons.rmishared.ParsonsEvaluation;
import evoparsons.rmishared.ParsonsPuzzle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.util.Duration;

public class PuzzleController implements BiInitializable {

    public static final int MAX_CONNECT_ATTEMPS = 3;
    public static final int DELAY_BETWEEN_RETRIES = 3; //3seconds
    public static Stats stats;
    
    private long currentPuzzleStartTimeMs;
    private long sessionStartTimeMs;
    private int moves;
    private int obtainedPuzzleCount = 0;
    private int solvedPuzzleCount = 0;

    private ParsonsPuzzle currentPuzzle;    
    private List<String> correctAnswer;
    private List<Fragment> currentPuzzleFragments;
    @FXML
    Label title;
    @FXML
    Label description;
    @FXML
    ListView<String> puzzle;
    @FXML
    ListView<String> trash;
    @FXML
    ListView<String> answer;
    @FXML
    ListView<String> feedback;
    @FXML
    ProgressBar connectionProgress;
    
    @FXML
    Button nextButton;
    @FXML
    Button giveUpButton;

    private void onPuzzleArrival(ParsonsPuzzle currentPuzzle) {
        //ParsonsPuzzle currentPuzzle = getter.getValue();
        System.out.printf("[Puzzle.onPuzzleArrival] Puzzle (%d, %d) obtained%n", AuthController.studentId, currentPuzzle.id);            
        Platform.runLater(() -> 
        {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> 
            {
                this.currentPuzzle = currentPuzzle;  
                this.correctAnswer = 
                    currentPuzzle.program
                        .stream()
                        .map(lines -> String.join(System.lineSeparator(), lines))
                        .collect(Collectors.toList());                     
                this.currentPuzzleFragments = currentPuzzle.buildFragments();                    
                moves = 0;       
                title.setText(currentPuzzle.title);
                description.setText(currentPuzzle.description);
                puzzle.setItems(
                    FXCollections.observableArrayList(
                        currentPuzzleFragments.stream().map(f -> f.line)
                            .collect(Collectors.toList())
                        ));
                answer.setItems(FXCollections.observableArrayList(""));
                trash.setItems(FXCollections.observableArrayList(""));
                replaceFeedbackMessage(String.format("Puzzle %d has been loaded", currentPuzzle.id));
                connectionProgress.setVisible(false);
                currentPuzzleStartTimeMs = System.currentTimeMillis();
                nextButton.setDisable(false);
                giveUpButton.setDisable(false);
            }));
            timeline.play();
        });
    }

    private void getPuzzle(int attempt) {        
        if (attempt >= MAX_CONNECT_ATTEMPS) {
            addFeedbackMessage("All connection attempts failed. Try again later");            
            return;
        }
        Task<ParsonsPuzzle> getter = new Task<ParsonsPuzzle>() {
            @Override 
            protected ParsonsPuzzle call() throws Exception {
                return AuthController.broker.getParsonsPuzzle(AuthController.studentId);
            }
        };
        getter.setOnSucceeded(state -> onPuzzleArrival(getter.getValue())); 
        getter.setOnFailed(state -> {
            Platform.runLater(() -> {
                System.err.printf("[Puzzle.getPuzzle] %s%n", getter.getException().getMessage());
                addFeedbackMessage("Connection to server failed");
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(DELAY_BETWEEN_RETRIES), e -> getPuzzle(attempt + 1)));
                timeline.play();        
            });
        });
        new Thread(getter).start();
    }

    public void addFeedbackMessage(String msg) {
        feedback.getItems().add(msg);
        feedback.scrollTo(feedback.getItems().size() - 1);
    }
    public void replaceFeedbackMessage(String msg) {
        feedback.getItems().set(feedback.getItems().size()-1, msg);
    }

    @SuppressWarnings("unchecked")
    private void applyChanges(Node target, ListCell<String> movingCell, DragEvent event) {
        if (target instanceof ListCell) {
            ListCell<String> targetCell = (ListCell<String>)target;
            int index = Math.min(targetCell.getIndex(), targetCell.getListView().getItems().size()-1);            
            if (targetCell.getListView().equals(movingCell.getListView()))
            {               
                if (movingCell.getIndex() > index) {
                    targetCell.getListView().getItems().add(index, movingCell.getItem());
                    movingCell.getListView().getItems().remove(movingCell.getIndex()+1);            
                } else {
                    if (index == (targetCell.getListView().getItems().size()-1))
                    {
                        targetCell.getListView().getItems().add(index, movingCell.getItem());
                        movingCell.getListView().getItems().remove(movingCell.getIndex());                        
                    } else 
                    {
                        targetCell.getListView().getItems().add(index+1, movingCell.getItem());
                        movingCell.getListView().getItems().remove(movingCell.getIndex());
                    }
                }
            } else {
                targetCell.getListView().getItems().add(index, movingCell.getItem());
                movingCell.getListView().getItems().remove(movingCell.getIndex());            
            }
            event.consume();
        }
        else if (target instanceof ListView) {
            ListView<String> list = ((ListView<String>)target);
            list.getItems().add(list.getItems().size() - 1, movingCell.getItem());
            movingCell.getListView().getItems().remove(movingCell.getIndex());
        }        
        else applyChanges(target.getParent(), movingCell, event);
    }

    @SuppressWarnings("unchecked")
    private void onDrop(DragEvent event) {
        ListCell<String> movingCell = (ListCell<String>)event.getGestureSource();       
        applyChanges((Node)event.getTarget(), movingCell, event);        
        if (puzzle.getItems().isEmpty()) {
            addFeedbackMessage("You can submit answer now.");
        } 
        moves++;
        event.setDropCompleted(true);    
    }

    private ListCell<String> buildCell() {
        return new ListCell<String>() {
            {        
                addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                    getListView().requestFocus();
                    if (getListView().getSelectionModel().getSelectedIndices().isEmpty()) {
                        getListView().getSelectionModel().clearSelection();
                    }
                    event.consume();
                });
        
                setOnDragDetected(event -> {
                    if ((getItem() == null) || (getItem() == "")) return;
                    Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(getItem());
                    dragboard.setContent(content);    
                    event.consume();
                });
        
                setOnDragOver(event -> {
                    if (!puzzle.equals(getListView()))
                            event.acceptTransferModes(TransferMode.MOVE);
                });
        
                setOnDragEntered(event -> setOpacity(0.3));
        
                setOnDragExited(event -> setOpacity(1));

                setOnDragDropped(e -> onDrop(e));        
                setOnDragDone(DragEvent::consume);
            }        
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                    return;
                } else {
                    setText(item);
                }
            }
        };        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        puzzle.setCellFactory(param -> buildCell());
        answer.setCellFactory(param -> buildCell());
        trash.setCellFactory(param -> buildCell());

        answer.setOnDragOver(e -> e.acceptTransferModes(TransferMode.MOVE));
        trash.setOnDragOver(e -> e.acceptTransferModes(TransferMode.MOVE));            
        answer.setOnDragDropped(e -> onDrop(e));
        trash.setOnDragDropped(e -> onDrop(e));

        addFeedbackMessage("This is status/feedback window");
        addFeedbackMessage("Getting new puzzle...");
        sessionStartTimeMs = System.currentTimeMillis();
        nextButton.setDisable(true);
        giveUpButton.setDisable(true);        
        getPuzzle(0);
    }

    public void sendEval(ParsonsEvaluation eval, int attempt) {
        if (attempt >= MAX_CONNECT_ATTEMPS) {
            System.err.printf("[Puzzle.sendEval] All connection attempts failed%n");
            addFeedbackMessage("All connection attempts failed. Try again latter.");
            return;
        }
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                AuthController.broker.setParsonsEvaluation(eval);
                return null;
            }            
        };
        task.setOnSucceeded(state -> {
            //get new puzzle
            Platform.runLater(() -> {
                if ((AppPSI.getConfig().maxPuzzleCount.isPresent()) && (solvedPuzzleCount == AppPSI.getConfig().maxPuzzleCount.get())) {
                    Alert alert = new Alert(AlertType.INFORMATION, "You have already solved enough number of puzzles\r\nYou can continue or go to report screen", ButtonType.OK);
                    alert.show();
                }
                getPuzzle(0);
            });
        });
        task.setOnFailed(state -> {
            Platform.runLater(() -> {
                System.err.printf("[Puzzle.sendEval] Failed: %s%n", task.getException().getMessage());
                sendEval(eval, attempt + 1); 
            }); 
        });
        new Thread(task).start();
    }

    @FXML
    private void handleReportRequest(ActionEvent event) {
        String loginKey = String.format("%s@%s:%d", AuthController.auth.login, AuthController.auth.host, AuthController.auth.port);
        long sessionEndTimeMs = System.currentTimeMillis();
        long duration = sessionEndTimeMs - sessionStartTimeMs;
        int prevSolvedPuzzleCount = 0;
        try {
            prevSolvedPuzzleCount = Integer.parseInt(AppPSI.getConfig().getProp(loginKey + ".solvedPuzzles"));
        } catch (Exception e) {}
        int prevObtainedPuzzleCount = 0;
        try {
            prevObtainedPuzzleCount = Integer.parseInt(AppPSI.getConfig().getProp(loginKey + ".obtainedPuzzles"));
        } catch (Exception e) {}
        long prevDuration = 0;
        try {
            prevDuration = Integer.parseInt(AppPSI.getConfig().getProp(loginKey + ".sessionDuration"));
        } catch (Exception e) {}
        stats = new Stats(solvedPuzzleCount + prevSolvedPuzzleCount, obtainedPuzzleCount + prevObtainedPuzzleCount, duration + prevDuration);
        AppPSI.getConfig().setProp(loginKey + ".solvedPuzzles", String.valueOf(stats.numCorrectPuzzles));
        AppPSI.getConfig().setProp(loginKey + ".obtainedPuzzles", String.valueOf(stats.numTotalPuzzles));
        AppPSI.getConfig().setProp(loginKey + ".sessionDuration", String.valueOf(stats.timeSpan));
        AppPSI.getConfig().save();
        Navigate.to(View.REPORT);
    }

    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        List<String> answerLines = 
            answer.getItems().stream()
            .filter(s -> !s.isEmpty())
            .collect(Collectors.toList());
        obtainedPuzzleCount++;
        if (answerLines.equals(correctAnswer))
        {
            System.out.println("Correct answer");
            addFeedbackMessage("Correct");
            addFeedbackMessage("Getting new puzzle...");
            long currentPuzzleEndTimeMs = System.currentTimeMillis();
            solvedPuzzleCount++;
            long currentPuzzleDurationMs = currentPuzzleEndTimeMs - currentPuzzleStartTimeMs;
            ParsonsEvaluation eval = 
                new ParsonsEvaluation(AuthController.studentId, currentPuzzle.id, (double)moves, currentPuzzleDurationMs, false, System.currentTimeMillis());            
            connectionProgress.setVisible(true);
            sendEval(eval, 0);
        } else {
            addFeedbackMessage("Incorrect. Check answer and trash windows.");
        }
    }

    @FXML 
    public void handleGiveUpButtonAction(ActionEvent event) {
        obtainedPuzzleCount++;
        nextButton.setDisable(true);
        giveUpButton.setDisable(true);
        addFeedbackMessage("Better luck next time.");
        addFeedbackMessage("Getting new puzzle...");
        long currentPuzzleEndTimeMs = System.currentTimeMillis();
        long currentPuzzleDurationMs = currentPuzzleEndTimeMs - currentPuzzleStartTimeMs;
        ParsonsEvaluation eval = 
            new ParsonsEvaluation(AuthController.studentId, currentPuzzle.id, (double)moves, currentPuzzleDurationMs, true, System.currentTimeMillis());            
        connectionProgress.setVisible(true);
        sendEval(eval, 0);        
    }

	@Override
	public void initializeAgain() {
        currentPuzzleStartTimeMs = sessionStartTimeMs = System.currentTimeMillis();
        obtainedPuzzleCount = solvedPuzzleCount = 0;
	}
    
}
