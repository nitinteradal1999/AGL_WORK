SUMMARY = "PDF parser and analyzer"
HOMEPAGE = "https://github.com/pdfminer/pdfminer.six"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=76a979b092386b9cfa8791fa22cbe404"

DEPENDS += "\
            python3-chardet-native \
            python3-cryptography-native \
           "

PYPI_PACKAGE = "pdfminer.six"

SRC_URI[sha256sum] = "0351f17d362ee2d48b158be52bcde6576d96460efd038a3e89a043fba6d634d7"

inherit pypi
inherit setuptools3

BBCLASSEXTEND = "native"
