package cc.yyf.note.procesor;

import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * 使用模板方法处理
 */
public interface Processor {
    void process(SourceNoteData sourceNoteData) throws IOException, TemplateException;
}
