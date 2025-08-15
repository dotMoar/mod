package dev.moar.astralresorcery.helper.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;

public class Hud {

    public static void renderHudLine(PoseStack ps, net.minecraft.world.phys.Vec3 cam, String label, int index, double CX, double CY, double CZ) {
        if (label == null) return;
        Font font = Minecraft.getInstance().font;

        ps.pushPose();
        ps.translate(CX - cam.x, (CY - 2.0) - (index * 0.12), CZ - cam.z);

        float scale = 0.01f;
        ps.scale(-scale, -scale, scale);

        float yaw = Minecraft.getInstance().gameRenderer.getMainCamera().getYRot();
        float pitch = Minecraft.getInstance().gameRenderer.getMainCamera().getXRot();
        ps.mulPose(Axis.YP.rotationDegrees(yaw));
        ps.mulPose(Axis.XP.rotationDegrees(pitch));

        int textWidth = font.width(label);
        font.drawInBatch(
                Component.literal(label),
                -textWidth / 2f, 0,
                0xFFFFFF,
                false,
                ps.last().pose(),
                Minecraft.getInstance().renderBuffers().bufferSource(),
                Font.DisplayMode.NORMAL,
                0, 15728880
        );
        ps.popPose();
    }
}
