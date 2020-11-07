package cc.yyf.note.util;

import cc.yyf.note.pojo.DataCenter;
import cc.yyf.note.pojo.NoteCenter;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * 将NoteCenter以Json保存起来
 */
public class SaveNoteCenterJson {
    /**
     * 保存
     */
    public static void saveNoteCenterToJson() {
        // 转化为json
        ObjectMapper objectMapper = new ObjectMapper();
        FileOutputStream noteFileOut = null;
        try {
            String note = objectMapper.writeValueAsString(NoteCenter.NoteMap);
            String path = UrlUtil.getUrl() + File.separator + "note.txt";
            File noteFile = new File(path);
            if (!noteFile.exists()) {
                noteFile.createNewFile();
            }
            noteFileOut = new FileOutputStream(noteFile);
            noteFileOut.write(note.getBytes(), 0, note.length());
            noteFileOut.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (noteFileOut != null) {
                    noteFileOut.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取
     */
    public static void getNoteJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        FileInputStream inputStream = null;
        try {
            // 获取路径
            String path = UrlUtil.getUrl() + File.separator + "note.txt";
            StringBuilder noteJson = new StringBuilder();
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            inputStream = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(bytes)) != -1) {
                noteJson.append(new String(bytes, 0, length));
            }
            if (!"".equals(noteJson.toString())) {
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(HashMap.class, String.class, List.class);
                NoteCenter.NoteMap = objectMapper.readValue(noteJson.toString(), javaType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
              if (inputStream != null) {
                  inputStream.close();
              }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
