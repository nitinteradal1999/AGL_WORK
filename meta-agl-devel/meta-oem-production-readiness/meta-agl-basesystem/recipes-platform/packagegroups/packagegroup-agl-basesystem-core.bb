DESCRIPTION = "AGL Basesystem Core Package Groups"
LICENSE = "Apache-2.0"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-basesystem-core \
"
RDEPENDS:${PN} += "\
    agl-systemd \
"
