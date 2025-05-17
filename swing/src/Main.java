import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Minecraft Calculator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1280, 720);


            JLabel label = new JLabel("Moin", SwingConstants.CENTER);
            frame.add(label);

            frame.setVisible(true);
        });
    }
}