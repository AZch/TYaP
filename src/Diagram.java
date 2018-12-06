public class Diagram {
    private Scaner scaner;
    private Tree tree;

    public Diagram(Scaner scaner) {
        this.scaner = scaner;
        tree = new Tree();
    }

    private int lexToNum(char[] lex) {
        int countStr = 0;
        String resNum = "";
        while (lex[countStr] != '\0') {
            resNum += String.valueOf(lex[countStr]);
            countStr++;
        }
        return Integer.parseInt(resNum);
    }

    private String lexToStr(char[] lex) {
        int countStr = 0;
        String resNum = "";
        while (lex[countStr] != '\0') {
            resNum += String.valueOf(lex[countStr]);
            countStr++;
        }
        return resNum;
    }

    /**
     * программа
     */
    public void PROG() {
        char[] lex = new char[Constants.MAX_LEX];
        int type, pointer;
        pointer = scaner.getPointer();
        type = scaner.processScanner(lex);
        //scaner.setPointer(pointer);
        while (type != Constants.END) {
            // запомнить указатель
            //pointer = scaner.getPointer();
            if (type == Constants.INT || type == Constants.CHAR || type == Constants.ID) {
                scaner.setPointer(pointer);
                // вернуть укащатель
                DATA();
            } else if (type == Constants.VOID) {
                type = scaner.processScanner(lex);
                if (type != Constants.MAIN)
                    scaner.PrintError("Ожидался main".toCharArray(), lex);
                tree.checkIndividualAllId(new String(lex), scaner);
                tree.add(new String(lex), Constants.FUNCTION, new TableData(new String(lex), Constants.FUNCTION, new SemType(Constants.FUNCTION)));
                VertTree currVert = tree.getCurrVert();
                currVert.tableData.retType = Constants.VOID;
                tree.addRight(new VertTree(-1, ""));

                type = scaner.processScanner(lex);
                if (type != Constants.ROUND_BRACE_OPEN)
                    scaner.PrintError("Ожидалась (".toCharArray(), lex);

                type = scaner.processScanner(lex);
                if (type != Constants.ROUND_BRACE_CLOSE)
                    scaner.PrintError("Ожидалась )".toCharArray(), lex);
                currVert.tableData.countOfParametrsFun = 0;
                //tree.add(")", Constants.ROUND_BRACE_CLOSE);

                DESC_FUN();

                tree.setCurrVert(currVert);
            } else if (type == Constants.CONST) {
                scaner.setPointer(pointer);
                CONST();
            } else {
                if (type != Constants.CLASS)
                    scaner.PrintError("Ожидался class ".toCharArray(), lex);

                type = scaner.processScanner(lex);
                if (type != Constants.ID)
                    scaner.PrintError("Ожидался идентификатор".toCharArray(), lex);
                tree.checkIndividualAllId(new String(lex), scaner);
                tree.add(new String(lex), Constants.CLASS, new TableData(new String(lex), Constants.CLASS, new SemType(Constants.CLASS)));
                VertTree currVert = tree.getCurrVert();
                tree.addRight(new VertTree(-1, ""));


                type = scaner.processScanner(lex);
                if (type != Constants.CURLY_BRACE_OPEN)
                    scaner.PrintError("Ожидалась {".toCharArray(), lex);
                //tree.add("{", Constants.CLASS);
                //tree.setCurrVert(currVert);


                pointer = scaner.getPointer();
                type = scaner.processScanner(lex);
                scaner.setPointer(pointer);
                while (type != Constants.CURLY_BRACE_CLOSE) {
                    if (type == Constants.INT || type == Constants.CHAR || type == Constants.ID)
                        DATA();
                    else {
                        scaner.PrintError("Ожидалися тип ".toCharArray(), lex);
                    }
                    pointer = scaner.getPointer();
                    type = scaner.processScanner(lex);
                    scaner.setPointer(pointer);
                }

                type = scaner.processScanner(lex);
                if (type != Constants.CURLY_BRACE_CLOSE)
                    scaner.PrintError("Ожидалась } ".toCharArray(), lex);
                //tree.add("}", Constants.CLASS);
                tree.setCurrVert(currVert);

                type = scaner.processScanner(lex);
                if (type != Constants.COMMA)
                    scaner.PrintError("Ожидалась ; ".toCharArray(), lex);
            }
            pointer = scaner.getPointer();
            type = scaner.processScanner(lex);
        }
        int a;
        a = 6;
    }

    private void DATA() {
        int pointer = scaner.getPointer();
        char[] lex = new char[Constants.MAX_LEX];
        int type = scaner.processScanner(lex);
        int whatType = 0;
        if (type != Constants.INT && type != Constants.CHAR && type != Constants.ID) {
            scaner.PrintError("Ожидался тип (int или char или id)".toCharArray(), lex);
        }
        whatType = type;
        if (type == Constants.ID) {
            tree.findId(new String(lex), scaner);
            tree.setLinkVert(tree.findVertUp(new String(lex)));
            whatType = Constants.CLASS;
        } else {
            tree.setLinkVert(null);
        }


        do {
            type = scaner.processScanner(lex);
            if (type != Constants.ID)
                scaner.PrintError("Ожидался тип идентификатор ".toCharArray(), lex);
            tree.checkIndividualId(new String(lex), scaner);
            tree.add(new String(lex), Constants.VARIABLE, new TableData(new String(lex), Constants.VARIABLE, new SemType(whatType)));

            VARIABLE();
            pointer = scaner.getPointer();
            type = scaner.processScanner(lex);
        } while (type == Constants.VIRGULE);

        if (type != Constants.COMMA)
            scaner.PrintError("Ожидался ; ".toCharArray(), lex);
    }

    private void CONST() {
        int pointer = scaner.getPointer();
        char[] lex = new char[Constants.MAX_LEX];
        int type = scaner.processScanner(lex);

        if (type != Constants.CONST)
            scaner.PrintError("Ожидался const ".toCharArray(), lex);
        type = scaner.processScanner(lex);
        if (type == Constants.INT) {
            type = scaner.processScanner(lex);
            if (type != Constants.ID)
                scaner.PrintError("Ожидался идентификатор ".toCharArray(), lex);
            tree.checkIndividualAllId(new String(lex), scaner);
            tree.add(new String(lex), Constants.CONST, new TableData(new String(lex), Constants.CONST, new SemType(Constants.INT)));

            VARIABLE();
        } else if (type == Constants.CHAR) {
            type = scaner.processScanner(lex);
            if (type != Constants.ID)
                scaner.PrintError("Ожидался идентификатор ".toCharArray(), lex);
            tree.checkIndividualAllId(new String(lex), scaner);
            tree.add(new String(lex), Constants.CONST, new TableData(new String(lex), Constants.CONST, new SemType(Constants.CHAR)));

            VARIABLE();
        } else
            scaner.PrintError("Ожидался char или int ".toCharArray(), lex);

        type = scaner.processScanner(lex);
        if (type != Constants.COMMA)
            scaner.PrintError("ожидалась ; ".toCharArray(), lex);

    }

    private void VARIABLE() {
        int pointer = scaner.getPointer();
        char[] lex = new char[Constants.MAX_LEX];
        int type = scaner.processScanner(lex);
        if (type == Constants.ASSIGN) {
            EXPRESSION(1, tree.getCurrVert().tableData.countOfMeasur, tree.getCurrVert().tableData.typeData);
            if (tree.getCurrVert().tableData.type != Constants.CONST)
                tree.getCurrVert().tableData.isInit = true;
        } else if (type == Constants.SQUARE_BRACE_OPEN) {
            tree.getCurrVert().tableData.changeType(Constants.ARRAY);
            //scaner.setPointer(pointer);
            do {
                //type = scaner.processScanner(lex);
                //if (type != Constants.SQUARE_BRACE_OPEN)
                //    scaner.PrintError("Ожидалась [ ".toCharArray(), lex);
                // CONST(); /// тут же по идее A7
                // я думаю здесь вообще так
                //type = scaner.processScanner(lex);
                 // конста в числах, или идентификатор (хотя по идее здесь идентефикатор может быть не констаты)
                SemType semType = A1(null);
                if (semType.type != Constants.VARIABLE)
                    scaner.PrintError("Ожидалась перменная или константа".toCharArray(), lex);
//                if (type != Constants.TYPE_INT && type != Constants.TYPE_SINT /*&& type != Constants.ID && type != Constants.TYPE_CHAR*/)
//                    scaner.PrintError("ожидалась константа ".toCharArray(), lex);
//                int size = lexToNum(lex);
                tree.getCurrVert().tableData.countOfMeasur++;
//                tree.getCurrVert().tableData.sizes.add(size);
//                if (type == Constants.ID) {
//                    tree.findId(new String(lex), scaner);
//                    if (tree.findVertUp(new String(lex)).tableData.type != Constants.CONST)
//                        scaner.PrintError("ожидалась константа ".toCharArray(), lex);
//                }


                type = scaner.processScanner(lex);
                if (type != Constants.SQUARE_BRACE_CLOSE)
                    scaner.PrintError("ожидалось ] ".toCharArray(), lex);
                pointer = scaner.getPointer();
                type = scaner.processScanner(lex);
                //
            } while (type == Constants.SQUARE_BRACE_OPEN);
            scaner.setPointer(pointer);
            VARIABLE();
        } else
            scaner.setPointer(pointer);

    }

    // доп чек
    private void EXPRESSION(int thisMeasure, int currMeasure, SemType semType) {
        int pointer = scaner.getPointer();
        char[] lex = new char[Constants.MAX_LEX];
        int type = scaner.processScanner(lex);

        if (type == Constants.CURLY_BRACE_OPEN) {
            //tree.add("{", Constants.CURLY_BRACE_OPEN);
            do {
                if (thisMeasure > tree.getCurrVert().tableData.countOfMeasur)
                    scaner.PrintError("Слишком много измерений".toCharArray(), "-".toCharArray());
                EXPRESSION(thisMeasure + 1, currMeasure, semType);
                pointer = scaner.getPointer();
                type = scaner.processScanner(lex);
            } while (type == Constants.VIRGULE);
            //scaner.setPointer(pointer);
            if (type != Constants.CURLY_BRACE_CLOSE)
                scaner.PrintError("Ожидалась } ".toCharArray(), lex);
            //tree.add("{", Constants.CURLY_BRACE_CLOSE);
        } else {
            scaner.setPointer(pointer);
            if (thisMeasure != currMeasure)
               scaner.PrintError("Не проследнее измерение".toCharArray(), "-".toCharArray());
            if (!lexToStr(lex).equals("}")) {
                SemType resSemType = null;
                resSemType = A1(resSemType);
                if (resSemType == null) {
                    if (semType != null)
                        scaner.PrintError("Несоответствие типов".toCharArray(), lex);
                } else {
                    if (semType == null)
                        scaner.PrintError("Несоответствие типов".toCharArray(), lex);
                    else if (semType.typeData != resSemType.typeData)
                        scaner.PrintError("Несоответствие типов".toCharArray(), lex);
                }
            }
        }

        //scaner.setPointer(pointer);
    }

    // лучше тоже чек
    private void OPERATOR() {
        int pointer = scaner.getPointer();
        char[] lex = new char[Constants.MAX_LEX];
        int type = scaner.processScanner(lex);
        scaner.setPointer(pointer);

        if (type == Constants.IF) {
            IF();
        } else if (type == Constants.CURLY_BRACE_OPEN) {
            DESC_FUN();
        } else {
            SemType semType = null;
            do {
                type = scaner.processScanner(lex);
                if (type != Constants.ID)
                    scaner.PrintError("Ожидался идентификатор".toCharArray(), lex);
                tree.findId(new String(lex), scaner);
                VertTree vert = tree.findVertUp(new String(lex));
                if (vert.tableData.type == Constants.FUNCTION || vert.tableData.type == Constants.CLASS) {
                    scaner.PrintError("Не перменная а класс или функция".toCharArray(), lex);
                }
                if (vert.tableData.type == Constants.CONST)
                    scaner.PrintError("Переопредление константы недопустимо".toCharArray(), lex);

                semType = ELEM_ARRAY(tree.findVertUp(new String(lex)), 0);
                pointer = scaner.getPointer();
                type = scaner.processScanner(lex);
            } while (type == Constants.DOT);
            //scaner.setPointer(pointer);

            if (type != Constants.ASSIGN)
                scaner.PrintError("Ожидалось = ".toCharArray(), lex);
            EXPRESSION(1, 1, semType);
            type = scaner.processScanner(lex);
            if (type != Constants.COMMA)
                scaner.PrintError("Ожидалась ; ".toCharArray(), lex);
        }

    }

    // конец этого тоже лучше чек
    private void IF() {
        int pointer = scaner.getPointer();
        char[] lex = new char[Constants.MAX_LEX];
        int type = scaner.processScanner(lex);

        if (type != Constants.IF)
            scaner.PrintError("Ожидался if ".toCharArray(), lex);
        type = scaner.processScanner(lex);
        if (type != Constants.ROUND_BRACE_OPEN)
            scaner.PrintError("Ожидалась ( ".toCharArray(), lex);
        EXPRESSION(1, 1, new SemType(Constants.VARIABLE, Constants.INT));
        type = scaner.processScanner(lex);
        if (type != Constants.ROUND_BRACE_CLOSE)
            scaner.PrintError("Ожидалась ) ".toCharArray(), lex);
        OPERATOR();

        pointer = scaner.getPointer();
        type = scaner.processScanner(lex);
        if (type == Constants.ELSE)
            OPERATOR();
        else
            scaner.setPointer(pointer);

    }

    private boolean checkID(int type) {
        if (type == Constants.ID) {
            char[] lex = new char[Constants.MAX_LEX];
            int pointer = scaner.getPointer();
            type = scaner.processScanner(lex);
            type = scaner.processScanner(lex);
            scaner.setPointer(pointer);
            return (type == Constants.ID);
        }
        return false;
    }

    // да вообще лучше всё чекнуть ), но это точно надо чекнтуь
    private void DESC_FUN() {
        int pointer = scaner.getPointer();
        char[] lex = new char[Constants.MAX_LEX];
        int type = scaner.processScanner(lex);

        if (type != Constants.CURLY_BRACE_OPEN)
            scaner.PrintError("Ожидалась { ".toCharArray(), lex);
        //tree.add("{", Constants.CURLY_BRACE_OPEN);

        tree.add("{", Constants.CURLY_BRACE_OPEN, null);
        VertTree currVert = tree.getCurrVert().parent;

        pointer = scaner.getPointer();
        type = scaner.processScanner(lex);

        while (type == Constants.INT || type == Constants.CHAR // DATA()
                || type == Constants.CONST                     // CONST()
                || type == Constants.CURLY_BRACE_OPEN          // OPERATOR() -> DESC_FUN()
                    || type == Constants.ID                    // OPERATOR() -> ID
                    || type == Constants.IF) {                 // OPERATOR() -> IF()
            scaner.setPointer(pointer);

            if (type == Constants.INT || type == Constants.CHAR || checkID(type))
                DATA();
            else if (type == Constants.CONST)
                CONST();
            else
                OPERATOR();
            pointer = scaner.getPointer();
            type = scaner.processScanner(lex);
        }
        // не требует восстановления так как будет являтся выходом из цикла, либо если в цикл вообще не зайдет то будет аналогично считано
        if (type != Constants.CURLY_BRACE_CLOSE)
            scaner.PrintError("Ожидалась } ".toCharArray(), lex);
        tree.setCurrVert(currVert);

        //scaner.setPointer(pointer);

    }

    // по идее полная пустота достигается. Тут есть вопрос
    private SemType ELEM_ARRAY(VertTree lastVert, int countMeasure) {
        int pointer = scaner.getPointer();
        char[] lex = new char[Constants.MAX_LEX];
        int type = scaner.processScanner(lex);
        VertTree currVert = tree.getCurrVert();
        SemType retSemType = new SemType(Constants.VARIABLE, Constants.INT);

        if (type == Constants.SQUARE_BRACE_OPEN || type == Constants.DOT) {
            if (type == Constants.SQUARE_BRACE_OPEN) {
                do {
                    //type = scaner.processScanner(lex);
                    SemType semType = A1(null);
                    if (semType.type != Constants.VARIABLE)
                        scaner.PrintError("Ожидалась перменная или константа".toCharArray(), lex);
//                    if (type != Constants.TYPE_INT && type != Constants.TYPE_SINT /*&&
//                        type != Constants.TYPE_CHAR && type != Constants.ID*/)
//                        scaner.PrintError("Ожидалась константа".toCharArray(), lex);

                   // int elem_index = lexToNum(lex);
                    if (countMeasure + 1 >= lastVert.tableData.countOfMeasur )//|| elem_index >= lastVert.tableData.sizes.get(countMeasure))
                        scaner.PrintError("Обращение за возможные элементы массива".toCharArray(), lex);
                    countMeasure++;


                    type = scaner.processScanner(lex);
                    if (type != Constants.SQUARE_BRACE_CLOSE)
                        scaner.PrintError("Ожидалась ]".toCharArray(), lex);
                    pointer = scaner.getPointer();
                    type = scaner.processScanner(lex);
                } while (type == Constants.SQUARE_BRACE_OPEN);
                if (lastVert.tableData.countOfMeasur > countMeasure + 1)
                    scaner.PrintError("Не простейшее выражение!".toCharArray(), lastVert.id.toCharArray());
                retSemType.typeData = lastVert.tableData.typeData.typeData;
                retSemType.type = Constants.VARIABLE;
            }
            if (type == Constants.DOT) {
                do {
                    if (lastVert != null) {
                        lastVert = lastVert.right;
                    }
                    type = scaner.processScanner(lex);
                    if (type != Constants.ID)
                        scaner.PrintError("Ожидался идентификатор".toCharArray(), lex);
                    //tree.findId(new String(lex), scaner);
                    if (lastVert == null) {
                        scaner.PrintError("не обнаружено данных".toCharArray(), lex);
                    }
                    VertTree vertTree = tree.findThisBranch(new String(lex), lastVert);
                    if (vertTree == null) {
                        scaner.PrintError("не обнаружено данных".toCharArray(), lex);
                    } else {
                        lastVert = vertTree;
                        countMeasure = 0;
                    }

                    pointer = scaner.getPointer();
                    type = scaner.processScanner(lex);
                } while (type == Constants.DOT);
                retSemType.typeData = lastVert.tableData.typeData.typeData;
                retSemType.type = lastVert.tableData.typeData.type;
                scaner.setPointer(pointer);
            } else
                scaner.setPointer(pointer);
            retSemType = ELEM_ARRAY(lastVert, countMeasure);
            tree.setCurrVert(currVert);
        } else {
            if (lastVert != null && lastVert.right != null || (countMeasure + 1 != lastVert.tableData.countOfMeasur)) {
                scaner.PrintError("Не простейшее выражение!".toCharArray(), lastVert.id.toCharArray());
            }
            scaner.setPointer(pointer);
        }

        return retSemType;
    }

    // на всякий случай быстро бы пробежаться
    private SemType A1(SemType semType) {
        semType = A2(semType);

        int pointer = scaner.getPointer();
        char[] lex = new char[Constants.MAX_LEX];
        int type = scaner.processScanner(lex);

        while (type == Constants.OR) {
            SemType semType1 = new SemType(semType);
            semType1 = A2(semType1);
            semType = SemOrAndEqualMoreLess(semType, semType1);
            pointer = scaner.getPointer();
            type = scaner.processScanner(lex);
        }

        scaner.setPointer(pointer);
        return semType;

    }

    private SemType A2(SemType semType) {
        semType = A3(semType);

        int pointer = scaner.getPointer();
        char[] lex = new char[Constants.MAX_LEX];
        int type = scaner.processScanner(lex);

        while (type == Constants.AND) {
            SemType semType1 = new SemType(semType);
            semType1 = A3(semType1);
            semType = SemOrAndEqualMoreLess(semType, semType1);
            pointer = scaner.getPointer();
            type = scaner.processScanner(lex);
        }

        scaner.setPointer(pointer);
        return semType;

    }

    private SemType A3(SemType semType) {
        semType = A4(semType);

        int pointer = scaner.getPointer();
        char[] lex = new char[Constants.MAX_LEX];
        int type = scaner.processScanner(lex);

        while (type == Constants.EQUAL) {
            SemType semType1 = new SemType(semType);
            semType1 = A4(semType1);
            semType = SemOrAndEqualMoreLess(semType, semType1);
            pointer = scaner.getPointer();
            type = scaner.processScanner(lex);
        }


        scaner.setPointer(pointer);
        return semType;

    }

    private SemType A4(SemType semType) {
        semType = A5(semType);

        int pointer = scaner.getPointer();
        char[] lex = new char[Constants.MAX_LEX];
        int type = scaner.processScanner(lex);

        while (type >= Constants.MORE && type <= Constants.LESS_EQUAL) { // >, >=, <, <= стоят по порядку
            SemType semType1 = new SemType(semType);
            semType1 = A5(semType1);
            semType = SemOrAndEqualMoreLess(semType, semType1);
            pointer = scaner.getPointer();
            type = scaner.processScanner(lex);
        }

        scaner.setPointer(pointer);
        return semType;

    }

    private SemType A5(SemType semType) {
        semType = A6(semType);

        int pointer = scaner.getPointer();
        char[] lex = new char[Constants.MAX_LEX];
        int type = scaner.processScanner(lex);

        while (type == Constants.PLUS || type == Constants.MINUS) {
            SemType semType1 = new SemType(semType);
            semType1 = A6(semType1);
            semType = SemPlusMinus(semType, semType1);
            pointer = scaner.getPointer();
            type = scaner.processScanner(lex);
        }

        scaner.setPointer(pointer);
        return semType;

    }

    private SemType A6(SemType semType) {
        semType = A7(semType);

        int pointer = scaner.getPointer();
        char[] lex = new char[Constants.MAX_LEX];
        int type = scaner.processScanner(lex);

        while (type == Constants.SLASH || type == Constants.STAR || type == Constants.PERCENT) {
            SemType semType1 = new SemType(semType);
            semType1 = A7(semType1);
            if (type == Constants.PERCENT)
                semType = SemPercent(semType, semType1);
            else
                semType = SemMultDiv(semType, semType1);
            pointer = scaner.getPointer();
            type = scaner.processScanner(lex);
        }

        scaner.setPointer(pointer);
        return semType;

    }

    // нужен ли тип чар, скорее всего да
    private SemType A7(SemType semType) {
        int pointer = scaner.getPointer();
        char[] lex = new char[Constants.MAX_LEX];
        int type = scaner.processScanner(lex);

        if (type == Constants.ROUND_BRACE_OPEN) {
            semType = A1(semType);

            type = scaner.processScanner(lex);
            if (type != Constants.ROUND_BRACE_CLOSE)
                scaner.PrintError("Ожидалась ) ".toCharArray(), lex);
            return semType;
        } else if (type == Constants.ID) {
            scaner.setPointer(pointer);

            do {
                type = scaner.processScanner(lex);

                if (type != Constants.ID)
                    scaner.PrintError("Ожидался идентификатор".toCharArray(), lex);
                tree.findId(new String(lex), scaner);

                semType = ELEM_ARRAY(tree.findVertUp(new String(lex)), 0);

                pointer = scaner.getPointer();
                type = scaner.processScanner(lex);
            } while (type == Constants.DOT);
            //scaner.setPointer(pointer);

        }
        else if (type == Constants.TYPE_INT || type == Constants.TYPE_SINT || type == Constants.TYPE_CHAR) {
            if (type == Constants.TYPE_INT || type == Constants.TYPE_SINT)
                semType = new SemType(Constants.VARIABLE, Constants.INT);
            else
                semType = new SemType(Constants.VARIABLE, Constants.CHAR);
            return semType;
        }
        scaner.setPointer(pointer);
        return semType;
    }

    private SemType SemOrAndEqualMoreLess(SemType firstType, SemType secondType) {
        if (firstType == null || secondType == null || firstType.type == Constants.ARRAY || secondType.type == Constants.ARRAY)
            return null;
        if (firstType.typeData == Constants.INT) {
            if (secondType.typeData == Constants.INT)
                return firstType;
            else if (secondType.typeData == Constants.CHAR)
                return firstType;
        } else if (firstType.typeData == Constants.CHAR) {
            if (secondType.typeData == Constants.INT)
                return secondType;
            else if (secondType.typeData == Constants.CHAR)
                return new SemType(firstType.type, Constants.INT);
        }
        System.out.println("Неизвестный тип!");
        return null;
    }

    private SemType SemPlusMinus(SemType firstType, SemType secondType) {
        if (firstType == null || secondType == null || firstType.type == Constants.ARRAY || secondType.type == Constants.ARRAY)
            return null;
        if (firstType.typeData == Constants.INT) {
            if (secondType.typeData == Constants.INT)
                return firstType;
            else if (secondType.typeData == Constants.CHAR)
                return firstType;
        } else if (firstType.typeData == Constants.CHAR) {
            if (secondType.typeData == Constants.INT)
                return secondType;
            else if (secondType.typeData == Constants.CHAR)
                return firstType;
        }
        System.out.println("Неизвестный тип!");
        return null;
    }

    private SemType SemMultDiv(SemType firstType, SemType secondType) {
        if (firstType == null || secondType == null || firstType.type == Constants.ARRAY || secondType.type == Constants.ARRAY)
            return null;
        if (firstType.typeData == Constants.INT) {
            if (secondType.typeData == Constants.INT)
                return firstType;
            else if (secondType.typeData == Constants.CHAR)
                return firstType;
        } else if (firstType.typeData == Constants.CHAR) {
            if (secondType.typeData == Constants.INT)
                return secondType;
            else if (secondType.typeData == Constants.CHAR)
                return new SemType(firstType.type, Constants.INT);
        }
        System.out.println("Неизвестный тип!");
        return null;
    }

    private SemType SemPercent(SemType firstType, SemType secondType) {
        if (firstType == null || secondType == null || firstType.type == Constants.ARRAY || secondType.type == Constants.ARRAY)
            return null;
        if (firstType.typeData == Constants.INT) {
            if (secondType.typeData == Constants.INT)
                return firstType;
        }
        System.out.println("Неизвестный тип!");
        return null;
    }
}