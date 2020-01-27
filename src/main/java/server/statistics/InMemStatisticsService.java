package server.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Default in-memory implementation of StatisticsService interface.
 */
public class InMemStatisticsService implements StatisticsService {

    private AtomicInteger requestHandledCounter = new AtomicInteger(0);
    private AtomicLong totalRequestTimes = new AtomicLong();

    @Override
    public void onRequestHandled(RequestHandledEvent event) {
        totalRequestTimes.addAndGet(event.getRequestHandleTimeSpanMs());
        requestHandledCounter.incrementAndGet();
    }

    @Override
    public int getRequestHandledCount() {
        return requestHandledCounter.get();
    }

    @Override
    public double getAverageRequestHandleTimeMs() {
        return requestHandledCounter.get() == 0
                ? 0
                :  (double) (totalRequestTimes.get() / requestHandledCounter.get());
    }
}
