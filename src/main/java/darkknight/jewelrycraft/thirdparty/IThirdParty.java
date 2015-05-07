package darkknight.jewelrycraft.thirdparty;

/** 
 * @author MineMarteen from Pneumaticraft
 */
public interface IThirdParty{

    public void preInit();

    public void init();

    public void postInit();

    /**
     * Gets called from the ClientProxy in the preInit.
     */
    public void clientSide();

    /**
     * Gets called from the ClientProxy in the Init.
     */
    public void clientInit();
}