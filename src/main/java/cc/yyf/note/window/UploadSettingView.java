package cc.yyf.note.window;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;


/**
 * 在setting中添加一个面板
 */
public abstract class UploadSettingView implements Configurable {
    @Override
    public @Nullable JComponent createComponent() {
        return getViewComponent();
    }

    abstract JComponent getViewComponent();
}
