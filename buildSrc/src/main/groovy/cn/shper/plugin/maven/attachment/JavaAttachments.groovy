package cn.shper.plugin.maven.attachment

import cn.shper.plugin.core.util.Logger
import cn.shper.plugin.maven.model.ability.Artifactable
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.component.SoftwareComponent
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.compile.GroovyCompile
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.javadoc.Javadoc

/**
 * Author: shper
 * Version: V0.1 2019-07-10
 */
class JavaAttachments extends MavenAttachments {

    JavaAttachments(String name, Project project, Artifactable artifactable) {
        super(javaComponentFrom(project))

        if (artifactable.sourcesJar) {
            addArtifactSources(javaSourcesJarTask(project, name))
        }

        if (artifactable.javadocJar) {
            addArtifactSources(javaJavadocsJarTask(project, name))
        }
    }

    private static SoftwareComponent javaComponentFrom(Project project) {
        return project.components.getByName('java')
    }

    private static Task javaSourcesJarTask(Project project, String name) {
        JavaCompile javaCompile = project.compileJava
        GroovyCompile groovyCompile = project.compileGroovy

        def fileTreeList = new ArrayList<>()
        fileTreeList.add(javaCompile.source)

        if (groovyCompile != null) {
            fileTreeList.add(groovyCompile.source)
        }

        return sourcesJarTask(project, name, null, fileTreeList.toArray())
    }

    private static Task javaJavadocsJarTask(Project project, String name) {
        Javadoc javadoc = project.javadoc
        return javadocsJarTask(project, name, javadoc,null)
    }

}