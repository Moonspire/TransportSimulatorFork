package minecrafttransportsimulator.packloading;

import java.util.HashMap;
import java.util.Map;

public class CustomClassifications {
    private static final Map<String, CustomItemClassification> classifications = new HashMap<>();

    public static void register(CustomItemClassification classification) {
        classifications.put(classification.toDirectory(), classification);
    }

    public static CustomItemClassification fromDirectory(String directory) {
        return classifications.get(directory);
    }
}
