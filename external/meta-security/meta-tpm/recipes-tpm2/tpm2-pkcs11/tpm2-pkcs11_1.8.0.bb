SUMMARY = "A PKCS#11 interface for TPM2 hardware"
DESCRIPTION = "PKCS #11 is a Public-Key Cryptography Standard that defines a standard method to access cryptographic services from tokens/ devices such as hardware security modules (HSM), smart cards, etc. In this project we intend to use a TPM2 device as the cryptographic token."
SECTION = "security/tpm"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0fc19f620a102768d6dbd1e7166e78ab"

DEPENDS = "autoconf-archive pkgconfig sqlite3 openssl libtss2-dev tpm2-tools libyaml p11-kit python3-setuptools-native"

SRC_URI = "https://github.com/tpm2-software/${BPN}/releases/download/${PV}/${BPN}-${PV}.tar.gz"

SRC_URI[sha256sum] = "79f28899047defd6b4b72b7268dd56abf27774954022315f818c239af33e05bd"

inherit autotools-brokensep pkgconfig python3native

EXTRA_OECONF += "--disable-ptool-checks"

do_configure:prepend() {
    # do not extract the version number from git
    sed -i -e 's/m4_esyscmd_s(\[git describe --tags --always --dirty\])/${PV}/' ${S}/configure.ac
}

do_compile:append() {
    cd ${S}/tools
    python3 setup.py build
}

do_install:append() {
    install -d ${D}${libdir}/pkcs11
    install -d ${D}${datadir}/p11-kit

    # remove symlinks
    rm -f ${D}${libdir}/pkcs11/libtpm2_pkcs11.so

    #install lib
    install -m 755 ${B}/src/.libs/libtpm2_pkcs11.so ${D}${libdir}/pkcs11/libtpm2_pkcs11.so

    cd ${S}/tools
    export PYTHONPATH="${D}${PYTHON_SITEPACKAGES_DIR}"
    ${PYTHON_PN} setup.py install --root="${D}" --prefix="${prefix}" --install-lib="${PYTHON_SITEPACKAGES_DIR}" --optimize=1 --skip-build

    sed -i -e "s:${PYTHON}:${USRBINPATH}/env ${PYTHON_PN}:g" "${D}${bindir}"/tpm2_ptool
}

PACKAGES =+ "${PN}-tools"

FILES:${PN}-tools = "\
    ${bindir}/tpm2_ptool \
    ${libdir}/${PYTHON_DIR}/* \
    "

FILES:${PN} += "\
    ${libdir}/pkcs11/* \
    ${datadir}/p11-kit/* \
    "

RDEPENDS:${PN} = "p11-kit tpm2-tools "
RDEPENDS:${PN}-tools = "${PYTHON_PN}-pyyaml ${PYTHON_PN}-cryptography ${PYTHON_PN}-pyasn1-modules"
