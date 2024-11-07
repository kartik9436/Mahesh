import java.io.*;
import java.util.*;

class MnemonicTable {
    public String mnemonic;
    public String opcode;
    public int num;

    public MnemonicTable(String mnemonic, String opcode, int num) {
        this.mnemonic = mnemonic;
        this.opcode = opcode;
        this.num = num;
    }
}

public class Pass_1 {

    Map<String, MnemonicTable> is = new Hashtable<>();
    ArrayList<String> symtab = new ArrayList<>();
    ArrayList<Integer> symaddr = new ArrayList<>();
    ArrayList<String> littab = new ArrayList<>();
    ArrayList<Integer> litaddr = new ArrayList<>();
    ArrayList<Integer> pooltab = new ArrayList<>();
    int LC = 0;

    public void createIS() {
        MnemonicTable m1 = new MnemonicTable("STOP", "00", 0);
        is.put("STOP", m1);
        MnemonicTable m2 = new MnemonicTable("ADD", "01", 0);
        is.put("ADD", m2);
        MnemonicTable m3 = new MnemonicTable("SUB", "02", 0);
        is.put("SUB", m3);
        MnemonicTable m4 = new MnemonicTable("MULT", "03", 0);
        is.put("MULT", m4);
        MnemonicTable m5 = new MnemonicTable("MOVER", "04", 0);
        is.put("MOVER", m5);
        MnemonicTable m6 = new MnemonicTable("MOVEM", "05", 0);
        is.put("MOVEM", m6);
        MnemonicTable m7 = new MnemonicTable("COMP", "06", 0);
        is.put("COMP", m7);
        MnemonicTable m8 = new MnemonicTable("BC", "07", 0);
        is.put("BC", m8);
        MnemonicTable m9 = new MnemonicTable("DIV", "08", 0);
        is.put("DIV", m9);
        MnemonicTable m10 = new MnemonicTable("READ", "09", 0);
        is.put("READ", m10);
        MnemonicTable m11 = new MnemonicTable("PRINT", "10", 0);
        is.put("PRINT", m11);
    }

    public void generateIC() throws IOException {
        try (BufferedWriter wr = new BufferedWriter(new FileWriter("ic.txt"));
                BufferedReader br = new BufferedReader(new FileReader("input.asm"))) {

            String line;
            pooltab.add(0, 0);
            wr.write("---------------------\n  Intermediate Code\n---------------------\n");

            while ((line = br.readLine()) != null) {
                String[] split = line.trim().split("\\s+");
                if (split.length > 0 && !split[0].isEmpty()) {
                    if (!symtab.contains(split[0])) {
                        symtab.add(split[0]);
                        symaddr.add(LC);
                    } else {
                        int index = symtab.indexOf(split[0]);
                        symaddr.set(index, LC);
                    }
                }

                switch (split[1]) {
                    case "START":
                        LC = Integer.parseInt(split[2]);
                        wr.write("(AD,01)(C," + split[2] + ")\n");
                        break;
                    case "ORIGIN":
                        LC = getAddress(split[2]);
                        break;
                    case "EQU":
                        int addr = getAddress(split[2]);
                        int index = symtab.indexOf(split[0]);
                        if (index == -1) {
                            symtab.add(split[0]);
                            symaddr.add(addr);
                        } else {
                            symaddr.set(index, addr);
                        }
                        break;
                    case "LTORG":
                    case "END":
                        for (int i = pooltab.get(pooltab.size() - 1); i < littab.size(); i++) {
                            if (litaddr.get(i) == 0) {
                                litaddr.set(i, LC++);
                            }
                        }
                        if (!split[1].equals("END")) {
                            pooltab.add(littab.size());
                            wr.write("(AD,05)\n");
                        } else {
                            wr.write("(AD,04)\n");
                        }
                        break;
                    case "DS":
                        LC += Integer.parseInt(split[2]);
                        wr.write("(DL,01) (C," + split[2] + ")\n");
                        break;
                    case "DC":
                        LC++;
                        wr.write("(DL,02) (C," + split[2].replace("'", "") + ")\n");
                        break;
                    default:
                        if (is.containsKey(split[1])) {
                            wr.write("(IS," + is.get(split[1]).opcode + ") ");
                            processOperands(wr, split);
                            LC++;
                        }
                        break;
                }
            }
        }

        writeTables();
    }

    private void processOperands(BufferedWriter wr, String[] split) throws IOException {
        if (split.length > 2 && split[2] != null) {
            String reg = split[2].replace(",", "");
            switch (reg) {
                case "AREG":
                    wr.write("(1) ");
                    break;
                case "BREG":
                    wr.write("(2) ");
                    break;
                case "CREG":
                    wr.write("(3) ");
                    break;
                case "DREG":
                    wr.write("(4) ");
                    break;
                default:
                    writeSymbolReference(wr, reg);
                    break;
            }
        }

        if (split.length > 3 && split[3] != null) {
            if (split[3].contains("=")) {
                String literal = split[3].replace("=", "").replace("'", "");
                writeLiteralReference(wr, literal);
            } else {
                writeSymbolReference(wr, split[3]);
            }
        }
    }

    private void writeSymbolReference(BufferedWriter wr, String symbol) throws IOException {
        if (!symtab.contains(symbol)) {
            symtab.add(symbol);
            symaddr.add(0);
        }
        wr.write("(S," + symtab.indexOf(symbol) + ")\n");
    }

    private void writeLiteralReference(BufferedWriter wr, String literal) throws IOException {
        if (!littab.contains(literal)) {
            littab.add(literal);
            litaddr.add(0);
        }
        wr.write("(L," + littab.indexOf(literal) + ")");
    }

    private int getAddress(String expression) {
        int temp = 0;
        if (expression.contains("+")) {
            String[] sp = expression.split("\\+");
            int ad = symaddr.get(symtab.indexOf(sp[0]));
            temp = ad + Integer.parseInt(sp[1]);
        } else if (expression.contains("-")) {
            String[] sp = expression.split("\\-");
            int ad = symaddr.get(symtab.indexOf(sp[0]));
            temp = ad - Integer.parseInt(sp[1]);
        }
        return temp;
    }

    private void writeTables() throws IOException {
        writeFile("sym.txt", "Symbol Table", "Symbol    Address\n", symtab, symaddr);
        writeFile("lit.txt", "Literal Table", "Literal       Address\n", littab, litaddr);
        writePoolTable();
    }

    private void writeFile(String filename, String header, String subHeader, ArrayList<String> table,
            ArrayList<Integer> addr) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("-------------------\n    " + header + "\n-------------------\n" + subHeader);
            for (int i = 0; i < table.size(); i++) {
                writer.write("  " + table.get(i) + "       " + addr.get(i) + "\n");
            }
        }
    }

    private void writePoolTable() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pool.txt"))) {
            writer.write(
                    "-----------------------------\n         Pool Table\n-----------------------------\nPool Index    Literal Index\n");
            for (int i = 0; i < pooltab.size(); i++) {
                writer.write("     " + i + "              " + pooltab.get(i) + "\n");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Pass_1 pass1 = new Pass_1();
        pass1.createIS();
        pass1.generateIC();
    }
}
