import { registerPlugin } from '@capacitor/core';

import type { BackgroundIntervalProcessPlugin } from './definitions';

const BackgroundIntervalProcess = registerPlugin<BackgroundIntervalProcessPlugin>(
  'BackgroundIntervalProcess'
);

export * from './definitions';
export { BackgroundIntervalProcess };
