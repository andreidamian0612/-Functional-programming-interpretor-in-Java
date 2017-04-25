import java.io.*;
import java.util.ArrayList;


/**
 * Created by dama on 14.03.2017.
 */
public class Main {

    public static boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }//verific daca un caracter
    //sir de caractere e numeric


    public static void main(String[] args) throws IOException {

        File fisier = new File(args[1]);
        if (!fisier.getParentFile().exists())
            fisier.getParentFile().mkdirs();
        if (!fisier.exists())
            fisier.createNewFile();
        FileOutputStream f = new FileOutputStream(fisier);
        System.setOut(new PrintStream(f));//redirectez outputul in fisier


        ArrayList<Object> pars = new ArrayList<Object>();


        FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr);
        String linieCrt;
        String operatori = "+-;]<*>/==";
        String operatori2 = "+-;<*>/==";
        String cifre = "0123456789";
        while ((linieCrt = br.readLine()) != null) {
            // System.out.println(linieCrt);
            for (int i = 0; i < linieCrt.length(); i++) {

                char c = linieCrt.charAt(i);
                String s = c + "";
                int count = 0;
                if (c == '[' || c == ']' || c == '<' || c == '+' || c == '-' || c == '*' || c == ';' || c == '>' || c == '/')
                    pars.add(c);
                else if (c == '=' && linieCrt.charAt(i + 1) == '=') {
                    pars.add("==");
                    i++;
                } else if (c == '=' && linieCrt.charAt(i + 1) != '=')
                    pars.add(c);
                else if (isNumeric(s)) {
                    for (int j = i; j < linieCrt.length(); j++) {

                        String s2 = linieCrt.charAt(j + 1) + "";
                        if (!isNumeric(s2)) break;
                        else {
                            s = s + s2;
                            i++;
                        }

                        //daca e cifra,verific daca e de mai multe cifre
                    }

                    pars.add(new Integer(s));

                } else if (c == 'r' && linieCrt.charAt(i + 5) == 'n') {
                    i = i + 5;//verific daca e return
                    pars.add("return");
                } else if (c == 'f' && linieCrt.charAt(i + 2) == 'r') {
                    pars.add("for");
                    i = i + 2;
                } else if (c == 'a' && linieCrt.charAt(i + 1) == 's' && linieCrt.charAt(i + 5) == 't') {
                    i = i + 5;
                    pars.add("assert");
                } else if (c == 'i' && linieCrt.charAt(i + 1) == 'f') {
                    i = i + 1;
                    pars.add("if");
                } else if (c != ' ' && c != '\t') pars.add(c);


            }


        }
        Node root = new Node(null);
        Tree.copac(0, pars, root);
        //Node.printTree(root," ");


        Tree.eval(root);


        //System.out.println(Tree.erori);


    }

}


