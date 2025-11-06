package dev._83080441.skyrain.block.components;

import dev._83080441.skyrain.SkyRain;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

/**
 * {@code ModDataComponents} registra todos los <b>Data Components</b> personalizados del mod {@link SkyRain}.
 * <p>
 * Los <b>Data Components</b> son una nueva forma en Minecraft (1.20.5+ y 1.21) de almacenar información
 * persistente en ítems, entidades, o bloques, reemplazando gradualmente el antiguo sistema de NBT.
 * <p>
 * A diferencia de las etiquetas NBT tradicionales, los Data Components usan tipos de datos
 * fuertemente tipados (por ejemplo, {@link BlockPos}, {@link Integer}, {@link String}, etc.)
 * y pueden ser serializados/deserializados automáticamente mediante {@code CODECs}.
 *
 * <h2>📦 ¿Qué hace esta clase?</h2>
 * Esta clase crea y registra un contenedor global de todos los {@link DataComponentType} utilizados
 * dentro del mod. Aquí puedes definir nuevos tipos de datos personalizados que se asocien a bloques
 * o ítems.
 *
 * <h3>Ejemplo:</h3>
 * <pre>{@code
 * // Guardar coordenadas en un ítem
 * itemStack.set(ModDataComponents.COORDINATES.get(), new BlockPos(10, 64, -5));
 *
 * // Leerlas más tarde
 * BlockPos pos = itemStack.get(ModDataComponents.COORDINATES.get());
 * }</pre>
 *
 * <h3>💡 Beneficios de Data Components</h3>
 * <ul>
 *   <li>Reemplazan NBT con tipos seguros.</li>
 *   <li>Fáciles de sincronizar entre servidor y cliente.</li>
 *   <li>Persisten en ítems, bloques o entidades.</li>
 *   <li>Compatibles con JSON y codecs (útiles en Datagen o configuración).</li>
 * </ul>
 *
 * <h3>🧩 Ubicación de registro:</h3>
 * Se deben registrar durante la inicialización del mod:
 * <pre>{@code
 * ModDataComponents.register(eventBus);
 * }</pre>
 *
 * Esto normalmente se hace dentro del constructor principal del mod:
 * <pre>{@code
 * public SkyRain(IEventBus modEventBus) {
 *     ModDataComponents.register(modEventBus);
 * }
 * }</pre>
 */
public class ModDataComponents {

    /**
     * Registro diferido de todos los {@link DataComponentType} del mod.
     * <p>
     * Usa el sistema de {@link DeferredRegister}, igual que con {@code ModBlocks} o {@code ModItems},
     * para garantizar que los componentes se registren en el momento adecuado del ciclo de carga.
     */
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE =
            DeferredRegister.createDataComponents(SkyRain.MOD_ID);

    /**
     * {@code COORDINATES} es un {@link DataComponentType} personalizado que almacena una
     * posición de bloque ({@link BlockPos}) en ítems o bloques.
     * <p>
     * Usa el {@code BlockPos.CODEC} para serializar/deserializar los datos correctamente.
     * <p>
     * Esto permite guardar, por ejemplo, la posición donde se colocó o usó un ítem.
     *
     * <h3>Ejemplo de uso:</h3>
     * <pre>{@code
     * // Guardar coordenadas en un ítem
     * ItemStack stack = new ItemStack(ModItems.WAND.get());
     * stack.set(ModDataComponents.COORDINATES.get(), new BlockPos(100, 70, -25));
     *
     * // Leer coordenadas después
     * BlockPos stored = stack.get(ModDataComponents.COORDINATES.get());
     * System.out.println("Posición almacenada: " + stored);
     * }</pre>
     */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> COORDINATES =
            register("coordinates", builder -> builder.persistent(BlockPos.CODEC));

    /**
     * Método genérico auxiliar para registrar un {@link DataComponentType} en el {@link DeferredRegister}.
     * <p>
     * Este método simplifica el registro permitiendo pasar un operador que configure el
     * {@link DataComponentType.Builder} antes de construir el tipo final.
     *
     * @param name             Nombre interno del componente (ej. "coordinates").
     * @param builderOperator  Función que modifica el builder (por ejemplo, agregando persistencia o codec).
     * @param <T>              Tipo de dato que almacenará el componente (por ejemplo {@link BlockPos} o {@link Integer}).
     * @return                 Un {@link DeferredHolder} que contiene el {@link DataComponentType} registrado.
     *
     * <h3>Ejemplo de registro personalizado:</h3>
     * <pre>{@code
     * public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CHARGE =
     *      register("charge", builder -> builder.persistent(Codec.INT));
     * }</pre>
     */
    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(
            String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator
    ) {
        return DATA_COMPONENT_TYPE.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    /**
     * Registra todos los {@link DataComponentType} definidos en esta clase en el {@link IEventBus}.
     * <p>
     * Debe llamarse durante la inicialización del mod, normalmente dentro del constructor
     * principal o durante el evento {@code FMLCommonSetupEvent}.
     *
     * @param eventBus Bus de eventos del mod (generalmente {@code FMLJavaModLoadingContext.get().getModEventBus()}).
     *
     * <h3>Ejemplo:</h3>
     * <pre>{@code
     * public SkyRain() {
     *     IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
     *     ModDataComponents.register(bus);
     * }
     * }</pre>
     */
    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPE.register(eventBus);
    }
}