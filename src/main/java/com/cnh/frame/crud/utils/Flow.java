package com.cnh.frame.crud.utils;

import com.cnh.frame.crud.define.FlowWork;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/11/10
 */
public class Flow {


    /**
     * 条件true时执行
     *
     * @param condition
     * @param work
     * @throws Exception
     */
    public static void right(boolean condition, FlowWork work) throws Exception {
        if (condition) {
            work.execute();
        }
    }

    /**
     * 条件false时执行
     *
     * @param condition
     * @param work
     * @throws Exception
     */
    public static void wrong(boolean condition, FlowWork work) throws Exception {
        if (!condition) {
            work.execute();
        }
    }

    /**
     * 条件true时执行rightWork，否则执行wrongWork
     *
     * @param condition
     * @param rightWork
     * @param wrongWork
     * @throws Exception
     */
    public static void whether(boolean condition, FlowWork rightWork, FlowWork wrongWork) throws Exception {
        if (condition) {
            rightWork.execute();
        } else {
            wrongWork.execute();
        }
    }

    /**
     * 任意一个条件true时执行work
     *
     * @param work
     * @param condition
     * @throws Exception
     */
    public static void any(FlowWork work, boolean... condition) throws Exception {
        for (boolean b : condition) {
            if (b) {
                work.execute();
                break;
            }
        }
    }

    /**
     * 所有的条件都true时执行work
     *
     * @param work
     * @param condition
     * @throws Exception
     */
    public static void all(FlowWork work, boolean... condition) throws Exception {
        for (boolean b : condition) {
            if (!b) {
                return;
            }
        }
        work.execute();
    }


    /**
     * 至少满足rightCount次true，才执行work
     * @param rightCount
     * @param work
     * @param condition
     * @throws Exception
     */
    public static void less(int rightCount, FlowWork work, boolean... condition) throws Exception {
        boolean allow = false;
        if (rightCount < 0) {
            allow = true;
        } else {
            int count = 0;
            for (boolean b : condition) {
                if (b) {
                    count++;
                }
            }
            if (count >= rightCount) {
                allow = true;
            }
        }
        if (allow) {
            work.execute();
        }
    }


}
