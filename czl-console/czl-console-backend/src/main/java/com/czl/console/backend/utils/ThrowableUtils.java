package com.czl.console.backend.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Author: CHEN ZHI LING
 * Date: 2023/7/4
 * Description:
 */
public class ThrowableUtils {

    public static String extractStackTrace(Throwable t)
    {
        StringWriter me = new StringWriter();
        PrintWriter pw = new PrintWriter(me);
        t.printStackTrace(pw);
        pw.flush();
        return me.toString();
    }
}
