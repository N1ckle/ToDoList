import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class ToDoListGUI extends JFrame {
  
  // Start Variables
  private int width;
  private int height;
  private JPanel panel;
  private Color bgColor = Color.cyan;
  private MyActionListener listener;
  // End Variables

  public ToDoListGUI(int w, int h) {
    this.width = w;
    this.height = h;
  }

  public void setupGUI() {
    listener = new MyActionListener();

    setLocation(960-(width/2),540-(height/2));
    setSize(width, height);
    setTitle("To Do List");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Creating and adding panel for all of our output to sit on
    panel = new JPanel();
    panel.setBackground(bgColor);
    add(panel);
    setVisible(true);
  }

  public void clearGUI() {
    panel.removeAll();
    panel.revalidate();
    panel.repaint();
  }

  public void startFrame() {
    String[] buttonText = {"Create New List", "Delete List", "Open List", "Quit"};
    String heading = "Select an option: ";
    // Setting up the gridbaylayout system to orgainize the different elements
    panel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    // Makes all of the buttons the same size
    gbc.gridwidth = 5;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    // This inset has a larger bottom to make a bigger gap between the buttons and the text
    gbc.insets = new Insets(5,5,15,5);
    panel.add(new JLabel(heading, SwingConstants.CENTER), gbc);
    // we make the bottom smaller so that there is less room between the buttons
    gbc.insets = new Insets(5,5,5,5);
    for(int i = 0; i <= buttonText.length-1; i++) {
      // Setting gridx to 0 puts the buttons into a vertical stack.
      gbc.gridx = 0;
      JButton button = new JButton(buttonText[i]);
      button.addActionListener(listener);
      panel.add(button, gbc);
    }
    // Reloading the screen
    panel.revalidate();
    boolean go = false;
    while(!go) {
      String clickedButton = listener.sendInfo();
      // Need to sleep here so that the program does not miss the call
      try {
        Thread.sleep(1);
      } catch (Exception e) {
      }

      if(clickedButton != null) {
        listener.reset();
        go = true;
        clearGUI();
        switch(clickedButton) {
          case("Create New List"):
            creationFrame();
          case("Delete List"):
            deletingFrame();
          case("Open List"):
            selectFrame();
          case("Quit"):
            System.exit(0);
          
        }
      }
    }
  }

  public void creationFrame(){
    if(new File("Lists/").listFiles().length < 5){
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridwidth = 5;
      gbc.gridx = 1;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(5,5,10,5);
      panel.add(new JLabel("Enter the name of your list: "), gbc);
      JTextField input = new JTextField("Name Goes Here");
      panel.add(input, gbc);
      JButton button = new JButton("Submit");
      button.addActionListener(listener);
      panel.add(button, gbc);
      panel.revalidate();
      
      boolean go = false;
      while(!go){
        String clickedButton = listener.sendInfo();
        // Need to sleep here so that the program does not miss the call
        try {
          Thread.sleep(1);
        } catch (Exception e) {
        }
  
        if(clickedButton != null) {
          String in = input.getText();

          listener.reset();
          go = true;
          clearGUI();

          openFrame(in);
        }
      }
    }
    else{
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridx = 1;
      gbc.insets = new Insets(5,5,15,5);
      panel.add(new JLabel("You have too many lists open already."), gbc);
      JButton button = new JButton("Continue");
      panel.add(button, gbc);
      button.addActionListener(listener);
      panel.revalidate();
    
      boolean go = false;
      while(!go) {
        String clickedButton = listener.sendInfo();
        // Need to sleep here so that the program does not miss the call
        try {
          Thread.sleep(1);
        } catch (Exception e) {
        }
  
        if(clickedButton != null) {
          listener.reset();
          go = true;
          clearGUI();
          startFrame();
        }
      }
    }
  }

  public void deletingFrame(){
    if(new File("Lists/").listFiles().length != 0){
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridx = 1;
      gbc.insets = new Insets(5,5,15,5);
      panel.add(new JLabel("Select the list that you would like to delete:"), gbc);
      gbc.insets = new Insets(5,5,5,5);
  
      File[] files = new File("Lists/").listFiles();
      String[] listNames = new String[5];
      int i = 0;
      for (File file : files) {
        String fileName = file.getName();
        listNames[i] = fileName.substring(0,fileName.length()-4);
        JButton button = new JButton(listNames[i]);
        button.addActionListener(listener);
        panel.add(button, gbc);
        
  
       i++;
      }
  
      panel.revalidate();
  
      boolean go = false;
      while(!go){
        String clickedButton = listener.sendInfo();
        // Need to sleep here so that the program does not miss the call
        try {
          Thread.sleep(1);
        } catch (Exception e) {
        }
  
        if(clickedButton != null) {
          listener.reset();
          go = true;
          clearGUI();
          ToDoList list = new ToDoList(clickedButton, "Lists/");
          list.deleteList();
          startFrame();
        }
      }
    }
    else{
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridx = 1;
      gbc.insets = new Insets(5,5,15,5);
      panel.add(new JLabel("You have no lists."), gbc);
      JButton button = new JButton("Continue");
      panel.add(button, gbc);
      button.addActionListener(listener);
      panel.revalidate();
    
      boolean go = false;
      while(!go) {
        String clickedButton = listener.sendInfo();
        // Need to sleep here so that the program does not miss the call
        try {
          Thread.sleep(1);
        } catch (Exception e) {
        }
  
        if(clickedButton != null) {
          listener.reset();
          go = true;
          clearGUI();
          startFrame();
        }
      }
    }
  }

  public void selectFrame(){
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.insets = new Insets(5,5,15,5);
    panel.add(new JLabel("Select the list that you would like to open:"), gbc);
    gbc.insets = new Insets(5,5,5,5);

    File[] files = new File("Lists/").listFiles();
    String[] listNames = new String[5];
    int i = 0;
    for (File file : files) {
      String fileName = file.getName();
      listNames[i] = fileName.substring(0,fileName.length()-4);
      JButton button = new JButton(listNames[i]);
      button.addActionListener(listener);
      panel.add(button, gbc);
      

     i++;
    }

    panel.revalidate();

    boolean go = false;
    while(!go){
      String clickedButton = listener.sendInfo();
      // Need to sleep here so that the program does not miss the call
      try {
        Thread.sleep(1);
      } catch (Exception e) {
      }

      if(clickedButton != null) {
        listener.reset();
        go = true;
        clearGUI();
        openFrame(clickedButton);
      }
    }

  }

  public void openFrame(String name){
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = 5;
    gbc.gridx = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    

    ToDoList activeList = new ToDoList(name, "Lists/");
    gbc.insets = new Insets(5,5,30,5);
    panel.add(new JLabel(name + ":", SwingConstants.CENTER), gbc);
    panel.add(new JLabel(activeList.toString()), gbc);
    panel.add(new JLabel("<html>Enter +itemName or -itemName to<br>change the list. Enter EXIT to quit.</html>"), gbc);
    gbc.insets = new Insets(5,5,5,5);
    JTextField input = new JTextField();
    panel.add(input,gbc);
    JButton button = new JButton("Submit");
    button.addActionListener(listener);
    panel.add(button, gbc);

    panel.revalidate();

    boolean go = false;
    while(!go) {
      String clickedButton = listener.sendInfo();
      // Need to sleep here so that the program does not miss the call
      try {
        Thread.sleep(1);
      } catch (Exception e) {
      }

      if(clickedButton != null) {
        listener.reset();
        
        try {
          String in = input.getText().trim();
        if(in.charAt(0) == '+'){
          String addition = in.substring(1);
          activeList.addItem(addition);
          go = true;
          clearGUI();
          openFrame(name);
        }

        else if(in.charAt(0) == '-'){
          String removal = in.substring(1);
          activeList.removeItem(removal);
          go = true;
          clearGUI();
          openFrame(name);
        }

        else if(in.equals("EXIT")){
          go = true;
          clearGUI(); 
          startFrame();
        }
        } catch (StringIndexOutOfBoundsException e) {
          // No code needed, just to stop issue when submit is hit without entering anything
        }
        
      }
    }

  }


  

  public void runGUI() {
    setupGUI();
    startFrame();
  }




  private class MyActionListener implements ActionListener {
    private String clickedButton = null;

    @Override
    public void actionPerformed(ActionEvent ae) {
      clickedButton = ae.getActionCommand();
    }
    
    private String sendInfo(){
      return clickedButton;
    }

    private void reset(){
      clickedButton = null;
    }

  }
}
