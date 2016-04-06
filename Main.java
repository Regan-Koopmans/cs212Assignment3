/*
Complete your details...
Name and Surname: 
Student/staff Number:
*/


public class Main
{
	public static void main(String [] args)
	{
		BStarTree myTree = new BStarTree(5);
		myTree.insertElement(4);
		System.out.println(myTree.breadthFirst());
		System.out.println(myTree.height());
	}
}
