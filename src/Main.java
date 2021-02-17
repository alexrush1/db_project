import Core.ConnectionDriver;
import View.ConnectMenu;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        ConnectionDriver conDriver = new ConnectionDriver();
        ConnectMenu logMenu = new ConnectMenu(conDriver);
    }
}
