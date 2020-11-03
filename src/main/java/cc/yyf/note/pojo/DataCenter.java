package cc.yyf.note.pojo;

import javax.swing.table.DefaultTableModel;
import java.util.LinkedList;
import java.util.List;

/**
 * 要展示的数据中心
 */
public class DataCenter {
    // 笔记的数据
    public static List<NoteData> NOTE_DATA_LIST = new LinkedList<>();

    // 构建表头
    public static String[] HEAD = {"标题", "备注", "文件名", "代码段"};

    // 数据模型
    public static DefaultTableModel TABLE_MODEL = new DefaultTableModel(null, HEAD);

    /**
     * 清空
     */
    public static void reset() {
        NOTE_DATA_LIST.clear();
        TABLE_MODEL.setDataVector(null, HEAD);
    }
}
