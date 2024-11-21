package org.ndrwdn.mbgradle

import com.github.gradle.node.NodePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

import static org.ndrwdn.mbgradle.MbPathUtil.mbPidFile
import static org.ndrwdn.mbgradle.MbPathUtil.mbScriptPath

class MountebankGradlePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.extensions.create('mountebank', MountebankPluginExtension)

        project.task(MountebankAcquisitionTask.NAME, type: MountebankAcquisitionTask) {
            outputs.upToDateWhen {
                mbScriptPath(project).exists()
            }
        }

        def nodePlugin = new NodePlugin()
        nodePlugin.apply(project)


        def mbScriptPath = project.mountebank.mbScriptPath
        def nodeVersion = project.node.version
        def resolvedPlatform = project.node.resolvedPlatform.get().name
        def resolvedArch = project.node.resolvedPlatform.get().arch

        project.logger.info("Resolved ${mbScriptPath} ${nodeVersion} ${resolvedPlatform} ${resolvedArch}")

        project.node.workDir.set(
                project.projectDir.toPath().resolve("${ mbScriptPath}/node").toFile())
        project.node.npmWorkDir.set(project.projectDir.toPath().resolve("${mbScriptPath}/node/node-v${nodeVersion}-${resolvedPlatform}-${resolvedArch}/npm")
                .toFile())
        project.node.nodeProjectDir.set(project.projectDir.toPath().resolve(mbScriptPath).toFile())
        project.node.download.set(true)

        project.task(MountebankStartTask.NAME, type: MountebankStartTask) {
            dependsOn MountebankAcquisitionTask.NAME, "nodeSetup", "npmSetup", "npmInstall"
            outputs.upToDateWhen {
                mbPidFile(project).exists()
            }
        }


        project.task(MountebankStopTask.NAME, type: MountebankStopTask) {
            dependsOn MountebankAcquisitionTask.NAME
            outputs.upToDateWhen {
                !mbPidFile(project).exists()
            }
        }


    }
}
