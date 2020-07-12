package com.yude.brainstormer.dao;

public class DaoFactory {

    private static DataDao dataDao = new DataDao();

    public static DataDao getDataDao() {
        return dataDao;
    }

}
