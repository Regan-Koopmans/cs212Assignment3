public class BStarTreeNode
{
	public String keys;
	public BStarTreeNode [] children;
	

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
		children = null;
	}
}