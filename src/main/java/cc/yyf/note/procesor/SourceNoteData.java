package cc.yyf.note.procesor;

import cc.yyf.note.pojo.NoteData;

import java.util.List;

public interface SourceNoteData {
    // 文件的全路径名称
    String getFileName();
    // 笔记的内容
    List<NoteData> getNoteList();
    // title
    String getTopic();
}
