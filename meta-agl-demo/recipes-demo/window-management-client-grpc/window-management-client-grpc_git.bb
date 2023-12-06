SUMMARY     = "Window management gRPC application"
DESCRIPTION = "AGL demonstration of window management using gRPC"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/src/window-management-client-grpc.git"
SECTION     = "apps"

LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = "qtbase qtdeclarative qtquickcontrols2 qtwayland \
	   qtwayland-native libqtappfw grpc grpc-native \
	   "

PV = "2.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/src/window-management-client-grpc.git;protocol=https;branch=${AGL_BRANCH}"
SRCREV  = "c625714c4adce04c34ab406bdd444b13773760e2"

S  = "${WORKDIR}/git"

inherit  meson pkgconfig

AGL_APP_NAME = "window-management-client-grpc"

do_install:append() {
    install -d ${D}${sysconfdir}/xdg/AGL/window-management-client-grpc
}

RDEPENDS:${PN} += "libqtappfw qtbase-qmlplugins"
