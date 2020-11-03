package cc.yyf.note.procesor;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用freeMark进行md文档生成
 */
public abstract class AbstractFreeMarkProcessor implements Processor {
    // 获取模板
    protected abstract Template getTemplate() throws IOException;

    // 获取Write对象
    protected Writer getWrite(SourceNoteData sourceNoteData) throws FileNotFoundException, UnsupportedEncodingException {
        String fileName = sourceNoteData.getFileName();
        File file = new File(fileName);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
        return bufferedWriter;
    }

    // 获取数据模型
    protected Object getModel(SourceNoteData sourceNoteData) {
        Map model = new HashMap();
        model.put("topic", sourceNoteData.getTopic());
        model.put("noteList", sourceNoteData.getNoteList());
        return model;
    }

    @Override
    public final void process(SourceNoteData sourceNoteData) throws IOException, TemplateException {
        Template template = getTemplate();
        Object model = getModel(sourceNoteData);
        Writer write = getWrite(sourceNoteData);
        template.process(model, write);
    }
}
