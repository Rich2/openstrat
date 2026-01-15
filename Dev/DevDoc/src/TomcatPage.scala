/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*, wcode.*

/** Web page for running Apache Tomcat for Scala. */
object TomcatPage extends HtmlPageInput
{ given thisPage: HtmlPageInput = this
  override val titleStr: String = "Apache Tomcat Server"
  override def fileNameStem: String = "tomcat"

  override def head: HtmlHead = headCss("documentation")
  override def body: HtmlBody = HtmlBody(HtmlH1("Using Apache Tomcat Server"), central,
  //  XComment("/openstrat/Dev/DevDoc/DevDocJs/target/scala-3.7.3/devdocjs-opt/"),
    HtmlScript.jsSrc("tomcat.js"), HtmlScript.main("TomcatPageJs"))

  def central: HtmlDiv = HtmlDiv.classAtt("central", p1, p2, steps)

  def p1: HtmlP = HtmlP("""This page is targeted at Scala Developers, who want to get a simple, or multiple web applications going, or create a dynamic web site
  |using Scala. However nearly everything will also apply to people who want to use Java, Kotlin and other JVM language. Its not geared towards advanced
  |professional Scala developers who will almost all be using other solutions. If like me you come to the Tomcat Server, with only the experience of running
  |Apache vanilla servers, setting up Tomcat is significantly more complicated than the extreme simplicity of installing an Apache Vanilla server. Note
  |referring to it as Apache Vanilla is my own naming scheme as referring to it just as "Apache" can be confusing. So here follows a list of steps for setting
  |up Tomcat on your own Desktop, laptop, home server or VPS.""".stripMargin)

  val uName1: String = "tommy"
  val nset: String = "nset"
  val cName1: String = "computer"
  val cset: String = "cset"
  val userAtCom: String = uName1 + "@" + cName1
  val tcMajorVer: String = "11.0"
  val tcMinorVer: String = "15"
  def tcVer1: String = tcMajorVer + "." + tcMinorVer
  val javaMajorVer: String = "25"
  val domain1: String = "mywebsite.com"

  val uNameLTI: LabelTextInput = LabelTextInput("uName", "User Name", uName1)
  val uNameIUT: InputUpdaterText = uNameLTI.child2
  val cNameLTI: LabelTextInput = LabelTextInput("cName", "Computer Name", cName1)
  val cNameIUT: InputUpdaterText = cNameLTI.child2
  val nRam1: Int = 2
  val ramLNI: LabelNumInput = LabelNumInput("nRam", "System Ram", nRam1)
  val ramIUN: InputUpdaterNum = ramLNI.child2
  def tomcatDirPrompt: BashPromptSpan = BashPromptSpan.input2Text(uNameIUT, cNameIUT) { (uName, cName) => s"$uName@$cName:/opt/tomcat" }
  val tomVerLTI: LabelTextInput = LabelTextInput("version", "Tomcat Version", tcVer1)
  val tomVarIUT: InputUpdaterText = tomVerLTI.child2
  val jVer1: Int = 25
  val javaVerLNI: LabelNumInput = LabelNumInput("javaVer", "Java Version", jVer1)
  val javaVerIUN: InputUpdaterNum = javaVerLNI.child2
  val domainLTI: LabelTextInput = LabelTextInput("dName", "Domain Name", domain1)
  val domainIUT: InputUpdaterText = domainLTI.child2


  def p2: HtmlP = HtmlP("""There are default values here that you can change as you work down the page. Although once you've used a value, stick with it or you
  |will create an inconsistent system. Insert your own values below. the data is used for page generation locally and is not sent back to our servers.""".
  stripMargin,
  LabelInputsLine(uNameLTI, cNameLTI, ramLNI, tomVerLTI, javaVerLNI, domainLTI))

  def steps = HtmlOl(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13)

  val s1 = HtmlLi("Upgrade packages.",
  BashLine("sudo apt update"),
  BashLine("sudo apt upgrade"),
  "Install Fail2Ban to protect against brute force login attacks",
  BashLine("sudo apt install fail2ban"),
  BashLine("sudo systemctl enable --now fail2ban"),
  )

  val s2 = HtmlLi("""Lease a VPS. A virtual private server. The price of these have dropped considerably over the years and will almost certainly continue to
  |drop. You can purchase a VPS with a couple of cores and 4 Gig of RAM for a few dollars / pounds / Euros a month these days. If you are really tight with
  |money you could probably get away with 2 gigs, but I would recommend starting with a comfortable 4 gigs. When starting out I recommend just buying monthly,
  |as your needs will change. For the time being I don't have enough experience to make recommendations. I've had good service from Digital Ocean for a number
  |of years running a VPS for Apache Vanilla, but they are some what pricey to get 4 gigs of ram for a small project with minimal users. I intend to come back
  |and update this later. I'm currently using an Ubuntu Operating System, just out of familiarity. Now obviously if you are using your own desktop, laptop or
  |home server, you won't need this step and you will probably want to try that first before spending money on a VPS. But you will almost certainly need one to
  |get your site / app out to the world.""".stripMargin)

  
  def s3 = HtmlLi("Install Java. Currently suggesting Java 25 LTS. Note the jdk at the end of the version.",
  BashLine.inputNum(javaVerIUN)(n => s"sudo apt install openjdk-${n.str0}-jdk -y"),
  "Check the version",
  BashLine("java -version"),
  CodeOutputLines("""openjdk version "25" 2025-09-16""",
  "OpenJDK Runtime Environment (build 25+36-Ubuntu-1)",
  "OpenJDK 64-Bit Server VM (build 25+36-Ubuntu-1, mixed mode, sharing)"),
  "Open the all users environment configuration file",
  BashLine("sudo nano /etc/environment"),
  "Add line",
  BashLine("JAVA_HOME=/usr/lib/jvm/java-25-openjdk-amd64"),
  "Save and exit (Ctrl-X and then Y)",  
  BashLine("sudo reboot"),
  "After reboot or logging in again for remote server",
  BashLine("echo $JAVA_HOME"),
  CodeOutputLine("/usr/lib/jvm/java-25-openjdk-amd64")
  )

  val s4 = HtmlLi(
  s"""Create a new user and a new group of the same name and add it to the sudo group. For these examples we'll call it '$uName1'. I find it better to have a
  |different name for the user than the folder we will create next. Again for desktop, laptop and home server this is not necessary and you can use your own
  |username.""". stripMargin,
  BashLine.inputText(uNameIUT){ uName => s"sudo useradd -ms /bin/bash -G sudo $uName"},
  BashLine.inputText(uNameIUT)(uName => s"sudo passwd $uName"),  
  )

  val s5 = HtmlLi("""Create a directory for tomcat and change the owner and group. The directory doesn't have to be called tomcat and placed in the Opt
  |directory, but this is a pretty standard schema. You can use your own username on a home machine.""".stripMargin,
  BashLine("sudo mkdir /opt/tomcat"),
  BashLine.inputText(uNameIUT)(uName => s"sudo chown $uName:$uName /opt/tomcat"),
  SpanLine.inputText(uNameIUT)(uName => s"Switch user to $uName. Then change directory."),
  "Change user unless, you already login in as the tomcat owner.",
  BashLine.inputText(uNameIUT)(uName => s"sudo su $uName"),
  BashLine("cd /opt/tomcat"),
  """Create a directory called Base inside the tomcat directory. This will be used for CatalinaBase and will allow you to keep configuration files to use with
  |multiple installs and major version changes of Apache.""".stripMargin,
  BashLine(tomcatDirPrompt, "mkdir Base")
  )

  val s6 = HtmlLi("Go to the Tomcat Download page: ", HtmlA("https://tomcat.apache.org/download-11.cgi"), s""". Currently we're on major version 11. Generally
  |you should use the latest version. I haven't tested these instructions before 10.0, but they should work at least back to version 9, if you have some
  |specific reason to use an earlier version. At the time of updating the latest sub version is $tcVer1. Make sure you download the latest sub version, because
  |Apache cut the links to the older sub versions. Copy the tar.gz file link into the browser. Once its downloaded copy the sha256 code into the next command to
  |check the integrity of the download. If its good the sha code should be echoed back in red and the file name in white.""".stripMargin,
  BashLine(tomcatDirPrompt,
    SpanInline.inputText(tomVarIUT){ version => s"wget https://dlcdn.apache.org/tomcat/tomcat-11/v$version/bin/apache-tomcat-$version.tar.gz"}),
  BashLine(tomcatDirPrompt,
    SpanInline.inputText(tomVarIUT){ version => s"sha512sum apache-tomcat-$version.tar.gz | grep alongsequenceoflettersanddigits"})
  )

  val s7 = HtmlLi("""Then unpack the tar file and create a link. This will allow us to easily swap in an updated minor version of Tomcat 11.0. These are
  |released frequently.""".stripMargin,
  BashLine(tomcatDirPrompt, SpanInline.inputText(tomVarIUT){ version => s"tar xf apache-tomcat-$version.tar.gz -C /opt/tomcat"}),
  BashLine(tomcatDirPrompt, SpanInline.inputText(tomVarIUT){ version => s"ln -s apache-tomcat-$version tom11"}),
  "Then checking what we've got.",
  BashLine(tomcatDirPrompt, "ls"),
  CodeOutputLine.inputText(tomVarIUT){ version => s"apache-tomcat-$version  apache-tomcat-$version.tar.gz  Base  tom11"}
  )

  val s8 = HtmlLi("""Create the logs and conf directories and copy across the server.xml and web.xml files from the installation directory structure to the base
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
  HtmlCodePre.input2Text(cNameIUT, tomVarIUT){ (cName, version) => HtmlPage.titleOnly("Holding Page", s"This is coming from $cName, a tomcat $version server").out }
  )

  val s9 = HtmlLi("Create a systemd unit file.",
  BashLine("sudo nano /etc/systemd/system/tom11.service"),
  "Add the following code. Then control o, return, control x.",
  HtmlCodeLines(StrArr(
  "[Unit]",
  "Description=Apache Tomcat 11.0 Web Application Container",
  "After=network.target",
  "",
  "[Service]",
  "Type=forking",
   "",
  """Environment="JAVA_HOME=/usr/lib/jvm/java-1.25.0-openjdk-amd64"""",
  """Environment="CATALINA_PID=/opt/tomcat/Base/temp/tomcat.pid"""",
  """Environment="CATALINA_HOME=/opt/tomcat/tom11/"""",
  """Environment="CATALINA_BASE=/opt/tomcat/Base/"""").toSystemdDivs +%
  HtmlDiv.inputNum(ramIUN){ n =>  val nn = n * 256
    val xmsStr = nn.min(512).str0
    val xmxStr = (nn.min(512) * 2 + (nn - 512).min(0)).min(8192)
  s"""Environment="CATALINA_OPTS=-Xms${xmsStr}M -Xmx${(nn * 2).str0}M -server -XX:+UseParallelGC""""} ++
  StrArr(
  """Environment="JAVA_OPTS=-Djava.awt.headless=true -Djava.security.egd=file:/dev/./urandom"""",
  "ExecStart=/opt/tomcat/tom11/bin/startup.sh",
  "ExecStop=/opt/tomcat/tom11/bin/shutdown.sh").toSystemdDivs +%
  HtmlDiv.inputText(uNameIUT){ uName => s"User=$uName"} +%
  HtmlDiv.inputText(uNameIUT){ uName => s"Group=$uName" } ++
  StrArr(
  "UMask=0007",
  "RestartSec=10",
  "Restart=always",
  "[Install]",
  "WantedBy=multi-user.target").toSystemdDivs
  )
  )

  val s10: HtmlLi = HtmlLi(
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

  val s11: HtmlLi = HtmlLi("To switch to port 80 the http defaults",
  BashLine("sudo apt install authbind"),
  BashLine("sudo touch /etc/authbind/byport/80"),
  BashLine.inputText(uNameIUT)(uName => s"sudo chown $uName: /etc/authbind/byport/80"),
  BashLine("sudo chmod 500 /etc/authbind/byport/80"),
  "And for HTTPS to use 443",
  BashLine("sudo touch /etc/authbind/byport/443"),
  BashLine.inputText(uNameIUT)(uName => s"sudo chown $uName: /etc/authbind/byport/443"),
  BashLine("sudo chmod 500 /etc/authbind/byport/443"),
  "Reopen the Systemd Unit file.",
  BashLine("sudo nano /etc/systemd/system/tom11.service"),
  CodeChangeLine("ExecStart=/opt/tomcat/tom11/bin/startup.sh", "ExecStart=authbind --deep /opt/tomcat/tom11/bin/startup.sh"),  
  "Open the Tomcat configuration file.",
  BashLine("sudo nano /opt/tomcat/Base/conf/server.xml"),
  CodeChangeLine("""<Connector port="8080" protocol""".escapeHtml, """<Connector port="80" protocol""".escapeHtml),
  CodeChangeLine("""redirectPort=\"8443\"""", """redirectPort=\"443\"""".escapeHtml),  
  "reset",
  BashLine("sudo systemctl daemon-reload"),
  BashLine("sudo systemctl restart tom11"),
  "The page should now be available without the port :8080 suffix."
  )

  val s12 = HtmlLi("Install snap",
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
  BashLine("sudo chgrp -R tommy /etc/letsencrypt/live/"),
  BashLine("sudo chgrp -R tommy /etc/letsencrypt/archive/"),
  BashLine("sudo chmod -R 750 /etc/letsencrypt/live/"),
  BashLine("sudo chmod -R 750 /etc/letsencrypt/archive/"),
  BashLine.inputText(domainIUT){ dName => s"sudo chmod 640 /etc/letsencrypt/live/$dName/privkey.pem" },
  BashLine.inputText(domainIUT){ dName => s"sudo chmod 644 /etc/letsencrypt/live/$dName/cert.pem" },
  BashLine.inputText(domainIUT){ dName => s"sudo chmod 644 /etc/letsencrypt/live/$dName.com/chain.pem" },
  "Check permissions - if you dont have access then something wrong...",
  BashLine.inputText(domainIUT){ dName => s"ls -la /etc/letsencrypt/live/richstrat.com/" },
  )

  val s13 = HtmlLi("Configure Tomcat to use 443 & link to ssl cert above",
  BashLine("nano /opt/tomcat/Base/conf/server.xml"),
  "Uncomment the section and modify as below",
  HtmlCodePre.inputText(domainIUT){ dName =>
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
  SpanLine.inputText(domainIUT){ dName => s"Go to https://$dName" }  
  )
}
