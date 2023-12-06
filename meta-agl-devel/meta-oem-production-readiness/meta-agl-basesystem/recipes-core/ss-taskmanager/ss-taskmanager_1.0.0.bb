SUMMARY = "ss-taskmanager for AGL software"
DESCRIPTION = "ss-taskmanager to build AGL software"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2ee41112a44fe7014dce33e26468ba93"

DEPENDS += " \
    os-rpclibrary-tool-native \
    ss-interfaceunified \
    ss-romaccesslibrary \
    ss-resourcemanager \
    ns-frameworkunified \
    ns-commonlibrary \
    os-rpclibrary \
    os-vehicleparameterlibrary \
    libxml2-native \
    expat-native \
"

PV = "1.0.0+gitr${SRCPV}"
SRC_URI = "git://gerrit.automotivelinux.org/gerrit/staging/basesystem.git;protocol=https;branch=${AGL_BRANCH}"
SRCREV := "${BASESYSTEM_REVISION}"

S = "${WORKDIR}/git/service/system/task_manager"

inherit agl-basesystem-common

BSMAKE_FILE = "Makefile.client"

RDEPENDS:${PN} += " \
    ss-interfaceunified \
    ns-frameworkunified \
    ns-commonlibrary \
    ss-romaccesslibrary \
    os-rpclibrary \
    ss-resourcemanager \
    os-vehicleparameterlibrary \
"
