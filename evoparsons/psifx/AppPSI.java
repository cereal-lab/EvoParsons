package evoparsons.psifx;

import evoparsons.psifx.models.Config;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *This is the main class of PSIfx and using which we load different scenes to our application on the base stage platform
 * @author himankvats
 */
public class AppPSI extends Application {      
    private static Stage stageStatic;
    private static Config config;
    //some noop
    public static Stage getStage() {return stageStatic;}
    public static Config getConfig() {return config;}
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Puzzle Coder");
        //Using the commented line we can launch the application on fullscreen if required        
        //stage.setFullScreen(true);
        // stage.setHeight(630);
        // stage.setWidth(800);        
        AppPSI.stageStatic = stage;
        Navigate.to(View.LOGIN);
        stage.setResizable(false);
        stage.show();        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //reading config        
        config = Config.load();
        launch(args);        
    }
    
}
