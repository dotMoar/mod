package dev.moar.astralresorcery;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.IEventBus;

@Mod(AstralReSorcery.MODID)
public class AstralReSorcery {
    public static final String MODID = "astralresorcery";

    public AstralReSorcery() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
    }

}