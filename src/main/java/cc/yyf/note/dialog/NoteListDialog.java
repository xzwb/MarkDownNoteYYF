package cc.yyf.note.dialog;

import cc.yyf.note.pojo.NoteList;
import cc.yyf.note.util.SetToArray;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.EditorTextField;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class NoteListDialog extends DialogWrapper {

    // 新的笔记的文件名
    private EditorTextField noteName;


    public NoteListDialog() {
        super(true);
        init();
        setTitle("选择笔记文件");
    }

    /**
     * 有关新建笔记的面板
     * @return
     */
    @Override
    protected @Nullable JComponent createCenterPanel() {
        // 创建面板
        JPanel jPanel = new JPanel();
        noteName = new EditorTextField("");
        noteName.setPreferredSize(new Dimension(100, 30));
        JLabel jLabel = new JLabel("添加到新笔记");
        jPanel.add(jLabel);
        jPanel.add(noteName);
        return jPanel;
    }

    /**
     * 有关选择框的面板
     * @return
     */
    @Override
    protected JComponent createNorthPanel() {
        JPanel jPanel = new JPanel();
        JLabel jLabel = new JLabel("添加到已有笔记中:");
        // 将set转化为String数组
        String[] noteList = SetToArray.convert(NoteList.noteNameList);
        if (noteList == null) {
            noteList = new String[]{""};
        }

        // 创建一个下拉列表框
        JComboBox<String> jComboBox = new JComboBox(noteList);
        // 设置默认选中的条目
        jComboBox.setSelectedIndex(0);
        jPanel.add(jLabel);
        jPanel.add(jComboBox);

        return jPanel;
    }

    /**
     * 按钮
     * @return
     */
    @Override
    protected JComponent createSouthPanel() {
        JPanel jPanel = new JPanel(new FlowLayout());
        JButton createButton = new JButton("创建");
        JButton addButton = new JButton("添加");
        // 创建按钮点击后的事件
        createButton.addActionListener(e -> {

        });
        // 添加按钮点击后的事件
        addButton.addActionListener(e -> {

        });
        jPanel.add(createButton);
        jPanel.add(addButton);
        return jPanel;
    }
}
