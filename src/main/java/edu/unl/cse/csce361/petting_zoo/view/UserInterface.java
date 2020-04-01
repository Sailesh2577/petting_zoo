package edu.unl.cse.csce361.petting_zoo.view;

import edu.unl.cse.csce361.petting_zoo.controller.Command;

import java.util.Collection;
import java.util.List;

public interface UserInterface {
    /**
     * Returns a list of the current menu. The commands in the list are in the order in which they are displayed.
     * @return a list of the currently-displayed commands
     */
    List<Command> getMenu();

    /**
     * Appends a new command to the list of actions a user can take
     *
     * @param command the {@link Command} to be added
     * @return the number of commands shown to the user after this method executes
     */
    int addCommand(Command command);

    /**
     * Removes the specified command from the list of actions a user can take. If the command is not currently shown
     * to the user, takes no action.
     *
     * @param command the {@link Command} to be removed
     * @return the number of commands shown to the user after this method executes
     */
    int removeCommand(Command command);

    /**
     * Appends new commands to the list of actions a user can take
     *
     * @param commands the {@link Command}s to be added
     * @return the number of commands shown to the user after this method executes
     */
    int addCommands(List<Command> commands);

    /**
     * Removes the specified commands from the list of actions a user can take. Only takes action for the commands
     * that are both in the collection passed to this method and also are currently shown to the user.
     *
     * @param commands the {@link Command} to be removed
     * @return the number of commands shown to the user after this method executes
     */
    int removeCommands(Collection<Command> commands);

    /**
     * Removes all commands currently shown to the user and replaces them with the commands passed to this method.
     *
     * @param commands the {@link Command}s to be displayed
     * @return the number of commands shown to the user after this method executes
     */
    int replaceCommands(List<Command> commands);

    /**
     * Presents information to the user.
     *
     * @param information the information to be displayed
     */
    void showInformation(String information);

    /**
     * Begins the game
     */
    void startGame();

    /**
     * Ends the game
     */
    void endGame();
}
