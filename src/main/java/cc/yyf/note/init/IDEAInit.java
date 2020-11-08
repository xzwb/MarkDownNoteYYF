package cc.yyf.note.init;


import cc.yyf.note.pojo.*;
import cc.yyf.note.util.DESUtil;
import cc.yyf.note.util.SaveNoteCenterJson;
import cc.yyf.note.util.UrlUtil;
import cc.yyf.note.util.YYFPasswordUtil;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.ide.ApplicationInitializedListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;


public class IDEAInit implements ApplicationInitializedListener {

    @Override
    public void componentsInitialized() {
        initGithubSetting();
        initNoteList();
        SaveNoteCenterJson.getNoteJson();
        NoteTopicNow.TopicNow = "";
//        NoteData noteData = NoteDataBuilder.build("abcde", "1.txt");
//        NoteData noteData1 = NoteDataBuilder.build("ccc", "2.txt");
//        List<NoteData> list = new ArrayList<>();
//        list.add(noteData);
//        list.add(noteData1);
//        NoteCenter.NoteMap.put("aa", list);
//        NoteList.noteNameList.add("aa");
//        NoteTopicNow.TopicNow = "aa";
    }

    /**
     * 将setting初始化
     */
    private void initGithubSetting() {
        InputStream githubAddressIn = null;
        InputStream githubTokenIn = null;
        InputStream githubOwnerIn = null;
        try {
//            String rootClassPath = this.getClass().getResource(File.separator + "template" + File.separator + "md.ftl").getPath();
//            rootClassPath = rootClassPath.substring(0, rootClassPath.lastIndexOf(File.separator));
//            rootClassPath = rootClassPath.substring(0, rootClassPath.lastIndexOf(File.separator));
//            rootClassPath = rootClassPath.substring(0, rootClassPath.lastIndexOf(File.separator));
//            rootClassPath = rootClassPath.substring(rootClassPath.indexOf(":") + 1);
            String rootClassPath = UrlUtil.getUrl();
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
    }

    /**
     * 初始化NoteList
     */
    private void initNoteList() {
        FileInputStream noteListIn = null;
        try {
            // 获取文件路径
            String noteListPath = UrlUtil.getUrl() + File.separator + "noteList.txt";
            File noteListFile = new File(noteListPath);
            if (!noteListFile.exists()) {
                noteListFile.createNewFile();
            }
            noteListIn = new FileInputStream(noteListFile);
            byte[] noteListByte = new byte[1024];
            StringBuilder noteList = new StringBuilder();
            int length = 0;
            while ((length = noteListIn.read(noteListByte)) != -1) {
                noteList.append(new String(noteListByte, 0, length));
            }
            ObjectMapper objectMapper = new ObjectMapper();
            if (!noteList.toString().equals("")) {
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(HashSet.class, String.class);
                NoteList.noteNameList = objectMapper.readValue(noteList.toString(), javaType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (noteListIn != null) {
                try {
                    noteListIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
