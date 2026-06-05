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
import mc.garakrral.geologica.util.RegistrationUtil;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GeologicaBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Geologica.MOD_ID);

    public static final DeferredBlock<Block> GECKO_STATUE = RegistrationUtil.registerBlock("gecko_statue",
            () -> new RotatableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
}
