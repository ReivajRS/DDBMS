package Extra;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class Routines {
    public static boolean someThreadIsRunning(Thread[] threads) {
        for (Thread thread : threads)
            if (thread.isAlive()) {
                return true;
            }
        return false;
    }

    public static String deleteZones(String statement){
        Pattern patron = compile("\\b(estado|zona)\\s*(?:=|in|not\\s+in|!=)\\s*('([^']*)'|\\([^)]*\\))");
        Matcher matcher = patron.matcher(statement);
        while (matcher.find()) {
            String condition = matcher.group(0);
            if (matcher.group(1).equals("zona"))
                statement = statement.replace(condition, "idcliente > 0");
        }
        return statement;
    }

}
