/*
 * Copyright (c) 2026 GaraKrral
 *
 * Source code in this project is licensed under the GNU General Public License v3.0 (GPLv3).
 * See the LICENSE file for details.
 *
 * All game assets, including but not limited to graphics, audio, models, textures,
 * and other non-code content, are proprietary and All Rights Reserved unless
 * explicitly stated otherwise.
 *
 */

package mc.garakrral.geologica.datagen;

import mc.garakrral.geologica.Geologica;
import mc.garakrral.geologica.util.LogUtil;
import mc.garakrral.geologica.worldgen.GeologicaWorldGen;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;

public class GeologicaDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Server event) {
        LogUtil.info("Gathering Data...");
        LogUtil.info(GeologicaWorldGen.BUILDER);

        event.getGenerator().addProvider(
                true,
                new DatapackBuiltinEntriesProvider(
                        event.getGenerator().getPackOutput(),
                        event.getLookupProvider(),
                        GeologicaWorldGen.BUILDER,
                        Set.of(Geologica.MOD_ID)
                )
        );
    }
}
