/*
Name and Surname: Regan Koopmans
Student/staff Number: 15043143
*/

import junit.framework.TestCase;

public class TestAssignment extends TestCase 
{
	public void testTree()
	{
		BStarTree testingTree = new BStarTree(5);
		
		System.out.println("\n Trivial Inserts \n");
		
		for (int x = 0; x < 8; x++)
		{
			assertEquals(testingTree.insertElement(new Integer(x)),true);
			testingTree.breadthFirst();
		}
		
		assertEquals(testingTree.fullness(),100);
		
		System.out.println("\n Trivial Deletes \n");
		
		for (int x = 0; x < 7; x++)
		{
			assertEquals(testingTree.deleteElement(new Integer(x)),false);
			testingTree.breadthFirst();
		}
		
	}
}
