package blocks;

public class DemoBlockFactory implements BlockFactory{

	@Override
	public Block generateBlock(int stateIndex, String mapName) {
		return new DemoBlock(stateIndex,mapName);
	}

}
