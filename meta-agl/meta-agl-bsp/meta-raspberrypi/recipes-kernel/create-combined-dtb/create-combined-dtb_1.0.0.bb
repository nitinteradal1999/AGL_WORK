SUMMARY = "Combine dtb and dtbo"
DESCRIPTION = "Combine specified dtb and one or more dtbo into specified filename found in deploydir"
SECTION = "bootloader"
PR = "r1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

DEPENDS = "dtc-native"

ALLOW_EMPTY:${PN} = "1"
FILES:${PN} = ""

S = "${WORKDIR}"

do_compile[depends] += "virtual/kernel:do_deploy"

do_compile () {
	# Plain VC4 (HDMI)
	if [ -f "${DEPLOY_DIR_IMAGE}/bcm2711-rpi-4-b.dtb" ]; then
		fdtoverlay -v -i ${DEPLOY_DIR_IMAGE}/bcm2711-rpi-4-b.dtb -o bcm2711-rpi-4-b+vc4.dtb \
			${DEPLOY_DIR_IMAGE}/${VC4DTBO}-pi4.dtbo
	fi

	# VC4 + LCD
	if [ -f "${DEPLOY_DIR_IMAGE}/bcm2711-rpi-4-b.dtb" -a -f "${DEPLOY_DIR_IMAGE}/rpi-ft5406.dtbo" ]; then
		fdtoverlay -v -i ${DEPLOY_DIR_IMAGE}/bcm2711-rpi-4-b.dtb -o bcm2711-rpi-4-b+vc4+ft5406.dtb \
			${DEPLOY_DIR_IMAGE}/rpi-ft5406.dtbo \
			${DEPLOY_DIR_IMAGE}/${VC4DTBO}-pi4.dtbo
	fi

}

do_deploy () {
	install -d ${DEPLOY_DIR_IMAGE}
	if [ -f "${S}/bcm2711-rpi-4-b+vc4+ft5406.dtb" ]; then
		install -m 0644 ${S}/bcm2711-rpi-4-b+vc4+ft5406.dtb ${DEPLOY_DIR_IMAGE}
	fi
	if [ -f "${S}/bcm2711-rpi-4-b+vc4.dtb" ]; then
		install -m 0644 ${S}/bcm2711-rpi-4-b+vc4.dtb ${DEPLOY_DIR_IMAGE}
	fi
}

addtask deploy after do_install
