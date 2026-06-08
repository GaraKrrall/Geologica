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

import mc.garakrral.geologica.block.GeologicaBlocks;
import mc.garakrral.geologica.util.RegistrationUtil;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class GeologicaConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> BROKEN_ROCK = RegistrationUtil.createNewConfiguredFeatureKey("broken_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TINY_ROCK = RegistrationUtil.createNewConfiguredFeatureKey("tiny_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LIMESTONE = RegistrationUtil.createNewConfiguredFeatureKey("limestone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE = RegistrationUtil.createNewConfiguredFeatureKey("calcite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GECKO_STATUE = RegistrationUtil.createNewConfiguredFeatureKey("gecko_statue");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DRIPSTONE = RegistrationUtil.createNewConfiguredFeatureKey("dripstone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SULFUR = RegistrationUtil.createNewConfiguredFeatureKey("sulfur");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest naturalStone = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);

        context.register(BROKEN_ROCK, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(GeologicaBlocks.BROKEN_ROCK.get()))));
        context.register(TINY_ROCK, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(GeologicaBlocks.TINY_ROCK.get()))));
        context.register(LIMESTONE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(naturalStone, GeologicaBlocks.LIMESTONE.get().defaultBlockState(), 28)));
        context.register(CALCITE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(naturalStone, Blocks.CALCITE.defaultBlockState(), 16)));
        context.register(GECKO_STATUE, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(GeologicaBlocks.GECKO_STATUE.get()))));
        context.register(DRIPSTONE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(naturalStone, Blocks.DRIPSTONE_BLOCK.defaultBlockState(), 10)));
        context.register(SULFUR, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(naturalStone, GeologicaBlocks.SULFUR.get().defaultBlockState(), 18)));
    }
}
