package cn.javatools.core.util;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTool {

    /**
     * 字符串替换 将字符串中 #{[]}包含的值替换为给定的值
     * @param template 模板字符串
     * @param paramsMap 待替换的值数组
     * @return 替换后的字符串
     */
    public static String getStrByTemplate(String template, Map paramsMap){
        String exp = "\\[(.*?)\\]";
        Pattern pattern = Pattern.compile(exp);
        Matcher matcher = pattern.matcher(template);

        Map valuesMap = new HashMap();
        while (matcher.find()){
            String group = matcher.group();
            String key = group.substring(1, group.length()-1);
            valuesMap.put(key, paramsMap.get(key));
        }

        ExpressionParser parser = new SpelExpressionParser();
        TemplateParserContext parserContext = new TemplateParserContext();
        String content = parser.parseExpression(template, parserContext).getValue(valuesMap, String.class);
        return content;
    }
}
