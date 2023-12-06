SUMMARY     = "AGL HTML5 Launcher Application"
HOMEPAGE    = "https://git.automotivelinux.org/apps/html5-launcher/"
SECTION     = "apps"
LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

PV      = "1.0+git${SRCPV}"
S       = "${WORKDIR}/git"
B       = "${WORKDIR}/build"

SRC_URI = " \
  git://gerrit.automotivelinux.org/gerrit/apps/html5-launcher;protocol=https;branch=master \
"
SRCREV = "5d264b8a8ee7813809ef6131be0037457b9cd1e4"

inherit pythonnative agl-app

AGL_APP_TEMPLATE = "agl-app-web"
AGL_APP_ID = "launcher"
AGL_APP_NAME = "HTML5 Launcher"

DEPENDS = "nodejs-native"

do_compile[network] = "1"
do_compile() {
  cd ${S}
  rm -rf package node_modules package-lock.json
  npm install
  npm run build
}

WAM_APPLICATIONS_DIR = "${libdir}/wam_apps"

do_install() {
  install -d ${D}${WAM_APPLICATIONS_DIR}/${PN}
  cp -R --no-dereference --preserve=mode,links ${S}/dist/* ${D}${WAM_APPLICATIONS_DIR}/${PN}
}

FILES:${PN} = "${WAM_APPLICATIONS_DIR}/${PN}"

RCONFLICTS:${PN} = "launcher flutter-homescreen"
RDEPENDS:${PN} = "applaunchd"
