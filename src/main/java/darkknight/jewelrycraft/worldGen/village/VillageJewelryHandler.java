package darkknight.jewelrycraft.worldGen.village;

import java.util.List;
import java.util.Random;

import net.minecraft.world.gen.structure.StructureVillagePieces.House1;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageCreationHandler;

public class VillageJewelryHandler implements IVillageCreationHandler
{
    @Override
    public PieceWeight getVillagePieceWeight (Random random, int i)
    {
        return new PieceWeight(ComponentJewelry.class, 30, i + random.nextInt(4));
    }

    @Override
    public Class<?> getComponentClass ()
    {
        return ComponentJewelry.class;
    }

    @Override
    public Object buildComponent (PieceWeight villagePiece, Start startPiece, @SuppressWarnings("rawtypes") List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
    {
        return ComponentJewelry.buildComponent(startPiece, pieces, random, p1, p2, p3, p4, p5);
    }
}