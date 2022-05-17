/** 
 * Quan Nguyen and Lee Nguyen
 * CECS 277 Project - Dungeons & Monsters
 * This program/project allows a user to explore a dungeon maze and fight monsters 
 * that they encounter along the way.
 * 4/23/2022
 */

public class Main {
  public static void main(String[] args) {
    int menuIntChoice = 0;
    EnemyGenerator enemyGen = new EnemyGenerator();
    
    System.out.print("What is your name, traveler?");
    
    String inputName = CheckInput.getString();
    Hero hero = new Hero(inputName);

    while (menuIntChoice!=5) {
      if (hero.getHp() == 0) break;
      System.out.print(hero.toString());
      menuIntChoice = mainMenu(hero);
      char posChar = 'x';
      
      if (menuIntChoice == 1) 
        posChar = hero.goNorth();
      
      else if (menuIntChoice == 2) 
        posChar = hero.goSouth();
      
      else if (menuIntChoice == 3) 
        posChar = hero.goEast();
    
      else if (menuIntChoice == 4) 
        posChar = hero.goWest();
    
      if(menuIntChoice!=5)  switch (posChar) {
        case 'x':
          System.out.println("Out of bounds.");
          break;
        case 'n':
          System.out.println("There was nothing here.");
          break;
        case 's':
          store(hero);
          break;
        case 'f':
          if (hero.hasKey()) {
            hero.levelUp();
            hero.useKey();
            System.out.println("You found a locked gate. Luckily you have a key! You proceed to the next area.");
          } else {
            System.out.println("You found a locked gate. Find a key and come back to unlock the gate.");
          }
          break;
        case 'i':
          if ( 0 + (int)(Math.random() * ((1 - 0) + 1)) == 1) {
            hero.pickUpKey();
            System.out.println("You found a Key!");
          } else {
            hero.pickUpPotion();
            System.out.println("You found a potion!");
          }
          break;
        case 'm':
          Enemy enemy = enemyGen.generateEnemy(hero.getLevel());
          monsterRoom(hero, enemy);
        default:
          break;
      }
    }
    System.out.println("Game Over !");
  }

  
  /** Displays the main menu and take the user input
   * @param hero is the player
   * @return int returns the user's choice
   */
  public static int mainMenu(Hero hero) {
    System.out.println("1. Go North");
    System.out.println("2. Go South");
    System.out.println("3. Go East");
    System.out.println("4. Go West");
    System.out.println("5. Quit");
    return CheckInput.getIntRange(1, 5);
  }

  
  /** Fight between a monster and a hero until either die or the player run away
   * @param hero is the player
   * @param e is the enemy
   * @return Boolean represents if the enemy is dead or alive at the end of the fight
   */
  public static Boolean monsterRoom(Hero hero, Enemy enemy) {
    System.out.print("You've encountered a ");
    loop: while (true) {
      if (enemy.getHp() <= 0) 
        break loop;
      
      System.out.println(enemy.toString());
      System.out.println("1. Fight\n2. Run Away");
      
      if (hero.hasPotion()) 
        System.out.println("3. Drink Potion");
      int decision = CheckInput.getIntRange(1, hero.hasPotion() ? 3 : 2);
      switch (decision) {
        case 1:
          fight(hero, enemy);
          if (enemy.getHp() == 0) return true;
          break;
        case 2:
          int randomDirection = 1 + (int)(Math.random() * ((4 - 1) + 1));
          if (randomDirection == 1) 
            hero.goNorth();
      
          else if (randomDirection == 2) 
            hero.goSouth();
          
          else if (randomDirection == 3) 
            hero.goEast();
        
          else if (randomDirection == 4) 
            hero.goWest();
          
          break loop;
        case 3:
          hero.usePotion();
          System.out.println("Healed to full HP.");
          break;
        default:
          break;
      }
      
    }
    return false;
  }

  
  /** Does a single round of damage
   * @param hero is the player
   * @param e is the enemy
   * @return Boolean represents if the enemy is dead or alive at the end of the fight
   */
  public static Boolean fight(Hero hero, Enemy enemy) {
    System.out.print(hero.getAttackMenu());
    int choice = CheckInput.getIntRange(1, hero.getNumAttackMenuItems());
    System.out.println(hero.getSubAttackMenu(choice));
    int subChoice = CheckInput.getIntRange(1, hero.getNumSubAttackMenuItems(choice));
    System.out.println(hero.attack(enemy, choice, subChoice));
    if (enemy.getHp() > 0) System.out.println(enemy.attack(hero));
    return false;
  }

  
  /** Displays the store and make purchase available to the user
   * @param hero is the player
   */
  public static void store(Hero hero) {
    System.out.println("Welcome to the store. What would you like to buy?");
    System.out.println("1. Health Potion - 25g");
    System.out.println("2. Key - 50g");
    System.out.println("3. Nothing, just browsing...");
    var choice = CheckInput.getIntRange(1, 3);
    switch (choice) {
      case 1:
        if (hero.getGold() < 25) {
          System.out.println("Insufficient funds.");
        } else {
          hero.pickUpPotion();
          hero.spendGold(25);
          System.out.println("You bought a potion! (-25 gold)");
        }
        break;
      case 2:
        if (hero.getGold() < 50) {
          System.out.println("Insufficient funds.");
        } else {
          hero.pickUpKey();
          hero.spendGold(50);
          System.out.println("You bought a key! (-50 gold)");
        }
        break;
      default:
        break;
    }
  }
}