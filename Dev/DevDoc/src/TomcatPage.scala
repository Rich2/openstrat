/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*, wcode.*

/** Web page for running Apache Tomcat for Scala. */
object TomcatPage extends HtmlPageInput
{ given thisPage: HtmlPageInput = this
  override def head: HtmlHead = HtmlHead.titleCss("Apache Tomcat Server", "documentation")
  override def body: HtmlBody = HtmlBody(HtmlH1("Using Apache Tomcat Server"), central,
  //  XComment("/openstrat/Dev/DevDoc/DevDocJs/target/scala-3.7.3/devdocjs-opt/"),
    HtmlScript.jsSrc("tomcat.js"), HtmlScript.main("TomcatPageJs"))

  def central: HtmlDiv = HtmlDiv.classAtt("central", p1, steps)

  def p1 = HtmlP("""This page is targeted at Scala Developers, who want to get a / some simple web applications going, or create a dynamic web site using Scala.
  |However nearly everything will also apply to people who want to use Java, Kotlin and other JVM language. Its not geared towards advanced professional Scala
  |developers who will almost all be using other solutions. If like me you come to the Tomcat Server, with only the experience of running Apache vanilla
  |servers, setting up Tomcat is significantly more complicated than the extreme simplicity of installing an Apache Vanilla server. Note referring to it as
  |Apache Vanilla is my own naming scheme as referring to it just as "Apache" can be confusing. So here follows a list of steps for setting up Tomcat on your
  |own Desktop, laptop, home server or VPS.""".stripMargin)

  val uName1: String = "tommy"
  val nset: String = "nset"
  val cName1: String = "computer"
  val cset: String = "cset"
  val userAtCom: String = uName1 + "@" + cName1
  val majorVersion: String = "11.0"
  val minorVersion: String = "12"
  def version1: String = majorVersion + "." + minorVersion

  def steps = HtmlOl(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10)
  val s1 = HtmlLi("""Lease a VPS. A virtual private server. The price of these have dropped considerably over the years and will almost certainly continue to
  |drop. You can purchase a VPS with a couple of cores and 4 Gig of RAM for a few dollars / pounds / Euros a month these days. If you are really tight with
  |money you could probably get away with 2 gigs, but I would recommend starting with a comfortable 4 gigs. When starting out I recommend just buying monthly,
  |as your needs will change. For the time being I don't have enough experience to make recommendations. I've had good service from Digital Ocean for a number
  |of years running a VPS for Apache Vanilla, but they are some what pricey to get 4 gigs of ram for a small project with minimal users. I intend to come back
  |and update this later. I'm currently using an Ubuntu Operating System, just out of familiarity. Now obviously if you are using your own desktop, laptop or
  |home server, you won't need this step and you will probably want to try that first before spending money on a VPS. But you will almost certainly need one to
  |get your site / app out to the world.""".stripMargin)

  def s2 = HtmlLi("Install Java. Currently suggesting Java 21 LTS. Note the jdk at the end of the version.",
  BashLine("sudo apt install openjdk-21-jdk -y"),
  "Check the version",
  BashLine("java -version"),
  HtmlCodeLines("""openjdk version "21.0.8" 2025-07-15""",
  "OpenJDK Runtime Environment (build 21.0.8+9-Ubuntu-0ubuntu124.04.1)",
  "OpenJDK 64-Bit Server VM (build 21.0.8+9-Ubuntu-0ubuntu124.04.1, mixed mode, sharing)")
  )

  val lti1: LabelTextInput = LabelTextInput("uName", "User Name", uName1)
  val ti1: InputUpdaterText = lti1.child2
  val lti2: LabelTextInput = LabelTextInput("cName", "Computer Name", cName1)
  val ti2: InputUpdaterText = lti2.child2
  val nRam1 = 2
  val lni1: LabelNumInput = LabelNumInput("nRam", "System Ram", nRam1)
  val ni1 = lni1.child2
  def tomcatDirPrompt: BashPromptSpan = BashPromptSpan.input2Text(ti1, ti2){ (uName, cName) => s"$uName@$cName:/opt/tomcat"}
  val lti3: LabelTextInput = LabelTextInput("version", "Tomcat Version", version1)
  val ti3: InputUpdaterText = lti3.child2

  val s3 = HtmlLi(
  HtmlP("""There are default values here that you can change as you work down the page. Although once you've used a value, stick with it or you will create an
  |inconsistent system. Insert your own values below. the data is used for page generation locally and is not sent back to our servers.""".stripMargin,
  LabelInputsLine(lti1, lti2, lni1, lti3)),
  s"""Create a new user and a new group of the same name. For these examples we'll call it '$uName1'. I find it better to have a different name for the user
  |than the folder we will create next. Again for desktop, laptop and home server this is not necessary and you can use your own username.""". stripMargin,
  BashLine.inputText(ti1){uName => s"sudo useradd -ms /bin/bash $uName"},
  BashLine.inputText(ti1)(uName => s"sudo passwd $uName"))

  val s4 = HtmlLi("""Create a directory for tomcat and change the owner and group. The directory doesn't have to be called tomcat and placed in the Opt
  |directory, but this is a pretty standard schema. You can use your own username on a home machine.""".stripMargin,
  BashLine("sudo mkdir /opt/tomcat"),
  BashLine.inputText(ti1)(uName => s"sudo chown $uName:$uName /opt/tomcat"),
  SpanLine.inputText(ti1)(uName => s"Switch user to $uName. Then change directory."),
  BashLine.inputText(ti1)(uName => s"sudo su $uName"),
  BashLine("cd /opt/tomcat"),
  """Create a directory called Base inside the tomcat directory. This will be used for CatalinaBase and will allow you to keep configuration files to use with
  |multiple installs and major version changes of Apache.""".stripMargin,
  BashLine(tomcatDirPrompt, "mkdir Base")
  )

  val s5 = HtmlLi("Go to the Apache Download page: ", HtmlA("https://tomcat.apache.org/download-11.cgi"), s""". Currently we're on major version 11. Generally
  |you should use the latest version. I haven't tested these instructions before 10.0, but they should work at least back to version 9, if you have some
  |specific reason to use an earlier version. At the time of writing I'm using the latest sub version $version1. Copy the tar.gz file link into the browser.
  |Once its downloaded copy the sha256 code into the next command to check the integrity of the download. If its good the sha code should be echoed back in red
  |and the file name in white.""".stripMargin,
  BashLine(tomcatDirPrompt, "wget https://dlcdn.apache.org/tomcat/tomcat-11/v11.0.11/bin/apache-tomcat-11.0.11.tar.gz"),
  BashLine(tomcatDirPrompt, "sha512sum apache-tomcat-11.0.11.tar.gz | grep alongsequenceoflettersanddigits"))

  val s6 = HtmlLi("""Then unpack the tar file and create a link. This will allow us to easily swap in an updated minor version of Tomcat 11.0. These are
  |released frequently.""".stripMargin,
  BashLine(tomcatDirPrompt, "tar xf apache-tomcat-11.0.11.tar.gz -C /opt/tomcat"),
  BashLine(tomcatDirPrompt, "ln -s apache-tomcat-11.0.11 tom11"),
  "Then checking what we've got.",
  BashLine(tomcatDirPrompt, "ls"),
  CodeOutputLine("apache-tomcat-11.0.11  apache-tomcat-11.0.11.tar.gz  Base  tom11"))

  val s7 = HtmlLi("""Copy across the server.xml and web.xml files from the installation directory structure to the base directory structure. If the catalina
  |base and catalina home directories are the same, which is often the case in beginners installation instructions, then this is redundant.""".stripMargin,
  BashLine(tomcatDirPrompt, "mkdir Base/conf"),
  BashLine(tomcatDirPrompt, "cp tom11/conf/server.xml tom11/conf/web.xml Base/conf"),
  "Create a home page for your server. Again not necessary if base and home are set to the same directory.",
  BashLine(tomcatDirPrompt, "mkdir -p Base/webapps/ROOT"),
  BashLine(tomcatDirPrompt, "nano Base/webapps/ROOT/index.html"),
  "Copy the code below into the editor.",
  HtmlPage.titleOnly("Holding Page", "This is coming from a tomcat 11.0.11 server").htmlEscape
  )

  val s8 = HtmlLi("Create a systemd unit file.",
  BashLine("sudo nano /etc/systemd/system/tom11.service"),
  HtmlCodeLines(StrArr(
  "[Unit]",
  "Description=Apache Tomcat 11.0 Web Application Container",
  "After=network.target",
  "",
  "[Service]",
  "Type=forking",
   "",
  """Environment="JAVA_HOME=/usr/lib/jvm/java-1.21.0-openjdk-amd64"""",
  """Environment="CATALINA_PID=/opt/tomcat/tom11/temp/tomcat.pid"""",
  """Environment="CATALINA_HOME=/opt/tomcat/tom11/"""",
  """Environment="CATALINA_BASE=/opt/tomcat/Base/"""").toSystemdSpans +%
  SpanLine.inputNum(ni1){n =>  val nn = n * 256
    val xmsStr = nn.min(512).str0
    val xmxStr = (nn.min(512) * 2 + (nn - 512).min(0)).min(8192)
  s"""Environment="CATALINA_OPTS=-Xms${xmsStr}M -Xmx${(nn * 2).str0}M -server -XX:+UseParallelGC""""} ++
  StrArr(
  """Environment="JAVA_OPTS=-Djava.awt.headless=true -Djava.security.egd=file:/dev/./urandom"""",
  "ExecStart=/opt/tomcat/tom11/bin/startup.sh",
  "ExecStop=/opt/tomcat/tom11/bin/shutdown.sh").toSystemdSpans +%
  SpanLine.inputText(ti1){ uName => s"User=$uName"} +%
  SpanLine.inputText(ti1){ uName => s"Group=$uName" } ++
  StrArr(
  "UMask=0007",
  "RestartSec=10",
  "Restart=always",
  "[Install]",
  "WantedBy=multi-user.target").toSystemdSpans
  )
  )

  val s9 = HtmlLi(
  BashLine("sudo sytemctl start tom11"),
  BashLine("sudo sytemctl status tom11"),
  "If status good, open a web page at localhost:8080",
  BashLine("sudo sytemctl enable tom11"),
  )

  val s10 = HtmlLi("To switch to port 80",
  BashLine("sudo apt install authbind"),
  BashLine("sudo touch /etc/authbind/byport/80"),
  BashLine.inputText(ti1)(uName => s"sudo chown $uName: /etc/authbind/byport/80"),
  BashLine("sudo chmod 500 /etc/authbind/byport/80"),
  BashLine("sudo nano /etc/systemd/system/tom11.service"),
  BashLine("change --> ExecStart=/opt/tomcat/tom11/bin/startup.sh"),
  BashLine("to     --> ExecStart=authbind --deep /opt/tomcat/tom11/bin/startup.sh"),
  BashLine("sudo systemctl daemon-reload"),
  BashLine("sudo nano /opt/tomcat/Base/conf/server.xml"),
  BashLine("""change --> <Connector port=\"8080\""""),
  BashLine("""to     --> <Connector port=\"80\""""),
  BashLine("sudo systemctl restart tom11")
  )
}