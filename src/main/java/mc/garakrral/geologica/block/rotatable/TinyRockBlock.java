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
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TinyRockBlock extends RotatableBlock {
    protected static final VoxelShape NORTH_SHAPE = Shapes.or(Block.box(6, 0, 10, 8, 2, 12), Block.box(4, 0, 8, 6, 2, 10), Block.box(6, 0, 6, 8, 2, 8), Block.box(8, 0, 8, 10, 2, 10), Block.box(6, 2, 8, 8, 4, 10), Block.box(6, 0, 8, 8, 2, 10));
    protected static final VoxelShape SOUTH_SHAPE = Shapes.or(Block.box(8, 0, 4, 10, 2, 6), Block.box(10, 0, 6, 12, 2, 8), Block.box(8, 0, 8, 10, 2, 10), Block.box(6, 0, 6, 8, 2, 8), Block.box(8, 2, 6, 10, 4, 8), Block.box(8, 0, 6, 10, 2, 8));
    protected static final VoxelShape EAST_SHAPE = Shapes.or(Block.box(4, 0, 6, 6, 2, 8), Block.box(6, 0, 4, 8, 2, 6), Block.box(8, 0, 6, 10, 2, 8), Block.box(6, 0, 8, 8, 2, 10), Block.box(6, 2, 6, 8, 4, 8), Block.box(6, 0, 6, 8, 2, 8));
    protected static final VoxelShape WEST_SHAPE = Shapes.or(Block.box(10, 0, 8, 12, 2, 10), Block.box(8, 0, 10, 10, 2, 12), Block.box(6, 0, 8, 8, 2, 10), Block.box(8, 0, 6, 10, 2, 8), Block.box(8, 2, 8, 10, 4, 10), Block.box(8, 0, 8, 10, 2, 10));

    private TinyRockBlock(Properties properties) {
        super(properties);
    }

    public static TinyRockBlock of(Properties properties) {
        return new TinyRockBlock(properties);
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