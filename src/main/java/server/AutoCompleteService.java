package server;

import server.data.Dictionary;
import server.model.ServerStatistics;
import server.statistics.RequestHandledEvent;
import server.statistics.StatisticsService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A service that receives a prefix and returns all valid English words that start with the prefix.
 * The service also records arbitrary server statistics.
 */
@Path("/service")
public class AutoCompleteService {
    private final String[] dictionary;
    private final StatisticsService statistics;

    /**
     * Constructs an instance of AutoCompleteService using the
     * <param>dictionary</param> and <param>statistics</param> arguments.
     * @param dictionary The dictionary used by this AutoCompleteService
     * @param statistics statistics collected by this AutoCompleteService.
     */
    public AutoCompleteService(Dictionary dictionary, StatisticsService statistics) {
        if (dictionary == null) {
            throw new IllegalArgumentException("dictionary");
        }

        if (statistics == null) {
            throw new IllegalArgumentException("statistics");
        }

        this.dictionary = dictionary.getWords();
        this.statistics = statistics;
    }

    /**
     * Returns all the words in the dictionary which starts with the <param>prefix</param> argument.
     * @param prefix The query argument.
     * @return array with all the words matching the <param>prefix</param> argument.
     */
    @GET
    @Path("/dictionary")
    @Produces(MediaType.APPLICATION_JSON)
    public String[] getWordsStartingPrefix(@QueryParam("prefix") String prefix) {
        String[] words;
        long start = System.currentTimeMillis();

        try {
            words = prefix == null || prefix.length() == 0
                    ? new String[0]
                    : getWordsStartingWithPrefix(dictionary, prefix);
            statistics.onRequestHandled(new RequestHandledEvent(System.currentTimeMillis()-start));
        } catch (Throwable t) {
            System.out.println(
                    "error retrieving matching words for prefix: '"+prefix+"', error: " + t);
            throw t;
        }
        return words;
    }

    /**
     * This method attempts get the index of the prefix using binary search, and than
     * returns all the words starting with the <param>prefix</param> argument from the index of the
     * the <param>prefix</param> argument in the the <param>words</param> array argument.
     * @param words sorted array of words of words to search in.
     * @param prefix prefix argument to search for.
     * @return array with all the words matching the <param>prefix</param> argument.
     */
    private static String[] getWordsStartingWithPrefix(String[] words, String prefix) {
        int index = Arrays.binarySearch(words, prefix);
        List<String> matchingWords = new ArrayList<>();

        // Arrays.binarySearch returns the index of the search key, if it is contained in the array;
        // otherwise, (-(insertion point) - 1)
        boolean prefixNotInArray = index < 0;
        if (prefixNotInArray) {
            index = -index - 1;
        } else {
            // forward index to the next word after the prefix
            index++;
        }

        if (index < words.length) {
            String tmp;
            while((index < words.length &&
                    (tmp = words[index++]).startsWith(prefix))) {
                matchingWords.add(tmp);
            }
        }
        return matchingWords.toArray(new String[0]);
    }

    /**
     * Returns server statistics object containing
     * arbitrary statistics data of this service instance an runtime.
     * @see server.statistics.StatisticsService
     * @see server.model.ServerStatistics
     * @return server statistics object.
     */
    @GET
    @Path("/statistics")
    @Produces(MediaType.APPLICATION_JSON)
    public ServerStatistics getServerStatistics() {
        return new ServerStatistics(
                statistics.getRequestHandledCount(),
                dictionary.length,
                statistics.getAverageRequestHandleTimeMs());
    }
}