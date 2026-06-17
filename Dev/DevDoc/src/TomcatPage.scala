/* Copyright 2025-6 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pweb.*, WebExts.*, osweb.*, wcode.*, Colour.LightGreen

/** Web page for running Apache Tomcat for Scala. */
object TomcatPage extends DevPageBase
{ override val titleStr: String = "Apache Tomcat Server"
  override def fileStemStr: String = "tomcat"

  override def head: HeadHtml = headCss("documentation")
  override def body: BodyHtml = BodyHtml("Using Apache Tomcat Server".h1, central, ScriptHtml.jsSrc("tomcat.js"), ScriptHtml.main("TomcatPageJs"))

  def central: DivHtml = DivHtml.classAtt("central", p1, pUpdaters, steps)

  def p1: PHtml = PHtml("""This page is targeted at Scala Developers, who want to get a simple, or multiple web applications going, or create a dynamic web site
  |using Scala. However nearly everything will also apply to people who want to use Java, Kotlin and other JVM language. Its not geared towards advanced
  |professional Scala developers who will almost all be using other solutions. If like me you come to the Tomcat Server, with only the experience of running
  |Apache vanilla servers, setting up Tomcat is significantly more complicated than the extreme simplicity of installing an Apache Vanilla server. Note
  |referring to it as Apache Vanilla is my own naming scheme as referring to it just as "Apache" can be confusing. So here follows a list of steps for setting
  |up Tomcat on your own Desktop, laptop, home server or VPS.""".stripMargin)
  
  val nset: String = "nset"
  val cName1: String = "computer"
  val cset: String = "cset"
  val userAtCom: String = uName1 + "@" + cName1
  val tcMajorVer: String = "11.0"
  val tcMinorVer: String = "22"
  def tcVer1: String = tcMajorVer + "." + tcMinorVer
  val javaMajorVer: String = "25"
  val domain1: String = "localhost"
  
  val uName1: String = "tommy"
  val uNameLTI: LabelTextInput = LabelTextInput("uName", "User Name", uName1)
  val uNameIUT: UpdaterTextInput = uNameLTI.child2
  
  val cNameLTI: LabelTextInput = LabelTextInput("cName", "Computer Name", cName1)
  val cNameIUT: UpdaterTextInput = cNameLTI.child2
  val nRam1: Int = 2
  val ramLNI: LabelNumInput = LabelNumInput("nRam", "System Ram", nRam1)
  val ramIUN: UpdaterNumInput = ramLNI.child2
  def tomcatDirPrompt: BashPromptSpan = BashPromptSpan.listen3Text(uNameIUT, cNameIUT, dirIUT) { (uName, cName, dir) => s"$uName@$cName:$dir" }
  val tomVerLTI: LabelTextInput = LabelTextInput("version", "Tomcat Version", tcVer1)
  val tomVarIUT: UpdaterTextInput = tomVerLTI.child2
  
  val domainLTI: LabelTextInput = LabelTextInput("dName", "Domain Name", domain1)
  val domainIUT: UpdaterTextInput = domainLTI.child2
  val dir1: String = "/opt/tomcat"
  val dirLTI: LabelTextInput = LabelTextInput("dirName", "Tomcat directory", dir1)
  val dirIUT: UpdaterTextInput = dirLTI.child2

  def pUpdaters: PHtml = PHtml(updaterExplain,
  LabelInputsLine(uNameLTI, opNameLTI, cNameLTI, ramLNI, tomVerLTI, javaVerLNI, domainLTI, dirLTI))

  def steps = OlLarge(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13)

  val fS1: OptionHtml => RArr[XCon] = opt =>
  { val res1 = DivHtml("Upgrade packages.")
    val res2: RArr[XCon] = opt match
    { case UbuntuDeriv => RArr(BashLine("sudo apt update"), BashLine("sudo apt upgrade"))
      case ArchDeriv => RArr(BashLine("Sudo pacman -Syu"))
      case _ => RArr("No code available.")
    }
    val res3 = DivHtml("Install Fail2Ban to protect against brute force login attacks")
    val res4 =  opt match
    { case UbuntuDeriv => BashLine("sudo apt install fail2ban")
      case ArchDeriv => BashLine("pacman -S fail2ban")
      case _ => "No code available."
    }
    val res5 = BashLine("sudo systemctl enable --now fail2ban")
    res1 %: res2 +% res3 +% res4 +% res5
  }
  val s1: LiHtml = LiHtml.listenOption(opNameIUT)(fS1)

  val s2 = LiHtml("""Lease a VPS. A virtual private server. The price of these have dropped considerably over the years and will almost certainly continue to
  |drop. You can purchase a VPS with a couple of cores and 4 Gig of RAM for a few dollars / pounds / Euros a month these days. If you are really tight with
  |money you could probably get away with 2 gigs, but I would recommend starting with a comfortable 4 gigs. When starting out I recommend just buying monthly,
  |as your needs will change. For the time being I don't have enough experience to make recommendations. I've had good service from Digital Ocean for a number
  |of years running a VPS for Apache Vanilla, but they are some what pricey to get 4 gigs of ram for a small project with minimal users. I intend to come back
  |and update this later. I'm currently using an Ubuntu Operating System, just out of familiarity. Now obviously if you are using your own desktop, laptop or
  |home server, you won't need this step and you will probably want to try that first before spending money on a VPS. But you will almost certainly need one to
  |get your site / app out to the world.""".stripMargin)
  
  val s3 = javaInstall

  val s4 = LiHtml(
  s"""Create a new user and a new group of the same name and add it to the sudo group. For these examples we'll call it '$uName1'. I find it better to have a
  |different name for the user than the folder we will create next. Again for desktop, laptop and home server this is not necessary and you can use your own
  |username.""". stripMargin,
  BashLine.listenText(uNameIUT){ uName => s"sudo useradd -ms /bin/bash -G sudo $uName"},
  BashLine.listenText(uNameIUT)(uName => s"sudo passwd $uName"),
  )

  val s5 = LiHtml("""Create a directory for tomcat and change the owner and group. The directory doesn't have to be called tomcat and placed in the Opt
  |directory, but this is a pretty standard schema. You can use your own username on a home machine.""".stripMargin,
  BashLine.listenText(dirIUT){dir => "sudo mkdir" -- dir},
  BashLine.listen2Text(uNameIUT, dirIUT)((uName, dir) => s"sudo chown $uName:$uName $dir"),
  SpanLine.listenText(uNameIUT)(uName => s"Switch user to $uName. Then change directory."),
  "Change user unless, you already login in as the tomcat owner.",
  BashLine.listenText(uNameIUT)(uName => s"sudo su $uName"),
  BashLine.listenText(dirIUT){ dir => s"cd $dir" },
  """Create a directory called Base inside the tomcat directory. This will be used for CatalinaBase and will allow you to keep configuration files to use with
  |multiple installs and major version changes of Apache.""".stripMargin,
  BashLine(tomcatDirPrompt, "mkdir Base")
  )

  val s6 = LiHtml("Go to the Tomcat Download page: ", AHtml("https://tomcat.apache.org/download-11.cgi"), s""". Currently we're on major version 11. Generally
  |you should use the latest version. I haven't tested these instructions before 10.0, but they should work at least back to version 9, if you have some
  |specific reason to use an earlier version. At the time of updating the latest sub version is $tcVer1. Make sure you download the latest sub version, because
  |Apache cut the links to the older sub versions. Copy the tar.gz file link into the browser. Once its downloaded copy the sha256 code into the next command to
  |check the integrity of the download. If its good the sha code should be echoed back in red and the file name in white.""".stripMargin,
  BashLine(tomcatDirPrompt,
    SpanInlineInedit.inputText(tomVarIUT){ version => s"wget https://dlcdn.apache.org/tomcat/tomcat-11/v$version/bin/apache-tomcat-$version.tar.gz"}),
  BashLine(tomcatDirPrompt,
    SpanInlineInedit.inputText(tomVarIUT){ version => s"sha512sum apache-tomcat-$version.tar.gz | grep alongsequenceoflettersanddigits"})
  )

  val s7 = LiHtml("""Then unpack the tar file and create a link. This will allow us to easily swap in an updated minor version of Tomcat 11.0. These are
  |released frequently.""".stripMargin,
  BashLine(tomcatDirPrompt, SpanInlineInedit.input2Text(tomVarIUT, dirIUT){ (version, dir) => s"tar xf apache-tomcat-$version.tar.gz -C $dir"}),
  BashLine(tomcatDirPrompt, SpanInlineInedit.inputText(tomVarIUT){ version => s"ln -s apache-tomcat-$version tom11"}),
  "Then checking what we've got.",
  BashLine(tomcatDirPrompt, "ls"),
  CodeOutputLine.inputText(tomVarIUT){ version => s"apache-tomcat-$version  apache-tomcat-$version.tar.gz  Base  tom11"}
  )

  val s8 = LiHtml("""Create the logs and conf directories and copy across the server.xml and web.xml files from the installation directory structure to the base
  |directory structure. If the catalina base and catalina home directories are the same, which is often the case in beginners installation instructions, then
  |this is redundant.""".stripMargin,
  BashLine(tomcatDirPrompt, "mkdir Base/logs"),
  BashLine(tomcatDirPrompt, "mkdir Base/conf"),
  BashLine(tomcatDirPrompt, "cp tom11/conf/server.xml tom11/conf/web.xml Base/conf"),
  """Create a home page for your server. Again not necessary if base and home are set to the same directory, as Tomcat comes with web pages and example
  |apps.""".stripMargin,
  BashLine(tomcatDirPrompt, "mkdir -p Base/webapps/ROOT"),
  BashLine(tomcatDirPrompt, "nano Base/webapps/ROOT/index.html"),
  "Copy the code below into the editor.",
  PreCode.listen3Text(cNameIUT, domainIUT, tomVarIUT){ (cName, domain, version) =>
    HtmlPage.titleOnly("Holding Page", s"This is coming from $cName at $domain, a tomcat $version server").out }
  )

  val s9 = LiHtml("Create a systemd unit file.",
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
    DivHtml.listenOption(opNameIUT){ ops =>
      val javaStr: String = ops match {
        case ArchDeriv => "java-25-openjdk"
        case _ => "java-1.25.0-openjdk-amd64"
      }
      RArr(s"""Environment="JAVA_HOME=/usr/lib/jvm/$javaStr"""")
    } +%
    DivHtml.listenStrText(dirIUT) { dir => s"""Environment="CATALINA_PID=$dir/Base/temp/tomcat.pid""""} +%
    DivHtml.listenStrText(dirIUT) { dir => s"""Environment="CATALINA_HOME=$dir/tom11/""""} +%
    DivHtml.listenStrText(dirIUT) { dir => s"""Environment="CATALINA_BASE=$dir/Base/""""} +%
    DivHtml.listenNum(ramIUN) { n =>
      val nn = n * 256
      val xmsStr = nn.min(512).str0
      val xmxStr = (nn.min(512) * 2 + (nn - 512).min(0)).min(8192)
      s"""Environment="CATALINA_OPTS=-Xms${xmsStr}M -Xmx${(nn * 2).str0}M -server -XX:+UseParallelGC""""
    } +%
    DivHtml("""Environment="JAVA_OPTS=-Djava.awt.headless=true -Djava.security.egd=file:/dev/./urandom"""") +%
    DivHtml.listenStrText(dirIUT) { dir => s"ExecStart=$dir/tom11/bin/startup.sh" } +%
    DivHtml.listenStrText(dirIUT) { dir => s"ExecStop=$dir/tom11/bin/shutdown.sh" } +%
    DivHtml.listenStrText(uNameIUT) { uName => s"User=$uName" } +%
    DivHtml.listenStrText(uNameIUT) { uName => s"Group=$uName" } +%
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
  BashLine("sudo apt install authbind"),
  BashLine("sudo touch /etc/authbind/byport/80"),
  BashLine.listenText(uNameIUT)(uName => s"sudo chown $uName: /etc/authbind/byport/80"),
  BashLine("sudo chmod 500 /etc/authbind/byport/80"),
  "And for HTTPS to use 443",
  BashLine("sudo touch /etc/authbind/byport/443"),
  BashLine.listenText(uNameIUT)(uName => s"sudo chown $uName: /etc/authbind/byport/443"),
  BashLine("sudo chmod 500 /etc/authbind/byport/443"),
  "Reopen the Systemd Unit file.",
  BashLine("sudo nano /etc/systemd/system/tom11.service"),
  CodeChangeLine.listenText(dirIUT){ dir => s"ExecStart=$dir/tom11/bin/startup.sh" }{ dir => s"ExecStart=authbind --deep $dir/tom11/bin/startup.sh" },
  "Open the Tomcat configuration file.",
  BashLine.listenText(dirIUT){ dir => s"nano $dir/Base/conf/server.xml" },
  CodeChangeLine("""<Connector port="8080" protocol""".escapeHtml, """<Connector port="80" protocol""".escapeHtml),
  CodeChangeLine("""redirectPort=\"8443\"""", """redirectPort=\"443\"""".escapeHtml),  
  "reset",
  BashLine("sudo systemctl daemon-reload"),
  BashLine("sudo systemctl restart tom11"),
  "The page should now be available without the port :8080 suffix."
  )

  val s12 = LiHtml("Install snap",
  BashLine("sudo apt install snapd"),
  "Install certbot",  
  BashLine("sudo snap install --classic certbot"),
  CodeOutputLine("certbot 5.1.0 from Certbot Project (certbot-eff✓) installed"),
  "Ensure that the cerbot command can be run",
  BashLine("sudo ln -s /snap/bin/certbot /usr/bin/certbot"),
  "Stop tomcat.",
  BashLine("sudo systemctl stop tom11"),
  "Install certificate. When asked to enter domain name, you can enter multiple web domains, but you only use the first in the ensuing commands.",
  BashLine("sudo certbot certonly --standalone"),
  "Configure permissions to certificates",
  BashLine.listenText(uNameIUT){ user => s"sudo chgrp -R $user /etc/letsencrypt/live/" },
  BashLine.listenText(uNameIUT){ user => s"sudo chgrp -R $user /etc/letsencrypt/archive/" },
  BashLine("sudo chmod -R 750 /etc/letsencrypt/live/"),
  BashLine("sudo chmod -R 750 /etc/letsencrypt/archive/"),
  BashLine.listenText(domainIUT){ dName => s"sudo chmod 640 /etc/letsencrypt/live/$dName/privkey.pem" },
  BashLine.listenText(domainIUT){ dName => s"sudo chmod 644 /etc/letsencrypt/live/$dName/cert.pem" },
  BashLine.listenText(domainIUT){ dName => s"sudo chmod 644 /etc/letsencrypt/live/$dName.com/chain.pem" },
  "Check permissions - if you dont have access then something wrong...",
  BashLine.listenText(domainIUT){ dName => s"ls -la /etc/letsencrypt/live/richstrat.com/" },
  )

  val s13 = LiHtml("Configure Tomcat to use 443 & link to ssl cert above",
  BashLine.listenText(dirIUT){ dir => "nano $dir/Base/conf/server.xml" },
  "Uncomment the section and modify as below",
  PreCode.listenText(domainIUT){ dName =>
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
  SpanLine.listenText(domainIUT){ dName => s"Go to https://$dName" }  
  )
}