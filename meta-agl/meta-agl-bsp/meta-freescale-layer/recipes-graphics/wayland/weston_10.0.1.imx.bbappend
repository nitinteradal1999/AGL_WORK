# Work around PACKAGECONFIG:remove of "x11 wayland" added to the recipe
# in meta-freescale commit 5a5c5dd.  This can be removed once the issue
# has been resolved by a revert of that breakage upstream.
# What is done below is effectively a disabling of the "wayland"
# PACKAGECONFIG option and then open coding what its effects would
# normally be, since the :remove prevents specifying it in the usual way.
PACKAGECONFIG[wayland] = ""
DEPENDS:append = " virtual/egl virtual/libgles2"
PACKAGECONFIG_CONFARGS:append = " -Dbackend-wayland=true"

