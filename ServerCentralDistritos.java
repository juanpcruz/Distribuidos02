import java.util.ArrayList;

public class ServerCentralDistritos extends Thread {
    ArrayList<ArrayList> distritos;
    public ServerCentralDistritos(ArrayList distrito){
        distritos = distrito;
        ArrayList distritos1 = new ArrayList();
        distritos1.add("ip");
        distritos1.add("puerto");
        distritos1.add("nombre");

        distritos.add(distritos1);

        ArrayList distrito2 = new ArrayList();
        distrito2.add("ip2");
        distrito2.add("puerto2");
        distrito2.add("nombre2");
        distritos.add(distrito2);
    }
}
