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

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BrokenRockBlock extends RotatableBlock {
    protected static final VoxelShape NORTH_SHAPE = Block.box(5, 0, 5, 13, 2, 15);
    protected static final VoxelShape EAST_SHAPE = Block.box(1, 0, 5, 11, 2, 13);
    protected static final VoxelShape WEST_SHAPE = Block.box(5, 0, 3, 15, 2, 11);
    protected static final VoxelShape SOUTH_SHAPE = Block.box(3, 0, 1, 11, 2, 11);

    public BrokenRockBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getNorthShape() {
        return NORTH_SHAPE;
    }

    @Override
    protected VoxelShape getSouthShape() {
        return SOUTH_SHAPE;
    }

    @Override
    protected VoxelShape getEastShape() {
        return EAST_SHAPE;
    }

    @Override
    protected VoxelShape getWestShape() {
        return WEST_SHAPE;
    }
}
