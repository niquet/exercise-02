package utilities;

import java.io.*;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.*;
import java.util.ArrayList;

public class Logger {

    private String path;

    public Logger() {

        // path to the logfile directory
        this.path = findLogDirectory("/src/logs");
        //this.path = findLogDirectory("/lamport_timestamps/src/logs/);

    }

    public String getPath(String threadID) {

        return this.path.concat("/" + threadID + ".txt");

    }

    public String findLogDirectory(String name) {

        Path path = Paths.get("").toAbsolutePath();
        return this.path = path.toString().concat(name);

    }

    public void writeHistory(String threadID, ArrayList<String> history) {
        Path fileToDelete = Paths.get(getPath(threadID));
        try {
            Files.deleteIfExists(fileToDelete);
        } catch (IOException e) {
        }
        try {

            String pathToLogfile = getPath(threadID);
            File file = new File(pathToLogfile);

            BufferedWriter writer = new BufferedWriter(new FileWriter(pathToLogfile, false));
            for(int i = 0; i < history.size(); i++) {
                writer.write(history.get(i));
                writer.newLine();
            }
            writer.flush();


        } catch (FileNotFoundException e) {
            // TODO
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
