# SC2002 Assignment


## Project Architecture 

### Links to pattern usage explanations
- [States](src/main/java/systems/states/states.md)
- [Observers](src/main/java/observable/observer.md)
- [Commands](src/main/java/commands/commands.md)
 
### Data System
- `data/GameResources` - The central serialized data repository for all the dynamic game configurations (i.e., entities, wave data, abilities)
- `data/JSONLoader` - Utility for easily loading in JSON data according to a data template class.
 
### Action System
- `data/ActionTemplate` - Read-only blueprint defining what an action should do
- Action - The instance tracking the cooldowns and other temporal attributes for a certain entity
- ActiveAction - The state containing the active target data during a turn resolve

### State System
- `states/battles` - Holds the battle-related game states
- `states/menu` - Holds the game states used to present other dialogs (selecting difficulty, showing end results etc.)

### Observer system (observable)
- `observable/xxxObserver` - Interfaces that guarantee that a Observer is able to handle notifications/events send from the GameEngine.

### Command system
- `commands/Command` - Interface that guarantees an implementation of command text retrieval, and an command execution method.
