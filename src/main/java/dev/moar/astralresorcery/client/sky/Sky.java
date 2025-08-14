package dev.moar.astralresorcery.client.sky;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import dev.moar.astralresorcery.AstralReSorcery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AstralReSorcery.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Sky {

    private static final ResourceLocation SUN_TEX = ResourceLocation.withDefaultNamespace("textures/environment/sun.png");
    private static final ResourceLocation MOON_TEX = ResourceLocation.withDefaultNamespace("textures/environment/moon_phases.png");

    private static final float R = 3f;
    private static final float S = 2f;

    // Padding UV prevent bleeding
    private static final float SUN_EPS = 1f / 16f;
    private static final float MOON_EPS_U = 1f / 64f;
    private static final float MOON_EPS_V = 1f / 32f;

    private static boolean FILTER_FIXED = false;

    @SubscribeEvent
    public static void render(RenderLevelStageEvent e) {
        Minecraft mc = Minecraft.getInstance();
        if (e.getStage() != RenderLevelStageEvent.Stage.AFTER_SKY) return; // render en la etapa del cielo
        if (mc.level == null || !mc.level.dimensionType().hasSkyLight()) return;

        ensureTextureFilters(mc);
        var cam = e.getCamera().getPosition();

        float sky = mc.level.getTimeOfDay(e.getPartialTick()); // cambia cada frame
        float sunDeg = sky * 360.0f;
        float moonDeg = (sky + 0.5f) * 360.0f;                 // 180° de desfase


        double cx = 8, cy = -50, cz = 8;

        // ===== Sun =====
        {
            PoseStack ps = e.getPoseStack();
            ps.pushPose();

            ps.translate(cx - cam.x(), cy - cam.y(), cz - cam.z());
            ps.mulPose(Axis.YP.rotationDegrees(-90));
            ps.mulPose(Axis.XP.rotationDegrees(sunDeg - 90));
            ps.translate(0.0, 0.0, R);
            ps.mulPose(Axis.XP.rotationDegrees(90));
            drawTexturedBillboardUV(ps, SUN_TEX, S, SUN_EPS, SUN_EPS, 1f - SUN_EPS, 1f - SUN_EPS);

            ps.popPose();
        }

        // ===== Moon =====
        {
            int phase = mc.level.getMoonPhase(); // 0..7
            int col = phase % 4, row = phase / 4;
            float du = 1f / 4f, dv = 1f / 2f;
            float u0 = col * du, v0 = row * dv, u1 = u0 + du, v1 = v0 + dv;

            PoseStack ps = e.getPoseStack();
            ps.pushPose();

            ps.translate(cx - cam.x(), cy - cam.y(), cz - cam.z());
            ps.mulPose(Axis.YP.rotationDegrees(-90));
            ps.mulPose(Axis.XP.rotationDegrees(moonDeg - 90)); // igual que el sol
            ps.translate(0.0, 0.0, R);
            ps.mulPose(Axis.XP.rotationDegrees(90));

            drawTexturedBillboardUV(ps, MOON_TEX, S,
                    u0 + MOON_EPS_U, v0 + MOON_EPS_V, u1 - MOON_EPS_U, v1 - MOON_EPS_V);

            ps.popPose();
        }
    }

    // ---------- Helpers (igual que antes) ----------

    private static void beginNoHaloState(ResourceLocation tex) {
        RenderSystem.depthMask(false);
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR);
        RenderSystem.disableCull();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, tex);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }

    private static void endNoHaloState() {
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
    }

    private static void drawTexturedBillboardUV(PoseStack ps, ResourceLocation tex, float size,
                                                float u0, float v0, float u1, float v1) {
        AbstractTexture t = Minecraft.getInstance().getTextureManager().getTexture(tex);
        if (t != null) t.setFilter(false, false);

        beginNoHaloState(tex);

        var mat = ps.last().pose();
        float s = size;

        Tesselator tess = Tesselator.getInstance();
        BufferBuilder buf = tess.getBuilder();
        buf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        // El quad está centrado; rotaciones ya aplicadas en la Pose (eje X)
        buf.vertex(mat, -s, 0f, s).uv(u0, v1).endVertex();
        buf.vertex(mat, s, 0f, s).uv(u1, v1).endVertex();
        buf.vertex(mat, s, 0f, -s).uv(u1, v0).endVertex();
        buf.vertex(mat, -s, 0f, -s).uv(u0, v0).endVertex();
        tess.end();

        endNoHaloState();
    }

    private static void ensureTextureFilters(Minecraft mc) {
        if (FILTER_FIXED) return;
        var tm = mc.getTextureManager();

        AbstractTexture s = tm.getTexture(SUN_TEX);
        if (s != null) s.setFilter(false, false);

        AbstractTexture m = tm.getTexture(MOON_TEX);
        if (m != null) m.setFilter(false, false);

        FILTER_FIXED = true;
    }
}