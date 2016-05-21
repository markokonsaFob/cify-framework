package io.cify.gradle

import groovy.xml.XmlUtil
import org.gradle.api.tasks.JavaExec

class GradleCucumberTask extends JavaExec {

    final static String JAVAEXEC_MAIN = 'cucumber.api.cli.Main'

    String gluePackage
    String hubUrl
    String fsUrl
    def combosObject
    List tagList
    List pluginList
    File featureFile

    String taskId

    String reporterAccessKey
    String reporterProjectId

    String targetIOSApp
    String targetAndroidApp
    String targetBrowserUrl


    @Override
    void exec() {
        def methodArgs = [];

        if (gluePackage?.trim()) {
            methodArgs << '--glue'
            methodArgs << gluePackage
        }

        if (pluginList) {
            pluginList.each {
                methodArgs << '--plugin'
                methodArgs << it
            }
        }

        methodArgs << '--plugin'
        methodArgs << "json:${project.buildDir}/reports/cify/${taskId}.json"

        methodArgs << '--plugin'
        methodArgs << 'io.cify.framework.plugins.CucumberLogPlugin'

        methodArgs << '--plugin'
        methodArgs << 'pretty'

        if (tagList) {
            tagList.each {
                methodArgs << '--tags'
                methodArgs << it
            }
        }

        methodArgs.add(featureFile.path)

        systemProperties.put('COMBOS', XmlUtil.serialize(combosObject))
        systemProperties.put('HUB_URL', hubUrl)
        systemProperties.put('FS_URL', fsUrl)
        systemProperties.put('BUILD_ID', System.getenv('BUILD_ID') ?: 0)
        systemProperties.put('TASK_NAME', taskId)
        systemProperties.put('REPORTER_ACCESS_KEY', reporterAccessKey)
        systemProperties.put('REPORTER_PROJECT_ID', reporterProjectId)
        systemProperties.put('TARGET_IOS_APP', targetIOSApp)
        systemProperties.put('TARGET_ANDROID_APP', targetAndroidApp)
        systemProperties.put('TARGET_BROWSER_URL', targetBrowserUrl)

        def classPath = project.configurations.testRuntime + project.sourceSets.test.output

        logger.debug('TASK_NAME: ' + systemProperties.get('TASK_NAME').toString())
        logger.debug('COMBOS: ' + systemProperties.get('COMBOS').toString())
        logger.debug('HUB_URL: ' + systemProperties.get('HUB_URL').toString())
        logger.debug('FS_URL: ' + systemProperties.get('FS_URL').toString())
        logger.debug(methodArgs.toString())


        args = methodArgs
        main = JAVAEXEC_MAIN
        classpath = classPath

        super.exec();
    }
}