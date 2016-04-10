/*
Name and Surname: Regan Koopmans
Student/staff Number: 15043143
*/

public class BStarTreeNode
{
	public String keys;
	public BStarTreeNode [] children;
	public int maxNode;

	public BStarTreeNode(int m)
	{
		keys = "";
		for (int x = 0; x < m; x++)
		{
			keys += "[]";
		}
		children = null;
	}

	public BStarTreeNode(Integer element, int m)
	{
		keys = "[" + element.toString() + "]";
		for (int x = 1; x < m; x++)
		{
			keys += "[]";
		}
		initializeChildren();
	}
	
	public void initializeChildren()
	{
		if (children == null) 
			children = new BStarTreeNode[maxNode];
	}
}