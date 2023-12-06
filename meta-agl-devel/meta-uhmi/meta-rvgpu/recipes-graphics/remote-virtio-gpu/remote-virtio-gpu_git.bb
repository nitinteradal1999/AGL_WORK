SUMMARY = "Remote virtual display userland"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=573c152503e0d9b97c8e0cc09fbb1ad2"

require remote-virtio-gpu.inc

SRC_URI:append = " \
    file://0001-Remove-some-rvgpu-command-options.patch \
    file://0002-Add-agl-shell-desktop-and-xdg-shell-support.patch \
"

S = "${WORKDIR}/git"

DEPENDS = "virglrenderer virtual/libgbm wayland wayland-native libepoxy libinput linux-libc-headers agl-compositor virtio-loopback-driver"
RDEPENDS:${PN} = " wayland libgles2"

inherit cmake pkgconfig

PACKAGECONFIG_CONFARGS += " \
    -DCMAKE_BUILD_TYPE=Release \
"

FILES:SOLIBSDEV = ""
FILES:${PN} += " \
    ${libdir}/librvgpu.so* \
"
