package cc.yyf.note.init;


import cc.yyf.note.pojo.GitHubBuilder;
import cc.yyf.note.util.DESUtil;
import cc.yyf.note.util.YYFPasswordUtil;
import com.intellij.ide.ApplicationInitializedListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


public class IDEAInit implements ApplicationInitializedListener {

    @Override
    public void componentsInitialized() {
        InputStream githubAddressIn = null;
        InputStream githubTokenIn = null;
        InputStream githubOwnerIn = null;
        try {
            String rootClassPath = this.getClass().getResource(File.separator + "template" + File.separator + "md.ftl").getPath();
            rootClassPath = rootClassPath.substring(0, rootClassPath.lastIndexOf(File.separator));
            rootClassPath = rootClassPath.substring(0, rootClassPath.lastIndexOf(File.separator));
            rootClassPath = rootClassPath.substring(0, rootClassPath.lastIndexOf(File.separator));
            rootClassPath = rootClassPath.substring(rootClassPath.indexOf(":") + 1);
//             文件如果没有就创建
            String classPath = rootClassPath + File.separator + "githubToken.txt";
//            File fileToken = new File(this.getClass().getResource(File.separator).getPath() + "gitHubToken.txt");
            File fileToken = new File(classPath);
            if (!fileToken.exists()) {
                fileToken.createNewFile();
            }
//            File fileAddress = new File(this.getClass().getResource(File.separator).getPath() + "gitHubAddress.txt");
            classPath = rootClassPath + File.separator + "githubAddress.txt";
            File fileAddress = new File(classPath);
            if (!fileAddress.exists()) {
                fileAddress.createNewFile();
            }
//            File fileOwner = new File(this.getClass().getResource(File.separator).getPath() + "gitHubOwner.txt");
            classPath = rootClassPath + File.separator + "githubOwner.txt";
            File fileOwner = new File(classPath);
            if (!fileOwner.exists()) {
                fileOwner.createNewFile();
            }
//             获取文件的InPutStream
            githubTokenIn = new FileInputStream(fileToken);
            githubAddressIn = new FileInputStream(fileAddress);
            githubOwnerIn = new FileInputStream(fileOwner);
            byte[] owner = new byte[1024];
            byte[] address = new byte[1024];
            byte[] token = new byte[1024];
            int addressNum = githubAddressIn.read(address);
            int tokenNum = githubTokenIn.read(token);
            int ownerNum = githubOwnerIn.read(owner);
            String githubAddress = "";
            if (addressNum != -1) {
                githubAddress = new String(address, 0, addressNum);
                githubAddress = DESUtil.decrypt(YYFPasswordUtil.YYF_KEY, githubAddress);
            }
            String githubToken = "";
            if (tokenNum != -1) {
                githubToken = new String(token, 0, tokenNum);
                githubToken = DESUtil.decrypt(YYFPasswordUtil.YYF_KEY, githubToken);
            }
            String githubOwner = "";
            if (ownerNum != -1) {
                githubOwner = new String(owner, 0, ownerNum);
                githubOwner = DESUtil.decrypt(YYFPasswordUtil.YYF_KEY, githubOwner);
            }
            GitHubBuilder.build(githubAddress, githubToken, githubOwner);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (githubAddressIn != null) {
                    githubAddressIn.close();
                }
                if (githubTokenIn != null) {
                    githubTokenIn.close();
                }
                if (githubOwnerIn != null) {
                    githubOwnerIn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//
    }
}
