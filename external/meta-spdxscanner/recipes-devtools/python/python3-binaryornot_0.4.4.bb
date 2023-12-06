SUMMARY = "Ultra-lightweight pure Python package to check if a file is binary or text"
HOMEPAGE = "https://github.com/audreyr/binaryornot"

LICENSE = "BSD-3-Clause"
# Doesn't provide a LICENSE in the pypi package
LIC_FILES_CHKSUM = "file://LICENSE;md5=883bfd76e31e48fac50f9ab457d0a131"

DEPENDS += "python3-chardet-native"

PYPI_PACKAGE = "binaryornot"

SRC_URI[sha256sum] = "359501dfc9d40632edc9fac890e19542db1a287bbcfa58175b66658392018061"

inherit pypi
inherit setuptools3

BBCLASSEXTEND = "native"
