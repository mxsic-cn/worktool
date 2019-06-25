package cn.siqishangshu.json;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * Created by lxu on 10/21/14.
 */
public class AIConfig {
    private static final String defaultConfigFileName = AIConfig.class.getPackage().getName().replace('.', '/').concat("/config/AI.config");
    private static final String defaultConfigDirName = AIConfig.class.getPackage().getName().replace('.', '/').concat("/");
    private static String userConfigFileName = "/preserve/AI_Config/AI.config";


    private static List<String> readLocalFileList(String fileName) {
        List<String> result = new ArrayList<>();
        try {
            InputStream stream = ClassLoader.getSystemResourceAsStream(fileName);
            if (stream == null) {
                if (new File(fileName).exists()) {
                    stream = new FileInputStream(fileName);
                } else {
                    return result;
                }
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            try {
                String line = br.readLine();
                while (line != null && !line.isEmpty()) {
                    result.add(line);
                    line = br.readLine();
                }

            } finally {
                br.close();
                stream.close();
            }

        } catch (Exception e) {
//            System.out.println("File not found: " + fileName);;
        }
        return result;
    }

    public static List<String> getConfig() {
        List<String> result = readLocalFileList(userConfigFileName);
        if (!result.isEmpty()) {
            return result;
        } else {
//            System.out.println("AI can not find user defined configure file");
        }
        result = readLocalFileList(defaultConfigFileName);
        if (!result.isEmpty()) {
            return result;
        } else {
//            System.out.println("AI can not find default configure file");
        }
        return result;
    }

    public static Collection<String> getResources(
            final Pattern pattern) {
        final ArrayList<String> retval = new ArrayList<>();
        final String classPath = System.getProperty("java.class.path", ".");
        final String[] classPathElements = classPath.split(":");
        for (final String element : classPathElements) {
            retval.addAll(getResources(element, pattern));
        }
        return retval;
    }

    private static Collection<String> getResources(
            final String element,
            final Pattern pattern) {
        final ArrayList<String> retval = new ArrayList<>();
        final File file = new File(element);
        if (file.isDirectory()) {
            retval.addAll(getResourcesFromDirectory(file, pattern));
        } else {
            retval.addAll(getResourcesFromJarFile(file, pattern));
        }
        return retval;
    }

    private static Collection<String> getResourcesFromJarFile(
            final File file,
            final Pattern pattern) {
        final ArrayList<String> retval = new ArrayList<>();
        ZipFile zf;
        try {
            zf = new ZipFile(file);
        } catch (final ZipException e) {
            throw new Error(e);
        } catch (final IOException e) {
            throw new Error(e);
        }
        final Enumeration e = zf.entries();
        while (e.hasMoreElements()) {
            final ZipEntry ze = (ZipEntry) e.nextElement();
            final String fileName = ze.getName();
            final boolean accept = pattern.matcher(fileName).matches();
            if (accept) {
                retval.add(fileName);
            }
        }
        try {
            zf.close();
        } catch (final IOException e1) {
            throw new Error(e1);
        }
        return retval;
    }

    private static Collection<String> getResourcesFromDirectory(
            final File directory,
            final Pattern pattern) {
        final ArrayList<String> retval = new ArrayList<>();
        final File[] fileList = directory.listFiles();
        for (final File file : fileList) {
            if (file.isDirectory()) {
                retval.addAll(getResourcesFromDirectory(file, pattern));
            } else {
                try {
                    final String fileName = file.getCanonicalPath();
                    final boolean accept = pattern.matcher(fileName).matches();
                    if (accept) {
                        retval.add(fileName);
                    }
                } catch (final IOException e) {
                    throw new Error(e);
                }
            }
        }
        return retval;
    }

    public static List<String> getProtocolFileName() {
        List<String> config = new ArrayList<>();
        try {
            config.addAll(getConfig());
        } catch (Exception e) {
//            System.out.println("AI could not read from configuration file");
        }
        String dir = "default";
        for (String oneLine : config) {
            String[] kv = oneLine.split(":");
            if (kv[0].equals("protocolDir") && kv.length == 2) {
                dir = kv[1];
            }
        }

        Pattern pattern;
        pattern = Pattern.compile(".*\\.protocolImpl");
        List<String> result = new ArrayList<>();
        if (dir.isEmpty() || dir.equals("default")) {
            try {
                Enumeration<URL> packageUrls = ClassLoader.getSystemResources(defaultConfigDirName);
                while (packageUrls.hasMoreElements()) {
                    URL packageUrl = packageUrls.nextElement();
                    String fileName = URLDecoder.decode(packageUrl.getFile(), "UTF-8");
                    if (packageUrl.getProtocol().equals("jar")) {
                        String jarFileName = fileName.substring(5, fileName.indexOf("!"));
                        Collection<String> list = AIConfig.getResources(jarFileName, pattern);
                        if (list.size() > 0) {
                            result.addAll(list);
                            return result;
                        }
                    } else {
                        Collection<String> list = AIConfig.getResources(fileName, pattern);
                        if (list.size() > 0) {
                            result.addAll(list);
                            return result;
                        }
                    }
                }
            } catch (Exception e) {
            }
        } else {
            File directory = new File(dir);
            Collection<String> list = AIConfig.getResourcesFromDirectory(directory, pattern);
            result.addAll(list);

        }
        return result;
    }


}
