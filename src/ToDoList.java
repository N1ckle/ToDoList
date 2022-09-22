import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A class for the functionality of a ToDoList
 * 
 * @author Tyler Spaeth
 */
public class ToDoList {

  // Start Variables
  private File list;
  // End Variables
  
  /**
   * Constructor for making a new ToDoList
   * 
   * @param name the name of the list
   * @param folderName the name of the folder in which the lists should be stored.
   */
  public ToDoList(String name, String folderName) {
    if(folderName.equals("")) {
      this.list = new File(name + ".txt");
    }
    else {
      this.list = new File(folderName + "/" + name + ".txt");
    }
    try {
      list.createNewFile();
    } catch (IOException e) {
      System.out.println("IOException in constructor");
    }
  }
  /**
   * Adds the given item to the first clear line in the list
   * 
   * @param string the item that is to be added to the list
   */
  public void addItem(String string) {
    try {
      FileWriter writer = new FileWriter(list, true);
      writer.write(string + System.lineSeparator());
      writer.close();
    } catch (Exception e) {
      System.out.println("Exception in addItem");
    }
  }

  /**
   * Removes the first occurence of the item from the list and then moves everything
   * as close to line 0 as possible.
   * 
   * @param string the item that is to be remove from the list
   * @return true if the item is successfully removed, otherwise false
   */
  public boolean removeItem(String string) {
    boolean removed = false;
    try {
      FileReader reader = new FileReader(list);
      BufferedReader buffReader = new BufferedReader(reader);
      String line;
      String tempList = "";
      // Sets line equal to the next line in the file and if it in not null, it runs
      while((line = buffReader.readLine()) != null) {
        // If the line is equal to the given string it will be removed and no more lines will be
        // removed.
        if(line.trim().equals(string) && !removed) {
          removed = true;
          continue;
        }
        // This list stores all of the data from the file except for the line that was removed.
        tempList += line.trim() + "\n";
      }
      // Write the temporary list to the same file without appending on so that it also clears the 
      // file. Then we close all of the open inputs streams so they are not left running.
      FileWriter writer = new FileWriter(list, false);
      writer.write(tempList);
      writer.close();
      buffReader.close();
      reader.close();
    } catch (Exception e) {
      System.out.println("Excetption in removeItem");
    }
    return removed;
  }

  /**
   * Attempts to delete the list and return the result
   * 
   * @return true if the list is successfully deleted, otherwise false
   */
  public boolean deleteList() {
    return list.delete();
  }

  public String toString() {
    String stringVersion = "<html>";
    try {
      FileReader reader = new FileReader(list);
      BufferedReader buffReader = new BufferedReader(reader);
      String line;
      while((line = buffReader.readLine()) != null) {
        stringVersion += "-- " + line.trim() + "<br>";
      }
      stringVersion += "</html>";
      reader.close();
      buffReader.close();
    } catch (Exception e) {
      System.out.println("Exception in get string");
    }
    return stringVersion;
  }

}
