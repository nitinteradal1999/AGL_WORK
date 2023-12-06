require chromium.inc

SRC_URI = "\
    git://github.com/igalia/chromium91;branch=@17.agl;protocol=https;rev=${SRCREV_chromium91};name=chromium91 \
    git://github.com/webosose/chromium-v8;branch=@chromium91;destsuffix=git/src/v8;rev=${SRCREV_v8};name=v8;protocol=https \
"

# Needed by catapult
DEPENDS += "python-six-native python-beautifulsoup4-native python-lxml-native python-html5lib-native python-webencodings-native"

SRCREV_chromium91 = "40fa88380696f62a3644efa6ae4fafda402698f7"
SRCREV_v8 = "b958ec542dcb086f422a1216a959be38a4cc0339"

CHROMIUM_VERSION = "91.0.4472.114"
PV_BRANCH_SUFFIX = "ose17.agl"

PV = "${CHROMIUM_VERSION}.${PV_BRANCH_SUFFIX}+git"

GN_ARGS += "use_gtk=false"

# Disable closure compile
# Else we need HOSTTOOLS += "java"
GN_ARGS += " \
  enable_mojom_closure_compile=false\
  enable_js_type_check=false\
"

# When using meta-clang, one can switch to using the lld linker
# by using the ld-is-lld distro feature otherwise use gold linker
GN_ARGS += "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-lld', 'use_lld=true use_gold=false', bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', 'use_lld=false use_gold=true', 'use_lld=false use_gold=false', d), d)}"

# Toolchains we will use for the build. We need to point to the toolchain file
# we've created, set the right target architecture and make sure we are not
# using Chromium's toolchain (bundled clang, bundled binutils etc).
GN_ARGS += "\
    host_toolchain=\"//build/toolchain/cros:host\" \
    cros_host_is_clang=false \
    use_custom_libcxx_for_host=false \
    cros_host_ar=\"${BUILD_AR}\" \
    cros_host_cc=\"${BUILD_CC} ${BUILD_CFLAGS}\" \
    cros_host_cxx=\"${BUILD_CXX} ${BUILD_CXXFLAGS}\" \
    cros_host_extra_ldflags=\"${BUILD_LDFLAGS}\" \
    custom_toolchain=\"//build/toolchain/cros:target\" \
    is_clang=false \
    use_custom_libcxx=false \
    target_cpu=\"${@gn_arch_name('${TUNE_ARCH}')}\" \
    cros_target_ar=\"${AR}\" \
    cros_target_cc=\"${CC}\" \
    cros_target_cxx=\"${CXX}\" \
    cros_target_extra_ldflags=\"${LDFLAGS}\" \
    v8_snapshot_toolchain=\"//build/toolchain/cros:v8_snapshot\" \
    cros_v8_snapshot_is_clang=false \
    cros_v8_snapshot_ar=\"${BUILD_AR}\" \
    cros_v8_snapshot_cc=\"${BUILD_CC}\" \
    cros_v8_snapshot_cxx=\"${BUILD_CXX}\" \
    gold_path=\"\" \
    v8_enable_embedded_builtins=false \
    use_v8_context_snapshot=false \
"

GN_ARGS:append = " \
  ozone_platform_wayland_external=false \
  ozone_platform_wayland=true \
  use_system_libwayland=true \
  use_system_wayland_scanner=false \
  use_system_minigbm=false \
  use_wayland_gbm=false \
"

GN_ARGS:append = " \
  is_webos=false \
  is_agl=true \
  grpc_use_static_linking=true \
"

# TODO: drop this after we migrate to ubuntu 16.04 or above
GN_ARGS += "\
    fatal_linker_warnings=false\
"

# TODO(rzanoni) copied from original recipe to fix qemux86 build.
# check if it can be removed in the future.
PACKAGECONFIG:remove:qemux86 = "gstreamer umediaserver neva-media gav neva-webrtc"
#END TODO
