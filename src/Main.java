

public class Main {
    public static void main(String[] argv) {
//        ScanerPR scaner = new ScanerPR("/home/az/IdeaProjects/TYaP/src/input2.txt");
////        LL1 ll1 = new LL1();
////        int resAnalise = ll1.LL1Analise(scaner);
//        PrecedenceAnalyz precedenceAnalyz = new PrecedenceAnalyz();
//        int resAnalise = precedenceAnalyz.analyz(scaner);
//        if (resAnalise == 1) {
//            System.out.println("Синтаксических ошибок не обнаружено");
//        } else {
//            System.out.println("Синтаксические ошибки обнаружены");
//        }
        Scaner scaner = new Scaner("/home/az/IdeaProjects/TYaP/src/input.txt");
        Diagram diagram = new Diagram(scaner);
        diagram.PROG();
        System.out.println(diagram.makeReport());

        char[] lex = new char[Constants.MAX_LEX];
        //int typeLex = scaner.processScanner(lex);
        //if (typeLex == Constants.END)
        //    System.out.println("Синтаксических ошибок не обнаружено");
       // else
       //     scaner.PrintError("Лишний текст в конце программы!".toCharArray(), "".toCharArray());

//        do {
//            typeLex = scaner.processScanner(lex);
//            System.out.println(String.valueOf(lex) + " type: " + typeLex);
//        } while (typeLex != Constants.END);
    }
}
