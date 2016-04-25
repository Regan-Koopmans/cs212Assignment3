/*
Name and Surname: Regan Koopmans
Student/staff Number: 15043143
*/

public class Main
{
	public static void main(String [] args)
	{
BStarTree testingTree = new BStarTree(6);
		
		System.out.println("///////////////////////////////////////////////////////////////////");
		
		System.out.println("\n \tTrivial Inserts \n");
		
		for (int x = 0; x < 40; x++)
		{
			testingTree.insertElement(new Integer(x));
			System.out.println();
			testingTree.breadthFirst();
			System.out.println();
		}
		
		System.out.println();
		System.out.println("///////////////////////////////////////////////////////////////////");
		System.out.println("\n \tSearches \n");
		
		for (int x = 0; x < 45; x++)
		{
			System.out.println(x + " : " + testingTree.search(x));
		}
		
//		for (int x = 40; x < 41; x++)
//		{
//			System.out.println(x + " : " + testingTree.search(x));
//		}
//		
		System.out.println();
		System.out.println("///////////////////////////////////////////////////////////////////");
		
		System.out.println("\n \tTrivial Deletes \n");
		
		for (int x = 0; x < 40; x++)
		{
			System.out.println("Deleting " + x);
			testingTree.deleteElement(new Integer(x));
			System.out.println();
			testingTree.breadthFirst();
			System.out.println();
		}
	}
}
