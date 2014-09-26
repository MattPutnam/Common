package common.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class BlockablePanel extends JLayeredPane {
  private static final long serialVersionUID = 1L;

  private static final Color BLOCKER_COLOR = ColorUtils.deriveColorAlpha(Color.GRAY, 127);
  
  private static final Font DEFAULT_BANNER_FONT = new JLabel().getFont().deriveFont(Font.ITALIC);
  
  private final JPanel _blocker;
  private final JLabel _banner;
  
  public BlockablePanel(JComponent content) {
    add(content, JLayeredPane.DEFAULT_LAYER);
    setPreferredSize(content.getPreferredSize());
    setMinimumSize(content.getMinimumSize());
    setMaximumSize(content.getMaximumSize());
    
    _blocker = new JPanel(new BorderLayout());
    _blocker.setOpaque(true);
    _blocker.setBackground(BLOCKER_COLOR);
    
    _banner = new JLabel("", SwingConstants.CENTER);
    _banner.setFont(DEFAULT_BANNER_FONT);
    
    _blocker.add(_banner, BorderLayout.CENTER);
  }
  
  public synchronized void startBlocking() {
    add(_blocker, JLayeredPane.PALETTE_LAYER);
  }
  
  public synchronized void stopBlocking() {
    remove(_blocker);
  }
  
  public synchronized void setBanner(String banner) {
    _banner.setText(banner);
  }
}
