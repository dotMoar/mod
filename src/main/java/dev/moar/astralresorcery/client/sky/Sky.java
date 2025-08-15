package dev.moar.astralresorcery.client.sky;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import dev.moar.astralresorcery.AstralReSorcery;
import dev.moar.astralresorcery.helper.render.Billboards;
import dev.moar.astralresorcery.helper.render.Hud;
import dev.moar.astralresorcery.helper.render.Quad;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AstralReSorcery.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Sky {

    private static final double CX = 8, CY = -50, CZ = 8;

    // Texturas vanilla
    private static final ResourceLocation SUN_TEX = ResourceLocation.withDefaultNamespace("textures/environment/sun.png");
    private static final ResourceLocation MOON_TEX = ResourceLocation.withDefaultNamespace("textures/environment/moon_phases.png");

    /**
     * RADIO
     **/
    private static final float R = 3f;
    /**
     * SIZE
     **/
    private static final float S = 2f;
    /**
     * SUN SPRITE FILTER
     **/
    private static final float SUN_EPS = 1f / 16f;
    /**
     * MOON SPRITE FILTER
     **/
    private static final float MOON_EPS_U = 1f / 64f;
    /**
     * MOON SPRITE V FILTER
     **/
    private static final float MOON_EPS_V = 1f / 32f;
    /**
     * ROTATION SPEED DEBUG
     **/
    private static final float SPEED_MULT = 200f; // “luna nueva rápida”
    /**
     * DEG SUN ECLIPSE TOLENCE
     **/
    private static final float ECLIPSE_TOL = 5f; // entra
    /**
     * DEG SUN ECLIPSE AFTER RESET
     **/
    private static final float ECLIPSE_RESET = 8f; // sale
    /**
     * DARKNESS ECLIPSE MIN
     **/
    private static final float MIN_LIGHT = 0.05f;  // nunca negro total
    /**
     * SIZE OVERLAY DARKNESS
     **/
    private static final float OVERLAY_SIZE = 5000f; // quad gigante frente a cámara
    /**
     *
     **/
    private static boolean eclipse = false;
    /**
     *
     **/
    private static float lastDiff = 999f;
    /**
     *
     **/
    private static boolean DEBUG_HUD = true;   // toggle HUD
    /**
     *
     **/
    private static boolean FOG_HOOK = true;   // aplicar hook de fog/sky color
    /**
     *
     **/
    private static float overlayStrengthBias = 1.0f; // 1=normal, >1 más oscuro, <1 más suave

    public static void setDebugHud(boolean enable) {
        DEBUG_HUD = enable;
    }

    public static void setFogHookEnabled(boolean enable) {
        FOG_HOOK = enable;
    }

    public static void setOverlayStrength(float bias) {
        overlayStrengthBias = Math.max(0f, bias);
    }

    @SubscribeEvent
    public static void render(RenderLevelStageEvent e) {
        final Minecraft mc = Minecraft.getInstance();
        if (e.getStage() != RenderLevelStageEvent.Stage.AFTER_SKY) return;
        if (mc.level == null || !mc.level.dimensionType().hasSkyLight()) return;

        Billboards.ensureTextureFilters(mc, SUN_TEX, MOON_TEX);

        final float pt = e.getPartialTick();
        var cam = e.getCamera().getPosition();

        // ---- cálculos base (ángulos) ----
        float sky = mc.level.getTimeOfDay(pt);
        float sunDeg = sky * 360.0f;
        float moonDeg = (sky + 0.5f) * 360.0f;
        float newMoonDeg = (sky * 360.0f * SPEED_MULT) % 360.0f;

        // ---- eclipse ----
        boolean isDaylight = isDaylight(sunDeg);
        float diff = angularDiffWrapped(newMoonDeg, sunDeg);

        boolean entering = isDaylight && diff <= ECLIPSE_TOL && !eclipse;
        if (mc.player != null && entering) {
            mc.player.sendSystemMessage(Component.literal("§6¡Eclipse!"));
        }
        eclipse = computeEclipseState(isDaylight, diff, eclipse);
        lastDiff = diff;

        // ---- render ----
        PoseStack ps = e.getPoseStack();

        // Sol
        pushTransform(ps, cam.x, cam.y, cam.z, sunDeg, R);
        Billboards.drawSunBillboard(ps, S, SUN_EPS, SUN_TEX);
        ps.popPose();

        // Luna
        var uv = moonPhaseUV(mc.level.getMoonPhase());
        pushTransform(ps, cam.x, cam.y, cam.z, moonDeg, R);
        Billboards.drawMoonBillboard(ps, S, uv.u0, uv.v0, uv.u1, uv.v1, MOON_TEX);
        ps.popPose();

        // Luna acelerada
        pushTransform(ps, cam.x, cam.y, cam.z, newMoonDeg, R - 0.1f);
        Billboards.drawMoonBillboard(ps, S - 0.5f, uv.u0, uv.v0, uv.u1, uv.v1, MOON_TEX);
        ps.popPose();

        // eclipse negro progresivo
        pushTransform(ps, cam.x, cam.y, cam.z, newMoonDeg, R - 1f);
        ps.mulPose(Axis.XP.rotationDegrees(90));
        drawBlackMoonObject(ps, mc, S - 1f, uv.u0, uv.v0, uv.u1, uv.v1, -0.001f, diff);
        ps.popPose();

        if (DEBUG_HUD) {
            Hud.renderHudLine(ps, cam, "Sky: " + sky, 0, CX, CY, CZ);
            Hud.renderHudLine(ps, cam, "Sun°: " + sunDeg, 1, CX, CY, CZ);
            Hud.renderHudLine(ps, cam, "Moon°: " + moonDeg, 2, CX, CY, CZ);
            Hud.renderHudLine(ps, cam, "NewMoon°: " + newMoonDeg, 3, CX, CY, CZ);
            Hud.renderHudLine(ps, cam, "Eclipse=" + eclipse + " diff=" + diff, 4, CX, CY, CZ);
        }

        applyDarkeningOverlay(ps, mc, pt, eclipse, diff);
    }

    @SubscribeEvent
    public static void onFogColor(ViewportEvent.ComputeFogColor e) {
        if (!FOG_HOOK || !eclipse) return;
        float perc = computeAstralPerc(lastDiff); // 0.05..1.0 (1=sin oscurecer)
        e.setRed(e.getRed() * perc);
        e.setGreen(e.getGreen() * perc);
        e.setBlue(e.getBlue() * perc);
    }

    private static boolean computeEclipseState(boolean isDay, float diff, boolean current) {
        if (isDay && diff <= ECLIPSE_TOL) return true;
        if (!isDay || diff >= ECLIPSE_RESET) return false;
        return current;
    }

    private static boolean isDaylight(float sunDeg) {
        return (sunDeg >= 285f || sunDeg <= 105f);
    }

    private static float angularDiffWrapped(float aDeg, float bDeg) {
        float x = (((aDeg - bDeg) + 180f) % 360f + 360f) % 360f - 180f;
        return Math.abs(x);
    }

    private static float computeAstralPerc(float diffDeg) {
        float t = 1.0f - clamp01(diffDeg / ECLIPSE_RESET);
        float base = MIN_LIGHT + (1f - MIN_LIGHT) * (1f - t);
        float biased = 1f - clamp01((1f - base) * overlayStrengthBias);

        return clamp01(biased);
    }

    private static float clamp01(float v) {
        return Math.max(0f, Math.min(1f, v));
    }

    private static void pushTransform(PoseStack ps, double camX, double camY, double camZ, float deg, float radius) {
        ps.pushPose();
        ps.translate(CX - camX, CY - camY, CZ - camZ);
        ps.mulPose(Axis.YP.rotationDegrees(-90));
        ps.mulPose(Axis.XP.rotationDegrees(deg - 90));
        ps.translate(0.0, 0.0, radius);
        ps.mulPose(Axis.XP.rotationDegrees(90));
    }

    private static MoonUV moonPhaseUV(int phase) {
        int col = phase % 4, row = phase / 4;
        float du = 1f / 4f, dv = 1f / 2f;

        float u0 = col * du + MOON_EPS_U;
        float v0 = row * dv + MOON_EPS_V;
        float u1 = (col + 1) * du - MOON_EPS_U;
        float v1 = (row + 1) * dv - MOON_EPS_V;
        return new MoonUV(u0, v0, u1, v1);
    }

    private record MoonUV(float u0, float v0, float u1, float v1) {
    }

    private static void applyDarkeningOverlay(PoseStack ps, Minecraft mc, float pt, boolean isEclipse, float diffDeg) {
        if (mc.level == null || !isEclipse) return;

        float perc = computeAstralPerc(diffDeg);
        float alpha = Math.max(0f, Math.min(1f, 1f - perc));
        if (alpha <= 0f) return;

        int a255 = Math.round(alpha * 255f);
        var cam = mc.gameRenderer.getMainCamera();

        ps.pushPose();
        ps.translate(cam.getPosition().x, cam.getPosition().y, cam.getPosition().z);
        ps.mulPose(Axis.YP.rotationDegrees(-cam.getYRot()));
        ps.mulPose(Axis.XP.rotationDegrees(cam.getXRot()));
        ps.translate(0.0, 0.0, -1.0);

        var buffers = mc.renderBuffers().bufferSource();

        VertexConsumer vc = buffers.getBuffer(RenderType.guiOverlay());

        float w = OVERLAY_SIZE * 2.0f;
        float h = OVERLAY_SIZE * 2.0f;

        Quad.emitQuadCentered(ps, vc, Quad.FULL_BRIGHT, new Quad.Color(0, 0, 0, a255), w, h);
        buffers.endBatch(RenderType.guiOverlay());

        ps.popPose();
    }

    private static void drawBlackMoonObject(PoseStack ps, Minecraft mc, float size, float u0, float v0, float u1, float v1, float zOffsetTowardsCamera, float diffDeg) {
        var buffers = mc.renderBuffers().bufferSource();
        var vc = buffers.getBuffer(RenderType.entityTranslucentCull(MOON_TEX));

        if (zOffsetTowardsCamera != 0f) {
            ps.translate(0.0, 0.0, zOffsetTowardsCamera);
        }

        // Calcula opacidad en base a la cercanía del centro del eclipse
        float perc = computeAstralPerc(diffDeg); // 1.0 = sin eclipse, MIN_LIGHT = máximo eclipse
        float alpha = Math.max(0f, Math.min(1f, 1f - perc)); // 0 → invisible, 1 → negro sólido

        int a255 = Math.round(alpha * 255f);

        Quad.emitQuadCenteredUV(ps, vc, Quad.FULL_BRIGHT,
                new Quad.Color(0, 0, 0, a255), // negro con alpha dinámico
                size, size, u0, v0, u1, v1);

        buffers.endBatch(RenderType.entityTranslucentCull(MOON_TEX));
        if (zOffsetTowardsCamera != 0f) {
            ps.translate(0.0, 0.0 , zOffsetTowardsCamera);
        }
    }
}