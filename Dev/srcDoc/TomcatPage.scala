/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*

/** Web page for running Apache Tomcat for Scala. */
object TomcatPage extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.titleCss("Apache Tomcat Server", "https://richstrat.com/Documentation/documentation")


  override def body: HtmlBody = HtmlBody(HtmlH1("Dev Module"), central)

  def central: HtmlDiv = HtmlDiv.classAtt("central")

  def p1 = HtmlP("""This page is targeted at Scala Developers, who want to get a or some simple web applications going, or create a dynamic web site using Scala
  |. However nearly everything will also apply to people who want to use Java, Kotlin and other JVM language. Its not geared towards advanced professional Scala
  | developers who will almost all be using other solutions. If like me you come to the Tomcat Server, with only the experience of running Apache vanilla
  | servers, setting up Tomcat is significantly more complicated than the extreme simplicity of installing an Apache Vanilla server. Note refering to it as
  | Apache Vanilla is my own naming scheme as referring to it just as "Apache" can be confusing. So her follows a list of steps for setting up Tomcat on your
  | own Desktop, laptop, home server or VPS.""".stripMargin)
}