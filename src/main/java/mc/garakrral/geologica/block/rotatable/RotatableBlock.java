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

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;

//? if 1.21.1 {
/*import net.minecraft.world.level.block.state.properties.DirectionProperty;
*///?} else
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class RotatableBlock extends Block {
    //? if 1.21.1 {
    /*public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    *///?} else
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

    protected RotatableBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    protected VoxelShape getNorthShape() {
        return null;
    }

    protected VoxelShape getSouthShape() {
        return null;
    }

    protected VoxelShape getEastShape() {
        return null;
    }

    protected VoxelShape getWestShape() {
        return null;
    }

    @MustBeInvokedByOverriders
    public static RotatableBlock of(Properties properties) {
        return new RotatableBlock(properties);
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        if (!state.hasProperty(FACING)) return getNorthShape() != null ? getNorthShape() : Shapes.block();

        VoxelShape shape = switch (state.getValue(FACING)) {
            case NORTH -> getNorthShape();
            case SOUTH -> getSouthShape();
            case EAST -> getEastShape();
            case WEST -> getWestShape();
            default -> getNorthShape();
        };

        return shape != null ? shape : Shapes.block();
    }
}