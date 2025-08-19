package dev.moar.astralresorcery.helper.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;

import java.awt.*;

public class Quad {

    public static final int FULL_BRIGHT = LightTexture.pack(15, 15);

    public record Color(int r, int g, int b, int a) {
        public static final Color WHITE = new Color(255, 255, 255, 255);
        public static final Color BLACK = new Color(0, 0, 0, 255);

        public static Color fromHexARGB(int argb) {
            int a = (argb >>> 24) & 0xFF;
            int r = (argb >>> 16) & 0xFF;
            int g = (argb >>> 8) & 0xFF;
            int b = (argb) & 0xFF;
            return new Color(r, g, b, a);
        }
    }

    public static void emitQuad(PoseStack ps, VertexConsumer v, int light, Color c) {
        var pose = ps.last().pose();
        var normal = ps.last().normal();
        v.vertex(pose, 0, 0, 0)
                .color(c.r, c.g, c.b, c.a)
                .uv(0, 0)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(normal, 0, 0, 1)
                .endVertex();
        v.vertex(pose, 1, 0, 0)
                .color(c.r, c.g, c.b, c.a).uv(1, 0)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light).normal(normal, 0, 0, 1)
                .endVertex();
        v.vertex(pose, 1, 1, 0)
                .color(c.r, c.g, c.b, c.a)
                .uv(1, 1)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(normal, 0, 0, 1)
                .endVertex();
        v.vertex(pose, 0, 1, 0)
                .color(c.r, c.g, c.b, c.a)
                .uv(0, 1)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(normal, 0, 0, 1)
                .endVertex();
    }


    public static void emitQuadUV(PoseStack ps, VertexConsumer v, int light, Color c,
                                  float u0, float v0, float u1, float v1) {
        var pose = ps.last().pose();
        var normal = ps.last().normal();

        v.vertex(pose, 0, 0, 0).color(c.r, c.g, c.b, c.a).uv(u0, v0)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
        v.vertex(pose, 1, 0, 0).color(c.r, c.g, c.b, c.a).uv(u1, v0)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
        v.vertex(pose, 1, 1, 0).color(c.r, c.g, c.b, c.a).uv(u1, v1)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
        v.vertex(pose, 0, 1, 0).color(c.r, c.g, c.b, c.a).uv(u0, v1)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
    }

    /**
     * Centrado en el origen: de -w/2..+w/2 y -h/2..+h/2 (ideal para rotar/orbitar). UVs 0..1
     */
    public static void emitQuadCentered(PoseStack ps, VertexConsumer v, int light, Color c,
                                        float w, float h) {
        var pose = ps.last().pose();
        var normal = ps.last().normal();
        float hw = w * 0.5f, hh = h * 0.5f;

        v.vertex(pose, -hw, -hh, 0).color(c.r, c.g, c.b, c.a).uv(0, 0)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
        v.vertex(pose, +hw, -hh, 0).color(c.r, c.g, c.b, c.a).uv(1, 0)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
        v.vertex(pose, +hw, +hh, 0).color(c.r, c.g, c.b, c.a).uv(1, 1)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
        v.vertex(pose, -hw, +hh, 0).color(c.r, c.g, c.b, c.a).uv(0, 1)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
    }

    public static void emitQuadCenteredUV(PoseStack ps, VertexConsumer v, int light, Color c,
                                          float w, float h,
                                          float u0, float v0, float u1, float v1) {
        var pose = ps.last().pose();
        var normal = ps.last().normal();
        float hw = w * 0.5f, hh = h * 0.5f;

        v.vertex(pose, -hw, -hh, 0).color(c.r, c.g, c.b, c.a).uv(u0, v0)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
        v.vertex(pose, +hw, -hh, 0).color(c.r, c.g, c.b, c.a).uv(u1, v0)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
        v.vertex(pose, +hw, +hh, 0).color(c.r, c.g, c.b, c.a).uv(u1, v1)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
        v.vertex(pose, -hw, +hh, 0).color(c.r, c.g, c.b, c.a).uv(u0, v1)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
    }

    public static void emitCubeDoubleSided(PoseStack ps, VertexConsumer vc, Quad.Color color, float size) {
        float half = size / 2f;

        // === Z+ (frente) ===
        ps.pushPose();
        ps.translate(0, 0, half);
        Quad.emitQuadCentered(ps, vc, Quad.FULL_BRIGHT, color, size, size); // exterior
        ps.mulPose(Axis.YP.rotationDegrees(180));
        Quad.emitQuadCentered(ps, vc, Quad.FULL_BRIGHT, color, size, size); // interior
        ps.popPose();

        // === Z- (atrás) ===
        ps.pushPose();
        ps.translate(0, 0, -half);
        ps.mulPose(Axis.YP.rotationDegrees(180));
        Quad.emitQuadCentered(ps, vc, Quad.FULL_BRIGHT, color, size, size); // exterior
        ps.mulPose(Axis.YP.rotationDegrees(180));
        Quad.emitQuadCentered(ps, vc, Quad.FULL_BRIGHT, color, size, size); // interior
        ps.popPose();

        // === X+ (derecha) ===
        ps.pushPose();
        ps.mulPose(Axis.YP.rotationDegrees(90));
        ps.translate(0, 0, half);
        Quad.emitQuadCentered(ps, vc, Quad.FULL_BRIGHT, color, size, size); // exterior
        ps.mulPose(Axis.YP.rotationDegrees(180));
        Quad.emitQuadCentered(ps, vc, Quad.FULL_BRIGHT, color, size, size); // interior
        ps.popPose();

        // === X- (izquierda) ===
        ps.pushPose();
        ps.mulPose(Axis.YP.rotationDegrees(-90));
        ps.translate(0, 0, half);
        Quad.emitQuadCentered(ps, vc, Quad.FULL_BRIGHT, color, size, size); // exterior
        ps.mulPose(Axis.YP.rotationDegrees(180));
        Quad.emitQuadCentered(ps, vc, Quad.FULL_BRIGHT, color, size, size); // interior
        ps.popPose();

        // === Y+ (arriba) ===
        ps.pushPose();
        ps.mulPose(Axis.XP.rotationDegrees(-90));
        ps.translate(0, 0, half);
        Quad.emitQuadCentered(ps, vc, Quad.FULL_BRIGHT, color, size, size); // exterior
        ps.mulPose(Axis.YP.rotationDegrees(180));
        Quad.emitQuadCentered(ps, vc, Quad.FULL_BRIGHT, color, size, size); // interior
        ps.popPose();

        // === Y- (abajo) ===
        ps.pushPose();
        ps.mulPose(Axis.XP.rotationDegrees(90));
        ps.translate(0, 0, half);
        Quad.emitQuadCentered(ps, vc, Quad.FULL_BRIGHT, color, size, size); // exterior
        ps.mulPose(Axis.YP.rotationDegrees(180));
        Quad.emitQuadCentered(ps, vc, Quad.FULL_BRIGHT, color, size, size); // interior
        ps.popPose();
    }
}
