import collection.mutable.Stack
import org.scalatest._

import net.kgtkr.twitter_tools._

class ConfigSpec extends FlatSpec with Matchers {

  "parseConfig" should "正常にパース出来るか" in {
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
                          |      discord_hook_url: https://""".stripMargin
    val expectResult = Config(
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

    Config.parseConfig(configString) should be(Some(expectResult))
  }
}
