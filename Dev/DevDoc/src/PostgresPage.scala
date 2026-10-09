/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pweb.*, WebExts.*, osweb.*, wcode.*

object PostgresPage extends DevPageBase
{ override def titleStr: String = "Postgresql for absolute beginners"
  override def fileStemStr: String = "postgres"

  override def body: BodyHtml = BodyHtml("Postgresql for beginners".h1, central, jsScriptStd)

  def central: DivHtml = CentreBlockAtt.div(pUpdaters, steps)

  /** Initial value for username. */
  val userName1: String = "john"

  /** Updater for username. */
  val uNameInp: UpdaterInputStr = UpdaterInputStr("uName", userName1)

  /** [[UpdaterInputStr]] and it's label for username. */
  val uNameLI: LabelInput = LabelInput("User Name", uNameInp)
  
  val dbName1 = "myfirstdb"
  val dbNameInp = UpdaterInputStr("dbName", dbName1)
  val dbNameLI = LabelInput("Database name", dbNameInp)
  
  def pUpdaters: PHtml = PHtml(updaterExplain, LabelInputsLine(uNameLI, computerNameLI, dbNameLI, opSysLI))

  def steps: OlLarge = OlLarge(s1, s2, s3)

  val postgresPsqlPrompt: PsqlPromptSpan = PsqlPromptSpan("postgres=#")
  def userPsqlPrompt: PsqlPromptSpan = PsqlPromptSpan.listenStrText(dbNameInp){ dbName => dbName + "=#"}
  def userLine(cmdStr: String): PsqlLine = PsqlLine(userPsqlPrompt, cmdStr)
  
  def yourBashPrompt: BashPromptSpan = BashPromptSpan.listen2StrText(uNameInp, computerNameInput) { (uName, cName) => s"$uName@$cName:/" }

  val s1: LiHtml = LiHtml("Install and main user.".h3,
    DivHtml.listenOptHtml(opSysInput){
      case UbuntuDeriv => RArr(BashLine("sudo apt install postgresql postgresql-contrib"))
      case _: ArchDeriv => RArr(
        BashLine("sudo pacman -S postgresql"),
        BashLine("sudo initdb --locale en_GB.UTF-8 -D /var/lib/postgres/data"),
        BashLine("sudo systemctl start postgresql"),
        BashLine("sudo systemctl enable postgresql")
      )
      case _ => RArr("No code available for installation on this operating system")
    },
    """Login as the postgres operating system user. Note we do this using sudo, leaving the postgres Linux user with no password. As the postgres user by
    |default you are automatically connected to the postgres database.""".stripMargin,
    BashLine(yourBashPrompt ,"sudo su postgres"),
    BashLine(BashPromptSpan.listenStrText(computerNameInput){ cName => s"postgres@$cName:/" }, "psql"),
    PsqlLine.listenStrHtml(uNameInp){ uName => RArr(postgresPsqlPrompt, s"CREATE USER $uName WITH SUPERUSER;") },
    "Quit psql",
    PsqlLine(postgresPsqlPrompt, """\q"""),
    "Switch back to your normal operating system user login.",
    BashLine("exit"),
    """Login to psql again under your usual username. This time you must specify the postgres database as there is no database with the same name as your
    |operating system username.""".stripMargin,
    BashLine(yourBashPrompt, "psql postgres"),
    "Create a new database. The owner is you. The main client of the database maybe a webserver. Give this its own user with maybe more limited capabilities.",
    PsqlLine(postgresPsqlPrompt, SpanInlineInedit.listen2StrText(dbNameInp, uNameInp){ (dbName, uName) => s"CREATE DATABASE $dbName OWNER $uName;" }),
    "Switch to new database.",
    PsqlLine(postgresPsqlPrompt, SpanInlineInedit.listenStrText(dbNameInp){ dbName => raw"""\connect $dbName;""" }),
    "If you logout of psql at any point, then when you log back in you now use",
    BashLine(yourBashPrompt, SpanInlineInedit.listenStrText(dbNameInp){ dbName => s"psql $dbName" }),
  )

  val s2: LiHtml = LiHtml(    
    DivHtml("Before continuing, here are some commands to correct and undo things if necessary. At some point you may get:"),
    PsqlLine.listenStrText(uNameInp){ uName => """database "$uName" has a collation version mismatch""" },
    DivHtml("then enter"),
    PsqlLine(userPsqlPrompt, SpanInlineInedit.listenStrText(dbNameInp){ dbName => s"ALTER DATABASE $dbName REFRESH COLLATION VERSION;" }),
    DivHtml(PsqlSpan(userPsqlPrompt, """\du+"""), "List users and other roles"),
    DivHtml(PsqlSpan(userPsqlPrompt, """\l"""), "List databases"),
    DivHtml(PsqlSpan(userPsqlPrompt, """\connect otherdbname"""), "Switch databases"),
    DivHtml(PsqlSpan(userPsqlPrompt, """DROP DATABASE dbname;"""), "Remove database"),
    DivHtml(PsqlSpan(userPsqlPrompt, """ALTER DATABASE oldname RENAME TO newname;"""),
      "Change database name. Note you can't be logged into the database whose name you are changing."),
    DivHtml(PsqlSpan(userPsqlPrompt, "DROP TABLE", SpanInlineInedit.pink("tablename"), ";"), "Delete table"),
    DivHtml(PsqlSpan(userPsqlPrompt, "TRUNCATE", SpanInlineInedit.pink("tablename"), ";"), "Delete all rows"),
    DivHtml(PsqlSpan(userPsqlPrompt, "ALTER TABLE users DROP CONSTRAINT", SpanInlineInedit.pink("yourconstraint"), ";"), "Drop constraint"),    
    DivHtml(PsqlSpan(userPsqlPrompt, "ALTER TABLE users DROP COLUMN", SpanInlineInedit.pink("columnname"), ";"), "To delete row"),
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
    userLine(s"""ALTER TABLE users ADD CONSTRAINT unamecheck CHECK(username ~ $uNameRegexStr);"""),
    userLine("CREATE UNIQUE INDEX usernamelower ON users(lower(username));"),
    "To add password",
    userLine(s"""ALTER TABLE users ADD password VARCHAR(128) NOT NULL;"""),
    userLine(s"""ALTER TABLE users ADD CONSTRAINT passwordcheck CHECK(password ~ $passwordRegexStr);"""),
    "To add status",
    userLine("CREATE type status AS ENUM ('User', 'Admin');"),
    userLine(s"""ALTER TABLE users ADD rank status DEFAULT 'User' NOT NULL;"""),
    "To add user",
    PsqlLine(userPsqlPrompt, "INSERT INTO users VALUES(DEFAULT,", SpanInlineInedit.pink("username".enquote1), ",", SpanInlineInedit.pink("password".enquote1),
      """, 'Admin');"""),
    PsqlLine(userPsqlPrompt, "INSERT INTO users VALUES(DEFAULT,", SpanInlineInedit.pink("username".enquote1), ",", SpanInlineInedit.pink("password".enquote1),
      ");"),
    "To update status",
    PsqlLine(userPsqlPrompt, """UPDATE users SET rank = 'Admin'""", "WHERE username =", """'username';""".pinkSpan),
    """Create our first application database user. Note there is no need to use same name as the operingsystem owner of the application, as this user doesn't
    |need to access the psql terminal.""".stripMargin,
    PsqlLine(userPsqlPrompt, "CREATE USER app1 WITH PASSWORD 'password1';"),
    PsqlLine(userPsqlPrompt, "GRANT USAGE ON SCHEMA public TO app1;"),
  )
}