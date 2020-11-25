# MarkDownNoteYYF
idea插件2020版本

---

# 项目介绍
本项目名为`MarkDownNoteYYF`,因为`Java`学习经常要翻看源码,但是源码是只读文件无法添加注释,所以编写一款插件用来给源码做笔记,并且可以保存为`MarkDown`格式文件,同时也支持上传到`GitHub`仓库保存.目前已经发布至JetBrains插件库中
https://plugins.jetbrains.com/plugin/15393-markdownnoteyyf

---

# 插件使用
1. 选中文本,右键点击`MarkDownNote`

2. 可以选择添加到已有笔记中,也可以选择创建一个新的笔记列表

![](https://github.com/xzwb/MarkDownNoteYYF/blob/main/img/8.png)

3. 编写笔记,**笔记标题:当前笔记的标题;笔记内容:对选中代码段的解释**

![](https://github.com/xzwb/MarkDownNoteYYF/blob/main/img/9.png)

4. 点击添加到笔记列表以后,笔记就会添加到`noteWindow`中

![](https://github.com/xzwb/MarkDownNoteYYF/blob/main/img/10.png)

5. 可以再添加一条笔记到当前笔记列表

![](https://github.com/xzwb/MarkDownNoteYYF/blob/main/img/11.png)

6. 创建一个新的笔记列表

![](https://github.com/xzwb/MarkDownNoteYYF/blob/main/img/12.png)
![](https://github.com/xzwb/MarkDownNoteYYF/blob/main/img/13.png)[](https://github.com/xzwb/MarkDownNoteYYF/blob/main/img/14.png)

7. 点击保存按钮以后选择保存路径可以生成当前文档标题的`MarkDown`格式文件

![](https://github.com/xzwb/MarkDownNoteYYF/blob/main/img/15.png)

8. 配置`setting`中的内容后可以双击上传,即可上传到指定`github`仓库
![](https://github.com/xzwb/MarkDownNoteYYF/blob/main/img/16.png)[](https://github.com/xzwb/MarkDownNoteYYF/blob/main/img/17.png)文档上传成功会显示[](https://github.com/xzwb/MarkDownNoteYYF/blob/main/img/18.png)
9. 删除笔记按钮点击后会删除当前笔记列表
10. 清空按钮点击后会清空当前的笔记列表,但不会删除
11. 关闭按钮点击后会关闭`noteWindow`窗口  

**Ps:笔记在`idea`关闭后再次打开也会存在**

# 如何申请GitHub Token

![](https://github.com/xzwb/MarkDownNoteYYF/blob/main/img/1.png)

点击`Setting`

![](https://github.com/xzwb/MarkDownNoteYYF/blob/main/img/2.png)

点击`Developer Settings`

![](https://github.com/xzwb/MarkDownNoteYYF/blob/main/img/3.png)
![](https://github.com/xzwb/MarkDownNoteYYF/blob/main/img/5.png)

选中所有以后,点击确定

![](https://github.com/xzwb/MarkDownNoteYYF/blob/main/img/6.png)

保存好`Token`因为不会再看到了,所以最好复制到文件中

 # 设计模式
- `建造者模式`: 对于`NoteData`类,有一个`NoteDataBuild`类
```java
public class NoteDataBuilder {
    public static NoteData build(String selectedText, String fileName) {
        NoteData noteData = new NoteData();
        noteData.setSelectedText(selectedText);
        noteData.setFileName(fileName);
        noteData.setFileType(getFileType(fileName));
        return noteData;
    }
    public static NoteData build(Map map) {
        NoteData noteData = new NoteData();
        noteData.setSelectedText((String) map.get("selectedText"));
        noteData.setFileName((String) map.get("fileName"));
        noteData.setFileType((String) map.get("fileType"));
        noteData.setNote((String) map.get("note"));
        noteData.setNoteTitle((String) map.get("noteTitle"));
        return noteData;
    }
}
```
因为使用`Jackson`从文件中取出`json`转换成`JavaBean`时会转换成`LinkedHashMap`所以用`build()`方法传入一个`Map`构造`NoteData`对象

- `模板模式`
	- 在使用`MarkDown`模板的时候,方便添加更多的模板在类`cc.yyf.note.procesor.Processor`和`cc.yyf.note.procesor.AbstractFreeMarkProcessor`和`cc.yyf.note.procesor.FreeMarkProcessor`
	- 在`Setting`页面中添加更多的上传方式,目前只支持`GitHub`后续可以添加更多的云盘 ，在类`cc.yyf.note.window.GitHubUploadSettingView`和`cc.yyf.note.window.UploadSettingView`

#  技术实现
- 使用`Jackson`来保存文档,还有保存笔记列表
```json
["StringSourceCode","IntegerSourceCode"]
```
```json
{"StringSourceCode":[{"selectedText":"private final byte[] value;","fileName":"String.class","fileType":"class","note":"String底层采用byte[]数组封装","noteTitle":"String底层封装"},{"selectedText":"public String() {\n        this.value = \"\".value;\n        this.coder = \"\".coder;\n    }","fileName":"String.class","fileType":"class","note":"String构造一个空字符串","noteTitle":"String构造"}],"IntegerSourceCode":[{"selectedText":"static final int low = -128;\n        static final int high;\n        static final Integer[] cache;","fileName":"Integer.class","fileType":"class","note":"Integer底层缓存了一个cache数组保存-128到127","noteTitle":"Integer缓存"}]}
```
- 实现`ApplicationInitializedListener`接口,用来在`IDEA`启动时完成初始化
```java
/**
用来初始化的方法
*/
public void componentsInitialized() {}
```
同时需要在`plugin.xml`中配置
```xml
<extensions defaultExtensionNs="com.intellij">
        <applicationInitializedListener implementation="cc.yyf.note.init.IDEAInit"/>
    </extensions>
```

- 实现了`ProjectCloseHandler`接口完成了在`IDEA`关闭时调用的操作
```java
@Override
    public boolean canClose(@NotNull Project project) {
		/**
		返回true可以关闭,false就不能关闭
		*/
        return true;
    }
```
同样这个也要在`plugin.xml`中配置
```xml
 <extensions defaultExtensionNs="com.intellij">
        <applicationInitializedListener implementation="cc.yyf.note.init.IDEAInit"/>
        <projectCloseHandler implementation="cc.yyf.note.destroy.IDEDestroy"/>
    </extensions>
```

- 实现`Configurable`接口用来在`Setting`中添加自己的界面
```java
/**
通过返回一个JCompont显示界面
*/
@Override
    public @Nullable JComponent createComponent() {
    }
```
同样需要在`plugin.xml`中配置
```xml
<extensions defaultExtensionNs="com.intellij">
        <applicationInitializedListener implementation="cc.yyf.note.init.IDEAInit"/>
        <projectCloseHandler implementation="cc.yyf.note.destroy.IDEDestroy"/>
        <applicationConfigurable displayName="MarkDownUpload" instance="cc.yyf.note.window.FirstSettingView">
            <configurable instance="cc.yyf.note.window.GitHubUploadSettingView"/>
        </applicationConfigurable>
    </extensions>
```
`applicationConfigurable`可以配置多级页面

- 通过实现`ToolWindowFactory`来实现`ToolWindow`(边边那个`noteWindow`框)
```java
 @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // 创建NoteListWindow对象
        NoteListWindow window = new NoteListWindow(project, toolWindow);
        // 将window中的JComboBox实例传出来
        MyComponent.noteToolWindowJComboBox = window.getTextFieldTopic();
        // 获取内容工厂实例
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        // 获取用于toolWindow显示的内容
        Content content = contentFactory.createContent(window.getContentPanel(), "", false);
        // 给toolWindow设置内容
        toolWindow.getContentManager().addContent(content);
    }
```
在`plugin.xml`中配置
```xml
<extensions defaultExtensionNs="com.intellij">
        <applicationInitializedListener implementation="cc.yyf.note.init.IDEAInit"/>
        <projectCloseHandler implementation="cc.yyf.note.destroy.IDEDestroy"/>
        <!-- Add your extensions here -->
        <toolWindow id = "noteWindow" factoryClass="cc.yyf.note.window.NoteToolWindow" anchor="right"/>
        <applicationConfigurable displayName="MarkDownUpload" instance="cc.yyf.note.window.FirstSettingView">
            <configurable instance="cc.yyf.note.window.GitHubUploadSettingView"/>
        </applicationConfigurable>
    </extensions>
```

- 继承`AnAction`实现一个动作(可以理解为一次点击事件)
```java
 @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
    }
```
```xml
<actions>
        <!-- Add your actions here -->
        <action id="PopupMenuActionID" class="cc.yyf.note.action.PopupMenuAction" text="MarkDownNote" description="添加MarkDown笔记">
            <add-to-group group-id="EditorPopupMenu" anchor="first"/>
            <keyboard-shortcut keymap="$default" first-keystroke="ctrl F"/>
        </action>
    </actions>
```
我的点击事件是`EditorPopupMenu`.

- 使用`FreeMark`模板
```
## ${topic}
@[toc]
<#list noteList as note>
### ${note.noteTitle}
- ${note.note}
- ${note.fileName}
```${note.fileType}
${note.selectedText}
`` `
</#list>
```

# 一些疑惑和BUG和提升空间
- 没有找到`IDEA`关闭的时候调用的方法,所以只能使用关闭项目时候调用的方法,导致在直接点击右上角关闭按钮的时候这个方法会调用两次.
- 可能是线程的一些问题,再点击上传到`github`按钮的时候,要点击两次才能上传成功.
- 后续可以开发删除单条笔记和修改笔记排序
- `github`上传时不能用中文作为文件名,所以不要使用中文作为文档标题

# 参考
[官方插件开发文档](https://jetbrains.org/intellij/sdk/docs/intro/welcome.html)
[easy_javadoc源代码](https://gitee.com/starcwang/easy_javadoc?utm_source=alading&utm_campaign=repo)

# 心路历程
一开始面对官方的英文文档看起来是很困难的,还有对于`gradle`也是第一次使用,以前都是使用`maven`,对于插件开发也是第一次,`API`的陌生是开发最大的阻力,很多东西有想法但是很难实现,第一阶段的开发没有上传`github`也不能支持多个笔记,也不能支持笔记的持久化,`IDEA`关了笔记就没了,去看了几个插件的实现,用了一个翻译插件,可惜不是用`Java`开发的后来看了`easy_javadoc`的源码,知道了如何在`Setting`中添加页面,加了`easy_javadoc`作者的开源交流群,群主很热心的解答问题.第二阶段有了上传`github`功能,但是不支持多笔记,而且不能持久化,于是去看了看`Java Swing`编程,用`Jackson`存储将对象转化为`json`存储在文件中.最终有了现在的项目
 
