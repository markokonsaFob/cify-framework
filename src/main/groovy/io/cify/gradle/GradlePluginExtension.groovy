package io.cify.gradle


class GradlePluginExtension {

    String glue

    String hub = 'http://localhost:4444/wd/hub'

    String fs = 'cify/fs'

    String inparallel = 'combos'   // ['combo','combo']

    int threads = 1

    int mult = 1

    List combos = ['combos']

    String accessKey

    String projectId

    String iosApp

    String androidApp

    String browserUrl
}
