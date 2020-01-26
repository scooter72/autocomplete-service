package server.statistics;

/**
 * Interface used to collect and access server statistics data.
 */
public interface StatisticsService {

    /**
     * Enables request handlers event emitters to report the time
     * it took to handle a specific request.
     * @param requestHandledEvent info of the handled request i.e. the time it took to handle a specific request in milliseconds.
     */
    void onRequestHandled(RequestHandledEvent requestHandledEvent);

    /**
     * Returns the number of recorded handled request
     * @return the number of recorded handled request
     */
    int getRequestHandledCount();

    /**
     * Returns request handle average time in milliseconds based on the data collected int this
     * StatisticsService.
     * @return request handle average time in milliseconds.
     */
    double getAverageRequestHandleTimeMs();
}
