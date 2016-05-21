package io.cify.gradle

import org.codehaus.plexus.util.Os
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class SetupTask extends DefaultTask {

    private static final String CHROME_DRIVER_VERSION = '2.21'

    public static final String CIFY_DIR = "cify"
    public static final String COMBOS_DIR = "src/test/resources/combos"


    public File cifyDir = new File("${project.projectDir}/$CIFY_DIR")
    public File seleniumDir = new File("${cifyDir}/selenium")
    public File webdriversDir = new File("${seleniumDir}/webdrivers")
    public File appiumDir = new File("${cifyDir}/appium")
    public File fsDir = new File("${cifyDir}/fs")


    public File combosDir = new File("${project.projectDir}/$COMBOS_DIR")
    public File combosFile = new File("${combosDir}/combos.xml")


    @TaskAction
    void exec() {

        makeDirectories()
        downloadSeleniumServer()
        downloadAndUnzipWebdrivers()
        cloneAndInstallAppium()
        writeCombosXml()
    }


    private void makeDirectories() {
        cifyDir.mkdirs()
        seleniumDir.mkdirs()
        webdriversDir.mkdirs()
        appiumDir.mkdirs()
        fsDir.mkdirs()
        combosDir.mkdirs()
    }


    private void downloadSeleniumServer() {

    }


    private void downloadAndUnzipWebdrivers() {
        if (Os.isFamily(Os.FAMILY_WINDOWS)) {
            downloadAndUnzipChromeDriver("win32")
        } else if (Os.isFamily(Os.FAMILY_MAC)) {
            downloadAndUnzipChromeDriver("mac32")
        } else if (Os.isFamily(Os.FAMILY_UNIX)) {
            downloadAndUnzipChromeDriver(Os.isArch('amd64') ? "linux64" : "linux32")
        }
    }


    public static String getChromeWebdriverName() {
        if (Os.isFamily(Os.FAMILY_WINDOWS)) {
            "chromedriver.exe"
        } else {
            "chromedriver"
        }
    }


    private void downloadAndUnzipChromeDriver(String osName) {
        String url = "http://chromedriver.storage.googleapis.com/${CHROME_DRIVER_VERSION}/chromedriver_" + osName + ".zip"
        def file = new File("${webdriversDir.absolutePath}/chromedriver.zip")
        download(url, file)
        unzip(file, webdriversDir)
    }


    private void cloneAndInstallAppium() {

    }


    private void writeCombosXml() {
        def xml = '''<?xml version="1.0" encoding="UTF-8"?>
<set>
    <combos>
        <combo>
            <name></name>
            <type></type>
            <platform></platform>
            <deviceName></deviceName>
            <platformVersion></platformVersion>
            <udid></udid>
            <browser></browser>
            <browserVersion></browserVersion>
        </combo>
    </combos>
</set>
'''
        if (!combosFile.exists()) combosFile.write(xml);
    }


    private static void download(String url, File destFile) {
        def stream = destFile.newOutputStream()
        stream << new URL(url).openStream()
        stream.close()
    }


    private static void unzip(File srcZip, File destDir) {
        AntBuilder builder = new AntBuilder();
        builder.unzip(
                src: srcZip.absolutePath,
                dest: destDir.absolutePath,
                overwrite: "true")
        builder.chmod(dir: destDir.absolutePath, perm: "+x", includes: "*")
        srcZip.delete()
    }

}
