package cc.yyf.note.dialog;

import cc.yyf.note.pojo.*;
import cc.yyf.note.util.NotificationUtil;
import cc.yyf.note.util.SetToArray;
import cc.yyf.note.window.NoteListWindow;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.EditorTextField;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NoteListDialog extends DialogWrapper {

    // 新的笔记的文件名
    private EditorTextField noteName;
    // 下拉框
    private JComboBox<String> jComboBox;
    // 笔记内容
    private NoteData noteData;


    public NoteListDialog(NoteData noteData) {
        super(true);
        this.noteData = noteData;
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
        jComboBox = new JComboBox(noteList);
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
            // 获取文本框中的数据
            String noteTopic = noteName.getText();
            if ("".equals(noteTopic) || noteTopic == null) {
                NotificationUtil.notification("创建新文档", "文档创建失败");
                return;
            }
            if (NoteList.noteNameList.add(noteTopic)) { // 添加set成功
               // 添加到map
                List<NoteData> list = new ArrayList<>();
                NoteCenter.NoteMap.put(noteTopic, list);
                NoteTopicNow.TopicNow = noteTopic;
                MyComponent.noteToolWindowJComboBox.addItem(noteTopic);
                NoteListDialog.this.dispose();
                // 弹出弹框
                AddNoteDialog addNoteDialog = new AddNoteDialog(noteData);
                addNoteDialog.show();
            } else { // 添加set失败
                NotificationUtil.notification("创建新文档", "文档已经存在");
                NoteListDialog.this.dispose();
            }
        });
        // 添加按钮点击后的事件
        addButton.addActionListener(e -> {
            // 从下拉框中获取当前文本
            String noteTopic = (String) jComboBox.getSelectedItem();
            if ("".equals(noteTopic) || noteTopic == null) {
                NotificationUtil.notification("添加文档", "文档不存在");
                NoteListDialog.this.dispose();
            }
            // 将当前的NoteTopicNow换成这个
            NoteTopicNow.TopicNow = noteTopic;
            NoteListDialog.this.dispose();
            // 弹出弹框
            AddNoteDialog addNoteDialog = new AddNoteDialog(noteData);
            addNoteDialog.show();
        });
        jPanel.add(createButton);
        jPanel.add(addButton);
        return jPanel;
    }
}
