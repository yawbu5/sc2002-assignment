package observable;

import commands.Command;

import java.util.List;

/**
 * Menu related events
 * e.g., User prompts and choices, selecting actions etc.
 */
public interface MenuObserver {
    void onChoicePrompt(String msg, List<Command> options);
    void onDisplayMessage(String msg);
}
