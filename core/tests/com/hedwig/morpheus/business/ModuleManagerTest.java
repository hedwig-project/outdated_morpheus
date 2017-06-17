package com.hedwig.morpheus.business;

import com.hedwig.morpheus.service.implementation.ModuleManager;
import com.hedwig.morpheus.service.interfaces.IModuleManager;
import com.hedwig.morpheus.domain.model.implementation.Module;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by hugo. All rights reserved.
 */
public class ModuleManagerTest {
    private final IModuleManager moduleManager;

    // TODO : Use mock for managers
    public ModuleManagerTest() {
        moduleManager = new ModuleManager(null);
    }

    @Test
    public void additionOfModuleToEmptyModuleManager() {
        Module masterBedroom = new Module("1", "masterBedroom", "/hw/masterbedroom");

        boolean successful = moduleManager.registerModule(masterBedroom);

        assertTrue(successful);
    }

    @Test
    public void additionOfDuplicateModule() {
        Module masterBedroom = new Module("1", "masterBedroom", "/hw/masterbedroom");
        moduleManager.registerModule(masterBedroom);

        boolean successful = moduleManager.registerModule(masterBedroom);

        assertFalse(successful);
    }

    @Test
    public void removalOfExistingModule() {
        Module masterBedroom = new Module("1", "masterBedroom", "/hw/masterbedroom");
        moduleManager.registerModule(masterBedroom);

        boolean successful = moduleManager.removeModuleById(masterBedroom.getId());

        assertTrue(successful);
    }

    @Test
    public void removalOfNonExistingModule() {
        Module masterBedroom = new Module("1", "masterBedroom", "/hw/masterbedroom");

        boolean successful = moduleManager.removeModuleById(masterBedroom.getId());

        assertFalse(successful);
    }
}
