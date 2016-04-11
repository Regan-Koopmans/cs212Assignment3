/*
Name and Surname: Regan Koopmans
Student/staff Number: 15043143
*/

import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BStarTree
{
	BStarTreeNode root;
	int maxNodeSize;
	int rootSize;

	public BStarTree(int m)
	{
		root = null;
		maxNodeSize = m;
		rootSize = (int)(2*(Math.floor((double)(2*m-1)/3))+1);
	}

	public boolean insertIntoNode(BStarTreeNode node,Integer element)
	{
		Integer [] elementList = convertNodeToIntegers(node);
		int numElements;
		Integer [] minimalList;
		
		if (node == root)
		{
			minimalList = new Integer[elementList.length +1];
			numElements = rootSize;
		}
		else
		{
			minimalList = new Integer[elementList.length+1];
			numElements = maxNodeSize;
		}

		for (int i = 0; i < elementList.length;i++)
		{
			minimalList[i] = elementList[i];
		}

		minimalList[minimalList.length -1] = element;

		Arrays.sort(minimalList);

		node.keys = "";
		for (int x = 0; x < numElements; x++)
		{
			if (x < minimalList.length)
			{
				node.keys += "[" + minimalList[x] + "]";
			}
			else
			{
				node.keys += "[]";
			}
		}
		return true;
	}
	
	public BStarTreeNode findParent(BStarTreeNode node)
	{
		if (root == null || node == root) return null;
		
		boolean isChild = false;
		BStarTreeNode nodePointer = root;
		
		for (int x = 0; x < node.children.length; x++)
			if (nodePointer.children[x] == node)
				isChild = true;
				
		while (!isChild)
		{
			isChild = true; 
		}
		return nodePointer;
	}
	
	public boolean deleteFromNode(BStarTreeNode node, int element)
	{
		return (deleteFromNode(node,new Integer(element)));
	}
	
	public boolean deleteFromNode(BStarTreeNode node,Integer element)
	{
		Integer [] elementList = convertNodeToIntegers(node);
		int numElements;
		Integer [] minimalList;

		if (node == root)
		{
			minimalList = new Integer[rootSize-spacesLeftInNode(node)-1];
			numElements = rootSize;
		}
		else
		{
			minimalList = new Integer[maxNodeSize-spacesLeftInNode(node)-1];
			numElements = maxNodeSize;
		}

		for (int i = 0; i < minimalList.length;i++)
		{
			minimalList[i] = elementList[i];
		}

		Arrays.sort(minimalList);

		node.keys = "";
		for (int x = 0; x < numElements; x++)
		{
			if (x < minimalList.length)
			{
				node.keys += "[" + minimalList[x] + "]";
			}
			else
			{
				node.keys += "[]";
			}
		}
		return true;
	}

	public boolean insertElement(int element)
	{
		return insertElement(new Integer(element));
	}
	
	public void splitChild(int i, BStarTreeNode node)
	{
//		BStarTreeNode temp = new BStarTreeNode;
	}
	
	public BStarTreeNode findToInsert(Integer element)
	{
		int rootReferences = 0;
		
		if (root.children != null)
			for (int x = 0; x < root.children.length; x++)
				if (root.children[x] != null)
					rootReferences++;
		
		if (spacesLeftInNode(root) > 0 && rootReferences == 0)
			return root;
		else return findToInsert(element,root);
	}
	
	public BStarTreeNode findToInsert(Integer element, BStarTreeNode node)
	{
		if (node != null)
		{
			int i = 1;
			for ( ; i <= convertNodeToIntegers(node).length && convertNodeToIntegers(node)[i-1].compareTo(element) < 0; i++);
			if ((i > convertNodeToIntegers(node).length || convertNodeToIntegers(node)[i-1].compareTo(element) > 0) && node.children != null && node.children.length >= i)
				return findToInsert(element,node.children[i-1]);
			else return node;
		}
		else return null;
	}

	public boolean insertElement(Integer element)
	{
		if (root == null)
		{
			root = new BStarTreeNode(element,rootSize);
			root.initializeChildren();
			return true;
		}
		else
		{
			
			BStarTreeNode nodeToInsertInto = findToInsert(element);
			if (spacesLeftInNode(nodeToInsertInto) == 0)
			{
				System.out.println(findToInsert(element) == root);
				
				if (findToInsert(element) == root)
				splitRootInsert(element);
				
			}
			else return insertIntoNode(findToInsert(element),element); 
				
		}
		return true;
	}
	

	public boolean deleteElement(int element)
	{
		return deleteElement(new Integer(element));
	}

	public boolean deleteElement(Integer element)
	{
		BStarTreeNode node = BStarSearch(element);
		
		if (node != null)
		{
			// if (node.children != null)
			// {
			//
			// }
			// else
			//
			// while (true)
			// {
			// 	if (node)
			// 	{
			// 		return;
			// 	}
			// 	else if ()
			// 	{
			//
			// 	}
			// 	else if ()
			// 	{
			//
			// 	}
			// 	else ()
			// 	{
			//
			// 	}
			// }
		}
		return false;
	}

	public BStarTreeNode BStarSearch(Integer key)
	{
		return BStarSearch(key,root);
	}

	public BStarTreeNode BStarSearch(Integer key, BStarTreeNode node)
	{
		if (node != null)
		{
			int i = 1;
			for ( ; i <= convertNodeToIntegers(node).length && convertNodeToIntegers(node)[i-1].compareTo(key) < 0; i++);
			if (i > convertNodeToIntegers(node).length || convertNodeToIntegers(node)[i-1].compareTo(key) > 0)
				return BStarSearch(key,node.children[i-1]);
			else return node;
		}
		else return null;
	}

	public String search(int element)
	{
		return search(new Integer(element));
	}

	public String search(Integer element)
	{
		return search(element,root);
	}

	public String search(Integer element, BStarTreeNode node)
	{
		if (node != null)
		{
			int i = 1;
			for ( ; i <= convertNodeToIntegers(node).length && element.compareTo(convertNodeToIntegers(node)[i-1]) > 0; i++);
			if (i > convertNodeToIntegers(node).length || convertNodeToIntegers(node)[i-1].compareTo(element) > 0)
				if (node.children != null && node.children.length > 0)
					return node.keys + "," + search(element,node.children[i-1]);
				else return node.keys + ",*NULL*";
			else return node.keys;
		}
		else return "*NULL*";
	}

	public int height()
	{
		return height(root);
	}

	public int height(BStarTreeNode node)
	{
		if (node == null) return 0;

		if (node.children == null) return 1;

		int [] heights = new int[node.children.length];

		for (int x = 0; x < node.children.length; x++)
		{
			heights[x] = height(node.children[x]);
		}
		int maxHeight = -1;
		for (int x = 0; x < node.children.length; x++)
		{
			if (heights[x] > maxHeight)
				maxHeight = heights[x];
		}
		return 1 + maxHeight;
	}

	public int fullness()
	{
		if (root == null) return 0;

		return (int)((double)(countSpacesFilled())/countSpacesInTree() * 100);
	}

	public String breadthFirst()
	{
		String treeString = "";

		Queue queue = new Queue();

		Node nodePointer = new Node(root);

		if (nodePointer != null)
		{
			queue.enqueue(nodePointer);
			while (!queue.isEmpty())
			{
				nodePointer = queue.dequeue();
				System.out.println(nodePointer.elem.keys);
				treeString += nodePointer.elem.keys;
				if (nodePointer.elem.children != null)
				{
					for (int x = 0; x < nodePointer.elem.children.length; x++)
					{
						if (nodePointer.elem.children[x] != null)
							queue.enqueue(nodePointer.elem.children[x]);
					}
				}
			}
		}
		return treeString;
	}

	public String getTreeString()
	{
		return getTreeString(root);
	}

	public String getTreeString(BStarTreeNode node)
	{
		if (node == null) return "";

		String nodeString = "";

		for (int x = 0; x < convertNodeToIntegers(node).length; x++)
		{
			nodeString += getTreeString(node.children[x]);
			nodeString += convertNodeToIntegers(node)[x];
		}
		nodeString += getTreeString();
		return nodeString;
	}

	public void splitRootInsert(Integer element)
	{
		Integer [] rootArray = new Integer[convertNodeToIntegers(root).length+1];
		Integer [] currentElements = convertNodeToIntegers(root);
		
		for (int x = 0; x < currentElements.length; x++)
		{
			rootArray[x] = currentElements[x];
		}
		rootArray[rootArray.length -1] = element;
		Arrays.sort(rootArray);
		
		for (int x = 0; x < rootArray.length; x++)
			System.out.println("\t" + rootArray[x]);
		
		
	}
	
	public void splitNodeInsert(BStarTreeNode node, Integer element)
	{
		System.out.println(node.children.length % maxNodeSize);
		
		return;
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
		if (node.children != null && node.children.length > 0)
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

		if (node.children != null && node.children.length > 0)
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
		int i = 0;
		String splitNum = node.keys;
		
		splitNum = splitNum.replace("[","");
		splitNum = splitNum.replace("]"," ");
		StringTokenizer st = new StringTokenizer(splitNum);
		String [] numbers = new String[st.countTokens()];
		while (st.hasMoreElements())
		{
			numbers[i] = st.nextToken();
			i++;
		}
		
		Integer [] valueArray = null;

		int notNullCounter = 0;
		
		for (int x = 0; x < numbers.length; x++)
			if (numbers[x] != " ")
				notNullCounter++;
		
		
		valueArray = new Integer[notNullCounter];
		
		for (int x = 0; x < valueArray.length; x++)
		{
			if (numbers[x].matches("\\d"))
				valueArray[x] = new Integer(Integer.parseInt(numbers[x]));
			else valueArray[x] = null;		
		}
		return valueArray;
	}
}
