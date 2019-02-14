public class LL1 {
    private int[] magazine = new int[5000];
    private int pointer = 0;

    private void epsilon() {
        pointer--;
    }

    public int LL1Analise(Scaner scaner) {
        int type, fl = 1, resCode = 1;

        char[] lex = new char[Constants.MAX_LEX], lll = new char[Constants.MAX_LEX];
        char[] sss = new char[Constants.MAX_LEX];
        magazine[pointer] = Constants.netermPROG;

        //int pointerSave = scaner.getPointer();
        type = scaner.processScanner(lex);
        //scaner.setPointer(pointerSave);
        while (fl != 0) {
            if (magazine[pointer] <= Constants.MAX_TYPE_TERMINAL) {
                if (magazine[pointer] == type) {
                    // верхушка совпадает с отсканированным терминалом
                    if (type == Constants.END)
                        fl = 0;
                    else {
                        type = scaner.processScanner(lex);
                        pointer--;
                    }
                }
                else {
                    resCode = -1;
                    if (magazine[pointer] == Constants.CURLY_BRACE_OPEN) {
                        scaner.PrintError("Ожидалась открывающаяся фигурная скобка".toCharArray(), lex);
                        pointer--;
                        //while (type != Constants.CURLY_BRACE_OPEN) {
                        //    ttt = pointer;
                        //    type = scaner.processScanner(lex);
                        //}
                        //scaner.setPointer(pointer);
                    } else if (magazine[pointer] == Constants.ROUND_BRACE_OPEN) {
                        scaner.PrintError("Ожидалась открывающаяся фигурная скобка".toCharArray(), lex);
                        pointer--;
                        //while (type != Constants.ROUND_BRACE_OPEN) {
                        //    ttt = pointer;
                        //    type = scaner.processScanner(lex);
                        //}
                        //scaner.setPointer(pointer);
                    } else {
                        scaner.PrintError("Неизвестный символ".toCharArray(), lex);
                        while (type != Constants.COMMA && type != Constants.CURLY_BRACE_CLOSE && type != Constants.ROUND_BRACE_CLOSE)
                            type = scaner.processScanner(lex);
                        while (pointer > 0 && magazine[pointer] != type) {
                            pointer--;
                        }
                    }
                    //return -1; // ожидался символ типа magazine[pointer], а не lex
                }
                continue;
            } else {
                switch (magazine[pointer]) {
                    case Constants.netermPROG :
                        if (type == Constants.INT || type == Constants.CHAR ||
                            type == Constants.ID) {
                            magazine[pointer++] = Constants.netermPROG;
                            magazine[pointer++] = Constants.netermDATA;
                        } else if (type == Constants.CLASS) {
                            magazine[pointer++] = Constants.netermPROG;
                            magazine[pointer++] = Constants.COMMA;
                            magazine[pointer++] = Constants.CURLY_BRACE_CLOSE;
                            magazine[pointer++] = Constants.netermCLASS_DESC;
                            magazine[pointer++] = Constants.CURLY_BRACE_OPEN;
                            magazine[pointer++] = Constants.ID;
                            magazine[pointer++] = Constants.CLASS;
                        } else if (type == Constants.CONST) {
                            magazine[pointer++] = Constants.netermPROG;
                            magazine[pointer++] = Constants.netermCONST;
                        } else if (type == Constants.VOID) {
                            magazine[pointer++] = Constants.netermPROG;
                            magazine[pointer++] = Constants.netermDESC_FUN;
                            magazine[pointer++] = Constants.ROUND_BRACE_CLOSE;
                            magazine[pointer++] = Constants.ROUND_BRACE_OPEN;
                            magazine[pointer++] = Constants.MAIN;
                            magazine[pointer++] = Constants.VOID;
                        } else {
                            magazine[pointer++] = type;
                        }
                          //  epsilon();
                        break;
                    case Constants.netermCLASS_DESC:
                        if (type == Constants.INT || type == Constants.CHAR ||
                                type == Constants.ID) { // Ч П С Ч Я Д В Ч М
                            magazine[pointer++] = Constants.netermCLASS_DESC;
                            magazine[pointer++] = Constants.netermDATA;
                        } //else
                           // epsilon();
                        break;
                    case Constants.netermDATA:
                        if (type == Constants.INT || type == Constants.CHAR ||
                                type == Constants.ID) { // Ч П С Ч Я Д В Ч М
                            magazine[pointer++] = Constants.netermALL_DATA;
                            magazine[pointer++] = Constants.netermTYPE;
                        }
                        break;
                    case Constants.netermTYPE:
                        if (type == Constants.INT) {
                            magazine[pointer++] = Constants.INT;
                        } else if (type == Constants.CHAR) {
                            magazine[pointer++] = Constants.CHAR;
                        } else if (type == Constants.ID) { // Ч П С Ч Я Д В Ч М
                            magazine[pointer++] = Constants.ID;
                        }
                        break;
                    case Constants.netermALL_DATA:
                        if (type == Constants.ID) {
                            magazine[pointer++] = Constants.COMMA;
                            magazine[pointer++] = Constants.netermNEXT_DATA;
                            magazine[pointer++] = Constants.netermVARIABLE;
                            magazine[pointer++] = Constants.ID;
                        }
                        break;
                    case Constants.netermNEXT_DATA:
                        if (type == Constants.VIRGULE) {
                            magazine[pointer++] = Constants.netermNEXT_DATA;
                            magazine[pointer++] = Constants.netermVARIABLE;
                            magazine[pointer++] = Constants.ID;
                            magazine[pointer++] = Constants.VIRGULE;
                        } //else
                          //  epsilon();
                        break;
                    case Constants.netermCONST:
                        if (type == Constants.CONST) {
                            magazine[pointer++] = Constants.COMMA;
                            magazine[pointer++] = Constants.netermVARIABLE;
                            //magazine[pointer++] = Constants.ASSIGN;
                            magazine[pointer++] = Constants.ID;
                            magazine[pointer++] = Constants.netermTYPE;
                            magazine[pointer++] = Constants.CONST;
                        }
                        break;
                    case Constants.netermVARIABLE:
                        if (type == Constants.SQUARE_BRACE_OPEN) {
                            magazine[pointer++] = Constants.netermVARIABLE;
                            magazine[pointer++] = Constants.netermNEXT_SQUARE_DATA;
                            magazine[pointer++] = Constants.SQUARE_BRACE_CLOSE;
                            magazine[pointer++] = Constants.netermA1;
                            magazine[pointer++] = Constants.SQUARE_BRACE_OPEN;
                        } else if (type == Constants.ASSIGN) {
                            magazine[pointer++] = Constants.netermEXPRESSION;
                            magazine[pointer++] = Constants.ASSIGN;
                        }// else
                          //  epsilon();
                        break;
                    case Constants.netermNEXT_SQUARE_DATA:
                        if (type == Constants.SQUARE_BRACE_OPEN) {
                            magazine[pointer++] = Constants.netermNEXT_SQUARE_DATA;
                            magazine[pointer++] = Constants.SQUARE_BRACE_CLOSE;
                            magazine[pointer++] = Constants.netermA1;
                            magazine[pointer++] = Constants.SQUARE_BRACE_OPEN;
                        }// else
                          //  epsilon();
                        break;
                    case Constants.netermEXPRESSION:
                        if (type == Constants.ROUND_BRACE_OPEN ||
                            type == Constants.ID || type == Constants.TYPE_INT ||
                            type == Constants.TYPE_SINT || type == Constants.TYPE_CHAR) {
                                magazine[pointer++] = Constants.netermA1;
                        } else if (type == Constants.CURLY_BRACE_OPEN) {
                            magazine[pointer++] = Constants.CURLY_BRACE_CLOSE;
                            magazine[pointer++] = Constants.netermMANY_EXPRESSION;
                            magazine[pointer++] = Constants.CURLY_BRACE_OPEN;
                        }
                        break;
                    case Constants.netermMANY_EXPRESSION:
                        if (type == Constants.ROUND_BRACE_OPEN ||
                            type == Constants.CURLY_BRACE_OPEN ||
                            type == Constants.ID || type == Constants.TYPE_CHAR ||
                            type == Constants.TYPE_SINT || type == Constants.TYPE_INT) {
                                magazine[pointer++] = Constants.netermNEXT_MANY_EXPRESSION;
                                magazine[pointer++] = Constants.netermEXPRESSION;
                        }
                        break;
                    case Constants.netermNEXT_MANY_EXPRESSION:
                        if (type == Constants.VIRGULE) {
                            magazine[pointer++] = Constants.netermNEXT_MANY_EXPRESSION;
                            magazine[pointer++] = Constants.netermEXPRESSION;
                            magazine[pointer++] = Constants.VIRGULE;
                        } //else
                          //  epsilon();
                        break;
                    case Constants.netermOPERATOR:
                        if (type == Constants.CURLY_BRACE_OPEN) {
                            magazine[pointer++] = Constants.netermDESC_FUN;
                        } else if (type == Constants.IF) {
                            magazine[pointer++] = Constants.netermIF;
                        } else if (type == Constants.ID) {
                            magazine[pointer++] = Constants.COMMA;
                            magazine[pointer++] = Constants.netermEXPRESSION;
                            magazine[pointer++] = Constants.ASSIGN;
                            magazine[pointer++] = Constants.netermELEM_ARRAY;
                            magazine[pointer++] = Constants.ID;
                        }
                        break;
                    case Constants.netermIF:
                        if (type == Constants.IF) {
                            magazine[pointer++] = Constants.netermMAY_ELSE;
                            magazine[pointer++] = Constants.netermOPERATOR;
                            magazine[pointer++] = Constants.ROUND_BRACE_CLOSE;
                            magazine[pointer++] = Constants.netermEXPRESSION;
                            magazine[pointer++] = Constants.ROUND_BRACE_OPEN;
                            magazine[pointer++] = Constants.IF;
                        }
                        break;
                    case Constants.netermMAY_ELSE:
                        if (type == Constants.ELSE) {
                            magazine[pointer++] = Constants.netermOPERATOR;
                            magazine[pointer++] = Constants.ELSE;
                        } //else
                          //  epsilon();
                        break;
                    case Constants.netermDESC_FUN:
                        //if (type == Constants.CURLY_BRACE_OPEN) {
                            magazine[pointer++] = Constants.CURLY_BRACE_CLOSE;
                            magazine[pointer++] = Constants.netermIN_DESC_FUN;
                            magazine[pointer++] = Constants.CURLY_BRACE_OPEN;
                        //}
                        break;
                    case Constants.netermIN_DESC_FUN:
                        int pointerScanner = scaner.getPointer();
                        int nextType = scaner.processScanner(sss);
                        if (type == Constants.INT || type == Constants.CHAR ||
                                (type == Constants.ID && nextType == Constants.ID)) {
                                magazine[pointer++] = Constants.netermNEXT_DESC_FUN;
                                magazine[pointer++] = Constants.netermDATA;
                        } else if (type == Constants.CONST) {
                            magazine[pointer++] = Constants.netermNEXT_DESC_FUN;
                            magazine[pointer++] = Constants.netermCONST;
                        } else if (type == Constants.CURLY_BRACE_OPEN ||
                                type == Constants.IF || type == Constants.ID) {
                            magazine[pointer++] = Constants.netermNEXT_DESC_FUN;
                            magazine[pointer++] = Constants.netermOPERATOR;
                        }
                        scaner.setPointer(pointerScanner);
                        break;
                    case Constants.netermNEXT_DESC_FUN:
                        int pointerScannerNext = scaner.getPointer();
                        int nextTypeNext = scaner.processScanner(sss);
                        if (type == Constants.INT || type == Constants.CHAR ||
                                (type == Constants.ID && nextTypeNext == Constants.ID)) {
                            magazine[pointer++] = Constants.netermNEXT_DESC_FUN;
                            magazine[pointer++] = Constants.netermDATA;
                        } else if (type == Constants.CONST) {
                            magazine[pointer++] = Constants.netermNEXT_DESC_FUN;
                            magazine[pointer++] = Constants.netermCONST;
                        } else if (type == Constants.CURLY_BRACE_OPEN ||
                                type == Constants.IF || type == Constants.ID) {
                            magazine[pointer++] = Constants.netermNEXT_DESC_FUN;
                            magazine[pointer++] = Constants.netermOPERATOR;
                        } //else
                          //  epsilon();
                        scaner.setPointer(pointerScannerNext);
                        break;
                    case Constants.netermELEM_ARRAY:
                        if (type == Constants.SQUARE_BRACE_OPEN) {
                            magazine[pointer++] = Constants.netermELEM_ARRAY;
                            magazine[pointer++] = Constants.netermINSIDE_ELEM;
                        } else if (type == Constants.DOT) {
                            magazine[pointer++] = Constants.netermELEM_ARRAY;
                            magazine[pointer++] = Constants.netermINSIDE_ELEM;
                        } //else
                          //  epsilon();
                        break;
                    case Constants.netermINSIDE_ELEM:
                        if (type == Constants.SQUARE_BRACE_OPEN) {
                            magazine[pointer++] = Constants.netermMAY_SQUARE;
                        } else if (type == Constants.DOT) {
                            magazine[pointer++] = Constants.netermMAY_POINT;
                        }
                        break;
                    case Constants.netermMAY_SQUARE:
                        if (type == Constants.SQUARE_BRACE_OPEN) {
                            magazine[pointer++] = Constants.netermNEXT_MAY_SQUARE;
                            magazine[pointer++] = Constants.SQUARE_BRACE_CLOSE;
                            magazine[pointer++] = Constants.netermEXPRESSION;
                            magazine[pointer++] = Constants.SQUARE_BRACE_OPEN;
                        }
                        break;
                    case Constants.netermNEXT_MAY_SQUARE:
                        if (type == Constants.SQUARE_BRACE_OPEN) {
                            magazine[pointer++] = Constants.netermNEXT_MAY_SQUARE;
                            magazine[pointer++] = Constants.SQUARE_BRACE_CLOSE;
                            magazine[pointer++] = Constants.netermEXPRESSION;
                            magazine[pointer++] = Constants.SQUARE_BRACE_OPEN;
                        }// else
                          //  epsilon();
                        break;
                    case Constants.netermMAY_POINT:
                        if (type == Constants.DOT) {
                            magazine[pointer++] = Constants.netermNEXT_MAY_POINT;
                            magazine[pointer++] = Constants.ID;
                            magazine[pointer++] = Constants.DOT;
                        }
                        break;
                    case Constants.netermNEXT_MAY_POINT:
                        if (type == Constants.DOT) {
                            magazine[pointer++] = Constants.netermNEXT_MAY_POINT;
                            magazine[pointer++] = Constants.ID;
                            magazine[pointer++] = Constants.DOT;
                        }// else
                          //  epsilon();
                        break;
                    case Constants.netermA1:
                        if (type == Constants.ROUND_BRACE_OPEN ||
                            type == Constants.ID || type == Constants.TYPE_CHAR ||
                            type == Constants.TYPE_INT || type == Constants.TYPE_SINT) {
                                magazine[pointer++] = Constants.netermA1_;
                                magazine[pointer++] = Constants.netermA2;
                        }
                        break;
                    case Constants.netermA1_:
                        if (type == Constants.OR) {
                            magazine[pointer++] = Constants.netermA1_;
                            magazine[pointer++] = Constants.netermA2;
                            magazine[pointer++] = Constants.OR;
                        }// else
                           // epsilon();
                        break;
                    case Constants.netermA2:
                        if (type == Constants.ROUND_BRACE_OPEN ||
                                type == Constants.ID || type == Constants.TYPE_CHAR ||
                                type == Constants.TYPE_INT || type == Constants.TYPE_SINT) {
                            magazine[pointer++] = Constants.netermA2_;
                            magazine[pointer++] = Constants.netermA3;
                        }
                        break;
                    case Constants.netermA2_:
                        if (type == Constants.AND) {
                            magazine[pointer++] = Constants.netermA2_;
                            magazine[pointer++] = Constants.netermA3;
                            magazine[pointer++] = Constants.AND;
                        }// else
                          //  epsilon();
                        break;
                    case Constants.netermA3:
                        if (type == Constants.ROUND_BRACE_OPEN ||
                                type == Constants.ID || type == Constants.TYPE_CHAR ||
                                type == Constants.TYPE_INT || type == Constants.TYPE_SINT) {
                            magazine[pointer++] = Constants.netermA3_;
                            magazine[pointer++] = Constants.netermA4;
                        }
                        break;
                    case Constants.netermA3_:
                        if (type == Constants.EQUAL) {
                            magazine[pointer++] = Constants.netermA3_;
                            magazine[pointer++] = Constants.netermA4;
                            magazine[pointer++] = Constants.EQUAL;
                        } //else
                          //  epsilon();
                        break;
                    case Constants.netermA4:
                        if (type == Constants.ROUND_BRACE_OPEN ||
                                type == Constants.ID || type == Constants.TYPE_CHAR ||
                                type == Constants.TYPE_INT || type == Constants.TYPE_SINT) {
                            magazine[pointer++] = Constants.netermA4_;
                            magazine[pointer++] = Constants.netermA5;
                        }
                        break;
                    case Constants.netermA4_:
                        if (type >= Constants.MORE && type <= Constants.LESS_EQUAL ) {
                            magazine[pointer++] = Constants.netermA4_;
                            magazine[pointer++] = Constants.netermA5;
                            magazine[pointer++] = type;
                        } //else
                          //  epsilon();
                        break;
                    case Constants.netermA5:
                        if (type == Constants.ROUND_BRACE_OPEN ||
                                type == Constants.ID || type == Constants.TYPE_CHAR ||
                                type == Constants.TYPE_INT || type == Constants.TYPE_SINT) {
                            magazine[pointer++] = Constants.netermA5_;
                            magazine[pointer++] = Constants.netermA6;
                        }
                        break;
                    case Constants.netermA5_:
                        if (type == Constants.PLUS || type == Constants.MINUS) {
                            magazine[pointer++] = Constants.netermA5_;
                            magazine[pointer++] = Constants.netermA6;
                            magazine[pointer++] = type;
                        }// else
                          //  epsilon();
                        break;
                    case Constants.netermA6:
                        if (type == Constants.ROUND_BRACE_OPEN ||
                                type == Constants.ID || type == Constants.TYPE_CHAR ||
                                type == Constants.TYPE_INT || type == Constants.TYPE_SINT) {
                            magazine[pointer++] = Constants.netermA6_;
                            magazine[pointer++] = Constants.netermA7;
                        }
                        break;
                    case Constants.netermA6_:
                        if (type == Constants.STAR || type == Constants.SLASH ||
                                type == Constants.PERCENT) {
                            magazine[pointer++] = Constants.netermA6_;
                            magazine[pointer++] = Constants.netermA7;
                            magazine[pointer++] = type;
                        } //else
                           // epsilon();
                        break;
                    case Constants.netermA7:
                        if (type == Constants.ROUND_BRACE_OPEN) {
                            magazine[pointer++] = Constants.ROUND_BRACE_CLOSE;
                            magazine[pointer++] = Constants.netermA1;
                            magazine[pointer++] = Constants.ROUND_BRACE_OPEN;
                        } else if (type == Constants.ID) {
                            magazine[pointer++] = Constants.netermELEM_ARRAY;
                            magazine[pointer++] = Constants.ID;
                        } else if (type == Constants.TYPE_INT || type == Constants.TYPE_SINT ||
                                    type == Constants.TYPE_CHAR) {
                            magazine[pointer++] = type;
                        }
                        break;

                } // end switch
            }
            pointer--;
        } // end cycle

        return resCode;
    }


}