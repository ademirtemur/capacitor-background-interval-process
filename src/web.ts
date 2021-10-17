import { WebPlugin } from '@capacitor/core';

import type { BackgroundIntervalProcessPlugin } from './definitions';

export class BackgroundIntervalProcessWeb
  extends WebPlugin
  implements BackgroundIntervalProcessPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
