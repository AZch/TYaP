public class PrecedenceAnalyz {
    private int[] magazine = new int[5000];
    private int pointer = 0;

    public int analyz(ScanerPR scaner) {
        String[][] PrecedentMatrix = {
                {"",   "<=", "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "" },
                {"",   ">",  "<=", "",   ">!=","",   "",   "",   "",   "",   ">!=",">",  ">",  "<=", ">!=","<=", ">",  "",   "",   "",   "",   "",   "",   "",   "",   "",   "" },
                {"",   "<=", "<=", "<=", "",   "",   "",   "",   "",   "<=", "<=", "<=", "<=", "<=", "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "<=", "",   "" },
                {"<=", ">",  ">",  ">",  ">!=","<=", "",   "",   ">",  "<=", "",   ">",  ">",  "",   "",   "",   ">",  "",   "",   "",   "",   "",   "",   "",   "",   "",   ">"},
                {"<=", "<=", ">",  ">",  "",   "<=", "",   "",   "",   "<=", "",   "<=", "<=", "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   ">",  "",   ">"},
                {"",   "",   "",   "",   "",   "",   "<=", "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "" },
                {"",   "",   "",   "",   "",   "",   "",   "<=", "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "" },
                {"",   "",   "<=", "",   "",   "",   "",   "",   "<=", "",   "",   "",   "",   "",   "",   "",   "",   "",   "<=", "<=", "<=", "<=", "<=", "<=", "",   "",   "" },
                {">",  ">",  ">",  "<=", "",   ">",  "",   "",   ">",  ">",  "",   ">",  ">",  "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "<=", ">"},
                {"",   "<=", "<=", "",   "",   "",   "",   "",   "",   "<=", "",   "<=", "<=", "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "" },
                {"",   "<=", "<=", ">",  "<=", "",   "",   "",   "",   "",   "",   ">",  ">",  "",   "<=", "",   "",   "<=", "<=", "<=", "<=", "<=", "<=", "<=", ">",  "",   "" },
                {"",   ">",  "",   "",   "<=", "",   "",   "",   "",   "",   "",   ">",  ">",  "<=", "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "" },
                {"",   ">",  "",   "",   "<=", "",   "",   "",   "",   "",   "",   ">",  ">",  "<=", "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "" },
                {"",   "<=", "<=", ">",  ">",  "",   "",   "",   "",   "",   "",   "",   "",   ">",  "<=", "",   "",   "",   "<=", "<=", "<=", "<=", "<=", "<=", "",   "",   "" },
                {"",   "<=", "",   "",   ">",  "",   "",   "",   "",   "",   ">",  "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "" },
                {"",   "<=", "<=", "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "<=", "<=", "",   "",   "",   "",   "",   "",   "",   "",   "" },
                {"",   "",   "",   "",   ">",  "",   "",   "",   "",   "",   ">",  "",   "",   "",   "",   ">",  ">",  "",   "",   "",   "",   "",   "",   "",   "",   "",   "" },
                {"",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   ">",  "",   "",   "",   "",   "",   "",   "",   "",   "",   "" },
                {"",   "<=", "",   "",   ">",  "",   "",   "<=", ">",  "",   "",   "",   "",   "",   "",   "",   "",   "<=", ">",  "<=", "<=", "<=", "<=", "<=", "",   "",   "" },
                {"",   "<=", "",   "",   ">",  "",   "",   "<=", ">",  "",   "",   "",   "",   "",   "",   "",   "",   "<=", ">",  ">",  "<=", "<=", "<=", "<=", "",   "",   "" },
                {"",   "<=", "",   "",   ">",  "",   "",   "<=", ">",  "",   "",   "",   "",   "",   "",   "",   "",   "<=", ">",  ">",  ">",  "<=", "<=", "<=", "",   "",   "" },
                {"",   "<=", "",   "",   ">",  "",   "",   "<=", ">",  "",   "",   "",   "",   "",   "",   "",   "",   "<=", ">",  ">",  ">",  ">",  "<=", "<=", "",   "",   "" },
                {"",   "<=", "",   "",   ">",  "",   "",   "<=", ">",  "",   "",   "",   "",   "",   "",   "",   "",   "<=", ">",  ">",  ">",  ">",  ">",  "<=", "",   "",   "" },
                {"",   "<=", "",   "",   ">",  "",   "",   "<=", ">",  "",   "",   "",   "",   "",   "",   "",   "",   "<=", ">",  ">",  ">",  ">",  ">",  ">",  "",   "",   "" },
                {"",   "",   "",   "",   "",   "",   "",   "<=", "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "" },
                {"",   "",   ">",  ">",  "",   "",   "",   "",   ">",  "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "" },
                {"<=", "<=", "",   "",   "",   "<=", "",   "",   "",   "<=", "",   "<=", "<=", "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "",   "" }
        } ;

        int type, resCode = 1;
        int flag = 1, AKS = 99;

        char[] lex = new char[Constants.MAX_LEX], lexSec = new char[Constants.MAX_LEX];
        magazine[pointer++] = Constants.END_PR;

        type = scaner.processScanner(lex);

        while (true) {
            String attitude = "";
            try {
                attitude = PrecedentMatrix[type][magazine[pointer - 1]];
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (attitude.equals("<=")) {
                magazine[pointer++] = type;
                type = scaner.processScanner(lex);
            } else if (attitude.equals(">")) {
                // как понять что совпадает (хранить правила или смотреть по >)
                // редуцировать всегда когда встретилось >
                // но тогда ошибки никогда не будет?

                //if
                switch (magazine[pointer]) {
                    case Constants.COMMA_PR:
                        if (pointer >= 6 &&
                            magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.ASSIGN_PR &&
                            magazine[pointer - 3] == Constants.ID_PR && magazine[pointer - 4] == AKS &&
                            magazine[pointer - 5] == Constants.CONST_PR && magazine[pointer - 6] == AKS) {
                            pointer -= 6;
                        } else if (pointer >= 6 &&
                            magazine[pointer - 1] == Constants.CBC_PR && magazine[pointer - 2] == AKS &&
                            magazine[pointer - 3] == Constants.CBO_PR && magazine[pointer - 4] == Constants.ID_PR &&
                            magazine[pointer - 5] == Constants.CLASS_PR && magazine[pointer - 6] == AKS) {
                            pointer -= 6;
                        } else if (pointer >= 5 &&
                                magazine[pointer - 1] == Constants.NUM_PR && magazine[pointer - 2] == Constants.ASSIGN_PR &&
                                magazine[pointer - 3] == Constants.ID_PR && magazine[pointer - 4] == AKS &&
                                magazine[pointer - 5] == Constants.CONST_PR) {
                            pointer -= 5;
                            magazine[pointer] = AKS;
                        } else if (pointer >= 4 &&
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.ASSIGN_PR &&
                                magazine[pointer - 3] == Constants.ID_PR && magazine[pointer - 4] == AKS) {
                            pointer -= 4;
                        } else if (pointer >= 3 &&
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.ASSIGN_PR &&
                                magazine[pointer - 4] == AKS) {
                            pointer -= 3;
                        } else if (pointer >= 3 &&
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.ASSIGN_PR &&
                                magazine[pointer - 3] == Constants.ID_PR) {
                            pointer -= 3;
                            magazine[pointer] = AKS;
                        } else if (pointer >= 3 &&
                                magazine[pointer - 1] == AKS &&
                                (magazine[pointer - 2] == Constants.INT_PR || magazine[pointer - 2] == Constants.CHAR_PR || magazine[pointer - 2] == Constants.ID_PR) &&
                                magazine[pointer - 3] == AKS) {
                            pointer -= 3;
                        } else if (pointer >= 2 &&
                                magazine[pointer - 1] == AKS &&
                                (magazine[pointer - 2] == Constants.INT_PR || magazine[pointer - 2] == Constants.CHAR_PR || magazine[pointer - 2] == Constants.ID_PR)) {
                            pointer -= 2;
                            magazine[pointer] = AKS;
                        }
                        break;
                    case Constants.CBC_PR:
                        if (pointer >= 7 &&
                            magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                            magazine[pointer - 3] == Constants.RBC_PR && magazine[pointer - 4] == Constants.RBO_PR &&
                            magazine[pointer - 5] == Constants.MAIN_PR && magazine[pointer - 6] == Constants.VOID_PR &&
                            magazine[pointer - 7] == AKS) {
                            pointer -= 7;
                        } else if (pointer >= 10 &&
                            magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                            magazine[pointer - 3] == Constants.ASSIGN_PR && magazine[pointer - 4] == Constants.SBC_PR &&
                            magazine[pointer - 5] == AKS && magazine[pointer - 6] == Constants.SBO_PR &&
                            magazine[pointer - 7] == AKS && magazine[pointer - 8] == Constants.ID_PR &&
                            magazine[pointer - 9] == Constants.VIRGULE_PR && magazine[pointer - 10] == AKS) {
                            pointer -= 10;
                        } else if (pointer >= 8 &&
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                                magazine[pointer - 3] == Constants.ASSIGN_PR && magazine[pointer - 4] == Constants.SBC_PR &&
                                magazine[pointer - 5] == AKS && magazine[pointer - 6] == Constants.SBO_PR &&
                                magazine[pointer - 7] == AKS && magazine[pointer - 8] == Constants.ID_PR) {
                            pointer -= 8;
                            magazine[pointer] = AKS;
                        } else if (pointer >= 6 &&
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                                magazine[pointer - 3] == Constants.ASSIGN_PR && magazine[pointer - 4] == Constants.ID_PR &&
                                magazine[pointer - 5] == Constants.VIRGULE_PR && magazine[pointer - 6] == AKS) {
                            pointer -= 6;
                        } else if (pointer >= 5 &&
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                                magazine[pointer - 3] == Constants.VIRGULE_PR && magazine[pointer - 4] == AKS) {
                            pointer -= 4;
                        } else if (pointer >= 4 &&
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                                magazine[pointer - 3] == Constants.ASSIGN_PR && magazine[pointer - 4] == Constants.ID_PR) {
                            pointer -= 4;
                            magazine[pointer] = AKS;
                        } else if (pointer >= 3 &&
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                                magazine[pointer - 3] == Constants.ASSIGN_PR) {
                            pointer -= 3;
                            magazine[pointer] = AKS;
                        } else if (pointer >= 3 &&
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                                magazine[pointer - 3] == AKS) {
                            pointer -= 3;
                        } else if (pointer >= 2 &&
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR) { // нужно ли расписывать вложенность у фиолетового
                            pointer -= 2;
                            magazine[pointer] = AKS;
                        }
                        break;
                    case Constants.RBC_PR:
                        break;
                    default:
                        scaner.PrintError("Неверный символ".toCharArray(), lex);
                        resCode = 0;
                        break;
                }

                // редуцирование правила
                if (true) { // если основа совпадает с правилом грамматики
                    pointer--; // редуцирование магазина
                    if (magazine[pointer] == Constants.END_PR)
                        break;
                } else {
                    scaner.PrintError("Неверный символ".toCharArray(), lex);
                    resCode = 0;
                    break;
                }
            } else {
                scaner.PrintError("Неверный символ".toCharArray(), lex);
                resCode = 0;
                break;
            }
        }

        return resCode;
    }
}
