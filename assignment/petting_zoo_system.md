#   Petting Zoo System

-   Assignment Due: April 10, 2020 at 9:30am CDT (UTC-5)
-   Peer Assessment Due: April 10, 2020 at 11:59pm CDT (UTC-5)

In this assignment you will implement a simple petting zoo system. Most of the
functional requirements come from user stories prepared by students.

## Objectives

Students will:

-   Gain additional experience developing software with a partner
    -   Gain experience using branches on a version control system
-   Demonstrate good software engineering practices
-   Learn three design patterns
    -   Builder Pattern
    -   Command Pattern
    -   Observer Pattern
-   Learn the Model-View-Controller architectural style

## Instructions

This assignment is to be completed in assigned pairs; **no collaboration
other than with your assigned partner is permitted**.  One of the purposes of
pair-assignments is to practice teamwork. After completing the assignment you
will need to complete a peer assessment. Your contribution grade will be based
on the peer assessments and on the git history.

*Commit material that you worked on individually under your own name* using the
defaults that you set. *When (and only when) you commit material that was
developed using pair programming, override the default commit author to reflect
both authors* so that we can properly credit both authors for their contribution
grades. When you override the default commit author list both students' names,
and for the email address use a fake email address that is unique to the pair
of students by concatenating your Canvas login IDs (the angle brackets around
the email address are required):
```
git commit --author="Herbie Husker and Lil Red <hhusker20lred19@dev.null>"
```
You can use this same technique for the rare circumstance in which your partner
is briefly unable to commit code themselves:
```
git commit --author="Herbie Husker <herbie@huskers.unl.edu>"
```

##  Setup

1.  You and your partner will work on a shared repository, which has been
    prepared for you.

    1.  Navigate to your shared directory
        (<https://git.unl.edu/csce_361/spring2020/30pairNN/>, where *NN* is your
        team number).

    1.  Verify that the repository is private, and that you and your partner
        both have Maintainer access.

1.  Both students should:

    1.  Clone the project: `git clone <URL>` (here the angle brackets should
        not be included).

        -   **Do *NOT* place your petting zoo system repository inside your
            csce361-homework repository!**

    1.  Import the project into your IDE. The project is set up as a Maven
        project, so you can follow your IDE's instructions to import a Maven
        project.

##  Assignment

Archie's Pleistocene Petting Zoo is a place for family-friendly fun. For a
modest entry fee, visitors can interact with megafauna (and not-so-mega fauna)
from the Pleistocene prehistorical epoch. The owner has hired your team to
automate the operation of the petting zoo.

Visitors can interact with animals in hands-on enclosures where they can touch
the animals, in hand-feeding enclosures, where they can purchase food pellets
to feed the animals;,and at hands-off enclosures, where the can look at the
animals but cannot come in direct contact with them. Behind-the-scenes, there
are also stalls where animals eat and rest when not in one of the public
enclosures~~, the veterinary clinic where sick and injured animals can heal, and
the mating pen whose purpose is self-explanatory~~.

### Architecture

#### Model-View-Controller

The system shall have a model-view-controller (MVC) architecture. As with the
3-tier architecture, there is a separation of concerns; however, the separation
is a little different, as is the communication pattern between subsystems. The
model is the underlying representation; most or nearly all of the classes
identified during object-oriented analysis are found in the model subsystem.
The view subsystem comprises one or more *views*, which are representations of
part of the model; usually thought of as a screen display, a view can be any
representation of part of the model, such as a pdf file. The controller accepts
input (usually from the user) and uses that input to direct changes to the
model and possibly the view.

-   <https://learning.oreilly.com/library/view/pattern-oriented-software-architecture/9781119963998/25_chap13.html>
-   <https://learning.oreilly.com/library/view/pattern-oriented-software-architecture/9781119963998/chap13-sec001.html>
-   <https://learning.oreilly.com/library/view/pattern-oriented-software-architecture/9781119963998/chap13-sec006.html>
-   <https://learning.oreilly.com/library/view/pattern-oriented-software-architecture/9781119963998/chap13-sec010.html>

#### Repository

This system can run with multiple players, in which case the MVC architecture
is the architecture for the local system. The overall system, then, also has a
*repository* architecture (see Kung p156), in which the subsystems or component
systems interact only through a common datastore. (There is also a related
architecture, *blackboard*, in which the datastore actively notifies subsystems
of updates.)

### Design Patterns

-   Use the *Builder Pattern* to create animals that have some fields with
    default values and other fields with non-default values.
    -   Figure 16.22 on Kung p409
    -   HFDP, [a brief discussion in the "Leftover Patterns" appendix](https://learning.oreilly.com/library/view/head-first-design/0596007124/apa.html#builder)
    -   See discussion below on using the Builder pattern to implement default
        constructor arguments.
-   Use the *Command Pattern* to launch the appropriate behavior for each menu
    option.
    -   Figure 17.7 on Kung p432
    -   HFDP, [Chapter 6](https://learning.oreilly.com/library/view/head-first-design/0596007124/ch06.html)
    -   Remember: the command classes typically have no logic; their
        responsibility is calling another object's method appropriate to
        initiate the desired behavior.
-   Use the *Observer Pattern* to notify views of changes to the model.
    -   Figure 16.27 on p413
    -   HFDP, [Chapter 2](https://learning.oreilly.com/library/view/head-first-design/0596007124/ch02.html)
    -   See discussion below on Java's implementation.

#### Default Constructor Arguments

The Builder Pattern, as discussed in both books, is a great way to create some
complex objects. It's also useful to overcome Java's lack of default
constructor arguments. Suppose you have a `Foo` class with two fields,
`int one` and `double two`. Suppose also that we want `one` to have a default
value of `1`, and `two` to have a default value of `2.0`. In a language that
has default arguments, we could implement this with a single constructor, such
as:
```
def __init__(self, one=1, two=2.0):
    self.one = one
    self.two = two
```
Java doesn't have default arguments, so we might try this:
```
public Foo() { this(1, 2.0); } // calls another constructor using default values
public Foo(int one) { this(one, 2.0); }
public Foo(double two) { this(1, two); }
public Foo(int one, double two) { this.one = one; this.two = two; }
```
Now suppose that instead of two fields, there are three. Or four. The number of
combinations grows exponentially. *If* you can make it work. In the example, `one` and `two` are different types. Suppose that `two` is an `int` instead of a `double`. The compiler won't let you create two constructors with the exact same signature in the same class:
```
public Foo(int one) { this(one, 2); }   // even if the compiler allowed this,
public Foo(int two) { this(1, two); }   // which is called for "Foo(5)"?
```
The solution is to create a builder class. As with both books, I recommend that
Foo be an interface (or an abstract class with mostly abstract methods). The
builder class is then free to select the best Foo implementation based on the
situation.
```
public class FooBuilder {
    private int one;
    private int two;

    public FooBuilder() {
        this.one = 1;
        this.two = 2;
    }

    public FooBuilder setOne(int one) {
        this.one = one;
        return this;
    }

    public FooBuilder setTwo(int two) {
        this.two = two;
        return this;
    }

    public Foo build() {
        return new ConcreteFoo(this.one, this.two);
    }

    /** The Foo implementation doesn't have to be an inner class,
        but it could be */
    private class ConcreteFoo {
        ConcreteFoo(int one, int two) {...}
        ...
    }
}
```
Now the `Foo` class needs only a single constructor, one that takes *all* of
the arguments. By calling a `setXX()` method, we override the default value; by
not calling it, we accept the default value, such as:
```
Foo foo1 = new FooBuilder().build();            // uses default values
Foo foo2 = new FooBuilder().setOne(5).build();  // overrides default "one"
Foo foo3 = new FooBuilder().setTwo(9).build();  // overrides default "two"
Foo foo4 = new FooBuilder().setOne(7).setTwo(12).build(); // overrides both
```

Our toy example here doesn't really illuminate the full value of separating the
interface from the implementation, but you should do so if only to make sure
that the client code cannot depend on a particular `Foo` implementation
(remember the Dependency Inversion Principle: depend on abstractions, not
concretions), which will make your code more maintainable. Doing so will also
get you in the habit for the that you do find it useful to be able to have your
code select from many possible implementations based on the situation.

#### Java's Implementation of the Observer Pattern

You may implement the Observer Pattern from scratch; however, a good practice
would be to use an existing implementation when one exists.

-   `java.util.Observer` and `java.util.Observable` have been part of the Java
    standard library from the beginning and would be a good choice
-   Java's `Observer` interface and `Observable` class have been deprecated
    since Java 9 due to their limited capabilities; however, this same
    simplicity makes them well-suited for your first experience with the
    Observer Pattern. Even though we are we are specifying through Maven that
    our code is targeting Java 8, your IDE may need you to use the
    `@SuppressWarnings("deprecation")` annotation.
-   If you use Java's implementations, be sure to read the JavaDoc, especially
    the roles that `setChanged()` and `hasChanged()` have in the behavior of
    `notifyObservers()`.
    -   <https://docs.oracle.com/javase/8/docs/api/java/util/Observer.html>
    -   <https://docs.oracle.com/javase/8/docs/api/java/util/Observable.html>

### Starter Code

-   There are two user interfaces available.

    -   The graphical user interface can be invoked by invoking the
        `javafx:run` Maven goal using the
        [same technique](https://piazza.com/class/k52rkmpexb65dp?cid=98) you
        used in the food truck assignment, *or* it can be invoked on the
        command line with
        ```
        mvn javafx:run
        ```

    -   The command line interface is designed to work in a standard 24x80
        terminal window and so is suitable for your computer or for running on
        the cse.unl.edu Unix server. It can be invoked by running `CLI.main()`
        from within your IDE, *or* in can be invoked on the command line with
        ```
        mvn exec:java -Dexec.mainClass=edu.unl.cse.csce361.petting_zoo.CLI -Dexec.cleanupDaemonThreads=false
        ```

    -   If you're going to use the command line interface, edit line 14 of
        `src/main/java/edu/unl/cse/csce361/petting_zoo/view/UserInterfaceManager.java`

-   `src/main/resources`

    -   ***You and your partner can share a database in this assignment.***
        Decide whose MySQL account you will use and provide the MySQL password
        with the other partner.

        -   Do *not* share your cse.unl.edu password!

        -   After the assignment is over, change your MySQL password

    -   Copy `hibernate.cfg.xml-TEMPLATE` to `hibernate.cfg.xml` and replace
        USERNAME and MYSQL_PASSWORD with the username and MySQL password that
        you decided to use for the assignment, the same as you did in the
        database setup assignment.

-   `src/main/java/edu/unl/cse/csce361/petting_zoo/view`

    -   Contains the user interfaces. They both implement the
        `UserInterface.java` interface.

        -   This interface provides methods to add and remove commands.

        -   This interface also provides a method to display information (to
            the user). Both user interfaces will display a string 23 rows tall
            by 40 characters wide.

            -   You may optionally use `StringBox.java` to generate   
                that string. See
                <https://git.unl.edu/bohn/string_box/-/blob/master/README.md>
                for usage.

        -   If you're going to use the command line interface, edit line 14 of
            `UserInterfaceManager.java`

-   `src/main/java/edu/unl/cse/csce361/petting_zoo/controller`

    -   `Command.java` is the interface for the Command Pattern.

        -   Each implementation should implement `execute()` to call the method
            that performs the behavior corresponding to the command. For
            example, `ResetCommand.execute()` calls
            `PettingZoo.resetPettingZoo()`.

        -   Each implementation should override `toString()` to provide the
            string that should be displayed in the menu.

    -   `PlayAsOwner.java` is an example of a Command that creates a new menu.

-   `src/main/java/edu/unl/cse/csce361/petting_zoo/model`

    -   Contains three classes whose objects are persisted in the database

        -   `PettingZoo` has the "big picture" for the petting zoo.

            -   It has fields (and accessors) of interest to the zoo owner,
                such as the bank balance and the level of public interest.

            -   It also has methods that are of interest to you, the developer.

                -   There are static methods: the factory method  
                    `getPetingZoo()` and `resetPettingZoo()` that takes your
                    database back to having just one animal.

                -   `updateZooStatus()` (along with some helpful fields) is
                    there to update the zoo's bank account (and a few other
                    fields) and animal locations based on time elapsing and
                    based on actions taken by other players.

            -   As with any of the starter code, you can expect to have to add
                or modify code. Be especially on the lookout for comments of
                the form `/* ******** DO SOMETHING **********/`

            -   I've left the setters/getters exposed. Use them wisely, if at
                all.

        -   `LocationEntity` defines places that animals can be. There is also
            a `Location` interface. The *only* code that should "know" that a
            `Location` is really a `LocationEntity` is code that hits the
            database. Everywhere else, the code should be written to the
            `Location` interface.

        -   `AnimalEntity` defines animals. Rather than defining classes for
            each species, we are using a single class, with the species'
            details stored in an archetype file (see discussion below). There
            is also an `Animal` interface. The *only* code that should "know"
            that an `Animal` is really an `AnimalEntity` is code that hits the
            database and code that creates `Animal` objects. Everywhere else,
            the code should be written to the `Animal` interface.

    -   `AnimalBuilder` is has the minimum components for a Builder class: the
        constructor and the `build()` method. You will complete it.

        -   The constructor that I expect you'll use is
            `AnimalBuilder(String species)`. Here, `species` is the colloquial
            species name (you can go with the scientific name if you want) with
            the spaces replaced with underscores; that is, it needs to be in
            snake_case. For example, a mammoth would simply have `mammoth`,
            whereas a saber-toothed tiger would have `saber-toothed_tiger`.

        -   When you write the additional methods, one option is to completely
            replace the default values, such as `setMass(int actualMass)`.
            Another option is to use a multiplying factor (which could be
            greater or less than 1), such as `adjustMass(double multiplier)`
            (since massInKg is an integer, you'll want to round the final
            answer).

        -   When there are a limited set of options, you may want to use
            0-argument methods, such as `setFemale()` & `setMale()` instead of
            `setSex(Sex sex)`.

    -   `src/main/resources/archetypes` is where you will put the archetype
        files. These files need to have the name `species.properties`, where
        `species` is replaced with the snake_case name of the species. See, for
        example, `brea_owl.properties` that is already in the directory.

        -   An archetype file needs to have key/value pairs for the "typical"
            values of species properties, such as `massInKg` and `type`. It
            also has the sale price of the species. You may, of course add more
            properties.

        -   Notice that by using configuration files, when the system is
            finished, we can extend our system with additional species without
            having to modify any code: it follows the *Open-Closed Principle*.

#### Notes on Hibernate

The general pattern for most accesses to the database take the form
```
session.beginTransaction();
... // possibly changes
... // CRUD
session.getTransaction().commit();
```

-   Create: `session.save(persistentObject)`
-   Retrieve: `persistentObject = session.load(PersistentClass.class, primaryKey)`
-   Update: `session.save(persistentObject)`
-   Delete: `session.remove(persistentObject)`

If you don't know the primary key, retrievals can also be accomplished using
Hibernate Query Language. In this assignment, a reference to each animal and
location is maintained in the `PettingZoo` object, so you won't need to create
any retrievals.

Any new fields that you need to add to `AnimalEntity`, `LocationEntity`, or
`PettingZoo` need to be annotated with `@Column` if you want them to be
persisted.

### Formulas

There are a couple of formulas your system will need to calculate: the public
interest and the rate of admission. Public interest is a measure of how much
people want to attend the petting zoo, how excited they are. The rate of
admission is how many are visiting (and paying admission) per hour.

You may use any formula you wish, but it must be a calculated formula. For
example, public interest might be:
```
total mass in zoo * 0.1 + mass in hands-on * 10 + mass in feeding * 10 + mass in hands-off * 5
```
and the admission rate might be:
```
20 * public interest / sqrt(admission price)
```

I just made up those formulas, and they may not make for an interesting game.
You can use them or make up your own formulas (perhaps ones that *would* make
for an interesting game).

## Issue Tracker

-   Create at least one Git Issue for each user story. You will probably create
    more than one Issue for each story.

-   Create Issues for other tasks as you deem appropriate.

-   Use the Issue assignment to coordinate your & your partner's work.

-   When committing code, include a reference to the Issue(s) the code is for
    using the hashtag-number notation.

-   Close an Issue for subtasks as appropriate.  Close an Issue for a whole
    user story only after the code passes the story's acceptance criteria.

-   You may, of course, create additional Issues when you discover their need.

### Branches

In addition to the master branch, you must have at least two development
branches and an integration/staging branch. Even though the usage should make
it clear which branch is the integration/staging branch, please give it a name
that makes it clear which branch is the integration/staging branch. Give the
development branches names appropriate to their specific purpose.

-   You may choose whether to have one development branch per student, per
    feature, or per issue; or you may device another scheme to organize your
    development branches.
-   Make original commits *only* to a development branch.
-   When you are ready to integrate your changes:
    -   Merge the integration/staging brach into your development branch
        -   `git merge staging` (assuming `staging` is the name of your
            integration/staging branch).
    -   Resolve any merge conflicts.
    -   Make sure your code still compiles, runs, and passes all of its tests.
    -   Double-check that while you resolved merge conflicts and verified that  
        your code remains unbroken, your partner didn't add anything to the
        integration/staging branch.
        -   `git diff staging` should show differences only in one direction.

        -   If there are differences in both directions, merge the integration/
            staging branch into your development branch again.
    -   Once your development branch is strictly ahead of the integration/
        staging branch, merge your development branch into the integration/
        staging branch.
        -   ```
            git checkout staging
            git merge your_development_branch
            ```
        -   There shouldn't be any merge conflicts (they should have all been
            resolved on your development branch), but resolve them if there are.
        -   Make sure your code still compiles, runs, and passes all of its
            tests. Because you already did this on your development branch,
            there shouldn't be any problems -- but better safe than sorry.
        -   Return to your development branch to continue your work.
            -   `git checkout your_development_branch`
-   When and only when both you and your partner agree it's safe to copy the
    integration/staging branch to the master branch, do so:
    -   ```
        git checkout master
        git merge staging
        ```

When we grade your use of branches, we can use metadata from the commits: each
commit knows what branch it was created in and which branches it's currently a
part of, and also which commit(s) is its parent(s) (a regular commit has a
single predecessor; a merge commit has two, from which we can determine which
branches were merged). Nonetheless, please do *not* delete any branches after
merging them -- if we can visually determine from the commit graph that you
used branches as required then we won't need to examine the metadata.

### Functional Requirements

-   The unit of currency is the Zorkmid, denoted with the Unicode currency sign
    `¤`.

-   There are two types of animals: carnivores and herbivores.

-   There are four types of locations in the petting zoo:

    -   Stalls, which are closed to the public. For simplicity, we can treat
        "the stalls" as a single location.

    -   Public enclosures: Hands-on enclosures, hand-feeding enclosures, and    
        hands-off enclosures. If there were capacity limits and/or a need to
        segregate carnivores and herbivores then we would have multiple
        instances of each type of enclosure; since these requirements have been
        removed we can assume one of each type of enclosure.

-   An animal is always in exactly one location.

    -   When an animal is added to the zoo, its initial location is the stalls.

-   The user can choose to play as the Petting Zoo Owner, as an Animal Handler,
    or as a Petting Zoo Visitor. Based on their selection, they will be shown
    the appropriate menu.

-   Any menu (other than the top-level menu) must have an option to go back to
    the previous menu.

-   Any command that queries or changes the model must first refresh the model
    from the database.

-   The Petting Zoo Owner, Animal Handler, and Petting Zoo Visitor menus must
    have command that only refreshes the model from the database.

-   If the current view changes because of changes to the model, the updated
    view must be displayed.

-   Hunger:

    -   An animal in a hand-feeding enclosure neither gains nor loses hunger.
        Otherwise:

    -   An animal's hunger grows at the rate of 1 unit per hour.

    -   An animal's hunger decreases when fed. In the interest of simplicity,
        we shall assume just-in-time food delivery.

        -   Forage for an herbivore costs ¤0.002/kg to remove 1 unit of hunger.

        -   Meaty-mix for a carnivore costs ¤0.005/kg to remove 1 unit of
            hunger.

    -   Hunger cannot be negative.

-   Stress:

    -   An animal in a hands-on enclosure or a hand-feeding enclosure gains
        stress at the rate of 1 unit per hour.

    -   An animal in a stall loses stress at the rate of 1 unit per hour.

    -   An animal in a hands-off enclosure neither gains nor loses stress.

    -   Stress cannot be negative.

-   Animal handlers wages are ¤1/hr per animal.

#### User Stories

1.  As the Petting Zoo Owner, I want to see statistics about my petting zoo so
    that I can make smart business decisions to maximize profit.
    -   I can see the number of tickets sold per day for the last week
    -   I can see the revenue and expenses per day for the last week, broken
        down by type
    -   I can see the number of each type of animal

1.  As the Petting Zoo Owner, I want to be able to buy an animal so that I can
    increase public interest in my petting zoo.
    -   A new animal with the specified parameters is now in a stall in my zoo
    -   The cost of the animal has been deducted from the zoo's bank balance

1.  As the Petting Zoo Owner, I want to be able to sell an animal so that I can
    raise funds.
    -   The animal is no longer in any location in my zoo
    -   90% of the purchase price of the animal has been added to the zoo's
        bank balance.

1.  As an Animal Handler, I want to see statistics about the health of the
    animals so that I can make smart decisions when caring for the animals.
    -   I can how many animals of each type are in each location
    -   I can see each animal's location
    -   I can see each animal's hunger level
    -   I can see each animal's stress level

1.  As an Animal Handler, I want to move animals from one location to another
    so that visitors can interact with animals and so that animals can have
    quiet time away from visitors.
    -   If there is room in the new location:
        -   The animal is no longer in its previous location
        -   The animal is in its new location
    -   If there is insufficient room in the new location: the animal remains
        in its old location

1.  As an Animal Handler, I want to feed animals so that they thrive in the
    petting zoo.
    -   The animal is less hungry
    -   The cost of the forage (for herbivores) or meaty-mix (for carnivores)
        is deducted from the zoo's bank balance

1.  As a Petting Zoo Visitor, I want to be able to move from one enclosure to
    another so that I can have different experiences.
    -   I am shown a list of animals in the enclosure

1.  As a Petting Zoo Visitor, I want to be able to interact with an animal so
    that I can enjoy my time with the animals.
    -   In a hands-on enclosure, the animal will respond to me petting it
    -   In a hand-feeding enclosure, the animal will respond to me feeding it
    -   In a hands-off enclosure, the animal will do whatever it does naturally

-   Thanks
    -   Most of these user stories are amalgams of stories produced by CSCE 361
        students.

### Tests

Use your best judgement for unit tests. We recommend that you have some unit
tests to reduce the likelihood of an undiscovered bug costing you points, and
we recommend that you use unit tests to help with debugging, but we are not
requiring any particular number of tests.

## Deliverables

For grading, we will clone your copy of the project after it is due, and we
will look for:

-   Source code for your petting zoo system
-   Unit tests for your source code

*It is your responsibility to ensure that your work is in the **correct
repository** and that we can access the repository at the **time the assignment
is due**.  We will grade what we can retrieve from the repository at the time
it is due.  Any work that is not in the correct repository, or that we cannot
access, will not be graded.*

## Assignment Rubric

The assignment is worth **39 points**:

-   **3 points** for implementing a MVC architecture
    -   Controller subsystem accepts input from the user and directs changes to
        the Model and/or the View
    -   Model subsystem alerts View that changes occurred
    -   View subsystem presents information to the user

-   **4 points** for implementing the Builder Pattern
    -   Use the Builder Pattern to create animals, which may have specified or
        default values for their fields

-   **4 points** for implementing the Command Pattern
    -   The user interface retrieves the appropriate command object and calls
        its `execute()` method.
    -   The `execute()` method has no behavior of its own but simply cal.ls a
        method on some other object to initiate the desired behavior

-   **4 points** for implementing (or using) the Observer Pattern
    -   Objects interested in changes to the blackboard datastore can register
        themselves with the datastore to be notified of updates to the
        datastore; each observer then decides whether it needs to take action
        due to the update.

-   **3 points** for using good design principles

-   **3 points** for good coding style

<!-- -   **5 points** for implementing the user stories -->
-   **4 points** for implementing the user stories

-   **2 point** for implementing the other specified functionality

-   **2 points** for making regular commits; *i.e.*, not waiting until the end
    of the project to make a massive commit.

-   **2 points** for using Git Issues as directed.

-   **4 points** for using branches as directed.
    -   automatic 2 point deduction if any commits are made directly to the
        master branch

-   **4 point** for meaningful and well-formatted commit messages


This assignment is scoped for a team of 2 students. If, despite your attempts
to engage your partner, your partner does not contribute to the assignment then
we will take that into account when grading.

*If **at any time** your repository is public or has internal visibility then
you will receive a 10% penalty. Further, if another student accesses your
non-private repository and copies your solution then I will assume that you are
complicit in their academic dishonesty.*

## Contribution Rubric

The contribution is worth **10 points**:

-   **1 point** for completing peer assessment
-   **5 points** for equitable contribution based on peer assessments
-   **4 points** for equitable contribution based on git history
