import javax.swing.*;
import java.awt.event.ActionListener;

public class AlgorithmButton extends JButton {
    AlgorithmButton(String text, ActionListener a, int x , int y) {
        super(text);
        this.setBounds(x , y, 100 , 50 );
        this.addActionListener(a);
        this.setFocusable(false);
    }
}
