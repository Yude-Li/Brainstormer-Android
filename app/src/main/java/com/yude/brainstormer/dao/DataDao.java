package com.yude.brainstormer.dao;

import com.yude.brainstormer.model.Brain;

public class DataDao {

    private Brain currentBrain;

    public DataDao() {
        currentBrain = null;
    }

    public Brain getCurrentBrain() {
        return currentBrain;
    }

    public void setCurrentBrain(Brain brain) {
        this.currentBrain = brain;
    }

    public void clearCurrentBrain() {
        currentBrain = null;
    }

}
