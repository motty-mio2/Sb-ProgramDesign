package fclass;

import java.util.ArrayList;
import java.util.Scanner;

class nVariable {
    ArrayList<String> vnames = new ArrayList<String>();
    ArrayList<Float> vnumsF = new ArrayList<Float>();
    ArrayList<Integer> vnumsI = new ArrayList<Integer>();
    ArrayList<String> types = new ArrayList<String>();
    int len = 0;

    void set(String s, String num) {
        int point = vnames.indexOf(s);
        try { // Int
            int i = Integer.parseInt(num);
            if (point == -1) {
                vnames.add(s);
                vnumsF.add(0f);
                vnumsI.add(i);
                types.add("Int");
                len += 1;
            } else {
                vnumsI.set(point, i);
                types.set(point, "Int");
            }
        } catch (NumberFormatException e) {
            try { // Float
                float f = Float.parseFloat(num);
                if (point == -1) {
                    vnames.add(s);
                    vnumsF.add(f);
                    vnumsI.add(0);
                    types.add("Float");
                    len += 1;
                } else {
                    vnumsF.set(point, f);
                    types.set(point, "Float");
                }
            } catch (NumberFormatException e2) {

            }
        }
    }

    String type(String s) {
        int p = vnames.indexOf(s);
        if (p == -1) {
            return "None";
        } else {
            return types.get(p);
        }
    }

    ArrayList<String> get(String s) {
        ArrayList<String> ret = new ArrayList<>();
        int p = vnames.indexOf(s);
        if (p == -1) {
            ret.add("0");
            ret.add("None");
        } else if (types.get(p) == "Int") {
            ret.add(Integer.toString(vnumsI.get(p)));
            ret.add("Int");
        } else /* if (types.get(p) == "Int") */ {
            ret.add(Float.toString(vnumsF.get(p)));
            ret.add("Float");
        }
        return ret;
    }
}

public class process {
    static nVariable nv = new nVariable();

    static int min_List(ArrayList<Integer> numbers) {
        int ret = Integer.MAX_VALUE;
        for (int i = 0; i < numbers.size(); i++) {
            int t = numbers.get(i);
            if (t != -1) {
                ret = Math.min(ret, t);
            }
        }

        if (ret == Integer.MAX_VALUE) {
            return -1;
        } else {
            return ret;
        }
    }

    static String deftype(String num) {
        try { // Int
            Integer.parseInt(num);
            return "Int";
        } catch (NumberFormatException e) {
            try { // Float
                Float.parseFloat(num);
                return "Float";
            } catch (NumberFormatException e2) {
                return "Error";
            }
        }
    }

    static String[] num_process(String cmd) {
        ArrayList<String> nums = new ArrayList<>();
        ArrayList<String> type = new ArrayList<>();
        ArrayList<String> opes = new ArrayList<>();
        String[] ret = { "", "" };

        String[] oplist = { "+", "-", "*", "/", "足す", "引く", "×", "割る", "たす", "ひく", "かける", "わる" };

        int isloop = 1;
        do {
            if (cmd.substring(0, 1).equals("(")) {
                int l = cmd.indexOf(")");
                String[] ic = num_process(cmd.substring(1, l));
                cmd = ic[0] + cmd.substring(l + 1);
            } else if (cmd.substring(0, 1).equals(")")) {
                break;
            }
            ArrayList<Integer> ls = new ArrayList<>();
            for (String s : oplist) {
                ls.add(cmd.indexOf(s));
            }
            isloop = min_List(ls);
            if (isloop != -1) {
                String n1 = cmd.substring(0, isloop);
                ArrayList<String> r = nv.get(n1);
                if (r.get(1) == "None") {
                    r.set(0, n1);
                    r.set(1, deftype(n1));
                }
                nums.add(r.get(0));
                type.add(r.get(1));
                cmd = cmd.substring(n1.length());
                for (int i = 0; i < oplist.length; i++) {
                    String t = cmd.substring(0, oplist[i].length());
                    if (t.equals(oplist[i])) {
                        opes.add(oplist[i % 4]);
                        cmd = cmd.substring(oplist[i].length());
                        break;
                    }
                }
            } else {
                String n1 = cmd;
                ArrayList<String> r = nv.get(n1);
                if (r.get(1) == "None") {
                    r.set(0, n1);
                    r.set(1, deftype(n1));
                }
                nums.add(r.get(0));
                type.add(r.get(1));
                cmd.replaceFirst(n1, "");
            }

        } while (isloop >= 0);

        String out = (type.indexOf("Float") >= 0 || opes.indexOf("/") >= 0) ? "Float" : "Int";

        if (type.indexOf("Error") >= 0) {
            ret[1] = "Error";
        } else if (out == "Int") {
            int xp = opes.indexOf("*");
            while (xp >= 0) {
                nums.set(xp, Integer.toString(Integer.parseInt(nums.get(xp)) * Integer.parseInt(nums.get(xp + 1))));
                nums.remove(xp + 1);
                opes.remove(xp);
                xp = opes.indexOf("*");
            }

            xp = opes.indexOf("+");
            while (xp >= 0) {
                nums.set(xp, Integer.toString(Integer.parseInt(nums.get(xp)) + Integer.parseInt(nums.get(xp + 1))));
                nums.remove(xp + 1);
                opes.remove(xp);
                xp = opes.indexOf("+");
            }

            xp = opes.indexOf("-");
            while (xp >= 0) {
                nums.set(xp, Integer.toString(Integer.parseInt(nums.get(xp)) - Integer.parseInt(nums.get(xp + 1))));
                nums.remove(xp + 1);
                opes.remove(xp);
                xp = opes.indexOf("-");
            }

            ret[0] = nums.get(0);
            ret[1] = "True";
        } else {
            int xp = opes.indexOf("*");
            while (xp >= 0) {
                nums.set(xp, Float.toString(Float.parseFloat(nums.get(xp)) * Float.parseFloat(nums.get(xp + 1))));
                nums.remove(xp + 1);
                opes.remove(xp);
                xp = opes.indexOf("*");
            }

            xp = opes.indexOf("/");
            while (xp >= 0) {
                nums.set(xp, Float.toString(Float.parseFloat(nums.get(xp)) / Float.parseFloat(nums.get(xp + 1))));
                nums.remove(xp + 1);
                opes.remove(xp);
                xp = opes.indexOf("/");
            }

            xp = opes.indexOf("+");
            while (xp >= 0) {
                nums.set(xp, Float.toString(Float.parseFloat(nums.get(xp)) + Float.parseFloat(nums.get(xp + 1))));
                nums.remove(xp + 1);
                opes.remove(xp);
                xp = opes.indexOf("+");
            }

            xp = opes.indexOf("-");
            while (xp >= 0) {
                nums.set(xp, Float.toString(Float.parseFloat(nums.get(xp)) - Float.parseFloat(nums.get(xp + 1))));
                nums.remove(xp + 1);
                opes.remove(xp);
                xp = opes.indexOf("-");
            }

            ret[0] = nums.get(0);
            ret[1] = "True";
        }

        return ret;

    }

    static ArrayList<String> parser(String inputcommand) { // {コマンド, 表示するか(T/N)}
        ArrayList<String> ret = new ArrayList<>();
        int l = Math.max(inputcommand.lastIndexOf("を"), inputcommand.lastIndexOf("と"));
        // return inputcommand.substring(l +
        if (inputcommand.substring(l + 1).equals("表示")) {
            if (inputcommand.contains("「")) {
                int st = inputcommand.indexOf("「") + 1;
                int ln = inputcommand.lastIndexOf("」");
                ret.add(inputcommand.substring(st, ln));
                ret.add("True");
                return ret;
            } else if (inputcommand.contains("(")) {
                int st = inputcommand.indexOf("(") + 1;
                int ln = inputcommand.lastIndexOf(")");
                String[] res = num_process(inputcommand.substring(st, ln));
                // System.out.println(nv.get(s));
                if (res[1].equals("Error")) {
                    ret.add("None");
                    ret.add("None");
                } else {
                    ret.add(res[0]);
                    ret.add("True");
                }
            } else {
                String[] res = num_process(inputcommand.substring(0, l));
                if (res[1].equals("Error")) {
                    ret.add("None");
                    ret.add("None");
                } else {
                    ret.add(res[0]);
                    ret.add("True");
                }
            }
        } else if (inputcommand.contains("は") || inputcommand.contains("=")) {
            int p = Math.max(inputcommand.lastIndexOf("は"), inputcommand.indexOf("="));
            String s = inputcommand.substring(0, p);
            String target = inputcommand.substring(p + 1);
            String[] res = num_process(target);
            // System.out.println(nv.get(s));
            if (res[1].equals("Error")) {
                ret.add("None");
                ret.add("None");
            } else {
                nv.set(s, res[0]);
                ret.add(res[0]);
                ret.add("False");
            }

        } else {
            ret.add("None");
            ret.add("None");
        }
        return ret;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.print("input Nadeshiko Command >>> ");
            String str = scan.next();
            if (str.equals("exit") || str.equals("終了")) {
                break;
            }
            ArrayList<String> ret = parser(str);
            if (ret.get(1).equals("True")) {
                System.out.println(ret.get(0));
            }
        }
        scan.close();
    }
}