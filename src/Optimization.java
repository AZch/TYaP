import java.util.ArrayList;

public class Optimization {

    // оптимизация по if
    public ArrayList<Triad> make(ArrayList<Triad> triads) {
        ArrayList<Triad> resTriad = new ArrayList<>();
        boolean isOptimization = false;
        boolean makeVert = false;
        Vert current = new Vert();
        Vert root = current;
        Vert baseIfVert = null;
        Vert vertWork = new Vert();
        boolean oneSkip = false;
        ArrayList<Vert> afterElseVert = new ArrayList<>();
        ArrayList<Vert> falseIfVert = new ArrayList<>();
        String descVar = "next";
        for (Triad triad : triads) {
            if (triads.indexOf(triad) == 12) {
                int a;
                a = 2;
            }
            for (Vert vert : afterElseVert) {
                if (vert.index == triads.indexOf(triad)) {
                    current = vert;
                    oneSkip = true;
                    afterElseVert.remove(vert);
                    break;
                }

            }
            for (Vert vert : falseIfVert) {
                if (vert.index == triads.indexOf(triad)) {
                    if ( !triads.get(vert.index - 1).proc.equals("go")) {
                        vert.parent.next = vert.parent.falseBrace;
                        descVar = "next";
                        current = vert;
                        current = current.parent;
                        vert.parent.falseBrace = null;
//                        oneSkip = true;
                        falseIfVert.remove(vert);

//                        if (triad.proc.equals("go")) {
//                            Vert nextVert = getVertByIndex(triads.indexOf(triad) + 1, root);
//                            Triad triadGoBrace = triads.get(Integer.parseInt(triad.operand1.split("\\)")[0]));
//                            nextVert.parent.next = new Vert(new Triad(triadGoBrace.proc, triadGoBrace.operand1, triadGoBrace.operand2), "true", nextVert,
//                                    Integer.parseInt(triad.operand1.split("\\)")[0]));
//                            afterElseVert.add(nextVert.parent.next);
//                        }

                        break;
                    }
                }

            }

            if (oneSkip) {
                oneSkip = false;
                continue;
            }

            if ((makeVert && !(triad.proc.equals("==") ||
                    triad.proc.equals("!=") ||
                    triad.proc.equals(">") ||
                    triad.proc.equals("<") ||
                    triad.proc.equals(">=") ||
                    triad.proc.equals("<=") ||
                    triad.proc.equals("+") ||
                    triad.proc.equals("-") ||
                    triad.proc.equals("*") ||
                    triad.proc.equals("/") ||
                    triad.proc.equals("%"))))
                makeVert = false;
            if (triad.proc.equals("=") && triad.operand1.equals("if")) {
                vertWork = new Vert(new Triad(triad.proc, triad.operand1, triad.operand2), "ifStart", null, triads.indexOf(triad));
                baseIfVert = vertWork;
                makeVert = true;
            } else if (triad.proc.equals("=")) {
                vertWork = getVertByIndex(Integer.parseInt(triad.operand1.split("\\)")[0]), root);
                makeVert = true;
                Vert newVert = new Vert(new Triad(triad.proc, triad.operand1, triad.operand2), descVar, vertWork, triads.indexOf(triad));
                vertWork.versionVert.add(newVert);
                vertWork = newVert;
            } else if (triad.proc.equals("if")) {
                makeVert = false;
                Vert newVert = new Vert(new Triad(triad.proc, triad.operand1, triad.operand2), "ifMake", current, triads.indexOf(triad));
                newVert.versionVert.add(baseIfVert);
                current.next = newVert;
                current = newVert;

                Triad triadTrueBrace = triads.get(Integer.parseInt(triad.operand1.split("\\)")[0]));
                current.trueBrace = new Vert(new Triad(triadTrueBrace.proc, triadTrueBrace.operand1, triadTrueBrace.operand2), "true", current,
                        Integer.parseInt(triad.operand1.split("\\)")[0]));

                Triad triadFalseBrace = triads.get(Integer.parseInt(triad.operand2.split("\\)")[0]));
                current.falseBrace = new Vert(new Triad(triadFalseBrace.proc, triadFalseBrace.operand1, triadFalseBrace.operand2), "false", current,
                        Integer.parseInt(triad.operand2.split("\\)")[0]));
                falseIfVert.add(current.falseBrace);
                descVar = "true";
                if (current.trueBrace.triads.proc.equals("=") && !current.trueBrace.triads.operand1.equals("if")) {
                    vertWork = getVertByIndex(Integer.parseInt(current.trueBrace.triads.operand1.split("\\)")[0]), root);
                    makeVert = true;
                    Triad triadTrue = triads.get(Integer.parseInt(triad.operand1.split("\\)")[0]));
                    Vert newVertEq = new Vert(new Triad(triadTrue.proc, triadTrue.operand1, triadTrue.operand2), "true", vertWork, triads.indexOf(triadTrue));
                    vertWork.versionVert.add(newVertEq);
                    vertWork = newVertEq;
                    current.trueBrace.desc = "notValid";
                }
                if (current.falseBrace.triads.proc.equals("=") && !current.falseBrace.triads.operand1.equals("if")) {
                    vertWork = getVertByIndex(Integer.parseInt(current.falseBrace.triads.operand1.split("\\)")[0]), root);
                    makeVert = true;
                    Triad triadFalse = triads.get(Integer.parseInt(triad.operand2.split("\\)")[0]));
                    Vert newVertEq = new Vert(new Triad(triadFalse.proc, triadFalse.operand1, triadFalse.operand2), "false", vertWork, triads.indexOf(triadFalse));
                    vertWork.versionVert.add(newVertEq);
                    vertWork = newVertEq;
                    current.falseBrace.desc = "notValid";
                }
                current = current.trueBrace;
                oneSkip = true;
            } else if (triad.proc.equals("fun") && triad.operand2.equals("end")) {
                makeVert = false;
                Vert newVert = new Vert(new Triad(triad.proc, triad.operand1, triad.operand2), "endfun", current, triads.indexOf(triad));
                current.next = newVert;
                current = newVert;
            } else if (triad.proc.equals("fun")) {
                makeVert = false;
                Vert newVert = new Vert(new Triad(triad.proc, triad.operand1, triad.operand2), "fun", current, triads.indexOf(triad));
                current.next = newVert;
                current = newVert;
            } else if (triad.proc.equals("go")) {
                makeVert = false;
                Vert goVert = new Vert(new Triad(triad.proc, triad.operand1, triad.operand2), "go", null, triads.indexOf(triad));
                current.next = goVert;
                current = getVertByIndex(triads.indexOf(triad) + 1, root);
                if (descVar.equals("true"))
                    descVar = "else";
                oneSkip = true;

                Triad triadGoBrace = triads.get(Integer.parseInt(triad.operand1.split("\\)")[0]));
                current.parent.next = new Vert(new Triad(triadGoBrace.proc, triadGoBrace.operand1, triadGoBrace.operand2), "true", current,
                        Integer.parseInt(triad.operand1.split("\\)")[0]));
                afterElseVert.add(current.parent.next);

            } else {
                Vert newVert = new Vert();
                newVert.triads = new Triad(triad.proc, triad.operand1, triad.operand2);
                newVert.index = triads.indexOf(triad);
                if (makeVert) {
                    newVert.parent = vertWork;
                    vertWork.next = newVert;
                    vertWork = newVert;
                } else {
                    newVert.parent = current;
                    current.next = newVert;
                    current = newVert;
                }

                if (triad.operand1.substring(triad.operand1.length() - 1).equals(")")) {
                    if (triads.get(Integer.parseInt(triad.operand1.split("\\)")[0])).operand2.equals("")) {
                        Vert vertTriad = getVertByIndex(Integer.parseInt(triad.operand1.split("\\)")[0]), root);
                        if (vertTriad != null)
                            vertTriad.useVert.add(newVert);
                    }
                }
                if (triad.operand2.length() > 0 && triad.operand2.substring(triad.operand2.length() - 1).equals(")")) {
                    if (triads.get(Integer.parseInt(triad.operand2.split("\\)")[0])).operand2.equals("")) {
                        Vert vertTriad = getVertByIndex(Integer.parseInt(triad.operand2.split("\\)")[0]), root);
                        if (vertTriad != null)
                            vertTriad.useVert.add(newVert);
                    }
                }
            }
        }
        deleteUnUseTriadVar(root.next);
        deleteUnUseTriadIf(root.next);
        deleteDublicateVar(root.next, triads);
        for (Triad triad : triads) {
            Vert vert = getVertByIndex(triads.indexOf(triad), root);
            boolean dontAdd = false;
            if (vert != null && vert.desc.equals("notValid")) {
                dontAdd = true;
                Vert vertCheck = getVertByIndex(Integer.parseInt(vert.triads.operand1.split("\\)")[0]), root);
                if (vertCheck != null) {
                    for (Vert vertVers : vertCheck.versionVert) {
                        if ((vertVers.index == vert.index || vertVers.desc.equals("true")) && !vertVers.desc.equals("notValid") &&
                                (vertVers.triads.proc.equals(vert.triads.proc) && vertVers.triads.operand1.equals(vert.triads.operand1) && vertVers.triads.operand2.equals(vert.triads.operand2))) {
                            dontAdd = false;
                            if (vertVers.triads.proc.equals(vert.triads.proc) && vertVers.triads.operand1.equals(vert.triads.operand1) && vertVers.triads.operand2.equals(vert.triads.operand2)) {
                                vert = vertVers;
                                vertVers.desc = "notValid";
                            }
                            break;
                        }
                    }
                }
            }
            if (vert != null && !dontAdd) {
                resTriad.add(new Triad(vert.triads.proc, vert.triads.operand1, vert.triads.operand2, vert.index));
            }
        }
        resTriad = sortTriad(resTriad);
        for (Triad triad : resTriad) {
            System.out.println(String.valueOf(triad.index) + ") " + triad.proc + " " + triad.operand1 + " " + triad.operand2);
        }
        return resTriad;
    }

    private ArrayList<Triad> sortTriad(ArrayList<Triad> triads) {
        double currId = triads.get(0).index;
        for (int i = 0; i < triads.size(); i++) {
            if (currId > triads.get(i).index) {
                for (int j = 0; j < triads.size() - 1; j++) {
                    if (triads.get(j).index < triads.get(i).index &&
                        triads.get(j + 1).index > triads.get(i).index) {
                        triads.add(j + 1, new Triad(triads.get(i).proc, triads.get(i).operand1, triads.get(i).operand2, triads.get(i).index + 0.1));
                        triads.remove(i + 1);
                        i--;
                        break;
                    }
                }
            } else {
                currId = triads.get(i).index;
            }
        }
        return triads;
    }

    private boolean makeStrTriadNextVert(Triad triadFirst, Triad triadSec, ArrayList<Triad> triads) {
        boolean result = false;
        if (!triadFirst.proc.equals(triadSec.proc))
            return false;

        if (triadFirst.operand1.substring(triadFirst.operand1.length() - 1).equals(")") && triadSec.operand1.substring(triadSec.operand1.length() - 1).equals(")")) {
            if (!makeStrTriadNextVert(triads.get(Integer.parseInt(triadFirst.operand1.split("\\)")[0])), triads.get(Integer.parseInt(triadSec.operand1.split("\\)")[0])),
                    triads))
                return false;
        } else {
            if (!triadFirst.operand1.equals(triadSec.operand1))
                return false;
        }

        if (triadFirst.operand2.length() > 0 && triadFirst.operand2.substring(triadFirst.operand2.length() - 1).equals(")") &&
                triadSec.operand2.length() > 0 &&  triadSec.operand2.substring(triadSec.operand2.length() - 1).equals(")")) {
            if (!makeStrTriadNextVert(triads.get(Integer.parseInt(triadFirst.operand2.split("\\)")[0])), triads.get(Integer.parseInt(triadSec.operand2.split("\\)")[0])),
                    triads))
                return false;
        } else {
            if (!triadFirst.operand2.equals(triadSec.operand2))
                return false;
        }
        return true;
    }

    private void makeAllNextNotValid(Vert start) {
        if (start != null) {
            start.desc = "notValid";
            if (start.next != null) {
                makeAllNextNotValid(start.next);
            }
        }
    }

    private int getTriadIndexFirstIfEq(ArrayList<Triad> triads, int currIndex) {
       for (int i = currIndex; i > 0; i--) {
           if (triads.get(i).proc.equals("=") && triads.get(i).operand1.equals("if")) {
               return i;
           }
       }
       return -1;
    }

    private void deleteDublicateVar(Vert curr, ArrayList<Triad> triads) {
        if (!curr.triads.proc.equals("if") && !curr.triads.proc.equals("go") && !curr.triads.proc.equals("=") && !curr.triads.proc.equals("fun")) {
            for (int i = 0; i < curr.versionVert.size() - 1; i++) {
                if (makeStrTriadNextVert(curr.versionVert.get(i).triads, curr.versionVert.get(i + 1).triads, triads)) {
                    System.out.println(String.valueOf("Дубликаты"));
                    System.out.println(String.valueOf(curr.versionVert.get(i).index));
                    System.out.println(curr.versionVert.get(i).desc);
                    System.out.println(String.valueOf(curr.versionVert.get(i + 1).index));
                    System.out.println(curr.versionVert.get(i + 1).desc);
                    if (curr.versionVert.get(i).desc.equals(curr.versionVert.get(i + 1).desc)) {
                        makeAllNextNotValid(curr.versionVert.get(i + 1));
                    } else if ((curr.versionVert.get(i + 1).desc.equals("false") && curr.versionVert.get(i).desc.equals("true")) || (
                            curr.versionVert.get(i + 1).desc.equals("true") && curr.versionVert.get(i).desc.equals("false"))) {
                        int indexIfStart = getTriadIndexFirstIfEq(triads, curr.versionVert.get(i).index);
                        curr.versionVert.get(i).index = indexIfStart - 1;
                        makeAllNextNotValid(curr.versionVert.get(i + 1));

                    }

                }
            }

        }
        if (curr.trueBrace != null)
            deleteDublicateVar(curr.trueBrace, triads);
        if (curr.falseBrace != null)
            deleteDublicateVar(curr.falseBrace, triads);
        if (curr.next != null)
            deleteDublicateVar(curr.next, triads);


    }

    private void deleteUnUseTriadIf(Vert curr) {
        if (curr.triads.proc.equals("if") && curr.trueBrace == null && curr.falseBrace == null) {
            System.out.println(String.valueOf(curr.index));
            if (curr.next != null)
                curr.next.parent = curr.parent;
            if (curr.parent.next == curr) {
                curr.parent.next = curr.next;

            } else if (curr.parent.trueBrace == curr) {
                curr.parent.trueBrace = curr.next;
            } else if (curr.parent.falseBrace == curr)
                curr.parent.falseBrace = curr.next;

        }
        if (curr.trueBrace != null)
            deleteUnUseTriadIf(curr.trueBrace);
        if (curr.falseBrace != null)
            deleteUnUseTriadIf(curr.falseBrace);
        if (curr.next != null)
            deleteUnUseTriadIf(curr.next);
    }

    private void deleteUnUseTriadVar(Vert curr) {
        if (curr.useVert.size() == 0) {
            if (!curr.triads.proc.equals("if") && !curr.triads.proc.equals("go") && !curr.triads.proc.equals("=") && !curr.triads.proc.equals("fun")) {
                System.out.println(String.valueOf(curr.index));
                if (curr.next != null)
                    curr.next.parent = curr.parent;
                if (curr.parent.next == curr) {
                    curr.parent.next = curr.next;

                } else if (curr.parent.trueBrace == curr) {
                    curr.parent.trueBrace = curr.next;
                } else if (curr.parent.falseBrace == curr)
                    curr.parent.falseBrace = curr.next;

            }
        }
        if (curr.trueBrace != null)
            deleteUnUseTriadVar(curr.trueBrace);
        if (curr.falseBrace != null)
            deleteUnUseTriadVar(curr.falseBrace);
        if (curr.next != null)
            deleteUnUseTriadVar(curr.next);


    }

    private Vert getVertByIndex(int index, Vert curr) {
        if (curr.index == index) {
            return curr;
        }
        Vert res;
        if (curr.trueBrace != null) {
            res = getVertByIndex(index, curr.trueBrace);
            if (res != null) {
                return res;
            }
        }
        if (curr.falseBrace != null) {
            res = getVertByIndex(index, curr.falseBrace);
            if (res != null) {
                return res;
            }
        }
        if (curr.next != null) {
            res = getVertByIndex(index, curr.next);
            if (res != null) {
                return res;
            }
        }
        for (Vert vert : curr.versionVert) {
            res = getVertByIndex(index, vert);
            if (res != null) {
                return res;
            }
        }
        return null;
    }
}
