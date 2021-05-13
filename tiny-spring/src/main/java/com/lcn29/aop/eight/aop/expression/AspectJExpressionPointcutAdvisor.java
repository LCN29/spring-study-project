package com.lcn29.aop.eight.aop.expression;

import com.lcn29.aop.eight.aop.pointcut.Pointcut;
import com.lcn29.aop.eight.aop.pointcut.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-22 20:55
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    private AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

    private Advice advice;

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public void setExpression(String expression) {
        this.pointcut.setExpression(expression);
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

}
