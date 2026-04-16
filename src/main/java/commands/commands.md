# Sending Commands to the Engine
### The engine as far as the architecture is concerned is just one giant black box that spits out messages via Observers and takes in input via Commands.

For taking in input, the engine can send a event (in this case onChoicePrompt) that sends a message and a list of Commands that the User can select, which the engine will take back in via a Queue. For this project we use a ConcurrentLinkedQueue to ensure thread-safe compatibility if we use a graphical view implementation.

Commands are quite versatile in their usage.

The default Command interface implementation is simply a `title` field which outlines what the command is asking for, and a `execute(BattleEngine engine)` method.

Commands are categorised exactly by what function they are applicable for.

For example, `MenuCommand` could specify an additional `Runnable` parameter in a variant of its constructor so that the input may directly interface with the engine

e.g., `new MenuCommand("1. Easy Difficulty", () -> engine.selectDifficulty(easyDifficulty);` where easyDifficulty is of type Wave.
(do note that the Engine should be the only one generating commands that run an engine side utility. If not this will violate the 'blind UI' rule.)

For other purposes it can simply act as an identifier for simpler applications.

e.g., `OpenInventoryCommand` is simply used to filter in incoming commands of this type during the input phase so that the `BattleState` may transition to an Inventory view.

References:
- [Command - Design Patterns Revisited from Game Programming Patterns](https://gameprogrammingpatterns.com/command.html)
- [ConcurrentLinkedQueue in Java](https://www.geeksforgeeks.org/java/concurrentlinkedqueue-in-java-with-examples/) / Implementation on taking Commands in a queue.
