SUMMARY     = "Momiyama Web UI for CES2023"
DESCRIPTION = "Momiyama Web UI for CES2023."
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

PV = "0.1.0"

SRC_URI = "git://github.com/agl-ic-eg/momiweb.git;protocol=https;branch=main \
           file://momiweb.conf \
          "
SRCREV = "cfdfdd670a83558a408bafa7b0262381313b907d"

S = "${WORKDIR}/git"

inherit allarch

do_install:append() {
	install -d ${D}${sysconfdir}/lighttpd.d
	install -m 0644 ${WORKDIR}/momiweb.conf ${D}${sysconfdir}/lighttpd.d/

	install -d ${D}/www/momiweb
	cp -R ${S}/webui/* ${D}/www/momiweb/

	install -d ${D}/www/cgi-bin
	cp -R ${S}/cgi/* ${D}/www/cgi-bin/
}

FILES:${PN} += " \
    /www/momiweb/* \
    /www/momiweb/*/* \
    /www/cgi-bin/* \
    "
RDEPENDS:${PN} = " \
    lighttpd \
    lighttpd-module-cgi \
    lighttpd-module-alias \
    lighttpd-module-access \
    lighttpd-module-accesslog \
    "
