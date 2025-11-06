package dev._83080441.skyrain.datagen;

import dev._83080441.skyrain.SkyRain;
import dev._83080441.skyrain.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

/**
 * {@code ModBlockStateProvider} se encarga de generar automáticamente
 * los archivos de modelos y blockstates (definiciones de variantes) para
 * todos los bloques del mod {@link SkyRain}.
 * <p>
 * Esta clase utiliza el sistema de <b>Data Generation</b> de NeoForge
 * para crear los archivos en <code>/generated/resources/assets/skyrain/</code>.
 * <p>
 * Cada método genera modelos y estados para diferentes tipos de bloques
 * (cubos simples, puertas, escaleras, botones, lámparas, etc.).
 *
 * <h2> ¿Qué es un "Data Generator"?</h2>
 * Los Data Generators en Minecraft crean automáticamente archivos JSON
 * (modelos, recetas, loot tables, tags...) evitando tener que escribirlos a mano.
 * Esto es especialmente útil para mods grandes.
 *
 * <h3> Archivos generados:</h3>
 * <ul>
 *     <li><b>blockstates/</b> → Define cómo se renderizan las variantes de un bloque.</li>
 *     <li><b>models/block/</b> → Define los modelos 3D (cubos, puertas, slabs...).</li>
 *     <li><b>models/item/</b> → Define el modelo del ítem en inventario/mano.</li>
 * </ul>
 *
 * <h3>Ejemplo de uso:</h3>
 * <pre>{@code
 * ModBlockStateProvider provider = new ModBlockStateProvider(output, helper);
 * provider.blockWithItem(ModBlocks.MAGIC_BLOCK);
 * provider.lampBlock(ModBlocks.MONOLITH_LAMP);
 * }</pre>
 * <p>
 * Esto generará automáticamente:
 * <ul>
 *     <li>blockstates/magic_block.json</li>
 *     <li>models/block/magic_block.json</li>
 *     <li>models/item/magic_block.json</li>
 *     <li>blockstates/monolith_lamp.json (con variantes on/off)</li>
 *     <li>models/block/monolith_lamp_off.json</li>
 *     <li>models/block/monolith_lamp_on.json</li>
 *     <li>models/item/monolith_lamp.json</li>
 * </ul>
 */
public class ModBlockStateProvider extends BlockStateProvider {

    /**
     * Constructor principal del generador de estados de bloque.
     *
     * @param output       El destino donde se escriben los archivos generados (provisto por el sistema de datagen).
     * @param exFileHelper Un ayudante que permite verificar si ciertos archivos existen antes de sobrescribirlos.
     */
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, SkyRain.MOD_ID, exFileHelper);
    }

    /**
     * Método principal del generador de modelos y estados.
     * Aquí se registran todos los bloques y sus modelos.
     * Este método se ejecuta automáticamente cuando corres la tarea
     * {@code runData} o {@code runClient} con la configuración de datagen.
     */
    @Override
    protected void registerStatesAndModels() {
        // Bloques cúbicos simples
        blockWithItem(ModBlocks.CINNABAR_BLOCK);
        blockWithItem(ModBlocks.MONOLITH);
        blockWithItem(ModBlocks.MAGIC_BLOCK);

        // Bloques con geometrías complejas y modelos especiales
        stairsBlock(ModBlocks.MONOLITH_STAIR.get(), blockTexture(ModBlocks.MONOLITH.get()));
        slabBlock(ModBlocks.MONOLITH_SLAB.get(), blockTexture(ModBlocks.MONOLITH.get()), blockTexture(ModBlocks.MONOLITH.get()));
        buttonBlock(ModBlocks.MONOLITH_BUTTON.get(), blockTexture(ModBlocks.MONOLITH.get()));
        pressurePlateBlock(ModBlocks.MONOLITH_PRESSURE.get(), blockTexture(ModBlocks.MONOLITH.get()));
        fenceBlock(ModBlocks.MONOLITH_FENCE.get(), blockTexture(ModBlocks.MONOLITH.get()));
        fenceGateBlock(ModBlocks.MONOLITH_FENCE_GATE.get(), blockTexture(ModBlocks.MONOLITH.get()));
        wallBlock(ModBlocks.MONOLITH_WALL.get(), blockTexture(ModBlocks.MONOLITH.get()));

        // Puerta y trampilla usan renderizado cutout (transparencias)
        doorBlockWithRenderType(ModBlocks.MONOLITH_DOOR.get(),
                modLoc("block/monolith_door_bottom"),
                modLoc("block/monolith_door_top"),
                "cutout");

        trapdoorBlockWithRenderType(ModBlocks.MONOLITH_TRAPDOOR.get(),
                modLoc("block/monolith_trapdoor"),
                true,
                "cutout");

        // Generación de modelos de ítem correspondientes
        blockItem(ModBlocks.MONOLITH_STAIR);
        blockItem(ModBlocks.MONOLITH_SLAB);
        blockItem(ModBlocks.MONOLITH_PRESSURE);
        blockItem(ModBlocks.MONOLITH_FENCE_GATE);
        blockItem(ModBlocks.MONOLITH_TRAPDOOR, "_bottom");

        // Bloque interactivo de lámpara (posee variantes on/off)
        lampBlock(ModBlocks.MONOLITH_LAMP);
        blockItem(ModBlocks.MONOLITH_LAMP, "_off");
    }

    /**
     * Genera un bloque simple (cubos sólidos, sin estados adicionales)
     * junto con su modelo de ítem correspondiente.
     *
     * @param deferredBlock Bloque registrado en {@link ModBlocks} a generar.
     *
     *                      <h3>Ejemplo:</h3>
     *                      <pre>{@code
     *                      blockWithItem(ModBlocks.MAGIC_BLOCK);
     *                      }</pre>
     *                      Genera:
     *                      <ul>
     *                          <li>blockstates/magic_block.json</li>
     *                          <li>models/block/magic_block.json</li>
     *                          <li>models/item/magic_block.json</li>
     *                      </ul>
     */
    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    /**
     * Genera el modelo de ítem asociado a un bloque simple (sin sufijos).
     * <p>
     * Este método se usa cuando el modelo de ítem comparte nombre exacto
     * con el modelo de bloque.
     *
     * @param deferredBlock Bloque a generar su modelo de ítem.
     */
    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(),
                new ModelFile.UncheckedModelFile("skyrain:block/" + deferredBlock.getId().getPath()));
    }

    /**
     * Variante de {@link #blockItem(DeferredBlock)} que permite agregar
     * un sufijo (appendix) al nombre del modelo de bloque.
     * <p>
     * Muy útil para bloques que tienen variantes (por ejemplo: puertas o lámparas),
     * donde el modelo visible en el inventario corresponde a un estado específico.
     *
     * @param deferredBlock Bloque a generar.
     * @param appendix      Sufijo del modelo (por ejemplo "_bottom" o "_off").
     *
     *                      <h3>Ejemplo:</h3>
     *                      <pre>{@code
     *                      blockItem(ModBlocks.MONOLITH_LAMP, "_off");
     *                      }</pre>
     *                      Genera:
     *                      <ul>
     *                          <li>models/item/monolith_lamp.json → parent: skyrain:block/monolith_lamp_off</li>
     *                      </ul>
     */
    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(),
                new ModelFile.UncheckedModelFile("skyrain:block/" + deferredBlock.getId().getPath() + appendix));
    }

    /**
     * Genera un bloque tipo lámpara con dos estados visuales: encendido y apagado.
     * <p>
     * Este método crea automáticamente dos modelos 3D:
     * <ul>
     *     <li>{@code models/block/<nombre>_off.json}</li>
     *     <li>{@code models/block/<nombre>_on.json}</li>
     * </ul>
     * Y un archivo {@code blockstates/<nombre>.json} que alterna entre ellos
     * según el valor de la propiedad {@code CLICK}.
     *
     * @param block Bloque tipo lámpara (debe tener una propiedad Boolean llamada "CLICK").
     *
     *              <h3>Ejemplo de uso:</h3>
     *              <pre>{@code
     *              lampBlock(ModBlocks.MONOLITH_LAMP);
     *              }</pre>
     *              <h3>Archivos generados:</h3>
     *              <ul>
     *                  <li><b>models/block/monolith_lamp_off.json</b> → Textura apagada</li>
     *                  <li><b>models/block/monolith_lamp_on.json</b> → Textura encendida</li>
     *                  <li><b>blockstates/monolith_lamp.json</b> → Cambia modelo según propiedad "clicked"</li>
     *              </ul>
     *
     *              <h3>Propiedad requerida en el bloque:</h3>
     *              <pre>{@code
     *              public static final BooleanProperty CLICK = BooleanProperty.create("clicked");
     *              }</pre>
     *
     *              <h3>Ejemplo en un bloque:</h3>
     *              <pre>{@code
     *              public class MonolithLamp extends Block {
     *                  public static final BooleanProperty CLICK = BooleanProperty.create("clicked");
     *                  ...
     *              }
     *              }</pre>
     */
    public void lampBlock(DeferredBlock<?> block) {
        String name = block.getId().getPath();

        // Modelos de lámpara encendida y apagada
        ModelFile lampOff = models().cubeAll(name + "_off", modLoc("block/" + name + "_off"));
        ModelFile lampOn = models().cubeAll(name + "_on", modLoc("block/" + name + "_on"));

        // Generador de variantes dinámicas (on/off)
        getVariantBuilder(block.get()).forAllStates(state -> {
            boolean clicked = state.getValue(dev._83080441.skyrain.block.custom.MonolithLamp.CLICK);
            return ConfiguredModel.builder()
                    .modelFile(clicked ? lampOn : lampOff)
                    .build();
        });
    }
}