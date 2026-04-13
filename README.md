# SC2002 Assignment

### Links to explanations
- [States](src/main/java/systems/states/states.md)

## Project Architecture 
### Data System
- data/GameResources - The central serialized data repository for all the dynamic game configurations (i.e., entities, wave data, abilities)
- data/JSONLoader - Utility for easily loading in JSON data according to a data template class.
### Action System
- data/ActionTemplate - Read-only blueprint defining what an action should do
- Action - The instance tracking the cooldowns and other temporal attributes for a certain entity
- ActiveAction - The state containing the active target data during a turn resolve