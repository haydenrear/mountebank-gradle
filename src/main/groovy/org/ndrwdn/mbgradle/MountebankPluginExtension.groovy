package org.ndrwdn.mbgradle

class MountebankPluginExtension {
    String mbScriptPath = 'mb_plugin'
    String extractPath = 'mb_plugin'
    String mountebankVersion = "2.9.1"
    long startTimeout = 5000
    long stopTimeout = 5000
    Integer port = null
    String configFile = null
    Boolean noParse = null
    String logFile = null
    String logLevel = null
    Boolean noLogFile = null
    Boolean allowInjection = null
    Boolean localOnly = null
    String ipWhitelist = null
    Boolean mock = null
    Boolean debug = null
    String pidFile = null
    String saveFile = null
    Boolean removeProxies = null
}
