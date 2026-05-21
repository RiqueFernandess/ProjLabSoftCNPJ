import view.LoginFrame;

import javax.swing.*;

public class AppMain {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new LoginFrame();
        });

    }
}