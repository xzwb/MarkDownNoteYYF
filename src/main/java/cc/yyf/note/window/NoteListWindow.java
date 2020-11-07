package cc.yyf.note.window;

import cc.yyf.note.pojo.*;
import cc.yyf.note.procesor.DefaultSourceNoteData;
import cc.yyf.note.procesor.FreeMarkProcessor;
import cc.yyf.note.procesor.Processor;
import cc.yyf.note.util.SetToArray;
import cc.yyf.note.util.UpLoadGitHubUtil;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageDialogBuilder;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import freemarker.template.TemplateException;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
    private JButton uploadButton;

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
        makeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
//                String topic = textFieldTopic.getText();
                String topic = "";
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
                    NotificationGroup firstPluginId = new NotificationGroup("保存文档", NotificationDisplayType.BALLOON, true);
                    Notification notification = firstPluginId.createNotification("文档保存成功", MessageType.INFO);
                    Notifications.Bus.notify(notification);
                }
            }
        });
        cancelButton.addActionListener(actionEvent -> DataCenter.reset());
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                toolWindow.hide(null);
            }
        });
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
//                String topic = textFieldTopic.getText();
                String topic = "";
                if (topic == null || "".equals(topic)) {
                    MessageDialogBuilder.yesNo("操作结果", "文档标题没有输入");
                    return;
                }
                // 要保存的文件名称
                String fileName = topic + ".md";
//                String path = this.getClass().getResource(File.separator).getPath() + fileName;
                String rootClassPath = this.getClass().getResource(File.separator + "template" + File.separator + "md.ftl").getPath();
                rootClassPath = rootClassPath.substring(0, rootClassPath.lastIndexOf(File.separator));
                rootClassPath = rootClassPath.substring(0, rootClassPath.lastIndexOf(File.separator));
                rootClassPath = rootClassPath.substring(0, rootClassPath.lastIndexOf(File.separator));
                rootClassPath = rootClassPath.substring(rootClassPath.indexOf(":") + 1);
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
                    NotificationGroup firstPluginId = new NotificationGroup("保存文档", NotificationDisplayType.BALLOON, true);
                    Notification notification = firstPluginId.createNotification("文档上传成功", MessageType.INFO);
                    Notifications.Bus.notify(notification);
                } else {
                    // 发送通知
                    NotificationGroup firstPluginId = new NotificationGroup("保存文档", NotificationDisplayType.BALLOON, true);
                    Notification notification = firstPluginId.createNotification("文档上传失败", MessageType.INFO);
                    Notifications.Bus.notify(notification);
                }
                file.delete();
            }
        });
    }
}
