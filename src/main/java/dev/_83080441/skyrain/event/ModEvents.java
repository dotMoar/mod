package dev._83080441.skyrain.event;

import dev._83080441.skyrain.SkyRain;
import dev._83080441.skyrain.item.custom.Hammer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * {@code ModEvents} maneja los eventos globales del juego relacionados con el mod,
 * suscribiéndose al bus de eventos de NeoForge.
 * <p>
 * En particular, esta clase implementa la lógica para que un {@link Hammer}
 * rompa múltiples bloques simultáneamente cuando el jugador destruye uno.
 *
 * <h2>🧠 Funcionamiento general</h2>
 * <ul>
 *   <li>Detecta cuando un jugador rompe un bloque ({@link BlockEvent.BreakEvent}).</li>
 *   <li>Verifica si el jugador sostiene un {@link Hammer} en la mano principal.</li>
 *   <li>Obtiene el área de bloques a romper usando {@link Hammer#getBlocksToBeDestroyed(int, BlockPos, ServerPlayer)}.</li>
 *   <li>Rompe cada bloque del área de manera segura, evitando bucles infinitos o dobles rompimientos.</li>
 * </ul>
 *
 * <h2>⚙️ Registro en el bus de eventos</h2>
 * La anotación {@link EventBusSubscriber} con {@code bus = EventBusSubscriber.Bus.GAME}
 * indica que esta clase escucha eventos del bus principal del juego (no de FML o cliente).
 * <pre>
 * @EventBusSubscriber(modid = SkyRain.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
 * </pre>
 *
 * <h3>⚡ Ejemplo: comportamiento esperado</h3>
 * <ul>
 *   <li>Si el jugador tiene un {@code Hammer} y rompe una piedra mirando al suelo, se romperán los 9 bloques del área 3x3.</li>
 *   <li>Si el jugador mira hacia una pared, el martillo romperá en plano vertical (3x3) sobre esa superficie.</li>
 * </ul>
 *
 * <h3>💡 Seguridad</h3>
 * Se usa una lista temporal {@link #HARVESTED_BLOCKS} para registrar los bloques que ya se están destruyendo,
 * evitando que el evento vuelva a dispararse recursivamente mientras los rompe.
 *
 * @author <PRIVATE_PERSON>
 * @version 1.0.0
 */
@EventBusSubscriber(modid = SkyRain.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {

    /**
     * Conjunto de bloques que ya han sido destruidos durante la ejecución del evento,
     * usado para prevenir recursividad o rompimientos duplicados.
     */
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    /**
     * Evento que se dispara cada vez que un bloque es destruido por un jugador.
     * Si el jugador sostiene un {@link Hammer}, calcula los bloques adyacentes y los destruye también.
     *
     * @param event Evento {@link BlockEvent.BreakEvent} lanzado automáticamente por NeoForge
     *              cada vez que un bloque es roto por un jugador.
     *
     *              <h3>Ejemplo de flujo:</h3>
     *              <ol>
     *                <li>El jugador rompe un bloque con el martillo.</li>
     *                <li>Este método detecta el {@code BreakEvent}.</li>
     *                <li>Obtiene el área afectada mediante {@link Hammer#getBlocksToBeDestroyed(int, BlockPos, ServerPlayer)}.</li>
     *                <li>Llama a {@code serverPlayer.gameMode.destroyBlock(pos)} para cada bloque dentro del área.</li>
     *                <li>Evita bucles infinitos registrando temporalmente cada bloque en {@link #HARVESTED_BLOCKS}.</li>
     *              </ol>
     *
     *              <h3>Consideraciones:</h3>
     *              <ul>
     *                <li>Solo funciona del lado del servidor ({@link ServerPlayer}).</li>
     *                <li>Solo rompe bloques válidos para el tipo de herramienta del martillo.</li>
     *                <li>El bloque original no se repite, se salta explícitamente.</li>
     *              </ul>
     */
    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        // Solo se ejecuta si el jugador sostiene un Hammer y está en el lado del servidor
        if (mainHandItem.getItem() instanceof Hammer hammer && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();

            // Si este bloque ya se está procesando, lo ignoramos (previene recursión infinita)
            if (HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            // Calcula los bloques adicionales a destruir según la dirección de la mirada del jugador
            for (BlockPos pos : Hammer.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {

                // Omite el bloque inicial y los que no son minables por el martillo
                if (pos.equals(initialBlockPos) || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
                }

                // Marca el bloque como en proceso
                HARVESTED_BLOCKS.add(pos);

                // Destruye el bloque del área adicional
                serverPlayer.gameMode.destroyBlock(pos);

                // Lo elimina del set para permitir futuras destrucciones
                HARVESTED_BLOCKS.remove(pos);
            }
        }
    }
}