package cc.yyf.note.util;

import java.io.File;

public class UrlUtil {
    public static String getUrl() {
        String rootClassPath = UrlUtil.class.getResource(File.separator + "template" + File.separator + "md.ftl").getPath();
        rootClassPath = rootClassPath.substring(0, rootClassPath.lastIndexOf(File.separator));
        rootClassPath = rootClassPath.substring(0, rootClassPath.lastIndexOf(File.separator));
        rootClassPath = rootClassPath.substring(0, rootClassPath.lastIndexOf(File.separator));
        rootClassPath = rootClassPath.substring(rootClassPath.indexOf(":") + 1);

        return rootClassPath;
    }
}
