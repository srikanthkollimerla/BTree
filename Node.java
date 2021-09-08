/**
 * This Node class contains the information related to a single node
 * and a method that compares the string lexicographically
 * 
 * Additional space for Strings and Nodes are created in every node to act as buffer positions
 * and can be useful in splittng the node when the node gets full. 
 * 
 */
public class Node {
  String studentName[] = new String[6];
  Node childNodes[] = new Node[6];
  boolean isLeafNode = true;
  int count;

  /**
   * this method compares two strings and returns negative value when parameter 1
   * is lexcially lower than the parameter 2
   * @param newStudentName this parameter accepts student Name (datatype: String)
   * @param exisingStudentName this paramater accepts newly entered Student Name (datatype: String)
   * @returns integer: the following the scenarios: 
   *      -> negative value, if the parameter 1 is lexicographically  less than parameter 2. 
   *      -> positive value, if the parameter 1 is lexicographically  greater than parameter 1.
   */
  public int compareString(String newStudentName, String exisingStudentName) {
    return newStudentName.compareTo(exisingStudentName);
  }
}