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

package mc.garakrral.geologica.item.group;

import mc.garakrral.geologica.Geologica;
import mc.garakrral.geologica.block.GeologicaBlocks;
import mc.garakrral.geologica.util.RegistrationUtil;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class GeologicaItemGroups {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Geologica.MOD_ID);

    public static final Supplier<CreativeModeTab> MAIN_TAB =
            RegistrationUtil.createNewCreativeTab(TABS, Geologica.MOD_ID, GeologicaBlocks.BROKEN_ROCK);
}
