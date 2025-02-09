package com.sap.citydata.scheduler;

public abstract class BaseIntegrationScheduler {

    /**
     * This method encapsulates the common integration logic and error handling.
     * Subclasses should call this method from their own scheduled methods.
     */
    protected void executeIntegration() {
        try {
            fetchDataFromSource();
        } catch (Exception e) {
            handleError(e);
        }
    }

    /**
     * Subclasses implement this method with the specific integration logic.
     */
    protected abstract void fetchDataFromSource();

    /**
     * Common error handling logic.
     */
    protected void handleError(Exception e) {
        // For example, log the error. Here we simply print the stack trace.
        e.printStackTrace();
    }
}
