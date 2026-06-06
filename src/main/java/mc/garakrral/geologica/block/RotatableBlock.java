package mc.garakrral.geologica.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
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

    protected static final VoxelShape NORTH_SHAPE = Block.box(4, 0, 4, 12, 6, 14);
    protected static final VoxelShape SOUTH_SHAPE = Block.box(4, 0, 2, 12, 6, 12);
    protected static final VoxelShape EAST_SHAPE = Block.box(2, 0, 4, 12, 6, 12);
    protected static final VoxelShape WEST_SHAPE = Block.box(4, 0, 4, 14, 6, 12);

    public RotatableBlock(Properties properties) {
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
        return NORTH_SHAPE;
    }

    protected VoxelShape getSouthShape() {
        return SOUTH_SHAPE;
    }

    protected VoxelShape getEastShape() {
        return EAST_SHAPE;
    }

    protected VoxelShape getWestShape() {
        return WEST_SHAPE;
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> getNorthShape();
            case SOUTH -> getSouthShape();
            case EAST -> getEastShape();
            case WEST -> getWestShape();
            default -> getNorthShape();
        };
    }
}