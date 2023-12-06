SUMMARY = "LXC host demo image"
LICENSE = "MIT"

require lxc-host-image-minimal.bb
require recipes-platform/images/agl-lxc-install-single-image.inc
require recipes-platform/images/agl-lxc-autostart.inc

CONTAINER_IMAGES ?= "agl-container-cluster:guest-image-cluster-demo \
                     agl-container-ivi:guest-image-ivi-demo \
                    "

IMAGE_INSTALL += " \
    kernel-modules \
    alsa-utils \
    packagegroup-pipewire \
    pipewire-ic-ipc \
    wireplumber-config-agl \
"

# packages required for network bridge settings via lxc-net
IMAGE_INSTALL += " \
    lxc-networking \
    iptables-modules \
    dnsmasq \
    systemd-netif-config \
    kernel-module-xt-addrtype \
    kernel-module-xt-multiport \
"

# network manager to use
VIRTUAL-RUNTIME_net_manager = "systemd"


# Under the this line, shall describe machine specific package.
IMAGE_INSTALL:append:rcar-gen3 = " kernel-module-gles gles-user-module-firmware"
