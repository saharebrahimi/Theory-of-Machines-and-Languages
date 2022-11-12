package com.company;

import java.util.*;


public class Main {
    private static ArrayList<List<String>> newright = new ArrayList<>();
    private static ArrayList<List<String>> newcopyright = new ArrayList<>();
    private static ArrayList<List<String>> check = new ArrayList<>();
    private static ArrayList<List<String>> unitremove = new ArrayList<>();
    private static ArrayList<List<String>> copyright = new ArrayList<>();
    private static List<String> redun = new ArrayList<>();
    private static List<String> left = new ArrayList<>();
    private static List<String> landa = new ArrayList<>();
    private static List<String> right = new ArrayList<>();


    public static void main(String[] args) {
        //get Grammar
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter a Grammar: ");
        String n = reader.next();
        reader.close();
        String[] items = n.split(",");
        List<String> input = new ArrayList<>();
        Collections.addAll(input, items);

        //seperate right and left part of Grammar
        List<String> v = new ArrayList<>();

        for (String item2 : input) {
            left.add(item2.substring(0, item2.indexOf(">")));
            right.add(item2.substring(item2.indexOf(">") + 1, item2.length()));

        }
        String[] leftside = new String[left.size()];
        String[] rightside = new String[right.size()];
        leftside = left.toArray(leftside);
        rightside = right.toArray(rightside);

        //landa production
        for (int i = 0; i < rightside.length; i++) {
            if (rightside[i].contains("#")) {
                v.add(leftside[i]);
            }
        }
        int num;
        int vsize = v.size();
        for (int i = 0; i < vsize; i++) {
            for (int j = 0; j < rightside.length; j++) {
                ///
                num = 0;
                if (rightside[j].length() == 1) {
                    if (rightside[j].equals(v.get(i)) && !v.contains(leftside[j])) {
                        v.add(leftside[j]);
                    }
                } else
                    for (int e = 0; e < rightside[j].length(); e++) {
                        char w = rightside[j].charAt(e);
                        if (v.contains(String.valueOf(w))) {
                            num++;
                        } else
                            break;
                        if (num == rightside[j].length() && !v.contains(leftside[j]))
                            v.add(leftside[j]);

                    }
            }

        }

        v.remove("S");


        for (int i = 0; i < rightside.length; i++) {
            List<String> temp = new ArrayList<>();
            List<String> temp2 = new ArrayList<>();
            temp2.add("notempty");
            List<String> finallist = new ArrayList<>();
            List<String> finalcopylist = new ArrayList<>();
            String t = rightside[i];
            if (t.equals("#")) {
                landa.add(leftside[i]);
                continue;
            }
            temp.add(t);
            finallist.add(t);
            finalcopylist.add(t);
            int count = 0;
            for (String aV1 : v) {
                if (t.contains(aV1)) {
                    count++;
                }


            }

            if (count > 0) {
                while (temp2.size() != 0) {

                    temp2.remove("notempty");
                    for (String aV : v) {

                        temp.add(temp.get(0).replaceFirst(aV, ""));
                        if (temp.get(0).equals(temp.get(1)) || temp.get(0).replaceFirst(aV, "").equals("")) {
                            temp.clear();
                            temp.add(t);
                        } else {
                            temp2.add(temp.get(1));
                            if (!finallist.contains(temp.get(1))) {
                                finallist.add(temp.get(1));
                                finalcopylist.add(temp.get(1));
                            }
                            temp.clear();
                            temp.add(t);

                        }
                    }
                    temp.clear();
                    if (!temp2.isEmpty()) {
                        temp.add(temp2.get(0));
                        t = temp2.get(0);
                        temp2.remove(0);
                    }
                }
            }
            newright.add(finallist);
            copyright.add((finalcopylist));

        }


        //unit production


        for (int i = 0; i < right.size(); i++) {
            if (right.get(i).equals("#")) {
                left.remove(i);
                right.remove(i);
                i--;
            }
        }
        ArrayList<String> copyleft = new ArrayList<>(left);
        String w;
        left.clear();
        for (String aCopyleft : copyleft) {
            w = aCopyleft;
            if (!left.contains(w))
                left.add(w);
        }

        for (String aLeft : left) {
            List<String> c = new ArrayList<>();
            for (int j = 0; j < copyleft.size(); j++) {
                if (aLeft.equals(copyleft.get(j))) {
                    for (int u = 0; u < newright.get(j).size(); u++) {
                        if (!c.contains(newright.get(j).get(u)))
                            c.add(newright.get(j).get(u));
                    }
                }
            }
            check.add(c);

        }

        newright.clear();

        newright = new ArrayList<>(check);

        for (String aLanda : landa) {
            for (List<String> aNewright : newright) {
                for (int j = 0; j < aNewright.size(); j++) {
                    if ((aNewright.get(j).equals(aLanda)) && !aNewright.contains("#")) {
                        aNewright.add("#");
                    }
                }

            }
        }


        for (int i = 0; i < left.size(); i++) {
            List<String> unit = new ArrayList<>();

            unitremove.add(i, AddMissingProduction(left.get(i), unit));
        }


        newcopyright = new ArrayList<>(newright);

        for (List<String> aNewcopyright : newcopyright) {

            for (int j = 0; j < aNewcopyright.size(); j++) {
                if ((aNewcopyright.get(j).toUpperCase().equals(aNewcopyright.get(j)) && aNewcopyright.get(j).length() == 1 && !aNewcopyright.get(j).equals("#"))) {
                    aNewcopyright.remove(aNewcopyright.get(j));
                    j--;
                }
            }

        }

        for (int u = 0; u < left.size(); u++) {


            for (int j = 0; j < unitremove.get(u).size(); j++) {
                if (!unitremove.get(u).get(j).isEmpty() && left.indexOf(unitremove.get(u).get(j)) != -1) {
                    for (int r = 0; r < newright.get(left.indexOf(unitremove.get(u).get(j))).size(); r++) {
                        if (left.indexOf(unitremove.get(u).get(j)) != -1)
                            if (!newcopyright.contains(newright.get(left.indexOf(unitremove.get(u).get(j))).get(r)))
                                newcopyright.get(u).add(newright.get(left.indexOf(unitremove.get(u).get(j))).get(r));
                    }

                }

            }


        }

        newright.clear();
        newright = new ArrayList<>(newcopyright);


        //useless production
        List<String> terminal = new ArrayList<>();
        List<String> newterminal = new ArrayList<>();

        for (int u = 0; u < newright.size(); u++) {
            for (int r = 0; r < newright.get(u).size(); r++) {
                if (newright.get(u).get(r).toLowerCase().equals(newright.get(u).get(r))) {
                    terminal.add(left.get(u));
                    break;
                }

            }
        }


        ArrayList<String> tempterminal = new ArrayList<>(terminal);
        ArrayList<String> finalterminal = new ArrayList<>();

        int tz = tempterminal.size();
        int ntz = newterminal.size();
        while (tz != ntz) {
            tz = terminal.size();
            for (String aTerminal : terminal) {
                for (int u = 0; u < newright.size(); u++) {
                    for (int r = 0; r < newright.get(u).size(); r++) {
                        if (newright.get(u).get(r).contains(aTerminal) && !newright.get(u).get(r).contains(left.get(u)))
                            if (newright.get(u).get(r).length() == 1 || !newright.get(u).get(r).toUpperCase().equals(newright.get(u).get(r)))
                                newterminal.add(left.get(u));
                    }
                }
            }
            ntz = newterminal.size();
            terminal.clear();
            terminal = new ArrayList<>(newterminal);

            finalterminal.addAll(newterminal);
            newterminal.clear();

        }
        finalterminal.addAll(tempterminal);
        Set<String> hs = new HashSet<>();
        hs.addAll(finalterminal);
        finalterminal.clear();
        finalterminal.addAll(hs);


        if (!finalterminal.contains("S"))
            finalterminal.add(0, "S");
        ArrayList<String> removable = new ArrayList<>(left);
        removable.removeAll(finalterminal);


        for (String aRemovable1 : removable) {
            newright.remove(left.indexOf(aRemovable1));
            left.remove(aRemovable1);
        }


        for (String aRemovable : removable) {
            for (List<String> aNewright : newright) {

                for (int r = 0; r < aNewright.size(); r++) {
                    if (aNewright.get(r).contains(aRemovable))
                        aNewright.remove(aNewright.get(r));
                }
            }
        }


        reach("S");
        if (!redun.contains("S"))
            redun.add(0, "S");


        ArrayList<String> notReachable = new ArrayList<>(left);
        notReachable.removeAll(redun);

        for (String aNotReachable1 : notReachable) {
            newright.remove(left.indexOf(aNotReachable1));
            left.remove(aNotReachable1);
        }

        for (String aNotReachable : notReachable) {
            for (List<String> aNewright : newright) {
                for (int r = 0; r < aNewright.size(); r++) {
                    if (aNewright.get(r).contains(aNotReachable))
                        aNewright.remove(aNewright.get(r));
                }
            }
        }

        System.out.println("Result :");
        for (int i = 0; i < newright.size(); i++) {
            System.out.print(left.get(i) + "->" + " ");
            for (int j = 0; j < newright.get(i).size(); j++) {
                if (j != 0)
                    System.out.print("|");
                System.out.print(newright.get(i).get(j));

            }
            if (i != newright.size() - 1)
                System.out.print(" " + "," + " ");
        }


    }

    private static void reach(String r) {

        if (left.indexOf(r) != -1)
            for (int k = 0; k < newright.get(left.indexOf(r)).size(); k++) {
                if (!newright.get(left.indexOf(r)).get(k).toLowerCase().equals(newright.get(left.indexOf(r)).get(k))) {
                    for (int h = 0; h < newright.get(left.indexOf(r)).get(k).length(); h++) {
                        if (newright.get(left.indexOf(r)).get(k).charAt(h) <= 90 && 65 <= newright.get(left.indexOf(r)).get(k).charAt(h) && !redun.contains(String.valueOf(newright.get(left.indexOf(r)).get(k).charAt(h)))) {
                            redun.add(String.valueOf(newright.get(left.indexOf(r)).get(k).charAt(h)));
                            reach(String.valueOf(newright.get(left.indexOf(r)).get(k).charAt(h)));

                        }

                    }


                }
            }
    }


    private static List<String> AddMissingProduction(String r, List unit) {
        for (int y = 0; y < left.size(); y++) {
            if (left.get(y).equals(r))
                for (int k = 0; k < newright.get(left.indexOf(r)).size(); k++) {
                    for (int h = 0; h < newright.get(left.indexOf(r)).get(k).length(); h++)


                        if (newright.get(left.indexOf(r)).get(k).length() == 1 && newright.get(left.indexOf(r)).get(k).toUpperCase().equals(newright.get(left.indexOf(r)).get(k)) && !unit.contains(String.valueOf(newright.get(left.indexOf(r)).get(k)))) {
                            unit.add(String.valueOf(newright.get(left.indexOf(r)).get(k)));
                            AddMissingProduction(newright.get(left.indexOf(r)).get(k), unit);

                        }


                }
        }
        if (unit.contains("#"))
            unit.remove("#");
        return unit;

    }
}


