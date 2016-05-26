package io.cify.gradle

import net.masterthought.cucumber.Configuration
import net.masterthought.cucumber.ReportBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction


class ReportTask extends DefaultTask {

    public File reportsDir = new File("${project.buildDir}/cify/reports")
    public File htmlDir = new File("${reportsDir}/html")


    @TaskAction
    void exec() {

        htmlDir.mkdirs();

        def jsonReports = project.fileTree(dir: reportsDir).include '**/*.json'.toString()

        List<String> jsonReportFiles = new ArrayList<String>();
        jsonReports.each { File file ->
            jsonReportFiles.add("${reportsDir}/${file.name}".toString());
        }


        Configuration configuration = new Configuration(htmlDir, project.name);
        configuration.setStatusFlags(true, false, true, true);

        ReportBuilder reportBuilder = new ReportBuilder(jsonReportFiles, configuration);
        reportBuilder.generateReports();
    }
}
