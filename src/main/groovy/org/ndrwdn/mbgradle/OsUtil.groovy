package org.ndrwdn.mbgradle

class OsUtil {

    private static final Map<String, String> osMappings = [
            'Linux': 'linux',
            'Mac OS X': 'darwin-arm64'
    ]

    public static String determineMbOs() {
        osMappings.get(System.getProperty('os.name'))
    }

    public static String determineNodeOs() {
        osMappings.get(System.getProperty('os.name'))
    }
}
