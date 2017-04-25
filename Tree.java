

import java.util.ArrayList;
import java.util.HashMap;

public class Tree {
    public static ArrayList<String> erori = new ArrayList<>();

    public static HashMap<String, Integer> hash = new HashMap();
    static int nrReturn = 0;

    public static boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }//verific daca un caracter

    public static int nrChild(int i, ArrayList<Object> input) {
        int count = 1;
        for (int c = i + 1; c < input.size(); c++) {
            if (input.get(c).equals('[')) count++;
            if (input.get(c).equals(']')) count--;
            if (count == 0) return c + 1;

        }

        return 0;
    }


    public static Node copac(int i, ArrayList<Object> input, Node nod) {
        if (i < input.size() && input.get(i).equals('[')) {
            i++;
            nod.setData(input.get(i));
            String var = input.get(i).toString();

            switch (var) {
                case "if"://if are 3 fii
                    Node x1 = Node.addChild(nod);
                    copac(i + 1, input, x1);
                    Node x2 = Node.addChild(nod);
                    copac(nrChild(i + 1, input), input, x2);
                    Node x3 = Node.addChild(nod);
                    int var1 = nrChild(i + 1, input);
                    copac(nrChild(var1, input), input, x3);
                    break;
                case "for"://are 3 fii,1 pt fiecare instructiune din for(ex ; x=1;x<n;x++)
                    Node f1 = Node.addChild(nod);
                    Node f2 = Node.addChild(nod);
                    Node f3 = Node.addChild(nod);
                    Node f4 = Node.addChild(nod);
                    copac(i + 1, input, f1);
                    int aux1 = nrChild(i + 1, input);
                    copac(nrChild(i + 1, input), input, f2);
                    copac(nrChild(aux1, input), input, f3);
                    int aux2 = nrChild(aux1, input);
                    copac(nrChild(aux2, input), input, f4);
                    break;
                case "assert"://assert are un fiu = conditia
                    Node a = Node.addChild(nod);
                    copac(i + 1, input, a);
                    break;
                case "return"://return are un fiu,valoarea/variabila de returnat
                    Node r1 = Node.addChild(nod);
                    copac(i + 1, input, r1);
                    break;
                default:

                    Node n1 = Node.addChild(nod);
                    Node n2 = Node.addChild(nod);
                    copac(i + 1, input, n1);
                    if (input.get(i + 1).equals('[')) {
                        copac(nrChild(i + 1, input), input, n2);
                    } else copac(i + 2, input, n2);


            }


        } else if (i < input.size() && !input.get(i).equals('[')) {
            nod.setData(input.get(i));
        }

        return null;

    }//construieste arborele

    public static void evalEgal(Node root) {

        String op = root.getData().toString();
        switch (op) {
            case "=":
                Node fiu1 = root.getChildren().get(0);
                Node fiu2 = root.getChildren().get(1);
                if (isNumeric(fiu2.getData().toString()))
                    hash.put(fiu1.getData().toString(), (Integer) fiu2.getData());

                hash.put(fiu1.getData().toString(), operatii(fiu2));
                break;


        }


    }//atribuie valoare

    public static boolean evalBool(Node root) {
        Node fiu1 = root.getChildren().get(0);
        Node fiu2 = root.getChildren().get(1);
        String op = root.getData().toString();
        if (!hash.containsKey(fiu1.getData().toString()) && !isNumeric(fiu1.getData().toString())) {
            erori.add("Check Failed");
            return false;
        }
        switch (op) {
            case "==":
                if (fiu2.getData().toString().equals("+") || fiu2.getData().toString().equals("*")) {
                    return fiu1.getData().equals(operatii(fiu2));
                }
                if (isNumeric(fiu1.getData().toString()) && isNumeric(fiu2.getData().toString()))
                    return fiu1.getData().equals(fiu2.getData());
                else if (!isNumeric(fiu1.getData().toString()) && isNumeric(fiu2.getData().toString())) {
                    return hash.get(fiu1.getData().toString()).equals(fiu2.getData());
                } else if (isNumeric(fiu1.getData().toString()) && !isNumeric(fiu2.getData().toString())) {
                    return hash.get(fiu2.getData().toString()).equals(fiu1.getData());
                } else {
                    return hash.get(fiu1.getData().toString()).equals(hash.get(fiu2.getData().toString()));
                }


            case "<":
                if (fiu2.getData().toString().equals("+") || fiu2.getData().toString().equals("*")) {
                    return fiu1.getData().equals(operatii(fiu2));
                }
                if (isNumeric(fiu1.getData().toString()) && isNumeric(fiu2.getData().toString()))
                    return (Integer) fiu1.getData() < (Integer) fiu2.getData();
                else if (!isNumeric(fiu1.getData().toString()) && isNumeric(fiu2.getData().toString())) {
                    return hash.get(fiu1.getData().toString()) < (Integer) fiu2.getData();
                } else if (isNumeric(fiu1.getData().toString()) && !isNumeric(fiu2.getData().toString())) {
                    return hash.get(fiu2.getData().toString()) > (Integer) fiu1.getData();
                } else if (!isNumeric(fiu1.getData().toString()) && !isNumeric(fiu2.getData().toString())) {
                    if (hash.containsKey(fiu1.getData().toString()) && hash.containsKey(fiu2.getData().toString())) {
                        return hash.get(fiu1.getData().toString()) < hash.get(fiu2.getData().toString());
                    } else {
                        erori.add("Check Failed");
                    }
                } else {
                    erori.add("Check Failed");
                }
                break;

        }
        return false;


    }//verifica < sau ==


    public static int operatii(Node root) {

        String op = root.getData().toString();
        if (root.getChildren().size() > 1) {
            Node left = root.getChildren().get(0);
            Node right = root.getChildren().get(1);

            if (hash.containsKey(left.getData().toString())) {
                switch (op) {
                    case "+":
                        return hash.get(left.getData().toString()) + operatii(right);
                    case "*":
                        return hash.get(left.getData().toString()) * operatii(right);
                    default:
                        break;

                }
            }
            if (hash.containsKey(right.getData().toString())) {
                switch (op) {
                    case "+":
                        return operatii(left) + hash.get(right.getData().toString());
                    case "*":
                        return operatii(left) * hash.get(right.getData().toString());
                    default:
                        break;

                }
            }

            if ((!isNumeric(left.getData().toString())) || (!isNumeric(right.getData().toString()))) {
                erori.add("Check Failed");
            }
            switch (op) {
                case "+":
                    return operatii(left) + operatii(right);
                case "*":
                    return operatii(left) * operatii(right);
                default:
                    break;

            }

        } else {
            String var = root.getData().toString();

            if (hash.containsKey(root.getData().toString())) {
                return hash.get(root.getData().toString());
            } else if (hash.containsKey(root.getData())) {
                return hash.get(root.getData());
            } else if (!hash.containsKey(var) && !isNumeric(var)) {
                erori.add("Check Failed");
            }
            try {
                return (Integer) root.getData();
            } catch (Exception e) {

                erori.add("Check Failed");
                return 0;
            }
        }
        return -1;
    }

    public static void evalIf(Node root) {
        Node fiu1 = root.getChildren().get(0);//conditia
        Node fiu2 = root.getChildren().get(1);//daca intra
        Node fiu3 = root.getChildren().get(2);//caz elese
        String op2 = fiu2.getData().toString();
        String op3 = fiu3.getData().toString();

        if (evalBool(fiu1)) {

            switch (op2) {
                case "=":
                    evalEgal(fiu2);
                    break;
                case "for":
                    evalFor(fiu2);
                    break;
                case "if":
                    evalIf(fiu2);
                    break;
                case "assert":
                    evalAssert(fiu2);
                    break;
                case "return":
                    evalReturn(fiu2);
                    break;

                default:
                    break;
            }
        } else {
            switch (op3) {
                case "=":
                    evalEgal(fiu3);
                    break;
                case "for":
                    evalFor(fiu3);
                    break;
                case "if":
                    evalIf(fiu3);
                    break;
                case "assert":
                    evalAssert(fiu3);
                    break;
                case "return":
                    evalReturn(fiu3);
                    break;

                default:
                    break;
            }

        }

    }

    public static void evalAssert(Node root) {
        Node fiu = root.getChildren().get(0);
        if (!evalBool(fiu)) {
            erori.add("Assert Failed");
        }

    }

    public static void evalReturn(Node root) {

        Node fiu = root.getChildren().get(0);
        String op = fiu.getData().toString();
        nrReturn++;
        if (erori.size() < 1) {
            switch (op) {
                case "+":
                    operatii(fiu);
                    if (erori.size() < 1)
                        System.out.print(operatii(fiu));
                    break;
                case "*":
                    operatii(fiu);
                    if (erori.size() < 1)
                        System.out.print(operatii(fiu));
                    break;
                default:
                    break;

            }

            if (isNumeric(op)) {
                System.out.print(op);
            } else {
                if (hash.get(op) != null)
                    System.out.print(hash.get(op));
            }

        }
    }

    public static void evalFor(Node root) {
        Node fiu0 = root.getChildren().get(0);//i=0
        Node fiu1 = root.getChildren().get(1);//i<n
        Node fiu2 = root.getChildren().get(2);//i++
        Node fiu3 = root.getChildren().get(3);//ce se face in for
        String op = fiu3.getData().toString();

        hash.put(fiu0.getChildren().get(0).getData().toString(), (Integer) fiu0.getChildren().get(1).getData());

        for (int i = hash.get(fiu0.getChildren().get(0).getData().toString()); evalBool(fiu1); evalEgal(fiu2)) {
            if (erori.size() > 0) {
                break;
            }

            switch (op) {
                case "=":
                    evalEgal(fiu3);
                    break;
                case "if":
                    evalIf(fiu3);
                    break;
                case "for":
                    evalFor(fiu3);
                    break;
                case "assert":
                    evalAssert(fiu3);
                    break;
                case "return":
                    evalReturn(fiu3);
                    break;
            }


        }


    }

    public static void eval(Node root) {
        evalTree(root);
        if (nrReturn == 0) {
            erori.add("Missing Return");
        }

        if (erori.contains("Check Failed")) {
            System.out.print("Check failed");
        } else if (erori.contains("Missing Return")) {
            System.out.print("Missing return");
        } else if (erori.contains("Assert Failed")) {
            System.out.print("Assert failed");
        }


    }


    public static void evalTree(Node root) {
        String op = root.getData().toString();
        switch (op) {
            case ";":
                for (Node child : root.getChildren()) {
                    evalTree(child);
                }
                break;
            case "for":
                if (erori.size() > 0) {
                    break;
                }
                evalFor(root);
                break;
            case "if":
                if (erori.size() > 0) {
                    break;
                }
                evalIf(root);
                break;
            case "assert":
                if (erori.size() > 0) {
                    break;
                }
                evalAssert(root);
                break;
            case "return":
                evalReturn(root);
                break;
            case "=":
                if (erori.size() > 0) {
                    break;
                }
                evalEgal(root);
                break;
            default:
                for (Node child : root.getChildren()) {
                    evalTree(child);
                }
                break;

        }

    }


}

