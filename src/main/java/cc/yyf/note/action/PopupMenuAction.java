package cc.yyf.note.action;

import cc.yyf.note.dialog.AddNoteDialog;
import cc.yyf.note.pojo.NoteData;
import cc.yyf.note.pojo.NoteDataBuilder;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;

/**
 * 鼠标右键后弹出菜单
 */
public class PopupMenuAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        // 获取被选中的文本
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        SelectionModel selectionModel = editor.getSelectionModel();
        String text = selectionModel.getSelectedText();
        // 获取被选中文本所在文件的文件名
        String fileName = e.getRequiredData(CommonDataKeys.PSI_FILE).getViewProvider().getVirtualFile().getName();
        NoteData noteData = NoteDataBuilder.build(text, fileName);
        // 弹出弹框
        AddNoteDialog addNoteDialog = new AddNoteDialog(noteData);
        addNoteDialog.show();
    }
}
