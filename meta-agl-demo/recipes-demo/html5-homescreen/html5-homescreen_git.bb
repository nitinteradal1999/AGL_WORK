SUMMARY     = "AGL HTML5 Homescreen"
HOMEPAGE    = "https://git.automotivelinux.org/apps/html5-homescreen/"
SECTION     = "apps"
LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI = " \
    git://gerrit.automotivelinux.org/gerrit/apps/html5-homescreen;protocol=https;branch=master \
    file://homescreen.service \
"
SRCREV = "e85c42ae77592afdb72df8e8d4f27e0e7ad17bb9"

PV      = "1.0+git${SRCPV}"
S       = "${WORKDIR}/git"
B       = "${WORKDIR}/build"

inherit pythonnative systemd

DEPENDS = "nodejs-native"

do_compile[network] = "1"
do_compile() {
    cd ${S}
    rm -rf package node_modules package-lock.json
    npm install
    npm run build
}

WAM_APPLICATIONS_DIR = "${libdir}/wam_apps"

SYSTEMD_SERVICE:${PN} = "homescreen.service"

do_install() {
    install -d ${D}${WAM_APPLICATIONS_DIR}/${PN}
    cp -R --no-dereference --preserve=mode,links ${S}/dist/* ${D}${WAM_APPLICATIONS_DIR}/${PN}
    install -D -m 0644 ${WORKDIR}/homescreen.service ${D}${systemd_system_unitdir}/homescreen.service
}

FILES:${PN} = " \
    ${WAM_APPLICATIONS_DIR}/${PN} \
    ${systemd_system_unitdir} \
"

RCONFLICTS:${PN} = "homescreen flutter-homescreen"
RDEPENDS:${PN} = "applaunchd html5-background"
