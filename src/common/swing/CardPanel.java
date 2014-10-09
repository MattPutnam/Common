package common.swing;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CardPanel extends JPanel {
  private static final long serialVersionUID = 1L;
  
  public CardPanel(List<Component> contents, final List<String> names) {
    final JList<String> jlist = new JList<>(names.toArray(new String[names.size()]));
    
    final CardLayout cardLayout = new CardLayout();
    final JPanel cardPanel = new JPanel(cardLayout);
    for (int i = 0; i < names.size(); ++i) {
      cardPanel.add(new JScrollPane(contents.get(i)), names.get(i));
    }
    
    jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jlist.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        cardLayout.show(cardPanel, names.get(jlist.getSelectedIndex()));
      }
    });
    jlist.setSelectedIndex(0);
    
    final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(jlist), cardPanel);
    splitPane.setDividerLocation(0.20);
    splitPane.setBorder(null);
    setLayout(new BorderLayout());
    add(splitPane, BorderLayout.CENTER);
  }
}
