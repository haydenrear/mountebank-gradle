package org.ndrwdn.mbgradle.acquisition


import org.gradle.api.Project

import java.nio.file.Path

import static org.ndrwdn.mbgradle.MbPathUtil.mbScriptPath

class Acquire {


    private final Project project

    Acquire(Project project) {
        this.project = project
    }

    def acquire() {
        def path = mbScriptPath(project)
        path.mkdirs()
        writeMbScript(path)


        writeMbPackageJson(path)
    }

    private void writeMbPackageJson(File path) {
        def packageJsonFile = path.toPath().resolve("package.json")

        if (!packageJsonFile.toFile().exists()) {
            this.project.logger.info("package.json did not exist for mountebank. Creating default with version set.")
            def version = this.project.mountebank.mountebankVersion
            def packageJson = """{
  "name": "mountebank-wrapper",
  "dependencies": {
    "mountebank": "${version}"
  }
}
"""
            packageJsonFile.withWriter { writer ->
                writer.write(packageJson)
            }

        } else {
            this.project.logger.info("package.json did exist for mountebank. Skipping creation.")
        }
    }

    private static void writeMbScript(File scriptPath) {
        def script = """#!/usr/bin/env sh
# allow running from any directory, including symlinks
# portable but incomplete readlink -f behavior
FILE=\$0
[ -L \$FILE ] && FILE=`readlink \$FILE`
DIR=`dirname \$FILE`
value=\$(ls node)
"\$DIR"/node/\$value/bin/node \$DIR/node_modules/.bin/mb "\$@"
"""

        scriptPath.toPath().resolve("mb").toFile().withWriter { writer ->
            writer.write(script)
        }

    }

}
