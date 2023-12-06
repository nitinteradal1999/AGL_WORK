SUMMARY     = "Settings application"
DESCRIPTION = "AGL demonstration Settings application"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/settings"
SECTION     = "apps"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = " \
    qtbase \
    qtdeclarative \
    qtquickcontrols2 \
    qtvirtualkeyboard \
    libqtappfw \
"

PV = "2.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/settings;protocol=https;branch=${AGL_BRANCH}"
SRCREV = "26dc6a8c8847248450bf902124b7cd867e027274"

S  = "${WORKDIR}/git"

inherit qmake5 pkgconfig agl-app

AGL_APP_NAME = "Settings"

RDEPENDS:${PN} += " \
    qtwayland \
    qtbase-qmlplugins \
    qtgraphicaleffects-qmlplugins \
    qtquickcontrols2-agl-style \
    libqtappfw \
"
