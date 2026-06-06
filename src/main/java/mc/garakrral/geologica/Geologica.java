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

package mc.garakrral.geologica;

import mc.garakrral.geologica.util.LogUtil;
import mc.garakrral.geologica.util.RegistrationUtil;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Geologica.MOD_ID)
public class Geologica {
    public static final String VERSION = /*$ mod_version*/ "0.1.0";
    public static final String MINECRAFT = /*$ minecraft*/ "26.1.2";
    public static final String MOD_ID = "geologica";

    /**
     * Uses {@link LogUtil} to display an informational message to the end user during release.
     *
     * And This is where the mod's main registrations are handled.
     */
    public Geologica(IEventBus bus) {
        //? if release
        LogUtil.info();

        RegistrationUtil.registerModBlocks(bus, true);
        RegistrationUtil.registerModItemsAndItemGroups(bus, true);
    }
}