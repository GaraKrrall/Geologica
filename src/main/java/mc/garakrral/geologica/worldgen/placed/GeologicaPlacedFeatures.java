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

package mc.garakrral.geologica.worldgen.placed;

import mc.garakrral.geologica.Geologica;
import mc.garakrral.geologica.block.GeologicaBlocks;
import mc.garakrral.geologica.util.LocationUtil;
import mc.garakrral.geologica.util.RegistrationUtil;
import mc.garakrral.geologica.worldgen.configured.GeologicaConfiguredFeatures;

import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class GeologicaPlacedFeatures {
    public static final ResourceKey<PlacedFeature> BROKEN_ROCK = RegistrationUtil.createNewPlacedFeatureKey("broken_rock");
    public static final ResourceKey<PlacedFeature> TINY_ROCK = RegistrationUtil.createNewPlacedFeatureKey("tiny_rock");
    public static final ResourceKey<PlacedFeature> LIMESTONE = RegistrationUtil.createNewPlacedFeatureKey("limestone");
    public static final ResourceKey<PlacedFeature> CALCITE = RegistrationUtil.createNewPlacedFeatureKey("calcite");
    public static final ResourceKey<PlacedFeature> GECKO_STATUE = RegistrationUtil.createNewPlacedFeatureKey("gecko_statue");
    public static final ResourceKey<PlacedFeature> DRIPSTONE = RegistrationUtil.createNewPlacedFeatureKey("dripstone");
    public static final ResourceKey<PlacedFeature> SULFUR = RegistrationUtil.createNewPlacedFeatureKey("sulfur");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configured = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(BROKEN_ROCK, new PlacedFeature(configured.getOrThrow(GeologicaConfiguredFeatures.BROKEN_ROCK), List.of(CountPlacement.of(185), InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(16)),
                //? if 1.21.1 {
                /*BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Blocks.DEEPSLATE),
                        BlockPredicate.ONLY_IN_AIR_PREDICATE)), BiomeFilter.biome())));
                *///?} else
                 BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.DEEPSLATE), BlockPredicate.ONLY_IN_AIR_PREDICATE)), BiomeFilter.biome())));

        context.register(TINY_ROCK, new PlacedFeature(configured.getOrThrow(GeologicaConfiguredFeatures.TINY_ROCK), List.of(CountPlacement.of(125), InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(16)),
                //? if 1.21.1 {
                /*BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Blocks.DEEPSLATE),
                        BlockPredicate.ONLY_IN_AIR_PREDICATE)), BiomeFilter.biome())));
                *///?} else
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.DEEPSLATE), BlockPredicate.ONLY_IN_AIR_PREDICATE)), BiomeFilter.biome())));

        context.register(LIMESTONE, new PlacedFeature(configured.getOrThrow(GeologicaConfiguredFeatures.LIMESTONE),
                List.of(CountPlacement.of(3), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-40),
                        VerticalAnchor.absolute(60)), BiomeFilter.biome())));

        context.register(CALCITE, new PlacedFeature(configured.getOrThrow(GeologicaConfiguredFeatures.CALCITE),
                List.of(CountPlacement.of(3), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-40),
                        VerticalAnchor.absolute(60)), BiomeFilter.biome())));

        context.register(GECKO_STATUE, new PlacedFeature(configured.getOrThrow(GeologicaConfiguredFeatures.GECKO_STATUE), List.of(CountPlacement.of(15), InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(16)),
                //? if 1.21.1 {
                /*BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Blocks.DEEPSLATE),
                        BlockPredicate.ONLY_IN_AIR_PREDICATE)), BiomeFilter.biome())));
                *///?} else
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), GeologicaBlocks.GECKO_STATUE.get()), BlockPredicate.ONLY_IN_AIR_PREDICATE)), BiomeFilter.biome())));

        context.register(DRIPSTONE, new PlacedFeature(configured.getOrThrow(GeologicaConfiguredFeatures.DRIPSTONE),
                List.of(CountPlacement.of(3), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-40),
                        VerticalAnchor.absolute(60)), BiomeFilter.biome())));

        context.register(SULFUR, new PlacedFeature(configured.getOrThrow(GeologicaConfiguredFeatures.SULFUR),
                List.of(CountPlacement.of(3), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-40),
                        VerticalAnchor.absolute(60)), BiomeFilter.biome())));

    }
}
