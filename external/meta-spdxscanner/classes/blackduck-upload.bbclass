# This class upload source code of OSS by Synopsys Detect during the building process.
#
# For more information on Synopsys Detect(CLI):
#   https://synopsys.atlassian.net/wiki/
#
# For more information on SPDX:
#   http://www.spdx.org
#
# Note:
HOSTTOOLS += "java"

#SPDXEPENDENCY += "synopsys-native:do_populate_sysroot"

COPYLEFT_RECIPE_TYPES ?= 'target nativesdk'
inherit copyleft_filter

inherit spdx-common 

do_upload[dirs] = "${SPDX_TOPDIR}"
do_bd_upload[network] = "1"

WAIT_TIME ?= "20"

python () {

    #If not for target, won't creat spdx.
    if bb.data.inherits_class('nopackages', d):
        return

    pn = d.getVar('PN')
    assume_provided = (d.getVar("ASSUME_PROVIDED") or "").split()
    if pn in assume_provided:
        for p in d.getVar("PROVIDES").split():
            if p != pn:
                pn = p
                break

    # glibc-locale: do_fetch, do_unpack and do_patch tasks have been deleted,
    # so avoid archiving source here.
    if pn.startswith('glibc-locale'):
        return
    if (d.getVar('PN') == "libtool-cross"):
        return
    if (d.getVar('PN') == "libgcc-initial"):
        return
    if (d.getVar('PN') == "shadow-sysroot"):
        return

    # We just archive gcc-source for all the gcc related recipes
    if d.getVar('BPN') in ['gcc', 'libgcc']:
        bb.debug(1, 'spdx: There is bug in scan of %s is, do nothing' % pn)
        return

    temp_dir = os.path.join(d.getVar('WORKDIR'), "temp")

    info = {}
    info['workdir'] = d.getVar('WORKDIR') or ""
    info['pn'] = d.getVar( 'PN') or ""
    info['pv'] = d.getVar( 'PV') or ""

    manifest_dir = d.getVar('SPDX_DEPLOY_DIR') or ""
    if not os.path.exists( manifest_dir ):
        bb.utils.mkdirhier( manifest_dir )

    def hasTask(task):
        return bool(d.getVarFlag(task, "task", False)) and not bool(d.getVarFlag(task, "noexec", False))
    
    if d.getVar('PACKAGES'):
        # Some recipes do not have any packaging tasks
        if hasTask("do_package_write_rpm") or hasTask("do_package_write_ipk") or hasTask("do_package_write_deb"):
            d.appendVarFlag('do_bd_upload', 'depends', ' synopsys-native:do_populate_sysroot')
            d.appendVarFlag('do_bd_upload', 'depends', ' %s:do_spdx_creat_tarball' % pn)
            d.appendVarFlag('do_synopsys_detect', 'depends', ' %s:do_bd_upload' % pn)
            bb.build.addtask('do_bd_upload', 'do_configure', 'do_patch', d)
            bb.build.addtask('do_synopsys_detect', 'do_configure', 'do_bd_upload', d)
}

python do_bd_upload(){
    import logging, shutil,time

    if bb.data.inherits_class('nopackages', d):
        return

    logger = logging.getLogger()
    logger.setLevel(logging.INFO)
    logging.basicConfig(level=logging.INFO)

    info = {}
    info['pn'] = d.getVar( 'PN') or ""
    info['pv'] = d.getVar( 'PV') or ""

    token = d.getVar('TOKEN')
    spdx_outdir = d.getVar('SPDX_OUTDIR')
    bb.note("Begin to upload : " + spdx_outdir)
    upload_oss(d, spdx_outdir)
    time.sleep(int(d.getVar('WAIT_TIME')))
}

def upload_oss(d, filepath):
    import os
    import subprocess
    import fnmatch

    server_url = d.getVar('BD_URI', True) or ""
    if server_url == "":
        bb.note("Please set blackduck URL by setting BD_URI!\n")
        raise OSError(errno.ENOENT, "No setting of BD_URI")

    token = d.getVar('TOKEN', True) or ""
    if token == "":
        bb.note("Please set token of blackduck by setting TOKEN!\n" + srcPath)
        raise OSError(errno.ENOENT, "No setting of TOKEN comes from blackduck server.")
    
    pro_name = d.getVar('PRO_NAME', True) or ""
    bb.note("pro_name = " +  pro_name)
    pro_ver = d.getVar('PRO_VER', True) or ""
    proxy_host = d.getVar('PROXY_HOST', True) or ""
    proxy_port = d.getVar('PROXY_PORT', True) or ""
    proxy_username = d.getVar('PROXY_UN', True) or ""
    proxy_pw = d.getVar('PROXY_PW', True) or ""
    recipesysrootnativedatadir = d.getVar('STAGING_DATADIR_NATIVE')
    synopsys_detect_jar = ''
    info_bd_dir = d.getVar('SPDX_DEPLOY_DIR')

    for file in os.listdir(recipesysrootnativedatadir):
        if fnmatch.fnmatch(file, "synopsys-detect-*.jar"):
            bb.note("Find " + file)
            synopsys_detect_jar = recipesysrootnativedatadir + "/" + file
   
    if synopsys_detect_jar != '':
        bb.note("synopsys_detect_jar = " + synopsys_detect_jar)
    else:
        bb.error("Ther is no synopsys-detect-*.jar file.")

    synopsys_detect_cmd = "java -jar " + synopsys_detect_jar \
                   + " --blackduck.url=" + server_url  \
                   + " --blackduck.api.token=" + token  \
                   + " --detect.source.path=" + filepath \
                   + " --blackduck.trust.cert=true "\
                   + " --detect.notices.report.path=" + info_bd_dir \
    
    if pro_name != '':
        synopsys_detect_cmd = synopsys_detect_cmd + " --detect.project.name=" + pro_name
    if pro_ver != '':
        synopsys_detect_cmd = synopsys_detect_cmd + " --detect.project.version.name=" + pro_ver
    if proxy_host!= '':
        synopsys_detect_cmd = synopsys_detect_cmd + " --blackduck.proxy.host=" + proxy_host
    if proxy_port != '':
        synopsys_detect_cmd = synopsys_detect_cmd + " --blackduck.proxy.port=" + proxy_port
    if proxy_username != '':
        synopsys_detect_cmd = synopsys_detect_cmd + " --blackduck.proxy.username=" + proxy_username
    if proxy_pw != '':
        synopsys_detect_cmd = synopsys_detect_cmd + " --blackduck.proxy.password=" + proxy_pw

    bb.note("Invoke synopsys_detect_cmd = " + synopsys_detect_cmd)
    try:
        rst = subprocess.check_output(synopsys_detect_cmd, stderr=subprocess.STDOUT, shell=True)
    except subprocess.CalledProcessError as e:
        bb.error(d.getVar('PN', True) + ": Upload to BD fail: \n%s" % e.output.decode("utf-8"))
        return False
    
    bb.note(str(rst, encoding = "utf-8"))

SSTATETASKS += "do_synopsys_detect"
python do_synopsys_detect_setscene () {
    sstate_setscene(d)
}
addtask do_synopsys_detect_setscene
do_synopsys_detect () {
    echo "Upload OSS to blackduck server."
}
addtask do_spdx_creat_tarball after do_patch
addtask do_bd_upload after do_patch
addtask do_synopsys_detect
do_build[recrdeptask] += "do_synopsys_detect"
do_populate_sdk[recrdeptask] += "do_synopsys_detect"

