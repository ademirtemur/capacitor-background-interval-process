#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(BackgroundIntervalProcessPlugin, "BackgroundIntervalProcess",
           CAP_PLUGIN_METHOD(isProcessAlive, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(startProcess, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(terminateProcess, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(removeAllListeners, CAPPluginReturnNone);
           
)
