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

package mc.garakrral.geologica.block.simple;

import net.minecraft.world.level.block.Block;

public class SimpleBlock extends Block {
    private SimpleBlock(Properties properties) {
        super(properties);
    }

    public static SimpleBlock of(Properties properties) {
        return new SimpleBlock(properties);
    }
}
