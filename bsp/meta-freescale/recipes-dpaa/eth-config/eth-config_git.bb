DESCRIPTION = "Ethernet Configuration Files"
SECTION = "eth-config"
LICENSE = "BSD-3-Clause & GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=47716bd5b656aa5e298a132a64d2d1e4"

PR = "r2"

SRC_URI = "git://github.com/nxp-qoriq/eth-config;protocol=https;nobranch=1"
SRCREV = "6164664070e45810c793f112781ebcedc979e132"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "D=${D}"

do_install() {
    oe_runmake install
    chown -R root:root ${D}
}

CLEANBROKEN = "1"
