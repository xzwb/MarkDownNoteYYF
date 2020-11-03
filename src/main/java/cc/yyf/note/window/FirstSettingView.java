package cc.yyf.note.window;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class FirstSettingView implements Configurable {
    @Override
    public @Nls(capitalization = Nls.Capitalization.Title) String getDisplayName() {
        return "MarkDownYYF";
    }

    @Override
    public @Nullable JComponent createComponent() {
        return new FirstWindow().getFirstPanel();
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {
    }
}
