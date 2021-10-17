export interface BackgroundIntervalProcessPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
