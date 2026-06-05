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

package mc.garakrral.geologica.util;

import mc.garakrral.geologica.block.GeologicaBlocks;
import mc.garakrral.geologica.item.GeologicaItems;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

public class RegistrationUtil {
    /**
     * Registers all blocks belonging to the mod.
     */
    @ApiStatus.Internal
    public static void registerModBLocks(IEventBus modBus, Boolean printLog) {
        GeologicaBlocks.BLOCKS.register(modBus);
        if (printLog) LogUtil.info("Registering Mod Blocks");
    }
    /**
     * Registers all items belonging to the mod.
     */
    @ApiStatus.Internal
    public static void registerModItems(IEventBus modBus, Boolean printLog) {
        GeologicaItems.ITEMS.register(modBus);
        if (printLog) LogUtil.info("Registering Mod Items");
    }

    @ApiStatus.Internal
    public static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = GeologicaBlocks.BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    @ApiStatus.Internal
    public static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        GeologicaItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
