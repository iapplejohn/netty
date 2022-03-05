package com.jemmy.netty.memory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import sun.misc.VM;

/**
 * @author zhujiang.cheng
 * @since 2021/3/10
 */
public class DirectByteBufferTest {

    public static void main(String[] args)
        throws IOException {
        ByteBuffer buffer = null;
        try {
            buffer = ByteBuffer.allocateDirect(1024 * 3);
            boolean isDirect = buffer.isDirect();
            System.out.println("isDirect = " + isDirect);
        } finally {
            if (buffer != null) {
                ByteBuffer finalBuffer = buffer;
                AccessController.doPrivileged(new PrivilegedAction<Object>() {

                                                  @Override
                                                  public Object run() {
                                                      Method cleanerMethod = null;
                                                      try {
                                                          cleanerMethod = finalBuffer.getClass().getMethod("cleaner");
                                                          Object cleaner = cleanerMethod.invoke(finalBuffer); // IllegalAccessException
                                                          Method method = cleaner.getClass().getMethod("clean");
                                                          method.invoke(cleaner);
                                                          System.out.println(cleaner);
                                                      } catch (NoSuchMethodException e) {
                                                          e.printStackTrace();
                                                      } catch (IllegalAccessException e) {
                                                          e.printStackTrace();
                                                      } catch (InvocationTargetException e) {
                                                          e.printStackTrace();
                                                      }

                                                      return cleanerMethod;
                                                  }
                                              });
            }
        }

        System.in.read();
    }


}
