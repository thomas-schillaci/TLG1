package com.twolazyguys.util;

import com.twolazyguys.sprites.Terminal;

import java.io.File;

public class GFile {

    private File javaFile;

    public GFile(String s) {
        javaFile = new File(s);
    }

    public String getPath() {
        String path = javaFile.getPath();

        path = path.replaceFirst(Terminal.ROOT, "");

        System.out.println(path);
        if (path.startsWith("/home/")) {
            path = path.replaceFirst("/home/", "~");
            int index = 1;
            if (path.contains("/")) index = path.indexOf("/");
            path = path.substring(0, index);
        }

        return path.length() == 0 ? "/" : path;
    }

    public GFile[] listFiles() {
        File[] files = javaFile.listFiles();
        GFile[] res = new GFile[files.length];
        for (int i = 0; i < res.length; i++) res[i] = new GFile(files[i].getPath());
        return res;
    }

    public String getName() {
        return javaFile.getName();
    }

    public boolean isDirectory() {
        return !javaFile.getName().contains(".");
    }

    public GFile getParent() {
        return new GFile(javaFile.getParent());
    }

    public boolean isRoot() {
        return getPath().equals("/");
    }

}
