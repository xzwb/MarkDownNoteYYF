package cc.yyf.note.dialog;

import cc.yyf.note.pojo.NoteData;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class NoteListDialog extends DialogWrapper {

    public NoteListDialog() {
        super(true);
        init();
        setTitle("选择笔记文件");
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return null;
    }
}
