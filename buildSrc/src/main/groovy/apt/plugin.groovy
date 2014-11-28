package apt
import org.gradle.api.*
import org.gradle.api.tasks.*
import org.gradle.api.artifacts.ProjectDependency
public class plugin implements Plugin<Project> {
    void apply(Project project) {
        project.apply plugin: 'apt'
        project.ext.artifactMaps = [:]

        project.ext.addAPT = { artifactMap ->
            project.dependencies {
                println artifactMap
                compile artifactMap
                apt artifactMap
            }
            project.addAPTReq(artifactMap)
        }
        project.ext.addAPTReqWComp = { artifactMap ->
            project.dependencies {
                compile artifactMap
            }
            project.addAPTReq(artifactMap)
        }
        project.ext.addAPTReq = { artifactMap ->
            project.artifactMaps << [(artifactMap.name): artifactMap]
        }
        project.task("copyInAPTThings", dependsOn: 'cleanCopyInAPTThings') {
            description "Copies apt libraries to an appropriate directory for adding to eclipse."
            ext.outputDir = "libs/apt"
            inputs.files(project.configurations.compile)
            outputs.dir(outputDir)
            doLast {
                project.copy {
                    def copythis = []
                    def artifacts = project.configurations.compile.resolvedConfiguration.resolvedArtifacts
                    .each {
                        println "${project.name}:${it.moduleVersion.id}"
                        if (project.artifactMaps.containsKey(it.name)) {
                            copythis << it.file
                        }
                    }
                    copythis.each {
                        from it
                    }
                    into outputDir
                }
            }
        }

        project.eclipseClasspath.dependsOn(project.copyInAPTThings)
    }
}
