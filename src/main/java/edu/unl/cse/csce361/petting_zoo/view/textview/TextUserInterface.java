package edu.unl.cse.csce361.petting_zoo.view.textview;

import edu.unl.cse.csce361.petting_zoo.CLI;
import edu.unl.cse.csce361.petting_zoo.controller.Command;
import edu.unl.cse.csce361.petting_zoo.view.AbstractUserInterface;
import edu.unl.cse.csce361.petting_zoo.view.Screens;
import edu.unl.cse.csce361.petting_zoo.view.StringBox;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TextUserInterface extends AbstractUserInterface {
    private boolean playGame;
    private final Scanner scanner;
    private final StringBox stringBox;
    private static final int boxWidth = 80;
    private static final int boxHeight = 24;
    private static final int commandOffset = 4;

    public TextUserInterface() {
        playGame = false;
        scanner = new Scanner(System.in);
        stringBox = new StringBox(boxHeight, boxWidth);
        stringBox.placeString("You can:", 0, 0);
        stringBox.placeString("Please make a selection: ", boxHeight - 1, 0);
    }

    private void displayCommand(Command command, int row) {
        stringBox.placeStringAlignTopRight(row + ")", row, commandOffset);
        stringBox.placeStringAlignTopLeft(command.toString(), row, commandOffset + 1);
    }

    private void unDisplayCommand(Command command, int row) {
        // When we later migrate to Java 11, we'll want to use String.repeat()
        int lengthOfBlankString = commandOffset + command.toString().length() + 1;
        String blankString = new String(new char[lengthOfBlankString]).replace("\0", " ");
        stringBox.placeStringAlignTopLeft(blankString, row, 0);
    }

    private void displayManyCommands(List<Command> commands, int startingRow) {
        int row = startingRow;
        for (Command command : commands) {
            displayCommand(command, row++);
        }
    }

    private void unDisplayAllCommands(List<Command> commands) {
        int row = 1;
        for (Command command : commands) {
            unDisplayCommand(command, row++);
        }
    }

    @Override
    public int addCommand(Command command) {
        int numberOfCommands = super.addCommand(command);
        displayCommand(command, numberOfCommands);
        return numberOfCommands;
    }

    @Override
    public int removeCommand(Command command) {
        int numberOfCommands = super.removeCommand(command);
        unDisplayCommand(command, numberOfCommands + 1);
        return numberOfCommands;
    }

    @Override
    public int addCommands(List<Command> commands) {
        int commandNumber = getDisplayedCommands().size() + 1;
        int numberOfCommands = super.addCommands(commands);
        displayManyCommands(commands, commandNumber);
        return numberOfCommands;
    }

    @Override
    public int removeCommands(Collection<Command> commands) {
        unDisplayAllCommands(getDisplayedCommands());
        int numberOfCommands = super.removeCommands(commands);
        displayManyCommands(getDisplayedCommands(), 1);
        return numberOfCommands;
    }

    @Override
    public int replaceCommands(List<Command> commands) {
        unDisplayAllCommands(getDisplayedCommands());
        int numberOfCommands = super.replaceCommands(commands);
        displayManyCommands(getDisplayedCommands(), 1);
        return numberOfCommands;
    }

    @Override
    public void showInformation(String information) {
        // When we later migrate to Java 11, we'll want to use String.repeat()
        for (int row = 0; row < boxHeight - 1; row++) {
            stringBox.placeStringAlignTopLeft("                                        ",
                    row, boxWidth / 2);
        }
        stringBox.placeStringAlignTopLeft(information, 0, boxWidth / 2);
    }

    @Override
    public void startGame() {
        playGame = true;
        addCommands(CLI.mainMenu);
        showInformation(Screens.splashScreen);
        while (playGame) {
            System.out.print(stringBox);
            try {
                int choice = scanner.nextInt();
                getDisplayedCommands().get(choice - 1).execute();
            } catch (InputMismatchException | IndexOutOfBoundsException ignored) {
                showInformation("\n\nPlease enter the number of a menu item");
            } finally {
                scanner.nextLine();     // consume the newline
            }
        }
    }

    @Override
    public void endGame() {
        playGame = false;
    }
}
