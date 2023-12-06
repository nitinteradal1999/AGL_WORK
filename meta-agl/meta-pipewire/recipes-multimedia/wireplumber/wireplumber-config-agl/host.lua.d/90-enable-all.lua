-- Provide the "default" pw_metadata, which stores
-- dynamic properties of pipewire objects in RAM
load_module("metadata")

-- Load devices
alsa_monitor.enable()
--v4l2_monitor.enable()

-- Track/store/restore user choices about devices
device_defaults.enable()

-- Automatically suspends idle nodes after 3 seconds
load_script("suspend-node.lua")

-- Automatically sets device profiles to 'On'
load_script("policy-device-profile.lua")

-- Mute ALSA sinks when requested by pipewire-ic-ipc
load_module("mixer-api")
load_script("alsa-suspend.lua")
