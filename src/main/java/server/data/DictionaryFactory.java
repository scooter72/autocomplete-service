package server.data;

import server.factory.AbstractFactory;

public class DictionaryFactory extends AbstractFactory {
    private static Dictionary dictionary;

    public static Dictionary getDictionary() {
        if (dictionary == null) {
            dictionary = getClassInstance("server.dictionary");
        }
        return dictionary;
    }


}
