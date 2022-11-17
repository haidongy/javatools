package cn.javatools.core.text;

import java.text.DecimalFormat;

public class RmbTool {
    private final static String[] STR_NUMBER = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private final static String[] STR_UNIT = {"", "拾", "佰", "仟", "萬", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "萬"};
    private final static String[] STR_UNIT2 = {"角", "分", "厘"};

    public static String convert(double number) {
        if (number == 0) return "零圆";
        DecimalFormat df = new DecimalFormat("#0.###");
        String strNum = df.format(number);
        
        boolean isInteger = !strNum.contains(".");
        if (isInteger) {
            return getInteger(strNum) + "圆整";
        } else {
            if (strNum.substring(0, strNum.indexOf(".")).length() > 13) {
                return "";
            }
            String num = strNum.substring(0, strNum.indexOf("."));
            return getInteger(num) + "圆" + getDecimal(strNum);
        }
    }

    private static String getInteger(String num) {
        num = new StringBuffer(num).reverse().toString();
        StringBuffer temp = new StringBuffer();
        for (int i = 0; i < num.length(); i++) {
            temp.append(STR_UNIT[i]);
            temp.append(STR_NUMBER[num.charAt(i) - 48]);
        }
        num = temp.reverse().toString();
        String[] oldStr = {"零拾", "零佰", "零仟", "零萬", "零亿", "零零", "亿万"};
        String[] newStr = {"零", "零", "零", "萬", "亿", "零", "亿"};
        for (int i = 0; i < oldStr.length; i++) {
            num = replace(num, oldStr[i], newStr[i]);
        }

        if (num.lastIndexOf("零") == num.length() - 1) {
            num = num.substring(0, num.length() - 1);
        }
        return num;
    }

    private static String getDecimal(String num) {
        if (num.indexOf(".") == -1) {
            return "";
        }
        num = num.substring(num.indexOf(".") + 1);
        num = new StringBuffer(num).toString();
        StringBuffer temp = new StringBuffer();
        for (int i = 0; i < num.length(); i++) {
            temp.append(STR_NUMBER[num.charAt(i) - 48]);
            temp.append(STR_UNIT2[i]);
        }
        num = temp.toString();
        String[] oldStr = {"零角", "零分", "零厘", "零零"};
        for (int i = 0; i < oldStr.length; i++) {
            num = replace(num, oldStr[i], "零");
        }

        if (num.lastIndexOf("零") == num.length() - 1) {
            num = num.substring(0, num.length() - 1);
        }
        return num;
    }

    private static String replace(String num, String oldStr, String newStr) {
        while (true) {
            if (num.indexOf(oldStr) == -1) {
                break;
            }
            num = num.replaceAll(oldStr, newStr);
        }
        return num;
    }
}
