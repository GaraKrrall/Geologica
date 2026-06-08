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

package mc.garakrral.geologica.block;

import mc.garakrral.geologica.Geologica;
import mc.garakrral.geologica.block.rotatable.BrokenRockBlock;
import mc.garakrral.geologica.block.rotatable.GeckoStatueBlock;
import mc.garakrral.geologica.block.rotatable.RotatableBlock;
import mc.garakrral.geologica.block.rotatable.TinyRockBlock;
import mc.garakrral.geologica.block.simple.SimpleBlock;
import mc.garakrral.geologica.util.RegistrationUtil;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GeologicaBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Geologica.MOD_ID);

    //? if 1.21.1 {
    /*public static final DeferredBlock<RotatableBlock> GECKO_STATUE = RegistrationUtil.registerBlock("gecko_statue",
            () -> GeckoStatueBlock.of(BlockBehaviour.Properties.of().strength(1.5F)));

    public static final DeferredBlock<RotatableBlock> BROKEN_ROCK = RegistrationUtil.registerBlock("broken_rock",
            () -> BrokenRockBlock.of(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE)));

    public static final DeferredBlock<RotatableBlock> TINY_ROCK = RegistrationUtil.registerBlock("tiny_rock",
            () -> TinyRockBlock.of(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE)));

    public static final DeferredBlock<SimpleBlock> LIMESTONE = RegistrationUtil.registerBlock("limestone",
            () -> SimpleBlock.of(BlockBehaviour.Properties.ofFullCopy(Blocks.CALCITE)));

    public static final DeferredBlock<SimpleBlock> SULFUR = RegistrationUtil.registerBlock("sulfur",
            () -> SimpleBlock.of(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)));

*///?} else {
   public static final DeferredBlock<RotatableBlock> GECKO_STATUE = RegistrationUtil.registerBlock("gecko_statue",
                GeckoStatueBlock::of, props -> props.strength(1.5F).requiresCorrectToolForDrops(), true);

    public static final DeferredBlock<RotatableBlock> BROKEN_ROCK = RegistrationUtil.registerBlock("broken_rock",
            BrokenRockBlock::of, props ->  BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE), true);

    public static final DeferredBlock<RotatableBlock> TINY_ROCK = RegistrationUtil.registerBlock("tiny_rock",
            TinyRockBlock::of, props ->  BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE), true);

    public static final DeferredBlock<SimpleBlock> LIMESTONE = RegistrationUtil.registerBlock("limestone",
            SimpleBlock::of, props ->  BlockBehaviour.Properties.ofFullCopy(Blocks.CALCITE), true);

    public static final DeferredBlock<SimpleBlock> SULFUR = RegistrationUtil.registerBlock("sulfur",
            SimpleBlock::of, props ->  BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF), true);
//?}
}
