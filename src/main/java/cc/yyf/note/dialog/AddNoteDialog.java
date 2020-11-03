package cc.yyf.note.dialog;

import cc.yyf.note.pojo.DataCenter;
import cc.yyf.note.pojo.DataConvert;
import cc.yyf.note.pojo.NoteData;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.EditorTextField;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * 弹出窗口添加笔记
 */
public class AddNoteDialog extends DialogWrapper {

    // 标题信息
    private EditorTextField textFieldTitle;
    // 笔记信息
    private EditorTextField textFieldNote;
    // 笔记内容
    private NoteData noteData;

    public AddNoteDialog(NoteData noteData) {
        super(true);
        this.noteData = noteData;
        init();
        setTitle("添加笔记");
    }

    /**
     * 创建对话框样式
     * @return
     */
    @Override
    protected @Nullable JComponent createCenterPanel() {
        // 创建面板
        JPanel jPanel = new JPanel(new BorderLayout());
        // 创建输入框
        textFieldTitle = new EditorTextField("笔记的标题");
        textFieldNote= new EditorTextField("笔记的内容");
        // 设置框的大小
        textFieldNote.setPreferredSize(new Dimension(200, 100));
        // 加到面板上
        jPanel.add(textFieldTitle, BorderLayout.NORTH);
        jPanel.add(textFieldNote, BorderLayout.CENTER);

        return jPanel;
    }

    /**
     * 按钮
     */
    @Override
    protected JComponent createSouthPanel() {
        JPanel jPanel = new JPanel();
        JButton button = new JButton("添加笔记到列表");
        // 按钮点击后的事件
        button.addActionListener(e -> {
            // 获取文本
            String title = textFieldTitle.getText();
            String markDownNote = textFieldNote.getText();
            // 设置文本
            noteData.setNoteTitle(title);
            noteData.setNote(markDownNote);
            // 添加到数据中心
            DataCenter.NOTE_DATA_LIST.add(noteData);
            DataCenter.TABLE_MODEL.addRow(DataConvert.convert(noteData));
            AddNoteDialog.this.dispose();
        });
        jPanel.add(button);
        return jPanel;
    }
}
