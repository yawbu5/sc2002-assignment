# Using the Observers

For this project we use the Observer/Subject pattern.

This allows decoupling the state logic from the UI, so the engine remains headless, only having to wait for any
potential Command input to come through.

- For this project we simply define two Observer interfaces,
    - BattleObserver
    - MenuObserver
- They allow the implementing UI classes to handle receiving events from the game engine in whatever way they want.
- Example:
    - BattleObserver guarantees `onLogAction` that contains a message or some other form of context, so whether the game
      is text-based or graphical, the UI engine only gets the updated text, and from there it can decide what to do with
      it.

The GameEngine will then be the Subject that holds the list of Observers, and will define a `notifyXXXObservers(event)`
function that the state logic can call depending on the specifc battle or menu context, and execute the Observer
interface's defined event and send messages to whichever UI system is observing the game.

### UI Implementation

A View will implement these observers so that they can start listening to events being fired from the game engine.

`public class someView implements MenuObserver, BattleObserver`

It will then implement the methods from the interfaces and be able to handle the events/messages as it needs to.

References:

- [Observer - Design Patterns Revisited from Game Programming Patterns](https://gameprogrammingpatterns.com/observer.html)
- [Refactoring Guru - Observer](https://refactoring.guru/design-patterns/observer) 