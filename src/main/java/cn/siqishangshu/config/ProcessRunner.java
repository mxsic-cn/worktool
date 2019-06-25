package cn.siqishangshu.config;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Copyright 2014 Istuary Innovation Lab Inc.
 * All rights reserved.
 * Created by wzhong on 2/13/14.
 */
public class ProcessRunner {
    private Process p;
    private int timeout;
    private String output;

    private String error;

    public ProcessRunner(Process p, int timeout) {
        this.p = p;
        this.timeout = timeout;
    }

    public int run() {
        final StringBuilder out = new StringBuilder();
        final InputStream outStream = p.getInputStream();
        final StringBuilder error = new StringBuilder();
        final InputStream errorStream = p.getErrorStream();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Reader r = new InputStreamReader(outStream);
                try {
                    int ch = r.read();
                    while (ch != -1) {
                        out.append((char) ch);
                        ch = r.read();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Reader r = new InputStreamReader(errorStream);
                try {
                    int ch = r.read();
                    while (ch != -1) {
                        error.append((char) ch);
                        ch = r.read();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();

        int count = 0;
        int exitCode = -1;
        while (count < 2 * timeout) {
            try {
                exitCode = p.exitValue();
                break;
            } catch (IllegalThreadStateException e) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                }
                count++;
            }
        }

        try {
            t1.join();
            t2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.output = out.toString();
        this.error = error.toString();
        if (exitCode == -1) {
            System.out.println("output for process : {} "+ this.output);
            System.out.println("error for process: {}"+this.error);
            System.out.println("destroying process");
            try {
                p.destroy();
            } catch (Exception e) {
                System.out.println("error destroying process"+ e.getMessage());
                System.out.println("error destroying process"+ e.getMessage());
            }
        }
        return exitCode;
    }

    public String getOutput() {
        return output;
    }

    public String getError() {
        return error;
    }
}
