# NTU - SC2002 Assignment

## Building
This project depends on Maven as its build tool.

External Libraries:
- Gson: JSON deserializing
- JUnit: Testing framework

Compiled JAR files are provided in the releases to run directly using `java -jar`, however if you wish to compile from source it is recommended to use an IDE to make the process faster.

Otherwise, if you *really* insist on using a command line from source:

1. execute `mvn package`
2. then `mvn exec:java -Dexec.mainClass="Main"` to run.

## Project Architecture 

### Links to pattern usage explanations
- [States](src/main/java/systems/states/states.md)
- [Observers](src/main/java/observable/observer.md)
- [Commands](src/main/java/commands/commands.md)
 
### Data System / Model
- `data/GameResources` - The central serialized data repository for all the dynamic game configurations (i.e., entities, wave data, abilities)
- `data/JSONLoader` - Utility for easily loading in JSON data according to a data template class.
- `data/ActionTemplate` - Read-only blueprint defining what an action should do
- `data/ActionEffectTemplate` - Read-only blueprint defining the types of status effects an action can perform.
- `data/EffectTemplate` - Describes status effects
- `actions/ActionManager` - Class that keeps tracks of action cooldowns and is overall in charge of the combat calculations 
- `actions/StatusManager` - Class that keeps track of all status effects and handles the relative calculations

### State System / Controller
- `states/battles` - Holds the battle-related game states
- `states/menu` - Holds the game states used to present other dialogs (selecting difficulty, showing end results etc.)
- `GameState`/`BattleState` - Interfaces that differentiate from necessary menu context vs necessary battle context.
- `BattleData` - Data/Storage class that holds the per-battle context. (i.e., round count, inventory snapshot, etc.)

### Controller + View interfaces
- `commands/xxxCommand` - Interface that guarantees methods to describe input to the GameEngine from some external source.
- `observable/xxxObserver` - Interfaces that guarantee methods to describe output from the GameEngine to some external source.
- `view/console/...` - Class that implements MenuObserver & BattleObserver to listen for messages from the game engine.


#### References - A lot of these references can be found in the explanations as well.
- [Enjoyable Game Architecture - Chickensoft](https://chickensoft.games/blog/game-architecture) / Game architecture reference
- [State - Refactoring Guru](https://refactoring.guru/design-patterns/state) / Explanations and elaborates on
  applicability to SOLID principles.
- [What is a statechart? - statecharts.dev](https://statecharts.dev/what-is-a-statechart.html) Hierarchical State Machine/Statechart explanations
- [Observer - Design Patterns Revisited from Game Programming Patterns](https://gameprogrammingpatterns.com/observer.html)
- [Command - Design Patterns Revisited from Game Programming Patterns](https://gameprogrammingpatterns.com/command.html)
- [State - Design Patterns Revisited from Game Programming Patterns](https://gameprogrammingpatterns.com/state.html) /
  Elaboration on State Machine patterns in game development.
