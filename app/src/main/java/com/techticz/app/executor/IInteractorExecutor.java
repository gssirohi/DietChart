package com.techticz.app.executor;

/**
 * Interface for create executors, the executors will only execute Interactors
 */
public interface IInteractorExecutor {

    void performAction(IInteractor IInteractor);

    boolean isRunning();

    void cancelCurrentRequest();
}
