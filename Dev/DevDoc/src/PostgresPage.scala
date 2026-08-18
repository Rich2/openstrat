/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pweb.*, WebExts.*, osweb.*, wcode.*

object PostgresPage extends DevPageBase
{ override def titleStr: String = "Postgresql for absolute beginners"
  override def fileStemStr: String = "postgres"

  override def body: BodyHtml = BodyHtml("Postgresql for beginners".h1, central, jsScriptStd)

  def central: DivHtml = DivHtml.classAtt("central", pUpdaters, steps)

  /** Initial value for username. */
  val userName1: String = "tommy"

  /** Updater for username. */
  val uNameUp: UpdaterInputStr = UpdaterInputStr("uName", userName1)

  /** [[UpdaterInputStr]] and it's label for username. */
  val uNameLTI: LabelInput = LabelInput("User Name", uNameUp)
  
  def pUpdaters: PHtml = PHtml(updaterExplain, LabelInputsLine(uNameLTI, opSysLI))

  def steps: OlLarge = OlLarge(s1, s2)

  val postgresPsqlPrompt: PsqlPromptSpan = PsqlPromptSpan("postgres=#")
  def userPsqlPrompt: PsqlPromptSpan = PsqlPromptSpan.listenStrText(uNameUp){ uName => uName + "=#"}
  def userLine(cmdStr: String): PsqlLine = PsqlLine(userPsqlPrompt, cmdStr)

  val s1: LiHtml = LiHtml("Install and main user.".h2,
    DivHtml.listenOptHtml(opSysInput){
      case UbuntuDeriv => RArr(BashLine("sudo apt install postgresql postgresql-contrib"))
      case ArchDeriv => RArr(BashLine("sudo pacman -S postgresql"))
      case _ => RArr("No code available for installation on this operating system")
    },
    "Change the postgres user password.",
    BashLine("sudo passwd postgres"),
    "Depending on your use case you may wish to manipulate Postgresql with a different user.",
    BashLine("su postgres"),
    BashLine("psql"),
    PsqlLine.listenStrHtml(uNameUp){ uName => RArr(postgresPsqlPrompt, s"CREATE USER $uName WITH SUPERUSER;") },
    "You may want to create a database with this user's name",
    PsqlLine.listenStrHtml(uNameUp){ uName => RArr(postgresPsqlPrompt, s"CREATE DATABASE $uName OWNER $uName;") },
    "To quit psql",
    PsqlLine(postgresPsqlPrompt, """\q"""),
    "Switch back to your main user.",
    BashLine.listenStrText(uNameUp)(uName => s"su $uName"),
  )

  val uNameRegexStr: String = RegLogForm.uNameRegexStr().enquote1
  
  val s2: LiHtml = LiHtml(
    DivHtml("If Postgresql is not enabled then you may need to start it on startup."),
    BashLine("sudo systemctl status postgresql"),
    BashLine("sudo systemctl start postgresql"),
    DivHtml.listenStrText(uNameUp){ uName => s"Enter psql again as user $uName" },
    BashLine("psql"),
    DivHtml("At some point you may get;"),
    PsqlLine.listenStrText(uNameUp){ uName => """database "$uName" has a collation version mismatch""" },
    DivHtml("then enter"),
    userLine("ALTER DATABASE template1 REFRESH COLLATION VERSION;"),
    DivHtml("To creat table with a good secure key."),
    userLine("CREATE TABLE users ( did uuid DEFAULT gen_random_uuid(), PRIMARY KEY (did));"),
    "To display table.",
    userLine("SELECT * FROM users;"),
    "To add username",
    userLine(s"""ALTER TABLE users ADD username VARCHAR(15) CHECK(~ $uNameRegexStr) UNIQUE NOT NULL;"""),
    userLine("CREATE UNIQUE INDEX usernameLower ON users(lower(username));")
  )
}