# State Architecture

### Flows & Intents

As part of the goal to make the game more in line with SOLID principles, specifically OCP and SRP, we split individual
actions/phases of the user-facing part of the game into their own states.

#### SRP - This pattern is useful to us as it allows us to separate phases of the game into clear and organisable classes that will have their own logic for whatever job they need to perform.

#### OCP - If extension is needed, we simply create a new state and make minimal changes to other states to point to or refer to this new state.

- Menu (i.e., game configurations, results, non gameplay loop)
    - Split into individual dialogs
    - That is, one class for each selection
    - Example: SelectCharacter and SelectDifficulty are two separate states.
    - It may seem convoluted at first, but this helps the GameView by dividing multiple dialogues, all blocking each
      other and allowing the GameView implementation to properly display info sequentially

If we stuck these together into one file, it may work for a sequential Console view where we wait for the next input on
a `Scanner.nextLine()` and block the program thread,
but for a potential 2D/3D graphical view that will have its own loop, the game engine can't simply freeze and wait for
user input while the the graphics engine is attempting to render everything else.

In most cases the application will be running on a single thread, and so the entire loop, both the engine and graphics
will be blocked. Accounting for this and implementing a multithread and thread-safe solution is its own bag of horrors.

So, to makes things easy, and in fact this is in line with most game dev practices, we just implement this as a giant
state machine.

Combined with the Command pattern that we've already implemented, we can easily refactor the engine to handle state
changes on an event-based basis, allowing non-blocking graphical UIs to simply send messages and data to the game engine
as Commands when input is ready.

References:

- [State - Design Patterns Revisited from Game Programming Patterns](https://gameprogrammingpatterns.com/state.html) /
  Elaboration on State Machine patterns in game development.
- [State - Refactoring Guru](https://refactoring.guru/design-patterns/state) / Explanations and elaborates on
  applicability to SOLID principles.