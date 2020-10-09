package com.bird.mm

import org.gradle.api.Plugin
import org.gradle.api.Project

class OnePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val ext = project.extensions.extraProperties
//        ext.get()
        project.task("one").doLast {
            println("Hello , I am custom gradle plugin")
        }
    }

}