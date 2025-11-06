package dev._83080441.skyrain.datagen;

import dev._83080441.skyrain.block.ModBlocks;
import dev._83080441.skyrain.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

    protected ModBlockLootTableProvider( HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {

        dropSelf(ModBlocks.MAGIC_BLOCK.get());
        dropSelf(ModBlocks.MONOLITH_BUTTON.get());
        dropSelf(ModBlocks.MONOLITH_FENCE.get());
        dropSelf(ModBlocks.MONOLITH_STAIR.get());
        dropSelf(ModBlocks.MONOLITH_FENCE_GATE.get());
        dropSelf(ModBlocks.MONOLITH_WALL.get());
        dropSelf(ModBlocks.MONOLITH_PRESSURE.get());
        dropSelf(ModBlocks.MONOLITH_TRAPDOOR.get());
        dropSelf(ModBlocks.MONOLITH_LAMP.get());

        add(ModBlocks.MONOLITH_SLAB.get(), (block) -> createSlabItemTable(ModBlocks.MONOLITH_SLAB.get()));
        add(ModBlocks.MONOLITH_DOOR.get(), (block) -> createDoorTable(ModBlocks.MONOLITH_DOOR.get()));

        // Para la mena: suelta el ítem CINNABAR_ORE
        add(ModBlocks.CINNABAR_BLOCK.get(),
                block -> createMultipleOreDrops(block, ModItems.CINNABAR_ORE.get(), 2.0F, 3.0F));

    }

    protected LootTable.Builder createMultipleOreDrops(Block block, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(block,
                this.applyExplosionDecay(block, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registryLookup.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
