package net.kgtkr.twitter_tools;

final case class Consumer(ck: String, cs: String);
final case class Token(tk: String, ts: String, ck: String, cs: String);
final case class FFMonitoring(
    interval: Int,
    accounts: List[FFMonitoringAccount]
)
final case class FFMonitoringAccount(token: Token, discordHookUrl: String);
final case class Config(
    consumers: List[Consumer],
    tokens: List[Token],
    ffMonitoring: FFMonitoring
);

object Config {
  def parseConfig(s: String): Option[Config] = {
    import io.circe.yaml.parser
    import io.circe.generic.auto._
    import io.circe._

    implicit lazy val configDecoder: Decoder[Config] =
      Decoder.forProduct3("consumers", "tokens", "ff_monitoring")(Config.apply)

    implicit lazy val ffMonitoringAccountDecoder: Decoder[FFMonitoringAccount] =
      Decoder.forProduct2("token", "discord_hook_url")(
        FFMonitoringAccount.apply
      )

    parser.parse(s).flatMap(_.as[Config]).toOption
  }
}
