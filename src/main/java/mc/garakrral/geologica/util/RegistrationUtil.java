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
import mc.garakrral.geologica.item.group.GeologicaItemGroups;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Utility class responsible for registering blocks, items,
 * block items and creative mode tabs used by Geologica.
 *
 * <p>This class centralizes common registration logic and
 * provides compatibility helpers for different NeoForge and
 * Minecraft versions.</p>
 */
public class RegistrationUtil {
    /**
     * Stores all items and block items that should be displayed in the mod's main creative tab.
     *
     * <p>Entries are automatically added when using one of the provided registration methods.</p>
     *
     * <p>This list is consumed by {@link GeologicaItemGroups#MAIN_TAB} when populating
     * the creative mode inventory.</p>
     */
    private static final List<Supplier<? extends ItemLike>> MAIN_TAB_CONTENTS = new ArrayList<>();

    /**
     * Constructs a new utility instance.
     *
     * <p>This constructor is intentionally inaccessible because this class only
     * contains static utility methods.</p>
     *
     * @throws UnsupportedOperationException always
     */
    private RegistrationUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Registers all blocks belonging to the mod.
     *
     * @param modBus the mod event bus used for registration
     * @param printLog whether a registration message should be written to the log
     */
    @ApiStatus.Internal
    public static void registerModBlocks(IEventBus modBus, Boolean printLog) {
        GeologicaBlocks.BLOCKS.register(modBus);
        if (printLog) LogUtil.info("Registering Mod Blocks");
    }

    /**
     * Registers all items belonging to the mod.
     *
     * @param modBus the mod event bus used for registration
     * @param printLog whether a registration message should be written to the log
     */
    @ApiStatus.Internal
    public static void registerModItems(IEventBus modBus, Boolean printLog) {
        GeologicaItems.ITEMS.register(modBus);
        if (printLog) LogUtil.info("Registering Mod Items");
    }

    /**
     * Registers all items and creative mode tabs belonging to the mod.
     *
     * <p>This method acts as a convenience wrapper around
     * {@link #registerModItems(IEventBus, Boolean)} and additionally registers
     * all creative mode tabs defined by Geologica.</p>
     *
     * <p>Using this method is recommended when both items and item groups should
     * be registered during mod initialization, as it reduces boilerplate and
     * keeps registration code centralized.</p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * public Geologica(IEventBus bus) {
     *     RegistrationUtil.registerModItemsAndItemGroups(bus, true);
     * }
     * }</pre>
     *
     * @param modBus the mod event bus used for registration
     * @param printLog whether registration messages should be written to the log
     */
    @ApiStatus.Internal
    public static void registerModItemsAndItemGroups(IEventBus modBus, Boolean printLog) {
        registerModItems(modBus, printLog);
        GeologicaItemGroups.TABS.register(modBus);
        if (printLog) LogUtil.info("Registering Mod Item Groups");
    }

    /**
     * Registers a block using the legacy registration system.
     *
     * <p>This method automatically creates and registers a matching
     * {@link BlockItem} and adds the block to the mod's main creative tab.</p>
     *
     * <p>Example:</p>
     * <pre>{@code
     * RegistrationUtil.registerBlock(
     *     "test_block",
     *     () -> new Block(BlockBehaviour.Properties.of())
     * );
     * }</pre>
     *
     * @param name the registry name of the block
     * @param block the block supplier
     * @param <T> the block type
     * @return the registered block
     */
    @ApiStatus.Internal
    public static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = GeologicaBlocks.BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        addToMainTab(toReturn);
        return toReturn;
    }

    /**
     * Registers a {@link BlockItem} for the specified block.
     *
     * <p>This method is used by the legacy block registration system and is
     * automatically called by {@link #registerBlock(String, Supplier)}.</p>
     *
     * <p>In most cases, this method should not be called directly.</p>
     *
     * @param name the registry name of the block item
     * @param block the block supplier associated with the block item
     * @param <T> the block type
     */
    @ApiStatus.Internal
    public static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        GeologicaItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    /**
     * Version 26.1.2 requires the new registration system. Please set {@code useNewApi} to {@code true}.
     *
     * @param name name
     * @param factory factory
     * @param properties properties
     * @param useNewApi use new api
     *
     * <p>Example:</p>
     * <pre>{@code
     * RegistrationUtil.registerBlock(
     *     "id",
     *     Block::new,
     *     props -> props.strength(1.5F).requiresCorrectToolForDrops(),
     *     true
     * );
     * }</pre>
     */
    @ApiStatus.Internal
    public static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> factory, UnaryOperator<BlockBehaviour.Properties> properties, boolean useNewApi) {
        DeferredBlock<T> block = GeologicaBlocks.BLOCKS.registerBlock(name, factory, properties);
        registerBlockItem(name, block, true);
        addToMainTab(block);
        return block;
    }

    /**
     * Registers a simple block item for the specified block using the NeoForge
     * 26.1.2+ registration system.
     *
     * <p>This method is automatically called by
     * {@link #registerBlock(String, Function, UnaryOperator, boolean)}.</p>
     *
     * <p>Version 26.1.2 introduced a simplified block item registration API.
     * Please use {@code useNewApi = true} when registering blocks through the
     * corresponding block registration method.</p>
     *
     * @param name the registry name of the block item
     * @param block the block supplier associated with the block item
     * @param useNewApi indicates that the new registration system should be used
     * @param <T> the block type
     */
    @ApiStatus.Internal
    public static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block, boolean useNewApi) {
        GeologicaItems.ITEMS.registerSimpleBlockItem(name, block);
    }

    /**
     * Creates and registers the main creative mode tab.
     *
     * @param tabs the creative tab deferred register
     * @param modId the mod identifier used for the tab translation key
     * @param icon the item used as the tab icon
     *
     * <p>Example:</p>
     * <pre>{@code
     * RegistrationUtil.createNewCreativeTab(
     *     TABS,
     *     "geologica",
     *     GeologicaBlocks.BROKEN_ROCK
     * );
     * }</pre>
     *
     * @return the registered creative mode tab supplier
     */
    @ApiStatus.Internal
    public static Supplier<CreativeModeTab> createNewCreativeTab(DeferredRegister<CreativeModeTab> tabs, String modId, Supplier<? extends ItemLike> icon) {
        return tabs.register("main", () -> CreativeModeTab.builder().icon(() ->
                new ItemStack(icon.get())).title(Component.translatable("itemGroup." + modId)).displayItems((params, output)
                -> getMainTabContents().forEach(item -> output.accept(item.get()))).build());
    }

    /**
     * Adds the specified item supplier to the main creative tab contents.
     *
     * <p>Items added through this method will automatically appear in the mod's
     * main creative tab when it is constructed.</p>
     *
     * @param item the item supplier to add
     */
    @ApiStatus.Internal
    public static void addToMainTab(Supplier<? extends ItemLike> item) {
        getMainTabContents().add(item);
    }

    /**
     * Returns the contents of the mod's main creative tab.
     *
     * <p>This method is primarily intended for internal use by the creative tab
     * registration system.</p>
     *
     * @return all entries currently registered for the main creative tab
     *
     *
    * <p>Example:</p>
     * <pre>{@code
     * RegistrationUtil.addToMainTab(ModItems.TEST_ITEM);
     * RegistrationUtil.addToMainTab(ModBlocks.TEST_BLOCK);
     * }</pre>
     *
     */
    @ApiStatus.Internal
    public static List<Supplier<? extends ItemLike>> getMainTabContents() {
        return getBlockItemContents();
    }

    /**
     * Returns the internal backing list used to populate the mod's main creative tab.
     *
     * <p>Modifying this list directly is discouraged. Use
     * {@link #addToMainTab(Supplier)} whenever possible.</p>
     *
     * <p>This API is experimental and may be changed or removed without notice
     * in future versions.</p>
     *
     * @return the internal creative tab contents list
     */
    @ApiStatus.Internal
    @ApiStatus.Experimental
    public static List<Supplier<? extends ItemLike>> getBlockItemContents() {
        return MAIN_TAB_CONTENTS;
    }
}
