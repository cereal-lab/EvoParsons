package evoparsons.psifx;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 *  The contents of FXML file will be drawn. The switching of scene takes place on the same stage, 
 *  we are just changing to what has to be shown on the screen at that time.
 * @author himankvats
 */


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public final class Navigate {

    private static class NavigationNode {
        public final BiInitializable controller;
        public final Scene scene;
        public NavigationNode(BiInitializable controller, Scene scene) {
            this.controller = controller;
            this.scene = scene;
        }
    }

    private static Map<String, NavigationNode> navigations = new HashMap<>();
    @SuppressWarnings("unchecked")
    public static <Controller extends BiInitializable> void to(String viewUrl, Consumer<Controller> contollerInitializer)
    {
        if (navigations.containsKey(viewUrl)) {
            NavigationNode nav = navigations.get(viewUrl);   
            nav.controller.initializeAgain();                   
            AppPSI.getStage().setScene(nav.scene);
        } else 
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AppPSI.class.getResource(viewUrl));
            Node root = fxmlLoader.load();
            Controller controller = (Controller)fxmlLoader.getController();  
            contollerInitializer.accept(controller);   
            Scene scene = new Scene((Parent)root);
            navigations.put(viewUrl, new NavigationNode(controller, scene));
            AppPSI.getStage().setScene(scene);
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    } 

    public static void to(String viewUrl)
    {
        if (navigations.containsKey(viewUrl)) {
            NavigationNode nav = navigations.get(viewUrl);  
            nav.controller.initializeAgain();
            AppPSI.getStage().setScene(nav.scene);
        } else 
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AppPSI.class.getResource(viewUrl));
            Node root = fxmlLoader.load();
            Scene scene = new Scene((Parent)root);
            navigations.put(viewUrl, new NavigationNode(fxmlLoader.getController(), scene));
            AppPSI.getStage().setScene(scene);
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }     
}
