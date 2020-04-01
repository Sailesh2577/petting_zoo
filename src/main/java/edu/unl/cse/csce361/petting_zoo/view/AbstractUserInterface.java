package edu.unl.cse.csce361.petting_zoo.view;

import edu.unl.cse.csce361.petting_zoo.controller.Command;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

abstract public class AbstractUserInterface implements UserInterface {
    private List<Command> displayedCommands;

    protected AbstractUserInterface() {
        displayedCommands = new LinkedList<>();
    }

    /**
     * Unlike {@link #getMenu}, this method returns the backing list of commands (not a copy) so subclasses can add
     * and remove commands.
     *
     * @return <i>the</i> list of the currently-displayed commands
     */
    protected List<Command> getDisplayedCommands() {
        return displayedCommands;
    }

    protected void fixUI() {
        UserInterfaceManager.setUI(this);
    }

    @Override
    public List<Command> getMenu() {
        return Collections.unmodifiableList(getDisplayedCommands());
    }

    @Override
    public int addCommand(Command command) {
        displayedCommands.add(command);
        return displayedCommands.size();
    }

    @Override
    public int removeCommand(Command command) {
        displayedCommands.remove(command);
        return displayedCommands.size();
    }

    @Override
    public int addCommands(List<Command> commands) {
        displayedCommands.addAll(commands);
        return commands.size();
    }

    @Override
    public int removeCommands(Collection<Command> commands) {
        displayedCommands.removeAll(commands);
        return commands.size();
    }

    @Override
    public int replaceCommands(List<Command> commands) {
        displayedCommands = new LinkedList<>(commands);
        return displayedCommands.size();
    }
}
