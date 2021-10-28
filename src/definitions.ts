import { PluginListenerHandle } from "@capacitor/core";

export interface IIsProcessAlive {
  status: boolean;
}

export interface IStartOptions {
  interval: number;
  title: string;
  description: string;
}

export enum ERROR_MESAGES {
  SOME_THING_WENT_WRONG = "SOME_THING_WENT_WRONG",
  SERVICE_IS_RUNNING_ALREADY = "SERVICE_IS_RUNNING_ALREADY",
  INCORRECT_INTERVAL_VALUE = "INCORRECT_INTERVAL_VALUE",
  COMING_SOON_NOT_IMPLEMENTED_YET = "COMING_SOON_NOT_IMPLEMENTED_YET"
}

export interface BackgroundIntervalProcessPlugin {
  isProcessAlive(): Promise<IIsProcessAlive>;
  startProcess(option: IStartOptions): Promise<void>;
  terminateProcess(): Promise<void>;
  addListener(eventName: 'DOIT', callback: () => void): Promise<PluginListenerHandle> & PluginListenerHandle;
  removeAllListeners(): Promise<void>;
}
