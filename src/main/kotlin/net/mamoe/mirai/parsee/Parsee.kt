package net.mamoe.mirai.parsee

import net.mamoe.mirai.Bot
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.contact.*
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.MemberJoinEvent
import net.mamoe.mirai.join
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.message.nextMessage
import java.lang.Exception
import java.net.URL

@Suppress("UNUSED_VARIABLE")
fun getTime(t: Int): MutableList<Int> {
    val s = t % 60
    val min = ((t - s) % 3600) / 60
    val h = (t - 60 * min - s) / 3600
    val list = mutableListOf<Int>(h, min, s)
    return list
}

fun getLuck(): String {
    val luck = listOf<String>("大吉", "吉", "小吉", "吉大于凶", "中", "凶大于吉", "小凶", "凶", "大凶").shuffled().take(1)
    return luck[0]
}

fun getConversation(): String {
    val conversation = listOf<String>(
            "操纵嫉妒心程度的能力...",
            "帕露帕露是桥姬哦~",
            "在?开花爷爷",
            "少女丑时参拜中...",
            "人类?难道是看上了我们这被诅咒的力量?"
    ).shuffled().take(1);
    return conversation[0]
}

fun getErrorCode(): String {
    val list =
            "a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z".split(
                    "|"
            )
    val sb = StringBuilder()
    for (index in 1..13) {
        sb.append(list.shuffled().take(1)[0])
    }
    return sb.toString()
}

fun getChoice(): Boolean {
    val choice = listOf(true, false).shuffled().take(1)
    return choice[0]
}

fun getPSS(): String {
    val PSS = listOf<String>("剪刀", "石头", "布").shuffled().take(1)
    return PSS[0]
}

suspend fun main() {
    val bot = Bot(
            QQAccount,
            "QQPassword"
    ) {
        fileBasedDeviceInfo("device.json") // 使用 "device.json" 保存设备信息
    }.alsoLogin()

    val sleep = mutableMapOf<String, Long>()
    val history = mutableMapOf<String, String>()
    val choice = mutableMapOf<String, Boolean>()
    bot.subscribeFriendMessages {
        startsWith(".暗改",removePrefix = true) {
            if (sender.id == 1791355024L || sender.id==1391704275L) {
                val lst = it.split(";")
                try {
                    history[lst[0]] = lst[1]
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                reply("暗改成功\n`${lst[0]}`\n的算卦结果是\n`${lst[1]}`")
            }else{
                reply("你暗改你马呢")
            }
        }
    }
    bot.subscribeGroupMessages {
        startsWith(".猜拳", removePrefix = true) {
            if (it != "石头" && it != "剪刀" && it != "布") {
                reply("玩`石头剪刀布`你不出这三个你玩你马呢?")
            }
            val pChoice = getPSS()
            if (pChoice == it) reply(At(sender as Member) + "\n你猜的:${it}\n帕露的:${pChoice}\n结果:平局")
            else if ((pChoice == "剪刀" && it == "布") || (pChoice == "布" && it == "石头") || (pChoice == "石头" && it == "剪刀")) reply(At(sender as Member) + "\n你猜的:${it}\n帕露的:${pChoice}\n结果:帕露帕露赢了")
            else if ((pChoice == "布" && it == "剪刀") || (pChoice == "石头" && it == "布") || (pChoice == "剪刀" && it == "石头")) reply(At(sender as Member) + "\n你猜的:${it}\n帕露的:${pChoice}\n结果:帕露帕露输了")
        }
        startsWith(".抽取",removePrefix = true){
            val rollLst= it.split(";")
            reply(At(sender as Member)+"\n结果:${rollLst.shuffled().take(1)[0].toString()}")
        }
        case(".stg"){
            val stgLst= listOf<String>(
                    "th06东方红魔乡",
                    "th07东方妖妖梦",
                    "th08东方永夜抄",
                    "th10东方风神录",
                    "th11东方地灵殿",
                    "th12东方星莲船",
                    "th13东方神灵庙",
                    "th14东方辉针城",
                    "th15东方绀珠传",
                    "th16东方天空璋",
                    "th17东方鬼形兽"
            )
            val difficultyLst= listOf<String>("EASY","NORMAL","HARD","LUNATIC")
            reply(At(sender as Member)+"\n作品:${stgLst.shuffled().take(1)[0]}\n难度:${difficultyLst.shuffled().take(1)[0]}")
        }
        (case("完全销毁") or case("一键删除")){
            reply("帕露西---满身疮痍")
            System.exit(0)
        }
        startsWith("复读 ", removePrefix = true) {
            reply(it)
        }
        contains("我爱你") {
            reply("不要男妈妈要紫妈妈${getErrorCode()}")
        }
        startsWith(".算卦 ", removePrefix = true) {
            if (it in history.keys) {
                reply(At(sender as Member) + "\n事件:${it}\n结果:${history.get(it)}")
            } else {
                val luck = getLuck()
                reply(At(sender as Member) + "\n事件:${it}\n结果:${luck}")
                history.put(it, luck)
            }
        }
        startsWith(".抉择 ", removePrefix = true) {
            if (it in choice.keys) {
                if (choice.get(it)!!) reply(At(sender as Member) + "\n事件:${it}\n抉择:Yes")
                else reply(At(sender as Member) + "\n事件:${it}\n抉择:No")
            } else {
                val resultBool = getChoice()
                val resultStr: String
                if (resultBool) resultStr = "Yes"
                else resultStr = "No"
                reply(At(sender as Member) + "\n事件:${it}\n抉择:${resultStr}")
                choice.put(it, resultBool)
            }
        }
        case("清空算卦历史") {
            history.keys.forEach() {
                history.remove(it)
            }
            reply("算卦历史已清空帕露帕露~")
        }
        contains("机器人") {
            reply("帕露帕露才不是机器人")
            sendImage(URL("http://175.24.40.13/thPics/19.png"))
        }

        case("运势") {
            reply(At(sender as Member) + "\n你今天的运势是我爱你")
        }
        case("睡觉") {
            if (senderName in sleep) {
                reply(At(sender as Member) + "\n你还没起床呢~先对帕露西说\"起床\"吧~")
            } else {
                reply(At(sender as Member) + "\n帕露晚安~")
                sleep[senderName] = System.currentTimeMillis() / 1000
            }
        }
        case("起床") {
            if (senderName in sleep) {
                val sleepTime = sleep.remove(senderName);
                val nowTime = System.currentTimeMillis() / 1000
                if (sleepTime != null) {
                    val sleepFor = (nowTime - sleepTime).toInt()
                    val t = getTime(sleepFor)
                    reply(At(sender as Member) + "\n帕露早安~\n你睡了${t[0]}小时${t[1]}分钟${t[2]}秒~")
                    if (t[0] >= 6) {
                        reply("真是美好的一觉啊帕露帕露")
                    } else if (t[0] == 0 && t[1] <= 30) {
                        reply("刚睡觉就起床你睡你马呢?")
                    } else {
                        reply("还要更多睡觉哦帕露帕露")
                    }
                } else {
                    reply(At(sender as Member) + "\n你还没睡觉呢~先对帕露西说\"睡觉\"吧~")
                }
            } else {
                reply(At(sender as Member) + "\n你还没睡觉呢~先对帕露西说\"睡觉\"吧~")
            }
        }
        case("睡觉榜") {
            val sb = StringBuilder("睡觉榜\n")
            val now = (System.currentTimeMillis() / 1000).toInt()
            sleep.forEach {
                val time = getTime(now - it.value.toInt())
                sb.append("${it.key}\t${time[0]}时${time[1]}分${time[2]}秒\n")
            }
            reply(sb.toString())
        }
        Regex(".*?") matchingReply {
            if (senderName in sleep.keys) {
                quoteReply("睡觉了之后就不要水群了哦帕露帕露~")
            }
        }
        atBot {
            reply(getConversation())
        }
        contains("author") {
            reply("帕露帕露\n作者:Su1kaYCP")
        }
        (contains("早安") or contains("早上好") or contains("早")){
            reply(At(sender as Member) + "\n早上好~帕露帕露")
            if (senderName in sleep) {
                reply("对帕露帕露说\"起床\"结束睡眠计时吧~")
            }
        }
        (contains("晚安") or contains("睡觉了") or contains("睡了")){
            reply(At(sender as Member) + "\n晚安~帕露帕露")
            if (senderName in sleep) {
            } else {
                reply("对帕露帕露说\"睡觉\"开始睡眠计时吧~")
            }
        }
    }
    bot.subscribeAlways<MemberJoinEvent> {
        it.group.sendMessage(PlainText("欢迎${it.member.nameCardOrNick}成为桃子的给粉帕露帕露"))
    }
    bot.join()//等到直到断开连接
}