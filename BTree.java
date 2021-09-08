/*************************************
 * 
 * Assignment-1
 * Srikanth Kollimerla
 * RedId: 825852359
 * 
 **************************************/

/**
 * The BTree program stores the student details lexicographically
 * and performs the following actions:
 * 
 * <ul> 
 *      <li>Returns the k'th element in the B-tree in lexicographical order. If k is
 *      out-of-bounds throw an exception.</li>
 *      <li>Print out the Red Ids of the students that are on probation (GPA less than 2.85) that in the
 *      list from the front to the back of the list.</li>
 *      <li>Print out the names of the students with GPA of 4.0 in the list from the back to the front of the 
 *      list</li>
 * </ul>
 */

import java.util.*;

public class BTree {

  private Node root;
  private Map < String, Student > mapStudentInformation;
  private List < Student > studentObjects;
  public static final double PROBATATION = 2.85;
  public static final double MAX_GPA = 4;
  public static final int MAX_KEYS = 2;
  public static final int MAX_CHILDREN = 3;

  private BTree() {
    root = new Node();
    studentObjects = new ArrayList < Student > ();
    mapStudentInformation = new HashMap < String, Student > ();
    root.count = 0;
    root.isLeafNode = true;
  }

  /**
   * This method is invoked after initializing the data into student objects and 
   * inserts the newly added node into the appropriate position
   * @param insertedNode this parameter contains the leaf Node/ nonleaf nodes where the new Student records needs to be added (datatype: Node)
   * @param studentName, this parameter contains the student Name that needs to be added
   * @return unused
   */

  private void findNode(Node insertedNode, String studentName) {
    if (insertedNode.isLeafNode) { //checks if the given node is a leaf or not.
      int iterator = 0;
      // the code below helps to find the insert position of the new student. 
      for (iterator = insertedNode.count - 1; iterator >= 0 && insertedNode.compareString(studentName, insertedNode.studentName[iterator]) < 0; iterator--) {
        insertedNode.studentName[iterator + 1] = insertedNode.studentName[iterator];
      }
      insertedNode.studentName[iterator + 1] = studentName;
      insertedNode.count = insertedNode.count + 1; //increments the count of the node.
    } else {
      /**
       * if the given node is not a leaf, navigate to its child node and check if the child node is full or empty, 
       * if the child node has space for a new key, perform the insertion operation (using insertvalue method)
       * else split the node and repeat the process recursively
       * until we find a child node that has an empty space to add a new key.
       */
      int iterator = 0;
      for (iterator = insertedNode.count - 1; iterator >= 0 && insertedNode.compareString(studentName, insertedNode.studentName[iterator]) < 0; iterator--) {};
      iterator++;
      Node temporaryNode = insertedNode.childNodes[iterator];
      if (temporaryNode.count == 5) {
        splitNode(insertedNode, iterator, temporaryNode);
        if (insertedNode.compareString(studentName, insertedNode.studentName[iterator]) > 0) {
          iterator++;
        }
      }
      findNode(insertedNode.childNodes[iterator], studentName);
    }

  }

  /**
   * this method maps student name to the student object stored in an array list.
   * @param name this parameter accepts the students name (datatype: string) 
   * @param redId this parameter accepts the students id (datatype: integer)
   * @param gpa this parameter accepts the students gpa (datatype: double)
   * @return unused
   */
  private void insertStudent(String studentName, int redId, double gpa) {
    studentObjects.add(new Student(studentName, redId, gpa)); //adding the student details into the student object
    mapStudentInformation.put(studentName, studentObjects.get(studentObjects.size() - 1));
    //mapping the student string with the new student object added.
  }

  /**
   * This method initialize the student details into the student node.
   * and then check if the node is filled with keys or not.
   *     if the given node is full, split method is called to break the node and create the new nodes
   * 
   *     if the given node is empty, then the new student element is added directly to the existing node.  
   * 
   * @param key this parameter accepts student's name (datatype: String)
   * @param redId this parameter accepts students redid (datatype: integer)
   * @param gpa this parameter accepts gpa as input (datatype: float)
   * @return unused
   */
  private void insertNode(final String studentName, int redId, double gpa) {
    insertStudent(studentName, redId, gpa);
    Node currentNode = root;
    if (currentNode.count == 5) { //  keys are stored in buffer and used in split function when buffer is full.
      Node newNode = new Node();
      root = newNode;
      newNode.isLeafNode = false;
      newNode.count = 0;
      newNode.childNodes[0] = currentNode;

      splitNode(newNode, 0, currentNode);
      findNode(newNode, studentName);
    } else {
      findNode(currentNode, studentName);
    }
  }

  /**
   * This method helps to spilt the node and reassign the child nodes too, if there are any childnodes.
   * @param newNode takes the parameter "newNode" which is created in the "insert" method (datatype: Node)
   * @param pos takes the parameter "position" from which the students are swapped and added (datatype: integer)
   * @param currentNode contains the details in which the entire tree structure is bundled. (datatype: Node)
   * @return unused
   */

  private void splitNode(Node parentNode, int position, Node leftNode) {
    Node rightNode = new Node();
    rightNode.isLeafNode = leftNode.isLeafNode;
    rightNode.count = MAX_KEYS;
    for (int j = 0; j < MAX_KEYS; j++) {
      rightNode.studentName[j] = leftNode.studentName[j + MAX_CHILDREN];
    } //transfers all the excess keys to the newly created node.
    if (!leftNode.isLeafNode) {
      for (int j = 0; j < MAX_CHILDREN; j++) {
        rightNode.childNodes[j] = leftNode.childNodes[j + MAX_CHILDREN];
      }
    } //transforms all the child nodes into the newly created node if its not a leaf node.

    leftNode.count = MAX_KEYS;
    for (int j = parentNode.count; j >= position + 1; j--) {
      parentNode.childNodes[j + 1] = parentNode.childNodes[j];
    }
    parentNode.childNodes[position + 1] = rightNode;

    for (int j = parentNode.count - 1; j >= position; j--) {
      parentNode.studentName[j + 1] = parentNode.studentName[j];
    }
    parentNode.studentName[position] = leftNode.studentName[MAX_KEYS];
    parentNode.count += 1;
    //adjust the nodes and children in the parent node to include the newly added node.
  }

  /** 
   * the methods showElement(), checkIndex() are used to display the kth element. Prior to display, it checks if the kth element exists or not.
   * @param x
   * @exception IndexOutofBound 
   * @return unused
   */
  private void showElement(int position) {
    checkIndex(position);
    showElement(root, position, 0);
  }

  private void checkIndex(int position) {
    try {
      if (studentObjects.size() < position) {
        throw new IndexOutOfBoundsException("Index " + position + " is out of bounds!");
      }
    } catch (Exception e) {
      System.out.println(e.getLocalizedMessage());
    }
  }

  private void showElement(Node node, int kPosition, int count) {

    for (int i = 0; i < node.count; i++) {
      count++;
      if (kPosition == count) {
        System.out.println("Element at position " + kPosition + ": " + node.studentName[i]);
      }
    }
    if (!node.isLeafNode) {
      for (int i = 0; i < node.count + 1; i++) {
        showElement(node.childNodes[i], kPosition, count);
      }
    }
  }

  /**
   * this method displays the red ids of the students who has a GPA of less than 2.85 
   * Red Ids are displayed front to the back of the list names.
   * 
   */

  private void showProbation() {
    showProbation(root);
  }
  private void showProbation(Node node) {
    for (int i = 0; i < node.count; i++) {
      //System.out.println("GPA: "+mapp.get(x.key[i]).GPA);
      if (mapStudentInformation.get(node.studentName[i]).gpa < PROBATATION)
        System.out.println("Probation Student RedId: " + mapStudentInformation.get(node.studentName[i]).redId);
    }
    if (!node.isLeafNode) {
      for (int i = 0; i < node.count + 1; i++) {
        showProbation(node.childNodes[i]);
      }
    }
  }

  /**
   *this method showGPA4 prints the red Ids of the students who has GPA of 4.0 from the back to the front of the list.
   the idea is to iterate to all the possible child nodes initially and than start displaying all the nodes at the end
   and prints in reverse order.
   */

  private void showGPA4() {
    showGPA4(root);
  }

  private void showGPA4(Node node) {

    if (!node.isLeafNode) {
      for (int i = 0; i < node.count + 1; i++) {
        showGPA4(node.childNodes[i]);
      }
    }

    for (int i = node.count - 1; i >= 0; i--) {
      //System.out.println("GPA: "+mapp.get(x.key[i]).GPA);
      if (mapStudentInformation.get(node.studentName[i]).getGpa() == MAX_GPA)
        System.out.println("GPA is equal to 4, Name: " + mapStudentInformation.get(node.studentName[i]).studentName);
    }
  }

  /**
   * This is the main method which makes the use of all the above methods
   * @param args Unused
   * @return nothing
   */

  public static void main(String[] args) {

    //Inserting new records into Btree, Parameters Order: Name, RedId, GPA
    BTree btree = new BTree();
    btree.insertNode("mark", 1456, 4);
    btree.insertNode("adam", 1458, 4);
    btree.insertNode("aaron", 1459, 4);
    btree.insertNode("martyn", 1457, 2.5);
    btree.insertNode("venus", 1461, 1.5);

    //method that prints kth element in the tree, input parameter: position of the element(integer data type)
    btree.showElement(2);

    //method that prints the redids who are in probation
    btree.showProbation();

    //method that print the students who has a GPA of 4.
    btree.showGPA4();
  }
}