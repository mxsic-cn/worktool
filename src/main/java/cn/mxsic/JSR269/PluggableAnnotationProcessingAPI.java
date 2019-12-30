package cn.mxsic.JSR269;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/**
 * Function: PluggableAnnotationProcessingAPI <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-11-19 15:50:00
 */
@SupportedAnnotationTypes(value = {"cn.mxsic.JSR269.AnnotationPlug"})
@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
public class PluggableAnnotationProcessingAPI extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("Annotation Plug processing");
        for (TypeElement typeElement : annotations) {
            System.out.println(typeElement);
        }
        System.out.println(roundEnv);
        return true;
    }

}
