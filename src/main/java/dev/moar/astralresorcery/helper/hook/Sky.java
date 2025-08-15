package dev.moar.astralresorcery.helper.hook;

import dev.moar.astralresorcery.AstralReSorcery;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AstralReSorcery.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Sky {

    private static boolean eclipse = false;
    private static float lastDiffDeg = 999f;

    public static void updateEclipseState(boolean isEclipse, float angularDiffDeg) {
        eclipse = isEclipse;
        lastDiffDeg = angularDiffDeg;
    }

    @SubscribeEvent
    public static void onFogColor(ViewportEvent.ComputeFogColor e) {
        if (!eclipse) return;

        final float DIFF_AT_0 = 0f;
        final float DIFF_AT_MAX = 8f;
        float t = 1.0f - Math.min(1.0f, Math.max(0.0f, (lastDiffDeg - DIFF_AT_0) / (DIFF_AT_MAX - DIFF_AT_0)));
        if (t <= 0f) return;

        float minLight = 0.05f; // nunca negro total
        float perc = minLight + (1f - minLight) * (1f - t);

        e.setRed  (e.getRed()   * perc);
        e.setGreen(e.getGreen() * perc);
        e.setBlue (e.getBlue()  * perc);
    }
}