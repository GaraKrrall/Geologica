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
    /*public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;;
     *///?} else
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

    private static final VoxelShape NORTH_SHAPE = Block.box(4, 0, 4, 12, 6, 14);
    private static final VoxelShape SOUTH_SHAPE = Block.box(4, 0, 2, 12, 6, 12);
    private static final VoxelShape EAST_SHAPE = Block.box(2, 0, 4, 12, 6, 12);
    private static final VoxelShape WEST_SHAPE = Block.box(4, 0, 4, 14, 6, 12);

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

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Direction facing = state.getValue(FACING);

        return switch (facing) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }
}