package edu.unl.cse.csce361.petting_zoo;

import com.vdurmont.emoji.EmojiParser;
import edu.unl.cse.csce361.petting_zoo.controller.*;
import edu.unl.cse.csce361.petting_zoo.view.textview.TextUserInterface;
import edu.unl.cse.csce361.petting_zoo.view.UserInterface;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CLI {
    private static final UserInterface ui = new TextUserInterface();

    // When we later migrate to Java 11, we'll want to use List.of()
    public static final List<Command> mainMenu = Collections.unmodifiableList(Arrays.asList(
            new PlayAsOwnerCommand(),
            new PlayAsAnimalHandlerCommand(),
            new PlayAsVisitorCommand(),
            new ResetCommand(),
            new ExitCommand())
    );

    public static void main(String[] args) {
        ui.startGame();
    }
}
