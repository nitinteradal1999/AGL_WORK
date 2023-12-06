SUMMARY     = "AGL Application Launcher service"
DESCRIPTION = "AGL Application Launcher service build with recipe method"
HOMEPAGE    = "https://git.automotivelinux.org/src/applaunchd"
SECTION     = "apps"
LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = " \
    glib-2.0 \
    glib-2.0-native \
    protobuf-native \
    grpc-native \
    grpc \
    systemd \
"

PV = "2.0+git${SRCPV}"

SRC_URI = " \
    git://gerrit.automotivelinux.org/gerrit/src/applaunchd;protocol=https;branch=${AGL_BRANCH}  \
    file://applaunchd.service \
    file://agl-app@.service \
    file://agl-app-web@.service \
    file://agl-app-flutter@.service \
    file://no-network.conf \
    file://private-tmp.conf \
"
SRCREV = "c32fe42f40d0af8b31b6113a3140f52b83be7769"

S = "${WORKDIR}/git"

inherit meson pkgconfig systemd useradd features_check

REQUIRED_DISTRO_FEATURES = "systemd polkit"

USERADD_PACKAGES = "${PN}"
USERADDEXTENSION = "useradd-staticids"
GROUPADD_PARAM:${PN} = "-g 1003 applaunchd ; "
USERADD_PARAM:${PN} = "\
    -g 1003 -u 1003 -o -d / -K PASS_MAX_DAYS=-1 applaunchd ; \
"

SYSTEMD_SERVICE:${PN} = "applaunchd.service"

do_install:append() {
    # Install generic template for all agl-app services
    install -d ${D}${systemd_system_unitdir}
    install -m 644 ${WORKDIR}/applaunchd.service ${D}${systemd_system_unitdir}/
    install -m 644 ${WORKDIR}/agl-app@.service ${D}${systemd_system_unitdir}/
    install -m 644 ${WORKDIR}/agl-app-web@.service ${D}${systemd_system_unitdir}/
    install -m 644 ${WORKDIR}/agl-app-flutter@.service ${D}${systemd_system_unitdir}/

    # Install individual sandboxing overrides/drop-ins to be used by apps
    install -d ${D}${systemd_system_unitdir}/sandboxing
    install -m 644 ${WORKDIR}/no-network.conf ${D}${systemd_system_unitdir}/sandboxing/
    install -m 644 ${WORKDIR}/private-tmp.conf ${D}${systemd_system_unitdir}/sandboxing/
}

PACKAGE_BEFORE_PN += "${PN}-template-agl-app ${PN}-template-agl-app-web ${PN}-template-agl-app-flutter"

FILES:${PN} += "${systemd_system_unitdir}"

FILES:${PN}-template-agl-app = "${systemd_system_unitdir}/agl-app@.service"

FILES:${PN}-template-agl-app-web = "${systemd_system_unitdir}/agl-app-web@.service"

FILES:${PN}-template-agl-app-flutter = "${systemd_system_unitdir}/agl-app-flutter@.service"

RDEPENDS:${PN} += " \
    agl-users \
    polkit-rule-agl-app \
"

RDEPENDS:${PN}-template-agl-app = "${PN}"

RDEPENDS:${PN}-template-agl-app-web = "${PN}"

RDEPENDS:${PN}-template-agl-app-flutter = "${PN}"
