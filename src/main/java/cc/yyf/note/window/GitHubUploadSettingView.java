package cc.yyf.note.window;

import cc.yyf.note.util.DESUtil;
import cc.yyf.note.util.YYFPasswordUtil;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class GitHubUploadSettingView extends UploadSettingView {
    private OtherSettingWindow otherSettingWindow = new OtherSettingWindow();


    /**
     * 获取最外层控件
     * @return
     */
    @Override
    JComponent getViewComponent() {
        return otherSettingWindow.getCommentPanel();
    }

    /**
     * 在setting中展示的名字
     * @return
     */
    @Override
    public @Nls(capitalization = Nls.Capitalization.Title) String getDisplayName() {
        return "上传github仓库";
    }


    @Override
    public boolean isModified() {
        return true;
    }

    /**
     * 点击apply或者ok按钮以后调用
     * @throws ConfigurationException
     */
    @Override
    public void apply() throws ConfigurationException {
        // 获取到文本框中的内容
        String gitHubAddress = otherSettingWindow.getGithubRepository().getText();
        String gitHubToken = otherSettingWindow.getGithubToken().getText();
        String gitHubOwner = otherSettingWindow.getGitHubOwner().getText();
        // 加密
        String gitHubAddressInFile = DESUtil.encrypt(YYFPasswordUtil.YYF_KEY, gitHubAddress);
        String gitHubTokenInFile = DESUtil.encrypt(YYFPasswordUtil.YYF_KEY, gitHubToken);
        String gitHubOwnerInFile = DESUtil.encrypt(YYFPasswordUtil.YYF_KEY, gitHubOwner);
        // 保存进文件
        FileOutputStream githubAddressIn = null;
        FileOutputStream githubTokenIn = null;
        FileOutputStream githubOwnerIn = null;
        try {
            // gitHubAddress
//            String classPath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("/").getPath()+"gitHubAddress.txt");
//            classPath = URLDecoder.decode(classPath, StandardCharsets.UTF_8.name());
            String rootClassPath = this.getClass().getResource(File.separator + "template" + File.separator + "md.ftl").getPath();
            rootClassPath = rootClassPath.substring(0, rootClassPath.lastIndexOf(File.separator));
            rootClassPath = rootClassPath.substring(0, rootClassPath.lastIndexOf(File.separator));
            rootClassPath = rootClassPath.substring(0, rootClassPath.lastIndexOf(File.separator));
            rootClassPath = rootClassPath.substring(rootClassPath.indexOf(":") + 1);
            String classPath = rootClassPath + File.separator + "githubAddress.txt";
            githubAddressIn = new FileOutputStream(classPath);
            githubAddressIn.write(gitHubAddressInFile.getBytes(), 0, gitHubAddressInFile.length());
            githubAddressIn.flush();
            // gitHubToken
//            classPath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("/").getPath()+"gitHubToken.txt");
//            classPath = URLDecoder.decode(classPath, StandardCharsets.UTF_8.name());
//            githubTokenIn = new FileOutputStream(classPath);
//            githubTokenIn.write(gitHubTokenInFile.getBytes(), 0, gitHubTokenInFile.length());
//            githubTokenIn.flush();
//             gitHubOwner
//            classPath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("/").getPath()+"gitHubOwner.txt");
//            classPath = URLDecoder.decode(classPath, StandardCharsets.UTF_8.name());
//            githubOwnerIn = new FileOutputStream(classPath);
//            githubOwnerIn.write(gitHubOwnerInFile.getBytes(), 0, gitHubOwnerInFile.length());
//            githubOwnerIn.flush();
//            GitHubBuilder.build(gitHubAddress, gitHubToken, gitHubOwner);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (githubAddressIn != null) {
                    githubAddressIn.close();
                }
                if (githubOwnerIn != null) {
                    githubOwnerIn.close();
                }
                if (githubTokenIn != null) {
                    githubTokenIn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 一开始进入或者点击右上角reset后调用
     */
    @Override
    public void reset() {
        InputStream githubAddressIn = null;
        InputStream githubTokenIn = null;
        InputStream githubOwnerIn = null;
        try {
            // 文件如果没有就创建
//            File fileToken = new File(this.getClass().getResource(File.separator).getPath() + "gitHubToken.txt");
//            if (!fileToken.exists()) {
//                fileToken.createNewFile();
//            }
//            File fileAddress = new File(this.getClass().getResource(File.separator).getPath() + "gitHubAddress.txt");
            String rootClassPath = this.getClass().getResource(File.separator + "template" + File.separator + "md.ftl").getPath();
            rootClassPath = rootClassPath.substring(0, rootClassPath.lastIndexOf(File.separator));
            rootClassPath = rootClassPath.substring(0, rootClassPath.lastIndexOf(File.separator));
            rootClassPath = rootClassPath.substring(0, rootClassPath.lastIndexOf(File.separator));
            rootClassPath = rootClassPath.substring(rootClassPath.indexOf(":") + 1);
            String classPath = rootClassPath + File.separator + "githubAddress.txt";
//            System.out.println(classPath);
            File fileAddress = new File(classPath);
            if (!fileAddress.exists()) {
                fileAddress.createNewFile();
            }
//            File fileOwner = new File(this.getClass().getResource(File.separator).getPath() + "gitHubOwner.txt");
//            if (!fileOwner.exists()) {
//                fileOwner.createNewFile();
//            }
            // 获取文件的InPutStream
//            githubTokenIn = new FileInputStream(fileToken);
            githubAddressIn = new FileInputStream(fileAddress);
//            githubOwnerIn = new FileInputStream(fileOwner);
//            byte[] owner = new byte[1024];
            byte[] address = new byte[1024];
//            byte[] token = new byte[1024];
            int addressNum = githubAddressIn.read(address);
//            int tokenNum = githubTokenIn.read(token);
//            int ownerNum = githubOwnerIn.read(owner);
            String githubAddress = "";
            if (addressNum != -1) {
                githubAddress = new String(address, 0, addressNum);
                githubAddress = DESUtil.decrypt(YYFPasswordUtil.YYF_KEY, githubAddress);
            }
//            String githubToken = "";
//            if (tokenNum != -1) {
//                githubToken = new String(token, 0, tokenNum);
//                githubToken = DESUtil.decrypt(YYFPasswordUtil.YYF_KEY, githubToken);
//            }
//            String githubOwner = "";
//            if (ownerNum != -1) {
//                githubOwner = new String(owner, 0, ownerNum);
//                githubOwner = DESUtil.decrypt(YYFPasswordUtil.YYF_KEY, githubOwner);
//            }
            otherSettingWindow.getGithubRepository().setText(githubAddress);
//            otherSettingWindow.getGithubToken().setText(githubToken);
//            otherSettingWindow.getGitHubOwner().setText(githubOwner);
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

    }
}
