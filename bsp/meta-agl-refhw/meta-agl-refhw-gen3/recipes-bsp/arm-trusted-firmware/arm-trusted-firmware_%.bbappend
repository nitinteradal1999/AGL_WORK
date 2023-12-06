FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:rcar-gen3 = " file://0001-rcar_gen3-plat-Do-not-panic-on-unrecognized-boards.patch"

# It is believed the eMMC configuration below makes the result AGL
# reference hardware specific, mark it as such when building with
# MACHINE=agl-refhw-h3 to potentially flag this in an incorrectly
# configured build.
COMPATIBLE_MACHINE:agl-refhw-h3 = "agl-refhw-h3"

# There are hardware issues in using hyperflash. arm-trusted-firmware, optee and
# u-boot have to be stored into eMMC by using serial donwload.
EMMC_BOOT_OPTION = "RCAR_SA6_TYPE=1"

# RCAR_DRAM_MEMRANK must be set, since in case of RCAR_DRAM_LPDDR4_MEMCONF=1
# ddr_rank_judge is called. But for RCAR_DRAM_MEMRANK=0 it can determine values
# only for Salvator XS and Starter Kit Pre.
#
# RCAR_DRAM_MEMRANK=2 is set because for ATF from BSP v4.7.0:
#
# https://github.com/renesas-rcar/arm-trusted-firmware/tree/af9f429a48b438e314289f17947ad5d8036f398e
#
# _board_judge returns hardcoded 'brd = 8;  /*  8Gbit/2rank */' by default.
#
DDR_8G_OPTION = "RCAR_DRAM_LPDDR4_MEMCONF=1 RCAR_DRAM_MEMRANK=2"

# AGL reference hardware numbered 100 or above has 16Gbit/1rank DRAM
# and please flash the firmware built with the following options.
DDR_8G_1RANK_OPTION = "RCAR_DRAM_LPDDR4_MEMCONF=1 RCAR_DRAM_MEMRANK=1"

ATFW_OPT:agl-refhw-h3 = "LSI=H3 RCAR_DRAM_SPLIT=1 ${EMMC_BOOT_OPTION} ${DDR_8G_OPTION}"

#
# Handle building as extra firmware with MACHINE=h3ulcb
#

# Build options for building as extra firmware when MACHINE=h3ulcb, based on H3[4x2g]
H3ULCB[agl-refhw-4x2g] = "LSI=H3 RCAR_DRAM_SPLIT=1 ${EMMC_BOOT_OPTION} ${DDR_8G_OPTION}"
H3ULCB[agl-refhw-4x2g-1rank] = "LSI=H3 RCAR_DRAM_SPLIT=1 ${EMMC_BOOT_OPTION} ${DDR_8G_1RANK_OPTION}"

python __anonymous() {
    # If building for MACHINE=agl-refhw-h3, the predefined default
    # extra configurations in the base recipe are not interesting
    # to us, as there is no compatible hardware.
    if d.getVar("MACHINE") == "agl-refhw-h3":
        d.delVarFlag("H3", "2x2g")
        d.delVarFlag("H3", "4x2g")
}

# Also build the extra 1rank firmware when MACHINE=agl-refhw-h3
H3[4x2g-1rank] = "LSI=H3 RCAR_DRAM_SPLIT=1 ${EMMC_BOOT_OPTION} ${DDR_8G_1RANK_OPTION}"

# Boot Normal World in EL2: this define configures ATF (SPSR register) to boot
# BL33 in EL2.
EXTRA_OEMAKE += " RCAR_BL33_EXECUTION_EL=1"

#
# Need to prepend the compile tasks with a distclean that actually cleans
# out all the platform specific files like the new rcar_layout_tool output,
# otherwise they do not get rebuilt and board builds can get the wrong
# version of e.g. cert_headers_sa6.  This needs to be addressed with
# upstream.
#

do_compile:prepend() {
    oe_runmake distclean PLAT=${PLATFORM} MBEDTLS_COMMON_MK=1 ${ATFW_OPT}
}

do_ipl_opt_compile:prepend() {
    oe_runmake distclean PLAT=${PLATFORM} MBEDTLS_COMMON_MK=1 ${ATFW_OPT}
}

do_extra_ipl_opt_refhw_fixup() {
    # Rename agl-refhw-h3 firmware files to drop h3ulcb-
    for f in ${DEPLOYDIR}/*-h3ulcb-agl-refhw-4x2g*; do
        n=`basename $f | sed 's/h3ulcb-//'`
        mv -f $f ${DEPLOYDIR}/$n
    done
}

EXTRA_IPL_OPT_POSTFUNCS = ""
EXTRA_IPL_OPT_POSTFUNCS_h3ulcb = "do_extra_ipl_opt_refhw_fixup"
do_extra_ipl_opt[postfuncs] += "${EXTRA_IPL_OPT_POSTFUNCS}"
