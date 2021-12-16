import java.util.ArrayList;
import java.util.Iterator;

public class ContainerItem extends Item
{
    private ArrayList<Item> items;
    
    public ContainerItem(String name, String type, String description)
    {   super(name, type, description);
        items = new ArrayList<Item>();
    }

    public void addItem(Item someItem)
    {   items.add(someItem);
    }

    public boolean hasItem(String someName)
    {   Iterator<Item> myIterator = items.iterator();
        while (myIterator.hasNext())
        {   Item item = myIterator.next();
            String itemName = item.getName();
            if (itemName.equalsIgnoreCase(someName))
            {   return true;
            }
        }
        return false;
    }

    public Item removeItem(String someName)
    {   if (hasItem(someName))
        {   Iterator<Item> myIterator = items.iterator();
            while (myIterator.hasNext())
            {   Item item = myIterator.next();
                String itemName = item.getName();
                if (itemName.equalsIgnoreCase(someName))
                {   items.remove(item);
                    return item;
                }
            }
        }
        return null;
    }

    @Override
    public String toString()
    {   String s = super.getName() + " [ " +  super.getType() + " ] : " + super.getDescription() + " that contains:";
        Iterator<Item> myIterator = items.iterator();
        String itemNames = "";
        while (myIterator.hasNext())
        {   Item item = myIterator.next();
            String itemName = item.getName();
            itemNames = String.join("\n+ ", itemNames, itemName);
        }
        return s + itemNames;
    }
}

