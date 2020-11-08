package cc.yyf.note.action;

import cc.yyf.note.dialog.AddNoteDialog;
import cc.yyf.note.dialog.NoteListDialog;
import cc.yyf.note.pojo.NoteCenter;
import cc.yyf.note.pojo.NoteData;
import cc.yyf.note.pojo.NoteDataBuilder;
import cc.yyf.note.util.SaveNoteCenterJson;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;

import java.util.HashMap;

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
        NoteListDialog dialog = new NoteListDialog(noteData);
        dialog.show();
    }
}
