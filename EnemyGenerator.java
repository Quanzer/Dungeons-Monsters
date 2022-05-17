import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class EnemyGenerator {
  private HashMap<String, Integer> enemies;

  /**
	 * Constructor reads the file and adds the different enemies and their 
   * base hp to the HashMap. 
	 */
    public EnemyGenerator() {
        enemies = new HashMap<String, Integer>();
        try {
            Scanner read = new Scanner(new File("Enemies.txt"));
            while(read.hasNextLine()) {
                String word = read.nextLine();
              
                String[] comma = word.split(",");
                enemies.put(comma[0], Integer.parseInt(comma[1]));
              
            }
            read.close();
        } catch( FileNotFoundException fnf ) {
            System.out.println("File not found");
        } catch (NullPointerException npe) {
            System.out.println("Could not sort list");
        }
    }

  /**
	 * Randomly selects an enemy from the map, then randomly selects an ability type (Fighter/Magical/Archer), 
	 * then copies over the name and base hp to construct a new enemy of that type. Thus, the difficulty will increase. 
	 * @param level passes the level of the enemy to modify the hp. 
	 * @return Enemy returns the Enemy
	 */
    public Enemy generateEnemy(int level) {
        Set<String> set = enemies.keySet();
        int randomType = (int) (Math.random() * 3) + 1; // 1-3
        int e = (int) (Math.random() * (set.size()));
      
        String name = (String)enemies.keySet().toArray()[e];
        int mHp = enemies.get(enemies.keySet().toArray()[e]);
      
        switch (randomType) {
            case 1:
                name+= " Warrior";
                return new Warrior(name, mHp * level + 1);
            case 2:
                name+= " Wizard";
                return new Wizard(name, mHp * level + 1);
            case 3:
                name+= " Ranger";
                return new Ranger(name, mHp * level + 1);

            default:
                return null;
        }
    }
  
}
