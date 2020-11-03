package cc.yyf.note.pojo;

/**
 * 使用建造者模式创建数据对象
 */
public class NoteDataBuilder {
    /**
     * 构造NoteData对象
     * @return
     */
    public static NoteData build(String selectedText, String fileName) {
        NoteData noteData = new NoteData();
        noteData.setSelectedText(selectedText);
        noteData.setFileName(fileName);
        noteData.setFileType(getFileType(fileName));
        return noteData;
    }

    /**
     * 根据文件名获取文件类型后缀
     * @param fileName
     * @return
     */
    private static String getFileType(String fileName) {
        if (fileName == null || "".equals(fileName)) {
            return "";
        }
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        return fileType;
    }
}
