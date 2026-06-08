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

package mc.garakrral.geologica.worldgen.modifier;

import mc.garakrral.geologica.util.RegistrationUtil;
import mc.garakrral.geologica.worldgen.placed.GeologicaPlacedFeatures;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;

public class GeologicaBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_BROKEN_ROCK = RegistrationUtil.createNewBiomeModifierKey("add_broken_rock");
    public static final ResourceKey<BiomeModifier> ADD_TINY_ROCK = RegistrationUtil.createNewBiomeModifierKey("add_tiny_rock");
    public static final ResourceKey<BiomeModifier> ADD_LIMESTONE = RegistrationUtil.createNewBiomeModifierKey("add_limestone");
    public static final ResourceKey<BiomeModifier> ADD_CALCITE = RegistrationUtil.createNewBiomeModifierKey("add_calcite");
    public static final ResourceKey<BiomeModifier> ADD_GECKO_STATUE = RegistrationUtil.createNewBiomeModifierKey("add_gecko_statue");
    public static final ResourceKey<BiomeModifier> ADD_DRIPSTONE = RegistrationUtil.createNewBiomeModifierKey("add_dripstone");
    public static final ResourceKey<BiomeModifier> ADD_SULFUR = RegistrationUtil.createNewBiomeModifierKey("add_sulfur");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        context.register(ADD_BROKEN_ROCK, new BiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(GeologicaPlacedFeatures.BROKEN_ROCK)), GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(ADD_TINY_ROCK, new BiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(GeologicaPlacedFeatures.TINY_ROCK)), GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(ADD_LIMESTONE, new BiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(GeologicaPlacedFeatures.LIMESTONE)), GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_CALCITE, new BiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(GeologicaPlacedFeatures.CALCITE)), GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_GECKO_STATUE, new BiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(GeologicaPlacedFeatures.GECKO_STATUE)), GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_DRIPSTONE, new BiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(GeologicaPlacedFeatures.DRIPSTONE)), GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_SULFUR, new BiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(GeologicaPlacedFeatures.SULFUR)), GenerationStep.Decoration.UNDERGROUND_ORES));
    }
}
