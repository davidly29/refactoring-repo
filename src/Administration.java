import javax.swing.*;

public class Administration {


    public static boolean verifyAdmin() {
        boolean admin = false;
        boolean pass = false;

        while (!admin) {
            Object adminUsername = JOptionPane.showInputDialog(null, "Enter Administrator Username:");
            if (!adminUsername.equals("admin"))//search admin list for admin with matching admin username
            {
                int reply = JOptionPane.showConfirmDialog(null, "Incorrect Username. Try again?", "Username Error", JOptionPane.ERROR_MESSAGE, JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {

                } else if (reply == JOptionPane.NO_OPTION) {
//                    mainFrame.dispose();
//                    secondaryFrame.dispose();
                }
            } else {
                admin = true;
            }
        }
        while (!pass) {
            Object adminPassword = JOptionPane.showInputDialog(null, "Enter Administrator Password;");
            if (!adminPassword.equals("admin11"))//search admin list for admin with matching admin password
            {
                int reply = JOptionPane.showConfirmDialog(null, "Incorrect Password. Try again?", "Password Error", JOptionPane.ERROR_MESSAGE, JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {

                } else if (reply == JOptionPane.NO_OPTION) {
                    pass = true;
//                    secondaryFrame.dispose();
//                    menuStart();
                }
            } else {
                pass = true;
            }
        }
        if (pass & admin) {
            return true;
        }
        return false;
    }
}
