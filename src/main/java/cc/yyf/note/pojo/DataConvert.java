package cc.yyf.note.pojo;

public class DataConvert {
    public static String[] convert(NoteData data) {
        String[] raw = new String[4];
        raw[0] = data.getNoteTitle();
        raw[1] = data.getNote();
        raw[2] = data.getFileName();
        raw[3] = data.getSelectedText();
        return raw;
    }
}
