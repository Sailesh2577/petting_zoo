package edu.unl.cse.csce361.petting_zoo.controller;

import edu.unl.cse.csce361.petting_zoo.controller.owner.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayerMenus {
    // When we later migrate to Java 11, we'll want to use List.of()

    public static final List<Command> mainMenu = Collections.unmodifiableList(Arrays.asList(
            new PlayAsOwnerCommand(),
            new PlayAsAnimalHandlerCommand(),
            new PlayAsVisitorCommand(),
            new ResetCommand(),
            new ExitCommand()
    ));

    public static final List<Command> ownerMenu = Collections.unmodifiableList(Arrays.asList(
            new SetAdmissionPriceCommand(),
            new BuyAnimalCommand(),
            new SellAnimalCommand(),
            new SeeAnimalStatisticsCommand(),
            new UpdateCommand()
    ));

    public static final List<Command> buyMenu = Collections.unmodifiableList(Arrays.asList(
            new BuyBunnyCommand(),
            new BuyGirrafeCommand(),
            new BuyKodiakBearCommand(),
            new BuyLionCommand(),
            new BuyTigerCommand(),
            new BuyZebraCommand()
    ));
}
