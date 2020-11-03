package cc.yyf.note.window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class NoteToolWindow implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // 创建NoteListWindow对象
        NoteListWindow window = new NoteListWindow(project, toolWindow);
        // 获取内容工厂实例
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        // 获取用于toolWindow显示的内容
        Content content = contentFactory.createContent(window.getContentPanel(), "", false);
        // 给toolWindow设置内容
        toolWindow.getContentManager().addContent(content);
    }
}
