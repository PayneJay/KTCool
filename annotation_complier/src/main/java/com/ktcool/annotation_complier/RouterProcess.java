package com.ktcool.annotation_complier;

import com.google.auto.service.AutoService;
import com.ktcool.annotation.Router;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)//这个注解是用来注册
public class RouterProcess extends AbstractProcessor {
    //存储路由地址和对应Element的映射表
    private final Map<String, TypeElement> routerMap = new HashMap<>();
    //用来创建文件
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Router.class);
        if (elements == null || elements.isEmpty()) {
            return false;
        }

        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            String path = typeElement.getAnnotation(Router.class).path();
            if (!routerMap.containsKey(path)) {
                routerMap.put(path, typeElement);
            }
        }
        createRouterFile();
        return true;
    }

    /**
     * 创建路由文件
     */
    private void createRouterFile() {
        if (routerMap.isEmpty()) {
            //如果映射表为空则不创建
            return;
        }

        Writer writer = null;
        Set<Map.Entry<String, TypeElement>> entrySet = routerMap.entrySet();
        for (Map.Entry<String, TypeElement> elementEntry : entrySet) {
            if (elementEntry != null) {
                //这里的key就是我们注解里添加的path
                String key = elementEntry.getKey();
                //这个是添加了注解的path对应的类Class文件
                TypeElement typeElement = elementEntry.getValue();
                PackageElement packageElement = (PackageElement) typeElement.getEnclosingElement();
                //获取包名
                String packageName = packageElement.getQualifiedName().toString();
                //由完整的包名+类名再拼接$MyRouter构成要生成文件的类名
                String className = typeElement.getSimpleName() + "$MyRouter";
                try {
                    JavaFileObject sourceFile = filer.createSourceFile(className);
                    writer = sourceFile.openWriter();
                    //开始写入
                    String builder = "package " + packageName + ";\n\n" +
                            "import " + "com.ktcool.common.router.MyRouter" + ";\n" +
                            "import " + "com.ktcool.common.router.IRouter" + ";\n\n" +
                            "public class " + className + " implements IRouter {" + "\n" +
                            "    @Override" + "\n" +
                            "    public void loadInto() {" + "\n" +
                            "        MyRouter.getInstance().addRouter(\"" + key + "\", " + typeElement.getSimpleName().toString() + ".class" + ");\n" +
                            "    }\n}\n";
                    writer.write(builder);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (writer != null) {
                        try {
                            writer.flush();
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        //返回最新的版本
        return SourceVersion.latestSupported();
    }

    /**
     * @return 返回支持的注解类型。在这里就是我们定义的所有被Router所注解的类
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> typeSet = new HashSet<>();
        //这个api是获取完整的包名+类名
        String canonicalName = Router.class.getCanonicalName();
        typeSet.add(canonicalName);
        return typeSet;
    }

}