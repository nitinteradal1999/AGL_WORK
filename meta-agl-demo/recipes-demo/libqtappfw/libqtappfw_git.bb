SUMMARY     = "AGL Qt AppFW Library"
DESCRIPTION = "libqtappfw"
HOMEPAGE    = "http://docs.automotivelinux.org"
SECTION     = "libs"

LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = " \
    qtbase \
    qtbase-native \
    qtdeclarative \
    qtwebsockets \
    glib-2.0 \
    bluez-glib \
    connman-glib \
    libmpdclient \
    protobuf-native \
    grpc-native \
    protobuf \
    grpc \
"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/src/libqtappfw;protocol=https;branch=${AGL_BRANCH}"
SRCREV  = "1234b2771bc45a885df54a779dfb8a125f315f93"
S       = "${WORKDIR}/git"

# PV needs to be modified with SRCPV to work AUTOREV correctly
PV = "2.0.1+git${SRCPV}"

inherit meson pkgconfig

RRECOMMENDS:${PN} += "bluez5 connman mpd"

BBCLASSEXTEND = "nativesdk"
