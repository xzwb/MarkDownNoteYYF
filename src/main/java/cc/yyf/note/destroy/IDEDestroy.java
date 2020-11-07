package cc.yyf.note.destroy;

import cc.yyf.note.pojo.NoteList;
import cc.yyf.note.util.SaveNoteCenterJson;
import cc.yyf.note.util.UrlUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectCloseHandler;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class IDEDestroy implements ProjectCloseHandler {

    @Override
    public boolean canClose(@NotNull Project project) {
        saveNoteList();
        SaveNoteCenterJson.saveNoteCenterToJson();
        return true;
    }

    /**
     * 保存noteList
     */
    private void saveNoteList() {
        FileOutputStream noteListOut = null;
        try {
            // 把笔记列表转换成json
            ObjectMapper noteListString = new ObjectMapper();
            String noteListJson = noteListString.writeValueAsString(NoteList.noteNameList);
            String noteListPath = UrlUtil.getUrl() + File.separator + "noteList.txt";
            File noteListFile = new File(noteListPath);
            if (!noteListFile.exists()) {
                noteListFile.createNewFile();
            }
            noteListOut = new FileOutputStream(noteListFile);
            noteListOut.write(noteListJson.getBytes(), 0, noteListJson.length());
            noteListOut.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (noteListOut != null) {
                    noteListOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
