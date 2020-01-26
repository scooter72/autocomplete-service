package server.model;

/**
 * Data transfer object of server statistics.
 */
public class ServerStatistics {
    public ServerStatistics(int requestHandledCount, int wordCount, double averageRequestHandleTimeMs) {
        this.requestHandledCount = requestHandledCount;
        this.wordCount = wordCount;
        this.averageRequestHandleTimeMs = averageRequestHandleTimeMs;
    }

    private int requestHandledCount;
    private int wordCount;
    private double averageRequestHandleTimeMs;

    public int getRequestHandledCount() {
        return requestHandledCount;
    }

    public int getWordCount() {
        return wordCount;
    }

    public double getAverageRequestHandleTimeMs() {
        return averageRequestHandleTimeMs;
    }
}
