/* Copyright 2025-6 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pweb.*, WebExts.*, osweb.*, wcode.*, Colour.LightGreen

/** Web page for running Apache Tomcat for Scala. */
object TomcatPage extends DevPageBase
{ override val titleStr: String = "Apache Tomcat Server"
  override def fileStemStr: String = "tomcat"

  override def head: HeadHtml = headCss("documentation")
  override def body: BodyHtml = BodyHtml("Using Apache Tomcat Server".h1, central, jsScriptStd)

  def central: DivHtml = DivHtml.classAtt("central", p1, pUpdaters, steps)

  def p1: PHtml = PHtml("""This page is targeted at Scala Developers, who want to get a simple, or multiple web applications going, or create a dynamic web site
  |using Scala. However nearly everything will also apply to people who want to use Java, Kotlin and other JVM language. Its not geared towards advanced
  |professional Scala developers who will almost all be using other solutions. If like me you come to the Tomcat Server, with only the experience of running
  |Apache vanilla servers, setting up Tomcat is significantly more complicated than the extreme simplicity of installing an Apache Vanilla server. Note
  |referring to it as Apache Vanilla is my own naming scheme as referring to it just as "Apache" can be confusing. So here follows a list of steps for setting
  |up Tomcat on your own Desktop, laptop, home server or VPS.""".stripMargin)
  
  val nset: String = "nset"
  
  /** Initial value for computer name. */
  val computerName1: String = "computer"
  val cset: String = "cset"  
  val tcMajorVer: String = "11.0"
  val tcMinorVer: String = "24"
  def tcVer1: String = tcMajorVer + "." + tcMinorVer
  val javaMajorVer: String = "25"
  val domain1: String = "mysite.com"
  
  /** Initial value for username. */
  val userName1: String = "tommy"

  /** Updater for username. */
  val userNameInput: UpdaterInputStr = UpdaterInputStr("uName", userName1)
  
  /** [[UpdaterInputStr]] and it's label for username. */
  val userNameLI: LabelInput = LabelInput("User Name", userNameInput)

  /** Updater for username. */
  val computerNameInput: UpdaterInputStr = UpdaterInputStr("cName", computerName1)
  
  /** [[UpdaterInputStr]] and it's label for computer name. */
  val computerNameLI: LabelInput = LabelInput("Computer Name", computerNameInput)  
  
  val nRam1: Int = 2
  val ramInput: UpdaterDblInput = UpdaterDblInput("nRam", nRam1)
  val ramLI: LabelInput = LabelInput("System Ram", ramInput)
  
  def tomcatDirPrompt: BashPromptSpan = BashPromptSpan.listen3StrText(userNameInput, computerNameInput, dirInput) { (uName, cName, dir) => s"$uName@$cName:$dir" }
  val tomVerInput: UpdaterInputStr = UpdaterInputStr("version", tcVer1)
  val tomVerLI: LabelInput = LabelInput("Tomcat Version", tomVerInput)
  
  val boundInput: UpdaterSelect = UpdaterSelect("boundary", PublicInternet, LocalHost, LocalNetwork)
  val boundaryLI = LabelInput("Network boundary", boundInput)

  val domainInput: UpdaterInputStr = UpdaterInputStr("dName", domain1)
  val domainLI: LabelInput = LabelInput("Domain Name", domainInput)
  
  val dir1: String = "/opt/tomcat"
  val dirInput: UpdaterInputStr = UpdaterInputStr("dirName", dir1)
  val dirLI: LabelInput = LabelInput("Tomcat directory", dirInput)

  def pUpdaters: PHtml = PHtml(updaterExplain,
  LabelInputsLine(userNameLI, opSysLI, computerNameLI, ramLI, tomVerLI, javaVerLI, boundaryLI, domainLI, dirLI))

  def steps: OlLarge = OlLarge(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, sCert, s13, s14, s15)
  
  val s1: LiHtml = LiHtml.listenOptHtml(opSysInput){ opt =>
    val res1: XCon = DivHtml("Upgrade packages.")
    val res2: RArr[XCon] = opt match
    { case UbuntuDeriv => RArr(BashLine("sudo apt update"), BashLine("sudo apt upgrade"))
      case ArchDeriv => RArr(BashLine("Sudo pacman -Syu"))
      case _ => RArr("No code available.")
    }
    val res3 = DivHtml("Install Fail2Ban to protect against brute force login attacks")
    val res4 = opt match
    { case UbuntuDeriv => BashLine("sudo apt install fail2ban")
      case ArchDeriv => BashLine("pacman -S fail2ban")
      case _ => "No code available."
    }
    val res5 = BashLine("sudo systemctl enable --now fail2ban")
    res1 %: res2 +% res3 +% res4 +% res5
  }

  val s2 = LiHtml("""Lease a VPS. A virtual private server. The price of these have dropped considerably over the years and will almost certainly continue to
  |drop. You can purchase a VPS with a couple of cores and 4 Gig of RAM for a few dollars / pounds / Euros a month these days. If you are really tight with
  |money you could probably get away with 2 gigs, but I would recommend starting with a comfortable 4 gigs. When starting out I recommend just buying monthly,
  |as your needs will change. For the time being I don't have enough experience to make recommendations. I've had good service from Digital Ocean for a number
  |of years running a VPS for Apache Vanilla, but they are some what pricey to get 4 gigs of ram for a small project with minimal users. I intend to come back
  |and update this later. I'm currently using an Ubuntu Operating System, just out of familiarity. Now obviously if you are using your own desktop, laptop or
  |home server, you won't need this step and you will probably want to try that first before spending money on a VPS. But you will almost certainly need one to
  |get your site / app out to the world.""".stripMargin)

  val s3: LiHtml = javaInstall(LiHtml)

  val s4: LiHtml = LiHtml(s"""Create a new user and a new group of the same name and add it to the sudo group. For these examples we'll call it '$userName1'. I
  |find it better to have a different name for the user than the folder we will create next. Again for desktop, laptop and home server this is not necessary and
  |you can use your own username.""".stripMargin,
    BashLine.listenStrText(userNameInput){ uName => s"sudo useradd -ms /bin/bash -G sudo $uName" },
    BashLine.listenStrText(userNameInput)(uName => s"sudo passwd $uName"),
  )

  val s5: LiHtml = LiHtml("""Create a directory for tomcat and change the owner and group. The directory doesn't have to be called tomcat and placed in the Opt
  |directory, but this is a pretty standard schema. You can use your own username on a home machine.""".stripMargin,
  BashLine.listenStrText(dirInput){ dir => "sudo mkdir" -- dir },
  BashLine.listen2StrText(userNameInput, dirInput)((uName, dir) => s"sudo chown $uName:$uName $dir"),
  SpanLine.listenStrText(userNameInput)(uName => s"Switch user to $uName. Then change directory."),
  "Change user unless, you already login in as the tomcat owner.",
  BashLine.listenStrText(userNameInput)(uName => s"sudo su $uName"),
  "If you have a specialist tomcat user then change the bash starting directory.",
  BashLine("nano ~/.bashrc"),
  "Add this line at the end of the script.", 
  BashLine.listenStrText(dirInput){ dir => s"cd $dir"},  
  BashLine.listenStrText(dirInput){ dir => s"cd $dir" },
  """Create a directory called Base inside the tomcat directory. This will be used for CatalinaBase and will allow you to keep configuration files to use with
  |multiple installs and major version changes of Apache.""".stripMargin,
  BashLine(tomcatDirPrompt, "mkdir Base")
  )

  val s6:LiHtml = LiHtml("Go to the Tomcat Download page: ", AHtml("https://tomcat.apache.org/download-11.cgi"), s""". Currently we're on major version 11.
  |Generally you should use the latest version. I haven't tested these instructions before 10.0, but they should work at least back to version 9, if you have
  |some specific reason to use an earlier version. At the time of updating the latest sub version is $tcVer1. Make sure you download the latest sub version,
  |because Apache cut the links to the older sub versions. Copy the tar.gz file link into the browser. Once its downloaded copy the sha256 code into the next
  |command to check the integrity of the download. If its good the sha code should be echoed back in red and the file name in white.""".stripMargin,
  BashLine(tomcatDirPrompt,
    SpanInlineInedit.listenStrText(tomVerInput){ version => s"wget https://dlcdn.apache.org/tomcat/tomcat-11/v$version/bin/apache-tomcat-$version.tar.gz"}),
  BashLine(tomcatDirPrompt,
    SpanInlineInedit.listenStrText(tomVerInput){ version => s"sha512sum apache-tomcat-$version.tar.gz | grep alongsequenceoflettersanddigits"})
  )

  val s7: LiHtml = LiHtml("""Then unpack the tar file. This will allow us to easily swap in an updated minor version of Tomcat 11.0. These are released
  |frequently. Run this command from the folder where the tar is downloaded.""".stripMargin,
  BashLine.listen2StrText(tomVerInput, dirInput){ (version, dir) => s"tar xf apache-tomcat-$version.tar.gz -C $dir" },
  "Create a link in the tomcat directory.",  
  BashLine(tomcatDirPrompt, SpanInlineInedit.listenStrText(tomVerInput){ version => s"ln -s apache-tomcat-$version tom11" }),
  "Then checking what we've got.",
  BashLine(tomcatDirPrompt, "ls"),
  CodeOutputLine.listenStrText(tomVerInput){ version => s"apache-tomcat-$version  apache-tomcat-$version.tar.gz  Base  tom11" }
  )

  val s8: LiHtml = LiHtml("""Create the logs and conf directories and copy across the server.xml and web.xml files from the installation directory structure to
  |the base directory structure. If the catalina base and catalina home directories are the same, which is often the case in beginners installation
  |instructions, then this is redundant.""".stripMargin,
  BashLine(tomcatDirPrompt, "mkdir Base/logs"),
  BashLine(tomcatDirPrompt, "mkdir Base/lib"),
  BashLine(tomcatDirPrompt, "mkdir Base/temp"),
  BashLine(tomcatDirPrompt, "mkdir Base/conf"),

  BashLine(tomcatDirPrompt, "cp tom11/conf/server.xml tom11/conf/web.xml tom11/conf/catalina.properties Base/conf"),
  """Create a home page for your server. Again not necessary if base and home are set to the same directory, as Tomcat comes with web pages and example
  |apps.""".stripMargin,
  BashLine(tomcatDirPrompt, "mkdir -p Base/webapps/ROOT"),
  BashLine(tomcatDirPrompt, "nano Base/webapps/ROOT/index.html"),
  "Copy the code below into the editor.",
  PreCode.listen3Text(computerNameInput, domainInput, tomVerInput){ (cName, domain, version) =>
    HtmlPage.titleOnly("Holding Page", s"This is coming from $cName at $domain, a tomcat $version server").out }
  )

  val s9: LiHtml = LiHtml("Create a systemd unit file.",
    BashLine("sudo nano /etc/systemd/system/tom11.service"),
    "Add the following code. Then control o, return, control x.",
    CodeLinesHtml(sysdLines)
  )

  def sysdLines: RArr[DivHtml] =
    DivColour(LightGreen, "[Unit]") %:
    StrArr(
      "Description=Apache Tomcat 11.0 Web Application Container",
      "After=network.target",
      "").toDivLines +%
    DivColour(LightGreen, "[Service]") +%
    DivHtml("Type=forking") +%
    DivHtml("") +%
    DivHtml.listenOptHtml(opSysInput){ ops =>
      val javaStr: String = ops match
      { case UbuntuDeriv => "java-1.25.0-openjdk-amd64"
        case ArchDeriv => "java-25-openjdk"
        case _ => "No code available"
      }
      RArr(s"""Environment="JAVA_HOME=/usr/lib/jvm/$javaStr"""")
    } +%
    DivHtml.listenStrText(dirInput) { dir => s"""Environment="CATALINA_PID=$dir/Base/temp/tomcat.pid""""} +%
    DivHtml.listenStrText(dirInput) { dir => s"""Environment="CATALINA_HOME=$dir/tom11/""""} +%
    DivHtml.listenStrText(dirInput) { dir => s"""Environment="CATALINA_BASE=$dir/Base/""""} +%
    DivHtml.listenDblText(ramInput) { n =>
      val nn = n * 256
      val xmsStr = nn.min(512).str0
      val xmxStr = (nn.min(512) * 2 + (nn - 512).min(0)).min(8192)
      s"""Environment="CATALINA_OPTS=-Xms${xmsStr}M -Xmx${(nn * 2).str0}M -server -XX:+UseParallelGC""""
    } +%
    DivHtml("""Environment="JAVA_OPTS=-Djava.awt.headless=true -Djava.security.egd=file:/dev/./urandom"""") +%
    DivHtml.listenStrText(dirInput) { dir => s"ExecStart=$dir/tom11/bin/startup.sh" } +%
    DivHtml.listenStrText(dirInput) { dir => s"ExecStop=$dir/tom11/bin/shutdown.sh" } +%
    DivHtml.listenStrText(userNameInput) { uName => s"User=$uName" } +%
    DivHtml.listenStrText(userNameInput) { uName => s"Group=$uName" } +%
    DivHtml("UMask=0007") +%
    DivHtml("RestartSec=10") +%
    DivHtml("Restart=always") +%
    DivColour(LightGreen, "[Install]") +%
    DivHtml("WantedBy=multi-user.target")

  val s10: LiHtml = LiHtml(
  "Check if Apache2 Vanilla is running. It seems to be running by default on Ubuntu Server.",
  BashLine("sudo systemctl status apache2"),
  "If its running",
  BashLine("sudo systemctl disable apache2"),
  BashLine("sudo systemctl stop apache2"),
  "Then reset Systemd",  
  BashLine("sudo systemctl daemon-reload"),
  BashLine("sudo systemctl start tom11"),
  BashLine("sudo systemctl status tom11"),
  """If status good, open a web page at the IpNumber:8080, or the DomainName:8080 on a VPS, or on a local machine at localhost:8080. On a VPS you will probably
  |want to now enable the server to start automatically, but perhaps not if this is your personal laptop or desktop""".stripMargin,
  BashLine("sudo systemctl enable tom11"),
  )

  val s11: LiHtml = LiHtml("To switch to port 80 the http defaults",
  BashLine.listenOptText(opSysInput){
    case UbuntuDeriv => "sudo apt install authbind"
    case ArchDeriv => "sudo yay authbind"
    case _ => "No code available"  
  },
  BashLine("sudo touch /etc/authbind/byport/80"),
  BashLine.listenStrText(userNameInput)(uName => s"sudo chown $uName: /etc/authbind/byport/80"),
  BashLine("sudo chmod 500 /etc/authbind/byport/80"),
  "And for HTTPS to use 443",
  BashLine("sudo touch /etc/authbind/byport/443"),
  BashLine.listenStrText(userNameInput)(uName => s"sudo chown $uName:$uName /etc/authbind/byport/443"),
  BashLine("sudo chmod 500 /etc/authbind/byport/443"),
  "Reopen the Systemd Unit file.",
  BashLine("sudo nano /etc/systemd/system/tom11.service"),
  CodeChangeLine.listenText(dirInput){ dir => s"ExecStart=$dir/tom11/bin/startup.sh" }{ dir => s"ExecStart=authbind --deep $dir/tom11/bin/startup.sh" },
  "Open the Tomcat configuration file.",
  BashLine.listenStrText(dirInput){ dir => s"nano $dir/Base/conf/server.xml" },
  CodeChangeLine("""<Connector port="8080" protocol""".escapeHtml, """<Connector port="80" protocol""".escapeHtml),
  CodeChangeLine("""redirectPort=\"8443\"""", """redirectPort=\"443\"""".escapeHtml),  
  "reset",
  BashLine("sudo systemctl daemon-reload"),
  BashLine("sudo systemctl restart tom11"),
  "The page should now be available without the port :8080 suffix."
  )

  val sCert: LiHtml = LiHtml.listen2Opt2StrHtml(boundInput, opSysInput, userNameInput, domainInput)(CertItemFunc) 

  val s13 = LiHtml("Configure Tomcat to use 443 & link to ssl cert above",
  BashLine.listenStrText(dirInput){ dir => s"nano $dir/Base/conf/server.xml" },
  "Uncomment the section and modify as below",
  PreCode.listenText(domainInput){ dName =>
  s"""<Connector port="443" protocol="org.apache.coyote.http11.Http11NioProtocol"
  |  maxThreads="150" SSLEnabled="true" secure="true" scheme="https">
  |  <UpgradeProtocol className="org.apache.coyote.http2.Http2Protocol" />
  |  <SSLHostConfig>
  |    <Certificate certificateFile="/etc/letsencrypt/live/$dName/cert.pem"
  |      certificateKeyFile="/etc/letsencrypt/live/$dName/privkey.pem"
  |      certificateChainFile="/etc/letsencrypt/live/$dName/chain.pem" />
  |  </SSLHostConfig>
  |</Connector>""".stripMargin },
  "Restart Tomcat",
  BashLine("sudo systemctl start tom11"),
  BashLine("sudo systemctl status tom11"),
  SpanLine.listenStrText(domainInput){ dName => s"Go to https://$dName" }  
  )

  val s14: LiHtml = LiHtml(
    "Creating a very basic servlet in Scala",
    BashLine(tomcatDirPrompt, "mkdir -p Base/webapps/Hello/WEB-INF/classes/ostrat/pDev"),
    "In Sbt run Servlet/compile",
    "Copy the HelloServlet.class file into above folder.",
    "We could use the following in Base/webapps/Hello/WEB-INF/web.xml, but there's no need as these servlets have the WebServlet annotation.",
    PreCode(Web6App1("Hello", "ostrat.pDev.HelloServlet").out(0, 0, 80)),
    "Download", AHtml("https://repo1.maven.org/maven2/org/scala-lang/scala-library/3.8.4/scala-library-3.8.4.jar"), """into Base/lib directory. We're putting it
    |into Base/lib rather than Hello/WEB-INF/lib, so it can be used by all web apps.""".stripMargin
  )

  val s15: LiHtml = LiHtml("Creating a servlet that uses the Util and Geom modules.",
    BashLine(tomcatDirPrompt, "mkdir -p Base/webapps/GeomUser/WEB-INF/classes/ostrat/pDev"),
    "Copy the GeomUser.class file into above folder.",
    "Add the Util and Geom jars to Base/lib."
  )
}