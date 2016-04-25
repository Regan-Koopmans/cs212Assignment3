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
		maxNodeSize = m-1;
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
	
	public BStarTreeNode findParent(BStarTreeNode searchNode)
	{
		return findParent(searchNode, root);
	}
	
	public BStarTreeNode findParent(BStarTreeNode searchNode, BStarTreeNode base)
	{
		if (searchNode == null || base == null || searchNode == base) return null;
		
		boolean isChild = false;
		
		for (int x = 0; x < base.children.length; x++)
			if (base.children[x] == searchNode)
				isChild = true;
		
		if (!isChild)
		{
			for (int x = 0; x < base.children.length; x++)
			{
				BStarTreeNode possibleParent = findParent(searchNode,base.children[x]);
				if (possibleParent != null)	
				return possibleParent;
			}	
			return null;
		}
		else return base;
	}
	
	public void redistribute(BStarTreeNode node)
	{
		int numNodes = 0;//convertNodeToIntegers(node).length;
		int minimumSize = (int)(Math.floor((double)(maxNodeSize*2-1)/3));
		boolean hasMoved;
		do
		{
			//System.out.println("Iteration");
			hasMoved  = false;
			for (int x = 0; x < node.children.length; x++)
			{
				if (maxNodeSize - spacesLeftInNode(node.children[x]) < minimumSize)
				{
					Integer newRoot = convertNodeToIntegers(node.children[x-1])[convertNodeToIntegers(node.children[x]).length-1];
					Integer oldRoot = convertNodeToIntegers(node)[x-1];
					deleteFromNode(node.children[x-1], convertNodeToIntegers(node.children[x-1])[convertNodeToIntegers(node.children[x-1]).length-1]);
					deleteFromNode(node, oldRoot);
					insertIntoNode(node, newRoot);
					insertIntoNode(node.children[x], oldRoot);
				}
			}	
		} while ( hasMoved == true );
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
		int i = 0;
		int j = 0;
		while (j < elementList.length && i < minimalList.length)
		{
			if (!elementList[j].equals(element))
			{	
				minimalList[i] = elementList[j];
				i++;
			}
			j++;
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
			{
				if (node.children != null && node.children[i-1] != null)
					return findToInsert(element,node.children[i-1]);
				else return node;
			}
			else return node;
		}
		else 
		{
			return null;
		}
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
				if (findToInsert(element) == root)
				{
					return splitRootInsert(element);
				}
				else
				{
					if (siblingsHaveSpace(nodeToInsertInto))
					{
						return reorderElementInsert(element,nodeToInsertInto);
					}
					else return splitNodeInsert(findToInsert(element),element);
				}
			}
			else return insertIntoNode(findToInsert(element),element); 
		}
	}
	
	public boolean reorderElementInsert(Integer element, BStarTreeNode node)
	{
		BStarTreeNode parent = findParent(node);
		int x = 0;
		for (; x < parent.children.length && parent.children[x] != node;x++);
		if (x > 0 && spacesLeftInNode(parent.children[x-1]) > 0)
		{
			Integer shiftKey = convertNodeToIntegers(node)[0];
			Integer temp = convertNodeToIntegers(parent)[x-1];
			deleteFromNode(parent, temp);
			deleteFromNode(node,convertNodeToIntegers(node)[0]);
			insertIntoNode(parent,shiftKey);
			insertIntoNode(node,element);
			insertIntoNode(parent.children[x-1], temp);
		}
		else if (x < maxNodeSize-1 && spacesLeftInNode(parent.children[x+1]) > 0)
		{
			
		}
		return true;
	}
	
	public boolean siblingsHaveSpace(BStarTreeNode node)
	{
		int x = 0;
		BStarTreeNode parent = findParent(node);
		for (; x < parent.children.length && parent.children[x] != node;x++);
		
		
		if (x == parent.children.length) return false;
	
		if (x == 0)
		{
			if (parent.children[1] != null)
				return (spacesLeftInNode(parent.children[1]) > 0);
		}
		else if (x == parent.children.length-1 || parent.children[x+1] == null)
		{
			//System.out.println((spacesLeftInNode(parent.children[x-1]) > 0));
			if (parent.children[x-1] != null)
				return (spacesLeftInNode(parent.children[x-1]) > 0);
		}
		else
		{
			if (parent.children[x-1] != null && parent.children[x+1] != null)
				return (spacesLeftInNode(parent.children[x-1]) > 0 || spacesLeftInNode(parent.children[x+1]) > 0);
		}
		return false;
	}

	public boolean deleteElement(int element)
	{
		return deleteElement(new Integer(element));
	}

	public boolean deleteElement(Integer element)
	{
		BStarTreeNode node = BStarSearch(element);
		
		if (node == null) return false;
		
		int minimumSize = (int)(Math.floor((double)(maxNodeSize*2-1)/3));
		
		boolean hasChildren = false;
		if (node.children != null)
		{
			for (int x = 0; x < node.children.length; x++)
				if (node.children[x] != null)
					hasChildren = true;
		}
		
		if (node != null && node != root)
		{
			 if ( hasChildren == false && convertNodeToIntegers(node).length > minimumSize)
			 {
				 deleteFromNode(node,element);
			 }
			 else 
			 {
				if (siblingsHaveSpare(node))
				{
					return reorderDelete(element,node);
				}
				else return mergeDelete(element,node);
			 }
		}
		return false;
	}
	
	public boolean reorderDelete(Integer element, BStarTreeNode node)
	{
		int minimumSize = (int)(Math.floor((double)(maxNodeSize*2-1)/3));
		BStarTreeNode parent = findParent(node);
		int x = 0;
		for (; x < parent.children.length && parent.children[x] != node;x++);
		
		if (x > 0 && countSpacesFilled(parent.children[x-1]) > minimumSize)
		{			
			Integer shiftKey = convertNodeToIntegers(node)[0];
			Integer temp = convertNodeToIntegers(parent)[x-1];
			
			deleteFromNode(parent, temp);
			
			deleteFromNode(node,convertNodeToIntegers(node)[0]);
			
			insertIntoNode(parent,shiftKey);
			insertIntoNode(node,element);
			insertIntoNode(parent.children[x-1], temp);
		}
		else if (x < maxNodeSize-1 && countSpacesFilled(parent.children[x+1]) > 0)
		{
			Integer shiftKey = convertNodeToIntegers(parent.children[x+1])[0];
			Integer temp = convertNodeToIntegers(parent)[x];
			
			deleteFromNode(parent, temp);
			deleteFromNode(parent.children[x+1], shiftKey);
			deleteFromNode(node,convertNodeToIntegers(node)[x]);
			
			insertIntoNode(parent,shiftKey);
			insertIntoNode(node,temp);
		}
		return true;
	}

	public boolean mergeDelete(Integer element, BStarTreeNode node)
	{

		
		BStarTreeNode parent = findParent(node);
		
		int numChild = -1;
		
		for (int x = 0; x < parent.children.length; x++)
		{
			if (parent.children[x] == node )
				numChild = x;
		}
		
		if (numChild == -1) return false;
		
		deleteFromNode(node, element);
		Integer [] elementList = new Integer[maxNodeSize];
		Integer [] nodeContents = convertNodeToIntegers(node);
		
		for (int x = 0; x < nodeContents.length; x++)
		{
			elementList[x] = nodeContents[x];
		}
	
		int counter = 0;
		BStarTreeNode sibling = null;
		BStarTreeNode newNode = new BStarTreeNode(maxNodeSize);
		Integer newParent;
		
		if (numChild == 0) 
		{
			sibling = parent.children[numChild+1];
		}
		else if (numChild == maxNodeSize-1)
		{
			sibling = parent.children[numChild-1];
		}
		else if (numChild > 0 && spacesLeftInNode(parent.children[numChild-1]) >= convertNodeToIntegers(node).length)
		{
			sibling = parent.children[numChild-1];
		}
		else if (numChild < maxNodeSize-1 && spacesLeftInNode(parent.children[numChild+1]) >= convertNodeToIntegers(node).length)
		{
			sibling = parent.children[numChild-1];
		}
		
		for (int x = nodeContents.length; x < maxNodeSize; x++)
		{
			elementList[x] = convertNodeToIntegers(sibling)[counter];
			counter++;
			//System.out.println(x);
		}
		Arrays.sort(elementList);
		for (int x = 0; x < elementList.length; x++)
		{
			insertIntoNode(newNode, elementList[x]);
		}
		
		if (sibling == parent.children[numChild+1])
		{
			parent.children[numChild] = newNode;
			Integer temp = convertNodeToIntegers(parent)[numChild];
			deleteFromNode(parent,convertNodeToIntegers(parent)[numChild]);
			
			insertIntoNode(newNode, temp);
			
		}
		else 
		{
			parent.children[numChild-1] = newNode;
			Integer temp = convertNodeToIntegers(parent)[numChild];
			deleteFromNode(parent,convertNodeToIntegers(parent)[numChild-1]);
			insertIntoNode(newNode, temp);
		}
		return true;
	}
	
	public BStarTreeNode BStarSearch(Integer key)
	{
		return BStarSearch(key,root);
	}
	
	public boolean siblingsHaveSpare(BStarTreeNode node)
	{
		int x = 0;
		int minSize = (int)(Math.floor((double)(maxNodeSize*2-1)/3));
		BStarTreeNode parent = findParent(node);
		for (; x < parent.children.length && parent.children[x] != node;x++);
		
		
		if (x == parent.children.length) return false;
	
		if (x == 0)
		{
			if (parent.children[1] != null)
				return (countSpacesFilled(parent.children[1]) > minSize);
		}
		else if (x == parent.children.length-1 || parent.children[x+1] == null)
		{
			if (parent.children[x-1] != null)
				return (countSpacesFilled(parent.children[x-1]) > minSize);
		}
		else
		{
			if (parent.children[x-1] != null && parent.children[x+1] != null)
				return (countSpacesFilled(parent.children[x-1]) > minSize || countSpacesFilled(parent.children[x+1]) > minSize);
		}
		return false;
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
				if (node.children != null && node.children.length > 0 && i < node.children.length -1)
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

	public boolean splitRootInsert(Integer element)
	{
		if (root == null) return false;
		
		Integer [] rootArray = new Integer[convertNodeToIntegers(root).length+1];
		Integer [] currentElements = convertNodeToIntegers(root);
		
		for (int x = 0; x < currentElements.length; x++)
		{
			rootArray[x] = currentElements[x];
		}
		rootArray[rootArray.length -1] = element;
		Arrays.sort(rootArray);
		
		int numLeft = (int)(Math.ceil((double)(currentElements.length)/2));
		
		BStarTreeNode leftNode = new BStarTreeNode(maxNodeSize);
		BStarTreeNode rightNode = new BStarTreeNode(maxNodeSize);
		
		for (int x = 0; x < numLeft; x++)
			insertIntoNode(leftNode, rootArray[x]);
		
		String nullString = "";
		for (int x = 0; x < rootSize; x++)
			nullString += "[]";
			
		root.keys = nullString;
		
		insertIntoNode(root,rootArray[numLeft]);
		
		for (int x = numLeft+1; x < rootArray.length; x++)
			insertIntoNode(rightNode, rootArray[x]);
		root.children[0] = leftNode;
		root.children[1] = rightNode;
		return true;
	}
	
	public boolean splitNodeInsert(BStarTreeNode node, Integer element)
	{
		BStarTreeNode parent = findParent(node);
		
		int numChild = -1;
		
		for (int x = 0; x < parent.children.length; x++)
		{
			if (parent.children[x] == node )
				numChild = x;
		}
		
		if (numChild == -1) return false;	
		//System.out.println("Parent is " + convertNodeToIntegers(parent)[numChild-1]);
		Integer [] elementList = new Integer[maxNodeSize+1];
		Integer [] nodeContents = convertNodeToIntegers(node);
		
		for (int x = 0; x < maxNodeSize; x++)
		{
			elementList[x] = nodeContents[x];
		}
		elementList[elementList.length-1] = element;
		Arrays.sort(elementList);
	
		BStarTreeNode newNodeLeft = new BStarTreeNode(maxNodeSize);
		BStarTreeNode newNodeRight = new BStarTreeNode(maxNodeSize);
		int splitter = (int)(Math.floor(2*(maxNodeSize+1)-1)/(double)(3));
		insertIntoNode(parent, elementList[splitter]);
		
		for (int x = 0; x < splitter; x++)
		{
			insertIntoNode(newNodeLeft, elementList[x]);
		}
		for (int x = splitter+1; x < elementList.length; x++)
		{
			insertIntoNode(newNodeRight, elementList[x]);
		}		
		//System.out.println("Num child " + numChild);
		
		parent.children[numChild] = newNodeLeft;
		if (numChild+1 < parent.children.length)
			parent.children[numChild+1] = newNodeRight;		
		//redistribute(parent);
		return true;
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
		if (node == null) return null;		
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
			if (numbers[x].matches("-?\\d+"))
			{
				valueArray[x] = new Integer(Integer.parseInt(numbers[x]));
			}
			else
			{
				//System.out.println(numbers[x]);
				valueArray[x] = null;		
			}
		}
		return valueArray;
	}
}