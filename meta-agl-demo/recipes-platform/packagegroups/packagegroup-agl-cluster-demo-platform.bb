SUMMARY = "The software for demo platform of AGL cluster profile"
DESCRIPTION = "A set of packages belong to AGL Cluster Demo Platform"

LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-cluster-demo-platform \
    "

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} += "\
    packagegroup-agl-ttf-fonts \
    packagegroup-agl-source-han-sans-ttf-fonts \
    packagegroup-agl-networking \
    "

AGL_APPS = " \
    cluster-dashboard \
    cluster-receiver \
    qt-cluster-receiver \
    "

RDEPENDS:${PN}:append = " \
    can-utils \
    ${AGL_APPS} \
"
