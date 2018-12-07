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
                        if (pointer >= 6 && magazine[pointer] == Constants.COMMA_PR && // AKS -> AKS const AKS id = AKS ;
                            magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.ASSIGN_PR &&
                            magazine[pointer - 3] == Constants.ID_PR && magazine[pointer - 4] == AKS &&
                            magazine[pointer - 5] == Constants.CONST_PR && magazine[pointer - 6] == AKS) {
                            pointer -= 6;
                        } else if (pointer >= 6 && magazine[pointer] == Constants.COMMA_PR && // AKS -> AKS class id { AKS } ;
                            magazine[pointer - 1] == Constants.CBC_PR && magazine[pointer - 2] == AKS &&
                            magazine[pointer - 3] == Constants.CBO_PR && magazine[pointer - 4] == Constants.ID_PR &&
                            magazine[pointer - 5] == Constants.CLASS_PR && magazine[pointer - 6] == AKS) {
                            pointer -= 6;
                        } else if (pointer >= 5 && magazine[pointer] == Constants.COMMA_PR && // AKS -> const AKS id = num ;
                                magazine[pointer - 1] == Constants.NUM_PR && magazine[pointer - 2] == Constants.ASSIGN_PR &&
                                magazine[pointer - 3] == Constants.ID_PR && magazine[pointer - 4] == AKS &&
                                magazine[pointer - 5] == Constants.CONST_PR) {
                            pointer -= 5;
                            magazine[pointer] = AKS;
                        } else if (pointer >= 4 && magazine[pointer] == Constants.COMMA_PR && // AKS -> AKS id = AKS ;
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.ASSIGN_PR &&
                                magazine[pointer - 3] == Constants.ID_PR && magazine[pointer - 4] == AKS) {
                            pointer -= 4;
                        } else if (pointer >= 3 && // id = AKS ;
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.ASSIGN_PR &&
                                magazine[pointer - 3] == Constants.ID_PR) {
                            pointer -= 3;
                            magazine[pointer] = AKS;
                        } else if (pointer >= 3 && magazine[pointer] == Constants.COMMA_PR && // AKS (int, char, id) AKS ;
                                magazine[pointer - 1] == AKS &&
                                (magazine[pointer - 2] == Constants.INT_PR || magazine[pointer - 2] == Constants.CHAR_PR || magazine[pointer - 2] == Constants.ID_PR) &&
                                magazine[pointer - 3] == AKS) {
                            pointer -= 3;
                        }/* else if (pointer >= 2 &&
                                magazine[pointer - 1] == AKS && // AKS (int, char, id) ;
                                (magazine[pointer - 2] == Constants.INT_PR || magazine[pointer - 2] == Constants.CHAR_PR || magazine[pointer - 2] == Constants.ID_PR)) {
                            pointer -= 2;
                            magazine[pointer] = AKS;
                        }*/
                        break;
                    case Constants.CBC_PR:
                        if (pointer >= 10 && magazine[pointer] == Constants.CBC_PR && // AKS -> AKS , id AKS [ AKS ] = { AKS }
                            magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                            magazine[pointer - 3] == Constants.ASSIGN_PR && magazine[pointer - 4] == Constants.SBC_PR &&
                            magazine[pointer - 5] == AKS && magazine[pointer - 6] == Constants.SBO_PR &&
                            magazine[pointer - 7] == AKS && magazine[pointer - 8] == Constants.ID_PR &&
                            magazine[pointer - 9] == Constants.VIRGULE_PR && magazine[pointer - 10] == AKS) {
                             pointer -= 10;
                        } else if (pointer >= 9 && magazine[pointer] == Constants.CBC_PR && // AKS -> AKS , id [ AKS ] = { AKS }
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                                magazine[pointer - 3] == Constants.ASSIGN_PR && magazine[pointer - 4] == Constants.SBC_PR &&
                                magazine[pointer - 5] == AKS && magazine[pointer - 6] == Constants.SBO_PR &&
                                magazine[pointer - 7] == Constants.ID_PR &&
                                magazine[pointer - 8] == Constants.VIRGULE_PR && magazine[pointer - 9] == AKS) {
                            pointer -= 9;
                        } else if (pointer >= 8 && // AKS -> id AKS [ AKS ] = { AKS }
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                                magazine[pointer - 3] == Constants.ASSIGN_PR && magazine[pointer - 4] == Constants.SBC_PR &&
                                magazine[pointer - 5] == AKS && magazine[pointer - 6] == Constants.SBO_PR &&
                                magazine[pointer - 7] == AKS && magazine[pointer - 8] == Constants.ID_PR) {
                            pointer -= 8;
                            magazine[pointer] = AKS;
                        } else if (pointer >= 7 && magazine[pointer] == Constants.CBC_PR && // AKS -> AKS , id AKS = { AKS }
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                                magazine[pointer - 3] == Constants.ASSIGN_PR &&
                                magazine[pointer - 4] == AKS &&
                                magazine[pointer - 5] == Constants.ID_PR &&
                                magazine[pointer - 6] == Constants.VIRGULE_PR && magazine[pointer - 7] == AKS) {
                            pointer -= 7;
                        } else if (pointer >= 7 && // AKS -> id [ AKS ] = { AKS }
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                                magazine[pointer - 3] == Constants.ASSIGN_PR && magazine[pointer - 4] == Constants.SBC_PR &&
                                magazine[pointer - 5] == AKS && magazine[pointer - 6] == Constants.SBO_PR &&
                                magazine[pointer - 7] == Constants.ID_PR) {
                            pointer -= 7;
                            magazine[pointer] = AKS;
                        } else if (pointer >= 7 && // AKS -> AKS void main ( ) { AKS }
                            magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                            magazine[pointer - 3] == Constants.RBC_PR && magazine[pointer - 4] == Constants.RBO_PR &&
                            magazine[pointer - 5] == Constants.MAIN_PR && magazine[pointer - 6] == Constants.VOID_PR &&
                            magazine[pointer - 7] == AKS) {
                            pointer -= 7;
                        }  /*else if (pointer >= 7 && // AKS -> AKS if ( AKS ) AKS
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                                magazine[pointer - 3] == Constants.RBC_PR && magazine[pointer - 4] == AKS &&
                                magazine[pointer - 5] == Constants.RBO_PR && magazine[pointer - 6] == Constants.IF_PR &&
                                magazine[pointer - 7] == AKS) {
                            pointer -= 7;
                        }*/ else if (pointer >= 6 && // AKS -> AKS , id = { AKS }
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                                magazine[pointer - 3] == Constants.ASSIGN_PR && magazine[pointer - 4] == Constants.ID_PR &&
                                magazine[pointer - 5] == Constants.VIRGULE_PR && magazine[pointer - 6] == AKS) {
                            pointer -= 6;
                        } else if (pointer >= 5 && // AKS -> id AKS = { AKS }
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                                magazine[pointer - 3] == Constants.ASSIGN_PR &&
                                magazine[pointer - 4] == AKS &&
                                magazine[pointer - 5] == Constants.ID_PR) {
                            pointer -= 5;
                            magazine[pointer] = AKS;
                        }/* else if (pointer >= 6 && // AKS -> if ( AKS ) AKS
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                                magazine[pointer - 3] == Constants.RBC_PR && magazine[pointer - 4] == AKS &&
                                magazine[pointer - 5] == Constants.RBO_PR && magazine[pointer - 6] == Constants.IF_PR) {
                            pointer -= 6;
                            magazine[pointer] = 6;
                        }*/ else if (pointer >= 4 && // AKS -> AKS , { AKS }
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                                magazine[pointer - 3] == Constants.VIRGULE_PR && magazine[pointer - 4] == AKS) {
                            pointer -= 4;
                        } else if (pointer >= 4 && // AKS -> id = { AKS }
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                                magazine[pointer - 3] == Constants.ASSIGN_PR && magazine[pointer - 4] == Constants.ID_PR) {
                            pointer -= 4;
                            magazine[pointer] = AKS;
                        }/* else if (pointer >= 4 && // AKS -> AKS else { AKS }
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                                magazine[pointer - 3] == Constants.ELSE_PR && magazine[pointer - 4] == AKS) {
                            pointer -= 4;
                        }*/ else if (pointer >= 3 && // AKS -> = { AKS }
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                                magazine[pointer - 3] == Constants.ASSIGN_PR) {
                            pointer -= 3;
                            magazine[pointer] = AKS;
                        } else if (pointer >= 3 && // AKS = AKS { AKS }
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR &&
                                magazine[pointer - 3] == AKS) {
                            pointer -= 3;
                        } else if (pointer >= 2 && // AKS -> { AKS }
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.CBO_PR) {
                            pointer -= 2;
                            magazine[pointer] = AKS;
                        }
                        break;
                    case Constants.RBC_PR: // где то здесь начал серъезно разбираться в том, что пишу
                        if (pointer >= 7 && // AKS -> AKS , id AKS = ( AKS )
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.RBO_PR &&
                                magazine[pointer - 3] == Constants.ASSIGN_PR && magazine[pointer - 4] == AKS &&
                                magazine[pointer - 5] == Constants.ID_PR && magazine[pointer - 6] == Constants.VIRGULE_PR &&
                                magazine[pointer - 7] == AKS) {
                            pointer -= 7;
                        } else if (pointer >= 5 && // AKS -> AKS if ( AKS ) AKS
                                magazine[pointer] == AKS && magazine[pointer - 1] == Constants.RBC_PR &&
                                magazine[pointer - 2] == AKS && magazine[pointer - 3] == Constants.RBO_PR &&
                                magazine[pointer - 4] == Constants.IF_PR && magazine[pointer - 5] == AKS) {
                            pointer -= 5;
                        } else if (pointer >= 5 && // AKS -> id AKS = ( AKS )
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.RBO_PR &&
                                magazine[pointer - 3] == Constants.ASSIGN_PR && magazine[pointer - 4] == AKS &&
                                magazine[pointer - 5] == Constants.ID_PR) {
                            pointer -= 5;
                            magazine[pointer] = AKS;
                        } else if (pointer >= 5 &&
                                magazine[pointer] == AKS && magazine[pointer - 1] == Constants.RBC_PR &&
                                magazine[pointer - 2] == Constants.RBO_PR && magazine[pointer - 3] == Constants.MAIN_PR &&
                                magazine[pointer - 4] == Constants.VOID_PR && magazine[pointer - 5] == AKS) {
                            pointer -= 5;
                        } else if (pointer >= 4 && // AKS -> if ( AKS ) AKS
                                magazine[pointer] == AKS && magazine[pointer - 1] == Constants.RBC_PR &&
                                magazine[pointer - 2] == AKS && magazine[pointer - 3] == Constants.RBO_PR &&
                                magazine[pointer - 4] == Constants.IF_PR) {
                            pointer -= 4;
                            magazine[pointer] = AKS;
                        } else if (pointer >= 3 && // AKS -> = ( AKS )
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.RBO_PR &&
                                magazine[pointer - 3] == Constants.ASSIGN_PR) {
                            pointer -= 3;
                            magazine[pointer] = AKS;
                        } else if (pointer >= 4 && // AKS -> AKS , ( AKS )
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.RBO_PR &&
                                magazine[pointer - 3] == Constants.VIRGULE_PR && magazine[pointer - 4] == AKS) {
                            pointer -= 4;
                        } else if (pointer >= 2 && // AKS -> ( AKS )
                            magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.RBO_PR) {
                            pointer -= 2;
                            magazine[pointer] = AKS;
                        }
                        break;
                    case Constants.SBC_PR: // ещё больше начал шарить
                        if (pointer >= 9 && // AKS -> AKS id . AKS . id AKS [ AKS ]
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.SBO_PR &&
                                magazine[pointer - 3] == AKS && magazine[pointer - 4] == Constants.ID_PR &&
                                magazine[pointer - 5] == Constants.DOT_PR && magazine[pointer - 6] == AKS &&
                                magazine[pointer - 7] == Constants.DOT_PR && magazine[pointer - 8] == Constants.ID_PR &&
                                magazine[pointer - 9] == AKS) {
                            pointer -= 9;
                        } else if (pointer >= 8 && // AKS -> AKS id . AKS . id [ AKS ]
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.SBO_PR &&
                                magazine[pointer - 3] == Constants.ID_PR && magazine[pointer - 4] == Constants.DOT_PR &&
                                magazine[pointer - 5] == AKS && magazine[pointer - 6] == Constants.DOT_PR &&
                                magazine[pointer - 7] == Constants.ID_PR && magazine[pointer - 8] == AKS) {
                            pointer -= 8;
                        } else if (pointer >= 6 && // AKS -> AKS , id AKS [ AKS ]
                            magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.SBO_PR &&
                            magazine[pointer - 3] == Constants.ID_PR && magazine[pointer - 4] == AKS &&
                            magazine[pointer - 5] == Constants.VIRGULE_PR && magazine[pointer - 6] == AKS) {
                            pointer -= 6;
                        } else if (pointer >= 6 && // AKS -> AKS , id [ AKS ]
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.SBO_PR &&
                                magazine[pointer - 3] == AKS && magazine[pointer - 4] == Constants.ID_PR &&
                                magazine[pointer - 5] == Constants.VIRGULE_PR && magazine[pointer - 6] == AKS) {
                            pointer -= 6;
                        } else if (pointer >= 6 && // AKS -> AKS id . id [ AKS ]
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.SBO_PR &&
                                magazine[pointer - 3] == Constants.ID_PR && magazine[pointer - 4] == Constants.DOT_PR &&
                                magazine[pointer - 5] == Constants.ID_PR && magazine[pointer - 6] == AKS) {
                            pointer -= 6;
                        } else if (pointer >= 4 && // AKS -> id AKS [ AKS ]
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.SBO_PR &&
                                magazine[pointer - 3] == AKS && magazine[pointer - 4] == Constants.ID_PR) {
                            pointer -= 4;
                            magazine[pointer] = AKS;
                        } else if (pointer >= 3 && // AKS -> id [ AKS ]
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.SBO_PR &&
                                magazine[pointer - 3] == Constants.ID_PR) {
                            pointer -= 3;
                            magazine[pointer] = AKS;
                        } else if (pointer >= 3 && // AKS -> AKS [ AKS ]
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.SBO_PR &&
                                magazine[pointer - 3] == AKS) { // коричневое нужно ли его добавлять так
                            pointer -= 3;
                        } else if (pointer >= 2 && // AKS -> [ AKS ]
                                magazine[pointer - 1] == AKS && magazine[pointer - 2] == Constants.SBO_PR) {
                            pointer -= 2;
                            magazine[pointer] = AKS;
                        }
                        break;
                    case Constants.VIRGULE_PR:
                        if (pointer >= 2 &&
                                magazine[pointer] == AKS && magazine[pointer - 1] == Constants.VIRGULE_PR &&
                                magazine[pointer - 2] == AKS) {
                            pointer -= 2;
                        }
                        break;
                    default:
                        if (magazine[pointer] == Constants.INT_PR ||
                            magazine[pointer] == Constants.CHAR_PR || magazine[pointer] == Constants.ID_PR ||
                            magazine[pointer] == Constants.NUM_PR) {
                            magazine[pointer] = AKS;
                        } else if (pointer >= 2 &&
                                magazine[pointer] == AKS && (magazine[pointer - 1] == Constants.PLUS_MINUS_PR ||
                                    magazine[pointer - 1] == Constants.OR_PR || magazine[pointer - 1] == Constants.AND_PR &&
                                    magazine[pointer - 1] == Constants.EQUAL_PR || magazine[pointer - 1] == Constants.MORE_EQUAL_LESS_PR ||
                                    magazine[pointer - 1] == Constants.MULT_SLASH_PERCENT_PR) &&
                                magazine[pointer - 2] == AKS) {
                            pointer -= 2;
                        } else if (pointer >= 2 && // AKS -> AKS else AKS
                                magazine[pointer] == AKS && magazine[pointer - 1] == Constants.ELSE_PR &&
                                magazine[pointer - 2] == AKS) {
                            pointer -= 2;
                        } else if (pointer >= 3 &&
                                magazine[pointer] == AKS && magazine[pointer - 1] == Constants.DOT_PR &&
                                magazine[pointer - 2] == Constants.ID_PR && magazine[pointer - 3] == AKS) {
                            pointer -= 3;
                        } else if (pointer >= 3 && // AKS -> id AKS = AKS
                                magazine[pointer] == AKS && magazine[pointer - 1] == Constants.ASSIGN_PR &&
                                magazine[pointer - 2] == AKS && magazine[pointer - 3] == Constants.ID_PR) {
                            pointer -= 3;
                            magazine[pointer] = AKS;
                        } else {
                            scaner.PrintError("Неверный символ".toCharArray(), lex);
                            resCode = 0;
                        }
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