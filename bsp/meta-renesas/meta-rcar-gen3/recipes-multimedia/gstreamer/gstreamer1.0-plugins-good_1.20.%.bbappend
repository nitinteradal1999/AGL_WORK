require include/rcar-gen3-modules-common.inc
require gstreamer.inc

SRC_URI:remove = "https://gstreamer.freedesktop.org/src/gst-plugins-good/gst-plugins-good-${PV}.tar.xz \
		  file://0001-qt-include-ext-qt-gstqtgl.h-instead-of-gst-gl-gstglf.patch \
           	  file://0002-rtpjitterbuffer-Fix-parsing-of-the-mediaclk-direct-f.patch \
                  "

SRC_URI:append = " ${RENESAS_GST_URL}"

DEPENDS += "mmngrbuf-user-module"

S = "${WORKDIR}/git/subprojects/gst-plugins-good"

EXTRA_OEMESON:append = " \
    -Dcont-frame-capture=true \
    -Dignore-fps-of-video-standard=true \
"
