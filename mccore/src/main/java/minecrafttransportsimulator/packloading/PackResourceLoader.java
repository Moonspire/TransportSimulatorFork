package minecrafttransportsimulator.packloading;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import minecrafttransportsimulator.items.components.AItemPack;
import minecrafttransportsimulator.jsondefs.*;

/**
 * Class responsible for loading pack resource files from pack archives.  This happens both during load
 * and during gameplay as different references are requested.
 *
 * @author don_bruce
 */
public class PackResourceLoader {
    /**
     * Returns the requested resource for the passed-in definition.  Name in this case is used
     * for the file-name, and may be different than the definition system name.
     */
    public static String getPackResource(AJSONItem definition, ResourceType type, String name) {
        switch (PackStructure.values()[PackParser.getPackConfiguration(definition.packID).fileStructure]) {
            case DEFAULT: {
                return "/assets/" + definition.packID + "/" + type.prefixFolder + definition.classification.toDirectory() + name + type.normalSuffix;
            }
            case LAYERED: {
                return "/assets/" + definition.packID + "/" + type.prefixFolder + definition.classification.toDirectory() + definition.prefixFolders + name + type.normalSuffix;
            }
            case MODULAR: {
                return "/assets/" + definition.packID + "/" + definition.classification.toDirectory() + definition.prefixFolders + name + type.modularSuffix;
            }
        }
        return null;
    }

    public enum PackStructure {
        DEFAULT,
        LAYERED,
        MODULAR
    }

    public enum ResourceType {
        OBJ_MODEL("objmodels/", ".obj", ".obj"),
        LT_MODEL("littletilesmodels/", ".txt", ".txt"),
        PNG("textures/", ".png", ".png"),
        ITEM_PNG("textures/items/", ".png", "_item.png"),
        ITEM_JSON("models/item/", ".json", "_item.json");

        public final String prefixFolder;
        public final String normalSuffix;
        public final String modularSuffix;

        ResourceType(String prefixFolder, String normalSuffix, String modularSuffix) {
            this.prefixFolder = prefixFolder;
            this.normalSuffix = normalSuffix;
            this.modularSuffix = modularSuffix;
        }
    }

    public enum ItemClassification implements CustomItemClassification {
        VEHICLE(JSONVehicle.class),
        PART(JSONPart.class),
        INSTRUMENT(JSONInstrument.class),
        POLE(JSONPoleComponent.class),
        ROAD(JSONRoadComponent.class),
        DECOR(JSONDecor.class),
        BULLET(JSONBullet.class),
        ITEM(JSONItem.class),
        SKIN(JSONSkin.class),
        PANEL(JSONPanel.class);

        private final Class<? extends AJSONBase> representingClass;

        ItemClassification(Class<? extends AJSONBase> representingClass) {
            this.representingClass = representingClass;
        }

        public static List<String> getAllTypesAsStrings() {
            List<String> assetTypes = new ArrayList<>();
            for (ItemClassification classification : ItemClassification.values()) {
                assetTypes.add(classification.name().toLowerCase(Locale.ROOT));
            }
            return assetTypes;
        }

        @Override
        public String toDirectory() {
            return this.name().toLowerCase(Locale.ROOT) + "s/";
        }

        @Override
        public Class<? extends AJSONBase> getRepresentingClass() {
            return representingClass;
        }

        // Returns null as Enumerated Classifications use hardcoded behavior located at PackParser#parseAllDefinitions
        @Override
        public AItemPack<?> getItem(AJSONMultiModelProvider provider, JSONSubDefinition subDefinition, String packID) {
            return null;
        }

        // Returns null as Enumerated Classifications use hardcoded behavior located at JSONParser#importJSON
        @Override
        public AJSONBase loadDefinition(File jsonFile, AJSONBase definition) {
            return null;
        }

        public static CustomItemClassification fromDirectory(String directory) {
            try {
                return ItemClassification.valueOf(directory.substring(0, directory.length() - "s/".length()).toUpperCase(Locale.ROOT));
            } catch (Exception ignored) {
                try {
                    return CustomClassifications.fromDirectory(directory);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Was told to get classification for directory: " + directory + " but none exists.  Contact the mod author!");
                }
            }
        }
    }
}
