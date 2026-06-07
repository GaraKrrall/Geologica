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

package mc.garakrral.geologica.worldgen;

import mc.garakrral.geologica.worldgen.configured.GeologicaConfiguredFeatures;
import mc.garakrral.geologica.worldgen.modifier.GeologicaBiomeModifiers;
import mc.garakrral.geologica.worldgen.placed.GeologicaPlacedFeatures;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class GeologicaWorldGen {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, GeologicaConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, GeologicaPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, GeologicaBiomeModifiers::bootstrap);
}
