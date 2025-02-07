package minecrafttransportsimulator.packloading;

import minecrafttransportsimulator.items.components.AItemPack;
import minecrafttransportsimulator.jsondefs.AJSONBase;
import minecrafttransportsimulator.jsondefs.AJSONMultiModelProvider;
import minecrafttransportsimulator.jsondefs.JSONSubDefinition;

import java.io.File;

public interface CustomItemClassification {
    String toDirectory();

    Class<? extends AJSONBase> getRepresentingClass();

    AItemPack<?> getItem(AJSONMultiModelProvider provider, JSONSubDefinition subDefinition, String packID);

    AJSONBase loadDefinition(File jsonFile, AJSONBase definition);
}
