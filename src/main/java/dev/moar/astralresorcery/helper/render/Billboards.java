package dev.moar.astralresorcery.helper.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;

public class Billboards {
    private static boolean FILTER_FIXED = false;

    public static void drawSunBillboard(PoseStack ps, float size, float epsUV, ResourceLocation SUN_TEX) {
        drawTexturedBillboardUV(ps, SUN_TEX, size, epsUV, epsUV, 1f - epsUV, 1f - epsUV);
    }

    public static void drawMoonBillboard(PoseStack ps, float size, float u0, float v0, float u1, float v1, ResourceLocation MOON_TEX) {
        drawTexturedBillboardUV(ps, MOON_TEX, size, u0, v0, u1, v1);
    }

    public static void drawTexturedBillboardUV(PoseStack ps, ResourceLocation tex, float size,
                                                float u0, float v0, float u1, float v1) {
        AbstractTexture t = Minecraft.getInstance().getTextureManager().getTexture(tex);
        if (t != null) t.setFilter(false, false);

        beginNoHaloState(tex);

        var mat = ps.last().pose();
        float s = size;

        Tesselator tess = Tesselator.getInstance();
        BufferBuilder buf = tess.getBuilder();
        buf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        buf.vertex(mat, -s, 0f, s).uv(u0, v1).endVertex();
        buf.vertex(mat, s, 0f, s).uv(u1, v1).endVertex();
        buf.vertex(mat, s, 0f, -s).uv(u1, v0).endVertex();
        buf.vertex(mat, -s, 0f, -s).uv(u0, v0).endVertex();
        tess.end();

        endNoHaloState();
    }

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

    public static void ensureTextureFilters(Minecraft mc, ResourceLocation SUN_TEX, ResourceLocation MOON_TEX) {
        if (FILTER_FIXED) return;
        var tm = mc.getTextureManager();

        AbstractTexture s = tm.getTexture(SUN_TEX);
        if (s != null) s.setFilter(false, false);

        AbstractTexture m = tm.getTexture(MOON_TEX);
        if (m != null) m.setFilter(false, false);

        FILTER_FIXED = true;
    }

}
