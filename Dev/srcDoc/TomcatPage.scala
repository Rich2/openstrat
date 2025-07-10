/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*

/** Web page for running Apache Tomcat for Scala. */
object TomcatPage extends HtmlPage
{ override def head: HtmlHead = HtmlHead.titleCss("Apache Tomcat Server", "documentation")
  override def body: HtmlBody = HtmlBody(HtmlH1("Using Apache Tomcat Server"), central)

  def central: HtmlDiv = HtmlDiv.classAtt("central", p1, steps)

  def p1 = HtmlP("""This page is targeted at Scala Developers, who want to get a / some simple web applications going, or create a dynamic web site using Scala.
  |However nearly everything will also apply to people who want to use Java, Kotlin and other JVM language. Its not geared towards advanced professional Scala
  |developers who will almost all be using other solutions. If like me you come to the Tomcat Server, with only the experience of running Apache vanilla
  |servers, setting up Tomcat is significantly more complicated than the extreme simplicity of installing an Apache Vanilla server. Note referring to it as
  |Apache Vanilla is my own naming scheme as referring to it just as "Apache" can be confusing. So here follows a list of steps for setting up Tomcat on your
  |own Desktop, laptop, home server or VPS.""".stripMargin)

  def steps = HtmlOl(
  HtmlLi("""Lease a VPS. A virtual private server. The price of these have dropped considerably over the years and will almost certainly continue to drop. You
  |can purchase a VPS with a couple of cores and 4 Gig of RAM for a few dollars / pounds / Euros a month these days. If you are really tight with money you
  |could probably get away with 2 gigs, but I would recommend starting with a comfortable 4 gigs. When starting out I recommend just buying monthly, as your
  |needs will change. For the time being I don't have enough experience to make recommendations. I've had good service from Digital Ocean for a number of years
  |running a VPS for Apache Vanilla, but they are some what pricey to get 4 gigs of ram for a small project with minimal users. I intend to come back and update
  |this later. I'm currently using an Ubuntu Operating System, just out of familiarity. Now obviously if you are using your own desktop, laptop or home server,
  |you won't need this step and you will probably want to try that first before spending money on a VPS. But you will almost certainly need one to get your site
  |/ app out to the world.""".stripMargin),

  HtmlLi("""Create a new user and a new group of the same name. For these examples we'll call it 'tommy'. Again for desktop, laptop and home server this is not
  |necessary and you can use your own username.<br>""".stripMargin,
  HtmlBashMulti("sudo useradd tommy", "sudo passwd tommy")),


  HtmlLi("""Create a directory for tomcat and change the owner and group. The directory doesn't have to be called tomcat are placed in the Opt directory, but
  |this is a pretty standard schema. You can use your own username on a home machine.<br>""".stripMargin,
  HtmlBashMulti("sudo mkdir opt/tomcat", "sudo chown tomcat:tomcat opt/tomcat")),

  HtmlLi("Go to the Apache Download page: ", HtmlA("https://tomcat.apache.org/download-11.cgi"), """. Currently we're on major version 11. Generally you should
  |use the latest version. I haven't tested these instructions before 10.0, but they should work at least back to version 9, if you have some specific reason to
  |use an earlier version.At the time of writing I'm using the latest sub vsersion 11.0.9. Copy the tar.gz file link into the browser. Once its downloaded copy
  |the sha256 code into the next command to check the integrity of the download. If its good the sha code should be echoed back in red and the file name in
  |White.<br>""".stripMargin,
  HtmlBashPromptMulti("tommy@ser:/opt/tomcat$", "wget https://dlcdn.apache.org/tomcat/tomcat-11/v11.0.9/bin/apache-tomcat-11.0.9.tar.gz",
  "tommy@ser:/opt/tomcat$", "sha512sum apache-tomcat-11.0.9.tar.gz | grep alongsequenceoflettersanddigits"))
  )
}