package com.jewin.common.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.math.BigDecimal;

/** 
* NumberUtils Tester. 
* 
* @author <Authors name> 
* @since <pre>九月 16, 2017</pre> 
* @version 1.0 
*/ 
public class NumberUtilsTest { 

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: isLessThan(final BigDecimal isThis, final BigDecimal lessThanThis)
    *
    */
    @Test
    public void testIsLessThanForIsThisLessThanThis() throws Exception {
        final BigDecimal isThis = new BigDecimal("12.34");
        final BigDecimal lessThanThis = new BigDecimal("56.78");
        Assert.assertTrue(NumberUtils.isLessThan(isThis, lessThanThis));
    }

    /**
    *
    * Method: isLessThanOrEqualTo(final BigDecimal isThis, final BigDecimal lessThanOrEqualToThis)
    *
    */
    @Test
    public void testIsLessThanOrEqualToForIsThisLessThanOrEqualToThis() throws Exception {
        final BigDecimal isThis = new BigDecimal("12.34");
        BigDecimal lessThanOrEqualToThis = new BigDecimal("56.78");
        Assert.assertTrue(NumberUtils.isLessThanOrEqualTo(isThis, lessThanOrEqualToThis));

        lessThanOrEqualToThis = new BigDecimal("12.34");
        Assert.assertTrue(NumberUtils.isLessThanOrEqualTo(isThis, lessThanOrEqualToThis));
    }

    /**
    *
    * Method: isGreaterThan(final BigDecimal isThis, final BigDecimal greaterThanThis)
    *
    */
    @Test
    public void testIsGreaterThanForIsThisGreaterThanThis() throws Exception {
        final BigDecimal isThis  = new BigDecimal("56.78");
        final BigDecimal greaterThanThis = new BigDecimal("12.34");
        Assert.assertTrue(NumberUtils.isGreaterThan(isThis, greaterThanThis));
    }

    /**
    *
    * Method: isGreaterThanOrEqualTo(final BigDecimal isThis, final BigDecimal greaterThanOrEqualToThis)
    *
    */
    @Test
    public void testIsGreaterThanOrEqualToForIsThisGreaterThanOrEqualToThis() throws Exception {
        final BigDecimal isThis  = new BigDecimal("56.78");
        BigDecimal greaterThanOrEqualToThis = new BigDecimal("12.34");
        Assert.assertTrue(NumberUtils.isGreaterThanOrEqualTo(isThis, greaterThanOrEqualToThis));

        greaterThanOrEqualToThis = new BigDecimal("56.78");
        Assert.assertTrue(NumberUtils.isGreaterThanOrEqualTo(isThis, greaterThanOrEqualToThis));
    }

    /**
    *
    * Method: isEqualTo(final BigDecimal isThis, final BigDecimal equalToThis)
    *
    */
    @Test
    public void testIsEqualToForIsThisEqualToThis() throws Exception {
        final BigDecimal isThis = new BigDecimal("12.34");
        final BigDecimal equalToThis = new BigDecimal("12.34");
        Assert.assertTrue(NumberUtils.isEqualTo(isThis, equalToThis));
    }

    /**
    *
    * Method: isBetweenInRange(final BigDecimal bd, final BigDecimal left, final BigDecimal right)
    *
    */
    @Test
    public void testIsBetweenInRangeForBdLeftRight() throws Exception {
        final BigDecimal bd = new BigDecimal("56.78");
        final BigDecimal left = new BigDecimal("12.34");
        final BigDecimal right = new BigDecimal("90.12");

        Assert.assertTrue(NumberUtils.isBetweenInRange(bd, left, right));
    }

    /**
    *
    * Method: isGreaterAndLessEqualRight(final BigDecimal bd, final BigDecimal left, final BigDecimal right)
    *
    */
    @Test
    public void testIsGreaterAndLessEqualRightForBdLeftRight() throws Exception {
        BigDecimal bd = new BigDecimal("56.78");
        final BigDecimal left = new BigDecimal("12.34");
        final BigDecimal right = new BigDecimal("90.12");

        Assert.assertTrue(NumberUtils.isGreaterAndLessEqualRight(bd, left, right));

        bd = new BigDecimal("12.34");
        Assert.assertTrue(!NumberUtils.isGreaterAndLessEqualRight(bd, left, right));

        bd = new BigDecimal("90.12");
        Assert.assertTrue(NumberUtils.isGreaterAndLessEqualRight(bd, left, right));

        bd = new BigDecimal("90.13");
        Assert.assertTrue(!NumberUtils.isGreaterAndLessEqualRight(bd, left, right));

    }

    /**
    *
    * Method: isGreaterEqualLeftAndLessRight(final BigDecimal bd, final BigDecimal left, final BigDecimal right)
    *
    */
    @Test
    public void testIsGreaterEqualLeftAndLessRightForBdLeftRight() throws Exception {
        BigDecimal bd = new BigDecimal("56.78");
        final BigDecimal left = new BigDecimal("12.34");
        final BigDecimal right = new BigDecimal("90.12");

        Assert.assertTrue(NumberUtils.isGreaterEqualLeftAndLessRight(bd, left, right));

        bd = new BigDecimal("12.33");
        Assert.assertTrue(!NumberUtils.isGreaterEqualLeftAndLessRight(bd, left, right));

        bd = new BigDecimal("90.12");
        Assert.assertTrue(!NumberUtils.isGreaterEqualLeftAndLessRight(bd, left, right));

        bd = new BigDecimal("90.13");
        Assert.assertTrue(!NumberUtils.isGreaterEqualLeftAndLessRight(bd, left, right));
    }

    /**
    *
    * Method: isGreaterLeftAndLessRight(final BigDecimal bd, final BigDecimal lefth, final BigDecimal right)
    *
    */
    @Test
    public void testIsGreaterLeftAndLessRightForBdLefthRight() throws Exception {
        BigDecimal bd = new BigDecimal("56.78");
        final BigDecimal left = new BigDecimal("12.34");
        final BigDecimal right = new BigDecimal("90.12");

        Assert.assertTrue(NumberUtils.isGreaterLeftAndLessRight(bd, left, right));

        bd = new BigDecimal("12.35");
        Assert.assertTrue(NumberUtils.isGreaterLeftAndLessRight(bd, left, right));

        bd = new BigDecimal("90.12");
        Assert.assertTrue(!NumberUtils.isGreaterLeftAndLessRight(bd, left, right));

        bd = new BigDecimal("90.13");
        Assert.assertTrue(!NumberUtils.isGreaterLeftAndLessRight(bd, left, right));
    }

    /**
    *
    * Method: isGreaterLeftAndLessRight(final Double bd, final Double left, final Double right)
    *
    */
    @Test
    public void testIsGreaterLeftAndLessRightForBdLeftRight() throws Exception {
        final Double bd = 56.78d;
        final Double left = 12.34d;
        final Double right = 90.12d;

        Assert.assertTrue(NumberUtils.isGreaterLeftAndLessRight(bd, left, right));
    }


} 
