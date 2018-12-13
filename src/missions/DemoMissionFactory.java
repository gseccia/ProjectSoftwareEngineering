package missions;

import configuration.EnemyConfiguration;
import configuration.ItemConfiguration;
import elements.Enemy;
import elements.Item;
import utils.RandomCollection;

import java.util.Set;

public class DemoMissionFactory implements MissionFactory {

    private ItemConfiguration itemConfiguration;
    private EnemyConfiguration enemyConfiguration;
    private SetStorageRoom targets;

    public DemoMissionFactory(ItemConfiguration itemConfiguration, EnemyConfiguration enemyConfiguration) {
        this.itemConfiguration = itemConfiguration;
        this.enemyConfiguration = enemyConfiguration;
        targets = new SetStorageRoom();
    }

    /**
     * Generates the missions
     *
     * @param itemCapacity the number of items that can be generated
     * @param mobCapacity  the number of mobs that can be generated
     * @param difficulty   the number of missions to generate
     * @return an instance of Mission
     * @throws NotEnoughMissionsException if there are no more missions available
     */
    @Override
    public Mission generateMissions(int itemCapacity, int mobCapacity, int difficulty) throws NotEnoughMissionsException {
        Mission manager = new MissionManager();
        RandomCollection<String> itemNames = new RandomCollection<>(itemConfiguration.getMissionItemNames());
        RandomCollection<String> mobNames = new RandomCollection<>(enemyConfiguration.getMobNames());
        String itemId = itemNames.getRandom();
        String enemyId = mobNames.getRandom();
        mobNames.remove(enemyId);
        String bossId = mobNames.getRandom();

        manager.add(new CollectNItemsMission(itemId, itemCapacity));
        manager.add(new KillNEnemiesMission(enemyId, mobCapacity-1));
        manager.add(new KillTheBossMission(bossId, difficulty));

        manager.produceTargets(targets);

        return manager;

    }

    /**
     * @return the items needed by the missions
     */
    @Override
    public Set<Item> targetItems() {
        return targets.getItemSet();
    }

    /**
     * @return the enemies needed by the missions
     */
    @Override
    public Set<Enemy> targetEnemies() {
        return targets.getEnemySet();
    }
}
