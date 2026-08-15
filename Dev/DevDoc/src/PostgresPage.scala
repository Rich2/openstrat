/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pweb.*, WebExts.*, osweb.*, wcode.*

object PostgresPage extends DevPageBase
{ override def titleStr: String = "Postgresql for absolute beginners"
  override def fileStemStr: String = "postgres"

  override def body: BodyHtml = BodyHtml("Postgresql for beginners".h1, central, ScriptHtml.jsSrc("postgres.js"), ScriptHtml.main("PostgresPageJs"))

  def central: DivHtml = DivHtml.classAtt("central", pUpdaters, steps)

  /** Initial value for username. */
  val userName1: String = "tommy"

  /** Updater for username. */
  val userNameIUT: UpdaterInputStr = UpdaterInputStr("uName", userName1)

  /** [[UpdaterInputStr]] and it's label for username. */
  val userNameLTI: LabelInput = LabelInput("User Name", userNameIUT)

  def steps: OlLarge = OlLarge(s1)

  def pUpdaters: PHtml = PHtml(updaterExplain, LabelInputsLine(userNameLTI, opSysLI))

  val postgresPrompt = BashPromptSpan("postgres=#")

  val s1: LiHtml = LiHtml("Install and main user.".h2,
    DivHtml.listenOptHtml(opSysInput){
      case UbuntuDeriv => RArr(BashLine("sudo apt install postgresql postgresql-contrib"))
      case ArchDeriv => RArr(BashLine("sudo pacman -S postgresql"))
      case _ => RArr("No code available for installation on this operating syatem")
    },
    "Change the postgres user password.",
    BashLine("sudo password postgres"),
    "Depending on your use case you may wish to manipulate Postgresql with a different user.",
    BashLine("su postgres"),
    BashLine("psql"),
    BashLine.listenStrHtml(userNameIUT){ uName => RArr(postgresPrompt, s"CREATE USER $uName WITH SUPERUSER;") },
    "You may want to create a database with this user's name",
    BashLine.listenStrHtml(userNameIUT){ uName => RArr(postgresPrompt, s"CREATE DATABASE $uName OWNER $uName;") },
    "To quit psql",
    BashLine(postgresPrompt, """\q""")
  )
}