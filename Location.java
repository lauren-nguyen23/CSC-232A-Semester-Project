import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Location
{
    private String name;
    private String description;
    private ArrayList<Item> items;
    private HashMap connections;

    public Location(String name, String description)
    {   this.name = name;
        this.description = description;
        items = new ArrayList<Item>();
        connections = new HashMap<String, Location>();
    }

    public String getName()
    {   return name;
    }

    public String getDescription()
    {   return description;
    }

    public void setName(String name)
    {   this.name = name;
    }

    public void setDescription(String description)
    {   this.description = description;
    }

    public void addItem(Item someItem)
    {   items.add(someItem);
    }

    public boolean hasItem(String someName)
    {   Iterator<Item> myIter = items.iterator();
        while (myIter.hasNext())
        {   Item item = myIter.next();
            String itemName = item.getName();
            if (itemName.equalsIgnoreCase(someName))
            {   return true;
            }
        }
        return false;
    }

    public Item getItem(String someName)
    {   Iterator<Item> myIter = items.iterator();
        while (myIter.hasNext())
        {   Item item = myIter.next();
            String itemName = item.getName();
            if (itemName.equalsIgnoreCase(someName))
            {   return item;
            }
        }
        return null;
    }

    public Item getItem(int someIndex)
    {   if (someIndex >= 0 && someIndex < items.size())
        {   return items.get(someIndex);
        }
        else
        {   return null;
        }
    }

    public int numItems()
    {   return items.size();
    }

    public Item removeItem(String someName)
    {   Item itemToRemove = getItem(someName);
        if (itemToRemove != null)
        {   items.remove(itemToRemove);
            return itemToRemove;
        }
        else
        {   return null;
        }
    }

    public void connect(String direction, Location location)
    {   connections.put(direction, location);
    }

    public boolean canMove(String direction)
    {   return connections.containsKey(direction);
    }

    public Location getLocation(String direction)
    {   if (canMove(direction))
        {   Location location = (Location) connections.get(direction);
            return location;
        }
        else 
        {   return null;
        }
    }

}
