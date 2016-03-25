package com.example.roysatm.calculator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by roysatm on 2016/3/25.
 */
public class CalcUnit {

    public static String result(String s) {
        double result = 0;
        SamplenessStack samplenessStack = new SamplenessStack();
        try {
            result = samplenessStack.stack(s);
        } catch (Exception e) {
            e.printStackTrace();
            return "出错";
        }
        return ""+result;
    }
}

class SamplenessStack {

    //创建动态数组list，用于存放输入的表达式及后面计算部分结果存放
    ArrayList list = new ArrayList();
    //创建动态数组rearlist,用于缓存括号内的计算结果
    ArrayList rearlist = new ArrayList();
    //创建栈stack1,stack2
    Stack stack1 = new Stack();
    Stack stack2 = new Stack();
    private int i;
    private int j;
    private int t;
    private int flag;
    private int flag_m;
    private double sum;
    private double f;

    //判断运算符优先级
    public int opratorPrior(char c) {
        switch (c) {
            case '(':
                return 1;
            case ')':
                return 1;
            case '*':
                return 2;
            case '/':
                return 2;
            case '+':
                return 3;
            case '-':
                return 3;
            default:
                return -1;
        }
    }

    public Double stack(String s) throws IOException {
        //把输入的表达式从String类型中的数字转换成double
        while (i < s.length()) {
            //判断是否为数字和小数点
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9' || s.charAt(i) == '.') {
                //先全部转换成整数
                if (s.charAt(i) != '.')
                    sum = (s.charAt(i) - '0') + sum * 10;
                else
                    //标记小数点位置，方便后面统计数值(没有使用hashcode)
                    j = i;
                t = 0;
                f++;
            }
            if ((s.charAt(i) < '0' || s.charAt(i) > '9') && s.charAt(i) != '.' || s.length() - 1 == i) {                               //判断四则运算符，和括号
                t++;
                if (t == 1) {
                    //这条if语句颇有意思，判断最后一个字符是不是‘)’
                    if (s.length() - 1 == i) {
                        //如果不判断则可能导致自己输入的数经过String转换double存到list数组中顺序有变化。
                        //你输入的是(6+6)/9.2存到list数组可能变成(6+)6/9.2，可能还会产生别的变化。
                        if (j != 0)
                            //转换成我们输入的数
                            sum = sum / Math.pow(10, i - j);
                        //放入动态数组
                        list.add(sum);
                        //放入动态数组
                        if (s.charAt(i) == ')')
                            list.add(s.charAt(i));
                    } else {
                        if (j != 0)
                            sum = sum / Math.pow(10, i - j - 1);
                        if (f != 0)
                            list.add(sum);
                        list.add(s.charAt(i));
                    }
                } else {
                    list.add(s.charAt(i));
                }
                j = 0;
                sum = 0;
            }
            i++;
        }
//在输入的表达式前面加上一对()方便后面计算使用
        list.add(')');
        list.add(0, '(');
        // 这个for语句很重要, 有了这条for语句才能自由的使用负号例如-5*5-3或6+(-9)，本程序负号和减号为同一符号
        // 这个for语句的这几条if语句对应了所有由‘-’引起的bug。
        for (int d = 0; d < list.size(); d++) {
            if (list.get(d).equals('-') && list.get(d - 1).equals('(') && list.get(d + 1).equals('(')) {
                for (int r = 0; r < list.size(); r++)
                    if (list.get(r).equals(')')) {
                        list.add(r, ')');
                        list.add(d, 0);
                        list.add(d, '(');
                        break;
                    }
                continue;
            }
            if (list.get(d).equals('-') && list.get(d - 1).equals('(') && !list.get(d + 1).equals(')')) {
                list.add(d, 0);
                list.add(d, '(');
                list.add(d + 4, ')');
            }
            if (list.get(d).equals('-') && list.get(d - 1).equals('(') && list.get(d + 1).equals(')'))
                list.add(d, 0);
        }
        double result = amongChangeRear();
        return result;
    }

    public double amongChangeRear() throws IOException {
        //这个for循环把中缀表达式转后缀表达式和后缀表达式计算一起进行的，就是中缀转后缀和后缀计算同步的，
        for (int i = 0; i < list.size(); i++) {
            //这也是本程序与别人的不同之处，举个例子(3+(6+9*6)*2)本程序是这么转换的先把6+9*2转换成
            //后缀表达式6 9 2 * +然后计算结果等于24，然后把24存入计算式就变成了(3+24)然后再接着计算....
            if (list.get(i).equals(')')) {
                int s = 0;
                s = i;
                while (!list.get(s).equals('('))
                    s--;
                for (int m = s + 1; m < i; m++) {
                    if (!list.get(m).toString().equals("*") && !list.get(m).toString().equals("/") && !list.get(m).toString().equals("+") && !list.get(m).toString().equals("-")) {
                        rearlist.add(list.get(m));
                        if (flag == 1)
                            rearlist.add(list.get(flag_m));
                        flag = 0;
                        continue;
                    }
                    if (stack2.empty()) {
                        stack2.push(list.get(m));
                        continue;
                    }
                    if (opratorPrior(list.get(m).toString().charAt(0)) < opratorPrior(stack2.peek().toString().charAt(0))) {
                        flag = 1;
                        flag_m = m;
                    } else {
                        rearlist.add(stack2.pop());
                        stack2.push(list.get(m));
                    }
                }
                //一组括号内表达式计算完后，把这组式子移除，否则重复计算这组表达式
                while (i >= s) {
                    list.remove(i);
                    i--;
                }
                if (!stack2.empty())
                    rearlist.add(stack2.pop());
                //用于计算表达式，用到了栈，用栈计算后缀表达式很方便
                for (int n = 0; n < rearlist.size(); n++) {
                    if (!rearlist.get(n).toString().equals("*") && !rearlist.get(n).toString().equals("/") && !rearlist.get(n).toString().equals("+") && !rearlist.get(n).toString().equals("-"))
                        stack1.push(rearlist.get(n));
                    else {
                        //将String对象转换成double数值
                        double result2 = Double.parseDouble(stack1.pop().toString());
                        double result1 = Double.parseDouble(stack1.pop().toString());
                        double result = 0;
                        if (rearlist.get(n).toString().equals("*")) {
                            result = result1 * result2;
                        }
                        if (rearlist.get(n).toString().equals("/")) {
                            result = result1 / result2;
                        }
                        if (rearlist.get(n).toString().equals("+")) {
                            result = result1 + result2;

                        }
                        if (rearlist.get(n).toString().equals("-")) {
                            result = result1 - result2;
                        }
                        stack1.push(result);
                    }
                }
                list.add(s, stack1.pop());
                //rearlist数组是重复多次调用的，为了不影响下次调用rearlist数组，所以每个括号里的表达式计算完后要把数组清空
                rearlist.clear();
            }
        }
        //完全计算后，list数组中就只有一个数，就是最后计算的结果，所以打印出结果。
        return (Double) list.get(0);
    }

}
