SUMMARY     = "AGL Launcher Application"
DESCRIPTION = "AGL Launcher Application build with recipe method"
HOMEPAGE    = "https://git.automotivelinux.org/apps/launcher"
SECTION     = "apps"
LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

DEPENDS = "\
        qtbase \
        qtdeclarative \
        qtquickcontrols2 \
        libqtappfw \
        wayland-native \
        wayland \
        qtwayland \
        qtwayland-native \
        agl-compositor \
        json-c \
        applaunchd \
"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/launcher;protocol=https;branch=${AGL_BRANCH} \
           file://launcher.service \
"
SRCREV = "4be88c049f31b7659e09223857b92ca531835039"

S = "${WORKDIR}/git"

inherit qmake5 systemd pkgconfig

PATH:prepend = "${STAGING_DIR_NATIVE}${OE_QMAKE_PATH_QT_BINS}:"

SYSTEMD_SERVICE:${PN} = "${BPN}.service"

do_install:append() {
    install -D -m0644 ${WORKDIR}/launcher.service ${D}${systemd_system_unitdir}/launcher.service
}

RDEPENDS:${PN} += " \
    libqtappfw \
    applaunchd \
    homescreen \
"
