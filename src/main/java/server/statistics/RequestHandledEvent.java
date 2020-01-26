package server.statistics;

public class RequestHandledEvent {
    private long requestHandleTimeSpanMs;

    public RequestHandledEvent(long requestHandleTimeSpanMs) {
        this.requestHandleTimeSpanMs = requestHandleTimeSpanMs;
    }

    public long getRequestHandleTimeSpanMs() {
        return requestHandleTimeSpanMs;
    }
}
