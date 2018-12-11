package blocks;

public interface BlockFactory {

    /**
     * Generate a new block
     * @param stateIndex the state index
     * @param mapName the map name
     * @return a new block
     */
    Block generateBlock(int stateIndex, String mapName);

}
