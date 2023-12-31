DESCRIPTION =  "Kernel drivers for the PowerVR SGX chipset found in the TI SoCs"
HOMEPAGE = "https://git.ti.com/graphics/omap5-sgx-ddk-linux"
LICENSE = "MIT | GPL-2.0-only"
LIC_FILES_CHKSUM = "file://eurasia_km/README;beginline=13;endline=22;md5=74506d9b8e5edbce66c2747c50fcef12"

inherit module

PROVIDES = "virtual/gpudriver"

COMPATIBLE_MACHINE = "ti33x|ti43x|omap-a15|am65xx"

MACHINE_KERNEL_PR:append = "x"
PR = "${MACHINE_KERNEL_PR}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "virtual/kernel"

BRANCH = "ti-img-sgx/${PV}/k5.10"

SRC_URI = "git://git.ti.com/git/graphics/omap5-sgx-ddk-linux.git;protocol=https;branch=${BRANCH}"

S = "${WORKDIR}/git"

SRCREV = "eda7780bfd5277e16913c9bc0b0e6892b4e79063"

TARGET_PRODUCT:omap-a15 = "jacinto6evm"
TARGET_PRODUCT:ti33x = "ti335x"
TARGET_PRODUCT:ti43x = "ti437x"
TARGET_PRODUCT:am65xx = "ti654x"
PVR_BUILD = "release"
PVR_WS = "nulldrmws"

EXTRA_OEMAKE += 'KERNELDIR="${STAGING_KERNEL_DIR}" BUILD=${PVR_BUILD} TARGET_PRODUCT=${TARGET_PRODUCT} WINDOW_SYSTEM=${PVR_WS}'

do_compile:prepend() {
    cd ${S}/eurasia_km/eurasiacon/build/linux2/omap_linux
}

do_install() {
    make -C ${STAGING_KERNEL_DIR} M=${B}/eurasia_km/eurasiacon/binary_omap_linux_${PVR_WS}_${PVR_BUILD}/target_armhf/kbuild INSTALL_MOD_PATH=${D}${root_prefix} PREFIX=${STAGING_DIR_HOST} modules_install
}

do_install:am65xx() {
    make -C ${STAGING_KERNEL_DIR} M=${B}/eurasia_km/eurasiacon/binary_omap_linux_${PVR_WS}_${PVR_BUILD}/target_aarch64/kbuild INSTALL_MOD_PATH=${D}${root_prefix} PREFIX=${STAGING_DIR_HOST} modules_install
}

RRECOMMENDS:${PN} += "ti-sgx-ddk-um"
