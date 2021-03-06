package cc.yyf.note.window;

import cc.yyf.note.pojo.*;
import cc.yyf.note.procesor.DefaultSourceNoteData;
import cc.yyf.note.procesor.FreeMarkProcessor;
import cc.yyf.note.procesor.Processor;
import cc.yyf.note.util.NotificationUtil;
import cc.yyf.note.util.SetToArray;
import cc.yyf.note.util.UpLoadGitHubUtil;
import cc.yyf.note.util.UrlUtil;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageDialogBuilder;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import freemarker.template.TemplateException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 工具视窗
 */
public class NoteListWindow {
    private JPanel contentPanel;

    public JPanel getContentPanel() {
        return contentPanel;
    }

    // 生成按钮
    private JButton makeButton;
    // 关闭按钮
    private JButton closeButton;
    // 清空按钮
    private JButton cancelButton;
    // 表格
    private JTable table;
    // 文档标题
    private JComboBox textFieldTopic;

    public JComboBox getTextFieldTopic() {
        return textFieldTopic;
    }

    private JButton uploadButton;
    private JButton deleteButton;

    /**
     * 初始化表格
     */
    private void init() {
        initTable();
        initJComboBox();
        initTablePojo();
    }

    /**
     * 设置表格
     */
    private void initTable() {
        // 设置表格
        table.setModel(DataCenter.TABLE_MODEL);
        table.setEnabled(true);
    }

    /**
     * 设置下拉框
     */
    private void initJComboBox() {
        String[] noteList =  SetToArray.convert(NoteList.noteNameList);
        if (noteList == null) {
            noteList = new String[]{""};
            textFieldTopic.addItem("");
        } else {
            for (int i = 0; i < noteList.length; i++) {
                textFieldTopic.addItem(noteList[i]);
            }
        }
        textFieldTopic.setSelectedIndex(0);
        NoteTopicNow.TopicNow = noteList[0];
    }

    /**
     * 设置table中的初始数据
     */
    private void initTablePojo() {
        // 判断当前的列表如果为空的话
        if ("".equals(NoteTopicNow.TopicNow)) {
            return;
        }
        // 获取当前列表的数据
        List<NoteData> list = NoteCenter.NoteMap.get(NoteTopicNow.TopicNow);
        // 判断选中的列表中有没有数据
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                NoteData noteData = list.get(i);
                DataCenter.NOTE_DATA_LIST = list;
                DataCenter.TABLE_MODEL.addRow(DataConvert.convert(noteData));
            }
        }
    }


    public NoteListWindow(Project project, ToolWindow toolWindow) {
        init();
        /**
         * 选择框
         */
        textFieldTopic.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                // 只处理选中状态
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    // 获取选中文本
                    String selectText = (String) textFieldTopic.getSelectedItem();
                    // 调用清除操作
                    DataCenter.reset();
                    // 获取当前列表中的数据
                    List<NoteData> list = NoteCenter.NoteMap.get(selectText);
                    if (list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            NoteData noteData = list.get(i);
                            DataCenter.NOTE_DATA_LIST = list;
                            DataCenter.TABLE_MODEL.addRow(DataConvert.convert(noteData));
                        }
                    }
                }
            }
        });
        /**
         * 创建文件按钮
         */
        makeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String topic = (String) textFieldTopic.getSelectedItem();
                if (topic == null || "".equals(topic)) {
                    MessageDialogBuilder.yesNo("操作结果", "文档标题没有输入");
                    return;
                }
                // 要保存的文件名称
                String fileName = topic + ".md";
                VirtualFile virtualFile = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFileDescriptor(), project, project.getBaseDir());
                if (virtualFile != null) {
                    // 获取文件保存的全路径
                    String path = virtualFile.getPath();
                    String fileFullPath = path + File.separator + fileName;
                    Processor processor = new FreeMarkProcessor();
                    try {
                        processor.process(new DefaultSourceNoteData(fileFullPath, topic, DataCenter.NOTE_DATA_LIST));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TemplateException e) {
                        e.printStackTrace();
                    }
                    // 发送通知
                    NotificationUtil.notification("保存成功", "文档保存成功");
                }
            }
        });
        /**
         * 清空文档按钮
         */
        cancelButton.addActionListener(actionEvent -> DataCenter.reset());
        /**
         * 关闭按钮
         */
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                toolWindow.hide(null);
            }
        });
        /**
         * 上传按钮
         */
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String topic = (String) textFieldTopic.getSelectedItem();
                if (topic == null || "".equals(topic)) {
                    MessageDialogBuilder.yesNo("操作结果", "文档标题没有输入");
                    return;
                }
                // 要保存的文件名称
                String fileName = topic + ".md";
                String rootClassPath = UrlUtil.getUrl();
                String path = rootClassPath + File.separator + fileName;
                File file = new File(path);
                // 文件已经存在
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                Processor processor = new FreeMarkProcessor();
                try {
                    processor.process(new DefaultSourceNoteData(path, topic, DataCenter.NOTE_DATA_LIST));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
                file = new File(path);
                if (UpLoadGitHubUtil.upload(GitHubBuilder.getInstance(), file, fileName)) {
                    // 发送通知
                    NotificationUtil.notification("保存文档", "文档上传成功");
                } else {
                    // 发送通知
                    NotificationUtil.notification("保存文档", "文档上传失败");
                }
                file.delete();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // 获取当前列表
                String selectText = (String) textFieldTopic.getSelectedItem();
                // 从NoteList中删除
                NoteList.noteNameList.remove(selectText);
                // 从NoteCenter中删除
                NoteCenter.NoteMap.remove(selectText);
                // 判断还有没有item
                textFieldTopic.removeItem(selectText);
                int count = textFieldTopic.getItemCount();
                DataCenter.reset();
                if (count == 0) {
                    textFieldTopic.addItem("");
                    textFieldTopic.setSelectedIndex(0);
                } else {
                    textFieldTopic.setSelectedIndex(0);
                    selectText = (String) textFieldTopic.getSelectedItem();
                    List<NoteData> list = new ArrayList<>();
                    list = NoteCenter.NoteMap.get(selectText);
                    for (int i = 0; i < list.size(); i++) {
                        NoteData noteData = list.get(i);
                        DataCenter.NOTE_DATA_LIST = list;
                        DataCenter.TABLE_MODEL.addRow(DataConvert.convert(noteData));
                    }
                }
            }
        });
    }
}
