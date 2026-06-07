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

package mc.garakrral.geologica.worldgen.configured;

import mc.garakrral.geologica.Geologica;
import mc.garakrral.geologica.block.GeologicaBlocks;
import mc.garakrral.geologica.util.LocationUtil;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class GeologicaConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> BROKEN_ROCK = ResourceKey.create(Registries.CONFIGURED_FEATURE,
            LocationUtil.modIdentifier(Geologica.MOD_ID, "broken_rock"));

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        context.register(BROKEN_ROCK, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(GeologicaBlocks.BROKEN_ROCK.get()))));
    }
}
