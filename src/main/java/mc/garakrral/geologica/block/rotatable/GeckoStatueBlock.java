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

package mc.garakrral.geologica.block.rotatable;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GeckoStatueBlock extends RotatableBlock {

    protected static final VoxelShape NORTH_SHAPE = Block.box(4, 0, 4, 12, 6, 14);
    protected static final VoxelShape SOUTH_SHAPE = Block.box(4, 0, 2, 12, 6, 12);
    protected static final VoxelShape EAST_SHAPE = Block.box(2, 0, 4, 12, 6, 12);
    protected static final VoxelShape WEST_SHAPE = Block.box(4, 0, 4, 14, 6, 12);

    private GeckoStatueBlock(Properties properties) {
        super(properties);
    }

    public static GeckoStatueBlock of(Properties properties) {
        return new GeckoStatueBlock(properties);
    }

    @Override
    protected VoxelShape getNorthShape() {
        return NORTH_SHAPE;
    }

    @Override
    public VoxelShape getEastShape() {
        return EAST_SHAPE;
    }
    @Override
    protected VoxelShape getSouthShape() {
        return SOUTH_SHAPE;
    }

    @Override
    public VoxelShape getWestShape() {
        return WEST_SHAPE;
    }
}
