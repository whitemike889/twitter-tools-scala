package net.kgtkr.twitter_tools.test.domain.models;

import collection.mutable.Stack
import org.scalatest._

import net.kgtkr.twitter_tools.domain.models._

object ConfigFixture {
  val configString = """|consumers:
                        |  - &cs_hoge
                        |    ck: xxx
                        |    cs: yyy
                        |tokens:
                        |  - &tk_foo
                        |    tk: aaa
                        |    ts: bbb
                        |    <<: *cs_hoge
                        |ff_monitoring:
                        |  interval: 3600
                        |  accounts:
                        |    - token: *tk_foo
                        |      discord_hook_url: https://
                        |""".stripMargin;

  val config = Config(
    consumers = List(Consumer(ck = "xxx", cs = "yyy")),
    tokens = List(Token(tk = "aaa", ts = "bbb", ck = "xxx", cs = "yyy")),
    ffMonitoring = FFMonitoring(
      interval = 3600,
      accounts = List(
        FFMonitoringAccount(
          token = Token(tk = "aaa", ts = "bbb", ck = "xxx", cs = "yyy"),
          discordHookUrl = "https://"
        )
      )
    )
  )
}

class ConfigSpec extends FlatSpec with Matchers {
  "parseConfig" should "正常にパース出来るか" in {

    Config.parse(ConfigFixture.configString) should be(
      Some(ConfigFixture.config)
    )
  }
}
