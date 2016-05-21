package io.cify.gradle

import groovyx.gpars.GParsPool
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class GradleTask extends DefaultTask {

    String glue
    String hub
    String fs
    String inparallel
    int threads
    int mult
    List combos

    List tags
    List plugins

    String accessKey
    String projectId

    String iosApp
    String androidApp
    String browserUrl


    @TaskAction
    void exec() {

        List tasksPool = []
        def features = project.fileTree(dir: 'src/test/resources').include '**/*.feature'

        if (inparallel.equalsIgnoreCase('combos')) {
            combos = combos.plus(combos * (mult - 1))

            features.each { File file ->
                combos.eachWithIndex { def cs, int index ->

                    String taskName = [file.name, "${cs.@name}".replaceAll(/\s/, '_'), index].join('_')

                    project.task(taskName, type: GradleCucumberTask) {
                        gluePackage = glue
                        hubUrl = hub
                        fsUrl = fs
                        combosObject = cs
                        tagList = tags
                        pluginList = plugins
                        featureFile = file

                        taskId = taskName

                        reporterAccessKey = accessKey
                        reporterProjectId = projectId

                        targetIOSApp = iosApp
                        targetAndroidApp = androidApp
                        targetBrowserUrl = browserUrl
                    }
                    tasksPool.add(project.tasks[taskName])
                }
            }
        } else {
            //TODO
        }


        GParsPool.withPool(threads) {
            tasksPool.eachParallel { GradleCucumberTask task ->
                try {
                    task.execute()
                } catch (all) {
                    logger.warn("TASK FAILED: ${task.name}", all)
                }
            }
        }
    }
}