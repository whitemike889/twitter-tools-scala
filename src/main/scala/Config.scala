final case class Consumer(ckg: String, cs: String);
final case class Token(ck: String, cs: String, tk: String, ts: String);
final case class FfMonitoring(
    interval: Int,
    accounts: List[FfMonitoringAccount]
)
final case class FfMonitoringAccount(token: Token, discordHookUrl: String);
final case class Config(
    consumers: List[Consumer],
    tokens: List[Token],
    ffMonitoring: FfMonitoring
);
