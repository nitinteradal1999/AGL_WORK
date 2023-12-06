DESCRIPTION = "Native Service Package Groups"
LICENSE = "Apache-2.0"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-basesystem-service \
"

RDEPENDS:${PN} += " \
    ns-commonlibrary \
    ns-frameworkunified \
    ns-loglibrary \
    os-posixbasedos001legacylibrary \
    os-rpclibrary \
    os-eventlibrary \
    ps-communication \
    ns-backupmanager \
    ss-romaccesslibrary \
    os-vehicleparameterlibrary \
    ss-config \
    ss-interfaceunified \
    ss-resourcemanager \
    ss-taskmanager \
    ss-versionlibrary \
    vs-positioning \
    vs-positioningbaselibrary \
"
