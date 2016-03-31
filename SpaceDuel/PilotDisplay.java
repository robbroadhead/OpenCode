//Title:
//Version:
//Copyright:
//Author:
//Company:
//Description:

package  Duel;

import java.awt.*;
import java.awt.event.*;
import borland.jbcl.layout.*;
import borland.jbcl.control.*;

public class PilotDisplay extends Dialog {
  Panel panel1 = new Panel();
  XYLayout xYLayout1 = new XYLayout();
  BevelPanel bevelPanel1 = new BevelPanel();
  Button cbPrev = new Button();
  Button cbNext = new Button();

  public PilotDisplay(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    add(panel1);
    pack();
  }

  public PilotDisplay(Frame frame, String title) {
    this(frame, title, false);
  }

  public PilotDisplay(Frame frame) {
    this(frame, "", false);
  }

  private void jbInit() throws Exception {
    xYLayout1.setWidth(320);
    xYLayout1.setHeight(243);
    cbPrev.setActionCommand("Previous");
      cbPrev.setLabel("Previous");
    cbPrev.addActionListener(new PilotDisplay_cbPrev_actionAdapter(this));
    cbNext.setBackground(Color.lightGray);
      cbNext.setLabel("Next");
    cbNext.addActionListener(new PilotDisplay_cbNext_actionAdapter(this));
    this.addWindowListener(new PilotDisplay_this_windowAdapter(this));
    panel1.setLayout(xYLayout1);
    panel1.add(bevelPanel1, new XYConstraints(9, 10, 298, 191));
    panel1.add(cbPrev, new XYConstraints(67, 211, 74, 25));
    panel1.add(cbNext, new XYConstraints(163, 211, 77, 25));
  }

  // OK
  void cbPrev_actionPerformed(ActionEvent e) {
    dispose();
  }

  // Cancel
  void cbNext_actionPerformed(ActionEvent e) {
    dispose();
  }
  
  void this_windowClosing(WindowEvent e) {
    dispose();
  }
}

class PilotDisplay_cbPrev_actionAdapter implements ActionListener{
  PilotDisplay adaptee;

  PilotDisplay_cbPrev_actionAdapter(PilotDisplay adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cbPrev_actionPerformed(e);
  }
}

class PilotDisplay_cbNext_actionAdapter implements ActionListener{
  PilotDisplay adaptee;

  PilotDisplay_cbNext_actionAdapter(PilotDisplay adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cbNext_actionPerformed(e);
  }
}

class PilotDisplay_this_windowAdapter extends WindowAdapter {
  PilotDisplay adaptee;

  PilotDisplay_this_windowAdapter(PilotDisplay adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}
 