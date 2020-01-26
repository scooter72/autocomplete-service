package server;

import org.junit.jupiter.api.Test;
import server.data.Dictionary;
import server.data.FileDictionary;
import server.model.ServerStatistics;
import server.statistics.InMemStatisticsService;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class AutoCompleteServiceTest {
    @Test
    public void testgGetWordsStartingPrefix() {

        AutoCompleteService service = getAutoCompleteService();

        String[] expectedResult = new String[]{"AAH","AAHED","AAHING","AAHS","AAL","AALII","AALIIS","AALS","AARDVARK",
                    "AARDVARKS","AARDWOLF","AARDWOLVES","AARGH","AARRGH","AARRGHH","AARTI","AARTIS","AAS","AASVOGEL",
                    "AASVOGELS"};
            String[] array = service.getWordsStartingPrefix("AA");
            assertTrue(Arrays.equals(expectedResult, array),
                    "Unexpected result");
    }
    @Test
    public void testService_UnexpectedNullDictionary() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AutoCompleteService service = new AutoCompleteService(
                    null,
                    new InMemStatisticsService());
        });

        String expectedMessage = "dictionary";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testService_UnexpectedNullStatisticsService() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AutoCompleteService service = new AutoCompleteService(
                    new FileDictionary("dictionary.txt"),
                    null);
        });

        String expectedMessage = "statistics";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testService_NullInput() {
        AutoCompleteService service = getAutoCompleteService();
        String[] array = service.getWordsStartingPrefix(null);
        assertArrayEquals(new String[0], array, "Unexpected result");
    }

    @Test
    public void testService_NumberInput() {
        AutoCompleteService service = getAutoCompleteService();
        String[] array = service.getWordsStartingPrefix("42");
        assertArrayEquals(new String[0], array, "Unexpected result");
    }

    @Test
    public void testService_EmptyInput() {
        AutoCompleteService service = getAutoCompleteService();
        String[] array = service.getWordsStartingPrefix("");
        assertArrayEquals(new String[0], array, "Unexpected result");
    }

    @Test
    public void testStatistics_RequestHandledCount() {
        Dictionary dictionary = new FileDictionary("dictionary.txt");
        AutoCompleteService service = new AutoCompleteService(
                dictionary,
                new InMemStatisticsService());
        Random rand = new Random();
        int iterations = rand.nextInt(100);
        for (int i = 0; i < iterations; i++) {
            service.getWordsStartingPrefix("AA");
        }
        ServerStatistics statistics = service.getServerStatistics();
        assertNotNull(statistics);
        assertEquals(iterations, statistics.getRequestHandledCount(),
                "Unexpected Request Handled Count");
    }

    @Test
    public void testStatistics_WordCount() {
        Dictionary dictionary = new FileDictionary("dictionary.txt");
        AutoCompleteService service = new AutoCompleteService(
                dictionary,
                new InMemStatisticsService());
        ServerStatistics statistics = service.getServerStatistics();
        assertNotNull(statistics);
        assertEquals(dictionary.getWords().length, statistics.getWordCount(),
                "Unexpected word count");
    }

    private AutoCompleteService getAutoCompleteService() {
        return new AutoCompleteService(
                new FileDictionary("dictionary.txt"),
                new InMemStatisticsService());
    }
}
