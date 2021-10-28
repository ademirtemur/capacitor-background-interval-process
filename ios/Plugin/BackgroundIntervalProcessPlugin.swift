import Foundation
import Capacitor
import UIKit

@objc(BackgroundIntervalProcessPlugin)
public class BackgroundIntervalProcessPlugin: CAPPlugin {
    private final let SMTH_WENT_WRONG: String = "SOME_THING_WENT_WRONG";
    private final let COMING_SOON_NOT_IMPLEMENTED_YET: String = "COMING_SOON_NOT_IMPLEMENTED_YET";
    
    @objc func isProcessAlive(_ call: CAPPluginCall){
        call.reject(self.COMING_SOON_NOT_IMPLEMENTED_YET);
    }
    
    @objc func startProcess(_ call: CAPPluginCall){
        call.reject(self.COMING_SOON_NOT_IMPLEMENTED_YET);
    }
    
    @objc func terminateProcess(_ call: CAPPluginCall){
        call.reject(self.COMING_SOON_NOT_IMPLEMENTED_YET);
    }
}
