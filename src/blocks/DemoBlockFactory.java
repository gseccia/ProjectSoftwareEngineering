package blocks;

public class DemoBlockFactory implements BlockFactory{

	/**
     * Generate a new block
     *
     * @param stateIndex the state index
     * @param mapName    the map name
     * @return a new block
     */
    
	@Override
	public Block generateBlock(int stateIndex, String mapName) {
		return new DemoBlock(stateIndex,mapName);
	}
}