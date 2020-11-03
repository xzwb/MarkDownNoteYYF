package cc.yyf.note.procesor;

import com.intellij.ide.fileTemplates.impl.UrlUtil;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;

public class FreeMarkProcessor extends AbstractFreeMarkProcessor {
    @Override
    protected Template getTemplate() throws IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_27);
        // 获取模板
        String templateContent = UrlUtil.loadText(FreeMarkProcessor.class.getResource("/template/md.ftl"));

        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("MDTemplate", templateContent);
        configuration.setTemplateLoader(stringTemplateLoader);
        return configuration.getTemplate("MDTemplate");
    }
}
