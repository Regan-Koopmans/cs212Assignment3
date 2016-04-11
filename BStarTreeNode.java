/*
Name and Surname: Regan Koopmans
Student/staff Number: 15043143
*/

public class BStarTreeNode
{
	public String keys;
	public BStarTreeNode [] children = null;
	public int maxNode;

	public BStarTreeNode(int m)
	{
		keys = "";
		for (int x = 0; x < m; x++)
		{
			keys += "[]";
		}
		maxNode = m;
		initializeChildren();
	}

	public BStarTreeNode(Integer element, int m)
	{
		maxNode = m;
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
		
		for (int x = 0; x < maxNode; x++)
			children[x] = null;
	}
}