package common.swing;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Optional;

import javax.swing.JComponent;

public final class Tooltip extends MouseMotionAdapter {
  private Tooltip() {}
  
  public static void registerTooltip(JComponent component, TooltipGenerator generator) {
    component.addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
        final Optional<String> os = generator.generateTooltip(e);
        if (os.isPresent())
          component.setToolTipText(os.get());
      }
    });
  }
  
  public static interface TooltipGenerator {
    public Optional<String> generateTooltip(MouseEvent e);
  }
}
