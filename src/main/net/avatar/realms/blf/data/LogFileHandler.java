package net.avatar.realms.blf.data;

import javafx.stage.FileChooser;
import net.avatar.realms.blf.exceptions.BLFDataException;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Nokorbis on 15/02/2016.
 */
public class LogFileHandler {

    public static final FileChooser.ExtensionFilter COMPRESSED_EXTENSION = new FileChooser.ExtensionFilter("Compressed log files", "*.gz", "*.zip");
    public static final FileChooser.ExtensionFilter LOG_EXTENSION = new FileChooser.ExtensionFilter("Log files", "*.log");
    public static final FileChooser.ExtensionFilter TEXT_EXTENSION = new FileChooser.ExtensionFilter("Text files", "*.txt");

    private static final String CHARSET = "UTF-8";

    public static List<String> readFile (File file) throws BLFDataException {
        LinkedList<String> lines = new LinkedList<>();
        if (file == null) {
            throw new BLFDataException("Invalid file.", new NullPointerException());
        }

        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, CHARSET));
            String line = null;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
        } catch (FileNotFoundException e) {
           throw new BLFDataException("Was not able to find the file.", e);
        }
        catch (IOException e) {
           throw new BLFDataException("Was not able to read a line from the file.", e);
        }

        return lines;
    }

    public static void writeFile(File file, List<String> lines) throws BLFDataException {
        if (file == null) {
            throw new BLFDataException("Invalid file.", new NullPointerException());
        }

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, CHARSET));

            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            throw new BLFDataException("Was not able to create the save file.", e);
        }
    }
}
