package com.sheaye.apt;

import com.sheaye.ClickFilter;

import java.util.Arrays;

import javax.lang.model.element.TypeElement;

/**
 * Created by yexinyan on 2017/5/24.
 */

public class ClassHolder {

    private String mPackageName;
    private String mTargetClassName;
    private String mSimpleClassName;
    private TypeElement mTypeElement;
    private String mClassFullName;
    private BindClickMethod mBindClickMethod;


    ClassHolder(String packageName, String targetClassName) {
        mPackageName = packageName;
        mTargetClassName = targetClassName;
        mSimpleClassName = targetClassName.replace(".", "$") + ClickFilter.SUFFIX;
        mClassFullName = packageName + "." + mSimpleClassName;
    }

    void setBindClickMethod(BindClickMethod method) {
        mBindClickMethod = method;
    }

    void setTypeElement(TypeElement mTypeElement) {
        this.mTypeElement = mTypeElement;
    }

    String getClassFullName() {
        return mClassFullName;
    }

    TypeElement getTypeElement() {
        return mTypeElement;
    }

    String generateJavaCode() {
        int[] resIds = mBindClickMethod.getResIds();
        return "// Generated code from AnticlutterClickBinder. Do not modify!\n" +
                "package " + mPackageName + ";\n" +
                "\n" +
                "import android.view.View;\n" +
                "import com.sheaye.binder.ClickBinder;\n" +
                "import com.sheaye.ClickFilter;\n" +
                "import com.sheaye.binder.Finder;\n" +
                "import android.util.Log;\n" +
                "\n" +
                "public class " + mSimpleClassName + "<T extends " + mTargetClassName + "> implements ClickBinder<T> {\n" +
                "    @Override\n" +
                "    public void bind(final T target, final Object source, final Finder finder) {\n" +
                "        int[] resIds = " + Arrays.toString(resIds).replace("[", "{").replace("]", "}") + ";\n" +
                "        for (int i = 0; i < resIds.length; i++) {\n" +
                "            View view = finder.findViewById(source, resIds[i]);\n" +
                "            if (view != null) {\n" +
                "                view.setOnClickListener(new View.OnClickListener() {\n" +
                "                    @Override\n" +
                "                    public void onClick(View v) {\n" +
                "                        ClickFilter.checkClickEvent(\"" + mTargetClassName + "\", source, v, new ClickFilter.ClickExecutor() {\n" +
                "                            @Override\n" +
                "                            public void execute(View view) {\n" +
                "                                target." + mBindClickMethod.getMethodName() + "(view);\n" +
                "                            }\n" +
                "                        });\n" +
                "                    }\n" +
                "                });\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }
}
