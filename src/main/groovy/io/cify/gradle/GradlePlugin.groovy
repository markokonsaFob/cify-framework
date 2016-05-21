package io.cify.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

public class GradlePlugin implements Plugin<Project> {

    static Map<String, Map> cifyTasks = [
            'run'        : ['description': 'Runs all stable features and scenarios', 'tags': ['~@Broken', '~@Skip', '~@Unstable']],
            'runAll'     : ['description': 'Runs all features and scenarios', 'tags': []],
            'runBroken'  : ['description': 'Runs features and scenarios marked with @Broken', 'tags': ['@Broken']],
            'runUnstable': ['description': 'Runs features and scenarios marked with @Unstable', 'tags': ['@Unstable']]
    ]


    static Map cifyPlugins = [
            'screenshot' : 'io.cify.framework.plugins.ScreenshotPlugin',
            'log'        : 'io.cify.framework.plugins.CucumberLogPlugin',
            'datastorage': 'io.cify.framework.plugins.DataStoragePlugin'
    ]


    @Override
    void apply(Project project) {

        project.extensions.create('cify', GradlePluginExtension)


        cifyTasks.each { String k, v ->
            project.task(k, type: GradleTask, dependsOn: [project.tasks.compileTestJava, project.tasks.processTestResources]) {

                group = 'Cify'
                description = v.description

                doFirst {
                    setTaskParameters.delegate = owner
                    setTaskParameters(project, v.tags)
                }

                doLast {
                    project.tasks['generateReport'].execute()
                }
            }
        }



        project.task('generateReport', type: ReportTask) {
            group = 'Cify'
            description = 'Generates local HTML report'
        }


        project.task('setup', type: SetupTask) {
            group = 'Cify'
            description = 'Setups Cify environment'
        }


        project.task('options') {
            group = 'Cify'
            description = 'Shows a list of available options'

            doLast {
                println(getHelpText())
            }
        }
    }


    def setTaskParameters = { Project project, tagList ->

        glue = getGlue(project)
        hub = getHub(project)
        fs = getFS(project)

        inparallel = getInparallel(project)
        threads = getThreads(project)
        mult = getMult(project)

        combos = getCombos(project)

        tags = getTags(project) + tagList
        plugins = getPlugins(project)

        accessKey = getAccessKey(project)
        projectId = getProjectId(project)

        iosApp = getIOSApp(project)
        androidApp = getAndroidApp(project)
        browserUrl = getBrowserUrl(project)
    }


    static String getGlue(Project project) {
        project.hasProperty('glue') ? project.glue : project.cify.glue
    }

    static String getHub(Project project) {
        project.hasProperty('hub') ? project.hub : project.cify.hub
    }

    static String getFS(Project project) {
        project.hasProperty('fs') ? project.fs : project.cify.fs
    }

    static String getInparallel(Project project) {
        project.hasProperty('inparallel') ? project.inparallel : project.cify.inparallel
    }

    static int getThreads(Project project) {
        project.hasProperty('threads') && project.threads.toInteger() > 1 ? project.threads.toInteger() : project.cify.threads
    }

    static int getMult(Project project) {
        project.hasProperty('mult') && project.mult.toInteger() > 1 ? project.mult.toInteger() : project.cify.mult
    }

    static List getCombos(Project project) {
        (project.hasProperty('combos') ? project.combos.tokenize(',') : project.cify.combos).collect {
            new XmlSlurper().parse("${SetupTask.COMBOS_DIR}/${it}.xml")
        }
    }

    static List getTags(Project project) {
        project.hasProperty('tag') ? project.tag.tokenize(',').collect { '@' + it.toString() } : []
    }

    static List getPlugins(Project project) {
        project.hasProperty('plugin') ?
                project.plugin.tokenize(',').collect { cifyPlugins[it] ? cifyPlugins[it] : it } : []
    }

    static String getAccessKey(Project project) {
        project.hasProperty('accessKey') ? project.accessKey : project.cify.accessKey
    }

    static String getProjectId(Project project) {
        project.hasProperty('projectId') ? project.projectId : project.cify.projectId
    }

    static String getIOSApp(Project project) {
        project.hasProperty('iosApp') ? project.iosApp : project.cify.iosApp
    }

    static String getAndroidApp(Project project) {
        project.hasProperty('androidApp') ? project.androidApp : project.cify.androidApp
    }

    static String getBrowserUrl(Project project) {
        project.hasProperty('browserUrl') ? project.browserUrl : project.cify.browserUrl
    }

    static String getHelpText() {

        '''
Options:
    -Pglue              Set a package to search step definitions in
                        Usage:  ./gradlew run -Pglue=com.example.stepdefinitions
    -Phub               Set Selenium grid hub/node to send the requests to
                        Usage:  ./gradlew run -Phub=http://localhost:4444
    -Pfs                Set absolute local path or remote http URL to a file storage with .ipa or .apk files
                        Usage:  ./gradlew run -Pfs=/Users/Cify/storage
                                ./gradlew run -Pfs=http://localhost/storage
    -PaccessKey         Set access key for datastorage plugin
                        Usage:  ./gradlew run -PaccessKey=test
    -PprojectId         Set project key for datastorage plugin
                        Usage:  ./gradlew run -PprojectId=5666458667319296
    -Pthreads           Specify number of parallel threads. Default 1
                        Usage: ./gradlew run -Pthreads=3
    -Pmult              Specify a combo number multiplier. Default 1
                        Usage: ./gradlew run -Pmult=3
    -Pcombos            Specify xml file with combos
                        Usage:  ./gradlew run -Pcombos=webcombos
                                ./gradlew run -Pcombos=mobilewebcombos
    -Ptag               Run features/scenarios with certain tag only
                        Usage:  ./gradlew run -Ptag=smoke
                                ./gradlew run -Ptag=android,ios
    -Pplugin            Register a built-in plugin. Built-in Cify plugins: screenshot
                        Usage:  ./gradlew run -Pplugin=screenshot
                                ./gradlew run -Pplugin=screenshot,saucelabs
    -PiosApp            Specify an iOS app to test
                        Usage: ./gradlew run -PiosApp=cify-app.ipa
    -PandroidApp        Specify an Android app to test
                        Usage: ./gradlew run -PandroidApp=cify-app.apk
    -PbrowserUrl        Specify a browser url to test
                        Usage: ./gradlew run -PbrowserUrl=www.example.com
        '''

    }
}
