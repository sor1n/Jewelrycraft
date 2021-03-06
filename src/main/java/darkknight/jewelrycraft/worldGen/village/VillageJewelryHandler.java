package darkknight.jewelrycraft.worldGen.village;

import java.util.List;
import java.util.Random;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageCreationHandler;
import darkknight.jewelrycraft.config.ConfigHandler;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;

public class VillageJewelryHandler implements IVillageCreationHandler
{
    
    /**
     * @param random
     * @param i
     * @return
     */
    @Override
    public PieceWeight getVillagePieceWeight(Random random, int i)
    {
        return new PieceWeight(ComponentJewelry.class, ConfigHandler.JEWELER_WEIGHT, ConfigHandler.MAX_VILLAGE_JEWELERS);
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getComponentClass()
    {
        return ComponentJewelry.class;
    }
    
    /**
     * @param villagePiece
     * @param startPiece
     * @param pieces
     * @param random
     * @param p1
     * @param p2
     * @param p3
     * @param p4
     * @param p5
     * @return
     */
    @Override
    public Object buildComponent(PieceWeight villagePiece, Start startPiece, @SuppressWarnings ("rawtypes") List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
    {
        return ComponentJewelry.buildComponent(startPiece, pieces, random, p1, p2, p3, p4, p5);
    }
}