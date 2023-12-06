SUMMARY = "A library to handle automated deprecations"
HOMEPAGE = "https://github.com/briancurtin/deprecation"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

PYPI_PACKAGE = "deprecation"

SRC_URI[sha256sum] = "72b3bde64e5d778694b0cf68178aed03d15e15477116add3fb773e581f9518ff"

inherit pypi setuptools3

BBCLASSEXTEND += "native nativesdk"
