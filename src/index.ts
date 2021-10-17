import { registerPlugin } from '@capacitor/core';

import type { BackgroundIntervalProcessPlugin } from './definitions';

const BackgroundIntervalProcess = registerPlugin<BackgroundIntervalProcessPlugin>(
  'BackgroundIntervalProcess',
  {
    web: () => import('./web').then(m => new m.BackgroundIntervalProcessWeb()),
  },
);

export * from './definitions';
export { BackgroundIntervalProcess };
