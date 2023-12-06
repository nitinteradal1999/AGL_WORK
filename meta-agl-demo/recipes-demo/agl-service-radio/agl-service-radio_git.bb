SUMMARY     = "Demo Radio Service Daemon"
DESCRIPTION = "Demo Radio Service Daemon"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/agl-service-radio"
LICENSE     = "Apache-2.0 & GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984 \
                    file://LICENSE.GPL-2.0-only;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = " \
    glib-2.0 \
    glib-2.0-native \
    gstreamer1.0 \
    protobuf-native \
    grpc-native \
    grpc \
    systemd \
    rtl-sdr \
    libusb-compat \
"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/agl-service-radio;protocol=https;branch=${AGL_BRANCH}"
SRCREV  = "7f26a2d06410fd3a2768612b9c9daf869778e480"

PV = "2.0+git${SRCPV}"
S  = "${WORKDIR}/git"

inherit meson pkgconfig systemd

SYSTEMD_SERVICE:${PN} = "agl-service-radio.service"

FILES:${PN} += "${systemd_system_unitdir}"
