package missions;

import elements.Enemy;
import elements.Item;
import elements.NullAnimationException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MissionManager extends Mission {

    private Set<Enemy> enemies;
    private Set<Item> items;
    private Set<Mission> missions;

    public MissionManager(){
        this.missions = new HashSet<>();
    }

    /**
     * Adds a mission to the list
     *
     * @param e the mission to add
     * @return true if the mission was added, false otherwise
     */
    @Override
    public boolean add(Mission e) {
        return missions.add(e);
    }

    /**
     * @return true if the mission was completed, false otherwise
     */
    @Override
    public boolean completed() {
        boolean flag = true;
        for(Mission m : missions){
            flag = flag && m.completed();
        }
        return flag;
    }

    /**
     * Check if an item contributes to a mission
     *
     * @param item the item to check
     */
    @Override
    public void check(MissionItem item) {
        for(Mission m : missions){
            m.check(item);
        }
    }

    /**
     * @return the items population needed by the mission (id, number)
     */
    @Override
    public Map<String, Integer> getItemPopulation() {
        return null;
    }

    /**
     * @return the item set generated by the mission
     */
    @Override
    public Set<Item> getItemSet() {
        if(items == null){
            try {
                buildItemSet();
            } catch (NullAnimationException e) {
                e.printStackTrace();
            }
        }
        return items;
    }

    /**
     * @return the mob set generated by the mission
     */
    @Override
    public Set<Enemy> getEnemySet() {
        return null;
    }

    /**
     * @return the enemies population needed by the mission (id, number)
     */
    @Override
    public Map<String, Integer> getEnemyPopulation() {
        return null;
    }

    /**
     * Build the minimal item set for this set of missions
     */
    private void buildItemSet() throws NullAnimationException {
        //Gets all the mission specific items
        Set<Item> primer = new HashSet<>();
        for(Mission m : missions){
            primer.addAll(m.getItemSet());
        }

        //Gets all the general items
        Map<String, Integer> generals = new HashMap<>();
        for(Mission m : missions){
            for(Map.Entry<String, Integer> e : m.getItemPopulation().entrySet()){
                Integer qty = generals.get(e.getKey());
                if(qty == null || qty < e.getValue()){
                    generals.put(e.getKey(), e.getValue());
                }
            }
        }

        //If there are some specific items that can be used also as general, remove one of them from the generals
        for(Item i : primer){
            String ID = i.getID();
            Integer value = generals.get(ID);
            if(value != null){
                generals.put(ID, value-1);
            }
        }

        //Generate the remaining items
        for(Map.Entry<String, Integer> e : generals.entrySet()){
            for(int i=0; i<e.getValue(); i++){
                primer.add(new Item(e.getKey()));
            }
        }

        this.items = primer;
    }

}