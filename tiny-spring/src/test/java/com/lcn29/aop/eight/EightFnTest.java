package com.lcn29.aop.eight;

import com.lcn29.aop.eight.aop.expression.AspectJExpressionPointcut;
import com.lcn29.aop.eight.service.HelloWorldService;
import com.lcn29.aop.eight.service.impl.HelloWorldServiceImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-26 10:36
 */
public class EightFnTest {

    @Test
    public void testClassFilter() throws Exception {
        String expression = "execution(* com.lcn29.aop.eight.*.*(..))";
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression(expression);
        boolean matches = aspectJExpressionPointcut.getClassFilter().matches(HelloWorldService.class);
        Assert.assertTrue(matches);
    }

    @Test
    public void testMethodInterceptor() throws Exception {
        String expression = "execution(* com.lcn29.aop.eight.*.*(..))";
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression(expression);
        boolean matches = aspectJExpressionPointcut.getMethodMatcher().matches(HelloWorldServiceImpl.class.getDeclaredMethod("helloWorld"), HelloWorldServiceImpl.class);
        Assert.assertTrue(matches);
    }
}
