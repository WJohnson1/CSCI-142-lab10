package viewer;

import backtracker.Configuration;

/**
 * Helper to store Configurations with their computed isValid and isGoal so the viewer doesn't have to compute them
 *
 * @author dwg7486 (David Grzebinski)
 */
public class BacktrackingData {
    private Configuration config;
    private boolean isValid;
    private boolean isGoal;

    /**
     * Create a BacktrackingData object
     * @param config the Configuration
     * @param isValid if the Configuration is valid
     * @param isGoal if the Configuration is the goal
     */
    public BacktrackingData(Configuration config, boolean isValid, boolean isGoal) {
        this.config = config;
        this.isValid = isValid;
        this.isGoal = isGoal;
    }

    /** Get the stored Configuration */
    public Configuration getConfig() {
        return config;
    }

    /** Is the stored Configuration valid */
    public boolean isValid() {
        return isValid;
    }

    /** Is the stored Configuration the goal? */
    public boolean isGoal() {
        return isGoal;
    }
}
