SUMMARY = "LXC host demo image"
LICENSE = "MIT"

require recipes-platform/images/lxc-host-image-minimal.bb

CONTAINER_IMAGES ?= "agl-container-cluster:guest-image-cluster-demo \
                     agl-container-ivi:agl-demo-platform \
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

IMAGE_INSTALL:append:rcar-gen3 = " kernel-module-gles "
