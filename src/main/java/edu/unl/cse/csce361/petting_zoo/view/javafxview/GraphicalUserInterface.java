package edu.unl.cse.csce361.petting_zoo.view.javafxview;

import edu.unl.cse.csce361.petting_zoo.controller.Command;
import edu.unl.cse.csce361.petting_zoo.controller.PlayerMenus;
import edu.unl.cse.csce361.petting_zoo.view.AbstractUserInterface;
import edu.unl.cse.csce361.petting_zoo.view.Screens;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GraphicalUserInterface extends AbstractUserInterface {
    @FXML
    TilePane buttonPane;

    @FXML
    Label informationLabel;

    @FXML
    public void initialize() {
        addCommands(PlayerMenus.mainMenu);
        showInformation(Screens.splashScreen);
        fixUI();        // Why does the GraphicalUserInterface object change? Why is this call necessary?
    }

    @Override
    public int addCommand(Command command) {
        int numberOfCommands = super.addCommand(command);
        CommandButton button = CommandButton.getButtonFor(command);
        buttonPane.getChildren().add(button);
        return numberOfCommands;
    }

    @Override
    public int removeCommand(Command command) {
        int numberOfCommands = super.removeCommand(command);
        CommandButton button = CommandButton.getButtonFor(command);
        buttonPane.getChildren().remove(button);
        return numberOfCommands;
    }

    @Override
    public int addCommands(List<Command> commands) {
        int numberOCommands = super.addCommands(commands);
        List<CommandButton> buttonList =
                commands.stream().map(CommandButton::getButtonFor).collect(Collectors.toList());
        buttonPane.getChildren().addAll(buttonList);
        return numberOCommands;
    }

    @Override
    public int removeCommands(Collection<Command> commands) {
        int numberOfCommands = super.removeCommands(commands);
        List<CommandButton> buttonList =
                commands.stream().map(CommandButton::getButtonFor).collect(Collectors.toList());
        buttonPane.getChildren().removeAll(buttonList);
        return numberOfCommands;
    }

    @Override
    public int replaceCommands(List<Command> commands) {
        List<Node> buttonList = buttonPane.getChildren();
        List<CommandButton> newButtonList =
                commands.stream().map(CommandButton::getButtonFor).collect(Collectors.toList());
        buttonList.removeAll(buttonPane.getChildren());
        int numberOfCommands = super.replaceCommands(commands);
        buttonList.addAll(newButtonList);
        return numberOfCommands;
    }

    @Override
    public void showInformation(String information) {
        informationLabel.setText(information);
    }

    @Override
    public void startGame() {
        JavaFXInterface.launch(JavaFXInterface.class);
    }

    @Override
    public void endGame() {
        Platform.exit();
    }

    public static class JavaFXInterface extends Application {

        @Override
        public void start(Stage stage) throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("petting_zoo.fxml"));
            stage.setTitle("Pleistocene Petting Zoo");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}
