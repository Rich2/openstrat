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
  val uNameInp: UpdaterInputStr = UpdaterInputStr("uName", userName1)

  /** [[UpdaterInputStr]] and it's label for username. */
  val uNameLI: LabelInput = LabelInput("User Name", uNameInp)
  
  def pUpdaters: PHtml = PHtml(updaterExplain, LabelInputsLine(uNameLI, opSysLI))

  def steps: OlLarge = OlLarge(s1, s2, s3)

  val postgresPsqlPrompt: PsqlPromptSpan = PsqlPromptSpan("postgres=#")
  def userPsqlPrompt: PsqlPromptSpan = PsqlPromptSpan.listenStrText(uNameInp){ uName => uName + "=#"}
  def userLine(cmdStr: String): PsqlLine = PsqlLine(userPsqlPrompt, cmdStr)

  val s1: LiHtml = LiHtml("Install and main user.".h3,
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
    PsqlLine.listenStrHtml(uNameInp){ uName => RArr(postgresPsqlPrompt, s"CREATE USER $uName WITH SUPERUSER;") },
    "You may want to create a database with this user's name",
    PsqlLine.listenStrHtml(uNameInp){ uName => RArr(postgresPsqlPrompt, s"CREATE DATABASE $uName OWNER $uName;") },
    "To quit psql",
    PsqlLine(postgresPsqlPrompt, """\q"""),
    "Switch back to your main user.",
    BashLine.listenStrText(uNameInp)(uName => s"su $uName"),
    DivHtml("If Postgresql is not enabled then you may need to start it on startup."),
    BashLine("sudo systemctl status postgresql"),
    BashLine("sudo systemctl start postgresql"),
    DivHtml.listenStrText(uNameInp){ uName => s"Enter psql again as user $uName" },
    BashLine("psql"),
    DivHtml("At some point you may get;"),
    PsqlLine.listenStrText(uNameInp){ uName => """database "$uName" has a collation version mismatch""" },
    DivHtml("then enter"),
    PsqlLine(userPsqlPrompt, SpanInlineInedit.listenStrText(uNameInp){ uName => s"ALTER DATABASE $uName REFRESH COLLATION VERSION;" }),
  )

  val s2: LiHtml = LiHtml("Before continuing, here are some commands to undo things if necessary.",
    DivHtml("To delete table"),
    PsqlLine(userPsqlPrompt, "DROP TABLE", SpanInlineInedit.pink("tableName"), ";"),
    "To delete all rows",
    PsqlLine(userPsqlPrompt, "TRUNCATE", SpanInlineInedit.pink("tableName"), ";"),
    "To drop constraint",
    PsqlLine(userPsqlPrompt, "ALTER TABLE users DROP CONSTRAINT", SpanInlineInedit.pink("yourConstraint"), ";"),
    "To delete row",
    PsqlLine(userPsqlPrompt, "ALTER TABLE users DROP COLUMN", SpanInlineInedit.pink("columnName"), ";")
  )

  val uNameRegexStr: String = UsernameInput.regexStrStd.enquote1
  val passwordRegexStr: String = PasswordInput.regexStrStd.enquote1
  
  val s3: LiHtml = LiHtml("Create users table".h3,    
    DivHtml("To create table with a good secure key."),
    userLine("CREATE TABLE users ( id uuid DEFAULT uuidv7(), PRIMARY KEY (id));"),
    "To display table.",
    userLine("SELECT * FROM users;"),
    "To add username",
    userLine(s"""ALTER TABLE users ADD username VARCHAR(15) UNIQUE NOT NULL;"""),
    userLine(s"""ALTER TABLE users ADD CONSTRAINT uNameCheck CHECK(username ~ $uNameRegexStr);"""),
    userLine("CREATE UNIQUE INDEX usernameLower ON users(lower(username));"),
    "To add password",
    userLine(s"""ALTER TABLE users ADD password VARCHAR(128) NOT NULL;"""),
    userLine(s"""ALTER TABLE users ADD CONSTRAINT passwordCheck CHECK(password ~ $passwordRegexStr);"""),
    "To add status",
    userLine("CREATE type Status AS ENUM ('User', 'Admin');"),
    userLine(s"""ALTER TABLE users ADD status Status DEFAULT 'User' NOT NULL;"""),
    "To add user",
    PsqlLine(userPsqlPrompt, "INSERT INTO users VALUES(DEFAULT,", SpanInlineInedit.pink("username".enquote1), ",", SpanInlineInedit.pink("password".enquote1),
      """, 'Admin');"""),
    PsqlLine(userPsqlPrompt, "INSERT INTO users VALUES(DEFAULT,", SpanInlineInedit.pink("username".enquote1), ",", SpanInlineInedit.pink("password".enquote1),
      ");"),
  )
}