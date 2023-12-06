require agl-ivi-demo-platform.bb

SUMMARY = "Cross SDK of demo AGL Distribution for IVI profile"

DESCRIPTION = "SDK image for full AGL Distribution for IVI profile. \
It includes the full toolchain, plus development headers and libraries \
for everything in the demo platform to form a standalone cross SDK."

inherit agl-crosssdk

require agl-ivi-crosssdk.inc

inherit populate_sdk_qt5

# Add qtwaylandscanner to the SDK
TOOLCHAIN_HOST_TASK += "nativesdk-qtwayland-tools"
