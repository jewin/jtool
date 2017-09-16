package com.jewin.common.util;

import java.math.BigDecimal;

/**
 * Created by jianyang on 17/9/16.
 * 数字比较工具
 */
public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils{

    /**
     * the first one is less than second one
     * @param isThis                        the first one
     * @param lessThanThis                  the second one
     * @return
     */
    public static boolean isLessThan(final BigDecimal isThis, final BigDecimal lessThanThis ) {
        if( isThis == null ){
            throw new IllegalArgumentException( "First argument is null" );
        }

        if( lessThanThis == null ){
            throw new IllegalArgumentException( "Second argument is null" );
        }

        return isThis.compareTo(lessThanThis) < 0;
    }

    /**
     * the first one is less and equal than second one
     * @param isThis                         the first one
     * @param lessThanOrEqualToThis          the second one
     * @return
     */
    public static boolean isLessThanOrEqualTo( final BigDecimal isThis, final BigDecimal lessThanOrEqualToThis ) {
        if( isThis == null ){
            throw new IllegalArgumentException( "First argument is null" );
        }

        if( lessThanOrEqualToThis == null ) {
            throw new IllegalArgumentException( "Second argument is null" );
        }

        return isThis.compareTo( lessThanOrEqualToThis ) <= 0;
    }

    /**
     * the first one is greater and equal than second one
     * @param isThis                          the first one
     * @param greaterThanThis                 the second one
     * @return
     */
    public static boolean isGreaterThan( final BigDecimal isThis, final BigDecimal greaterThanThis ) {
        if( isThis == null ){
            throw new IllegalArgumentException( "First argument is null" );
        }
        if( greaterThanThis == null ){
            throw new IllegalArgumentException( "Second argument is null" );
        }

        return isThis.compareTo( greaterThanThis ) > 0;
    }

    /**
     * the first one is greater and equal than second one
     * @param isThis                          the first one
     * @param greaterThanOrEqualToThis                 the second one
     * @return
     */
    public static boolean isGreaterThanOrEqualTo( final BigDecimal isThis, final BigDecimal greaterThanOrEqualToThis ) {
        if( isThis == null ){
            throw new IllegalArgumentException( "First argument is null" );
        }
        if( greaterThanOrEqualToThis == null ){
            throw new IllegalArgumentException( "Second argument is null" );
        }

        return isThis.compareTo( greaterThanOrEqualToThis ) >= 0;
    }

    /**
     * the first one is equal than second one
     * @param isThis                          the first one
     * @param equalToThis                     the second one
     * @return
     */
    public static boolean isEqualTo( final BigDecimal isThis, final BigDecimal equalToThis ) {
        if( isThis == null ){
            throw new IllegalArgumentException( "First argument is null" );
        }
        if( equalToThis == null ) {
            throw new IllegalArgumentException( "Second argument is null" );
        }

        return isThis.compareTo( equalToThis ) == 0;
    }

    /**
     * the number greater and equal than left one , and less and equal than right one
     * @param bd                             the number
     * @param left                           the left one
     * @param right                          the right one
     * @return
     */
    public static boolean isBetweenInRange( final BigDecimal bd, final BigDecimal left, final BigDecimal right ) {
        if( bd == null ){
            throw new IllegalArgumentException( "first argument is null" );
        }

        if( left == null ){
            throw new IllegalArgumentException( "left bound argument is null" );
        }

        if( right == null ){
            throw new IllegalArgumentException( "right bound argument is null" );
        }

        if( isGreaterThan(left, right) ) {
            throw new IllegalArgumentException( "left bound is greater than right bound" );
        }

        return isGreaterThanOrEqualTo( bd, left ) && isLessThanOrEqualTo( bd, right );
    }


    /**
     * the number gather than left one , and less and equal than right one
     * @param bd                             the number
     * @param left                       the left one
     * @param right                      the right one
     * @return
     */
    public static boolean isGreaterAndLessEqualRight( final BigDecimal bd, final BigDecimal left, final BigDecimal right ) {
        if( bd == null ){
            throw new IllegalArgumentException( "first argument is null" );
        }

        if( left == null ){
            throw new IllegalArgumentException( "left bound argument is null" );
        }

        if( right == null ){
            throw new IllegalArgumentException( "right bound argument is null" );
        }

        if( isGreaterThan(left, right) ) {
            throw new IllegalArgumentException( "left bound is greater than right bound" );
        }

        return isGreaterThan( bd, left ) && isLessThanOrEqualTo( bd, right );
    }


    /**
     * the number greater and equal left one , and less than right one
     * @param bd                             the number
     * @param left                       the left one
     * @param right                      the right one
     * @return
     */
    public static boolean isGreaterEqualLeftAndLessRight( final BigDecimal bd, final BigDecimal left, final BigDecimal right ) {
        if( bd == null ){
            throw new IllegalArgumentException( "first argument is null" );
        }

        if( left == null ){
            throw new IllegalArgumentException( "left bound argument is null" );
        }

        if( right == null ){
            throw new IllegalArgumentException( "right bound argument is null" );
        }

        if( isGreaterThan(left, right) ) {
            throw new IllegalArgumentException( "left bound is greater than right bound" );
        }

        return isGreaterThanOrEqualTo( bd, left ) && isLessThan( bd, right );
    }


    /**
     * the number greater left one , and less than right one
     * @param bd                             the number
     * @param lefth                       the left one
     * @param right                      the right one
     * @return
     */
    public static boolean isGreaterLeftAndLessRight( final BigDecimal bd, final BigDecimal lefth, final BigDecimal right ) {
        if( bd == null ){
            throw new IllegalArgumentException( "first argument is null" );
        }

        if( lefth == null ){
            throw new IllegalArgumentException( "left bound argument is null" );
        }

        if( right == null ){
            throw new IllegalArgumentException( "right bound argument is null" );
        }

        if( isGreaterThan(lefth, right) ) {
            throw new IllegalArgumentException( "left bound is greater than right bound" );
        }

        return isGreaterThan( bd, lefth ) && isLessThan( bd, right );
    }











    /**
     * the first one is less than second one
     * @param isThis                        the first one
     * @param lessThanThis                  the second one
     * @return
     */
    public static boolean isLessThan(final Double isThis, final Double lessThanThis ) {

        if( isThis == null ){
            throw new IllegalArgumentException( "First argument is null" );
        }

        if( lessThanThis == null ){
            throw new IllegalArgumentException( "Second argument is null" );
        }

        BigDecimal isThisBD = BigDecimal.valueOf(isThis);
        BigDecimal lessThanThisBD = BigDecimal.valueOf(lessThanThis);

        return isLessThan(isThisBD, lessThanThisBD);
    }

    /**
     * the first one is less and equal than second one
     * @param isThis                         the first one
     * @param lessThanOrEqualToThis          the second one
     * @return
     */
    public static boolean isLessThanOrEqualTo( final Double isThis, final Double lessThanOrEqualToThis ) {
        if( isThis == null ){
            throw new IllegalArgumentException( "First argument is null" );
        }

        if( lessThanOrEqualToThis == null ) {
            throw new IllegalArgumentException( "Second argument is null" );
        }

        BigDecimal isThisBD = BigDecimal.valueOf(isThis);
        BigDecimal lessThanOrEqualToThisBD = BigDecimal.valueOf(lessThanOrEqualToThis);

        return isLessThanOrEqualTo(isThisBD, lessThanOrEqualToThisBD);
    }

    /**
     * the first one is greater and equal than second one
     * @param isThis                          the first one
     * @param greaterThanThis                 the second one
     * @return
     */
    public static boolean isGreaterThan( final Double isThis, final Double greaterThanThis ) {
        if( isThis == null ){
            throw new IllegalArgumentException( "First argument is null" );
        }
        if( greaterThanThis == null ){
            throw new IllegalArgumentException( "Second argument is null" );
        }

        BigDecimal isThisBD = BigDecimal.valueOf(isThis);
        BigDecimal greaterThanThisBD = BigDecimal.valueOf(greaterThanThis);

        return isGreaterThan(isThisBD, greaterThanThisBD);
    }

    /**
     * the first one is greater and equal than second one
     * @param isThis                          the first one
     * @param greaterThanOrEqualToThis                 the second one
     * @return
     */
    public static boolean isGreaterThanOrEqualTo( final Double isThis, final Double greaterThanOrEqualToThis ) {
        if( isThis == null ){
            throw new IllegalArgumentException( "First argument is null" );
        }
        if( greaterThanOrEqualToThis == null ){
            throw new IllegalArgumentException( "Second argument is null" );
        }


        BigDecimal isThisBD = BigDecimal.valueOf(isThis);
        BigDecimal greaterThanOrEqualToThisBD = BigDecimal.valueOf(greaterThanOrEqualToThis);

        return isGreaterThanOrEqualTo(isThisBD, greaterThanOrEqualToThisBD);
    }

    /**
     * the first one is equal than second one
     * @param isThis                          the first one
     * @param equalToThis                     the second one
     * @return
     */
    public static boolean isEqualTo( final Double isThis, final Double equalToThis ) {
        if( isThis == null ){
            throw new IllegalArgumentException( "First argument is null" );
        }
        if( equalToThis == null ) {
            throw new IllegalArgumentException( "Second argument is null" );
        }

        BigDecimal isThisBD = BigDecimal.valueOf(isThis);
        BigDecimal equalToThisBD = BigDecimal.valueOf(equalToThis);

        return isEqualTo(isThisBD, equalToThisBD);
    }

    /**
     * the number greater and equal than left one , and less and equal than right one
     * @param bd                             the number
     * @param left                           the left one
     * @param right                          the right one
     * @return
     */
    public static boolean isBetweenInRange( final Double bd, final Double left, final Double right ) {
        if( bd == null ){
            throw new IllegalArgumentException( "first argument is null" );
        }

        if( left == null ){
            throw new IllegalArgumentException( "left bound argument is null" );
        }

        if( right == null ){
            throw new IllegalArgumentException( "right bound argument is null" );
        }

        if( isGreaterThan(left, right) ) {
            throw new IllegalArgumentException( "left bound is greater than right bound" );
        }

        BigDecimal bdBD = BigDecimal.valueOf(bd);
        BigDecimal leftBD = BigDecimal.valueOf(left);
        BigDecimal rightBD = BigDecimal.valueOf(right);

        return isBetweenInRange(bdBD, leftBD, rightBD);
    }


    /**
     * the number gather than left one , and less and equal than right one
     * @param bd                             the number
     * @param left                       the left one
     * @param right                      the right one
     * @return
     */
    public static boolean isGreaterAndLessEqualRight( final Double bd, final Double left, final Double right ) {
        if( bd == null ){
            throw new IllegalArgumentException( "first argument is null" );
        }

        if( left == null ){
            throw new IllegalArgumentException( "left bound argument is null" );
        }

        if( right == null ){
            throw new IllegalArgumentException( "right bound argument is null" );
        }

        if( isGreaterThan(left, right) ) {
            throw new IllegalArgumentException( "left bound is greater than right bound" );
        }

        BigDecimal bdBD = BigDecimal.valueOf(bd);
        BigDecimal leftBD = BigDecimal.valueOf(left);
        BigDecimal rightBD = BigDecimal.valueOf(right);

        return isGreaterAndLessEqualRight(bdBD, leftBD, rightBD);
    }


    /**
     * the number greater and equal left one , and less than right one
     * @param bd                             the number
     * @param left                       the left one
     * @param right                      the right one
     * @return
     */
    public static boolean isGreaterEqualLeftAndLessRight( final Double bd, final Double left, final Double right ) {
        if( bd == null ){
            throw new IllegalArgumentException( "first argument is null" );
        }

        if( left == null ){
            throw new IllegalArgumentException( "left bound argument is null" );
        }

        if( right == null ){
            throw new IllegalArgumentException( "right bound argument is null" );
        }

        if( isGreaterThan(left, right) ) {
            throw new IllegalArgumentException( "left bound is greater than right bound" );
        }

        BigDecimal bdBD = BigDecimal.valueOf(bd);
        BigDecimal leftBD = BigDecimal.valueOf(left);
        BigDecimal rightBD = BigDecimal.valueOf(right);

        return isGreaterEqualLeftAndLessRight(bdBD, leftBD, rightBD);
    }


    /**
     * the number greater left one , and less than right one
     * @param bd                             the number
     * @param left                           the left one
     * @param right                          the right one
     * @return
     */
    public static boolean isGreaterLeftAndLessRight( final Double bd, final Double left, final Double right ) {
        if( bd == null ){
            throw new IllegalArgumentException( "first argument is null" );
        }

        if( left == null ){
            throw new IllegalArgumentException( "left bound argument is null" );
        }

        if( right == null ){
            throw new IllegalArgumentException( "right bound argument is null" );
        }

        if( isGreaterThan(left, right) ) {
            throw new IllegalArgumentException( "left bound is greater than right bound" );
        }

        BigDecimal bdBD = BigDecimal.valueOf(bd);
        BigDecimal leftBD = BigDecimal.valueOf(left);
        BigDecimal rightBD = BigDecimal.valueOf(right);

        return isGreaterLeftAndLessRight(bdBD, leftBD, rightBD);
    }


}
