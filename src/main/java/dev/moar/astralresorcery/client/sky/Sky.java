package dev.moar.astralresorcery.client.sky;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.moar.astralresorcery.AstralReSorcery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = AstralReSorcery.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Sky {
    private static final ResourceLocation WHITE = ResourceLocation.withDefaultNamespace("textures/misc/white.png");

    @SubscribeEvent
    public static void render(RenderLevelStageEvent e) {
        if (e.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;

        Minecraft mc = Minecraft.getInstance();
        long ticks = mc.level.getDayTime();
        float sun = mc.level.getSunAngle(e.getPartialTick()); // en radianes
        if (ticks % 100 == 0) {
            mc.gui.getChat().addMessage(Component.literal("Ticks: " + ticks + " " + sun));
        }
        PoseStack ps = e.getPoseStack();
        MultiBufferSource.BufferSource buf = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer v = buf.getBuffer(RenderType.entityTranslucent(WHITE));

        // posición fija en el mundo
        var cam = e.getCamera().getPosition();
        double worldX = 8, worldY = -59, worldZ = 8;

        ps.pushPose();
        ps.translate(worldX - cam.x(), worldY - cam.y(), worldZ - cam.z());

        // ⭐️ 1) Llevar el origen al centro del quad (0.5, 0.5, 0)
        ps.translate(0.5, 0.5, 0.0);
        // ⭐️ 2) Rotar "como el sol": alrededor de X con el ángulo solar
        ps.mulPose(com.mojang.math.Axis.XP.rotation(sun));
        // (Opcional: si quieres que además “gire sobre sí mismo” en el plano, usa Z: Axis.ZP)
        // ps.mulPose(com.mojang.math.Axis.ZP.rotation(sun));
        // ⭐️ 3) Regresar el origen a la esquina inferior-izquierda del quad
        ps.translate(-0.5, -0.5, 0.0);

        var pose = ps.last().pose();
        var normalMat = ps.last().normal();
        int light = LightTexture.pack(15, 15); // fullbright
        int r = 255, g = 255, b = 255, a = 255;

        v.vertex(pose, 0, 0, 0)
                .color(r, g, b, a).uv(0, 0)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light).normal(normalMat, 0, 0, 1).endVertex();

        v.vertex(pose, 1, 0, 0)
                .color(r, g, b, a).uv(1, 0)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light).normal(normalMat, 0, 0, 1).endVertex();

        v.vertex(pose, 1, 1, 0)
                .color(r, g, b, a).uv(1, 1)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light).normal(normalMat, 0, 0, 1).endVertex();

        v.vertex(pose, 0, 1, 0)
                .color(r, g, b, a).uv(0, 1)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light).normal(normalMat, 0, 0, 1).endVertex();

        ps.popPose();
        buf.endBatch(RenderType.entityTranslucent(WHITE));
    }
}
