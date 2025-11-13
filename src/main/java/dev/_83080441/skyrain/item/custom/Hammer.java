package dev._83080441.skyrain.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Hammer} es una herramienta personalizada que extiende {@link DiggerItem} para comportarse
 * como un pico capaz de romper múltiples bloques a la vez (un área 3x3, 5x5, etc.).
 * <p>
 * Este tipo de herramienta es típica en mods que implementan "Mega Pickaxes" o "Hammers",
 * donde al romper un bloque, también se rompen los bloques adyacentes dentro de un rango.
 *
 * <h2>⚙️ Comportamiento principal</h2>
 * <ul>
 *   <li>El martillo funciona sobre bloques minables con pico ({@link BlockTags#MINEABLE_WITH_PICKAXE}).</li>
 *   <li>La orientación del área de destrucción depende de la cara del bloque que el jugador mire.</li>
 *   <li>Usa un "ray trace" (línea de visión) para determinar la dirección exacta del impacto.</li>
 * </ul>
 *
 * <h2>📦 Ejemplo de uso básico</h2>
 * <pre>{@code
 * // Crear un nuevo Hammer en el registro de ítems
 * public static final DeferredItem<Item> MONOLITH_HAMMER = ITEMS.register("monolith_hammer",
 *     () -> new Hammer(Tiers.NETHERITE, new Item.Properties().durability(2500)));
 * }</pre>
 *
 * <h2>🧮 Cómo funciona el área de rotura</h2>
 * <ul>
 *   <li>Si el jugador mira hacia arriba o abajo → área horizontal (plano XZ).</li>
 *   <li>Si el jugador mira hacia norte/sur → área vertical (plano XY).</li>
 *   <li>Si el jugador mira hacia este/oeste → área vertical (plano YZ).</li>
 * </ul>
 *
 * <h3>Ejemplo visual (rango 1):</h3>
 * Si el jugador mira hacia abajo y rompe el bloque central:
 * <pre>
 * [ ][X][ ]
 * [X][#][X]
 * [ ][X][ ]
 * </pre>
 * El bloque marcado con "#" es el inicial, y los "X" son los adicionales destruidos.
 *
 * <h2>⚠️ Notas importantes</h2>
 * <ul>
 *   <li>El método {@link #getBlocksToBeDestroyed(int, BlockPos, ServerPlayer)} <b>solo calcula las posiciones</b> — no destruye los bloques.</li>
 *   <li>Para aplicarlo en el juego, deberás recorrer la lista resultante y destruirlos manualmente.</li>
 *   <li>El método ignora líquidos y solo considera colisiones con bloques sólidos ({@link ClipContext.Block#COLLIDER}).</li>
 * </ul>
 *
 * <h3>Ejemplo de integración:</h3>
 * <pre>{@code
 * @Override
 * public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
 *     if (!level.isClientSide && entity instanceof ServerPlayer player) {
 *         List<BlockPos> toBreak = Hammer.getBlocksToBeDestroyed(1, pos, player);
 *         for (BlockPos target : toBreak) {
 *             level.destroyBlock(target, true, player);
 *         }
 *     }
 *     return super.mineBlock(stack, level, state, pos, entity);
 * }
 * }</pre>
 *
 * @author <PRIVATE_PERSON>
 * @version 1.0.0
 */
public class Hammer extends DiggerItem {

    /**
     * Crea un nuevo {@code Hammer}.
     *
     * @param tier       El {@link Tier} de la herramienta (por ejemplo, {@code Tiers.NETHERITE}, {@code Tiers.DIAMOND}).
     * @param properties Propiedades adicionales del ítem (durabilidad, rareza, etc.).
     */
    public Hammer(Tier tier, Properties properties) {
        super(tier, BlockTags.MINEABLE_WITH_PICKAXE, properties);
    }

    /**
     * Calcula todas las posiciones de bloques que deben ser destruidas junto con el bloque inicial
     * cuando se usa el martillo, basándose en la dirección del jugador.
     * <p>
     * Este método no destruye los bloques, solo devuelve sus coordenadas.
     *
     * @param range            Rango de destrucción (1 = área 3x3, 2 = área 5x5, etc.).
     * @param initalBlockPos   Posición del bloque original que el jugador golpeó.
     * @param player           Jugador que está usando el martillo.
     * @return Lista de {@link BlockPos} que representan los bloques dentro del área afectada.
     *
     * <h3>Ejemplo de uso:</h3>
     * <pre>{@code
     * List<BlockPos> targets = Hammer.getBlocksToBeDestroyed(1, pos, player);
     * for (BlockPos target : targets) {
     *     level.destroyBlock(target, true, player);
     * }
     * }</pre>
     */
    public static List<BlockPos> getBlocksToBeDestroyed(int range, BlockPos initalBlockPos, ServerPlayer player) {
        List<BlockPos> positions = new ArrayList<>();

        // Calcula hacia dónde está mirando el jugador mediante un ray trace (línea de visión)
        BlockHitResult traceResult = player.level().clip(new ClipContext(
                player.getEyePosition(1f),
                player.getEyePosition(1f).add(player.getViewVector(1f).scale(6f)),
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
        ));

        // Si no golpea ningún bloque, no hay nada que romper
        if (traceResult.getType() == HitResult.Type.MISS) {
            return positions;
        }

        // Dirección vertical (mirando arriba o abajo)
        if (traceResult.getDirection() == Direction.DOWN || traceResult.getDirection() == Direction.UP) {
            for (int x = -range; x <= range; x++) {
                for (int z = -range; z <= range; z++) {
                    positions.add(new BlockPos(
                            initalBlockPos.getX() + x,
                            initalBlockPos.getY(),
                            initalBlockPos.getZ() + z
                    ));
                }
            }
        }

        // Dirección norte/sur (mirando en eje Z)
        if (traceResult.getDirection() == Direction.NORTH || traceResult.getDirection() == Direction.SOUTH) {
            for (int x = -range; x <= range; x++) {
                for (int y = -range; y <= range; y++) {
                    positions.add(new BlockPos(
                            initalBlockPos.getX() + x,
                            initalBlockPos.getY() + y,
                            initalBlockPos.getZ()
                    ));
                }
            }
        }

        // Dirección este/oeste (mirando en eje X)
        if (traceResult.getDirection() == Direction.EAST || traceResult.getDirection() == Direction.WEST) {
            for (int z = -range; z <= range; z++) {
                for (int y = -range; y <= range; y++) {
                    positions.add(new BlockPos(
                            initalBlockPos.getX(),
                            initalBlockPos.getY() + y,
                            initalBlockPos.getZ() + z
                    ));
                }
            }
        }

        return positions;
    }
}