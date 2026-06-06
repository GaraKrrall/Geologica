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

import mc.garakrral.geologica.Geologica;
import mc.garakrral.geologica.block.GeologicaBlocks;
import mc.garakrral.geologica.util.LocationUtil;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import java.util.List;

public class GeologicaConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> BROKEN_ROCK_KEY =
            ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    LocationUtil.modIdentifier(Geologica.MOD_ID, "broken_rock"));

    public static OreConfiguration brokenRockConfig() {
        return new OreConfiguration(
                List.of(
                        OreConfiguration.target(
                                OreFeatures.,
                                GeologicaBlocks.BROKEN_ROCK.get().defaultBlockState()
                        )
                ),
                4
        );
    }
}
