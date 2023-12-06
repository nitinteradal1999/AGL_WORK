SUMMARY     = "Minimal cluster demo gauges that can be used standalone"
DESCRIPTION = "AGL HMI Application for demonstrating instrument cluster gauges as a wayland compositor"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/agl-cluster-demo-gauges"
SECTION     = "apps"

LICENSE     = "Apache-2.0 & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984 \
                    file://app/cluster-gauges.qml;beginline=10;endline=49;md5=54187d50b29429abee6095fe8b7c1a78"

DEPENDS = "qtquickcontrols2 qtwebsockets"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/agl-cluster-demo-gauges;protocol=https;branch=${AGL_BRANCH} \
           file://cluster-gauges-qtcompositor.service \
"

SRCREV  = "23eaf7da1f7d252aefddf9e41e52c745b7433b50"

S  = "${WORKDIR}/git"

inherit pkgconfig cmake_qt5 systemd

SYSTEMD_SERVICE:${PN} = "${BPN}.service"

do_install:append() {
    install -D -m 0644 ${WORKDIR}/${BPN}.service ${D}${systemd_system_unitdir}/${BPN}.service
}

RDEPENDS:${PN} += " \
    qtquickcontrols \
    qtquickcontrols-qmlplugins \
    qtquickcontrols2 \
    qtquickcontrols2-qmlplugins \
    qtwebsockets \
    qtwebsockets-qmlplugins \
    kms-conf \
"
