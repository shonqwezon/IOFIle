import java.io.*;
import java.util.HashMap;

public class FileAnalyser {
    private String inFile;
    private String outFile;
    private int FILE_END = 65535;

    public FileAnalyser() {
        this(null, null);
    }

    public FileAnalyser(String inFile, String outFile) {
        this.inFile = inFile;
        this.outFile = outFile;
    }

    public void setInFile(String inFile) throws EmptyIOPathException {
        if(inFile.isEmpty())
            throw new EmptyIOPathException("Пустой путь до файла ввода");
        this.inFile = inFile;
    }

    public void setOutFile(String outFile) throws EmptyIOPathException {
        if(outFile.isEmpty())
            throw new EmptyIOPathException("Пустой путь до файла вывода");
        this.outFile = outFile;
    }

    void analyse() throws FileAnalyserException, IOException {
        if(outFile.isEmpty())
            throw new EmptyIOPathException("Пустой путь до файла вывода");
        if(inFile.isEmpty())
            throw new EmptyIOPathException("Пустой путь до файла ввода");

        HashMap<Character, Integer> map = new HashMap<>();
        FileReader inStream = new FileReader(inFile);

        if(new File(outFile).exists())
            System.out.println("Файл '" + outFile + "' существует. Идёт перезапись...");
        else
            System.out.println("Файл '" + outFile + "' не существует. Создаём...");

        FileWriter outStream = new FileWriter(outFile);
        char ch;
        int otherCharCounter = 0;
        while((ch = (char) inStream.read()) != FILE_END) {
//            From 'A' to 'Z' or from 'a' to 'z'
            if ((64 < ch && ch < 91) || (96 < ch && ch < 123))
                map.put(ch, map.getOrDefault(ch, 0) + 1);
            else
                otherCharCounter++;
        }
        inStream.close();

        for(HashMap.Entry<Character, Integer> entry : map.entrySet())
            outStream.write(entry.getKey() + ":" + entry.getValue() + "\n");
        outStream.write("others:" + otherCharCounter);
        outStream.close();

        System.out.println("Файл '" + inFile + "' успешно проанализирован");
    }
}
