SUMMARY     = "Camera gstreamer demo application"
DESCRIPTION = "AGL demonstration of displaying incoming camera feed"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/camera-gstreamer"
SECTION     = "apps"

LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = " \
    wayland wayland-native \
    gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad \
"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/camera-gstreamer;protocol=https;branch=${AGL_BRANCH}"
SRCREV  = "30c275c5c87b9d4f0bc3b0e0e3175775d3738319"

S  = "${WORKDIR}/git"

inherit cmake pkgconfig agl-app

AGL_APP_NAME = "Camera"

RDEPENDS:${PN} += " \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-bad \
"
