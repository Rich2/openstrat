/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*

/** Web page for running Apache Tomcat for Scala. */
object TomcatPage extends HtmlPage
{ override def head: HtmlHead = HtmlHead.titleCss("Apache Tomcat Server", "documentation")
  override def body: HtmlBody = HtmlBody(HtmlH1("Dev Module"), central)

  def central: HtmlDiv = HtmlDiv.classAtt("central", p1, steps)

  def p1 = HtmlP("""This page is targeted at Scala Developers, who want to get a / some simple web applications going, or create a dynamic web site using Scala
  |. However nearly everything will also apply to people who want to use Java, Kotlin and other JVM language. Its not geared towards advanced professional Scala
  | developers who will almost all be using other solutions. If like me you come to the Tomcat Server, with only the experience of running Apache vanilla
  | servers, setting up Tomcat is significantly more complicated than the extreme simplicity of installing an Apache Vanilla server. Note refering to it as
  | Apache Vanilla is my own naming scheme as referring to it just as "Apache" can be confusing. So here follows a list of steps for setting up Tomcat on your
  | own Desktop, laptop, home server or VPS.""".stripMargin)

  def steps = HtmlOl(
    HtmlLi("""Lease a VPS. A virtual private server. The price of these have dropped considerably over the years and will almost certainly continue to
  | drop. You can purchase a VPS with a couple of cores and 4 Gig of RAM for a few dollars / pounds / Euros a month these days. If you are really tight with
  | money you could probably get away with 2 gigs, but I would recommend starting with a comfortable 4 gigs. When starting out I recomend just buying monthly,
  | as your needs will change. For the time being I don't have enough experience to make recommendations. I've had good service from Digital Ocean for a number
  | of years running a VPS for Apache Vanilla, but they are some what pricey to get 4 gigs of ram for a small project with minimal users. I intend to come back
  | and update this later. I'm currently using an Ubuntu Operating System, just out of familiarity. Now obviously if you are using your own desktop, laptop or
  | home server, you won't need this step and you will probably want to try that frist before spending money on a VPS. But you will almost certainly need one to
  | get your site / app out to the world.""".stripMargin),

  HtmlLi("""Create a new user and a new group of the same name. For these examples we'll call it 'tommy'. Again for desktop, laptop and home server this is not
  | necessary and you can use your own username.<br>""".stripMargin,
    HtmlBashMulti("sudo useradd tommy", "sudo passwd tommy"))
  )
}