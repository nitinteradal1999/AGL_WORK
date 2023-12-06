require recipes-platform/images/agl-image-boot.bb

SUMMARY = "A minimal container guest image"

IMAGE_ROOTFS_EXTRA_SPACE:append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "" ,d)}"

IMAGE_INSTALL += " \
    packagegroup-agl-ic-core \
    packagegroup-agl-container-feature-logging-guest \
"

NO_RECOMMENDATIONS = "1"
