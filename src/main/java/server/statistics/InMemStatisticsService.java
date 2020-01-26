package server.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Default in-memory implementation of StatisticsService interface.
 */
public class InMemStatisticsService implements StatisticsService {

    private List<Long> requestHandledTimeSpanRecords = new ArrayList<>();
    private AtomicInteger requestHandledCounter = new AtomicInteger();

    @Override
    public void onRequestHandled(RequestHandledEvent event) {
        requestHandledTimeSpanRecords.add(event.getRequestHandleTimeSpanMs());
        requestHandledCounter.incrementAndGet();
    }

    @Override
    public int getRequestHandledCount() {
        return requestHandledCounter.get();
    }

    @Override
    public double getAverageRequestHandleTimeMs() {
        return requestHandledTimeSpanRecords.isEmpty()
                ? 0
                : requestHandledTimeSpanRecords
                .stream()
                .mapToLong(Long::longValue)
                .average()
                .getAsDouble();
    }
}
