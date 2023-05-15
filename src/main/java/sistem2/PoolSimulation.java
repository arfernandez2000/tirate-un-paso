package sistem2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static sistem2.FileGenerator.setupBalls;
import static sistem2.GPC5.gear;
import static sistem2.GPC5.initialRs;

public class PoolSimulation {
    public static void main(String[] args) {
        try {
            for (int i = 7; i < 21; i++) {
                FileWriter myWriter = new FileWriter("src/main/resources/timesTotal/times_" + FileGenerator.white_y(i) + ".txt");
                PrintWriter printWriter = new PrintWriter(myWriter);

                printWriter.println(FileGenerator.white_y(i));

                for (int k = 0; k < 100; k++) {
                    initialRs(setupBalls(i));

                    printWriter.println(gear());
                }
                System.out.println("Fin de la iteracion " + i);
                myWriter.close();
                printWriter.close();
            }

        } catch (IOException e) {
            e.printStackTrace();;
        }

    }
}
