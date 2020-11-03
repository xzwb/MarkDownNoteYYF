package cc.yyf.note.pojo;

/**
 * 需要保存的信息
 */
public class NoteData {
    // 选中的文本
    private String selectedText;
    // 文本所在的文件名
    private String fileName;
    // 文本所在文件的类型
    private String fileType;
    // 自己添加的笔记
    private String note;
    // 笔记的标题
    private String noteTitle;

    public String getSelectedText() {
        return selectedText;
    }

    public void setSelectedText(String selectedText) {
        this.selectedText = selectedText;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }
}
