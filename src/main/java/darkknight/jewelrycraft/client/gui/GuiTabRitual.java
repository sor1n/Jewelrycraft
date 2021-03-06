package darkknight.jewelrycraft.client.gui;

import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.client.Page;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class GuiTabRitual extends GuiTab
{
    public GuiTabRitual(int id)
    {
        super(id);
    }
    
    public String getName()
    {
        return StatCollector.translateToLocal("guide." + Variables.MODID + ".tab.ritual");
    }
    
    @Override
    public ItemStack getIcon()
    {
        return new ItemStack(BlockList.handPedestal);
    }
   
    @Override
    public void drawBackground(GuiGuide gui, int x, int y, int page)
    {
        int xPos = page % 2 == 0 ? 107 : -35;
        switch(page)
        {
            case 1:                
                Page.drawText(gui, EnumChatFormatting.DARK_BLUE + "\u00a7n" + "Layer 1", gui.getLeft() + xPos + 35, gui.getTop() - 30);
                for(int i = -1; i < 10; i++)
                    for(int j = 0; j < 11; j++)
                        Page.addSlotItem(gui, gui.getLeft() + xPos + 11*i, gui.getTop() + 11*j, x, y, new ItemStack(Blocks.air), 0, 0, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*8, gui.getTop() + 11*1, x, y, new ItemStack(Blocks.stonebrick), 0, 0, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*0, gui.getTop() + 11*1, x, y, new ItemStack(Blocks.stonebrick), 0, 0, 0);  
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*8, gui.getTop() + 11*9, x, y, new ItemStack(Blocks.stonebrick), 0, 0, 0);  
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*0, gui.getTop() + 11*9, x, y, new ItemStack(Blocks.stonebrick), 0, 0, 0);  
                
                //Top                
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*2, gui.getTop() + 11*1, x, y, new ItemStack(BlockList.handPedestal), 0, 45, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*4, gui.getTop() + 11*0, x, y, new ItemStack(BlockList.handPedestal), 0, 0, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*6, gui.getTop() + 11*1, x, y, new ItemStack(BlockList.handPedestal), 0, -45, 0);
                                
                //Left                
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*0, gui.getTop() + 11*3, x, y, new ItemStack(BlockList.handPedestal), 0, 45, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*-1, gui.getTop() + 11*5, x, y, new ItemStack(BlockList.handPedestal), 0, 90, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*0, gui.getTop() + 11*7, x, y, new ItemStack(BlockList.handPedestal), 0, 135, 0);

                //Bottom
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*2, gui.getTop() + 11*9, x, y, new ItemStack(BlockList.handPedestal), 0, 135, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*4, gui.getTop() + 11*10, x, y, new ItemStack(BlockList.handPedestal), 0, 180, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*6, gui.getTop() + 11*9, x, y, new ItemStack(BlockList.handPedestal), 0, 225, 0);

                //Right
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*8, gui.getTop() + 11*3, x, y, new ItemStack(BlockList.handPedestal), 0, -35, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*9, gui.getTop() + 11*5, x, y, new ItemStack(BlockList.handPedestal), 0, 270, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*8, gui.getTop() + 11*7, x, y, new ItemStack(BlockList.handPedestal), 0, 225, 0);

                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*2, gui.getTop() + 11*5, x, y, new ItemStack(Blocks.stonebrick), 0, 0, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*4, gui.getTop() + 11*5, x, y, new ItemStack(BlockList.handPedestal), 0, 0, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*6, gui.getTop() + 11*5, x, y, new ItemStack(Blocks.stonebrick), 0, 0, 0);                
                break;
                
            case 2:                
                Page.drawText(gui, EnumChatFormatting.DARK_BLUE + "\u00a7n" + "Layer 2", gui.getLeft() + xPos + 35, gui.getTop() - 30);
                for(int i = -1; i < 10; i++)
                    for(int j = 0; j < 11; j++)
                        Page.addSlotItem(gui, gui.getLeft() + xPos + 11*i, gui.getTop() + 11*j, x, y, new ItemStack(Blocks.air), 0, 0, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*8, gui.getTop() + 11*1, x, y, new ItemStack(Blocks.stonebrick), 0, 0, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*0, gui.getTop() + 11*1, x, y, new ItemStack(Blocks.stonebrick), 0, 0, 0);  
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*8, gui.getTop() + 11*9, x, y, new ItemStack(Blocks.stonebrick), 0, 0, 0);  
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*0, gui.getTop() + 11*9, x, y, new ItemStack(Blocks.stonebrick), 0, 0, 0);  

                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*2, gui.getTop() + 11*5, x, y, new ItemStack(Blocks.stonebrick), 0, 0, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*6, gui.getTop() + 11*5, x, y, new ItemStack(Blocks.stonebrick), 0, 0, 0);                
                break;
                
            case 3:                
                Page.drawText(gui, EnumChatFormatting.DARK_BLUE + "\u00a7n" + "Layer 3", gui.getLeft() + xPos + 35, gui.getTop() - 30);
                for(int i = -1; i < 10; i++)
                    for(int j = 0; j < 11; j++)
                        Page.addSlotItem(gui, gui.getLeft() + xPos + 11*i, gui.getTop() + 11*j, x, y, new ItemStack(Blocks.air), 0, 0, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*8, gui.getTop() + 11*1, x, y, new ItemStack(BlockList.shadowBlock), 0, 0, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*0, gui.getTop() + 11*1, x, y, new ItemStack(BlockList.shadowBlock), 0, 0, 0);  
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*8, gui.getTop() + 11*9, x, y, new ItemStack(BlockList.shadowBlock), 0, 0, 0);  
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*0, gui.getTop() + 11*9, x, y, new ItemStack(BlockList.shadowBlock), 0, 0, 0);  

                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*2, gui.getTop() + 11*5, x, y, new ItemStack(Blocks.stonebrick), 0, 0, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*6, gui.getTop() + 11*5, x, y, new ItemStack(Blocks.stonebrick), 0, 0, 0);                
                break;
                
            case 4:                
                Page.drawText(gui, EnumChatFormatting.DARK_BLUE + "\u00a7n" + "Layer 4", gui.getLeft() + xPos + 35, gui.getTop() - 30);
                for(int i = -1; i < 10; i++)
                    for(int j = 0; j < 11; j++)
                        Page.addSlotItem(gui, gui.getLeft() + xPos + 11*i, gui.getTop() + 11*j, x, y, new ItemStack(Blocks.air), 0, 0, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*2, gui.getTop() + 11*5, x, y, new ItemStack(Blocks.stone_brick_stairs), 0, 90, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*3, gui.getTop() + 11*5, x, y, new ItemStack(Blocks.stone_brick_stairs), 0, -90, 180);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*4, gui.getTop() + 11*5, x, y, new ItemStack(BlockList.shadowEye), 0, 90, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*5, gui.getTop() + 11*5, x, y, new ItemStack(Blocks.stone_brick_stairs), 0, 90, 180);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*6, gui.getTop() + 11*5, x, y, new ItemStack(Blocks.stone_brick_stairs), 0, -90, 0);                
                break;
                
            case 5:                
                Page.drawText(gui, EnumChatFormatting.DARK_BLUE + "\u00a7n" + "Layer 5", gui.getLeft() + xPos + 35, gui.getTop() - 30);
                for(int i = -1; i < 10; i++)
                    for(int j = 0; j < 11; j++)
                        Page.addSlotItem(gui, gui.getLeft() + xPos + 11*i, gui.getTop() + 11*j, x, y, new ItemStack(Blocks.air), 0, 0, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*3, gui.getTop() + 11*5, x, y, new ItemStack(Blocks.stone_slab, 1, 5), 0, 0, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*4, gui.getTop() + 11*5, x, y, new ItemStack(Blocks.stone_slab, 1, 5), 0, 0, 0);
                Page.addSlotItem(gui, gui.getLeft() + xPos + 11*5, gui.getTop() + 11*5, x, y, new ItemStack(Blocks.stone_slab, 1, 5), 0, 0, 0);              
                break;
        }
    }
    
    @Override
    public int getMaxPages()
    {
        return 5;
    }
    
    @Override
    public void drawForeground(GuiGuide gui, int x, int y, int page)
    {}
}
