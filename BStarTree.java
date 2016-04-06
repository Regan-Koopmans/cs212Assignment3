import java.util.regex.Pattern;

/*
Name and Surname: Regan Koopmans
Student/staff Number: 15043143
*/

/*You must complete this class to create a fully functional B*-Tree containing
Integer objects.  Additional instructions are provided in comments throughout the class*/
public class BStarTree
{
	/*
	1. You may the signatures of any of the given methods.  You may however
	add any additional methods and/or field which you may require to aid you 
	in the completion of this assignment.
	
	2. You will have to design and implement a your own Node class.  The BStarTree 
	should house Integer objects.
	
	3. You will notice that there are some overloaded methods, some of which work 
	for Integer objects and some with primitive type int.  You have to find a way 
	to implement the methods to work with both types.
	*/

	BStarTreeNode root;
	int maxNodeSize;
	int rootSize;
	
	public BStarTree(int m)
	{
		/*
		The constructor.  You must create a BStarTree object of order m,
		where m is passed as a parameter to the constructor.
		*/
		root = null;
		maxNodeSize = m;
	}
	
	public boolean insertElement(int element)
	{
		/*
		The int element passed as a parameter should be inserted in
		your B*-Tree.  The method should return true after a 
		successful insert, and false otherwise.
		*/
				
		return insertElement(new Integer(element));
	}
	
	public boolean insertElement(Integer element)
	{
		/*
		The Integer object passed as a parameter should be inserted in
		your B*-Tree.  The method should return true after a 
		successful insert, and false otherwise.
		*/
		if (element == null) return false;
		
		if (root == null)
		{
			rootSize = (int)(2*(Math.floor((2*maxNodeSize - 1 )/3)) + 1);
			root = new BStarTreeNode(element,rootSize);
			return true;
		}
		else
		{

		}

		return false;
	}
	
	public boolean deleteElement(int element)
	{
		/*
		The int element passed as a parameter should be deleted from
		your B*-Tree.  The method should return true after a 
		successful delete, and false otherwise.
		*/
				
		return false;
	}
	
	public boolean deleteElement(Integer element)
	{
		/*
		The Integer object passed as a parameter should be deleted from
		your B*-Tree.  The method should return true after a 
		successful delete, and false otherwise.
		*/
				
		return false;
	}
	
	public String search(int element)
	{
		/*
		A String should be returned representing the search path
		for the element sent in as a parameter.  Refer to the
		specification for more details.
		*/
		return null;
	}
	
	public String search(Integer element)
	{
		/*
		A String should be returned representing the search path
		for the element sent in as a parameter.  Refer to the
		specification for more details.
		*/
		return null;
	}
	
	public int height()
	{
		/*
		This method should return the height of the tree.
		*/
		if (root == null) return 0;


		return 0;
	}
	
	public int fullness()
	{
		/*
		This method should return as a percentage the fullness of the tree.
		The percentage is out of 100 and if, for example, 70 is returned,
		it means that the tree is 70% full.
		A tree containing no keys is 0% full.
		*/
		if (root == null) return 0;
		

		return 0;
	}
	
	public String breadthFirst()
	{
		/*
		This method returns a String containing the nodes in breath-first
		order.  You should not include null nodes in the string.  The string
		for an empty tree is simply "".
		*/
		//System.out.println("Space left in root: " + spacesLeftInNode(root.keys));
		//System.out.println("Total spaces in the tree: " + countSpacesInTree());
		//System.out.println("Spaces occupied: " + countSpacesFilled());

		//System.out.println( root.keys.split(Pattern.quote("[]"),-1)[0] );
		System.out.println( convertNodeToIntegers(root) );


		String treeString = "";

		if (root != null)
		{
			treeString += root.keys;
		}

		return treeString;
	}

	public int spacesLeftInNode(BStarTreeNode node)
	{
		if (node != null)
			return spacesLeftInNode(node.keys);
		else return 0;
	}

	public int spacesLeftInNode(String node)
	{
		if (node != "" && node != null)
			return node.split(Pattern.quote("[]"),-1).length -1;
		else return 0;
	}

	public int countSpacesInTree()
	{
		return countSpacesInTree(root);
	}

	public int countSpacesInTree(BStarTreeNode node)
	{
		int currentNodeSpaces = node.keys.split(Pattern.quote("["),-1).length -1;
		int childNodeSpaces = 0;
		if (node.children != null)
		{
			for (int i = 0; i < maxNodeSize; i++ )
			{
				if (node.children[i] != null)
					childNodeSpaces += (int)(node.children[i].keys.split(Pattern.quote("["),-1).length -1)/2;
			}

		}
		return currentNodeSpaces + childNodeSpaces;
	}


	public int countSpacesFilled()
	{
		return countSpacesFilled(root);
	}

	public int countSpacesFilled(BStarTreeNode node)
	{
		if (node == null) return 0;

		int spacesFilled = 0;
		int childrenSpacesFilled = 0;
		if (node == root)
			spacesFilled = rootSize - spacesLeftInNode(node);
		else spacesFilled = maxNodeSize - spacesLeftInNode(node);
	
		if (node.children != null)
		{
			for (int i = 0; i < maxNodeSize; i++)
			{
				if (node.children[i] != null)
					childrenSpacesFilled += maxNodeSize - spacesLeftInNode(node.children[i]);
			}
		}

		return spacesFilled + childrenSpacesFilled;

	}

	public Integer[] convertNodeToIntegers(BStarTreeNode node)
	{
		String [] numbers = node.keys.split(Pattern.quote("[]"),-1);

		Integer [] valueArray;

		for (int x = 0; x < numbers.length; x++)
		{
			System.out.println(numbers[x]);
		}

		valueArray = new Integer[numbers.length];
		System.out.println(numbers[1] == " ");

		for (int i = 0; i < numbers.length; i++)
		{
			if (numbers[i] != "[]")
			{
				
				numbers[i] = numbers[i].replace("[","");
				System.out.println("One");

				numbers[i] = numbers[i].replace("]","");
				System.out.println("Two");

					valueArray[i] = Integer.parseInt(numbers[i]);
					System.out.println(valueArray[i]);
			}	

		}

		return valueArray;
	}


}
