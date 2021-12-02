import Foundation
import Capacitor
import UIKit
import UserNotifications
import BackgroundTasks
import UIKit

@objc(BackgroundIntervalProcessPlugin)
public class BackgroundIntervalProcessPlugin: CAPPlugin, UNUserNotificationCenterDelegate {
    private final let SMTH_WENT_WRONG: String = "SOME_THING_WENT_WRONG";
    private final let EVENT: String = "DOIT";
    private final let center = UNUserNotificationCenter.current();
    private final var timer: Timer = Timer();
    private final var isRunning: Bool = false;
    private final var workItem: DispatchWorkItem! = nil;
    
    @objc public override func load() {
        self.center.delegate = self;
    
        self.center.requestAuthorization(options: [.alert, .badge, .sound]) { (granted, error) in
            if granted {
                print("Yay!")
            } else {
                print("D'oh")
            }
        };
        
        print("LOAD FUNC");
    }
    
    @objc func showNotification(title: String, description: String) {
        self.center.delegate = self;

        let content = UNMutableNotificationContent();
        content.title = title;
        content.subtitle = "";
        content.body = description;
        content.categoryIdentifier = "category-1";
                
        let trigger = UNTimeIntervalNotificationTrigger(timeInterval: 7.0, repeats: false);
                
        let request = UNNotificationRequest(identifier: "identifier-3", content: content, trigger: trigger );
                
        self.center.add(request, withCompletionHandler: nil);
    }
    
    @objc func isProcessAlive(_ call: CAPPluginCall){
        call.resolve(["status": self.isRunning]);
    }
    
    @objc func abc(interval: Int){
        self.isRunning = true;
        var dispatchAfter = DispatchTimeInterval.seconds(interval);
        
        self.workItem = DispatchWorkItem {
            self.notifyListeners(self.EVENT, data: [:]);
            self.abc(interval: interval)
        }
            
        DispatchQueue.global(qos: .background).asyncAfter(deadline: .now() + dispatchAfter, execute: self.workItem );
        // self.workItem.cancel()
    }
    
    @objc func startProcess(_ call: CAPPluginCall){
        let interval: Int = call.getInt("interval", 5000);
        let title: String = call.getString("title", "Service is Running");
        let description: String = call.getString("description", "Listening for Screen Off/On events");
        
        let sn = Double(interval) / Double(1000);
        
        self.abc(interval: Int.init(sn));
        
        call.resolve([:]);
    }
    
    @objc func terminateProcess(_ call: CAPPluginCall){
        do{
            if (self.workItem != nil) {
                self.workItem.cancel();
            }
            
            call.resolve([:])
        }catch{
            call.reject(self.SMTH_WENT_WRONG);
        }
    }
}
