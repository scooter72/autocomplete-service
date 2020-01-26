package server.statistics;

import server.factory.AbstractFactory;

public class StatisticsServiceFactory extends AbstractFactory {
    private static StatisticsService statisticsService;

    public static StatisticsService getStatisticsService() {
        if (statisticsService == null) {
            statisticsService = getClassInstance("server.statistics");
        }
        return statisticsService;
    }
}
