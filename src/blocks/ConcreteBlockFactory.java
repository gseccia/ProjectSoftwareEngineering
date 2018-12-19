package blocks;


/**
 * This class allows to create ConcreteBlock
 */
public class ConcreteBlockFactory implements BlockFactory {

    /**
     * Generate a new block
     *
     * @param stateIndex the state index
     * @param mapName    the map name
     * @return a new block
     */
    @Override
    public Block generateBlock(int stateIndex, String mapName) {
        return new ConcreteBlock(stateIndex, mapName);
    }
}
