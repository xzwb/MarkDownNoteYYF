package cc.yyf.note.util;

import cc.yyf.note.pojo.GitHub;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 * 将笔记上传到github
 */
public class UpLoadGitHubUtil {
    public static boolean upload(GitHub gitHub, File file, String fileName) {
        if (gitHub.getGitHubToken() == null || "".equals(gitHub.getGitHubToken())) {
            return false;
        }
        if (gitHub.getGitHubAddress() == null || "".equals(gitHub.getGitHubAddress())) {
            return false;
        }
        if (gitHub.getGitHubOwner() == null || "".equals(gitHub.getGitHubOwner())) {
            return false;
        }
        BufferedReader in = null;
        HttpURLConnection conn = null;
        String url = "https://api.github.com/repos/" + gitHub.getGitHubOwner() + "/" + gitHub.getGitHubAddress() + "/contents/" + fileName;
        try {
            URL realUrl = new URL(url);
            conn = (HttpURLConnection) realUrl.openConnection();
            conn.setConnectTimeout(120000);
            conn.setReadTimeout(120000);
            // 设置
            conn.setDoOutput(true); // 需要输出
            conn.setDoInput(true); // 需要输入
            conn.setUseCaches(false); // 不允许缓存
            conn.setRequestMethod("PUT"); // 设置PUT方式连接

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "token " + gitHub.getGitHubToken());
            conn.setRequestProperty("User-Agent", "Github File Uploader App");
            conn.connect();
            // 传输数据
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            // 传输json头部
            dos.writeBytes("{\"message\":\".\",\"content\":\"");
            // 传输文件
            byte[] buffer = new byte[1024 * 1002]; // 3的倍数
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            long size = raf.read(buffer);
            while (size > -1) {
                if (size == buffer.length) {
                    dos.write(Base64.getEncoder().encode(buffer));
                } else {
                    byte tmp[] = new byte[(int) size];
                    System.arraycopy(buffer, 0, tmp, 0, (int) size);
                    dos.write(Base64.getEncoder().encode(tmp));
                }
                size = raf.read(buffer);
            }
            raf.close();
            // 传输json尾部
            dos.writeBytes("\"}");
            dos.flush();
            dos.close();

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
