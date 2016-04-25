public class Queue
{
  public Node root = null;

  public Node dequeue()
  {
    Node temp = root;

    if (root != null)
    {
      root = root.next;
    }

    return temp;
  }

  public void enqueue(Node node)
  {
    enqueue(node.elem);
  }

  public void enqueue(BStarTreeNode node)
  {
    if (root == null)
    {
      root = new Node(node);
      return;
    }

    Node nodePointer = root;
    while (nodePointer.next != null)
    {
      nodePointer = nodePointer.next;
    }
    nodePointer.next = new Node(node);
  }

  public boolean isEmpty()
  {
    return (root == null);
  }
}
