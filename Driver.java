import java.util.Scanner;

public class Driver
{   
    public static Location currLocation;
    public static ContainerItem myInventory;

    public static void main(String[] args)
    {   Scanner myScanner = new Scanner(System.in);

        createWorld();

        while (true)
        {   System.out.println("Enter command: ");
            String command = myScanner.nextLine();
            String[] lineOfCommand = command.split(" ");
            String commandType = lineOfCommand[0].toLowerCase();

            switch(commandType)
            {   case "go":
                    if (lineOfCommand.length > 1)
                    {   String direction = lineOfCommand[1].toLowerCase();
                        if (direction.equals("north") || direction.equals("south") || direction.equals("west") || direction.equals("east"))
                        {   if (currLocation.canMove(direction))
                            {   currLocation = currLocation.getLocation(direction);
                            }
                            else
                            {   System.out.println("There is no location in that direction");
                            }
                        }
                        else
                        {   System.out.println("You have not provided a valid direction to go");
                        }
                    }
                    else
                    {   System.out.println("You have not indicated which direction to go");
                    }
                    break;

                case "look":
                    System.out.println(currLocation.getName() + " - " + currLocation.getDescription() + " currently has the following items: ");
                    for (int i = 0; i < currLocation.numItems(); i++)
                    {   System.out.println("+ " + currLocation.getItem(i).getName());
                    }
                    break;

                case "examine":
                    if (lineOfCommand.length > 1)
                    {   String itemName = lineOfCommand[1];
                        Item itemToExamine = currLocation.getItem(itemName);
                        if (itemToExamine != null)                        
                        {   System.out.println(itemToExamine);
                        }
                        else
                        {   System.out.println("Cannot find that item");
                        }
                    }
                    else
                    {   System.out.println("You have not indicated which item to examine");
                        System.out.println("Please re-enter the command");
                    }
                    break;

                case "inventory":
                    System.out.println(myInventory);
                    break;

                case "take":
                    if (lineOfCommand.length == 1)
                    {   System.out.println("You have not indicated which item to take");
                    }
                    else if (lineOfCommand.length == 2)
                        {   String itemName = lineOfCommand[1];
                            if (currLocation.hasItem(itemName))
                            {   Item item = currLocation.removeItem(itemName);
                                myInventory.addItem(item);
                            }
                            else 
                            {   System.out.println("Cannot find that item here");
                            }
                        }
                    else if (lineOfCommand.length == 4 && lineOfCommand[2].equalsIgnoreCase("from"))
                    {   String containerName = lineOfCommand[3];
                        if (currLocation.hasItem(containerName))
                        {   Item container = currLocation.getItem(containerName);
                            if (container instanceof ContainerItem)
                            {   String itemName = lineOfCommand[1];
                                ContainerItem container1 = (ContainerItem) container;
                                if (container1.hasItem(itemName))
                                {   Item itemToTake = container1.removeItem(itemName);
                                    myInventory.addItem(itemToTake);
                                }
                                else
                                {   System.out.println("Cannot find that item in the container");
                                }
                            }
                            else 
                            {   System.out.println("You have provided an item, not a container, to take an item from");
                            }
                        }
                        else 
                        {   System.out.println("Cannot find that container here");
                        }
                    }
                    else 
                    {   System.out.println("This command is not supported");
                    }
                    break;

                case "drop":
                    if (lineOfCommand.length > 1)
                    {   String itemName = lineOfCommand[1];
                        if (myInventory.hasItem(itemName))
                        {   Item item = myInventory.removeItem(itemName);
                            currLocation.addItem(item);
                        }
                        else 
                        {   System.out.println("Cannot find that item in your inventory");
                        }
                    }
                    else 
                    {   System.out.println("You have not indicated which item to drop");
                    }
                    break;

                case "put":
                    if (lineOfCommand.length == 4 && lineOfCommand[2].equalsIgnoreCase("in"))
                        {   String containerName = lineOfCommand[3];
                            if (currLocation.hasItem(containerName))
                            {   Item container = currLocation.getItem(containerName);
                                if (container instanceof ContainerItem)
                                {   ContainerItem container1 = (ContainerItem) container;
                                    String itemName = lineOfCommand[1];
                                    if (myInventory.hasItem(itemName))
                                    {   Item itemToPut = myInventory.removeItem(itemName);
                                        container1.addItem(itemToPut);
                                    }
                                    else
                                    {     System.out.println("Cannot find that item in your inventory");
                                    }
                                }
                                else 
                                {   System.out.println("Cannot put an item in an item");
                                }
                            }
                            else
                            {   System.out.println("Cannot find that container here");
                            }
                        }
                    else 
                    {   System.out.println("This command is not supported");
                    }
                    break;

                case "help":
                    printAllCommands();
                    break;

                case "quit":
                    myScanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("I don't know how to do that");
                    break;
            }
        }
    }

    public static void createWorld()
    {   Location kitchen = new Location("Kitchen", "a place to cook");
        Location hallway = new Location("Hallway", "a place to walk");
        Location bedroom = new Location("Bedroom", "a place to sleep");
        Location garden = new Location("Garden", "a place to plant flowers");

        kitchen.connect("west", hallway);
        kitchen.connect("south", garden);
        hallway.connect("east", kitchen);
        hallway.connect("south", bedroom);
        bedroom.connect("north", hallway);
        bedroom.connect("east", garden);
        garden.connect("west", bedroom);
        garden.connect("north", kitchen);

        Item knife = new Item("Knife", "Weapon", "A sharp black knife");
        Item turkey = new Item("Turkey", "Food", "A turkey on a plate");
        Item plate = new Item("Plate", "Crockery", "A big round white plate");
        kitchen.addItem(knife);
        kitchen.addItem(turkey);
        kitchen.addItem(plate);

        Item lamp  = new Item("Lamp", "Furniture", "a big lamp on the wall");
        Item vase = new Item("Vase", "Decoration", "a vase of flowers on the floor");
        Item mat = new Item("Mat", "Decoration", "a red mat along the hallway");
        hallway.addItem(lamp);
        hallway.addItem(vase);
        hallway.addItem(mat);

        Item bed = new Item("Bed", "Furniture", "a queen size bed");
        Item picture = new Item("Picture", "Decoration", "on the wall");
        Item closet = new Item("Closet", "Furniture", "a wooden closet full of clothes");
        bedroom.addItem(bed);
        bedroom.addItem(picture);
        bedroom.addItem(closet);

        Item swing = new Item("Swing", "Furniture", "a blue swing");
        Item shoes = new Item("Shoes", "Clothes", "a pair of shoes on the grass");
        Item flowers = new Item("Flowers", "Plant", "a lot of colorful flowers");
        garden.addItem(swing);
        garden.addItem(shoes);
        garden.addItem(flowers);

        ContainerItem refrigerator = new ContainerItem("Refrigerator", "Electronics", "keeps food refrigerated");
        refrigerator.addItem(new Item("Chicken", "poultry", "a big chicken"));
        refrigerator.addItem(new Item("Cabbage", "vegetables", "green cabbage"));
        refrigerator.addItem(new Item ("Beef", "meat", "two pounds of beef steak"));
        kitchen.addItem(refrigerator);

        ContainerItem vault = new ContainerItem("Vault", "Container", "stores valuable things");
        vault.addItem(new Item("Watch", "accessories", "an old Rolex watch in gold"));
        vault.addItem(new Item("Painting", "decoration", "The Starry Night by Vincent van Gogh"));
        vault.addItem(new Item ("Camera", "antiques", "an old film camera"));
        hallway.addItem(vault);

        ContainerItem chest = new ContainerItem("Chest", "Container", "stores jewelry");
        chest.addItem(new Item("Necklace", "accessories", "a silver necklace with a diamond"));
        chest.addItem(new Item("Bracelet", "accessories", "a chain bracelet in sterling silver"));
        chest.addItem(new Item ("Earrings", "accessories", "a pair of teardrop earrings in gold"));
        bedroom.addItem(chest);

        ContainerItem toolkit = new ContainerItem("Toolkit", "Container", "keeps tools to fix things");
        toolkit.addItem(new Item("Hammer", "Tool", "a claw hammer"));
        toolkit.addItem(new Item("Screwdriver", "Tool", "a cross-head screwdriver"));
        toolkit.addItem(new Item ("Drill", "Tool", "an electric drill"));
        garden.addItem(toolkit);

        currLocation = kitchen;
        myInventory = new ContainerItem("Inventory", "Container", "your backpack");
    }

    public static void printAllCommands()
    {   System.out.println("quit: exit game");
        System.out.println("look: print a description of your surroundings");
        System.out.println("examine: examine an item at the current location");
        System.out.println("go: go in the specified direction");
        System.out.println("inventory: list all the items in the inventory");
        System.out.println("take: add an item at the current location to the inventory");
        System.out.println("take _ from _: add an item from a container at the current location to the inventory");
        System.out.println("drop: drop an item from the inventory to the current location");
        System.out.println("put _ in _: drop an item from the inventory to a container at the current location");
        System.out.println("help: print all commands and how they work");
    }
}