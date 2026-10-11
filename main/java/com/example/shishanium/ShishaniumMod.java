package com.example.shishanium;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemAttributeModifiers;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(ShishaniumMod.MODID)
public final class ShishaniumMod {
    public static final String MODID = "shishanium";

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);

    public static final DeferredBlock<Block> SHISHANIUM = BLOCKS.register("shishanium",
            () -> new ShishaniumBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN)
                    .strength(5.0F, 1200.0F).lightLevel(state -> 0).noOcclusion()));
    public static final DeferredItem<BlockItem> SHISHANIUM_ITEM = ITEMS.register("shishanium",
            () -> new BlockItem(SHISHANIUM.get(), new Item.Properties().attributes(
                    ItemAttributeModifiers.builder()
                            // Player base damage is 1, so +17 makes the total 18 damage.
                            .add(Attributes.ATTACK_DAMAGE,
                                    new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, 17.0,
                                            AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND)
                            // Player base attack speed is 4, so -1 makes it 3.
                            .add(Attributes.ATTACK_SPEED,
                                    new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, -1.0,
                                            AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND)
                            .build())));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShishaniumBlockEntity>> SHISHANIUM_BE =
            BLOCK_ENTITIES.register("shishanium", () -> BlockEntityType.Builder.of(ShishaniumBlockEntity::new, SHISHANIUM.get()).build(null));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SHISHANIUM_TAB = TABS.register("shishanium_tab",
            () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.shishanium"))
                    .icon(() -> SHISHANIUM_ITEM.get().getDefaultInstance())
                    .displayItems((parameters, output) -> output.accept(SHISHANIUM_ITEM.get())).build());

    public ShishaniumMod(IEventBus modBus) {
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        TABS.register(modBus);
        BLOCK_ENTITIES.register(modBus);
    }
}
